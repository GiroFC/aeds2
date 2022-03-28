public class TP01Q01 {
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }                                                                       // Condição de parada da leitura
    public static boolean isPalindromo(String s){
        int tam = s.length();
        Boolean resp = false;
        for(int i = 0; i < tam; i++){
            if(s.charAt(i) == s.charAt(tam - i - 1)){
                resp = true;
            } else{
                resp = false;
                i = s.length();
            }
        }
        return resp;
    }                                           //Verificador de palindromo
    

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int numEntrada = 0;

        do{
            entrada[numEntrada] = MyIO.readLine();
        } while(isFim(entrada[numEntrada++]) == false); numEntrada--;

        for(int i = 0; i < numEntrada; i++){
            if(isPalindromo(entrada[i])){
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        
    }                                   //Leitor da entrada e criador da saida


    
}
