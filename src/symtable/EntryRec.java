package symtable;

// lista de EntryClass usada para representar os tipos de uma lista de paramentros

public class EntryRec extends EntryTable {
public EntryTable type;			// tipo de um objeto
public int dim;					// dimensao
public EntryRec next;			// apontador para o resto da lista
public int cont;				// numero de elementos a partir do elemento
public boolean opt;				// parametro ou metodo opcional

// cria elemento
public EntryRec(EntryTable p, int d, int c, boolean o)
{
	type = p;
	cont = c;
	dim = d;
	next = null;
	opt = o;
}

// cria elemento e poe no inicio da lista
public EntryRec(EntryTable p, int d, int c, EntryRec t, boolean o)
{
	type = p;
	cont = c;
	dim = d;
	next = t;
	opt = o;
}
}
