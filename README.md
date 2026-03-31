cics-java-liberty-restapp
===========================

[![Build](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml)

Sample RESTful web application for deployment to a Liberty JVM server in CICS. The application is supplied with two resources:

1. `InfoResource` - This queries the JVM server environment using system properties and uses JAXB beans to return a JSON response detailing the CICS environment.
1. `ReverseResource` - This is similar to `InfoResource`, but uses the JCICS API to link to the COBOL program `EDUCHAN` using channels and containers. An input string is passed to `EDUCHAN`, which is then reversed and returned, along with the time from CICS.

Further extensions to this application are available in the repository [cics-java-liberty-restappext](https://github.com/cicsdev/cics-java-liberty-restappext) which provides several code examples for accessing CICS resources from Java using the JCICS API.

The following Java source components are supplied in the [`cics-java-liberty-restapp-app/src/main/java`](cics-java-liberty-restapp-app/src/main/java) directory in this repository.

## Java package com.ibm.cicsdev.restapp
* [`CICSApplication`](src/main/java/com/ibm/cicsdev/restapp/CICSApplication.java) - Sets the `ApplicationPath` for resources in this application
* [`InfoResource`](src/main/java/com/ibm/cicsdev/restapp/InfoResource.java) - Returns JSON structure using `CICSInformation` bean
* [`ReverseResource`](src/main/java/com/ibm/cicsdev/restapp/ReverseResource.java) - Returns JSON structure using `ReverseResult` bean


## Java package com.ibm.cicsdev.restapp.bean
* [`CICSEnvironment`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSEnvironment.java) - JAXB bean returning JSON structure containing information about CICS product and version
* [`CICSInformation`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSInformation.java) - JAXB bean returning JSON structure containing CICS applid, time and JVM server name and instance of `CICSEnvironment`
* [`ReverseResult`](src/main/java/com/ibm/cicsdev/restapp/bean/ReverseResult.java) - JAXB bean returning JSON structure containg input and output containers sent to `EDUCHAN` COBOL program

## Contents

- [cics-java-liberty-restapp](./cics-java-liberty-restapp) - Top-level project.
- [cics-java-liberty-restapp-app](./cics-java-liberty-restapp-app) - Application source code.
- [cics-java-liberty-restapp-bundle](./cics-java-liberty-restapp-bundle) - CICS bundle plug-in based project. Use with Gradle and Maven builds.
- [etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle](./etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle) - CICS Explorer based CICS bundle project, contains Web application bundle-part. Use with CICS Explorer 'Export to zFS' deployment capability.


## Prerequisites
- CICS TS V5.5 or later
- Java SE 1.8 or later on the workstation
- One of the following on your workstation:
    - Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty
    - An IDE of your choice that supports Gradle or Maven 
    - A command line, to run Gradle or Maven

## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - DFHCSDUP sample input stream for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](cics-java-liberty-restapp-app/src/cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
* [`build.gradle`](build.gradle) - A sample Gradle build file to enable an automated build of the deployable WAR
* [`pom.xml`](pom.xml) - A sample Maven build file to enable an automated build of the deployable WAR
   

## Downloading

- Download the sample as a [ZIP](archive/refs/heads/main.zip) and unzip onto the workstation or clone the repository using a git client.


## Building 


The sample includes an Eclipse project configuration, a Gradle build, a Maven POM, Gradle/Maven Wrappers and Eclipse Gradle and Maven project natures offering a wide range of build options with the tooling and IDE of your choice.

Choose from the following 2 main approaches:

1. Use the command line to drive the supplied Gradle or Apache Maven Wrappers, this means there is no requirement for Gradle, Maven, Eclipse, or CICS Explorer SDK to be installed.
1. Use the CICS Explorer SDK capability to build the application in Eclipse.

 

> [!IMPORTANT]
> The sample comes pre-configured for use with a JDK 1.8 and CICS TS V5.5 Libraries for Java EE & Jakarta EE 8. When you initially import the project to your IDE, if your IDE is not configured for a JDK 1.8, or does not have CICS Explorer SDK installed, you might experience local project compile errors. 
If you are building and deploying with Gradle or Maven then you don't necessarily need to fix the local Eclipse build problems as the build files are used to build the sample.



### Option 1a: Building with Gradle

The sample comes pre-configured with a Gradle wrapper and Gradle build files to facilitate automated builds. The `gradlew` command is used to invoke the wrapper and should be invoked from the top-level 'cics-java-liberty-restapp' directory which will then invoke the individual build.gradle files for each sub-project. 

The JVM server the CICS bundle is targeted at is controlled through the `cics.jvmserver` property, defined in the [`cics-java-liberty-restapp-bundle/build.gradle`](cics-java-liberty-restapp-bundle/build.gradle) file, or alternatively can be set on the command line:

**Gradle Wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle Wrapper (Windows):**
```shell
gradlew.bat clean build
```

**Gradle (command-line & setting jvmserver):**
```shell
gradlew clean build -Pcics.jvmserver=MYJVM
```

If successful, a WAR file is created inside the `cics-java-liberty-restapp-app/build/libs` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/build/distribution` directory. 

### Option 1b: Building with Apache Maven

The sample comes pre-configured with a Maven wrapper and Maven build files to facilitate automated builds. The `mvnw` command is used to invoke the wrapper and should be invoked from the top-level 'cics-java-liberty-restapp' directory which will then invoke the individual Maven `pom.xml` files for each sub-project. 

If building a CICS bundle ZIP the CICS JVM server name for the WAR bundle part should be modified in the 
 `cics.jvmserver` property, defined in [`cics-java-liberty-restapp-bundle/pom.xml`](cics-java-liberty-restapp-bundle/pom.xml) file under the `defaultjvmserver` configuration property, or alternatively can be set on the command line as shown.


**Maven Wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven Wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Maven wrapper (command-line & setting jvmserver):**
```shell
mvnw clean verify -Dcics.jvmserver=MYJVM
```

A WAR file is created inside the `cics-java-liberty-restapp-app/target` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/target` directory.

### Option 2: Building with Eclipse

Install the latest version of the IBM CICS Explorer [see](https://www.ibm.com/support/pages/cics-explorer-downloads)

Import the projects into Eclipse using **File &rarr; Import &rarr; General &rarr; Existing projects into workspace &rarr; Select archive file** and select the downloaded zip as the archive. Ensure you select **Search for nested projects** and include the CICS Bundle project `com.ibm.cicsdev.wlp.restapp.cicsbundle`.

To resolve build issues in Eclipse you should configure the application project's build-path to add your preferred combination of CICS TS, JDK, and Liberty's Enterprise Java libraries (Java EE or Jakarta EE). On the `cics-java-liberty-restapp-app` project select Select **Build Path  &rarr; Configure Build Path  &rarr; Java Build Path** then on the **Libraries** tab, click **Add Library CICS with Enterprise Java and Liberty** and choose the appropriate CICS and Enterprise Java versions.
If the project built correctly the Eclipse Problems view should no longer have any errors displayed.


## Deploying

### Configuring the Liberty JVM server
1. Create a Liberty JVM server.
2. Install the JVM server.
3. Add the `jaxrs-1.1` (or later version) Liberty feature to `server.xml` depending on your version of Java EE.


### Deploying the CICS Bundle with CICS Explorer
1. First check the name of the JVMSERVER in the .warbundle file of the CICS bundle project (com.ibm.cicsdev.wlp.restapp.cicsbundle), and ensure this matches the name of your JVMSERVER resource defined in CICS. The default used is DFHWLP.
2. Export the CICS bundle project to zFS by selecting **Export Bundle project to z/OS Unix File System** from the context menu in Eclipse. The samples uses the directory `/u/cics1/com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.


### Deploying the CICS Bundle from Gradle or Maven
1. Manually upload the ZIP file from the _cics-java-liberty-restapp-bundle/target_ or _cics-java-liberty-restapp-bundle/build/distributions_ directory to zFS.
2. Unzip this ZIP file on zFS (e.g. `${JAVA_HOME}/bin/jar -xvf /path/to/bundle.zip`).
3. Create a CICS BUNDLE resource definition,  and install it into the CICS region. 


### To install the sample into a CICS region:
1. Check the JVM server is in the `Enabled` state.
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.
1. Create a CICS BUNDLE definition, an example for usage with the DFHCSDUP utility is provided in [DFHCSD.txt](etc/DFHCSD.txt) file. Ensure you set the bundle directory attribute to the zFS location you previously deployed the bundle proejct to.
1. Install the CICS BUNDLE resource into CICS and ensure it becomes enabled.



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

## License
This project is licensed under [Apache License Version 2.0](LICENSE).

## Usage terms
By downloading, installing, and/or using this sample, you acknowledge that separate license terms may apply to any dependencies that might be required as part of the installation and/or execution and/or automated build of the sample, including the following IBM license terms for relevant IBM components:

• IBM CICS development components terms: https://www.ibm.com/support/customer/csol/terms/?id=L-ACRR-BBZLGX

