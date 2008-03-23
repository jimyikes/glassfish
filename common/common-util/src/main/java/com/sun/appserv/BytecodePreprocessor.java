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

/*
 * BytecodePreprocessor.java
 *
 * Created on May 22, 2002, 4:54 PM
 */

package com.sun.appserv;

import java.util.Hashtable;

/** Third party tool vendors may implement this interface to provide code
 * instrumentation to the application server.
 */
public interface BytecodePreprocessor {
    
    /** Initialize the profiler instance.  This method should be called exactly 
     * once before any calls to preprocess.
     * @param parameters Initialization parameters.
     * @return true if initialization succeeded.
     */    
    public boolean initialize(Hashtable parameters);
    
    /** This function profiler-enables the given class.  This method should not 
     * be called until the initialization method has completed.  It is thread-
     * safe.
     * @param classname The name of the class to process.  Used for efficient 
     * filtering.
     * @param classBytes Actual contents of class to process
     * @return The instrumented class bytes.
     */    
    public byte[] preprocess(String classname, byte[] classBytes);
    
}
