package semananalysis;

import parser.*;

import symtable.*;
import syntaticTree.*;

import parser.FunConstants;

public class TypeCheck extends VarCheck {

	public boolean typeNot( EntryTable entry,  EntryTable... types) {
		for( EntryTable t : types) {
			if(entry == t) {
				return false;
			}
		}
		return true;
	}

	public boolean typeNotValid( EntryTable entry) {
		return typeNot(entry,
			STRING_TYPE,
			INT_TYPE,
			BOOLEAN_TYPE,
			FLOAT_TYPE,
			CHAR_TYPE,
			NULL_TYPE);
	}

	int nesting; // controla o nivel de aninhamento em comandos repetitivos
	public int Nlocals; // conta nmero de variveis locais num mtodo
	type Returntype; // tipo de retorno de um mtodo

	public  EntrySimple STRING_TYPE;
	public  EntrySimple INT_TYPE;
	public  EntrySimple BOOLEAN_TYPE;
	public  EntrySimple FLOAT_TYPE;
	public  EntrySimple CHAR_TYPE;

	public  EntrySimple NULL_TYPE;

	public EntryMethod CurMethod; // mtodo sendo analisado
	boolean cansuper; // indica se chamada super permitida

	public TypeCheck() {
		super();
		nesting = 0;
		Nlocals = 0;

		STRING_TYPE = (EntrySimple) Maintable.classFindUp("string");
		INT_TYPE = (EntrySimple) Maintable.classFindUp("int");
		BOOLEAN_TYPE = (EntrySimple) Maintable.classFindUp("boolean");
		FLOAT_TYPE = (EntrySimple) Maintable.classFindUp("float");
		CHAR_TYPE = (EntrySimple) Maintable.classFindUp("char");

		NULL_TYPE = new EntrySimple("$NULL$");

		Maintable.add(NULL_TYPE);
	}

	public void TypeCheckRoot( ListNode x) throws SemanticException {
		VarCheckRoot(x); // faz anlise das variveis e mtodos
		TypeCheckClassDeclListNode(x); // faz anlise do corpo dos mtodos

		if(foundSemanticError != 0) { // se houve erro, lana exceo
			throw new SemanticException(foundSemanticError +
				" Semantic Errors found (phase 3)");
		}
	}

	// ------------- lista de classes --------------------------
	public void TypeCheckClassDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckClassDeclNode((ClassDeclNode) x.node);
		} catch( SemanticException e) { // se um erro ocorreu na classe, da a msg mas faz a anlise p/ prxima
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckClassDeclListNode(x.next);
	}

	// verifica se existe referncia circular de superclasses
	public boolean circularSuperclass( EntryClass orig,  EntryClass e) {
		if(e == null) {
			return false;
		}

		if(orig == e) {
			return true;
		}

		return circularSuperclass(orig, e.parent);
	}

	// ------------- declaraco de classe -------------------------
	public void TypeCheckClassDeclNode( ClassDeclNode x)
		throws SemanticException {
		 Symtable temphold = Curtable; // salva tabela corrente
		EntryClass nc;

		if(x == null) {
			return;
		}

		nc = (EntryClass) Curtable.classFindUp(x.name.image);

		if(circularSuperclass(nc, nc.parent)) { // se existe declaraco circular, ERRO
			nc.parent = null;
			throw new SemanticException(x.position, "Circular inheritance");
		}

		Curtable = nc.nested; // tabela corrente = tabela da classe
		TypeCheckClassBodyNode(x.body);
		Curtable = temphold; // recupera tabela corrente
	}

	// ------------- corpo da classe -------------------------
	public void TypeCheckClassBodyNode( ClassBodyNode x) {
		if(x == null) {
			return;
		}

		TypeCheckClassDeclListNode(x.clist);
		TypeCheckVarDeclListNode(x.vlist);
		TypeCheckConstructDeclListNode(x.ctlist);
		TypeCheckMethodDeclListNode(x.mlist);
	}

	// ---------------- Lista de declaraces de variveis ----------------
	public void TypeCheckVarDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckVarDeclNode((VarDeclNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckVarDeclListNode(x.next);
	}

	// -------------------- Declaraco de varivel --------------------
	public void TypeCheckVarDeclNode( VarDeclNode x) throws SemanticException {
		ListNode p;
		EntryVar l;

		if(x == null) {
			return;
		}

		for(p = x.vars; p != null; p = p.next) {
			 VarNode q = (VarNode) p.node;

			// tenta pegar 2a. ocorrncia da varivel na tabela
			l = Curtable.varFind(q.position.image, 2);

			// se conseguiu a varivel foi definida 2 vezes, ERRO
			if(l != null) {
				throw new SemanticException(q.position,
					"Variable " + q.position.image + " already declared");
			}

			TypeCheckExpreNode(q);
		}
	}

	// -------------- Lista de construtores ---------------------
	public void TypeCheckConstructDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckConstructDeclNode((ConstructDeclNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckConstructDeclListNode(x.next);
	}

	// ------------------ Declaraco de construtor -----------------
	public void TypeCheckConstructDeclNode( ConstructDeclNode x)
		throws SemanticException {
		EntryMethod t;
		EntryRec r = null;
		EntryTable e;
		EntryClass thisclass;
		EntryVar thisvar;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;

		if(x == null) {
			return;
		}

		p = x.body.param;
		n = 0;

		// monta a lista com os tipos dos parmetros
		while(p != null) {
			q = (VarDeclNode) p.node; // q = n com a declaraco do parmetro
			u = (VarNode) q.vars.node; // u = n com o nome e dimenso
			n++;

			// acha a entrada do tipo na tabela
			e = Curtable.classFindUp(q.position.image);

			// constri a lista com os tipos dos parmetros
			r = new EntryRec(e, u.dim, n, r);
			p = p.next;
		}

		if(r != null) {
			r = r.inverte(); // inverte a lista
		}

		// acha a entrada do construtor na tabela
		t = Curtable.methodFind("constructor", r);
		CurMethod = t; // guarda mtodo corrente

		// inicia um novo escopo na tabela corrente
		Curtable.beginScope();

		// pega a entrada da classe corrente na tabela
		thisclass = Curtable.levelup;

		thisvar = new EntryVar("this", thisclass, 0);
		Curtable.add(thisvar); // inclui varivel local "this" com nmero 0
		Returntype = null; // tipo de retorno do mtodo = nenhum
		nesting = 0; // nvel de aninhamento de comandos for
		Nlocals = 1; // inicializa numero de variveis locais
		TypeCheckMethodBodyNode(x.body);
		t.totallocals = Nlocals; // nmero de varivamos locais do mtodo
		Curtable.endScope(); // retira variveis locais da tabela
	}

	// -------------------------- Lista de mtodos -----------------
	public void TypeCheckMethodDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckMethodDeclNode((MethodDeclNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckMethodDeclListNode(x.next);
	}

	// --------------------- Declaraco de mtodo ---------------
	public void TypeCheckMethodDeclNode( MethodDeclNode x)
		throws SemanticException {
		EntryMethod t;
		EntryRec r = null;
		EntryTable e;
		EntryClass thisclass;
		EntryVar thisvar;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;

		if(x == null) {
			return;
		}

		p = x.body.param;
		n = 0;

		// monta a lista com os tipos dos parmetros
		while(p != null) {
			q = (VarDeclNode) p.node; // q = n com a declaraco do parmetro
			u = (VarNode) q.vars.node; // u = n com o nome e dimenso
			n++;

			// acha a entrada do tipo na tabela
			e = Curtable.classFindUp(q.position.image);

			// constri a lista com os tipos dos parmetros
			r = new EntryRec(e, u.dim, n, r);
			p = p.next;
		}

		if(r != null) {
			r = r.inverte();
		}

		// acha a entrada do mtodo na tabela
		t = Curtable.methodFind(x.name.image, r);
		CurMethod = t; // guarda mtodo corrente

		// Returntype = tipo de retorno do mtodo
		Returntype = new type(t.type, t.dim);

		// inicia um novo escopo na tabela corrente
		Curtable.beginScope();

		// pega a entrada da classe corrente na tabela
		thisclass = Curtable.levelup;

		thisvar = new EntryVar("this", thisclass, 0, 0);
		Curtable.add(thisvar); // inclui varivel local "this" na tabela

		nesting = 0; // nvel de aninhamento de comandos for
		Nlocals = 1; // inicializa nmero de variveis locais
		TypeCheckMethodBodyNode(x.body);
		t.totallocals = Nlocals; // nmero de variveis locais declaradas
		Curtable.endScope(); // retira variveis locais da tabela corrente
	}

	// -------------------------- Corpo de mtodo ----------------------
	public void TypeCheckMethodBodyNode( MethodBodyNode x) {
		if(x == null) {
			return;
		}

		TypeCheckLocalVarDeclListNode(x.param); // trata parmetro como var. local

		cansuper = false;

		if(Curtable.levelup.parent != null) { // existe uma superclasse para a classe corrente ?
												// acha primeiro comando do mtodo

			StatementNode p = x.stat;

			while(p instanceof BlockNode)
				p = (StatementNode) ((BlockNode) p).stats.node;

			cansuper = p instanceof SuperNode; // verifica se chamada super
		}

		try {
			TypeCheckStatementNode(x.stat);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
	}

	// ------------------------ lista de variveis locais ----------------------
	public void TypeCheckLocalVarDeclListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckLocalVarDeclNode((VarDeclNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckLocalVarDeclListNode(x.next);
	}

	// ---------------------- Declaraco de variveis locais ----------------------
	public void TypeCheckLocalVarDeclNode( VarDeclNode x)
		throws SemanticException {
		ListNode p;
		VarNode q;
		EntryVar l;
		EntryTable c;

		if(x == null) {
			return;
		}

		// procura tipo da declaraco na tabela de smbolos
		c = Curtable.classFindUp(x.position.image);

		// se no achou, ERRO
		if(c == null) {
			throw new SemanticException(x.position,
				"Class " + x.position.image + " not found.");
		}

		for(p = x.vars; p != null; p = p.next) {
			q = (VarNode) p.node;
			l = Curtable.varFind(q.position.image);

			// se varivel j existe preciso saber que tipo de varivel
			if(l != null) {
				// primeiro verifica se local, definida no escopo corrente
				if(l.scope == Curtable.scptr) { // se for, ERRO
					throw new SemanticException(q.position,
						"Variable " + p.position.image + " already declared");
				}

				// c.c. verifica se uma varivel de classe
				if(l.localcount < 0) { // se for d uma advertncia
					System.out.println("Line " + q.position.beginLine +
						" Column " + q.position.beginColumn +
						" Warning: Variable " + q.position.image +
						" hides a class variable");
				} else { // seno, uma varivel local em outro escopo
					System.out.println("Line " + q.position.beginLine +
						" Column " + q.position.beginColumn +
						" Warning: Variable " + q.position.image +
						" hides a parameter or a local variable");
				}
			}

			// insere a varivel local na tabela corrente
			Curtable.add(new EntryVar(q.position.image, c, q.dim, Nlocals++));
		}
	}

	// --------------------------- Comando composto ----------------------
	public void TypeCheckBlockNode( BlockNode x) {
		Curtable.beginScope(); // incio de um escopo
		TypeCheckStatementListNode(x.stats);
		Curtable.endScope(); //  do escopo, libera vars locais
	}

	// --------------------- Lista de comandos --------------------
	public void TypeCheckStatementListNode( ListNode x) {
		if(x == null) {
			return;
		}

		try {
			TypeCheckStatementNode((StatementNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckStatementListNode(x.next);
	}

	// --------------------------- Comando print ---------------------
	public void TypeCheckPrintNode( PrintNode x) throws SemanticException {
		type t;

		if(x == null) {
			return;
		}

		// t = tipo e dimenso do resultado da expresso
		t = TypeCheckExpreNode(x.expr);

		// tipo tem que ser string e dimenso tem que ser 0
		if(typeNotValid(t.ty) || t.dim != 0) {
			throw new SemanticException(x.position, "string expression required");
		}
	}

	// ---------------------- Comando read --------------------------
	public void TypeCheckReadNode( ReadNode x) throws SemanticException {
		type t;

		if(x == null) {
			return;
		}

		// verifica se o n filho tem um tipo vlido
		if(!(x.expr instanceof DotNode || x.expr instanceof IndexNode ||
			x.expr instanceof VarNode)) {
			throw new SemanticException(x.position,
				"Invalid expression in read statement");
		}

		// verifica se e uma atribuio para "this"
		if(x.expr instanceof VarNode) {
			 EntryVar v = Curtable.varFind(x.expr.position.image);

			if(v != null && v.localcount == 0) // a varivel local 0?
			{
				throw new SemanticException(x.position,
					"Reading into variable " + " \"this\" is not legal");
			}
		}

		// verifica se o tipo string ou int
		t = TypeCheckExpreNode(x.expr);

		if(typeNotValid(t.ty)) {
			throw new SemanticException(x.position,
				"Invalid type. Must be int or string");
		}

		// verifica se no array
		if(t.dim != 0) {
			throw new SemanticException(x.position, "Cannot read array");
		}
	}

	// --------------------- Comando return -------------------------
	public void TypeCheckReturnNode( ReturnNode x) throws SemanticException {
		type t;

		if(x == null) {
			return;
		}

		// t = tipo e dimenso do resultado da expresso
		t = TypeCheckExpreNode(x.expr);

		// verifica se igual ao tipo do mtodo corrente
		if(t == null) { // t == null no tem expresso no return

			if(Returntype == null) {
				return;
			} else { // se Returntype != null e um mtodo, ento ERRO
				throw new SemanticException(x.position,
					"Return expression required");
			}
		} else {
			if(Returntype == null) { // retorno num construtor, ERRO
				throw new SemanticException(x.position,
					"Constructor cannot return a value");
			}
		}

		// compara tipo e dimenso
		if(t.ty != Returntype.ty || t.dim != Returntype.dim) {
			throw new SemanticException(x.position, "Invalid return type");
		}
	}

	// ------------------------ Comando super --------------------------
	public void TypeCheckSuperNode( SuperNode x) throws SemanticException {
		type t;

		if(x == null) {
			return;
		}

		if(Returntype != null) {
			throw new SemanticException(x.position,
				"super is only allowed in constructors");
		}

		if(!cansuper) {
			throw new SemanticException(x.position,
				"super must be first statement in the constructor");
		}

		cansuper = false; // no permite outro super

		// p aponta para a entrada da superclasse da classe corrente
		 EntryClass p = Curtable.levelup.parent;

		if(p == null) {
			throw new SemanticException(x.position,
				"No superclass for this class");
		}

		// t.ty possui um EntryRec com os tipos dos parmetros
		t = TypeCheckExpreListNode(x.args);

		// procura o construtor na tabela da superclasse
		 EntryMethod m = p.nested.methodFindInclass("constructor",
			(EntryRec) t.ty);

		// se no achou, ERRO
		if(m == null) {
			throw new SemanticException(x.position,
				"Constructor " + p.name + "(" +
					(t.ty == null ? "" : ((EntryRec) t.ty).toStr()) +
					") not found");
		}

		CurMethod.hassuper = true; // indica que existe chamada a super no mtodo
	}

	// ------------------------- Comando de atribuio -------------------
	public void TypeCheckAtribNode( AtribNode x) throws SemanticException {
		type t1;
		type t2;
		EntryVar v;

		if(x == null) {
			return;
		}

		// verifica se o n filho tem um tipo vlido
		if(!(x.expr1 instanceof DotNode || x.expr1 instanceof IndexNode ||
			x.expr1 instanceof VarNode)) {
			throw new SemanticException(x.position,
				"Invalid left side of assignment");
		}

		// verifica se uma atribuio para "this"
		if(x.expr1 instanceof VarNode) {
			v = Curtable.varFind(x.expr1.position.image);

			if(v != null && v.localcount == 0) // a varivel local 0?
			{
				throw new SemanticException(x.position,
					"Assigning to variable " + " \"this\" is not legal");
			}
		}

		t1 = TypeCheckExpreNode(x.expr1);

		if(FLOAT_TYPE.equals(t1.ty) && x.expr2 instanceof IntConstNode) {
			t2 = TypeCheckFloatConstNode((FloatConstNode) x.expr2);
		} else {
			t2 = TypeCheckExpreNode(x.expr2);
		}

		// verifica tipos das expresses
		// verifica dimenses
		if(t1.dim != t2.dim) {
			throw new SemanticException(x.position,
				"Invalid dimensions in assignment");
		}

		// verifica se lado esquerdo uma classe e direito null, OK
		if(t1.ty instanceof EntryClass && t2.ty == NULL_TYPE) {
			return;
		}

		// verifica se t2 e subclasse de t1
		if(!(isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty))) {
			throw new SemanticException(x.position,
				"Incompatible types for assignment ");
		}
	}

	public boolean isSubClass( EntryTable t1,  EntryTable t2) {
		// verifica se so o mesmo tipo (vale para tipos simples)
		if(t1 == t2) {
			return true;
		} else if(t2 == INT_TYPE) {
			return t1 == FLOAT_TYPE;
		}

		// verifica se so classes
		if(!(t1 instanceof EntryClass && t2 instanceof EntryClass)) {
			return false;
		}

		// procura t2 nas superclasses de t1
		for(EntryClass p = ((EntryClass) t1).parent; p != null; p = p.parent)
			if(p == t2) {
				return true;
			}

		return false;
	}

	// ---------------------------------- comando if --------------------
	public void TypeCheckIfNode( IfNode x) {
		type t;

		if(x == null) {
			return;
		}

		try {
			t = TypeCheckExpreNode(x.expr);

			if(t.ty != BOOLEAN_TYPE || t.dim != 0) {
				throw new SemanticException(x.expr.position,
					"Integer expression expected");
			}
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		try {
			TypeCheckStatementNode(x.stat1);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		try {
			TypeCheckStatementNode(x.stat2);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
	}

	// ------------------------- comando for -----------------------
	public void TypeCheckForNode( ForNode x) {
		type t;

		if(x == null) {
			return;
		}

		// analisa inicializao
		try {
			TypeCheckStatementNode(x.init);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa expresso de controle
		try {
			t = TypeCheckExpreNode(x.expr);

			if(t.ty != BOOLEAN_TYPE || t.dim != 0) {
				throw new SemanticException(x.expr.position,
					"Integer expression expected");
			}
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa expresso de incremento
		try {
			TypeCheckStatementNode(x.incr);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa comando a ser repetido
		try {
			nesting++; // incrementa o aninhamento
			TypeCheckStatementNode(x.stat);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		nesting--; // decrementa o aninhamento
	}

	// --------------------------- Comando break --------------------
	public void TypeCheckBreakNode( BreakNode x) throws SemanticException {
		if(x == null) {
			return;
		}

		// verifica se est dentro de um for. Se no, ERRO
		if(nesting <= 0) {
			throw new SemanticException(x.position,
				"break not in a for statement");
		}
	}

	// --------------------------- Comando vazio -------------------
	public void TypeCheckNopNode( NopNode x) {
		// nada a ser feito
	}

	// ------------------ Expresses ----------------------------------------
	// -------------------------- Alocao de objeto ------------------------
	public type TypeCheckNewObjectNode( NewObjectNode x)
		throws SemanticException {
		type t;
		EntryMethod p;
		EntryTable c;

		if(x == null) {
			return null;
		}

		// procura a classe da qual se deseja criar um objeto
		c = Curtable.classFindUp(x.name.image);

		// se no achou, ERRO
		if(c == null) {
			throw new SemanticException(x.position,
				"Class " + x.name.image + " not found");
		}

		// t.ty recebe a lista de tipos dos argumentos
		t = TypeCheckExpreListNode(x.args);

		// procura um construtor com essa assinatura
		 Symtable s = ((EntryClass) c).nested;
		p = s.methodFindInclass("constructor", (EntryRec) t.ty);

		// se no achou, ERRO
		if(p == null) {
			throw new SemanticException(x.position,
				"Constructor " + x.name.image + "(" +
					(t.ty == null ? "" : ((EntryRec) t.ty).toStr()) +
					") not found");
		}

		// retorna c como tipo, dimenso = 0, local = -1 (no local)
		t = new type(c, 0);

		return t;
	}

	// -------------------------- Alocao de array ------------------------
	public type TypeCheckNewArrayNode( NewArrayNode x) throws SemanticException {
		type t;
		EntryTable c;
		ListNode p;
		int k;

		if(x == null) {
			return null;
		}

		// procura o tipo da qual se deseja criar um array
		c = Curtable.classFindUp(x.name.image);

		// se no achou, ERRO
		if(c == null) {
			throw new SemanticException(x.position,
				"Type " + x.name.image + " not found");
		}

		// para cada expresso das dimenses, verifica se tipo e int
		for(k = 0, p = x.dims; p != null; p = p.next) {
			t = TypeCheckExpreNode((ExpreNode) p.node);

			if(t.ty != INT_TYPE || t.dim != 0) {
				throw new SemanticException(p.position,
					"Invalid expression for an array dimension");
			}

			k++;
		}

		return new type(c, k);
	}

	// --------------------------- Lista de expresses ---------------
	public type TypeCheckExpreListNode( ListNode x) {
		type t;
		type t1;
		EntryRec r;
		int n;

		if(x == null) {
			return new type(null, 0);
		}

		try {
			// pega tipo do primeiro n da lista
			t = TypeCheckExpreNode((ExpreNode) x.node);
		} catch( SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
			t = new type(NULL_TYPE, 0);
		}

		// pega tipo do restante da lista. t1.ty contm um EntryRec
		t1 = TypeCheckExpreListNode(x.next);

		// n = tamanho da lista em t1
		n = t1.ty == null ? 0 : ((EntryRec) t1.ty).cont;

		// cria novo EntryRec com t.ty como 1.o elemento
		r = new EntryRec(t.ty, t.dim, n + 1, (EntryRec) t1.ty);

		// cria type com r como varivel ty
		t = new type(r, 0);

		return t;
	}

	// --------------------- Expresso relacional -------------------
	public type TypeCheckRelationalNode( RelationalNode x)
		throws SemanticException {
		type t1;
		type t2;
		int op; // operao

		if(x == null) {
			return null;
		}

		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se ambos so int, retorna OK
		if(t1.ty == INT_TYPE && t2.ty == INT_TYPE) {
			return new type(INT_TYPE, 0);
		}

		// se a dimenso diferente, ERRO
		if(t1.dim != t2.dim) {
			throw new SemanticException(x.position,
				"Can not compare objects with different dimensions");
		}

		// se dimenso > 0 s pode comparar igualdade
		if(op != FunConstants.EQ && op != FunConstants.NEQ &&
			t1.dim > 0) {
			throw new SemanticException(x.position,
				"Can not use " + x.position.image + " for arrays");
		}

		// se dois so objetos do mesmo tipo pode comparar igualdade
		// isso inclui 2 strings
		if((isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty)) &&
			(op == FunConstants.NEQ || op == FunConstants.EQ)) {
			return new type(INT_TYPE, 0);
		}

		// se um objeto e outro null, pode comparar igualdade
		if((t1.ty instanceof EntryClass && t2.ty == NULL_TYPE ||
			t2.ty instanceof EntryClass && t1.ty == NULL_TYPE) &&
			(op == FunConstants.NEQ || op == FunConstants.EQ)) {
			return new type(INT_TYPE, 0);
		}

		throw new SemanticException(x.position,
			"Invalid types for " + x.position.image);
	}

	// ------------------------ Soma ou subtrao -------------------
	public type TypeCheckAddNode( AddNode x) throws SemanticException {
		type t1;
		type t2;

		if(x == null) {
			return null;
		}

		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se dimenso > 0, ERRO
		if(t1.dim > 0 || t2.dim > 0) {
			throw new SemanticException(x.position,
				"Can not use " + x.position.image + " for arrays");
		}

		if(t1.ty == STRING_TYPE && t2.ty == STRING_TYPE) {
			return new type(STRING_TYPE, 0);
		} else {
			if(typeNot(t1.ty, INT_TYPE, FLOAT_TYPE)
				|| typeNot(t2.ty, INT_TYPE, FLOAT_TYPE)) {
				throw new SemanticException(x.position,
					"Invalid types for " + x.position.image);
			}

			if(t1.ty == FLOAT_TYPE || t2.ty == FLOAT_TYPE) {
				return new type(FLOAT_TYPE, 0);
			} else if(t1.ty == INT_TYPE || t2.ty == INT_TYPE) {
				return new type(INT_TYPE, 0);
			}
		}

		throw new SemanticException(x.position,
			"Invalid types for " + x.position.image);
	}

	// ---------------------- Multiplicao ou diviso --------------------
	public type TypeCheckMultNode( MultNode x) throws SemanticException {
		type t1;
		type t2;

		if(x == null) {
			return null;
		}

		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se dimenso > 0, ERRO
		if(t1.dim > 0 || t2.dim > 0) {
			throw new SemanticException(x.position,
				"Can not use " + x.position.image + " for arrays");
		}

		if(typeNot(t1.ty, INT_TYPE, FLOAT_TYPE)
			|| typeNot(t2.ty, INT_TYPE, FLOAT_TYPE)) {
			throw new SemanticException(x.position,
				"Invalid types for " + x.position.image);
		}


		if(t1.ty == FLOAT_TYPE || t2.ty == FLOAT_TYPE) {
			return new type(FLOAT_TYPE, 0);
		} else if(t1.ty == INT_TYPE || t2.ty == INT_TYPE) {
			return new type(INT_TYPE, 0);
		}
		throw new SemanticException(x.position,
			"Invalid types for " + x.position.image);
	}

	// ------------------------- Expresso unaria ------------------------
	public type TypeCheckUnaryNode( UnaryNode x) throws SemanticException {
		type t;

		if(x == null) {
			return null;
		}

		t = TypeCheckExpreNode(x.expr);

		// se dimenso > 0, ERRO
		if(t.dim > 0) {
			throw new SemanticException(x.position,
				"Can not use unary " + x.position.image + " for arrays");
		}

		// s int aceito
		if(typeNot(t.ty, INT_TYPE, FLOAT_TYPE)) {
			throw new SemanticException(x.position,
				"Incompatible type for unary " + x.position.image);
		}

		return new type(INT_TYPE, 0);
	}

	public type TypeCheckConstNode(ConstNode x) throws SemanticException {
		if(x == null) {
			return null;
		}

		if(x instanceof IntConstNode) {
			return TypeCheckIntConstNode((IntConstNode) x);
		}else if(x instanceof FloatConstNode) {
			return TypeCheckFloatConstNode((FloatConstNode) x);
		} else if(x instanceof StringConstNode) {
			return TypeCheckStringConstNode((StringConstNode) x);
		} else if(x instanceof CharConstNode) {
			return TypeCheckCharConstNode((CharConstNode) x);
		} else if(x instanceof NullConstNode) {
			return TypeCheckNullConstNode((NullConstNode) x);
		} else if(x instanceof BooleanConstNode) {
			return TypeCheckBooleanConstNode((BooleanConstNode) x);
		} else {
			throw new SemanticException(x.position, "Invalid const type!");
		}
	}

	// -------------------------- Constante inteira ----------------------
	public type TypeCheckIntConstNode( IntConstNode x) throws SemanticException {
		// tenta transformar imagem em numero inteiro
		try {
			Integer.parseInt(x.position.image);
			return new type(INT_TYPE, 0);
		} catch( NumberFormatException e1) {
			throw new SemanticException(x.position, "Invalid int constant");
		}
	}

	public type TypeCheckFloatConstNode( FloatConstNode x) throws SemanticException {
		try {
			Float.parseFloat(x.position.image.replaceAll("\\D", ""));
			return new type(FLOAT_TYPE, 0);
		} catch( NumberFormatException e) { // se deu erro, formato e invlido (possivelmente fora dos limites)
			throw new SemanticException(x.position, "Invalid double constant");
		}
	}

	// ------------------------ Constante string ----------------------------
	public type TypeCheckStringConstNode( StringConstNode x) {
		if(x == null) {
			return null;
		}

		return new type(STRING_TYPE, 0);
	}

	public type TypeCheckCharConstNode( CharConstNode x) throws SemanticException {
		if(x.position.image.length() == 3) { // Ex: 'a' ou 'b'
			return new type(CHAR_TYPE, 0);
		}
		throw new SemanticException(x.position, "Invalid char constant");
	}

	// ------------------------------ Constante null --------------------------
	public type TypeCheckNullConstNode( NullConstNode x) {
		if(x == null) {
			return null;
		}

		return new type(NULL_TYPE, 0);
	}

	public type TypeCheckBooleanConstNode( BooleanConstNode x) {
		if(x == null) {
			return null;
		}

		return new type(BOOLEAN_TYPE, 0);
	}

	// -------------------------------- Nome de varivel ------------------
	public type TypeCheckVarNode( VarNode x) throws SemanticException {
		EntryVar p;

		if(x == null) {
			return null;
		}

		// procura varivel na tabela
		p = Curtable.varFind(x.position.image);

		// se no achou, ERRO
		if(p == null) {
			throw new SemanticException(x.position,
				"Variable " + x.position.image + " not found");
		}

		return new type(p.type, p.dim);
	}

	// ---------------------------- Chamada de mtodo ------------------------
	public type TypeCheckCallNode( CallNode x) throws SemanticException {
		EntryClass c;
		EntryMethod m;
		type t1;
		type t2;

		if(x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t1 = TypeCheckExpreNode(x.expr);

		// se for array, ERRO
		if(t1.dim > 0) {
			throw new SemanticException(x.position, "Arrays do not have methods");
		}

		// se no for uma classe, ERRO
		if(!(t1.ty instanceof EntryClass)) {
			throw new SemanticException(x.position,
				"Type " + t1.ty.name + " does not have methods");
		}

		// pega tipos dos argumentos
		t2 = TypeCheckExpreListNode(x.args);

		// procura o mtodo desejado na classe t1.ty
		c = (EntryClass) t1.ty;
		m = c.nested.methodFind(x.meth.image, (EntryRec) t2.ty);

		// se no achou, ERRO
		if(m == null) {
			throw new SemanticException(x.position,
				"Method " + x.meth.image + "(" +
					(t2.ty == null ? "" : ((EntryRec) t2.ty).toStr()) +
					") not found in class " + c.name);
		}

		return new type(m.type, m.dim);
	}

	// ---------------------------- Chamada de mtodo ------------------------
	public type TypeCheckMethodCallNode( MethodCallNode x) throws SemanticException {
		EntryMethod m;
		EntryClass caller = null;
		type t;

		if(x == null) {
			return null;
		}

		if(x.caller != null) {
			// tipo da classe chamadora do metodo
			caller = (EntryClass) Curtable.varFind(x.caller.image).type;
		}

		// pega tipos dos argumentos
		t = TypeCheckExpreListNode(x.args);

		// procura o mtodo desejado na classe atual ou caller
		m = caller != null ? caller.nested.methodFind(x.position.image, (EntryRec) t.ty) : Curtable.methodFind(x.position.image, (EntryRec) t.ty);

		// se no achou, ERRO
		if(m == null) {
			throw new SemanticException(x.position,
				"Method " + x.position.image + "(" +
					(t.ty == null ? "" : ((EntryRec) t.ty).toStr()) +
					") not found" +
					(caller != null ? " in class " + caller.name : ""));
		}

		return new type(m.type, m.dim);
	}

	// --------------------------- Indexao de varivel ---------------
	public type TypeCheckIndexNode( IndexNode x) throws SemanticException {
		type t1;
		type t2;

		if(x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t1 = TypeCheckExpreNode(x.expr1);

		// se no for array, ERRO
		if(t1.dim <= 0) {
			throw new SemanticException(x.position,
				"Can not index non array variables");
		}

		// pega tipo do ndice
		t2 = TypeCheckExpreNode(x.expr2);

		// se no for int, ERRO
		if(t2.ty != INT_TYPE || t2.dim > 0) {
			throw new SemanticException(x.position,
				"Invalid type. Index must be int");
		}

		return new type(t1.ty, t1.dim - 1);
	}

	// -------------------------- Acesso a campo de varivel ---------------
	public type TypeCheckDotNode( DotNode x) throws SemanticException {
		EntryClass c;
		EntryVar v;
		type t;

		if(x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t = TypeCheckExpreNode(x.expr);

		// se for array, ERRO
		if(t.dim > 0) {
			throw new SemanticException(x.position, "Arrays do not have fields");
		}

		// se no for uma classe, ERRO
		if(!(t.ty instanceof EntryClass)) {
			throw new SemanticException(x.position,
				"Type " + t.ty.name + " does not have fields");
		}

		// procura a varivel desejada na classe t.ty
		c = (EntryClass) t.ty;
		v = c.nested.varFind(x.field.image);

		// se no achou, ERRO
		if(v == null) {
			throw new SemanticException(x.position,
				"Variable " + x.field.image + " not found in class " + c.name);
		}

		return new type(v.type, v.dim);
	}

	public type TypeCheckVarInitNode( VarInitNode x) throws SemanticException {
		EntryVar p;
		type t;

		if(x == null) {
			return null;
		}

		p = Curtable.varFind(x.position.image);

		if(p == null) {
			throw new SemanticException(x.position,
				"Variable " + x.position.image + " not found");
		}

		if(x.assignment != null) {
			if(FLOAT_TYPE.equals(p.type) && x.assignment instanceof IntConstNode) {
				t = TypeCheckFloatConstNode((FloatConstNode) x.assignment);
			} else {
				t = TypeCheckExpreNode(x.assignment);
			}

			// verifica tipos das expresses
			// verifica dimenses
			if(p.dim != t.dim) {
				throw new SemanticException(x.position,
					"Invalid dimensions in assignment");
			}

			// verifica se lado esquerdo uma classe e direito null, OK
			if(p.type instanceof EntryClass && t.ty == NULL_TYPE) {
				return new type(p.type, p.dim);
			}

			// verifica se t2 e subclasse de t1
			if(!(isSubClass(t.ty, p.type) || isSubClass(p.type, t.ty))) {
				throw new SemanticException(x.position,
					"Incompatible types for assignment ");
			}
		}

		return new type(p.type, p.dim);
	}

	public type TypeCheckBooleanExpressionNode( BooleanExpressionNode x) throws SemanticException {
		type t1, t2, t3;

		if(x == null) {
			return null;
		}

		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);
		t3 = TypeCheckExpreNode(x.expr3);

		if(t1.dim > 0) {
			throw new SemanticException(x.position,
				"Can not use " + x.position.image + " for arrays");
		}

		if(t2 == null) {
			if(typeNot(t1.ty, BOOLEAN_TYPE)) {
				throw new SemanticException(x.position,
					"Invalid types for expression: " + x.position.image + " is not a boolean value");
			}
		} else {
			if(t2.dim > 0) {
				throw new SemanticException(x.position,
					"Can not use " + x.position.image + " for arrays");
			}

			if(x.equality) {
				if(typeNotValid(t1.ty)) {
					throw new SemanticException(x.expr1.position,
						"Invalid types for " + x.expr1.position.image);
				}
				if(typeNotValid(t2.ty)) {
					throw new SemanticException(x.expr1.position,
						"Invalid types for " + x.expr1.position.image);
				}
			} else {
				if(typeNot(t1.ty, INT_TYPE, CHAR_TYPE, FLOAT_TYPE)) {
					throw new SemanticException(x.expr1.position,
						"Invalid types for " + x.expr1.position.image);
				}

				if(typeNot(t2.ty, INT_TYPE, CHAR_TYPE, FLOAT_TYPE)) {
					throw new SemanticException(x.expr2.position,
						"Invalid types for " + x.expr2.position.image);
				}
			}
		}

		if(t3 != null) {
			if(t3.dim > 0) {
				throw new SemanticException(x.position,
					"Can not use " + x.position.image + " for arrays");
			}

			if(typeNot(t3.ty, BOOLEAN_TYPE)) {
				throw new SemanticException(x.position,
					"Invalid types for comparison " + x.comparison2.image);
			}
		}

		return new type(BOOLEAN_TYPE, 0);
	}

	// --------------------------- Expresso em geral --------------------------
	public type TypeCheckExpreNode( ExpreNode x) throws SemanticException {
		if(x instanceof NewObjectNode) {
			return TypeCheckNewObjectNode((NewObjectNode) x);
		} else if(x instanceof NewArrayNode) {
			return TypeCheckNewArrayNode((NewArrayNode) x);
		} else if(x instanceof RelationalNode) {
			return TypeCheckRelationalNode((RelationalNode) x);
		} else if(x instanceof AddNode) {
			return TypeCheckAddNode((AddNode) x);
		} else if(x instanceof MultNode) {
			return TypeCheckMultNode((MultNode) x);
		} else if(x instanceof UnaryNode) {
			return TypeCheckUnaryNode((UnaryNode) x);
		} else if(x instanceof CallNode) {
			return TypeCheckCallNode((CallNode) x);
		} else if(x instanceof ConstNode) {
			return TypeCheckConstNode((ConstNode) x);
		} else if(x instanceof StringConstNode) {
			return TypeCheckStringConstNode((StringConstNode) x);
		} else if(x instanceof NullConstNode) {
			return TypeCheckNullConstNode((NullConstNode) x);
		} else if(x instanceof BooleanConstNode) {
			return TypeCheckBooleanConstNode((BooleanConstNode) x);
		} else if(x instanceof IndexNode) {
			return TypeCheckIndexNode((IndexNode) x);
		} else if(x instanceof DotNode) {
			return TypeCheckDotNode((DotNode) x);
		} else if(x instanceof VarInitNode) {
			return TypeCheckVarInitNode((VarInitNode) x);
		} else if(x instanceof VarNode) {
			return TypeCheckVarNode((VarNode) x);
		} else if(x instanceof BooleanExpressionNode) {
			return TypeCheckBooleanExpressionNode((BooleanExpressionNode) x);
		} else {
			return null;
		}
	}

	// --------------------------- Comando em geral -------------------
	public void TypeCheckStatementNode( StatementNode x)
		throws SemanticException {
		if(x instanceof BlockNode) {
			TypeCheckBlockNode((BlockNode) x);
		} else if(x instanceof VarDeclNode) {
			TypeCheckLocalVarDeclNode((VarDeclNode) x);
		} else if(x instanceof AtribNode) {
			TypeCheckAtribNode((AtribNode) x);
		} else if(x instanceof IfNode) {
			TypeCheckIfNode((IfNode) x);
		} else if(x instanceof ForNode) {
			TypeCheckForNode((ForNode) x);
		} else if(x instanceof PrintNode) {
			TypeCheckPrintNode((PrintNode) x);
		} else if(x instanceof NopNode) {
			TypeCheckNopNode((NopNode) x);
		} else if(x instanceof ReadNode) {
			TypeCheckReadNode((ReadNode) x);
		} else if(x instanceof ReturnNode) {
			TypeCheckReturnNode((ReturnNode) x);
		} else if(x instanceof SuperNode) {
			TypeCheckSuperNode((SuperNode) x);
		} else if(x instanceof BreakNode) {
			TypeCheckBreakNode((BreakNode) x);
		}else if(x instanceof MethodCallNode) {
			TypeCheckMethodCallNode((MethodCallNode) x);
		}
	}
}