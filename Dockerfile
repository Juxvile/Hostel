FROM java:8
ADD out/artifacts/hostel_jar/hostel.jar hostel.jar
EXPOSE 8082
ENTRYPOINT [ "java","-jar","hostel.jar" ]
