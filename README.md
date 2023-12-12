# Web Spotify Data Visualization System

## Description
This is the backend part of the project "Web Spotify Data Visualization System", which helps users to visualize their personal data obtained from Spotify within their GDPR rights.
The frontend part of the project is [here](https://github.com/zw2375/spotify-visualization-report-app).

The back-end part applies Java Spring Boot as the web framework to handle requests and responses, and uses MySQL as the database storage. 
It also applies libraries like MyBatis Plus, Hutool, and Jackson for faster development. The overall structure of the backend is shown in this picture:

![Backend Structure](src/main/resources/static/backend%20service.png)

For a detailed explanation of this project including both frontend and backend design and implementations, you can see this [report](./src/main/resources/static/2023_Fall_CSCI_2390_Final_Project_Report.pdf).

## Get Started
To run this backend service, you need to complete 3 steps.
1. Set up your MySQL Storage by running tne sql statements in [init.sql](src/main/resources/sql/init.sql). 
Then configure relative database information in [application.yml](src/main/resources/application.yml).

2. Go to [Spotify Web API website](https://developer.spotify.com/documentation/web-api/tutorials/getting-started) and follow the instructions to get your own Spotify client id and client secret.
Then configure the id and secret in [application.yml](src/main/resources/application.yml).

3. Install all the dependencies using maven and our pom.xml. There are also other configurations can be modified in [application.yml](src/main/resources/application.yml) and you can tailor your needs. After all those steps, you could run the start the Spring Boot application:
    ```bash
    $ cd project-directory
    $ mvn clean install
    $ java -jar target/project-jar-file.jar
    ```

## API Documentation
This project integrates Knife4j to display its api information. To access the interface, simply go to http://localhost:8011/api/doc.html after the Spring Boot application is running. You should be able to see and test the apis there.

![API Interface](src/main/resources/static/api%20documentation.png)
