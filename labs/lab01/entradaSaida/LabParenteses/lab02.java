class lab02{

    static boolean verifica(String s){
        int aux = 0;
        for(int i = 0; i < s.length() && aux >=0; i++){
            if(s.charAt(i) == '(')aux++;
            if(s.charAt(i) == ')')aux--;
        }

        return(aux==0);
    }

    public static boolean isFim(String s) {
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
      int numEntrada = 0;

      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (isFim(entrada[numEntrada++]) == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i++){
         if(verifica(entrada[i]) == true){
            MyIO.println("correto");
        } else{
            MyIO.println("incorreto");
        }
      }
    }
}