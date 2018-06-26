package syntacticTree;

import parser.Token;


public class NotNode extends ExpreNode {
    public ExpreNode expr1;
    public ExpreNode expr2;
    
    public NotNode(Token t, ExpreNode e1, ExpreNode e2) {
        super(t);
        expr1 = e1;
        expr2 = e2;
    }

  
}
