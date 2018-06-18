/* Circulo */
class Circulo extends FormaGeometrica {

	private final Quadrado um_quadrado_sem_sentido = new Quadrado();

	double outra_variavel_sem_sentido = 10.5;

	public final byte um_byte = 10b, outro_byte = 0x34Ac;

	constructor() {
		super(1 % 1);
		um_quadrado_sem_sentido.fazOutraCoisaSemSentido(100l);
		this.um_quadrado_sem_sentido.fazOutraCoisaSemSentido(10l);
	}

	public void fazOutraCoisaSemSentido(int argumento1, char argumento2) {
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
	}

}
