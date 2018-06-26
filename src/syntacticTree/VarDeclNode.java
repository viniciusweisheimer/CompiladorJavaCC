package syntacticTree;

import parser.*;


public class VarDeclNode extends StatementNode {
    public ListNode vars;
    public ListNode expr;
    
  
    
    public VarDeclNode(Token t, ListNode p) {
        super(t);
        vars = p;
      
    }
    
    public VarDeclNode(Token t, ListNode p , ListNode exp) {
        super(t);
        vars = p;
        expr = exp;
    }
}
