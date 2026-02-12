# My Media tracker

A media rating application, used for tracking movies, games, books and all sorts of media consuming by users.

<sub><sup>Sh... it's still a WIP</sup></sub>
## How to run
First you want to create the following database
```SQL
CREATE DATABASE IF NOT EXISTS `my_media_tracker`
	DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
	DEFAULT ENCRYPTION='N';
```
```YML
    url: jdbc:mysql://localhost:3306/my_media_tracker?useTimezone=true&serverTimezone=UTC
    username: root
    password: root
```
Be sure to use the following DB connection, otherwise change it on the application.yml:
```
localhost:3306
```
And then run the following command in the root folder:
```bash
mvn clean package spring-boot:run -DskipTests
```
Now you can access [Swagger Page](http://localhost:8080/swagger-ui/index.html#/) to see the complete list of endpoints

Default username and password: admin:admin123
## TODO
- [x] Swagger
- [x] Authentication (JWT)
- [ ] Entities
  - CRUD on Media: done
  - Shield endpoints based on user permissions: done
  - CRUD on user_media: TODO
- [ ] Tests
- [ ] Content negotiation
- [ ] HATEOAS
- [ ] Tests (unit and automated)
- [ ] File upload/download

## License

[MIT](https://choosealicense.com/licenses/mit/)