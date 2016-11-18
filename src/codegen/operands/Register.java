package codegen.operands;

import codegen.Operand;
import java.util.LinkedHashSet;

public enum Register implements Operand {
    R0("r0"), R1("r1"), R2("r2"), R3("r3"), R4("r4"), R5("r5"), R6("r6"), R7("r7"), R8("r8"),
    R9("r9"), R10("r10"), R11("r11"), R12("r12"), R13("r13"), R14("r14"), R15("r15"), PC("pc"), LR("lr");

    private String representation;

    Register(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    public static LinkedHashSet<Register> allRegisters() {
        LinkedHashSet<Register> allRegisters = new LinkedHashSet<>();
        for(Register register : Register.values()) {
            allRegisters.add(register);
        }
        return allRegisters;
    }

}
