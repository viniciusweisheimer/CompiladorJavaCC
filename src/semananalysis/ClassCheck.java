package semananalysis;

import java.util.Arrays;
import java.util.List;

import symtable.*;
import syntaticTree.*;

public class ClassCheck {
Symtable Maintable;				//table de mais alto nivel
protected Symtable Curtable;	// apontador para a tabela corrente
int foundSemanticError;			// contador de erros encontrados

public ClassCheck() {
	final List<String> types = Arrays.asList(
		"int",
		"string",
		"boolean",
		"byte",
		"char",
		"long",
		"double",
		"short",
		"float");

	Maintable = new Symtable(); // cria tabela principal

	for(final String type : types) {
		Maintable.add(new EntrySimple(type));
	}

	foundSemanticError = 0;
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
