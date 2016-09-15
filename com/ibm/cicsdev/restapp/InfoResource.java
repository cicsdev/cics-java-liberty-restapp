/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2016 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.restapp;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.cicsdev.restapp.bean.CICSEnvironment;
import com.ibm.cicsdev.restapp.bean.CICSInformation;

@Path("cicsinfo")
@Produces( MediaType.APPLICATION_JSON )
public class InfoResource {

    /**
     * Formatting string used to produce an ISO-8601 standard timestamp.
     */
    private static final String ISO8601_FORMAT = "%tFT%<tT.%<tLZ";
    
    /**
     * RESTful web application that returns information about the CICS environment. 
     */
    @GET
    public CICSInformation getCICSInformation() {
        
        // Establish the CICS environment bean
        CICSEnvironment env = new CICSEnvironment();

        // Initialise with system property values
        env.setProd(System.getProperty("com.ibm.cics.jvmserver.cics.product.name"));
        env.setCicsVer(System.getProperty("com.ibm.cics.jvmserver.cics.product.version"));

        // Establish the CICS information bean
        CICSInformation info = new CICSInformation();

        // Initialise with system property values
        info.setApplid(System.getProperty("com.ibm.cics.jvmserver.applid"));
        info.setJvmServer(System.getProperty("com.ibm.cics.jvmserver.name"));

        // Format the current time to ISO 8601 standards
        Calendar nowUTC = Calendar.getInstance(TimeZone.getTimeZone("Z"));
        info.setTime(String.format(ISO8601_FORMAT, nowUTC));

        // Add the environment information
        info.setEnv(env);

        // Return to the user
        return info;
    }
}
