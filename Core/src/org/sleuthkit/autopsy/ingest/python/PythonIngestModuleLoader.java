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

package org.sleuthkit.autopsy.ingest.python;

import java.util.ArrayList;
import java.util.List;
import org.sleuthkit.autopsy.ingest.IngestModuleAbstractFile;
import org.sleuthkit.autopsy.ingest.IngestModuleDataSource;

/**
 * RJCTODO
 */
public class PythonIngestModuleLoader {
    private List<IngestModuleAbstractFile> fileIngestModules = new ArrayList<>();
    private List<IngestModuleDataSource> dataSourceIngestModules = new ArrayList<>();
    
    public PythonIngestModuleLoader() {
        // RJCTODO: Replace this proof of concept code
        PythonIngestModuleFactory factory = PythonIngestModuleFactory.getInstance();
        PythonIngestModule module = factory.createIngestModule("C:\\PythonIngestModules\\MakisuFileIngestModule.py");
        if (module instanceof PythonFileIngestModule) {
            FileIngestModuleAdapter adapter = new FileIngestModuleAdapter((PythonFileIngestModule)module); 
            fileIngestModules.add(adapter);
        }
    }
            
    public List<IngestModuleAbstractFile> getFileIngestModules() {
        List<IngestModuleAbstractFile> modules = new ArrayList<>();
        modules.addAll(fileIngestModules);
        return modules;
    }

    /**
     * Get loaded data source modules
     *
     * @return data source modules loaded
     */
    public List<IngestModuleDataSource> getDataSourceIngestModules() {
        List<IngestModuleDataSource> modules = new ArrayList<>();
        modules.addAll(dataSourceIngestModules);
        return modules;
    }    
}
