import java.util.*;

public class TP01Q06 {

    // Condição de parada da leitura
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // verficador de vogal
    public static String isVogal(String s){
        String b = s.toLowerCase();
        boolean resp = false;

        for(int i = 0; i < b.length(); i++){
            if((int)b.charAt(i) > 64 && (int)b.charAt(i) < 123){
                if(b.charAt(i) != 'a' || b.charAt(i) != 'e' || b.charAt(i) != 'i' || b.charAt(i) != 'o' 
            || b.charAt(i) != 'u'){
                i = b.length();
                resp = false;
            } else {
                resp = true;
            } 

            } else{
                resp = false;
            }
        }
        //retornador
        if (resp == true){
            return "SIM";
        } else {
            return "NAO";
        }
    }


    // verificador de consoante
    public static String isConsoante(String s){
        String b = s.toLowerCase();
        boolean resp = false;

        for(int i = 0; i < b.length(); i++){
            if((int)b.charAt(i) > 64 && (int)b.charAt(i) < 123){
                if(b.charAt(i) == 'a' || b.charAt(i) == 'e' || b.charAt(i) == 'i' || b.charAt(i) == 'o' 
            || b.charAt(i) == 'u'){
                i = b.length();
                resp = false;
            } else {
                resp = true;
            }

            } else{
                resp = false;
            }
        }
        
        //retornador
        if (resp == true){
            return "SIM";
        } else {
            return "NAO";
        }
    }

    public static String isInteiro(String s){
        boolean resp = false;

        for(int i = 0; i < s.length(); i++){
            if((int) s.charAt(i) > 47 && (int) s.charAt(i) < 58){
                resp = true;
            } else {
                resp = false;
                i = s.length();
            } 
        }
        //retornador
        if (resp == true){
            return "SIM";
        } else {
            return "NAO";
        }
    }

    public static String isReal (String s){
        boolean resp = false;
        for(int i =0; i < s.length(); i++){
            if((int)s.charAt(i) > 47 && (int)s.charAt(i) < 58 || s.charAt(i) == 46){
                resp = true;
            } else {
                resp = false;
                i = s.length();
            }
        }
        // retornador
        if(resp == true){
           return "SIM";
        } else {
            return "NAO";
        }
        
    }

    public static void main(String[] args) {
        // Leitor da entrada e criador da saida

        String[] entrada = new String[1000];
        int numEntrada = 0;

        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;

        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(isVogal(entrada[i]) + " " + isConsoante(entrada[i]) + " " 
            + isInteiro(entrada[i]) + " " + isReal(entrada[i]) );
        }

    }

}
