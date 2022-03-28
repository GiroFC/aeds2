import java.util.*;

public class TP01Q04 {

    // Condição de parada da leitura
    public static boolean isFim(String s){  
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // Gerador de minuscula
    public static String letraAleatoria(Random gerador, String s){
        String resp = "";

        char letraUm = (char)('a' + Math.abs(gerador.nextInt()) % 26);
        char letraDois = (char)('a' + Math.abs(gerador.nextInt()) % 26);

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == letraUm){
                resp = resp + (char)letraDois;
            } else {
                resp = resp + (char)(s.charAt(i));
            }
        }

        return resp;
    }           
    

    public static void main(String[] args) {
        //Leitor da entrada e criador da saida
        Random gerador = new Random();
        gerador.setSeed(4);
        String[] entrada = new String[1000];
        int numEntrada = 0;

        do{
            entrada[numEntrada] = MyIO.readLine();
        } while(isFim(entrada[numEntrada++]) == false); numEntrada--;

        for(int i = 0; i < numEntrada; i++){
            MyIO.println(letraAleatoria(gerador, entrada[i]));
        }
        
    }                                   

    
}
