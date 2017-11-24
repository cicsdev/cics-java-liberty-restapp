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

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.cics.server.Channel;
import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.Container;
import com.ibm.cics.server.Program;
import com.ibm.cics.server.Task;
import com.ibm.cicsdev.restapp.bean.ReverseResult;


/**
 * RESTful web application that links to the CICS COBOL program EDUCHAN
 * and returns a reversed input string. 
 */
@Path("reverse")
@Produces(MediaType.APPLICATION_JSON)
public class ReverseResource {

    /**
     * Formatting string used to produce an ISO-8601 standard timestamp.
     */
    private static final String ISO8601_FORMAT = "%tFT%<tT.%<tLZ";

    /**
     * Default string to reverse.
     */
    private static final String DEFAULT_STRING = "Hello from Java";

    /**
     * Name of the CICS program the {@link #reverse(String)} method will LINK to.
     */
    private static final String PROGRAM_NAME = "EDUCHAN";

    /**
     * Name of the channel to create. Must be 16 characters or less.
     */
    private static final String CHANNEL_NAME = "MYCHANNEL";

    /**
     * Name of the container used to pass the data to the CICS program.
     */
    private static final String INPUT_CONTAINER = "INPUTDATA";

    /**
     * Name of the container used to pass the data from the CICS program.
     */
    private static final String OUTPUT_CONTAINER = "OUTPUTDATA";


    /**
     * GET method with no additional input 
     * 
     * @return - JAXB bean ReverseResult with input, output and time
     */
    @GET
    public ReverseResult reverseNoArgs() {
        return reverse(DEFAULT_STRING);
    }

    
    /**
     * GET method to process input string from URI path 
     * Links to CICS program to reverse input string
     *  
     * @param inputStr - String input 
     * @return - JAXB bean ReverseResult with input, output and time
     */
    @GET
    @Path("/{text}")
    public ReverseResult reverse(@PathParam("text") String inputStr) {
        
        // Variable declarations
        Channel chan;
        Container inputContainer; 
        Container outputContainer;               
        
        // Truncate the input string
        inputStr = inputStr.trim();

        // Create a reference to the Program we will invoke
        Program prog = new Program();
        prog.setName(PROGRAM_NAME);

        // Get hold of the current CICS Task
        Task task = Task.getTask();

        try {
            // Create a channel to store the data
            chan = task.createChannel(CHANNEL_NAME);
        }
        catch (CicsConditionException cce) {

            // Report the error
            String msg = MessageFormat.format("Error creating channel \"{0}\" - failure message \"{1}\"",
                    CHANNEL_NAME, cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        try {
            // Create a CHAR container populated with a simple String
            // CHAR containers will be created in UTF-16 when created in JCICS
            inputContainer = chan.createContainer(INPUT_CONTAINER);
        }
        catch (CicsConditionException cce) {
            
            // Report the error
            String msg = MessageFormat.format("Error creating container \"{0}\" - failure message \"{1}\"",
                    INPUT_CONTAINER, cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        try {
            // Trim the input string and add to the response object
            inputContainer.putString(inputStr);            
        }
        catch (CicsConditionException cce) {

            // Report the error
            String msg = MessageFormat.format("Error setting value of container - failure message \"{0}\"",
                    cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        try {
            // Link to the CICS program
            prog.link(chan);
        }
        catch (CicsConditionException cce) {

            // Report the error
            String msg = MessageFormat.format("Error linking to program \"{0}\" - failure message \"{1}\"",
                    prog.getName(), cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        try {
            // Read CHAR container from channel container data as formatted string
            // CICS returns this in a UTF-16 format and JCICS reads this into a String
            outputContainer = chan.getContainer(OUTPUT_CONTAINER);
        }
        catch (CicsConditionException cce) {

            // Report the error
            String msg = MessageFormat.format("Could not obtain output container \"{0}\" - failure message \"{1}\"",
                    OUTPUT_CONTAINER, cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        String outputStr;

        try {          
            // Container object will be null if container not present
            if (outputContainer != null) {

                // Get the container as a string
                outputStr = outputContainer.getString();
            }
            else {
                String msg = MessageFormat.format("Output container \"{0}\" not found in response",
                        OUTPUT_CONTAINER);
                Response r = Response.serverError().entity(msg).build();
                throw new WebApplicationException(r);
            }
        }
        catch (CicsConditionException cce) {

            // Report the error
            String msg = MessageFormat.format("Could not obtain output container \"{0}\" - failure message \"{1}\"",
                    OUTPUT_CONTAINER, cce.getMessage());
            Response r = Response.serverError().entity(msg).build();

            // Pass the error back up the handler chain
            throw new WebApplicationException(cce, r);
        }

        // Create the result bean
        ReverseResult result = new ReverseResult();

        // Populate with the original string
        result.setOriginalText(inputStr);

        // Format the current time to ISO 8601 standards
        Calendar nowUTC = Calendar.getInstance(TimeZone.getTimeZone("Z"));
        result.setTime(String.format(ISO8601_FORMAT, nowUTC));

        // Trim the output and store in the result object
        result.setReverseText(outputStr.trim());

        // Was this truncated?
        result.setTruncated(outputStr.length() < inputStr.length());

        // Return result object
        return result;
    }
}
