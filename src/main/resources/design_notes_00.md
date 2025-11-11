### Back-End Design Notes: General Object Shape
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
    startTime: Instant i { createdAt <= i <= ? }
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
### Entity: Attendance