/*
 * Autopsy Forensic Browser
 * 
 * Copyright 2014 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sleuthkit.autopsy.ingest;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.python.util.PythonInterpreter;

// RJCTODO: Looks like this could be more generic
/**
 * Creates Java objects associated with instantiated Python classes. 
 * Implemented as a singleton to ensure that a single instance of the Python
 * interpreter is created.
 */
class PythonIngestModuleFactory {
    private static PythonIngestModuleFactory instance = null;
    private PythonInterpreter interpreter;
    
    synchronized static PythonIngestModuleFactory getInstance() {
        if (null == instance) {
            instance = new PythonIngestModuleFactory();
        }
        return instance;
    }
    
    private PythonIngestModuleFactory() {
        try {
            interpreter = new PythonInterpreter();
        } 
        catch (Exception ex) {
            NotifyDescriptor d =
                    new NotifyDescriptor.Message("ERROR creating python interpreter",
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
        }
    }
    
    // RJCTODO: Update
    /**
     * Generate a Java object associated with a python class. 
     * 
     * @param interfaceName Fully qualified Java interface that python class
     * implements
     * @param pathToJythonFile Full path to python file
     * @return Instance of the python class
     */
    PythonIngestModule createIngestModule(String pathToJythonFile) {
        if (interpreter == null) {
            return null;
        }
    
        // interpret the passed in file
        try {
            interpreter.execfile(pathToJythonFile);
        } catch (Exception e) {
            NotifyDescriptor d =
                    new NotifyDescriptor.Message("ERROR Loading Python File (" + pathToJythonFile + "): " + e.toString(),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
            return null;
        }

        // create instance of the python class and assign it
        try {
            // get the python class name based on the name of the python file
            String pythonClassName = pathToJythonFile.substring(pathToJythonFile.lastIndexOf("\\") + 1);
            pythonClassName = pythonClassName.substring(0, pythonClassName.indexOf("."));

            // Name we use to create an instance of the python class with
            String instanceName = pythonClassName + "_obj";
            interpreter.exec(instanceName + " = " + pythonClassName + "()");

            // get the reference to the instance we created
            return (PythonIngestModule)interpreter.get(instanceName).__tojava__(PythonIngestModule.class);
        } catch (Exception e) {
            NotifyDescriptor d =
                    new NotifyDescriptor.Message("ERROR getting instance from python file (" + pathToJythonFile + "): " + e.toString(),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
        }

        return null;
    }    
}
