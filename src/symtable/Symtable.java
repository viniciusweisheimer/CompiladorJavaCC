package symtable;

import syntaticTree.ClassBodyNode;

public class Symtable {
//  apontador para o topo da tabela (mais recente)
public EntryTable top;

// numero que controla o escopo (aninhamento) corrente
public int scptr;

// apontador para a entrada EntryClass de nivel superior
public EntryClass levelup;

public Symtable() // cria uma tabela vazia
{
	top = null;
	scptr = 0;
	levelup = null;
}

// cria tabela vazia apontando para nivel superior
public Symtable(EntryClass up)
{
	top = null;
	scptr = 0;
	levelup = up;
}

public void add(EntryTable x) // adiciona uma entrada a tabela
{
	x.next = top;		// inclui nova entrada no topo
	top = x;
	x.scope = scptr;	// atribui para a entrada o numero do escopo
	x.mytable = this;	// faz a entrada apontar para a propria tabela
}

public void beginScope()
{
	scptr++;		// inicia novo aninhamento de variaveis
}

public void endScope()
{
	while (top != null && top.scope == scptr)
		top = top.next;		// retira todas as vars do aninhamento corrente
	scptr--;				// finaliza aninhamento corrente
}

/* Metodo q procura o simbolo x na tabela e tambem nas tabelas de
 * nivel superior, apontada por levelup. Procura por uma entrada do tipo
 * EtrnyClass ou EntrySimple
 */
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
		
		p = p.next; 		// proxima entrada
	}
	if (levelup == null)	// se nao achou e e o nivel mais externo
		return null;		
	
	// procura no nivel mais externo
	return levelup.mytable.classFindUp(x);
	
}

/* Metodo q procura o simbolo x na tabela e tambem nas tabelas das 
 * supercalsses, apontada por levelup.parent. Procura por uma entrada do
 * tipo EntryVar
 */
public EntryVar varFind(String x) 
{
	return varFind(x,1);
}

/* Esse metodo procura a n-esima ocorrencia do simbolo x na tabela e tambem
na(s) tabela(s) da(s)  superclasse(s), apontada por levelup.parent. Procura
por uma entrada do  tipo EntryVar */
public EntryVar varFind(String x, int n) {
    EntryTable p = top;
    EntryClass q;

    while (p != null) {
        if (p instanceof EntryVar && p.name.equals(x)) {
            if (--n == 0) {
                return (EntryVar) p;
            }
        }

        p = p.next;
    }

    q = levelup;

    if (q.parent == null) {
        return null;
    }

    return q.parent.nested.varFind(x, n);
}

/* Esse metodo procura o simbolo x com uma lista de parametros igual a r na
tabela e tambem na(s) tabela(s) da(s)  superclasse(s), apontada por
levelup.parent. Procura por uma entrada do  tipo EntryMethod */
public EntryMethod methodFind(String x, EntryRec r) {
    EntryTable p = top;
    EntryClass q;

    while (p != null) {
        if (p instanceof EntryMethod && p.name.equals(x)) {
            EntryMethod t = (EntryMethod) p;

            if (t.param == null) {
                if (r == null) {
                    return t;
                }
            } else {
                if (t.param.equals(r)) {
                    return t;
                }
            }
        }

        p = p.next;
    }

    q = levelup;

    if (q.parent == null) {
        return null;
    }

    return q.parent.nested.methodFind(x, r);
}

/* Esse metodo procura o simbolo x com uma lista de parametros igual a r
apenas na tabela, nao na(s) tabela(s) da(s)  superclasse(s),
apontada por levelup.parent. Procura por uma entrada do  tipo EntryMethod */
public EntryMethod methodFindInclass(String x, EntryRec r) {
    EntryTable p = top;
    EntryClass q;

    // para cada entrada da tabela
    while (p != null) {
        // verifica se tipo  EntryMethod e compara o nome
        if (p instanceof EntryMethod && p.name.equals(x)) {
            EntryMethod t = (EntryMethod) p;

            // compara os parmetros
            if (t.param == null) {
                if (r == null) {
                    return t;
                }
            } else {
                if (t.param.equals(r)) {
                    return t;
                }
            }
        }

        p = p.next; // prxima entrada
    }

    return null; // nao achou
}
}
