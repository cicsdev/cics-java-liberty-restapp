# cics-java-liberty-restapp

[![Build](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/build.yaml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/build.yaml)
[![License](https://img.shields.io/badge/License-EPL%202.0-green.svg)](https://opensource.org/licenses/EPL-2.0)

## Overview

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

- [cics-java-liberty-restapp-app](./cics-java-liberty-restapp-app) - Application source code.
- [cics-java-liberty-restapp-bundle](./cics-java-liberty-restapp-bundle) - CICS bundle plug-in based project. Use with Gradle and Maven builds.
- [com.ibm.cicsdev.wlp.restapp.cicsbundle](./com.ibm.cicsdev.wlp.restapp.cicsbundle) - CICS Explorer based CICS bundle project, contains Web application bundle-part. Use with Eclipse and CICS Explorer.


## Prerequisites
- CICS TS V5.5 or later
- Java SE 1.8 or later on the workstation
- One of the following on your workstation:
    - Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty
    - An IDE of your choice that supports Gradle or Maven (or can run the wrappers)
    

## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - DFHCSDUP sample input stream for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](etc/src/cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
   

## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-restapp/archive/main.zip) and unzip onto the workstation


## Building 

The sample includes Eclipse project configurations, Gradle and Maven build files and Gradle/Maven Wrappers offering a wide range of build options with the tooling and IDE of your choice.

We document the following 2 approaches:

1. Use the command line to drive the supplied Gradle or Apache Maven wrappers, this means there is no requirement for Gradle, Maven, Eclipse, or CICS Explorer SDK to be installed. Alternatively the build files can also be used with locally installed Gradle or Maven runtimes.
1. Use the built-in Eclipse and CICS Explorer SDK capability


### Option 1a: Building with Gradle

For a complete build you should run the gradle build in the top-level `cics-java-liberty-restapp` directory which is designed to invoke the individual build.gradle files for each sub-project. 

If successful, a WAR file is created inside the `cics-java-liberty-restapp-app/build/libs` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/build/distribution` directory. 

The JVM server the CICS bundle is targeted at is controlled through the `cics.jvmserver` property, defined in the [`cics-java-liberty-restapp-bundle/build.gradle`](cics-java-liberty-restapp-bundle/build.gradle) file, or alternatively can be set on the command line as follows:

**Gradle wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle wrapper (Windows):**
```shell
gradle.bat clean build
```
**Gradle wrapper (command-line & setting jvmserver):**
```shell
./gradlew clean build "-Pcics.jvmserver=MYJVM"
```


### Option 1b: Building with Apache Maven

For a complete build you should run the Maven pom.xml file in the top-level `cics-java-liberty-restapp` directory. A WAR file is created inside the `cics-java-liberty-restapp-app/target` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/target` directory.

If building a CICS bundle ZIP the CICS JVM server name for the WAR bundle part should be modified in the 
 `cics.jvmserver` property, defined in [`cics-java-liberty-restapp-bundle/pom.xml`](cics-java-liberty-restapp-bundle/pom.xml) file under the `defaultjvmserver` configuration property, or alternatively can be set on the command line as follows:

**Maven wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Maven wrapoper (command-line & setting jvmserver):**
```shell
./mvnw clean verify "-Dcics.jvmserver=MYJVM"
```



### Option 2: Building with Eclipse

#### Importing the Project

To import the sample into Eclipse:
1. Clone the repository using your IDE's support, such as the Eclipse Git plugin, **or**
2. Download the zip archive and use the **File > Import > Existing Projects into Workspace** wizard and select the expanded zip archive directory as the root directory

**Important:** Ensure you check "Search for nested projects", and do not select **Copy projects into workspace**

#### Resolving Dependencies

The sample comes pre-configured with the CICS TS V5.5 with Java EE and Liberty 8 library in the Eclipse classpath. This means the project should compile immediately after import if you have the CICS Explorer SDK installed.

However, if you see compilation errors, or if you want to use Gradle or Maven for dependency management instead of the pre-configured CICS library, you have several options:

**Option 2a: Using Build Tool Integration**

If you have Gradle (Buildship) or Maven (m2e) integration installed in Eclipse, you can use the build tool to automatically resolve dependencies:

**For Gradle:**
1. Right-click on `cics-java-liberty-restapp` → **Run As** → **Gradle Build...**
2. In the dialog, enter `clean build` in the "Gradle Tasks" field
3. Click **Run** - this will download Java 8 (if needed via toolchain auto-provisioning) and resolve all dependencies
4. Once the build succeeds, right-click on `cics-java-liberty-restapp` → **Gradle** → **Refresh Gradle Project**
5. Clean and rebuild: **Project** → **Clean** → Select all projects → **Clean**

**For Maven:**
1. Right-click on `cics-java-liberty-restapp` → **Maven** → **Update Project** → Check "Force Update of Snapshots/Releases"
2. Clean and rebuild: **Project** → **Clean** → Select all projects → **Clean**

The build tool will automatically download and configure all required dependencies (CICS libraries, JAX-RS, JAXB, etc.) and update Eclipse's classpath.

**Note:** For Gradle projects, the initial "Run As → Gradle Build" step is required to trigger toolchain auto-provisioning if Java 8 is not installed. After a successful build, "Gradle → Refresh Gradle Project" will synchronize the Eclipse classpath with the resolved dependencies.

**Option 2b: Verifying Pre-Configured CICS Library (Default)**

The project is already configured with the CICS library. If you see errors:

1. Ensure you have the latest CICS Explorer SDK plug-in installed
2. Verify the CICS library is present: Right-click on `cics-java-liberty-restapp-app` → **Build Path** → **Configure Build Path** → **Libraries** tab
3. If the CICS library shows an error or is missing, remove it and re-add: Click **Add Library** → **CICS with Enterprise Java and Liberty** → Select **CICS TS V5.5 with Java EE and Liberty 8**
4. Clean and rebuild: **Project** → **Clean** → Select all projects → **Clean**

**Note:** The pre-configured CICS library (Option 2b) provides the fastest setup for Eclipse users. Gradle/Maven integration (Option 2a) is useful if you want to use the same dependency management approach across different IDEs or build environments.



## Deploying

The sample Java classes are designed to be built into a an WAR file and deployed into a Liberty JVM server using a CICS bundle resource. To do this you will need to configure a Liberty JVM server in your CICS region, deploy the WAR archive to zFS, and then install this into CICS using a CICS BUNDLE resource. In addition the EDUCHAN COBOL can  be deployed to support the function to call into CICS.

### Configuring the Liberty JVM server
1. Create a Liberty JVM server in your target CICS region
2. Install the JVM server.
3. Add the `jaxrs-1.1` (or later version) Liberty feature to `server.xml` depending on your version of Java EE.


### Deploying to zFS


#### Option 1 - Deploying CICS Bundle Maven/Gradle plugin builds
1. Upload the built CICS bundle ZIP file in binary to zFS from your *target* or */build/distributions* directory in the cics-java-liberty-restapp-bundle project.
1. Connect to USS on the host system (e.g. SSH).
2. Create a bundle directory in zFS for the project
1. Copy the CICS bundle ZIP file into the bundle directory.
1. Extract the CICS bundle ZIP file. This can be done using the `jar` command. For example:
   ```shell
   jar -xvf bundle.zip
   ```

#### Option 2 - Deploying using CICS Explorer SDK and the CICS bundle projects

1. Deploy the CICS bundle project `com.ibm.cicsdev.wlp.restapp.cicsbundle` from CICS Explorer to zFS using the **Export Bundle Project to z/OS UNIX File System** wizard. The samples use the sub-directory `com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.

### Installing into CICS

1. In CICS, create a BUNDLE resource definition, setting the bundle directory attribute to the zFS location you just exported to, and install it. 
**Note:** A sample DFHCSDUP input file for the required CICS BUNDLE resource definition is supplied in the supporting file [DFHCSD.txt](etc/DFHCSD.txt) file.  
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.




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

