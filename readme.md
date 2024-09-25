# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.enigmacamp.warung-makan-bahari-api' is invalid and this project uses 'com.enigmacamp.warung_makan_bahari_api' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.3/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#using.devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#io.validation)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Swagger Link
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

### Miscellaneous Chattering

ini waktu export aplikasi ke 
````
jar mvn clean package -DskipTest -DDB_USERNAME=postgres -DDB_PASSWORD=123 -DJWT_SECRET=sukaSukaS4y@doNk -DDIRECTORY_PATH=/assets/img
````
ini buat bikin images docker 
````
build -t app-wmb .
````
setelah ini pastikan database di container postgres docker sudah dibuat. host_db sesuaikan dengan alamat ip container postgres

ini bikin container buat aplikasi java
````
docker run -d -e HOST_DB=172.17.0.2 -e POSTGRES_PASSWORD=123 -e DB_NAME=db_wmb4 -e JWT_SECRET=sukaSukaS4y@doNk -e DIRECTORY_PATH=/assets/img --mount type=volume,src=newfolder,destination=/var/lib/postgresql/data --name app-wmb app-wmb
````