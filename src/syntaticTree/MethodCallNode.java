package syntaticTree;

import parser.Token;

public class MethodCallNode extends StatementNode {
public Token caller;
public ListNode args;

public MethodCallNode(Token t, Token c, ListNode l)
{
	super(t);
	caller = c;
	args = l;
}
}