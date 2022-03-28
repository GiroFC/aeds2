import java.util.*;

static class leitor {

    private String st;
    private int position;
    private int[] operadores = new int[3];
    private int count = 0;

    public leitor() {
        this.st = "";
        this.position = 0;
        for (int i = 0; i < operadores.length; i++) {
            operadores[i] = 0;
        }
    }

    public void setSt(String s) {
        this.st = s;
    }

    public void setOperadores(int[] array) {
        for (int i = 0; i < 3; i++) {
            this.operadores[i] = array[i];
        }
    }

    public void setPosition(int i) {
        this.position = i;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public int[] getOperadores() {
        return operadores;
    }

    public int getOperadores(int num) {
        return operadores[num];
    }

    public int getPosition() {
        return position;
    }

    public String getSt() {
        return st;
    }

    public int getCount() {
        return count;
    }

    public void posicaoAdd(int i) {
        this.position = position + i;
    }
} // fim interpreter

public class TP01Q05 {

    public static int leitorExpressao(leitor leitor) {
        int aux = 0;
        int result = 0;
        String s = leitor.getSt();
        char c = s.charAt(leitor.getPosition());
        leitor.posicaoAdd(1);
        
        if (c == 'A') {
            result = leitor.getOperadores(0);
        } else if (c == 'B') {
            result = leitor.getOperadores(1);
        } else if (c == 'C') {
            result = leitor.getOperadores(2);
        } else if (c == 'n') {
            leitor.posicaoAdd(3);
            if (leitorExpressao(leitor) == 0) {
                result = 1;
            } else if (leitorExpressao(leitor) == 1) {
                result = 0;
            }
            leitor.posicaoAdd(1);
        } else if (c == 'a') {
            leitor.posicaoAdd(3);
            if (leitorExpressao(leitor) == 0) {
                result = 0;
            } else if (leitorExpressao(leitor) == 1) {
                result = 1;
            }
            while (s.charAt(leitor.getPosition()) == ',') {
                leitor.posicaoAdd(1);
                aux = leitorExpressao(leitor);
                if (result == 0 || aux == 0) {
                    result = 0;
                } else {
                    result = 1;
                }
            }
            leitor.posicaoAdd(1);
        } else if (c == 'o') {
            leitor.posicaoAdd(3);
            if (leitorExpressao(leitor) == 0) {
                result = 0;
            } else if (leitorExpressao(leitor) == 1) {
                result = 1;
            }
            while (s.charAt(leitor.getPosition()) == ',') {
                leitor.posicaoAdd(1);
                aux = leitorExpressao(leitor);
                if (result == 0 || aux == 0) {
                    result = 1;
                } else {
                    result = 0;
                }
            }
            leitor.posicaoAdd(1);
        }

        return result;

    }

    public static boolean isFim(String s){  
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String offSpace(String s) {
        String offEspaco = s;
        return offEspaco.replace(" ", "");
    }

    public static void main(String[] args) {
        
        String[] entrada = new String[1000];
        int numEntrada = 0;

        do{
            entrada[numEntrada] = MyIO.readLine();
        } while(isFim(entrada[numEntrada++]) == false); numEntrada--;

        while (numOperadores != 0) {
            int[] operadores = new int[3];
            for (int j = 0; j < numOperadores; j++) {
                operadores[j] = MyIO.nextInt();
            }
            leitor.setOperadores(operadores);

            // removendo o espaço
            String espaco = "";
            String vazio = "";
            espaco = MyIO.nextLine();
            vazio = offSpace(espaco);
            leitor.setSt(vazio);

            leitor.setPosition(0);

            int resultado = leitorExpressao(leitor);
            System.out.println(resultado);
            numOperadores = MyIO.nextInt();

        }
    }
}
