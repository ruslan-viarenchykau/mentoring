###################################################################################################

JPA

Create models: Employee, EmployeeStatus, Address, Personal, Project, Unit

Take into account the following:
EmployeeStatus should be Enum type
Address should be embedded to Employee object
Relationship between Employee and EmployeePersonalInfo should be one-to-one
Relationship between Employee and Project should be many-to-many
Relationship between Unit and Employee should be one-to-many

Implement Service API which provides:
Create Employee / Unit / Project
Find employee / Unit / Project by id
Delete employee / Unit / Project by id
Update Employee / Unit / Project by id
Add Employee to Unit by id’s
Assign Employee for Project by id’s

Use Hibernate to store data (users, accounts) of web-application in a database.

###################################################################################################

Spring core

Take as base application JPA task.

Part 1. Use annotations or java-based configuration.

Implement services using dependency injection by type on field, constructor, setter
Implement a service: populate field values from properties file (e.g. database connection service)
Implement a service using dependency injection by name, use different scope
Implement bean factory with annotations
Implement beans configuration as Java class using annotations

Part 2. Configure beans using xml.

Use different bean scope (singleton, prototype, thread)
Use factory-method (singleton) and factory-bean (service locator), implement FactoryBean interface
Pass bean references as constructor parameters, string constants and primitive types via xml configuration
Use setter approach for passing bean parameters
Divide complex xml configs into simple
Implement bean which sets property username="Awesome" in spring context
Implement bean that sends message to log at initialization and destroy phaces