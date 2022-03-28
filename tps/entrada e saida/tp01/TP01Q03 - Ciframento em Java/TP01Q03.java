public class TP01Q03 {

    // Condição de parada da leitura
    public static boolean isFim(String s){  
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // Ciframento da string
    public static String ciframento(String s){
        String resp = "";
        for(int i = 0; i < s.length(); i++){
            resp = resp + (char)(s.charAt(i) + 3);
        }
        return resp;
    }                                           
    

    public static void main(String[] args) {
        //Leitor da entrada e criador da saida
        String[] entrada = new String[1000];
        int numEntrada = 0;

        do{
            entrada[numEntrada] = MyIO.readLine();
        } while(isFim(entrada[numEntrada++]) == false); numEntrada--;

        for(int i = 0; i < numEntrada; i++){
            MyIO.println(ciframento(entrada[i]));
        }
        
    }                                   

    
}