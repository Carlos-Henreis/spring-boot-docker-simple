FROM openjdk:11.0.4-jre-slim

RUN apt update
RUN apt install -y tesseract-ocr-por

RUN tesseract --list-langs    
RUN tesseract -v
RUN find / -name "tessdata"


ENV LC_ALL=C
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata

WORKDIR /app 
		
COPY target/spring-docker-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]