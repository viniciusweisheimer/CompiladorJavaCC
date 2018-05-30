class SuccessClass extends ErrorClass{
	
	/* Declaracao variaveis, usando tipos e
	* reconhecimento de comentários multiline
	* int
	* float
	* string
	* char
	* boolean
	*/

	int teste = 1;
	string stringGlobal = "string";
	char caracterGlobal = a;
	boolean booleanoGlobal = "true";
	float floatGlobal = 1.10;
	
		
	// Construtor com XOR,NOT,AND,OR e comentario singleline
	constructor(boolean paramBool, int paramInt){
		Success success = new Success();
		super(paramInt,paramBool);
		boolean outroBoleano = NOT paramBool OR (paramInt == globalInt);
		boolean resultBoolean = paramBool XOR outroBoleano AND paramBool;
	}
	
	int init () {
		int i = -1;
		//float floatInterno = -2.10;
		
		return i;
	}
	
	int funcao (){
		int i;
		for(i = 0; i<5; i = i+1) {
			if(intGlobal <= i){
				if(intGlobal != i){
					i = i*i;
					i = i/1;
					i = i%2;
					int const = null;
					i = i-1;
				}
				break;
			} else {
				i = i+1;
			}
		}
		print("oi");
	
		return i;
	}
	
	
}