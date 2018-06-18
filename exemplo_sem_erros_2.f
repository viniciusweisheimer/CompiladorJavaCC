/* FormaGeometrica */
class FormaGeometrica {

	private final string[] uma_variavel_sem_sentido = new string[10];
	private byte outra_variavel_sem_sentido;

	protected final int num_lados;

	constructor(int numLados) {
		if(numLados >= 0) {
			num_lados = numLados;
		} else {
			num_lados = 0;
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

	protected void fazAlgoSemSentido(boolean argumento) {
		boolean umaVariavelLocalSemSentido = true;
		do {
			print "ola";

			// por favor, use argumento = true para nao dar loop :P
			umaVariavelLocalSemSentido = umaVariavelLocalSemSentido and not argumento;
		}(umaVariavelLocalSemSentido);
	}

}
