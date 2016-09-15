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
@XmlType(name = "cicsEnvironment")
public class CICSEnvironment {

    @XmlElement(name = "cicsProduct")
    private String prod;
    
    @XmlElement(name = "cicsVersion")
    private String cicsVer;
    
    public String getProd() {
        return prod;
    }

    public String getCicsVer() {
        return cicsVer;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public void setCicsVer(String cicsVer) {
        this.cicsVer = cicsVer;
    }
}
