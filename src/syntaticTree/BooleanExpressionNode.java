package syntaticTree;

import parser.Token;

public class BooleanExpressionNode extends ExpreNode {

	public ExpreNode expr1, expr2, expr3;
	public Token negation, comparison1, comparison2;
	public boolean equality;

	public BooleanExpressionNode( Token t,  ExpreNode e1,  Token t1,
	boolean eq,  ExpreNode e2,  Token t2,  ExpreNode b) {
		super(e1.position);
		expr1 = e1;
		expr2 = e2;
		expr3 = b;

		negation = t;
		comparison1 = t1;
		comparison2 = t2;
		equality = eq;
	}

}