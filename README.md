
# Spotify API Automation




## Goals
The goal for this project is to build a REST Assured API test automation framework using Java + Maven + TestNG. While using clean code practices and integrate it with CI framework (Jenkins).
## Technologies/Tools used in building the framework
- Rest Assured
- Java, TestNG
- Allure Reports
- Hamcrest
- Jackson API
- Lombok
- GitHub & Jenkins
## Installation

Install and run test locally follow these steps
- Install maven
- Install java 11+
- Create an account on [Spotify Dev](https://developer.spotify.com/)
- Generate your client_id, client_secret, user_id and refresh_token for the spotify account
- Either create a secret.properties file or pass them as command parameters (Thanks to aeonbits owner)
- Download, extract this repo and run these commands to build project
```bash
cd SpotifyRestAssuredProj

(If you decide to create a secret.properties)
mvn clean test 

(Else you can pass those values as arguments)
mvn clean test -Dclient_id="__" -Dclient_secret="__" -Duser_id="__" -Drefresh_token="__"
```
    
## Automated Tests

All the automated tests can be found on this sheet. [Test Scenarios](https://docs.google.com/spreadsheets/d/1O5E3oCaI9KkHVDCKGS8RxCOl-w2rsU6sZtM3d3txy7s/)

As per scope, decided to automate Playlist related CRUD APIs - Create playlist, Get playlists corresponding to a user,  Get particular playlist details, Update playlist details, Add track/ item to playlist and finally a few E2E(end to end) scenarios for smoke runs.
## Results

We have a framework which is highly Scalable and extensible.

Some of the highlights for the clean code in this framework are -
- Separation of API layer from test layer
- POJOs for Serialization and Deserialization
- Singleton Design Pattern is used for Loading Config files only once and use forever. Also, using aeonbits.owner allows for dynamical fetching of properties either from command line arguments or from file.
- Lombok for reducing Boilerplate code.
- Robust reporting and logging using Allure. Specifically, using various annotations to filter the reports and Step annotation of testNG to print request/ response payload for debugging.
- Automated access token renewal. TokenManager handles the automatic renewal of tokens only once when token is expired which is efficient.
- Integration with Jenkins and github. Also, using Git hooks whenever there is a commit, regression is triggered on jenkins.

Here are few screenshots of jenkins execution -

![image](https://github.com/user-attachments/assets/3f323238-6331-4ff2-8b7a-8c1d8114863a)
We can see that the run was started by Github webhooks


![image](https://github.com/user-attachments/assets/bd25e314-1cb2-47d3-abf4-1b0d9f97cfce)
We can see trends, overall tests on overview of allure report


![image](https://github.com/user-attachments/assets/fb46dce9-02cb-437e-961d-2f5bba898f13)
We also have detailed request/ response logs for each test to further debug/ investigate incase of an issue.
