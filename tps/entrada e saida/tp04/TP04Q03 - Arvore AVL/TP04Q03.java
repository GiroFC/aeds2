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

class No {
    public Filme elemento; // Conteudo do no.
    public No esq, dir; // Filhos da esq e dir.
    public int nivel; // Numero de niveis abaixo do no

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     */
    public No(Filme elemento) {
        this(elemento, null, null, 1);
    }

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     * @param esq      No da esquerda.
     * @param dir      No da direita.
     */
    public No(Filme elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }

    /**
     * Cálculo do número de níveis a partir de um vértice
     */
    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }

    /**
     * Retorna o número de níveis a partir de um vértice
     * 
     * @param no nó que se deseja o nível.
     */
    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }
}

class AVL {
    private No raiz; // Raiz da arvore.

    /**
     * Construtor da classe.
     */
    public AVL() {
        raiz = null;
    }

    /**
     * Metodo publico iterativo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @return <code>true</code> se o elemento existir, <code>false</code> em caso
     *         contrario.
     */
    public boolean pesquisar(String x) {
        return pesquisar(x, raiz);
    }

    /**
     * Metodo privado recursivo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @param i No em analise.
     * @return <code>true</code> se o elemento existir, <code>false</code> em caso
     *         contrario.
     */
    private boolean pesquisar(String x, No i) {
        boolean resp;
        if (i == null) {
            System.out.println(" NAO");
            resp = false;

        } else if (x.compareTo(i.elemento.getTituloOriginal()) == 0) {
            System.out.println(" SIM");
            TP04Q03.contador++;
            resp = true;

        } else if (x.compareTo(i.elemento.getTituloOriginal()) < 0) {
            System.out.print(" esq");
            TP04Q03.contador++;
            resp = pesquisar(x, i.esq);

        } else {
            System.out.print(" dir");
            TP04Q03.contador++;
            resp = pesquisar(x, i.dir);
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
     * @param i No em analise.
     */
    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            System.out.print(i.elemento + " "); // Conteudo do no.
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
     * @param i No em analise.
     */
    private void caminharPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + "(fator " + (No.getNivel(i.dir) - No.getNivel(i.esq)) + ") "); // Conteudo do
                                                                                                         // no.
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
     * @param i No em analise.
     */
    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq); // Elementos da esquerda.
            caminharPos(i.dir); // Elementos da direita.
            System.out.print(i.elemento + " "); // Conteudo do no.
        }
    }

    /**
     * Metodo publico iterativo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */
    public void inserir(Filme x) {
        raiz = inserir(x, raiz);
    }

    /**
     * Metodo privado recursivo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se o elemento existir.
     */
    private No inserir(Filme x, No i) {
        if (i == null) {
            i = new No(x);

        } else if (x.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) < 0) {
            i.esq = inserir(x, i.esq);
            TP04Q03.contador++;
        } else if (x.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) > 0) {
            i.dir = inserir(x, i.dir);
            TP04Q03.contador++;
        } else {
            System.out.println("Erro ao inserir!");
        }

        return balancear(i);
    }

    /**
     * Metodo publico iterativo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @throws Exception Se nao encontrar elemento.
     */
    public void remover(String x) {
        raiz = remover(x, raiz);
    }

    /**
     * Metodo privado recursivo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se nao encontrar elemento.
     */
    private No remover(String x, No i) {

        if (i == null) {
            System.out.println("Erro ao remover!");
            TP04Q03.contador++;
        } else if (x.compareTo(i.elemento.getTituloOriginal()) < 0) {
            i.esq = remover(x, i.esq);
            TP04Q03.contador++;
        } else if (x.compareTo(i.elemento.getTituloOriginal()) > 0) {
            i.dir = remover(x, i.dir);
            TP04Q03.contador++;
            // Sem no a direita.
        } else if (i.dir == null) {
            i = i.esq;
            TP04Q03.contador++;
            // Sem no a esquerda.
        } else if (i.esq == null) {
            i = i.dir;
            TP04Q03.contador++;
            // No a esquerda e no a direita.
        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return balancear(i);
    }

    /**
     * Metodo para trocar o elemento "removido" pelo maior da esquerda.
     * 
     * @param i No que teve o elemento removido.
     * @param j No da subarvore esquerda.
     * @return No em analise, alterado ou nao.
     */
    private No maiorEsq(No i, No j) {

        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
            TP04Q03.contador++;
            // Existe no a direita.
        } else {
            // Caminha para direita.
            TP04Q03.contador++;
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No balancear(No no) {
        if (no != null) {
            int fator = No.getNivel(no.dir) - no.getNivel(no.esq);

            // Se balanceada
            if (Math.abs(fator) <= 1) {
                no.setNivel();
                TP04Q03.contador++;
                // Se desbalanceada para a direita
            } else if (fator == 2) {

                int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);

                // Se o filho a direita tambem estiver desbalanceado
                if (fatorFilhoDir == -1) {
                    no.dir = rotacionarDir(no.dir);
                    TP04Q03.contador++;
                }
                no = rotacionarEsq(no);

                // Se desbalanceada para a esquerda
            } else if (fator == -2) {

                int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);

                // Se o filho a esquerda tambem estiver desbalanceado
                if (fatorFilhoEsq == 1) {
                    no.esq = rotacionarEsq(no.esq);
                    TP04Q03.contador++;
                }
                no = rotacionarDir(no);

            } else {
                System.out.println("Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
            }
        }

        return no;
    }

    private No rotacionarDir(No no) {
        //System.out.println("Rotacionar DIR(" + no.elemento + ")");
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        no.setNivel(); // Atualizar o nivel do no
        noEsq.setNivel(); // Atualizar o nivel do noEsq

        return noEsq;
    }

    private No rotacionarEsq(No no) {
        //System.out.println("Rotacionar ESQ(" + no.elemento + ")");
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel(); // Atualizar o nivel do no
        noDir.setNivel(); // Atualizar o nivel do noDir
        return noDir;
    }
}

public class TP04Q03 {

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

        AVL arvore = new AVL();

        // lendo o arquivo dos filmes
        for (int i = 0; i < numInput; i++) {
            Filme aux = new Filme();
            aux.ler("/tmp/filmes/" + input[i]);
            arvore.inserir(aux);
            // filmes[i].Imprimir();
        }

        // lendo o nome para a verificação
        int n = MyIO.readInt();
        String comando;
        String comandoP = "";
        long inicio=0, fim=0;
        double diferenca=0;
        // String pos = "";

        for (int i = 0; i < n; i++) {
            comando = MyIO.readLine();
            comandoP += comando.charAt(0);
            // System.out.println(comandoP);
            // separador = comando.split(" ");
            comando = comando.trim();
            comando = comando.substring(1);
            comando = comando.trim();
            // System.out.println(comando);

            Filme aux = new Filme();

            if (comandoP.equals("I") == true) {
                aux.ler("/tmp/filmes/" + comando);
                arvore.inserir(aux);
            } else if (comandoP.equals("R") == true) {
                // aux.ler("../tmp/filmes/" + comando);
                arvore.remover(comando);
            }

            // pos = "";
            comandoP = "";
        }

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
            TP04Q03.contador++;
        }

        fim = now();
        diferenca = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("750077_arvoreAVL.txt", "rw");

        Arq.writeChars("750077" + "\t" + diferenca + "\t" + TP04Q03.contador);

        Arq.close();

    }
}
