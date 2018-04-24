public class Success extends ErrorClass{
	
	/* Declaracao variaveis, usando tipos e
	* reconhecimento de comentários multiline
	* int
	* float
	* string
	* char
	* boolean
	*/
	int inteiroGlobal = 1;
	string stringGlobal = "string";
	char caracterGlobal = "a";
	boolean booleanoGlobal = true;
	float floatGlobal = 0.10;
	
	
	// Construtor com XOR,NOT,AND,OR e comentario singleline
	constructor(boolean paramBool, int paramInt){
		Success success = new Success();
		super(paramInt,paramBool);
		boolean outroBoleano = NOT paramBool OR (paramInt == globalInt);
		boolean resultBoolean = paramBool XOR outroBoleano AND paramBool;
	}
	
	public int init () {
		int i = -1;
		float floatInterno = -2.10;
		
		return i;
	}
	
	public int funcao (){
		
		for(int i = 0, i<5, i++) {
			if(intGlobal <= i){
				if(intGlobal != i){
					i = i*i;
					i = i/1;
					i = i%2;
					int const = null;
					i--;
				}
				break;
			} else {
				i++;
			}
		}
		byte teclado = (byte) System.in.read();
		System.out.print("oi");
		
		
		return i;
	}
	
	
}