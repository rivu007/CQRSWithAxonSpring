# Task-List application with Axon and Spring-Boot

## Introduction

This is a sample Task-List application to demonstrate Spring Boot (1.5.x) and Axon Framework (3.3.3).

* Domain Driven Design
* CQRS
* Event Sourcing
* Task based User Interface

#### Technologies used in the project

* Gradle Wrapper
* Spring Boot (web/axon/security/web-socket/jpa)
* Angular JS (Demo front-end)
* Lombok
* Mongo (Event store)
* H2
* Docker (Mongo running as container)

#### Prerequisites installed

* JAVA 1.8
* Docker

#### What is Axon?
[Axon](http://www.axonframework.org/) is a lightweight framework that helps developers build scalable and 
extensible applications. The axon framework is focused on making life easier 
for developers that want to create a java application based on the CQRS principles.

##### How Axon works?
It helps developers to create such applications by providing the most important 
building blocks. With Axon you manipulate domain objects (called **aggregate**) with 
commands which will lead to **events**. A **command** is an intend to change aggregates.
It contains all the necessary information to execute it. 
Each command is handled by a Command Handler. 
It verifies the command and executes a method on the aggregate to change its state.
A command can also be rejected. The change of the aggregate will lead to events.
An event is a change of the aggregate that has already happened.
You cannot change the past, therefore you should not validate or reject events.
Only the events are persisted in the event store. 
The state of the aggregate is volatile, but can always be reconstructed from the event store.

  

## Getting started

To start this web application just follow these steps:

* Checkout the Task-List project : `git clone https://github.com/rivu007/CQRSWithAxonSpring.git`

* Navigate to the root of the project : `cd cqrswithaxonspring`

##### To run the application locally

It's time to run the Task-List application: 

```
$ ./gradlew bootRun
``` 

To do a quick sanity check, please browse to : `http://localhost:8080/health`

Expected Response:
```
{
    "status": "UP"
}
```

Choose your favourite browser and navigate to : `http://localhost:8080` and follow the
 instruction on screen.
 
##### Running the Test

* Simply run the `test` gradle task to have all the test running:

```
$ ./gradlew test
```

## Lombok Setup:

* IntelliJ

  * Go to `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processing`
  * Check `Enable Annotation processing`

* Eclipse

  * Change to directory which contains lombok.jar (if not available download it)
  * Run `java -jar lombok.jar` in console to open Lombok Installation Wizard
  * Define path to eclipse.exe
  * Restart eclipse

## Resources

* [Axon Framework Docs](https://docs.axonframework.org/part-i-getting-started/introduction)
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Data JPA](https://projects.spring.io/spring-data-jpa/)
