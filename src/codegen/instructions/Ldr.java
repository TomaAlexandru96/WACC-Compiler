package codegen.instructions;

import codegen.Instruction;
import codegen.Operand;
import codegen.operands.Register;

public class Ldr implements Instruction {

    private String firstOperand;
    private String secondOperand;

    public Ldr(Register dest, Operand operand) {
        assert(!(operand instanceof Register));
        this.firstOperand = dest.toString();
        this.secondOperand = operand.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LDR ").append(firstOperand).append(", ").append(secondOperand);
        return sb.toString();
    }
}
