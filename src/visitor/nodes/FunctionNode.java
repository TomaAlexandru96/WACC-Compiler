package visitor.nodes;

import antlr.WACCParser;
import main.CompileTimeError;
import org.antlr.v4.runtime.ParserRuleContext;
import symobjects.SymbolTable;
import symobjects.identifierobj.FunctionObj;
import symobjects.identifierobj.TypeObj;
import symobjects.identifierobj.VariableObj;
import visitor.Node;
import visitor.nodes.stat.CompositionNode;
import visitor.nodes.stat.ExitNode;
import visitor.nodes.stat.ReturnNode;
import visitor.nodes.util.ParamNode;
import visitor.nodes.type.TypeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionNode extends Node {

    private TypeObj returnType;
    private List<ParamNode> paramList;
    private StatNode body;
    private String name;

    public FunctionNode(SymbolTable currentST, WACCParser.FuncContext ctx, TypeNode typeNode,
                        List<ParamNode> paramNodeList, StatNode statNode) {
        super(currentST, ctx);
        this.returnType = typeNode.getType();
        this.paramList = paramNodeList;
        this.body = statNode;
        this.name = ctx.IDENT().getText();
        check();
    }

    private void check() {
        // TODO: check for if as well and statBlock
        // last statement is either exit or return
        StatNode current = body;
        List<ReturnNode> returnStatList = new LinkedList<>();
        while (current instanceof CompositionNode) {
            if (current instanceof ReturnNode) {
                returnStatList.add((ReturnNode) current);
            }
            current = ((CompositionNode) current).getSecondStatNode();
        }

        if (!(current instanceof ExitNode) && !(current instanceof ReturnNode)) {
            addSyntacticError(CompileTimeError.RETURN_STATEMENT_MISSING_FROM_LAST_LINE);
        }

        // current is either return statement or exit
        if (current instanceof ReturnNode) {
            TypeObj returnStatementType = ((ReturnNode) current).getReturnType();
            if (!returnStatementType.equals(returnType)) {
                addSemanticError(CompileTimeError.RETURN_TYPE_MISMATCH);
                return;
            }
        }

        for (ReturnNode retStat: returnStatList) {
            if (!retStat.equals(returnType)) {
                addSemanticError(CompileTimeError.RETURN_TYPE_MISMATCH);
                return;
            }
        }

        for (ParamNode param : paramList) {
            if (!(param instanceof ParamNode)) {
                addSemanticError(CompileTimeError.INVALID_PARAMETER_USE);
                return;
            }
        }
    }
}
