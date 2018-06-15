package semananalysis;

import symtable.*;
import syntaticTree.*;

public class VarCheck extends ClassCheck {

public VarCheck() 
{
	super();
}

public void VarCheckRoot(ListNode x) throws SemanticException
{
	ClassCheckRoot(x);		// faz analise da classe
	VarCheckClassDeclListNode(x);
	if(foundSemanticError != 0)		// se houve erro, lança exceção
		throw new SemanticException(foundSemanticError +
										" Erros semanticos encontrados (phase 2)");
		
}

public void VarCheckClassDeclListNode(ListNode x)
{
	if (x == null) return;
	try {
		VarCheckClassDeclNode( (ClassDeclNode) x.node);
	}
	catch (Exception e) 
	{ // se um erro ocorreu na analise de class,
		// da a mensagem, mas faz a analise para proxima classe
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	VarCheckClassDeclListNode(x.next);
}

public void VarCheckClassDeclNode(ClassDeclNode x) throws SemanticException
{
Symtable temphold = Curtable; 	// salva tabela corrente
EntryClass c = null;
EntryClass nc;

	if(x == null) return;
	if(x.supername != null)
	{	// verifica se superclasse foi definida
		c = (EntryClass) Curtable.classFindUp(x.supername.image);
		if (c == null)	// Se nao achou superclasse, ERRO
		{
			throw new SemanticException(x.position, "Superclass "+
									x.supername.image + " not found");
			
		}
	}
	nc  = (EntryClass) Curtable.classFindUp(x.name.image);
	nc.parent = c;			// coloca na tabela o apontador p/ superclasse
	Curtable = nc.nested;	// tablea corrente  = tabela de classe 
	VarCheckClassBodyNode(x.body);
	Curtable = temphold;	// recupera tabela corrente
}

public void VarCheckClassBodyNode(ClassBodyNode x)
{
	if(x == null) return;
	
	VarCheckClassDeclListNode(x.clist);
	VarCheckVarDeclListNode(x.vlist);
	VarCheckConstructDeclListNode(x.ctlist);
	
	// se nao existe construtor(), insere um falso
	if(Curtable.methodFindInclass("constructor", null) == null);
	{
		Curtable.add(new EntryMethod("constructor",
										Curtable.levelup, true));
	}
	VarCheckMethodDeclListNode(x.mlist);
}

public void VarCheckVarDeclListNode(ListNode x)
{
	if(x == null) return;
	try {
		VarCheckVarDeclNode( (VarDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	VarCheckVarDeclListNode(x.next);
}

public void VarCheckVarDeclNode(VarDeclNode x)  throws SemanticException
{
	EntryTable c;
	ListNode p;
	
	if(x == null) return;
	
	// acha entrada do tipo da variavel
	c = Curtable.classFindUp(x.position.image);
	// se nao achou, ERRO
	if (c == null)
		throw new SemanticException(x.position, "Class "+
				x.position.image + " nao encontrado");
	
	// para cada variavel da declaracao, cria uma entrada na tabela
	for(p = x.vars; p != null; p = p.next)
	{
		VarNode = (VarNode) p.node;
		Curtable.add(new EntryVar(q.position.image, c, q, dim));
	}
}

public void VarCheckConstructDeclListNode(ListNode x)
{
	if(x == null) return;
	
	try {
		VarCheckConstructDeclNode((ConstructDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	VarCheckConstructDeclListNode(x.next);
}

public void VarCheckConstructDeclNode(ConstructDeclNode x) throws SemanticException
{
EntryMethod c;
EntryRec r = null;
EntryTable e;
ListNode p;
VarDeclNode q;
VarNode u;
int n;


	if(x == null) return;
	p = x.body.param;
	n = 0;
	while(p != null)		// para cada paramentro do construtor
	{
		q = (VarDeclNode) p.node;	// q = no com a declaracao do parametro
		u = (VarNode) q.vars.node;	// u = no com o nome e dimensao
		n++;
		// acha a entrada do tipo na tabela
		e = Curtable.classFindUp(q.position.image);
		
		// se nao achou : ERRO
		if(e == null)
			throw new SemanticException(q.position, "Class " +
							q.position.image + " not found");
		
		// constroi a lista com os parametros
		r = new EntryRec(e, u.dim, n ,r);
		p = p.next;
	}
	
	if(r != null) 
		r = r.inverte(); 	//inverte a lista
	
	// procura construtor com essa assinatura dentro da mesam classe
	c = Curtable.methodFindInclass("constructor", r);
	
	if(c == null)
	{// se nao achou, insere
		c = new EntryMethod("constructor", Curtable.levelup, 0, r);
		Curtable.add(c);
	}
	else		// construtor ja definido na mesma classe: ERRO
		throw new SemanticException(x.position, "Constructor " +
					Curtable.levelup.name +
					"(" + (r == null? "" : r.toString()) + ")" +
					" ja declarado");		
}

public EntryMethod methodFindInclass(String x, EntryRec r)
{
EntryTable p = top;
EntryClass q;

	// para cada entrada da tabela
	while(p != null)
	{
		// verifica se tipo é EntryMethod e compara o nome
		if(p instanceof EntryMethod && p.name.equals(x))
		{
			EntryMethod t = (EntryMethod) p;
			// compara os parametros
			if(t.param == null)
			{
				if(r == null)  return t;
			}
			else
			{
				if(t.param.equals(r))
					return t;
			}
		}
		p = p.next;		// proxima entrada
	}
	return null; // não achou
}

public void VarCheckMethodDeclListNode(ListNode x)
{
	if(x == null) return;
	try {
		VarCheckMethodDeclNode( (MethodDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	VarCheckMethodDeclListNode(x.next);
}

public void VarCheckMethodDeclNode(MethodDeclNode x) throws SemanticException
{
EntryMethod c;
EntryRec r = null;
EntryTable e;
ListNode p;
VarDeclNode q;
VarNode u;
int n;

	if(x == null) return;
	p = x.body.param;
	n = 0;
	while(p != null)				// para cada parametro do metodo
	{
		n++;
		q = (VarDeclNode) p.node;	// q = no da declaracao do parametro	
		
		u = (VarNode) p.node;		// u = no com o nome e dimensao
		// acha a entrada na tabela do tipo
		e = Curtable.classFindUp(q.position.image);
		// se nao achou, ERRO
		if(e == null)
			throw new SemanticException(q.position, "Class " +
							q.position.image + " nao encontrada");
		// constroi lista de tipos dos parametros
		r = new EntryRec(e, u.dim, n, r);
		p = p.next;
	}
	if(r != null)
		r = r.inverte();			// inverte a lista
	
	// procura na tabela o tipo de retorno do metodo
	e = Curtable.classFindUp(x.position.image);
	if(e == null)
		throw new SemanticException(x.position, "Class " +
						x.position.image + " nao encontrada");
	
	// procura metodo na tabela, dentro da mesma classe
	c = Curtable.methodFindInclass(x.name.image, r);
	if(c == null)
	{// se nao achou, insere
		c = new EntryMethod(x.name.image, e, x.dim, r);
		Curtable.add(c);
	}
	else	// metodo jaz deifinido na mesma classe, ERRO
		throw new SemanticException(x.position, "Method " +
							x.name.image + "(" + (r == null? "" : r.toString())
							+ ")" + " ja declarado");
}
}
