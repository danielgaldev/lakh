# First Spring App
Following [Tutorial: 11. Developing Your First Spring Boot Application](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-first-application.html)

The empty project with only the Dockerfiles (and maybe the pom.xml) is all that is needed for starting development and deployment, thus it is a good starting point for beginner Maven/Spring users (including myself :P).

## Goal of this project
Developing a Spring application without downloading anything on my local machine, from scratch. To achieve this, I use Docker containers.
1. Packaging container to build the project using Maven image
2. Deployment container to run the app (maybe using Tomcat)
3. Database container, which can be accessed by the app, using MySQL
4. Whatever else is needed for my assignment

## How to use this repository
0. Create a Docker volume named `mvn-repo`
1. Set up `pom.xml`
2. Write code in `src/`
3. `docker-compose up`

## Explanation

To prevent Maven from downloading files every time the image is built, we use an external volume named `mvn-repo`. You might need a Docker volume created with this name to use the repo.

### Building with the Maven image
The jar/war creation is done using a `maven` image. Look in the Dockerfile at the root of the project. First the source code and `pom.xml` are added, then `mvn` commands are run using `docker-compose`. You can change the `mvn` commands easily in the `docker-compose.yml`.
> Note: the jar needs copied outside the target/ directory into another directory to allow for deployment. The &ast;.jar files are excluded from the repository.

### Deployment
Spring boot creates jars with Tomcat embedded, so a jre image is enough. The image runs the jar using a `docker-compose` command. The app is run only when the build container finished running. To achieve this, the deployment container keeps pinging the build container, and when it gets no reply, the app starts. The command responsible for this is:
```
bash -c "while ping -c1 package &>/dev/null; do sleep 1; done; echo package_finish && java -jar $$(ls *.jar)"
```
(thus we need to install `bash` in the alpine deployment container)
