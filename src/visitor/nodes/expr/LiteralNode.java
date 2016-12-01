package visitor.nodes.expr;

import antlr.WACCParser;
import symobjects.SymbolTable;
import visitor.nodes.ExprNode;

public abstract class LiteralNode<T extends WACCParser.LiteralContext>
        extends ExprNode<T> {

    protected String value;

    public LiteralNode(SymbolTable currentST, T ctx) {
        super(currentST, ctx);
    }

    public String getValue() {
        return value;
    }
}
