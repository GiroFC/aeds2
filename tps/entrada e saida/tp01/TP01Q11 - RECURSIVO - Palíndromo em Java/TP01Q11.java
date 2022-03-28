public class TP01Q11 {
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }                                                                       // Condição de parada da leitura
    public static boolean isPalindromo(String s, int cont, int x){
        
        Boolean resp = false;
        if(x < (s.length()/2)){ 
            if (s.charAt(x) == s.charAt(s.length() - x -1)){ 
                return isPalindromo(s, cont + 1, x + 1); // condição recursiva
            } else { 
                return isPalindromo(s, cont, x + 1);
            }
        } 
            
        if (cont == s.length()/2){
            resp = true;
        } else {
            resp = false;
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
            if(isPalindromo(entrada[i], 0, 0)){
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        
    }                                   //Leitor da entrada e criador da saida


    
}

