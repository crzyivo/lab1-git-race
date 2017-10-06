# Installing Gradle

## Windows 10
1. Download the latest Gradlle distribution, in this case, version 4.2
1. Unpack the distribution.
	1. Create a new directory, named Gradle.
	1. Extract the the zip content in the newly created folder. You can unpack the distribution using an archiver tool of your choice.
1. Configure your system environment.
	1. In File Explorer right-click on the This PC (or Computer)  icon, then click Properties -> Advanced System Settings -> Environmental Variables.
	1. Under System Variables select Path, then click Edit. Add an entry for `C:\Gradle\gradle-4.2\bin` and name it GRADLE_HOME. Click OK to save.
1. Verify your installation with the command `gradle -v` on your console system

#### Common error.
* `tools.js not found` when `gradle -v`

    To fix this, a new user variable must be created (like `GRADLE_HOME` one).
    Name it `JAVA_HOME`, and refers to JDK installation folder (`C:\Program Files\Java\jdk1.8`... is a possible route).
    Ultimately, edit the variable PATH adding `;%JAVA_HOME%\bin` at the end.

## Ubuntu
1. To install Gradle on your Ubuntu system, use this command at a terminal prompt:
```
sudo apt install gradle
```

2. Verify your installation with the command `gradle -v` on your Ubuntu system

## Arch Linux
1. To install Gradle on your Arch Linux system, use the following command:
```
sudo pacman -S gradle
```

2. Verify that Gradle has been installed using `gradle -v`.

# Deployment with Heroku

### Procfile
A [Procfile](https://devcenter.heroku.com/articles/procfile) is a text file in the root directory of your application
that defines process types and explicitly declares what command should be executed to start your app.
It is optional, and you need to include it only if your application needs any special configuration to run.
The default configuration is the following:

```
web: java -Dserver.port=$PORT $JAVA_OPTS -jar build/libs/*.jar
```

The name `web` is important. It declares that this process type will be attached to the `HTTP routing` stack of Heroku,
and receive web traffic when deployed.

In our case, we will create a `war` file at `build\libs` path, which will be used as the web application. For avoiding
errors when looking for the .war, it is necessary to specify its name in the `build.gradle` file:
```
war {
    baseName = 'webeng'
}
```

And last, we must specify the special behavior when starting the application. In this case, define the path to get the `.war` file.


```
web: java -Dserver.port=$PORT $JAVA_OPTS -jar build/libs/webeng.war
```

## Step-by-step deployment
1. Create a free account in [Heroku](https://signup.heroku.com/).
1. If you already had an account, the main page will show you your web applications.
1. Click on `New` at the right upper corner, and then click on `Create New App`.
1. Define a name for you web application and select `Europe` as region. Then click on `Create App`.
1. Go to `Deploy` tab and select `GitHub` as the deployment method. This will synchronize your GitHub account to Heroku.
Afterwards, Heroku will ask you which repository does you want to synchronize. Select `lab1-git-race`.
1. Now you will see a new functionality. We will focus on `Automatic deploys`. This allow us to select a branch and every push
will deploy a new version of the application. Also, you can tell Heroku to deploy the application only if `Travis CI` has
been passed without errors. Select the `master` branch and `Wait for CI to pass before deploy` option, since is a need for us to
pass all tests on every push to master. Finally, click on `Enable automatic deploys`.
1. If you want to access to the web application, go to the right upper corner and click on `Open app`. If you are facing
with errors at the moment of visualize the web page, you can also see the log by clicking on `More ->  View logs` button

### Spring Framework Overview
The [Spring](https://spring.io/docs) framework provides a comprehensive programming and configuration model for modern Java applications for
any platform. A key element of Spring is infrastructural support at the application level: Spring focuses on the wiring
of enterprise applications so that teams can focus on application-level business logic, without unnecessary ties
to specific deployment environments.

Spring's main features:
* [Dependency injection](http://www.vogella.com/tutorials/SpringDependencyInjection/article.html) - Decoupling dependent components from each other makes testing easier and
        increases re-usability.
* [Aspect-Oriented Programming (AOP)](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html) - Through the concept of 'aspects', Spring achieves modularization of 		concerns such as transaction management that cut across multiple types and objects. Spring's declarative transactions work both with XML files or annotations.
* [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) web application and RESTful web service framework - Spring provides a strong framework for developing
        web services and applications.

### Trigger a new Travis-CI build
Travis brings now a beta feature that allows to trigger a new build without altering git history. This could be useful for
building the code with Travis if commits were added before setting up Travis:
1. On your Travis repository, click on 'More options' at the right.
1. Select 'Trigger build'.
1. Fill the fields with the new config (optional) and click on 'Trigger custom build'.

# Deploy your app using Gradle

Thanks to the huge variety of plugins available for Gradle, Gradle's automation capabilities can be greatly extended. In this case, we'll be using [the Gradle Cargo Plugin](https://github.com/bmuschko/gradle-cargo-plugin) to deploy our project to a local or even a remote server using Gradle.

In this example we'll be using Tomcat 8.0, but [more applications are supported](https://codehaus-cargo.github.io/cargo/Home.html). Be sure that the applications is correctly installed and that the permissions of its configurations files are correctly set. Otherwise, you will be facing a lot of errors.

## Configuration

Firstly we need to add the following code snippet to the very beginning of our `build.gradle`, in order to retrieve the Cargo Plugin

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.bmuschko:gradle-cargo-plugin:2.2.3'
    }
}
```

Next we need to add the following code snippet to use the plugin
```
apply plugin: 'com.bmuschko.cargo'

```
We also need to set up its dependencies. To do so, copy the following code snippet to the `dependencies` closure of your `build.gradle`
```
    def cargoVersion = '1.4.5'
    cargo "org.codehaus.cargo:cargo-core-uberjar:$cargoVersion",
          "org.codehaus.cargo:cargo-ant:$cargoVersion"
```

Now we can configure how the Plugin works. The following code shows an example. It should be included at the end of your `build.gradle`

```
cargoStartLocal.dependsOn assemble
cargoDeployRemote.dependsOn assemble

cargo {
  containerId='tomcat8x'
  
  remote {
    hostname = 'valor.humiltat.es'
    username = 'seny'
    password = 'imparapla'
  }

  local {
    homeDir=file('/usr/share/tomcat8')
  }
} 
```

With the first two lines we are making the Cargo plugin tasks depend on assemble. Prior to deploying to a local or remote server the project will be assemble.

The `cargo` closure defines the properties of the plugin. `containerId` refers to the application where our app will be deployed.

Within the first closure we can find to two different closures:
  * The `local` closure defines how the app is deployed in our local server. The path to the server is defined using the `homeDir` property. 

  * The `remote` closure defines how the app is deployed in our remote server. While `hostname` indicates the hostname of our server, `username` and `password` define the credentials for the remote web container.

## Usage

Once we have correctly configured our `build.gradle` we can deploy our application using Gradle.

To deploy to the local server use the following command: 

```
gradle cargoStartLocal
```

To stop the local server use the following one:
```
gradle stopLocal 
```

To deploy the app to the remote server use this command:
```
gradle cargoDeployRemote
```

To undeploy it use this command:
```
gradle cargoUndeployRemote
```

## Travis integration

In addition, we can use Travis to automatically deploy our app to our local server once all the changes pushed to the master branch have passed the test. To do so, add the following code snippet to your `.travis.yml` file.

```
after_success:
  - if [ "$TRAVIS_BRANCH" == "master" ]; then
      ./gradlew cargoDeployRemote
    fi
```

## Password encryption

As you can see this solution has a big downside. Our password is public and without any encryption. Gradle doesn't provide native support to encryption, but a workaround exists. 

Firstly, we need to create a `gradle.properties` file in the same directory where our `build.gradle` is stored. This file should also be included in our `.gitignore` file, because we just want to keep it locally. In our `gradle.properties` file we should add the following lines, containing our password.

```
encrypted_user = seny
```

Now we need to modify our `build.gradle`, replacing our password with the following:

```
  remote {
    hostname = 'valor.humiltat.es'
    username = seny
    password = ${encrypted_pass}
  }

```

These method might not be the best one, but at least our password is not in plain text and public. As this method does keep our pass locally, our Travis Build will fail. To fix this, we need to encrypt our password using Travis. To do so, we need to execute the following command in the directory where our `.travis.yml` is located.

```
travis encrypt PASSWD="imparapla" --add
```

Now we need to modify our `.travis.yml` to use the encrypted password. Replace the `after_success` with the following code:
```
after_success:
  - if [ "$TRAVIS_BRANCH" == "master" ]; then
      ./gradlew -Pencrypted_pass=${PASSWD} cargoDeployRemote
    fi
```