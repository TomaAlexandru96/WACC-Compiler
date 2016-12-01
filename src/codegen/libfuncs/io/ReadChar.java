package codegen.libfuncs.io;

import codegen.DataDir;

public class ReadChar extends Readable {

    public ReadChar(DataDir dataDir) {
        super(dataDir, ARGUMENT_MESSAGE_READ_CHAR);
    }
}
