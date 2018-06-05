package syntaticTree;

import parser.*;

public class VarInitNode extends VarNode {
public ExpreNode expression, assignment;

public VarInitNode(ExpreNode t, ExpreNode e) 
{
	super(t.position);
	expression = t;
	assignment = e;
}
}