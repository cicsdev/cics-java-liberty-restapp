/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2016 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.restapp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "cicsInformation")
public class CICSInformation {

    @XmlElement(name = "applid")
    private String applid;
    
    @XmlElement(name = "jvmServer")
    private String jvmServer;
    
    @XmlElement(name = "time") 
    private String time;
    
    @XmlElement(name = "cicsEnvironment")
    private CICSEnvironment env;
    
    public void setApplid(String applid) {
        this.applid = applid;
    }

    public void setJvmServer(String jvmServer) {
        this.jvmServer = jvmServer;
    }

    public void setEnv(CICSEnvironment env) {
        this.env = env;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApplid() {
        return applid;
    }

    public String getJvmServer() {
        return jvmServer;
    }

    public CICSEnvironment getEnv() {
        return env;
    }

    public String getTime() {
        return time;
    }
}
