cics-java-liberty-restapp
===========================

[![Build](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml)

Sample RESTful web application for deployment to a Liberty JVM server in CICS. The application is supplied with two resources:

1. `InfoResource` - This queries the JVM server environment using system properties and uses JAXB beans to return a JSON response detailing the CICS environment.
1. `ReverseResource` - This is similar to `InfoResource`, but uses the JCICS API to link to the COBOL program `EDUCHAN` using channels and containers. An input string is passed to `EDUCHAN`, which is then reversed and returned, along with the time from CICS.

Further extensions to this application are available in the repository [cics-java-liberty-restappext](https://github.com/cicsdev/cics-java-liberty-restappext) which provides several code examples for accessing CICS resources from Java using the JCICS API.

The following Java source components are supplied in the [`src/main/java`](src/main/java) directory in this repository.

## Java package com.ibm.cicsdev.restapp
* [`CICSApplication`](src/main/java/com/ibm/cicsdev/restapp/CICSApplication.java) - Sets the `ApplicationPath` for resources in this application
* [`InfoResource`](src/main/java/com/ibm/cicsdev/restapp/InfoResource.java) - Returns JSON structure using `CICSInformation` bean
* [`ReverseResource`](src/main/java/com/ibm/cicsdev/restapp/ReverseResource.java) - Returns JSON structure using `ReverseResult` bean


## Java package com.ibm.cicsdev.restapp.bean
* [`CICSEnvironment`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSEnvironment.java) - JAXB bean returning JSON structure containing information about CICS product and version
* [`CICSInformation`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSInformation.java) - JAXB bean returning JSON structure containing CICS applid, time and JVM server name and instance of `CICSEnvironment`
* [`ReverseResult`](src/main/java/com/ibm/cicsdev/restapp/bean/ReverseResult.java) - JAXB bean returning JSON structure containg input and output containers sent to `EDUCHAN` COBOL program


## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - DFHCSDUP sample input stream for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](src/cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
* [`build.gradle`](build.gradle) - A sample Gradle build file to enable an automated build of the deployable WAR
* [`pom.xml`](pom.xml) - A sample Maven build file to enable an automated build of the deployable WAR


## Pre-reqs

* CICS TS V5.5 or later.
* Java SE 1.8 or later on the workstation
* Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty, or any IDE that supports usage of the Maven Central artifact [com.ibm.cics:com.ibm.cics.server.](https://search.maven.org/artifact/com.ibm.cics/com.ibm.cics.server) 

   

## Configuration

The sample Java classes are designed to be added to a dynamic web project and deployed into a Liberty JVM server as a WAR, either using the dropins directory or using 
a CICS bundle project. 



### To add the resources to Eclipse:
1. Using an Eclipse development environment create a dynamic web project called `com.ibm.cicsdev.restapp` and add the Java samples to the `src` folder
1. Add the CICS Liberty JVM server libraries to the build path of your project. 
1. Ensure the web project is targeted to compile at a level that is compatible with the Java level being used on CICS. This can be achieved by editing the Java Project Facet in the project properties.
1. Create a CICS bundle project called `com.ibm.cicsdev.restapp.cicsbundle` and add a dynamic web project include for the project created in step 1.

## Building the Sample

The sample can be built using Gradle or Maven to produce a WAR file and optionally a CICS Bundle archive.

### Building with Gradle

A WAR file is created inside the `build/libs` directory and a CICS bundle ZIP file inside the `build/distributions` directory.

If using the CICS bundle ZIP, the CICS JVM server name should be modified in the  `cics.jvmserver` property in the gradle build [file](build.gradle) to match the required CICS JVMSERVER resource name, or alternatively can be set on the command line.

**Gradle Wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle Wrapper (Windows):**
```shell
gradle.bat clean build
```
**Gradle (command-line):**
```shell
gradle clean build
```
**Gradle (command-line & setting jvmserver):**
```shell
gradle clean build -Pcics.jvmserver=MYJVM
```


### Building with Apache Maven
A WAR file and a CICS bundle ZIP file are created inside the `target/` directory.

If building a CICS bundle ZIP the CICS bundle plugin bundle-war goal is driven using the maven verify phase. The CICS JVM server name should be modified in the `<cics.jvmserver>` property in the [`pom.xml`](pom.xml) to match the required CICS JVMSERVER resource name, or alternatively can be set on the command line.

**Maven Wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven Wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Maven (command-line):**
```shell
mvn clean verify
```
**Maven (command-line & setting jvmserver):**
```shell
mvn clean verify -Dcics.jvmserver=MYJVM
```

## Deployment
### To start a JVM server in CICS:
1. Enable Java support in the CICS region by adding the `SDFJAUTH` library to the `STEPLIB` concatenation and setting `USSHOME` and the `JVMPROFILEDIR` SIT parameters.
    * This step is **not** required if using CICS 5.5 or later
1. Define a Liberty JVM server called `DFHWLP` using the supplied sample definition `DFHWLP` in the CSD group `DFH$WLP`.
1. Copy the CICS sample `DFHWLP.jvmprofile` zFS file to the `JVMPROFILEDIR` directory specified above and ensure the `JAVA_HOME` variable is set correctly.
1. Add the `jaxrs-1.1` (or later version) Liberty feature to `server.xml` depending on your version of Java EE.
1. Install the `DFHWLP` resource defined in step 2 and ensure it becomes enabled.


### To deploy the samples into a CICS region:
1. Using the CICS Explorer export the CICS bundle project to a zFS directory. The samples use the directory `/u/cics1/com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.
1. Create a CICS BUNDLE definition referencing the zFS directory created in step 1.
1. Install the CICS BUNDLE resource.
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.


**Note:** A sample DFHCSDUP input file for the required CICS BUNDLE resource definition is supplied in the supporting file [DFHCSD.txt](etc/DFHCSD.txt) file.  


## Running the Sample

### Using a web browser you can issue the following HTTP GET requests

* http://host:port/com.ibm.cicsdev.restapp/rest/cicsinfo

This will invoke the `InfoResource` class and return the following JSON response with information about the target CICS system:

`{"applid":"IYK2Z32E","jvmServer":"DFHWLP","time":"2016-09-09T16:19:55.384Z","cicsEnvironment":{"cicsProduct":"CICS Transaction Server for z/OS","cicsVersion":"5.3.0"}}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse

This will invoke the `ReverseResource` class which links to the CICS COBOL program and reverses the default string "Hello from Java" returning the following JSON response:

`{"time":"2016-09-09T16:15:52.756Z","original":"Hello from Java","reverse":"avaJ morf olleH","truncated":false}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse/ilovecics

This will invoke the `ReverseResource` class which links to the CICS COBOL program reversing the input string "ilovecics" as follows:

`{"time":"2016-09-09T16:15:32.466Z","original":"ilovecics","reverse":"scicevoli","truncated":false}`

