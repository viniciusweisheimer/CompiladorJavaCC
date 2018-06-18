/* FormaGeometrica */
class FormaGeometrica {

	 byte outra_variavel_sem_sentido;

	  int num_lados;

	constructor(int numLados) {
		if(numLados >= 0) {
			num_lados = numLados;
		} else {
			num_lados = 0;
		}

		int i;
		// ERRO: o comando "for" espera dois ";"
		for(i = 0; i < num_lados i = i + 1) {
		// for(i = 0; i < num_lados; i = i + 1) {
			print i;
		}
	}

	  int getNumLados() {
		return num_lados;
	}

	 boolean isCirculo() {
		// ERRO: o token "or" nao deveria estar ali
		return not num_lados != or 0;
		// return not num_lados != 0;
	}

	 int fazAlgoSemSentido(boolean argumento) {
		boolean umaVariavelLocalSemSentido = true;
		do {
			// ERRO: print espera um valor para imprimir
			print ;
			// print "ola";

			// por favor, use argumento = true para nao dar loop ;)
			umaVariavelLocalSemSentido = umaVariavelLocalSemSentido and not argumento;
		}(umaVariavelLocalSemSentido);
		return 0;
	}

}

/* Quadrado */
class Quadrado extends FormaGeometrica {

	int outra_variavel_sem_sentido;

	constructor() {
		super(16 / 4);
		fazOutraCoisaSemSentido(4l);
		super.fazAlgoSemSentido(true);
	}

	// ERRO: foi removido o token "int", entao o nome do metodo eh desconhecido
	 fazOutraCoisaSemSentido(float argumento) {
	//  void fazOutraCoisaSemSentido(float argumento) {
		float umaVariavelLocalSemSentido = 2d * -5;

		read outra_variavel_sem_sentido;

		byte outraVariavelSemSentido = 0b;
		while(true xor outraVariavelSemSentido)
			fazAlgoSemSentido(true);
		return 0;
	}

}

/* Circulo */
class Circulo extends FormaGeometrica {

	  Quadrado um_quadrado_sem_sentido = new Quadrado();

	// ERRO: nao ha virgulas separando a declaracao de variaveis
	float outra_variavel_sem_sentido uma_variavel_que_nao_deveria_estar_aqui = 10.5;
	// float outra_variavel_sem_sentido = 10.5;

	constructor() {
		super(1 % 1);
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(100f);
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(10f);
	}
}
