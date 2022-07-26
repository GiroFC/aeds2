import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

class Filme {

    // atributos privados
    private String nome;
    private String tituloOriginal;
    private Date dataDeLancamento;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String palavrasChave[];

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // primeiro construtor
    Filme() {
        nome = "";
        tituloOriginal = "";
        dataDeLancamento = null;
        duracao = 0;
        genero = "";
        idiomaOriginal = "";
        situacao = "";
        orcamento = 0;
        palavrasChave = null;
    }

    // Segundo construtor, agora recebendo argumentos
    Filme(String nome, String tituloOriginal, Date dataDeLancamento, int duracao, String genero, String idiomaOriginal,
            String situacao, float orcamento, String palavrasChave[]) {
        this.nome = nome;
        this.tituloOriginal = tituloOriginal;
        this.dataDeLancamento = dataDeLancamento;
        this.duracao = duracao;
        this.genero = genero;
        this.idiomaOriginal = idiomaOriginal;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavrasChave = palavrasChave;
    }

    // set de stributos

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public void setDataDeLancamento(Date dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public void setPalavrasChave(String palavrasChave[]) {
        this.palavrasChave = palavrasChave;
    }

    // get de atributos

    public String getNome() {
        return nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public Date getDataDeLancamento() {
        return dataDeLancamento;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public String getSituacao() {
        return situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    // removendo tags html
    public String removeTags(String line) {
        String nova = "";
        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>')
                    i++;
            } else {
                nova += line.charAt(i);
            }
            i++;
        }
        nova = nova.replace("&nbsp;", " ");
        nova = nova.replace("Título original", " ");
        nova = nova.replace("Idioma original", " ");
        nova = nova.replace("Situação", " ");
        nova = nova.replace("(BR)", " ");
        nova = nova.replace("Orçamento", " ");
        nova = nova.replace("$", " ");
        nova = nova.replace(",", "");

        return nova;
    }

    // transformando o array de duração em um inteiro de minutos
    public int duracaoToInt(String s) {
        String aux[];
        String minuto = "";
        int minutosTotais;

        if (s.contains("h") && s.contains("m")) {
            aux = s.split("h");
            minuto = aux[1].substring(0, aux[1].length() - 1).trim();
            minutosTotais = (Integer.parseInt(aux[0].trim()) * 60) + (Integer.parseInt(minuto));
        } else if (s.contains("m") == false) {
            s = s.replace("h", " ");
            minutosTotais = ((Integer.parseInt(s.trim())) * 60);
        } else {
            minuto += s.trim();
            minuto = minuto.replace("m", " ");

            minutosTotais = Integer.parseInt(minuto.trim());
        }

        // return minutosTotais;
        return minutosTotais;
    }

    // lendo o array ate achar um parenteses
    public String ateParenteses(String line) {
        String nova = "";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '(') {
                nova += line.charAt(i);
            } else {
                return nova;
            }
        }
        return nova;

    }

    // Leitor
    public void ler(String nomeArquivo) throws Exception {
        InputStreamReader fr = new FileReader(nomeArquivo);
        BufferedReader br = new BufferedReader(fr);
        String linha = br.readLine();

        // Lendo o nome
        while (!(linha = br.readLine()).contains("<title>"))
            ;
        this.nome = ateParenteses(removeTags(linha).trim());

        // Lendo a data de lançamento

        while (!(linha = br.readLine()).contains("span class=\"release\""))
            ;
        linha = br.readLine();
        this.dataDeLancamento = sdf.parse(ateParenteses(removeTags(linha).trim()));

        // Lendo os generos
        while (!(linha = br.readLine()).contains("genres"))
            ;
        br.readLine();
        linha = br.readLine();
        linha = removeTags(linha.trim());
        String auxi[];
        auxi = linha.split(" ");
        this.genero = palavraToStringNoSpace(auxi);

        // Lendo a duração
        while (!(linha = br.readLine()).contains("runtime"))
            ;
        br.readLine();
        linha = br.readLine();
        this.duracao = duracaoToInt(removeTags(linha).trim());

        // Lendo o Titulo Original

        while (!(linha = br.readLine()).contains("<section class=\"facts left_column\">"))
            ;
        while (!(linha = br.readLine()).contains("<strong><bdi>Situação</bdi></strong>")) {
            if ((linha = br.readLine()).contains("Título original")) {
                this.tituloOriginal = removeTags(linha).trim();
            }
        }

        if (tituloOriginal == "") {
            this.tituloOriginal = nome;
        }

        // while(!(linha=br.readLine()).contains("Título original"));
        // this.tituloOriginal = removeTags(linha).trim();

        // lendo a situação
        // while(!(linha=br.readLine()).contains("Situação</bdi>"));
        this.situacao = removeTags(linha).trim();

        // Lendo o idioma
        while (!(linha = br.readLine()).contains("Idioma original"))
            ;
        this.idiomaOriginal = removeTags(linha).trim();

        // Lendo o orçamento
        while (!(linha = br.readLine()).contains("Orçamento"))
            ;
        if (linha.contains("<p><strong><bdi>Orçamento</bdi></strong> -</p>")) {
            linha = removeTags(linha).trim();
            this.orcamento = 0;
        } else {
            this.orcamento = Float.parseFloat(removeTags(linha).trim());
        }

        // Lendo as palavras-chave
        // e verificando se nao tem nenhuma
        while (!(linha = br.readLine()).contains("Palavras-chave"))
            ;
        linha = "";
        br.readLine();
        linha = br.readLine();
        if (linha.contains("Nenhuma palavra-chave foi adicionada.")) {
            this.palavrasChave = removeTags(linha).trim().split("--");
        } else {
            while (!(linha += br.readLine().trim() + "-").contains("</ul>"))
                ;
            this.palavrasChave = removeTags(linha).trim().split("--");
        }

        br.close();
    }

    // convertendo as palavras chave para uma unica string
    public String palavraToString(String s[]) {
        String aux = "";
        int i = 0;
        if (s[0].contains("Nenhuma palavra-chave foi adicionada.")) {
            aux = "";
        } else {
            for (; i < s.length - 2; i++) {
                aux += s[i];
                aux += ", ";
            }
            aux += s[i];
        }
        // for(; i < s.length - 2; i++){
        // aux+=s[i];
        // aux+= ", ";
        // }
        // aux+=s[i];
        return aux.replace("-", "");
    }

    public String palavraToStringNoSpace(String s[]) {
        String aux = "";
        int i = 0;
        if (s[0].contains("Nenhuma palavra-chave foi adicionada.")) {
            aux = "";
        } else {
            for (; i < s.length - 1; i++) {
                aux += s[i];
                aux += ",";
            }
            aux += s[i];
        }
        // for(; i < s.length - 2; i++){
        // aux+=s[i];
        // aux+= ", ";
        // }
        // aux+=s[i];
        return aux.replace("-", "");
    }

    // clone
    public Filme clone() {
        Filme resp = new Filme();
        resp.nome = this.nome;
        resp.tituloOriginal = this.tituloOriginal;
        resp.dataDeLancamento = this.dataDeLancamento;
        resp.duracao = this.duracao;
        resp.genero = this.genero;
        resp.idiomaOriginal = this.idiomaOriginal;
        resp.situacao = this.situacao;
        resp.orcamento = this.orcamento;
        resp.palavrasChave = this.palavrasChave;
        return resp;
    }

    // imprimir
    public void Imprimir() {
        MyIO.println(this.nome + this.tituloOriginal + " " + sdf.format(this.dataDeLancamento) + " " + this.duracao
                + " " + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento + " ["
                + palavraToString(this.palavrasChave) + "]");
    }

}

class NoAN {
    public boolean cor;
    public Filme elemento;
    public NoAN esq, dir;

    public NoAN() {
        this(null);
    }

    public NoAN(Filme elemento) {
        this(elemento, false, null, null);
    }

    public NoAN(Filme elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public NoAN(Filme elemento, boolean cor, NoAN esq, NoAN dir) {
        this.cor = cor;
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class Alvinegra {
    private NoAN raiz; // Raiz da arvore.

    /**
     * Construtor da classe.
     */
    public Alvinegra() {
        raiz = null;
    }

    /**
     * Metodo publico iterativo para pesquisar elemento.
     * 
     * @param elemento Elemento que sera procurado.
     * @return <code>true</code> se o elemento existir,
     *         <code>false</code> em caso contrario.
     */
    public boolean pesquisar(String elemento) {
        return pesquisar(elemento, raiz);
    }

    /**
     * Metodo privado recursivo para pesquisar elemento.
     * 
     * @param elemento Elemento que sera procurado.
     * @param i        NoAN em analise.
     * @return <code>true</code> se o elemento existir,
     *         <code>false</code> em caso contrario.
     */
    private boolean pesquisar(String elemento, NoAN i) {
        boolean resp;
        if (i == null) {
            System.out.println(" NAO");
            TP04Q04.contador++;
            resp = false;

        } else if (elemento.compareTo(i.elemento.getTituloOriginal()) == 0) {
            System.out.println(" SIM");
            TP04Q04.contador++;
            resp = true;

        } else if (elemento.compareTo(i.elemento.getTituloOriginal()) < 0) {
            System.out.print(" esq");
            resp = pesquisar(elemento, i.esq);
            TP04Q04.contador++;

        } else {
            System.out.print(" dir");
            resp = pesquisar(elemento, i.dir);
            TP04Q04.contador++;
        }
        return resp;
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i NoAN em analise.
     */
    private void caminharCentral(NoAN i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
            caminharCentral(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPre() {
        System.out.print("[ ");
        caminharPre(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i NoAN em analise.
     */
    private void caminharPre(NoAN i) {
        if (i != null) {
            System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
            caminharPre(i.esq); // Elementos da esquerda.
            caminharPre(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPos() {
        System.out.print("[ ");
        caminharPos(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i NoAN em analise.
     */
    private void caminharPos(NoAN i) {
        if (i != null) {
            caminharPos(i.esq); // Elementos da esquerda.
            caminharPos(i.dir); // Elementos da direita.
            System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
        }
    }

    /**
     * Metodo publico iterativo para inserir elemento.
     * 
     * @param elemento Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */
    public void inserir(Filme elemento){

        // Se a arvore estiver vazia
        if (raiz == null) {
            raiz = new NoAN(elemento);
            //System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento + ").");

            // Senao, se a arvore tiver um elemento
        } else if (raiz.esq == null && raiz.dir == null) {
            if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new NoAN(elemento);
                //System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e esq(" + raiz.esq.elemento + ").");
            } else {
                raiz.dir = new NoAN(elemento);
                //System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } else if (raiz.esq == null) {

            if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new NoAN(elemento);
                TP04Q04.contador++;
                //System.out.println("Antes, dois elementos(A). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

            } else if (elemento.getTituloOriginal().compareTo(raiz.dir.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new NoAN(raiz.elemento);
                TP04Q04.contador++;
                raiz.elemento = elemento;
                //System.out.println("Antes, dois elementos(B). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

            } else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                TP04Q04.contador++;
                raiz.dir.elemento = elemento;
                //System.out.println("Antes, dois elementos(C). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }

            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } else if (raiz.dir == null) {

            if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) > 0) {
                raiz.dir = new NoAN(elemento);
                //System.out.println("Antes, dois elementos(D). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            } else if (elemento.getTituloOriginal().compareTo(raiz.esq.elemento.getTituloOriginal()) > 0) {
                raiz.dir = new NoAN(raiz.elemento);
                TP04Q04.contador++;
                raiz.elemento = elemento;
                //System.out.println("Antes, dois elementos(E). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            } else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                TP04Q04.contador++;
                raiz.esq.elemento = elemento;
                //System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento + "), esq ("+ raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }

            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } else {
            //System.out.println("Arvore com tres ou mais elementos...");
            inserir(elemento, null, null, null, raiz);
        }

        raiz.cor = false;
    }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {

        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {

            // 4 tipos de reequilibrios e acoplamento
            if (pai.elemento.getTituloOriginal().compareTo(avo.elemento.getTituloOriginal()) > 0) { // rotacao a esquerda ou direita-esquerda
                if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) > 0) {
                    avo = rotacaoEsq(avo);
                    TP04Q04.contador++;
                } else {
                    avo = rotacaoDirEsq(avo);
                    TP04Q04.contador++;
                }

            } else { // rotacao a direita ou esquerda-direita
                if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
                    avo = rotacaoDir(avo);
                    TP04Q04.contador++;
                } else {
                    avo = rotacaoEsqDir(avo);
                    TP04Q04.contador++;
                }
            }

            if (bisavo == null) {
                raiz = avo;
                TP04Q04.contador++;
            } else if (avo.elemento.getTituloOriginal().compareTo(bisavo.elemento.getTituloOriginal()) < 0) {
                bisavo.esq = avo;
                TP04Q04.contador++;
            } else {
                bisavo.dir = avo;
                TP04Q04.contador++;
            }

            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
            //System.out.println("Reestabeler cores: avo(" + avo.elemento + "->branco) e avo.esq / avo.dir("+ avo.esq.elemento + "," + avo.dir.elemento + "-> pretos)");
        } // if(pai.cor == true)
    }

    /**
     * Metodo privado recursivo para inserir elemento.
     * 
     * @param elemento Elemento a ser inserido.
     * @param avo      NoAN em analise.
     * @param pai      NoAN em analise.
     * @param i        NoAN em analise.
     * @throws Exception Se o elemento existir.
     */
    private void inserir(Filme elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        if (i == null) {

            if (elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
                i = pai.esq = new NoAN(elemento, true);
                TP04Q04.contador++;
            } else {
                i = pai.dir = new NoAN(elemento, true);
                TP04Q04.contador++;
            }

            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
                TP04Q04.contador++;
            }

        } else {

            // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                    TP04Q04.contador++;
                }
            }

            if (elemento.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) < 0) {
                inserir(elemento, avo, pai, i, i.esq);
                TP04Q04.contador++;
            } else if (elemento.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) > 0) {
                inserir(elemento, avo, pai, i, i.dir);
                TP04Q04.contador++;
            } else {
                System.out.println("Erro inserir (elemento repetido)!");
            }
        }
    }

    private NoAN rotacaoDir(NoAN no) {
        // System.out.println("Rotacao DIR(" + no.elemento + ")");
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;
        TP04Q04.contador++;
        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        // System.out.println("Rotacao ESQ(" + no.elemento + ")");
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;
        TP04Q04.contador++;
        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        TP04Q04.contador++;
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        TP04Q04.contador++;
        return rotacaoDir(no);
    }
}

public class TP04Q04 {

    public static int contador = 0;

    public static long now() {
        return new Date().getTime();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception {
        String[] input = new String[1000];
        int numInput = 0;
        MyIO.setCharset("iso-8859-1");
        


        do {
            input[numInput] = MyIO.readLine();
        } while (isFim(input[numInput++]) == false);
        numInput--;// Desconsiderar a palavra FIM

        Alvinegra arvore = new Alvinegra();

        // lendo o arquivo dos filmes
        for (int i = 0; i < numInput; i++) {
            Filme aux = new Filme();
            aux.ler("/tmp/filmes/" + input[i]);
            arvore.inserir(aux);
            // filmes[i].Imprimir();
        }

        //lendo o nome para a verificação
        // int n = MyIO.readInt();
        // String comando;
        // String comandoP = "";
        long inicio=0, fim=0;
        double diferenca=0;
        // String pos = "";

        // for (int i = 0; i < n; i++) {
        //     comando = MyIO.readLine();
        //     comandoP += comando.charAt(0);
        //     // System.out.println(comandoP);
        //     // separador = comando.split(" ");
        //     comando = comando.trim();
        //     comando = comando.substring(1);
        //     comando = comando.trim();
        //     // System.out.println(comando);

        //     Filme aux = new Filme();

        //     if (comandoP.equals("I") == true) {
        //         aux.ler("/tmp/filmes/" + comando);
        //         arvore.inserir(aux);
        //     } else if (comandoP.equals("R") == true) {
        //         // aux.ler("../tmp/filmes/" + comando);
        //         arvore.remover(comando);
        //     }

        //     // pos = "";
        //     comandoP = "";
        // }

        String[] input2 = new String[1000];
        int numInput2 = 0;
        do {
            input2[numInput2] = MyIO.readLine();
        } while (isFim(input2[numInput2++]) == false);
        numInput2--;// Desconsiderar a palavra FIM

        for (int i = 0; i < (numInput2); i++) {
            System.out.println(input2[i]);
            System.out.print("raiz");
            arvore.pesquisar(input2[i]);
            TP04Q04.contador++;
        }

        fim = now();
        diferenca = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("750077_arvoreAlvinegra.txt", "rw");

        Arq.writeChars("750077" + "\t" + diferenca + "\t" + TP04Q04.contador);

        Arq.close();

    }
}

