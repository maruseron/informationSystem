### Back-End Design Notes: Genericizing Functional Service Handlers
###### Marcelo Rodríguez, 8 nov 2025
While I'm in the middle of migrating all controller business logic into the
service layer (where it should have been from the start), I took the liberty
to do some important refactoring to make the application easier to maintain.
I'm documenting the process in this entry for future reference.

### Separating an Employee endpoint into Controller and Service
Currently, the backend only has `InsertEntityHereController`s that do
everything: they expose REST endpoints, handle the requests, and do the 
persistence-related work through JPA repositories, all in the same class.
I admit this is far from fancy, and made my own life harder trying to wrestle
with full entities in `@RequestBody` arguments which were supposed to be partial.

At some point, however, enough is enough, so I decided to add a service layer to
the backend to solve this issue. For brevity, we'll only be seeing examples 
from the `Employee` entity, which is the core of the whole backend. Here's the
overall structure:
```java
@Entity
public class Employee extends Base { // id and createdAt are here
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String nid;
    private Role role;
}
```
###### Assume all columns are properly annotated with their nullity and uniqueness indicators.
As for the DTOs, I introduced three: one for creation requests, one for 
presentation purposes, and one for updating existing entries. Named after CRUD 
operations, here they are:
```java
public record Create(String username, String password, 
                     String firstName, String lastName,
                     String nid, String role) {}

public record Read(int id, String username, 
                   String firstName, String lastName) {}

public record Update(String username, String password, 
                     String firstName, String lastName,
                     String role) {}
```
All of these are partial structures: neither `Create` nor `Update` need an `id`
field because it's either auto-generated or sent as a path variable, `Read`
does not include the password because it's sensible information that should not be 
exposed to the frontend, and so on and so forth.

These partial structures are a lifesaver. Instead of having to fight with a
partial `Employee` object, I can just tell my controller to expect these specs
in different methods on an endpoint:
```java
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Integer id,
                                @RequestBody EmployeeDTO.Update request) {}
```
Now onto the good part: the `EmployeeService`. It started out quite naively.
Let's look at an early iteration of the `EmployeeService.update` method the 
controller is delegating to:

```java
public Either<EmployeeDTO.Read, HttpResult> update(
        final int id, final EmployeeDTO.Update request) {
    
    if (!employeeRepository.existsById(id))
        return Either.right(new HttpResult(HttpStatus.NOT_FOUND));

    // fetch employee
    var employee = employeeRepository
            .findById(id)
            .orElseThrow(RuntimeException::new);

    // if username is changing and target username is already in use
    if (!employee.getUsername().equals(request.username())
            && employeeRepository.existsByUsername(request.username())) {
        return Either.right(new HttpResult(HttpStatus.CONFLICT,
                "This username is already in use."));
    }
    
    // if we've reached this place, it's safe to change all fields
    employee.setUsername(request.getUsername());
    employee.setPassword(request.getPassword());
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setNid(request.getNid());
    employee.setRole(request.getRole());
    
    // save to database and convert to read spec
    return Either.left(EmployeeDTO.Read.from(
            employeeRepository.save(employee)));
}
```
Going through the method, a couple of things stand out: first, I'm using `Either` 
monads. In case you don't know what they are or do, they're like `Optional`, but can
hold one of two possible valid states (left and right branches), instead of one 
valid and one faulty. Second, the code is awfully imperative. Surely we can make 
this a bit more manageable? We can indeed. The method `findById` in 
`employeeRepository` has the following signature:
```java
interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // this doesn't really go here, it's actually from JpaRepository
    Optional<Employee> findById(Integer id); 
}
```
Which is our starting point for a more concise `update` method. We know that
this method can only return an empty `Optional` if the id does not belong to any
employee, so we start by declaring this:

```java
public Either<EmployeeDTO.Read, HttpResult> update(
        final int id, final EmployeeDTO.Update request) {
    return employeeRepository
            .findById(id)
            .map(/* some operation here must map to an Either */)
            .orElseGet(() -> 
                    Either.right(new HttpResult(HttpStatus.NOT_FOUND)));
}
```
If the optional is empty, we unpack it as the right side of an `Either` with an
`HttpResult` signaling a `404 NOT FOUND` error. Now we need to add the bit of code
that actually does the work for us. I'm skipping the logic here, but this is the 
signature:
```java
Either<Employee, HttpResult> updateFields(final Employee employee,
                                          final EmployeeDTO.Update request) {}
```
Note it returns an `Either` of `Employee`or `HttpResult` - this is because the
validation logic might exit early with an `HttpResult` object indicating the
response to the front-end. For example, a taken username should return a 
`409 CONFLICT` indicating the username is already in use. If all validation 
succeeds, `updateFields` should return normally with an `Either` containing an
`Employee`.
```java
public Either<EmployeeDTO.Read, HttpResult> update(
        final int id, final EmployeeDTO.Update request) {
    return employeeRepository
            .findById(id)
            .map(e -> updateFields(e, request))
            .orElseGet(() -> 
                    Either.right(new HttpResult(HttpStatus.NOT_FOUND)));
}
```
With the update pipeline set, the only work remaining is saving to the database 
and mapping into a `Read` object: 
```java
public Either<EmployeeDTO.Read, HttpResult> update(
        final int id, final EmployeeDTO.Update request) {
    return employeeRepository
            .findById(id)
            .map(e -> updateFields(e, request))
            .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)))
            .map(employeeRepository::save)
            .map(EmployeeDTO.Read::from);
}
```
### Why?
After reading all of this, you might be wondering about the usefulness of this 
approach. After all, we just moved the imperative complexity of the update 
transaction to another method (updateFields). Isn't this just pulling the rug?

And you'd be right; but even pulling the rug has its uses. If you look at the
new update method closely, you can understand what we've done here: we've turned
`update` into an entity-agnostic pipeline of transformations, where the only
variance is found in the work `updateFields` is doing. Imagine for a second
we erased all information regarding an Employee entity from the last code snippet:
```java
public Either<ReadSpec, HttpResult> update(final int id, 
                                           final UpdateSpec request) {
    return repository
            .findById(id)
            .map(x -> updateFields(x, request))
            .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)))
            .map(repository::save)
            .map(ThisClass::convertToRead);
}
```
It's identical! This logic is common to all services in the application. It
stands to reason, then, that one could write a generic `BaseService<T, C, R, U>`
and have `EmployeeService` extend it with `Employee`, `EmployeeDTO.Create`, 
`EmployeeDTO.Read`, and `EmployeeDTO.Update`, only implementing the imperative
abstract methods like `updateFields` and conversions between
`T`s and `R`s. Maybe I'll follow this up with a document about that.