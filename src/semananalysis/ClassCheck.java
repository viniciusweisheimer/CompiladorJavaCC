package semananalysis;

import symtable.*;
import syntaticTree.*;

public class ClassCheck {
Symtable Maintable;				//table de mais alto nivel
protected Symtable Curtable;	// apontador para a tabela corrente

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
	Curtable = Maintable			// tabela corrente = principal
	ClassCheckClassDeclListNode(x);	// chama analise para raiz da arvore
	if (foundSemanticError != 0)	// se houve erro, lanca excecao
		throw new SemanticException(foundSemanticError +
				" Erro semantico encontrado (fase 1)");
}

public void ClassCheckClassDeclListNode(ListNode x)
{
	if (x == null) return;
	try {
		ClassCheckClassDeclNode((ClassDeclNode) x.node);
	} catch (SemanticException e) 
	{/* se um erro ocorreu na analise da classe,
	 	* dá a mensagem, mas faz a analise para proxiuma classe
	 	*/
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	ClassCheckClassDeclListNode(x.next);
}

public void ClassCheckClassDeclNode(ClassDeclNode x)
{
Symtable temphold = Curtable; // salva apontador p/ tabela corrente
EntryClass nc;

	if(x == null) return;
	// procura classe na tabela
	nc = (EntryClass) Curtable.classFindUp(x.name.image);
	{
		throw new SemanticException(x.name,
				"Class "+ x.name.image + " already declared");
	}
	
	// inclui classe na tabela corrente
	Curtable.add(nc = new EntryClass(x.name.image, Curtable));
	Curtable = nc.nested; // tabela corrente = tabela da classe
	ClassCheckClassBodyNode(x.body);
	Curtable = temphold;	// recupera apontador p/ tabela corrente
}

public EntryTable classFindUp(String x)
{
EntryTable p = top;

	// para cada elemento da tabela corrente
	while(p != null)
	{
		// verifica se e uma entrada de classe ou tipo simples
		// e entao compara o nome
		if( ((p instanceof EntryClass) || (p instanceof EntrySimple)) && p.name.equals(x))
			return p;
	}
	if (levelup == null)	// se nao achou e e o nivel mais externo
		return null;		
	
	// procura no nivel mais externo
	return levelup.mytable.classFindUp(x);
	
}

public void ClassCheckClassBodyNode(ClassBodyNode x)
{
	if(x == null) return;
	ClassCheckClassDeclListNode(x.clist);
}
}
