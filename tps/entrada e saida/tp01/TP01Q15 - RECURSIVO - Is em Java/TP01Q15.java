public class TP01Q15 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // todas as mesamas funções da questao 06, agora recursivo

    public static String isVogal(String s, int i, int contador) {
        String z = s.toLowerCase();
        if (i < z.length()) {
            if (z.charAt(i) == 'a' || z.charAt(i) == 'e' || z.charAt(i) == 'i' || z.charAt(i) == 'o'
                    || z.charAt(i) == 'u') {
                return isVogal(s, i + 1, contador + 1);
            } else {
                return isVogal(s, i + 1, contador);
            }
        } else {
            if (contador == z.length()) {
                return "SIM";
            } else {
                return "NAO";
            }
        }

    }

    public static String isConsoante(String s, int i, int contador) {
        String z = s.toLowerCase();
        if (i < s.length()) {
            if (!(z.charAt(i) > 47 && z.charAt(i) < 58)) {
                if (z.charAt(i) == 'a' || z.charAt(i) == 'e' || z.charAt(i) == 'i' || z.charAt(i) == 'o'|| z.charAt(i) == 'u') {
                    return isConsoante(s, i + 1, contador);
                } else {
                    return isConsoante(s, i + 1, contador + 1);
                }
            } else {
                if (contador == z.length()) {
                    return "SIM";
                } else {
                    return "NAO";
                }
            }
        } else {
            return "NAO";
        }
    }

    public static String isInteiro(String s, int i, int contador) {
        if (i < s.length()) {
            if ((int) s.charAt(i) > 47 && (int) s.charAt(i) < 58) {
                return isInteiro(s, i + 1, contador + 1);
            } else {
                return isInteiro(s, i + 1, contador);
            }
        } else {
            if (contador == s.length()) {
                return "SIM";
            } else {
                return "NAO";
            }
        }

    }

    public static String isReal(String s, int i, int contador) {
        if (i < s.length()) {
            if ((int) s.charAt(i) > 47 && (int) s.charAt(i) < 58 || (int) s.charAt(i) == 46) {
                return isReal(s, i + 1, contador + 1);
            } else {
                return isReal(s, i + 1, contador);
            }
        } else {
            if (contador == s.length()) {
                return "SIM";
            } else {
                return "NAO";
            }
        }

    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int numEntrada = 0;

        // Leitura entrada, e saida
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; 

        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(isVogal(entrada[i], 0, 0) + " " +  isConsoante(entrada[i], 0, 0) + " " +  isInteiro(entrada[i], 0, 0)+ " " +  isReal(entrada[i], 0, 0));
        }
    }
}