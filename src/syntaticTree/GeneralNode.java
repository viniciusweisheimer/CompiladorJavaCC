package syntaticTree;

import parser.Token;

public abstract class GeneralNode {
public Token position;
public int number;
public GeneralNode(Token x) 
{
	position = x;
	number = 0;
}
}
