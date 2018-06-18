package symtable;

// entrada utilizada para declarar os tipos basico da linguagem

public class EntrySimple extends EntryTable{

public EntrySimple(String n)
{
	name = n;
}

@Override
public String dscJava() {
	if(name.equals("string"))
	{
		return "Ljava/lang/String;"; // classe String JAVA
	}
	return name;
}
}
