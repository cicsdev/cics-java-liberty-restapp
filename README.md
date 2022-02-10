cics-java-liberty-restapp
===========================

Sample RESTful web application for deployment to a Liberty JVM server in CICS. The application is supplied with two resources:

1. `InfoResource` - This queries the JVM server environment using system properties and uses JAXB beans to return a JSON response detailing the CICS environment.
1. `ReverseResource` - This is similar to `InfoResource`, but uses the JCICS API to link to the COBOL program `EDUCHAN` using channels and containers. An input string is passed to `EDUCHAN`, which is then reversed and returned, along with the time from CICS. 

The following Java source components are supplied in the [`src/Java`](src/Java) directory in this repository.

## Java package com.ibm.cicsdev.restapp
* [`CICSApplication`](src/Java/com/ibm/cicsdev/restapp/CICSApplication.java) - Sets the `ApplicationPath` for resources in this application
* [`InfoResource`](src/Java/com/ibm/cicsdev/restapp/InfoResource.java) - Returns JSON structure using `CICSInformation` bean
* [`ReverseResource`](src/Java/com/ibm/cicsdev/restapp/ReverseResource.java) - Returns JSON structure using `ReverseResult` bean


## Java package com.ibm.cicsdev.restapp.bean
* [`CICSEnvironment`](src/Java/com/ibm/cicsdev/restapp/bean/CICSEnvironment.java) - JAXB bean returning JSON structure containing information about CICS product and version
* [`CICSInformation`](src/Java/com/ibm/cicsdev/restapp/bean/CICSInformation.java) - JAXB bean returning JSON structure containing CICS applid, time and JVM server name and instance of `CICSEnvironment`
* [`ReverseResult`](src/Java/com/ibm/cicsdev/restapp/bean/ReverseResult.java) - JAXB bean returning JSON structure containg input and output containers sent to `EDUCHAN` COBOL program


## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - Output from a DFHCSDUP EXTRACT for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](src/Cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
* [`build.gradle`](build.gradle) - A sample Gradle build file to enable an automated build of the deployable WAR
* [`pom.xml`](pom.xml) - A sample Maven build file to enable an automated build of the deployable WAR


## Pre-reqs

* CICS TS V5.1 or later, due to the usage of the `getString()` methods.
* Java SE 1.8 on the z/OS system
* Java SE 1.8 on the workstation
* Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty, or any IDE that supports usage of the Maven Central artifact [com.ibm.cics:com.ibm.cics.server.](https://search.maven.org/artifact/com.ibm.cics/com.ibm.cics.server) 

   

## Configuration

The sample Java classes are designed to be added to a dynamic web project and deployed into a Liberty JVM server as a WAR, either using the dropins directory or using 
a CICS bundle project. 



### To add the resources to Eclipse:
1. Using an Eclipse development environment create an dynamic web project called `com.ibm.cicsdev.restapp` and add the Java samples to the `src` folder
1. Add the CICS Liberty JVM server libraries to the build path of your project. 
1. Ensure the web project is targeted to compile at a level that is compatible with the Java level being used on CICS. This can be achieved by editing the Java Project Facet in the project properties.
1. Create a CICS bundle project called `com.ibm.cicsdev.restapp.cicsbundle` and add a dynamic web project include for the project created in step 1.


### To start a JVM server in CICS:
1. Enable Java support in the CICS region by adding the `SDFJAUTH` library to the `STEPLIB` concatenation and setting `USSHOME` and the `JVMPROFILEDIR` SIT parameters.
1. Define a Liberty JVM server called `DFHWLP` using the supplied sample definition `DFHWLP` in the CSD group `DFH$WLP`.
1. Copy the CICS sample `DFHWLP.jvmprofile` zFS file to the `JVMPROFILEDIR` directory specified above and ensure the `JAVA_HOME` variable is set correctly.
1. Add the `jaxrs-1.1` or `jaxrs-2.0` Liberty feature to `server.xml` depending on your version of Java EE.
1. Install the `DFHWLP` resource defined in step 2 and ensure it becomes enabled.

### To deploy the samples into a CICS region:
1. Using the CICS Explorer export the CICS bundle project to a zFS directory. The samples use the directory `/u/cics1/com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.
1. Create a CICS BUNDLE definition referencing the zFS directory created in step 1.
1. Install the CICS BUNDLE resource.
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.

**Note:** sample DFHCSDUP EXTRACT output for the required CICS resource definitions is supplied in the supporting file [DFHCSD.txt](etc/DFHCSD.txt) file.  

### Running the Example

#### Using a web browser you can issue the following HTTP GET requests

* http://host:port/com.ibm.cicsdev.restapp/rest/cicsinfo

This will invoke the `InfoResource` class and return the following JSON response with information about the target CICS system:

`{"applid":"IYK2Z32E","jvmServer":"DFHWLP","time":"2016-09-09T16:19:55.384Z","cicsEnvironment":{"cicsProduct":"CICS Transaction Server for z/OS","cicsVersion":"5.3.0"}}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse

This will invoke the `ReverseResource` class which links to the CICS COBOL program and reverses the default string "Hello from Java" returning the following JSON response:

`{"time":"2016-09-09T16:15:52.756Z","original":"Hello from Java","reverse":"avaJ morf olleH","truncated":false}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse/ilovecics

This will invoke the `ReverseResource` class which links to the CICS COBOL program reversing the input string "ilovecics" as follows:

`{"time":"2016-09-09T16:15:32.466Z","original":"ilovecics","reverse":"scicevoli","truncated":false}`

