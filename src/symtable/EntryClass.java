package symtable;

// classe corresponde a uma declaracao de classe na tab. de simbolos
public class EntryClass extends EntryTable {
public Symtable nested;		// tabela p/ declaracao de elementos aninhados
public EntryClass parent;	// entrada correspondente a superclasse

public EntryClass(String n, Symtable t)
{
	name = n;						// nome da classe declarada
	nested = new Symtable(this); 	// tabela onde inserir variaveis metodos ou classes
	parent = null;					// sua superclasse
}

public String completeName() 	// devolve nome completo da classe
{
	String p;
	Symtable t;
	EntryClass up;
	
	t = mytable;
	up = (EntryClass) t.levelup;
	
	if(up == null)
	{
		p = "";		// no uma classe aninhada
	} 
	else
	{
		p = up.completeName() + "$";	// classe aninhada 
	}
	
	return p + name;	// retorna nome nivel superio $ nome da classe
}

public String dscJava() 
{
	return "L" + completeName() + ";";
}
}
