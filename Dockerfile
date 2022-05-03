FROM maven:3.6.3-openjdk-14
RUN mkdir /usr/src/job4j_pooh
WORKDIR /usr/src/job4j_pooh
COPY . .
RUN mvn package -Dmaven.test.skip=true
EXPOSE 9000/tcp
CMD ["java", "-jar", "target/job4j_pooh-1.0.jar"]