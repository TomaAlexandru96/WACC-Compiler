package visitor.nodes.stat;

import antlr.WACCParser;
import codegen.CodeGenerator;
import codegen.Instruction;
import codegen.operands.Register;
import symobjects.SymbolTable;
import visitor.nodes.StatNode;
import visitor.nodes.util.AssignPairArrayNode;

import java.util.List;

public class AssignPairArrayStatNode extends StatNode<WACCParser.AssignPairArrayStatContext> {
    private AssignPairArrayNode node;
    public AssignPairArrayStatNode(SymbolTable currentST, WACCParser.AssignPairArrayStatContext ctx,
                                   AssignPairArrayNode node) {
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
