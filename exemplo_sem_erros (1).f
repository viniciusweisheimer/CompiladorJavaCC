/* FormaGeometrica */
class FormaGeometrica {

	private final string[] uma_variavel_sem_sentido = new string[10];
	private byte um_byte_sem_sentido;

	protected final int num_lados;

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

	public final int getNumLados() {
		return num_lados;
	}

	public boolean isCirculo() {
		return not num_lados != 0;
	}

	protected int fazAlgoSemSentido(boolean argumento) {
		boolean umaVariavelLocalSemSentido = true or false;
		do {
			print "ola";

			// por favor, use argumento = true para nao dar loop :P
			umaVariavelLocalSemSentido = umaVariavelLocalSemSentido and not argumento;
		}(umaVariavelLocalSemSentido);
		return 0;
	}

}

/* Quadrado */
class Quadrado extends FormaGeometrica {

	private short um_short_sem_sentido = 123, outro_short_sem_sentido = 0xA, mais_um_short_sem_sentido = 11b, um_outro_short_sem_sentido = 1o;

	constructor() {
		super(16 / 4);
		this.fazOutraCoisaSemSentido(4l);
		super.fazAlgoSemSentido(true);
	}

	public int fazOutraCoisaSemSentido(long argumento) {
		double umaVariavelLocalSemSentido = 2d * -5.6;

		read um_short_sem_sentido;

		byte outraVariavelSemSentido = 0b;
		while(true xor outraVariavelSemSentido > 0)
			fazAlgoSemSentido(true);
		return 0;
	}

}

/* Circulo */
class Circulo extends FormaGeometrica {

	private final Quadrado um_quadrado_sem_sentido = new Quadrado();

	double um_double_sem_sentido = 10.5 * -1d;

	public final byte um_byte = 10b, outro_byte = 0x34, mais_um_byte = 2, um_outro_byte_sem_sentido = 1o;

	constructor() {
		super(1 % 1);
		
		int umInt = 110010b, outroInt = 0x345F, maisUmInt = 234523, umOutroInt = 43o;
		
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(100l);
		this.um_quadrado_sem_sentido.fazOutraCoisaSemSentido(10l);
	}

	public int fazOutraCoisaSemSentido(int argumento1, char argumento2) {
		if(argumento2 == null) {
			argumento2 = 'c';
		}
	
		switch (argumento1) {
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
		return 0;
	}

}
