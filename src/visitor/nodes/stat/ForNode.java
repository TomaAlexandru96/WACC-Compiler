package visitor.nodes.stat;

import antlr.WACCParser;
import codegen.CodeGenerator;
import codegen.Instruction;
import codegen.instructions.BaseInstruction;
import codegen.instructions.Ins;
import codegen.instructions.LabelIns;
import codegen.operands.LabelOp;
import codegen.operands.Offset;
import codegen.operands.Register;
import main.CompileTimeError;
import symobjects.SymbolTable;
import symobjects.identifierobj.typeobj.scalarobj.BoolObj;
import visitor.nodes.ExprNode;
import visitor.nodes.StatNode;
import visitor.nodes.util.AssignPairArrayNode;
import visitor.nodes.util.AssignPrimitiveNode;
import visitor.nodes.util.assignlhs.AssignLhsArrayElemNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ForNode extends StatNode<WACCParser.ForStatContext> {

    private AssignPrimitiveNode assignment;
    private ExprNode condition;
    private AssignPairArrayNode stepAssignment;
    private StatNode body;

    public ForNode(SymbolTable currentST, WACCParser.ForStatContext ctx,
                   AssignPrimitiveNode assignment, ExprNode condition, AssignPairArrayNode stepAssignment,
                    StatNode body) {
        super(currentST, ctx);

        this.assignment = assignment;
        this.condition = condition;
        this.stepAssignment = stepAssignment;
        this.body = body;

        if(assignment.hasErrors() || condition.hasErrors() || stepAssignment.hasErrors() || body.hasErrors()) {
            setError();
            return;
        }

        check();
    }

    private void check() {
        if(!(condition.getType() instanceof BoolObj)) {
            addSemanticError(CompileTimeError.INCOMPATIBLE_TYPE, "for condition", "BOOL", condition.getCtx().getText(),
                    condition.getType().toString());
            return;
        }
    }

    @Override
    public List<Instruction> generateInstructions(CodeGenerator codeGenRef, List<Register> availableRegisters) {
        List<Instruction> instructions = new ArrayList<>();

        String label1 = codeGenRef.getNextLabel();
        String label2 = codeGenRef.getNextLabel();

        List<Instruction> loopBlock = new ArrayList<>();

        loopBlock.addAll(assignment.generateInstructions(codeGenRef, availableRegisters));
        List<Register> bodyAvailableRegs = availableRegisters.stream()
                .skip(1).collect(Collectors.toList());

        loopBlock.add(new BaseInstruction(Ins.B, new LabelOp(label1)));
        loopBlock.add(new LabelIns(label2));

        loopBlock.addAll(body.generateInstructions(codeGenRef, bodyAvailableRegs));

        loopBlock.addAll(stepAssignment.generateInstructions(codeGenRef, availableRegisters));
        loopBlock.add(new LabelIns(label1));
        loopBlock.addAll(condition.generateInstructions(codeGenRef, bodyAvailableRegs));
        loopBlock.add(new BaseInstruction(Ins.CMP, bodyAvailableRegs.get(0), new Offset(1)));
        loopBlock.add(new BaseInstruction(Ins.BEQ, new LabelOp(label2)));

        instructions.addAll(CodeGenerator.makeSpaceOnStackAndRestore(currentST, loopBlock));
        return instructions;
    }
}
