class Circulo extends FormaGeometrica {

	private final Quadrado quadrado = new Quadrado();
	double variavelInvalida variavelInvalida = 10.5;
	public final byte umByte = 10b, outroByte = 0x34Ac;

	constructor() {
		super(1 % 1);
		quadrado.umMetodoQualquer(100l);
		this.quadrado.umMetodoQualquer(10l);
	}

	public void umMetodoQualquer(int argumento1, char argumento2) {
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