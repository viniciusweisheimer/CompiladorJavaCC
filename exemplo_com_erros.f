/* FormaGeometrica */
class FormaGeometrica {

	// ERRO: token "final" duplicado
	private final final string[] uma_variavel_sem_sentido = new string[10];
	// private final string[] uma_variavel_sem_sentido = new string[10];
	private byte outra_variavel_sem_sentido;

	protected final int num_lados;

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

	public final int getNumLados() {
		return num_lados;
	}

	public boolean isCirculo() {
		// ERRO: o token "or" nao deveria estar ali
		return not num_lados != or 0;
		// return not num_lados != 0;
	}

	protected void fazAlgoSemSentido(boolean argumento) {
		boolean umaVariavelLocalSemSentido = true;
		do {
			// ERRO: print espera um valor para imprimir
			print ;
			// print "ola";

			// por favor, use argumento = true para nao dar loop :P
			umaVariavelLocalSemSentido = umaVariavelLocalSemSentido and not argumento;
		}(umaVariavelLocalSemSentido);
	}

}

/* Quadrado */
class Quadrado extends FormaGeometrica {

	private short outra_variavel_sem_sentido;

	constructor() {
		super(16 / 4);
		this.fazOutraCoisaSemSentido(4l);
		super.fazAlgoSemSentido(true);
	}

	// ERRO: foi removido o token "void", entao o nome do metodo eh desconhecido
	public fazOutraCoisaSemSentido(long argumento) {
	// public void fazOutraCoisaSemSentido(long argumento) {
		double umaVariavelLocalSemSentido = 2d * -5;

		read outra_variavel_sem_sentido;

		byte outraVariavelSemSentido = 0b;
		while(true xor outraVariavelSemSentido)
			fazAlgoSemSentido(true);
	}

}

/* Circulo */
class Circulo extends FormaGeometrica {

	private final Quadrado um_quadrado_sem_sentido = new Quadrado();

	// ERRO: nao ha virgulas separando a declaracao de variaveis
	double uma_variavel_que_nao_deveria_estar_aqui outra_variavel_sem_sentido = 10.5;
	// double outra_variavel_sem_sentido = 10.5;

	public final byte um_byte = 10b, outro_byte = 0x34Ac;

	constructor() {
		super(1 % 1);
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(100l);
		this.um_quadrado_sem_sentido.fazOutraCoisaSemSentido(10l);
	}

	public void fazOutraCoisaSemSentido(int argumento1, char argumento2) {
		// ERRO: switch espera apenas um <IDENT>
		switch (argumento1 argumento2) {
			case 0: {
				print "da 0 pra ele";
				break;
			}

			case 6:
				print "eu contei 6";
				break;

			case 10:
				print "mas vc eh 10!";
				break;

			default:
				print argumento2;
				break;
		}
	}

}
