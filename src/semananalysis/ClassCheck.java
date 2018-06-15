package semananalysis;

import symtable.*;
import syntaticTree.*;

public class ClassCheck {
Symtable Maintable;				//table de mais alto nivel
protected Symtable Curtable;	// apontador para a tabela corrente
int foundSemanticError;			// contador de erros encontrados

public ClassCheck()
{
EntrySimple k;
	
	foundSemanticError = 0;
	Maintable = new Symtable();		// cria tabela principal
	k = new EntrySimple("int");		// insere tipos basicos da linguagem
	Maintable.add(k);
	k = new EntrySimple("string");
	Maintable.add(k);
}

public void ClassCheckRoot(ListNode x) throws SemanticException
{
	Curtable = Maintable;				// tabela corrente = principal
	ClassCheckClassDeclListNode(x);		// chama analise para raiz da arvore
	if (foundSemanticError != 0)		// se houve erro, lanca excecao
		throw new SemanticException(foundSemanticError +
				" Erro semantico encontrado (fase 1)");
}

public void ClassCheckClassDeclListNode(ListNode x) throws SemanticException
{
	if (x == null) return;
	ClassCheckClassDeclNode((ClassDeclNode) x.node);
	ClassCheckClassDeclListNode(x.next);
}

public void ClassCheckClassDeclNode(ClassDeclNode x)
{
Symtable temphold = Curtable; // salva apontador p/ tabela corrente
EntryClass nc;

	if(x == null) return;
	// procura classe na tabela
	nc = (EntryClass) Curtable.classFindUp(x.name.image);
	
	try {
		if (nc != null) // ja declarada, ERRO
		{	
			throw new SemanticException(x.name,
				"Class "+ x.name.image + " ja declarada");
		}
	} catch (SemanticException e) {
		System.out.println(x.name +
				"Class "+ x.name.image + " ja declarada");
	}
	
	// inclui classe na tabela corrente
	Curtable.add(nc = new EntryClass(x.name.image, Curtable));
	Curtable = nc.nested; // tabela corrente = tabela da classe
	ClassCheckClassBodyNode(x.body);
	Curtable = temphold;	// recupera apontador p/ tabela corrente
}


public void ClassCheckClassBodyNode(ClassBodyNode x)
{
	if(x == null) return;
	try {
		ClassCheckClassDeclListNode(x.clist);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
	}
}
}
