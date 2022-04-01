# Quiz App

<p  align="center" >
  <img width="900" height="510" src="image/start.png">
</p>

Hello everyone in this project you can have account. This app have quiz and a lot of question in them, also user can see right or wron answer the question after test.

Clone
--------

```sh
git clone https://github.com/drnserhio/quizProd
```

# Technology use in App
+ Spring
+ MySQL
+ Angular


# Security
When you run application. You can use two role Admin and User. App use jwt, and user has field role, and ovveride userdetailsservice in customprincipal, and loadbyusername find in database.

Admin has dashboard and he can check all passed test users, create new quiz,question, also add, remove question in quiz.
User can use open test, and also he check answer after the end test.

Admin
--------

```sh
username: joy; password:5600;
```

User
--------

```sh
username: jack; password:5600;
```

# Getting started

You'll need Java 17 installed. `Maven jar`

    deploy.sh

# Try it out with [Docker](https://www.docker.com/)

You'll need Docker installed. `Docker compose`

    deploy-docker.sh

To test that it works, open a browser tab at http://localhost:4200 .

# Help

gmail : `duran199756@gmail.com`

telegram : `@drn_serhio`


