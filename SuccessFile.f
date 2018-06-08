class SuccessClass extends ErrorClass{
	
	/* Declaracao variaveis, usando tipos e
	* reconhecimento de coment�rios multiline
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
//	constructor(boolean paramBool, int paramInt){
//		Success success = new Success();
//		super(paramInt,paramBool);
//		boolean outroBoleano = NOT paramBool OR (paramInt == globalInt);
//		boolean resultBoolean = paramBool XOR outroBoleano AND paramBool;
//	}
	
	
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
		//print("oi");
	
		return i;
	}	
}

class SuccessClass2 extends ErrorClass{
	
	/* Declaracao variaveis, usando tipos e
	* reconhecimento de coment�rios multiline
	* int
	* float
	* string
	* char
	* boolean
	*/

	// Construtor com XOR,NOT,AND,OR e comentario singleline
	constructor(boolean paramBool, int paramInt){
		int teste = 1;
		string stringGlobal = "string";
		char caracterGlobal = a;
		boolean booleanoGlobal = "true";
		float floatGlobal = 1.10;
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
		print oi;
	
		return i;
	}
}

class Person {

	 string first_name;
	 string last_name;
	 int age;
	 float height;
	 float weight;
	 byte makes_no_sense_here;
	 char gender;

	constructor () {
		first_name = "default";
	}

	constructor(string firstName, string lastName, int age, float height, float weight, string str_gender) {
		firt_name = firstName;
		last_name = lastName;
		age = age;
		height = height;
		weight = weight;

		if(NOT str_gender == "male") {
			gender = "f";
		} else {
			gender = "m";
		}

	}

}

class User extends Person {

	string roles = new string[50];
	string username;
	string password;

	boolean isAdmin;

	constructor(string name, string pass, boolean isAdmin) {

		super();

		if(isAdmin) {
			username = "ADM" + name;
		} else {
			username = "USER" + name;
		}

		password = pass;
	}

	boolean isCommonUser() {
		return NOT isAdmin;
	}

	boolean authenticateUser(string user, string pass) {
		if(username == user AND password == pass)
			return true;
		else
			return false;
	}

	string hasRole(string role) {

		int i;

		string result = null;

		for(i = 0; i < roles.length; i = i + 1) {

			print i;
			print roles[i];

			if(roles[i] == role) {
 				result = roles[i];
 				break;
			}
		}
	}

	void executeTask() {

		nonSenseClass = new NonSenseClass(1.3, true);

		nonSenseClass.unuselessMethod(10, "Padrão");

	}

}

class NonSenseClass {

	byte global_byte_var;
	float global_float_var = 0.0;
	float global_short_var = 15;

	constructor(float float_arg, boolean bool_arg) {

		int desc_count = 50;

		this.unuselessMethod(15, "default");
	}

	void unuselessMethod(int int_arg, string string_arg) {

		read string_arg;

	}
	
	constructor(float float_arg, boolean bool_arg) {

		int desc_count = 50;

		this.unuselessMethod(15, "default");
	}

}