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

import org.sleuthkit.autopsy.ingest.IngestModuleAbstractFile;
import org.sleuthkit.autopsy.ingest.IngestModuleInit;
import org.sleuthkit.autopsy.ingest.PipelineContext;
import org.sleuthkit.datamodel.AbstractFile;

/**
 * RJCTODO
 */
class FileIngestModuleAdapter extends IngestModuleAbstractFile {

    private PythonFileIngestModule pythonModule;

    FileIngestModuleAdapter(PythonFileIngestModule pythonModule) {
        this.pythonModule = pythonModule;
    }

    @Override
    public String getName() {
        return pythonModule.getName();
    }

    @Override
    public String getVersion() {
        return pythonModule.getVersion();
    }

    @Override
    public String getDescription() {
        return pythonModule.getDescription();
    }

    @Override
    public void init(IngestModuleInit imi) {
        pythonModule.init();
    }

    @Override
    public IngestModuleAbstractFile.ProcessResult process(PipelineContext<IngestModuleAbstractFile> pc, AbstractFile af) {
        // RJCTODO: Add result code to Python module interface
        pythonModule.process(af);
        return IngestModuleAbstractFile.ProcessResult.OK;
    }
    
    @Override
    public boolean hasBackgroundJobsRunning() {
        return false;
    }
        
    @Override
    public void complete() {
        pythonModule.complete();
    }

    @Override
    public void stop() {
        pythonModule.stop();
    }
}
