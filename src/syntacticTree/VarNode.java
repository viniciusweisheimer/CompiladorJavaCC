package syntacticTree;

import parser.*;


public class VarNode extends ExpreNode {
    public int dim;
    public ExpreNode expr1;
    //public ExpreNode expr2;
    
    public VarNode(Token t) {
        super(t);
        dim = 0;
    }

    public VarNode(Token t, int k) {
        super(t);
        dim = k;
    }
    
       
    public VarNode(Token t,ExpreNode e1, int k) { //
        super(t);
        dim = k;
        expr1 = e1;
       
    }
}
