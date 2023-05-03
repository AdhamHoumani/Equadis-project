# Equadis-project

this solution contains 3 main project (2 backend, 1 frontend)
1- BankCustomerSystem.Backend.Core\Core (java spring-boot 2.6.3/ java 17)
2- BankCustomerSystem.Backend.Finance\Finance (java spring-boot 2.6.3/ java 17)
3- BankCustomerSystem.Web\ClientApp (Angular 14)

this project depends on external database using postgreSQL 12 (port 5432)
to run app & connect with the db we should create database with name bank_customer_system_db
and create 2 schema in this db : core & finance
then after run the app, flyway library will migrate the database and create tables ...