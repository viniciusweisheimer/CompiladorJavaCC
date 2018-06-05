package syntaticTree;

import parser.*;


public class BooleanExpressionNode extends ExpreNode {

    public ExpreNode expr1, expr2, expr3;

    public BooleanExpressionNode(Token t, ExpreNode e1, ExpreNode e2, ExpreNode b) {
        super(t == null ? e1.position : t);
        expr1 = e1;
        expr2 = e2;
		expr3 = b;
    }

}