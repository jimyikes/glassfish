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

package com.sun.enterprise.deployment;

import java.util.logging.*;
import com.sun.logging.*;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;

import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.enterprise.deployment.util.LogDomains;


/**
 * Contains a single interceptor binding entry.
 */ 
public class InterceptorBindingDescriptor extends Descriptor
{
    private static LocalStringManagerImpl localStrings =
	    new LocalStringManagerImpl(InterceptorBindingDescriptor.class);

    static Logger _logger = LogDomains.getLogger(LogDomains.DPL_LOGGER);

    public enum BindingType {
        
        DEFAULT,
        CLASS,
        METHOD

    }

    // Only applies to CLASS and METHOD
    private String ejbName;

    // Only applies to METHOD
    private MethodDescriptor businessMethod;

    // Ordered list of interceptor classes.
    private LinkedList<String> interceptors = new LinkedList<String>();

    // True if interceptor list represents a total ordering.  
    private boolean isTotalOrdering;
    
    // Only applies to CLASS or METHOD
    private boolean excludeDefaultInterceptors;

    // Only applies to METHOD
    private boolean excludeClassInterceptors;

    private boolean needsOverloadResolution;

    public InterceptorBindingDescriptor() {
    }

    public BindingType getBindingType() {
        if( ejbName.equals("*") ) {
            return BindingType.DEFAULT;
        } else if( businessMethod == null ) {
            return BindingType.CLASS;
        } else {
            return BindingType.METHOD;
        }
    }

    public void setNeedsOverloadResolution(boolean flag) {
        needsOverloadResolution = flag;
    }

    public boolean getNeedsOverloadResolution() {
        return needsOverloadResolution;
    }

    public void setEjbName(String ejb) {
        ejbName = ejb;
    }

    public String getEjbName() {
        return ejbName;
    }

    public void setBusinessMethod(MethodDescriptor desc) {
        businessMethod = desc;
    }

    public MethodDescriptor getBusinessMethod() {
        return businessMethod;
    }

    public void appendInterceptorClass(String interceptor) {
        interceptors.addLast(interceptor);
    }
    
    public List<String> getInterceptorClasses() {
        return new LinkedList<String>(interceptors);
    }

    public void setIsTotalOrdering(boolean flag) {
        isTotalOrdering = flag;
    }

    public boolean getIsTotalOrdering() {
        return isTotalOrdering;
    }

    public void setExcludeDefaultInterceptors(boolean flag) {
        excludeDefaultInterceptors = flag;
    }

    public boolean getExcludeDefaultInterceptors() {
        return excludeDefaultInterceptors;
    }

    public void setExcludeClassInterceptors(boolean flag) {
        excludeClassInterceptors = flag;
    }

    public boolean getExcludeClassInterceptors() {
        return excludeClassInterceptors;
    }
    
}
