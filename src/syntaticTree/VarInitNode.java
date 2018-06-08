package syntaticTree;

import parser.*;

public class VarInitNode extends VarNode {
public ExpreNode assignment;

public VarInitNode(Token t, ExpreNode e) 
{
	super(t);
	assignment = e;
}
}