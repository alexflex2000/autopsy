import jarray
from org.sleuthkit.autopsy.ingest.python import PythonFileIngestModule
from org.sleuthkit.autopsy.ingest.python import PythonIngestModule
from org.sleuthkit.datamodel import AbstractFile
from org.sleuthkit.datamodel import ReadContentInputStream
from org.sleuthkit.datamodel import BlackboardArtifact
from org.sleuthkit.datamodel import BlackboardAttribute

class MakisuFileIngestModule(PythonFileIngestModule):

    def getName(self):
        return "Python File Ingest Module"

    def getDescription(self):
        return "World Peace"

    def getVersion(self):
        return "1.0"

    def init(self):
        pass

    def process(self, file):
        if file.getName().endswith("txt"):
            # Make an artifact
            art = file.newArtifact(BlackboardArtifact.ARTIFACT_TYPE.TSK_INTERESTING_FILE_HIT)
            att = BlackboardAttribute(BlackboardAttribute.ATTRIBUTE_TYPE.TSK_SET_NAME.getTypeID(), "pythonModule", "Text FILE")
            art.addAttribute(att)

        # Read the contents of the file
        inputStream = ReadContentInputStream(file)
        buffer = jarray.zeros(1024, "b")
        totLen = 0
        len = inputStream.read(buffer)
        while (len != -1):
            totLen = totLen + len
            len = inputStream.read(buffer)

        return totLen

    def complete(self):
        pass
    
    def stop(self):
        pass