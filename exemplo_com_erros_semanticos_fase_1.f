/* FormaGeometrica */
class FormaGeometrica {

	 string[] uma_variavel_sem_sentido = new string[10];

	  int num_lados;

	constructor(int numLados) {
		if(numLados >= 0) {
			num_lados = numLados;
		} else {
			num_lados = 2o;
		}

		int i;
		for(i = 0; i < num_lados; i = i + 1) {
			print i;
		}
	}

	  int getNumLados() {
		return num_lados;
	}

	 boolean isCirculo() {
		return not num_lados != 0;
	}

	 int fazAlgoSemSentido(boolean argumento) {
		boolean umaVariavelLocalSemSentido = true or false;
		do {
			print "ola";

			// por favor, use argumento = true para nao dar loop ;)
			umaVariavelLocalSemSentido = "teste" and not argumento;
		}(umaVariavelLocalSemSentido);
		
		// ERRO: metodo deveria retornar int ao inves de string
		return "abc";
	}

}

class FormaGeometrica {}

/* Quadrado */
class Quadrado extends FormaGeometrica {

	constructor() {
		// ERRO: divisao nao pode ser feita com uma string
		super("oi" / 4);
		fazOutraCoisaSemSentido(4l);
		super.fazAlgoSemSentido(true);
	}

	 int fazOutraCoisaSemSentido(long argumento) {
		// ERRO: expressao aritmetica invalida
		double umaVariavelLocalSemSentido = 2d * "ccccc";

		read um_short_sem_sentido;

		byte outraVariavelSemSentido = 0b;
		
		// ERRO: um dos lados da expressao nao eh boolean
		while(true xor 0)
			// ERRO: metodo espera boolean, nao int
			fazAlgoSemSentido(500);
		return 0;
	}

}

/* Circulo */
class Circulo extends FormaGeometrica {

	  Quadrado um_quadrado_sem_sentido = new Quadrado();

	double um_double_sem_sentido = 10.5 * -1d;

	  byte um_byte = 10b, outro_byte = 0x34, mais_um_byte = 2, um_outro_byte_sem_sentido = 1o;

	constructor() {
		super(1 % 1);
		
		int umInt = 110010b, outroInt = 0x345F, maisUmInt = 234523, umOutroInt = 43o;
		
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(100l, 50);
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(10l);
	}

	 int fazOutraCoisaSemSentido(int argumento1, char argumento2) {
		if(argumento2 == null) {
			// ERRO: variavel nao eh um array
			argumento2[50] = 'c';
		}

}

class Triangulo extends Quadrado2 {}

class Quadrado2 extends Triangulo {}
