package syntacticTree;

import parser.Token;

public class MethodCallNode extends StatementNode {
	public Token caller;
	public ListNode args;

	public MethodCallNode(final Token t, final Token c, final ListNode l) {
		super(t);
		caller = c;
		args = l;
	}
}