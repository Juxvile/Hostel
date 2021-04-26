FROM java:8
ADD target/hostel-0.0.1-SNAPSHOT.jar hostel-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT [ "java","-jar","hostel-0.0.1-SNAPSHOT.jar" ]