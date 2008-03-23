/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.enterprise.deployment.util.webservice;

/**
 * This class is used by the deployment tool to set the required information for jaxrpc-config.xml
 * that is passed as an argument to the wscompile tool. This class is to be used when the developer is
 * using the deploytool to generate SEI from WSDL
 */

public class WSDLConfig {

    private String webServiceName;
    private String wsdlLocation;
    private String packageName;

    /**
     * Constructor takes all required arguments and sets them appropriately
     * @param wsName  Name of the webservice
     * @param wsdl    the WSDL file location
     * @param pkg     the package name where the SEI and its implementations are present
     */
    
    public WSDLConfig(String wsName, String wsdl, String pkg) {
        this.webServiceName = wsName;
        this.wsdlLocation = wsdl;
        this.packageName = pkg;
    }
    
    public String getWebServiceName() { return this.webServiceName; }
    
    public String getWsdlLocation() { return this.wsdlLocation; }
    
    public String getPackageName() { return this.packageName; }
}
