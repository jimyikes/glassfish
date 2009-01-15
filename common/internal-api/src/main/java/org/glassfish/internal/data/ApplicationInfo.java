/*
 * The contents of this file are subject to the terms 
 * of the Common Development and Distribution License 
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at 
 * https://glassfish.dev.java.net/public/CDDLv1.0.html or
 * glassfish/bootstrap/legal/CDDLv1.0.txt.
 * See the License for the specific language governing 
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL 
 * Header Notice in each file and include the License file 
 * at glassfish/bootstrap/legal/CDDLv1.0.txt.  
 * If applicable, add the following below the CDDL Header, 
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information: 
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package org.glassfish.internal.data;

import org.glassfish.api.deployment.archive.ReadableArchive;
import org.glassfish.api.deployment.*;
import org.glassfish.api.container.Sniffer;
import org.glassfish.api.container.Container;
import org.glassfish.api.ActionReport;
import org.glassfish.api.admin.ParameterNames;
import org.glassfish.internal.deployment.ExtendedDeploymentContext;
import org.jvnet.hk2.component.PreDestroy;
import org.jvnet.hk2.config.ConfigSupport;
import org.jvnet.hk2.config.TransactionFailure;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.instrument.ClassFileTransformer;
import java.beans.PropertyVetoException;

import com.sun.logging.LogDomains;
import com.sun.enterprise.config.serverbeans.Application;
import com.sun.enterprise.config.serverbeans.Engine;
import com.sun.enterprise.config.serverbeans.Module;

/**
 * Information about a running application. Applications are composed of modules.
 * Modules run in an individual container.
 *
 * @author Jerome Dochez
 */
public class ApplicationInfo {

    final static private Logger logger = LogDomains.getLogger(ApplicationInfo.class, LogDomains.CORE_LOGGER);



    final private ModuleInfo[] modules;
    final private String name;
    final private ReadableArchive source;


    /**
     * Creates a new instance of an ApplicationInfo
     *
     * @param source the archive for this application
     * @param name name of the application
     * @param modules the modules that are forming the application
     */
    public ApplicationInfo(ReadableArchive source,
                           String name, ModuleInfo... modules) {
        this.name = name;
        this.source = source;
        this.modules = modules;
    }
    

    
    /**
     * Returns the registration name for this application
     * @return the application registration name
     */
    public String getName() {
        return name;
    }  
    


    /**
     * Returns the directory where the application bits are located
     * @return the application bits directory
     * */
    public ReadableArchive getSource() {
        return source;
    }


    /**
     * Returns the modules of this application
     * @return the modules of this application
     */
    public ModuleInfo[] getModuleInfos() {
        return modules;
    }

    /**
     * Returns the list of sniffers that participated in loaded this
     * application
     *
     * @return array of sniffer that loaded the application's module
     */
    public Collection<Sniffer> getSniffers() {
        List<Sniffer> sniffers = new ArrayList<Sniffer>();
        for (ModuleInfo module : modules) {
            sniffers.addAll(module.getSniffers());
        }
        return sniffers;
    }

    /*
     * Returns the EngineRef for a particular container type
     * @param type the container type
     * @return the module info is this application as a module implemented with
     * the passed container type
     */
    public <T extends Container> Collection<EngineRef> getEngineRefsForContainer(Class<T> type) {
        Set<EngineRef> refs = new LinkedHashSet<EngineRef>();
        for (ModuleInfo info : modules) {
            EngineRef ref = null;
            try {
                ref = info.getEngineRefForContainer(type);
            } catch (Exception e) {
                // ignore, wrong container
            }
            if (ref!=null) {
                refs.add(ref);
            }
        }
        return refs;
    }

    public void load(ExtendedDeploymentContext context, ActionReport report, ProgressTracker tracker)
            throws Exception {

        context.setPhase(ExtendedDeploymentContext.Phase.LOAD);
        for (ModuleInfo module : modules) {
            module.load(context, report, tracker);
        }
    }


    public void start(
        DeploymentContext context,
        ActionReport report, ProgressTracker tracker) throws Exception {

        // registers all deployed items.
        for (ModuleInfo module : getModuleInfos()) {
            module.start(context, report, tracker);
        }
    }

    public void stop(ApplicationContext context, Logger logger) {
        
        for (ModuleInfo module : getModuleInfos()) {
            module.stop(context, logger);
        }
        
    }

    public void unload(ExtendedDeploymentContext context, ActionReport report) {

        stop(context, logger);

        for (ModuleInfo module : getModuleInfos()) {
            module.unload(context, report);
        }

    }

    public boolean suspend(Logger logger) {

        boolean isSuccess = true;

        for (ModuleInfo module : modules) {
            if (!module.suspend(logger)) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public boolean resume(Logger logger) {

        boolean isSuccess = true;

        for (ModuleInfo module : modules) {
            if (!module.resume(logger)) {
                isSuccess=false;
            }
        }

        return isSuccess;
    }

    public void clean(ExtendedDeploymentContext context) throws Exception {
        for (ModuleInfo info : modules) {
            info.clean(context);
        }
    }
    

    /**
     * Saves its state to the configuration. this method must be called within a transaction
     * to the configured Application instance.
     *
     * @param app the application being persisted
     */
    public void save(Application app) throws TransactionFailure, PropertyVetoException {

        for (ModuleInfo module : modules) {
            Module modConfig = app.createChild(Module.class);
            app.getModule().add(modConfig);
            module.save(modConfig);

        }        
    }

}
