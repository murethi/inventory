# Brandlogs Inventory Code Test

### How to run
You can clone this repo,build an image and run it using docker.
I have used the Jib maven plugin to build the docker image. Here is a linked to the
Jib maven plugin</br>
* https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin

To build an image using Jib run the following command

````
mvn compile jib:dockerBuild
````

in case you do not have jib plugin configured, you can build the app by running

````
mvn compile com.google.cloud.tools:jib-maven-plugin:dockerBuild
````
This will build docker images for the 5 services on this app:
* Api Gateway
* Discovery Server
* Product Service
* Store Service
* Vender Service

* You can change the image name by editing within the root ````pom.xml```` file. <br>

You can now run the application on docker using the following command

````
docker compose up
````

The application will run on ````http://localhost:8080 ```` and database on
````http://localhost:3307 ````


Each service has a readme file in its directory for instructions on how to 
access different endpoints

**I have included a postman collection within the root**