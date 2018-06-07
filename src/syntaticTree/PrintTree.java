package syntaticTree;

public class PrintTree {
	int kk;

	public PrintTree() {
		kk = 1; // inicializa contador de ns
	}

	public void printRoot( ListNode x) {
		if(x == null) {
			System.out.println("Empty syntatic tree. Nothing to be printed");
		} else {
			numberClassDeclListNode(x);
			printClassDeclListNode(x);
		}

		System.out.println();
	}

	// ------------- lista de classes --------------------------
	public void numberClassDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberClassDeclNode((ClassDeclNode) x.node);
		numberClassDeclListNode(x.next);
	}

	public void printClassDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (ClassDeclNode)  ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		printClassDeclNode((ClassDeclNode) x.node);
		printClassDeclListNode(x.next);
	}

	// ------------- declaraco de classe -------------------------
	public void numberClassDeclNode( ClassDeclNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberClassBodyNode(x.body);
	}

	public void printClassDeclNode( ClassDeclNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ClassDeclNode ===> " + x.name.image +
			" " + (x.supername == null ? "null" : x.supername.image) + " " +
			(x.body == null ? "null" : String.valueOf(x.body.number)));

		printClassBodyNode(x.body);
	}

	// ------------------------- Corpo da classe -------------------
	public void numberClassBodyNode( ClassBodyNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberClassDeclListNode(x.clist);
		numberVarDeclListNode(x.vlist);
		numberConstructDeclListNode(x.ctlist);
		numberMethodDeclListNode(x.mlist);
	}

	public void printClassBodyNode( ClassBodyNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ClassBodyNode ===> " +
			(x.clist == null ? "null" : String.valueOf(x.clist.number)) +
			" " + (x.vlist == null ? "null" : String.valueOf(x.vlist.number)) +
			" " +
			(x.ctlist == null ? "null" : String.valueOf(x.ctlist.number)) +
			" " + (x.mlist == null ? "null" : String.valueOf(x.mlist.number)));

		printClassDeclListNode(x.clist);
		printVarDeclListNode(x.vlist);
		printConstructDeclListNode(x.ctlist);
		printMethodDeclListNode(x.mlist);
	}

	// ---------------- Lista de declaraces de variveis ----------------
	public void numberVarDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberVarDeclNode((VarDeclNode) x.node);
		numberVarDeclListNode(x.next);
	}

	public void printVarDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (VarDeclNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		printVarDeclNode((VarDeclNode) x.node);
		printVarDeclListNode(x.next);
	}

	// -------------------- Declaraco de varivel --------------------
	public void numberVarDeclNode( VarDeclNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberVarListNode(x.vars);
	}

	public void printVarDeclNode( VarDeclNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": VarDeclNode ===> " + x.position.image +
			" " + x.vars.number);

		printVarListNode(x.vars);
	}

	// ------------------- Lista de variveis --------------------
	public void numberVarListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		if(x.node instanceof VarInitNode) {
			numberVarInitNode((VarInitNode) x.node);
		} else {
			numberVarNode((VarNode) x.node);
		}
		numberVarListNode(x.next);
	}

	public void printVarListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (VarNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		if(x.node instanceof VarInitNode) {
			printVarInitNode((VarInitNode) x.node);
		} else {
			printVarNode((VarNode) x.node);
		}
		printVarListNode(x.next);
	}

	public void printVarInitListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (VarInitNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		printVarInitNode((VarInitNode) x.node);
		printVarInitListNode(x.next);
	}

	// -------------- Lista de construtores ---------------------
	public void numberConstructDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberConstructDeclNode((ConstructDeclNode) x.node);
		numberConstructDeclListNode(x.next);
	}

	public void printConstructDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (ConstructDeclNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		printConstructDeclNode((ConstructDeclNode) x.node);
		printConstructDeclListNode(x.next);
	}

	// ------------------ Declaraco de construtor -----------------
	public void numberConstructDeclNode( ConstructDeclNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberMethodBodyNode(x.body);
	}

	public void printConstructDeclNode( ConstructDeclNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ConstructDeclNode ===> " +
			x.body.number);
		printMethodBodyNode(x.body);
	}

	// -------------------------- Lista de mtodos -----------------
	public void numberMethodDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberMethodDeclNode((MethodDeclNode) x.node);
		numberMethodDeclListNode(x.next);
	}

	public void printMethodDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (MethodDeclNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printMethodDeclNode((MethodDeclNode) x.node);
		printMethodDeclListNode(x.next);
	}

	// --------------------- Declaraco de mtodo ---------------
	public void numberMethodDeclNode( MethodDeclNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberMethodBodyNode(x.body);
	}

	public void printMethodDeclNode( MethodDeclNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": MethodDeclNode ===> " +
			x.position.image + " " + (x.dim == 0 ? "" : "[" + x.dim + "] ") +
			x.name.image + " " + x.body.number);
		printMethodBodyNode(x.body);
	}

	// -------------------------- Corpo de mtodo ----------------------
	public void numberMethodBodyNode( MethodBodyNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberVarDeclListNode(x.param);
		numberStatementNode(x.stat);
	}

	public void printMethodBodyNode( MethodBodyNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": MethodBodyNode ===> " +
			(x.param == null ? "null" : String.valueOf(x.param.number)) +
			" " + x.stat.number);
		printVarDeclListNode(x.param);
		printStatementNode(x.stat);
	}

	// --------------------------- Comando composto ----------------------
	public void numberBlockNode( BlockNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberStatementListNode(x.stats);
	}

	public void printBlockNode( BlockNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": BlockNode ===> " + x.stats.number);
		printStatementListNode(x.stats);
	}

	// --------------------- Lista de comandos --------------------
	public void numberStatementListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberStatementNode((StatementNode) x.node);
		numberStatementListNode(x.next);
	}

	public void printStatementListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (StatementNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));

		printStatementNode((StatementNode) x.node);
		printStatementListNode(x.next);
	}

	// --------------------------- Comando print ---------------------
	public void numberPrintNode( PrintNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
	}

	public void printPrintNode( PrintNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": PrintNode ===> " + x.expr.number);
		printExpreNode(x.expr);
	} 

	// ---------------------- Comando read --------------------------
	public void numberReadNode( ReadNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
	}

	public void printReadNode( ReadNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ReadNode ===> " + x.expr.number);
		printExpreNode(x.expr);
	}

	// --------------------- Comando return -------------------------
	public void numberReturnNode( ReturnNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
	}

	public void printReturnNode( ReturnNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ReturnNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr);
	}

	// ------------------------ Comando super --------------------------
	public void numberSuperNode( SuperNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreListNode(x.args);
	}

	public void printSuperNode( SuperNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": SuperNode ===> " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printExpreListNode(x.args);
	}

	// ------------------------- Comando de atribuio -------------------
	public void numberAtribNode( AtribNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void printAtribNode( AtribNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": AtribNode ===> " + x.expr1.number + " " +
			x.expr2.number);
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}

	// ---------------------------------- comando if --------------------
	public void numberIfNode( IfNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
		numberStatementNode(x.stat1);
		numberStatementNode(x.stat2);
	}

	public void printIfNode( IfNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": IfNode ===> " + x.expr.number + " " +
			x.stat1.number + " " +
			(x.stat2 == null ? "null" : String.valueOf(x.stat2.number)));

		printExpreNode(x.expr);
		printStatementNode(x.stat1);
		printStatementNode(x.stat2);
	}

	// ------------------------- comando for -----------------------
	public void numberForNode( ForNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberAtribNode(x.init);
		numberExpreNode(x.expr);
		numberAtribNode(x.incr);
		numberStatementNode(x.stat);
	}

	public void printForNode( ForNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ForNode ===> " +
			(x.init == null ? "null" : String.valueOf(x.init.number)) + " " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)) + " " +
			(x.incr == null ? "null" : String.valueOf(x.incr.number)) + " " +
			x.stat.number);

		printAtribNode(x.init);
		printExpreNode(x.expr);
		printAtribNode(x.incr);
		printStatementNode(x.stat);
	}

	// --------------------------- Comando break --------------------
	public void numberBreakNode( BreakNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
	}

	public void printBreakNode( BreakNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": BreakNode");
	}

	// --------------------------- Comando vazio -------------------
	public void numberNopNode( NopNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
	}

	public void printNopNode( NopNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": NopNode");
	}

	// -------------------------- Alocao de objeto ------------------------
	public void numberNewObjectNode( NewObjectNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreListNode(x.args);
	}

	public void printNewObjectNode( NewObjectNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": NewObjectNode ===> " + x.name.image +
			" " + (x.args == null ? "null" : String.valueOf(x.args.number)));

		printExpreListNode(x.args);
	}

	// -------------------------- Alocao de array ------------------------
	public void numberNewArrayNode( NewArrayNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreListNode(x.dims);
	}

	public void printNewArrayNode( NewArrayNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": NewArrayNode ===> " + x.name.image +
			" " + (x.dims == null ? "null" : String.valueOf(x.dims.number)));

		printExpreListNode(x.dims);
	}

	// --------------------------- Lista de expresses ---------------
	public void numberExpreListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode((ExpreNode) x.node);
		numberExpreListNode(x.next);
	}

	public void printExpreListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (ExpreNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printExpreNode((ExpreNode) x.node);
		printExpreListNode(x.next);
	}

	// --------------------------- Lista de expressoes booleanadas ---------------
	public void numberBooleanExpressionListNode( ListNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode((BooleanExpressionNode) x.node);
		numberExpreListNode(x.next);
	}

	public void printBooleanExpressionListNode( ListNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (BooleanExpressionNode) ===> " +
			x.node.number + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printExpreNode((BooleanExpressionNode) x.node);
		printExpreListNode(x.next);
	}

	// --------------------- Expresso booleana -------------------
	public void numberBooleanExpressionNode( BooleanExpressionNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
		numberExpreNode(x.expr3);
	}

	public void printBooleanExpressionNode( BooleanExpressionNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": BooleanExpressionNode ===> " +
			(x.negation != null ? " " + x.negation.image : "") +
			" " + x.expr1.number +
			(x.comparison1 != null ? " " + x.comparison1.image : "") +
			(x.expr2 != null ? " " + x.expr2.number : "") +
			(x.comparison2 != null ? " " + x.comparison2.image : "") +
			(x.expr3 != null ? " " + x.expr3.number : ""));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
		printExpreNode(x.expr3);
	}

	// --------------------- Expresso relacional -------------------
	public void numberRelationalNode( RelationalNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void printRelationalNode( RelationalNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": RelationalNode ===> " + x.expr1.number +
			" " + x.position.image + " " + x.expr2.number);
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}

	// ------------------------ Soma ou subtrao -------------------
	public void numberAddNode( AddNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void printAddNode( AddNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": AddNode ===> " + x.expr1.number + " " +
			x.position.image + " " + x.expr2.number);
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}

	// ---------------------- Multiplicao ou diviso --------------------
	public void numberMultNode( MultNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void printMultNode( MultNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": MultNode ===> " + x.expr1.number + " " +
			x.position.image + " " + x.expr2.number);
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}

	// ------------------------- Expresso unria ------------------------
	public void numberUnaryNode( UnaryNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
	}

	public void printUnaryNode( UnaryNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": UnaryNode ===> " + x.position.image +
			" " + x.expr.number);
		printExpreNode(x.expr);
	}

	// -------------------------- Constante ----------------------
	public void numberConstNode( ConstNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
	}

	public void printConstNode( ConstNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(String.format("%d: %s ===> %s", x.number, x.getClass().getSimpleName(), x.position.image));
	}

	// -------------------------------- Nome de varivel ------------------
	public void numberVarNode( VarNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
	}

	public void printVarNode( VarNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": VarNode ===> " + x.position.image + " " +
			(x.dim == 0 ? "" : "[" + x.dim + "]"));
	}

	public void numberVarInitNode( VarInitNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;

		numberExpreNode(x.assignment);
	}

	public void printVarInitNode( VarInitNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": VarInitNode ===> " + x.position.image + " " +
			(x.dim == 0 ? "" : "[" + x.dim + "]") +
			(x.assignment != null ? " " + x.assignment.number : ""));

		printExpreNode(x.assignment);
	}

	// ---------------------------- Chamada de mtodo ------------------------
	public void numberCallNode( CallNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
		numberExpreListNode(x.args);
	}

	public void printCallNode( CallNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": CallNode ===> " + x.expr.number + " " +
			x.meth.image + " " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printExpreNode(x.expr);
		printExpreListNode(x.args);
	}

	public void numberMethodCallNode( MethodCallNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreListNode(x.args);
	}

	public void printMethodCallNode( MethodCallNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": CallNode ===> " + x.position.image + " " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printExpreListNode(x.args);
	}

	// --------------------------- Indexao de varivel ---------------
	public void numberIndexNode( IndexNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void printIndexNode( IndexNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": IndexNode ===> " + x.expr1.number + " " +
			x.expr2.number);
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}

	// -------------------------- Acesso a campo de varivel ---------------
	public void numberDotNode( DotNode x) {
		if(x == null) {
			return;
		}

		x.number = kk++;
		numberExpreNode(x.expr);
	}

	public void printDotNode( DotNode x) {
		if(x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": DotNode ===> " + x.expr.number + " " +
			x.field.image);
		printExpreNode(x.expr);
	}

	// --------------------------- Expresso em geral --------------------------
	public void printExpreNode( ExpreNode x) {
		if(x instanceof NewObjectNode) {
			printNewObjectNode((NewObjectNode) x);
		} else if(x instanceof NewArrayNode) {
			printNewArrayNode((NewArrayNode) x);
		} else if(x instanceof RelationalNode) {
			printRelationalNode((RelationalNode) x);
		} else if(x instanceof AddNode) {
			printAddNode((AddNode) x);
		} else if(x instanceof MultNode) {
			printMultNode((MultNode) x);
		} else if(x instanceof UnaryNode) {
			printUnaryNode((UnaryNode) x);
		} else if(x instanceof CallNode) {
			printCallNode((CallNode) x);
		} else if(x instanceof ConstNode) {
			printConstNode((ConstNode) x);
		} else if(x instanceof IndexNode) {
			printIndexNode((IndexNode) x);
		} else if(x instanceof DotNode) {
			printDotNode((DotNode) x);
		} else if(x instanceof VarInitNode) {
			printVarInitNode((VarInitNode) x);
		} else if(x instanceof VarNode) {
			printVarNode((VarNode) x);
		} else if(x instanceof BooleanExpressionNode) {
			printBooleanExpressionNode((BooleanExpressionNode) x);
		}
	}

	public void numberExpreNode( ExpreNode x) {
		if(x instanceof NewObjectNode) {
			numberNewObjectNode((NewObjectNode) x);
		} else if(x instanceof NewArrayNode) {
			numberNewArrayNode((NewArrayNode) x);
		} else if(x instanceof RelationalNode) {
			numberRelationalNode((RelationalNode) x);
		} else if(x instanceof AddNode) {
			numberAddNode((AddNode) x);
		} else if(x instanceof MultNode) {
			numberMultNode((MultNode) x);
		} else if(x instanceof UnaryNode) {
			numberUnaryNode((UnaryNode) x);
		} else if(x instanceof CallNode) {
			numberCallNode((CallNode) x);
		} else if(x instanceof ConstNode) {
			numberConstNode((ConstNode) x);
		} else if(x instanceof IndexNode) {
			numberIndexNode((IndexNode) x);
		} else if(x instanceof DotNode) {
			numberDotNode((DotNode) x);
		} else if(x instanceof VarInitNode) {
			numberVarInitNode((VarInitNode) x);
		} else if(x instanceof VarNode) {
			numberVarNode((VarNode) x);
		} else if(x instanceof BooleanExpressionNode) {
			numberBooleanExpressionNode((BooleanExpressionNode) x);
		}
	}

	// --------------------------- Comando em geral -------------------
	public void printStatementNode( StatementNode x) {
		if(x instanceof BlockNode) {
			printBlockNode((BlockNode) x);
		} else if(x instanceof VarDeclNode) {
			printVarDeclNode((VarDeclNode) x);
		} else if(x instanceof AtribNode) {
			printAtribNode((AtribNode) x);
		} else if(x instanceof IfNode) {
			printIfNode((IfNode) x);
		} else if(x instanceof ForNode) {
			printForNode((ForNode) x);
		} else if(x instanceof PrintNode) {
			printPrintNode((PrintNode) x);
		} else if(x instanceof NopNode) {
			printNopNode((NopNode) x);
		} else if(x instanceof ReadNode) {
			printReadNode((ReadNode) x);
		} else if(x instanceof ReturnNode) {
			printReturnNode((ReturnNode) x);
		} else if(x instanceof SuperNode) {
			printSuperNode((SuperNode) x);
		} else if(x instanceof BreakNode) {
			printBreakNode((BreakNode) x);
		} else if(x instanceof MethodCallNode) {
			printMethodCallNode((MethodCallNode) x);
		}
	}

	public void numberStatementNode( StatementNode x) {
		if(x instanceof BlockNode) {
			numberBlockNode((BlockNode) x);
		} else if(x instanceof VarDeclNode) {
			numberVarDeclNode((VarDeclNode) x);
		} else if(x instanceof AtribNode) {
			numberAtribNode((AtribNode) x);
		} else if(x instanceof IfNode) {
			numberIfNode((IfNode) x);
		} else if(x instanceof ForNode) {
			numberForNode((ForNode) x);
		} else if(x instanceof PrintNode) {
			numberPrintNode((PrintNode) x);
		} else if(x instanceof NopNode) {
			numberNopNode((NopNode) x);
		} else if(x instanceof ReadNode) {
			numberReadNode((ReadNode) x);
		} else if(x instanceof ReturnNode) {
			numberReturnNode((ReturnNode) x);
		} else if(x instanceof SuperNode) {
			numberSuperNode((SuperNode) x);
		} else if(x instanceof BreakNode) {
			numberBreakNode((BreakNode) x);
		} else if(x instanceof MethodCallNode) {
			numberMethodCallNode((MethodCallNode) x);
		}
	}
}