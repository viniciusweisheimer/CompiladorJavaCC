class Person {

	 string first_name;
	 string last_name;
	 int age;
	 double height;
	 float weight;
	 byte makes_no_sense_here;
	 char gender;

	constructor () {
		first_name = "default";
	}

	constructor(string firstName, string lastName, int age, double height, float weight, string str_gender) {
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
	float global_double_var = 0.0;
	float global_short_var = 15;

	constructor(double double_arg, boolean bool_arg) {

		int desc_count = 50;

		this.unuselessMethod(15, "default");
	}

	void unuselessMethod(int int_arg, string string_arg) {

		read string_arg;

	}

}