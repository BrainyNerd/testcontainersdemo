#### MySQL database in docker container

`docker run --name test -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testcontainerdemo -e MYSQL_ROOT_HOST='%' -p 3321:3306 -d mysql/mysql-server:8.0.20`
