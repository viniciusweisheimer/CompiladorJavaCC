package symtable;

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
		top = top.next		// retira todas as vars do aninhamento corrente
	scptr--;				// finaliza aninhamento corrente
}

}
