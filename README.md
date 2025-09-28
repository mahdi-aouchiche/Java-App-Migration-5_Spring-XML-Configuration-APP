# Java App Migration: Part 5 - Spring XML Configuration APP

Author: Mahdi Aouchiche (<https://github.com/mahdi-aouchiche/Java-App-Migration-5_Spring-XML-Configuration-APP>)

## Overview

This Java App Migration Part 5 show cases the migration of the previous app, which implements Hibernate using an Object-Relational Mapping (ORM) approach, to a Spring app using Inversion of Control (IoC) Container with only XML based configuration. 

The Java Web Application is still an example to manage employees in a company with different department.

## Major Advantages

* Introduction of the Inversion of Control (IoC) design principle and Dependency Injection (DI) reduce boilerplate code by letting Spring IoC Container manage and configure ojects initiation and configuration and allows developers focus on the business logic.
* Reduce coupling through DI which provides dependencies at runtime
* Improve testability by easily providing the dependency we want to test.
* Centralize the configuration in the applicationContext.xml file which gives central visibility to understand the structure and make updates of changes in one place. 

Note: Login info to run the app: username = Admin, password = 123

