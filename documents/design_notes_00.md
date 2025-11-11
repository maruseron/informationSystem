### Back-End Design Notes: General Object Shape
### CRUD
| entity              | creatable | readable | updatable | deletable |
|:--------------------|-----------|----------|-----------|-----------|
| absence             | Y         | Y        | Y         | N         |
| attendance          | Y         | Y        | N         | N         |
| brand               | Y         | Y        | N         | N         |
| client              | Y         | Y        | Y         | N         |
| devolution          | Y         | Y        | N         | N         |
| employee            | Y         | Y        | Y         | N         |
| payment             | Y         | N        | N         | N         |
| product             | Y         | Y        | Y         | N         |
| product_detail*     | N         | N        | N         | N         |
| purchase            | Y         | Y        | N         | N         |
| sale                | Y         | Y        | N         | N         |
| supplier            | Y         | Y        | N         | N         |
| transaction         | N         | Y        | N         | N         |
| transaction_detail* | N         | N        | N         | N         |
| user                | Y         | Y        | Y         | N         |
### Table of contents
| Entities                     |
|:-----------------------------|
| [Base Entity](#entity-base)  |
| [Absence](#entity-absence)   |
| [Employee](#entity-employee) |
### Entity: Base
```puml
Entity {
    id: Int
    createdAt: Instant
}
```
### Entity: Absence
```puml
// TODO: upper bound for start time
PermissionStatus: Enum {
    PENDING, APPROVED, REJECTED
}

Absence <: Entity {
    reason: String
    permissionStatus: PermissionStatus = PENDING
    startTime: Instant i { createdAt <= i <= START_TIME_UPPER_BOUND }
    duration: Int -> i { 60 <= i<= 480 }
    authorizer: Employee? = null
    employee: Employee
}

InputDTO<Absence> {
    reason
    startTime: Long
    duration
    employeeId: Int
}

OutputDTO<Absence> {
    id
    createdAt: Long
    reason
    permissionStatus: String
    startTime: Long
    duration
    authorizer: OutputDTO<Employee>?
    employee: OutputDTO<Employee>
}

UpdateDTO<Absence> {
    permissionStatus: String
    authorizerId: Int
}
```
### Entity: Attendance
```puml
Attendance <: Entity {
    startTime: Instant
    duration: Int
    employee: Employee
}

InputDTO<Attendance> {
    startTime: Long
    duration: Int
    employeeId: Int
}

OutputDTO<Attendance> {
    id
    createdAt: Long
    startTime: Long
    duration
    employee: OutputDTO<Employee>1
}

UpdateDTO<Attendance> {} ?
```
### Entity: Employee
```puml
/* TODO: should password updates go through the normal
         update endpoint? */
Employee <: Entity {
    username: String { unique }
    password: String { ... }
    firstName: String
    lastName: String
    nid: String { unique, regex }
    role: Role
}

InputDTO<Employee> {
    username
    password
    firstName
    lastName
    nid
    role: String
}

OutputDTO<Employee> {
    id
    createdAt: Long
    username
    firstName
    lastName
    nid
    role: String
}

UpdateDTO<Employee> {
    username
    password
    firstName
    lastName
    role: String
}
```