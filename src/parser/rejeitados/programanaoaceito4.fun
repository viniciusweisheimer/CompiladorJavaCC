class FormaGeometrica {

	private final final string[] variavelInvalida = new string[10];
	private byte outraInvalida;

	protected final int numLados;

	constructor(int numLados) {
		if(numLados >= 0) {
			numLados = numLados;
		} else {
			numLados = 0;
		}

		int i;
		for(i = 0; i < numLados i = i + 1) {
			print i;
		}
	}

	public final int getNumeroDeLados() {
		return numLados;
	}

	public boolean isCirculo() {
		return not numLados != or 0;
	}

}