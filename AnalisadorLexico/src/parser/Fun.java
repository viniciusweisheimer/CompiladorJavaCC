/* Generated By:JavaCC: Do not edit this line. Fun.java */
package parser;

public class Fun implements FunConstants {

boolean Menosshort = false;

  public static void main(String args []) throws ParseException
  {
    String filename = ""; // nome do arquivo a ser analisado;
    Fun parser;
    int i = 0;
    boolean minusshort = false;
    
    // Lê os parametros passados ao compilador
    while( i < args.length -1) {
    	if(args[i].toLowerCase().equals("-short")) {
    		minusshort = true;
    	} else {
    		System.out.println("Usage is: java Fun [-short] inputfile");
    		System.exit(0);
    	}
    }
    
    if (args[i].equals("-")) {
    	// Lê Stdin
    	System.out.println("Lendo da entrada padrao");
    	parser = new Fun(System.in);
    }
    else
    {
    	// Lê do arquivo passado
    	filename = args[args.length-1];
    	System.out.println("Lendo do arquivo " + filename +"");
    	try {
    		parser = new Fun(new java.io.FileInputStream(filename));
    	}
    	catch (java.io.FileNotFoundException e) {
    		System.out.println("Arquivo" + filename + " nao encontrado");
    		return;
    	}
    }
    parser.Menosshort = minusshort;
    parser.program();
    if(parser.token_source.foundLexError() != 0 ) // verifica erros lexicos
    	System.out.println(parser.token_source.foundLexError() + 
    			"Erros lexicos encontrados");
    	else
    		System.out.println("Programa analizado");
  }

  static public String image(int x) {
	int k;
	String s;
		s = tokenImage[x];
		k = s.lastIndexOf("\u005c"");
		try {
			s = s.substring(1, k);
		} catch (StringIndexOutOfBoundsException e) {}
	  return s;
  }
  
  void program() throws ParseException {
	  Token t;
	  do {
		  t = getNextToken();
		  Token st = t;
		  while( st.specialToken != null)
			  st = st.specialToken;
		  do {
			  if (Menosshort)
				  System.out.println(st.image + "  " +
                          image(st.kind) + " " +
                          st.kind);
			  	else
			  		System.out.println("Line: " + st.beginLine +
                          " Column: " + st.beginColumn +
                          " " + st.image +
                          "  " + image(st.kind) + "  "+t.kind);
			  st = st.next;
		  } while (st != t.next);
	  } while (t.kind != FunConstants.EOF);
  }

  final public int one_line() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
    case MINUS:
    case LPAREN:
      sum();
      jj_consume_token(SEMICOLON);
    {if (true) return 0;}
      break;
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
    {if (true) return 1;}
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public void sum() throws ParseException {
    term();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      term();
    }
  }

  final public void term() throws ParseException {
    unary();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
        jj_consume_token(MULTIPLY);
        break;
      case DIVIDE:
        jj_consume_token(DIVIDE);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      unary();
    }
  }

  final public void unary() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      element();
      break;
    case CONSTANT:
    case LPAREN:
      element();
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void element() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
      jj_consume_token(CONSTANT);
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      sum();
      jj_consume_token(RPAREN);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  /** Generated Token Manager. */
  public FunTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x4000,0x0,0x0,0x0,0x0,0x4000,0x4000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x41010,0x18,0x18,0x60,0x60,0x1010,0x1000,};
   }

  /** Constructor with InputStream. */
  public Fun(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Fun(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new FunTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Fun(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new FunTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Fun(FunTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(FunTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[64];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 64; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
