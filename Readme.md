#### Refer [build.gradle](https://github.com/BrainyNerd/testcontainersdemo/blob/master/build.gradle) and [src/test/resources/application.yaml](https://github.com/BrainyNerd/testcontainersdemo/blob/master/src/test/resources/application.yaml) for configuration. 
#### Run MySQL database in docker container

`docker run --name test -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_HOST='%' -p 3321:3306 -d mysql/mysql-server:8.0.20`

#### Connect to db and create new schema
`create schema testcontainerdemo;`

#### Command to Start application
` ./gradlew bootRun`

#### Swagger UI:
`http://localhost:8000/swagger-ui.html`

#### Command to run tests
` ./gradlew test`
