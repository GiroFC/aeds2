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

class Celula {
	public Filme elemento; // Elemento inserido na celula.
	public Celula prox; // Aponta a celula prox.


	/**
	 * Construtor da classe.
	 */
	public Celula() {
		this(null);
	}

	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	public Celula(Filme elemento) {
      this.elemento = elemento;
      this.prox = null;
	}
}

class Lista {
	private Celula primeiro;
	private Celula ultimo;
    private int qt;

	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
        qt = 0;
	}

    public int getQuantity() {
        return qt;
    }
	/**
	 * Insere um elemento na primeira posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirInicio(Filme x) {
		Celula tmp = new Celula(x);
      tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if (primeiro == ultimo) {                 
			ultimo = tmp;
		}
        qt++;
      tmp = null;
	}


	/**
	 * Insere um elemento na ultima posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirFim(Filme x) {
		ultimo.prox = new Celula(x);
		ultimo = ultimo.prox;
        qt++;
	}


	/**
	 * Remove um elemento da primeira posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Filme removerInicio() {
		if (primeiro == ultimo) {
			//throw new Exception("Erro ao remover (vazia)!");
		}

      Celula tmp = primeiro;
		primeiro = primeiro.prox;
		Filme resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
        qt--;
		return resp;
	}


	/**
	 * Remove um elemento da ultima posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Filme removerFim() {
		if (primeiro == ultimo) {
			//throw new Exception("Erro ao remover (vazia)!");
		} 

		// Caminhar ate a penultima celula:
        Celula i;
        for(i = primeiro; i.prox != ultimo; i = i.prox);

        Filme resp = ultimo.elemento; 
        ultimo = i; 
        i = ultimo.prox = null;
        qt--;

	    return resp;
	}


	/**
    * Insere um elemento em uma posicao especifica considerando que o 
    * primeiro elemento valido esta na posicao 0.
    * @param x int elemento a ser inserido.
	 * @param pos int posicao da insercao.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
   public void inserir(Filme x, int pos){

      int tamanho = tamanho();

      if(pos < 0 || pos > tamanho){
			//throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
      } else if (pos == 0){
         inserirInicio(x);
         TP04Q07.contador++;
      } else if (pos == tamanho){
         inserirFim(x);
         TP04Q07.contador++;
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = new Celula(x);
         tmp.prox = i.prox;
         i.prox = tmp;
         tmp = i = null;
         qt++;
      }
   }


	/**
    * Remove um elemento de uma posicao especifica da lista
    * considerando que o primeiro elemento valido esta na posicao 0.
	 * @param posicao Meio da remocao.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
	public Filme remover(int pos) {
      Filme resp = null;
      int tamanho = tamanho();

		if (primeiro == ultimo){
			//throw new Exception("Erro ao remover (vazia)!");

      } else if(pos < 0 || pos >= tamanho){
			//throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
      } else if (pos == 0){
         resp = removerInicio();
         TP04Q07.contador++;
      } else if (pos == tamanho - 1){
         resp = removerFim();
         TP04Q07.contador++;
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = i.prox;
         resp = tmp.elemento;
         i.prox = tmp.prox;
         tmp.prox = null;
         i = tmp = null;
      }
        qt--;
		return resp;
	}

	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar() {
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
        }
	}
    /**
	 * Procura um elemento e retorna se ele existe.
	 * @param x Elemento a pesquisar.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String x) {
		boolean resp = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
         if(i.elemento.getTituloOriginal().compareTo(x) == 0){
            resp = true;
            TP04Q07.contador++;
            i = ultimo;
         }
		}

        if(resp == false) {
            System.out.println("NAO");
            TP04Q07.contador++;
        }

		return resp;
	}

	/**
	 * Calcula e retorna o tamanho, em numero de elementos, da lista.
	 * @return resp int tamanho
	 */
   public int tamanho() {
      int tamanho = 0; 
      for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }

    public Filme getLast() {
        return ultimo.elemento;
    }

    public Filme getFirst() {
        Filme temp = null;
        if (primeiro != ultimo) {
            temp = primeiro.prox.elemento;
            TP04Q07.contador++;
        }
        return temp;
    }

    public Filme getItemAt(int index) {
        Filme resp = null;
        if (index == 1) {
            resp = getFirst();
        } else if (index == qt) {
            resp = getLast();
        } else if ((index > 1 && index < qt) && primeiro != ultimo) {
            Celula temp = primeiro;
            for (int i = 0; i < index; i++, temp = temp.prox)
                ;
                TP04Q07.contador++;
            resp = temp.elemento;
        } else {
            MyIO.println("ERRO - Get Index com indice " + index + " n?o permitido");
        }
        return resp;
    }

}


class HashIndiretoLista {
    Lista tabela[];
    int tamanho;
    final int NULO = -1;
 
    public HashIndiretoLista (){
       this(21);
    }
 
    public HashIndiretoLista (int tamanho){
       this.tamanho = tamanho;
       tabela = new Lista[tamanho];
       for(int i = 0; i < tamanho; i++){
        TP04Q07.contador++;
          tabela[i] = new Lista();
       }
    }
 
    public int h(int elemento){
        TP04Q07.contador++;
       return elemento % tamanho;
    }
 
    boolean pesquisar(String elemento){
        int ascii = 0;
        boolean resp = false;

        for (int i = 0; i < elemento.length(); i++) {
            TP04Q07.contador++;
            ascii = ascii + (int) elemento.charAt(i);
        }

        int pos = h(ascii);
        resp = tabela[pos].pesquisar(elemento);
        if(resp == true){
            System.out.println("Posicao: " + pos);
        }

       return resp;
    }
    
    public int getValorAscii(Filme elemento) {
        int ascii = 0;
        String nome = elemento.getTituloOriginal();

        for (int i = 0; i < nome.length(); i++) {
            TP04Q07.contador++;
            ascii = ascii + (int) nome.charAt(i);
        }

        return ascii;
    }

    public void inserirInicio (Filme elemento){
       int pos = h(getValorAscii(elemento));
       TP04Q07.contador++;
       tabela[pos].inserirInicio(elemento);
    }
 
}

public class TP04Q07 {

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
        


        do {
            input[numInput] = MyIO.readLine();
        } while (isFim(input[numInput++]) == false);
        numInput--;// Desconsiderar a palavra FIM

        HashIndiretoLista hash = new HashIndiretoLista();

        // lendo o arquivo dos filmes
        for (int i = 0; i < numInput; i++) {
            Filme aux = new Filme();
            aux.ler("/tmp/filmes/" + input[i]);
            hash.inserirInicio(aux);
            // filmes[i].Imprimir();
        }

        //lendo o nome para a verifica??o
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
        //         hash.inserir(aux);
        //     } else if (comandoP.equals("R") == true) {
        //         // aux.ler("../tmp/filmes/" + comando);
        //         hash.remover(comando);
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
            System.out.println("=> "+input2[i]);
            hash.pesquisar(input2[i]);
            TP04Q07.contador++;
        }

        fim = now();
        diferenca = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("750077_hashIndireto.txt", "rw");

        Arq.writeChars("750077" + "\t" + diferenca + "\t" + TP04Q07.contador);

        Arq.close();

    }
}



