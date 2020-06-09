# version required
* Java: 8
* mysql: 5.7

# Idea plugin Needed
* mapstruct https://plugins.jetbrains.com/plugin/10036-mapstruct-support/
* lombok https://plugins.jetbrains.com/plugin/6317-lombok/

# local environment set up
## Install mysql in docker
username: root, password: 123456
```shell script
  docker run --restart always --name mysql-hero-book \
    -e MYSQL_ROOT_PASSWORD=123456 -d -p 3306:3306 mysql:5.7.27 \
    --character-set-server=utf8 --collation-server=utf8_general_ci \
    --lower_case_table_names=1
  ``` 
## Create database in docker
create database `hero_book`
1. execute command on the container
    ```shell script
    docker exec -it mysql-hero-book bash -c 'mysql -uroot -p123456'
    ```
2. create database
    ```sql
   create database hero_book;
    ```
## Create user & grant privileges
```text
create user <user>@<user-host> identified by <password>;
grant all privileges on <database.table> to <user>@<user-host>;
```
***sample***
```mysql
create user test@'%' identified by '123456';
grant all privileges on hero_book_test.* to test@'%';
```
## Install rabbitmq in docker
```shell script
  docker run -d --name rabbitmq \
    -p 5672:5672 -p 15672:15672 \
    --restart always rabbitmq:management
```
