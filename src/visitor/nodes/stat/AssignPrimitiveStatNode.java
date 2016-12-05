package visitor.nodes.stat;

import antlr.WACCParser;
import codegen.CodeGenerator;
import codegen.Instruction;
import codegen.operands.Register;
import symobjects.SymbolTable;
import visitor.nodes.StatNode;
import visitor.nodes.util.AssignPrimitiveNode;

import java.util.List;

public class AssignPrimitiveStatNode extends StatNode<WACCParser.AssignPrimitiveStatContext> {
    private AssignPrimitiveNode node;

    public AssignPrimitiveStatNode(SymbolTable currentST, WACCParser.AssignPrimitiveStatContext ctx,
                                   AssignPrimitiveNode node) {
        super(currentST, ctx);

        this.node = node;

        if(node.hasErrors()) {
            setError();
            return;
        }
    }

    @Override
    public List<Instruction> generateInstructions(CodeGenerator codeGenRef, List<Register> availableRegisters) {
        return node.generateInstructions(codeGenRef, availableRegisters);
    }
}
