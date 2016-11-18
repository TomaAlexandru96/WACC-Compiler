package codegen.libfuncs.runtimeerror;

import codegen.BaseInstruction;
import codegen.DataDir;
import codegen.Instruction;
import codegen.LibFunc;

import java.util.ArrayList;
import java.util.List;

public class ThrowRuntimeError extends LibFunc {
    public static String FUNC_NAME = "lib_throw_runtime_error";

    public ThrowRuntimeError(DataDir dataDir) {
        super(dataDir);
    }

    @Override
    public List<Instruction> getInstructions() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ThrowRuntimeError{}";
    }
}
