import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
class Filme{

    //atributos privados
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

    //primeiro construtor
    Filme(){
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

    //Segundo construtor, agora recebendo argumentos
    Filme(String nome, String tituloOriginal, Date dataDeLancamento, int duracao, String genero, String idiomaOriginal, String situacao, float orcamento, String palavrasChave[]){
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

    //set de stributos

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

    //get de atributos

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

    //removendo tags html
    public String removeTags(String line){
        String nova="";
        int i = 0;
        while(i<line.length()){
            if(line.charAt(i)== '<'){
                i++;
                while(line.charAt(i) != '>')i++;
            }else{
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


    //transformando o array de duração em um inteiro de minutos
    public int duracaoToInt(String s){
        String aux[];
        String minuto = "";
        int minutosTotais;

        if(s.contains("h")){
            aux = s.split("h");
            minuto = aux[1].substring(0, aux[1].length() - 1).trim();
            minutosTotais = (Integer.parseInt(aux[0].trim()) * 60) + (Integer.parseInt(minuto));
        }else{
            minuto += s.trim();
            minuto = minuto.replace("m", " ");
            
            minutosTotais = Integer.parseInt(minuto.trim());
        }

        // return minutosTotais;
        return minutosTotais;
    }


    //lendo o array ate achar um parenteses
    public String ateParenteses(String line){
        String nova = "";
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) != '('){
                nova+=line.charAt(i);
            } else { 
                return nova;
            }
        }
        return nova;
        
    }
    //Leitor
    public void ler(String nomeArquivo) throws Exception{
        InputStreamReader fr = new FileReader(nomeArquivo);
        BufferedReader br = new  BufferedReader(fr);
        String linha = br.readLine();


        //Lendo o nome
        while(!(linha=br.readLine()).contains("<title>"));
        this.nome = ateParenteses(removeTags(linha).trim());


        //Lendo a data de lançamento
        
        while(!(linha=br.readLine()).contains("span class=\"release\""));
        linha = br.readLine();
        this.dataDeLancamento = sdf.parse(ateParenteses(removeTags(linha).trim()));


        //Lendo os generos 
        while(!(linha=br.readLine()).contains("genres"));
        br.readLine();
        linha = br.readLine();
        linha = removeTags(linha.trim());
        String auxi[];
        auxi = linha.split(" ");
        this.genero = palavraToStringNoSpace(auxi);


        //Lendo a duração
        while(!(linha=br.readLine()).contains("runtime"));
        br.readLine();
        linha = br.readLine();
        this.duracao = duracaoToInt(removeTags(linha).trim());


        //Lendo o Titulo Original

        while(!(linha=br.readLine()).contains("<section class=\"facts left_column\">"));
        while(!(linha=br.readLine()).contains("<strong><bdi>Situação</bdi></strong>")){
            if((linha=br.readLine()).contains("Título original")){
                this.tituloOriginal = removeTags(linha).trim();
            }
        }

        if(tituloOriginal == ""){
            this.tituloOriginal = nome;
        }
        
        
        // while(!(linha=br.readLine()).contains("Título original"));
        // this.tituloOriginal = removeTags(linha).trim();
        

        //lendo a situação
        //while(!(linha=br.readLine()).contains("Situação</bdi>"));
        this.situacao = removeTags(linha).trim();

        //Lendo o idioma
        while(!(linha=br.readLine()).contains("Idioma original"));
        this.idiomaOriginal = removeTags(linha).trim();

        
        //Lendo o orçamento
        while(!(linha=br.readLine()).contains("Orçamento"));
        if(linha.contains("<p><strong><bdi>Orçamento</bdi></strong> -</p>")){
            linha = removeTags(linha).trim();
            this.orcamento = 0;
        }else{
        this.orcamento = Float.parseFloat(removeTags(linha).trim());
        }

        //Lendo as palavras-chave
        //e verificando se nao tem nenhuma
        while(!(linha=br.readLine()).contains("Palavras-chave"));
        linha = "";
        br.readLine();
        linha=br.readLine();
        if(linha.contains("Nenhuma palavra-chave foi adicionada.")){
            this.palavrasChave = removeTags(linha).trim().split("--");
        }else{
            while(!(linha+=br.readLine().trim()+"-").contains("</ul>"));
            this.palavrasChave = removeTags(linha).trim().split("--");
        }
        

        br.close();
    }

    //convertendo as palavras chave para uma unica string
    public String palavraToString(String s[]){
        String aux = "";
        int i = 0;
        if(s[0].contains("Nenhuma palavra-chave foi adicionada.")){
            aux = "";
        }else{
            for(; i < s.length - 2; i++){
                aux+=s[i];
                aux+=  ", ";
            }
            aux+=s[i];
        }
        // for(; i < s.length - 2; i++){
        //     aux+=s[i];
        //     aux+=  ", ";
        // }
        // aux+=s[i];
        return aux.replace("-", "");
    }

    public String palavraToStringNoSpace(String s[]){
        String aux = "";
        int i = 0;
        if(s[0].contains("Nenhuma palavra-chave foi adicionada.")){
            aux = "";
        }else{
            for(; i < s.length - 1; i++){
                aux+=s[i];
                aux+=  ",";
            }
            aux+=s[i];
        }
        // for(; i < s.length - 2; i++){
        //     aux+=s[i];
        //     aux+=  ", ";
        // }
        // aux+=s[i];
        return aux.replace("-", "");
    }

    //clone
    public Filme clone(){
        Filme resp = new Filme();
        resp.nome = this.nome;
        resp.tituloOriginal = this.tituloOriginal;
        resp.dataDeLancamento = this.dataDeLancamento;
        resp.duracao = this.duracao;
        resp.genero = this.genero;
        resp.idiomaOriginal= this.idiomaOriginal;
        resp.situacao = this.situacao;
        resp.orcamento = this.orcamento;
        resp.palavrasChave = this.palavrasChave;
        return resp;
    }

    //imprimir
    public void Imprimir(){
        MyIO.println(this.nome + (this.tituloOriginal).trim() + " " + sdf.format(this.dataDeLancamento) + " " + this.duracao + " " + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento  + " [" + palavraToString(this.palavrasChave) + "]");
    }
    

    
}

/**
 * Lista estatica
 * @author Max do Val Machado
 * @version 2 01/2015
 */
class Pilha {
    private Filme[] array;
    private int n;

    public int getN(){
        return this.n;
    }
 
    /**
     * Construtor da classe.
     */
    public Pilha () {
       this(6);
    }
 
 
    /**
     * Construtor da classe.
     * @param tamanho Tamanho da lista.
     */
    public Pilha (int tamanho){
       array = new Filme[tamanho];
       n = 0;
    }
 
 
    /**
     * Insere um elemento na primeira posicao da lista e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    // public void inserirInicio(Filme x) throws Exception {
 
    //    //validar insercao
    //    if(n >= array.length){
    //       throw new Exception("Erro ao inserir!");
    //    } 
 
    //    //levar elementos para o fim do array
    //    for(int i = n; i > 0; i--){
    //       array[i] = array[i-1];
    //    }
 
    //    array[0] = x;
    //    n++;
    // }
 
 
    /**
     * Insere um elemento na ultima posicao da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserir(Filme x) throws Exception {
 
       //validar insercao
       if(n >= array.length){
          throw new Exception("Erro ao inserir!");
       }
 
       array[n] = x;
       n++;
    }
 
 
    /**
     * Insere um elemento em uma posicao especifica e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @param pos Posicao de insercao.
     * @throws Exception Se a lista estiver cheia ou a posicao invalida.
     */
    // public void inserir(Filme x, int pos) throws Exception {
 
    //    //validar insercao
    //    if(n >= array.length || pos < 0 || pos > n){
    //       throw new Exception("Erro ao inserir!");
    //    }
 
    //    //levar elementos para o fim do array
    //    for(int i = n; i > pos; i--){
    //       array[i] = array[i-1];
    //    }
 
    //    array[pos] = x;
    //    n++;
    // }
 
 
    /**
     * Remove um elemento da primeira posicao da lista e movimenta 
     * os demais elementos para o inicio da mesma.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    // public Filme removerInicio() throws Exception {
 
    //    //validar remocao
    //    if (n == 0) {
    //       throw new Exception("Erro ao remover!");
    //    }
 
    //    Filme resp = array[0];
    //    n--;
 
    //    for(int i = 0; i < n; i++){
    //       array[i] = array[i+1];
    //    }
 
    //    return resp;
    // }
 
 
    /**
     * Remove um elemento da ultima posicao da lista.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Filme remover() throws Exception {
 
       //validar remocao
       if (n == 0) {
          throw new Exception("Erro ao remover!");
       }
 
       return array[--n];
    }
 
 
    /**
     * Remove um elemento de uma posicao especifica da lista e 
     * movimenta os demais elementos para o inicio da mesma.
     * @param pos Posicao de remocao.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
     */
    // public Filme remover(int pos) throws Exception {
 
    //    //validar remocao
    //    if (n == 0 || pos < 0 || pos >= n) {
    //       throw new Exception("Erro ao remover!");
    //    }
 
    //    Filme resp = array[pos];
    //    n--;
 
    //    for(int i = pos; i < n; i++){
    //       array[i] = array[i+1];
    //    }
 
    //    return resp;
    // }
 
 
    /**
     * Mostra os elementos da lista separados por espacos.
     */
    public void mostrar(){
       System.out.print("[ ");
       for(int i = 0; i < n; i++){
          System.out.print(array[i] + " ");
       }
       System.out.println("]");
    }
 
 
    /**
     * Procura um elemento e retorna se ele existe.
     * @param x int elemento a ser pesquisado.
     * @return <code>true</code> se o array existir,
     * <code>false</code> em caso contrario.
     */

    public int comparacoes = 0;
    public boolean pesquisaSequencial(String nomeP){
        boolean resp = false;
        for(int i = 0; i < n; i++){
            // System.out.println(entrada[i].getNome());
            // System.out.println(nomeP);
            if(((array[i].getNome()).trim()).equals(nomeP) == true){
                resp = true;
                i = n;
            }else{
                resp = false;
            }
        }
        return resp;
    }

    public boolean pesquisaBinaria(String nomeP){
        boolean resp = false;
        int dir = n, esq = 0, meio;
        
        while(esq < dir){
            meio = (esq + dir) / 2;
            
            // System.out.println(array[meio].getNome());
            // System.out.println(nomeP);
            if(nomeP.equals((array[meio].getNome()).trim()) == true){
                resp = true;
                esq = dir;
            }else if(nomeP.compareTo((array[meio].getNome()).trim()) > 0){ 
                esq = meio + 1;
            } else{
                dir = meio - 1;
            }
        }
        return resp;
    }

    public void imprimirA(){
        for(int i = 0; i < n; i++){
            array[i].Imprimir();
            
        }
    }

    //seleção
    public void selectionSort() {
        for (int i = 0; i < (n - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < n; j++){
               if (array[menor].getTituloOriginal().compareTo(array[j].getTituloOriginal()) > 0 ){ 
                 TP03Q08.contador++; 
                 menor = j;
               }
            }
            swap(menor, i);
         }
     }

     //inserçao
     public void insertionSort() {
        for (int i = 1; i < n; i++) {
            Filme tmp = array[i];
            int j = i - 1;
            while ((j >= 0) && (array[j].getDataDeLancamento().compareTo(tmp.getDataDeLancamento()) > 0)) {
                TP03Q08.contador++;
                array[j + 1] = array[j];
                j--;
            } 
            while ((j >= 0) && (array[j].getDataDeLancamento().compareTo(tmp.getDataDeLancamento()) == 0) && (array[j].getNome().compareTo(tmp.getNome()) > 0)) {
                array[j + 1] = array[j];
                TP03Q08.contador++;
                j--;
            } 
            TP03Q08.contador++;
            array[j + 1] = tmp;
        }
     }

     //heap
     public void heapSort() {
        // Alterar o vetor ignorando a posicao zero
        Filme[] tmp = new Filme[n + 1];
        for (int i = 0; i < n; i++) {
            TP03Q08.contador++;
            tmp[i + 1] = array[i];
        }
        array = tmp;

        // Contrucao do heap
        for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
            TP03Q08.contador++;
            construir(tamHeap);
        }

        // Ordenacao propriamente dita
        int tamHeap = n;
        while (tamHeap > 1) {
            TP03Q08.contador++;
            swap(1, tamHeap--);
            reconstruir(tamHeap);
        }

        // Alterar o vetor para voltar a posicao zero
        tmp = array;
        array = new Filme[n];
        for (int i = 0; i < n; i++) {
            TP03Q08.contador++;
            array[i] = tmp[i + 1];
        }
    }

    public void construir(int tamHeap) {
        for (int i = tamHeap; i > 1 && ((array[i].getGenero().compareTo(array[i / 2].getGenero()) > 0) || ((array[i].getGenero().compareTo(array[i / 2].getGenero()) == 0)&& (array[i].getNome().compareTo(array[i / 2].getNome()) > 0))); i /= 2) { 
            TP03Q08.contador++;
            swap(i, i / 2);
        }
    }

    public void reconstruir(int tamHeap) {
        int i = 1;
        while (i <= (tamHeap / 2)) {
            int filho = getMaiorFilho(i, tamHeap);
            if (array[i].getGenero().compareTo(array[filho].getGenero()) < 0) {
                TP03Q08.contador++;
                swap(i, filho);
                i = filho;
            } else {
                TP03Q08.contador++;
                i = tamHeap;
            }
        }
    }

    public int getMaiorFilho(int i, int tamHeap) {
        int filho;
        if (2 * i == tamHeap || array[2 * i].getGenero().compareTo(array[2 * i + 1].getGenero()) > 0) {
            TP03Q08.contador++;
            filho = 2 * i;
        }else {
            TP03Q08.contador++;
            filho = 2 * i + 1;
        }
        return filho;
    }

    public void swap(int i, int primeiro) {
        Filme aux = array[i];
        TP03Q08.contador++;
        array[i] = array[primeiro];
        array[primeiro] = aux;
    }

    public void quicksort() {
        quicksort(0, n - 1);
    }

    private void quicksort(int esq, int dir) {      
        int i = esq, j = dir;
        Filme pivo = array[(dir + esq) / 2];
        while (i <= j) {
            TP03Q08.contador++;
            while ((array[i].getSituacao().compareTo(pivo.getSituacao()) < 0) || ((array[i].getSituacao().compareTo(pivo.getSituacao()) == 0) && (array[i].getNome().compareTo(pivo.getNome()) < 0)))
                i++;
            while ((array[j].getSituacao().compareTo(pivo.getSituacao()) > 0) || ((array[j].getSituacao().compareTo(pivo.getSituacao()) == 0) && (array[j].getNome().compareTo(pivo.getNome()) > 0)))
                j--;
            
            if (i <= j) {
                TP03Q08.contador++;
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j){
            TP03Q08.contador++;
            quicksort(esq, j);
        }
        if (i < dir){
            TP03Q08.contador++;
            quicksort(i, dir);
        }
        
        
    }

    public void bubbleSort() {
		for (int i = (n - 1); i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (array[j].getDuracao() > array[j + 1].getDuracao()) { //colocar um ou aqui
                    TP03Q08.contador++;
                    swap(j, j+1);
				}
                if ((array[j].getDuracao() == array[j + 1].getDuracao()) && (array[j].getNome().compareTo(array[j+1].getNome()) > 0)) { //colocar um ou aqui
                    TP03Q08.contador++;
                    swap(j, j+1);
				}
			}
		}
   }

 }




public class TP03Q08{

    public static int contador = 0; 

    public static long now(){
        return new Date().getTime();
    }

    public static boolean isFim(String s) {
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception{
        String[] input = new String[1000];
        int numInput = 0;
        long inicio=0, fim=0;
        double diferenca = 0.0;
        MyIO.setCharset("UTF-8");


        inicio = now();

        OutputStream os = new FileOutputStream("matricula-bolha.txt"); // nome do arquivo que será escrito
        Writer wr = new OutputStreamWriter(os); // criação de um escritor
        BufferedWriter br = new BufferedWriter(wr); // adiciono a um escritor de buffer

        do{
            input[numInput] = MyIO.readLine();
        }while(isFim(input[numInput++]) == false);
        numInput--;//Desconsiderar a palavra FIM
            
        Pilha pilha = new Pilha(100);
    
        //lendo o arquivo dos filmes
        for(int i = 0; i < numInput;i++){
            Filme aux = new Filme();
            aux.ler("/tmp/filmes/"+input[i]);
            pilha.inserir(aux);
            // filmes[i].Imprimir();
        }

        pilha.bubbleSort();
        pilha.imprimirA();
        fim = now();
        diferenca = (fim - inicio) / 1000.0;

        br.write("750077" + "\t" + diferenca + "\t" + pilha.comparacoes);

        br.close();

    }

}