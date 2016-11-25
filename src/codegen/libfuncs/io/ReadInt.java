package codegen.libfuncs.io;

import codegen.DataDir;

public class ReadInt extends Readable {

    public ReadInt(DataDir dataDir) {
        super(dataDir, ARGUMENT_MESSAGE_READ_INT);
    }
}
