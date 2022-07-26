import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.*;


class Filmes{
    private String nome;
    private String tituloOriginal;
    private Date dataDeLancamento;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    Filmes(){
    }
    
    Filmes(String nome, String tituloOriginal, Date dataDeLancamento, int duracao, String genero, String idiomaOriginal, String situacao, float orcamento,String[] palavrasChave){
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

    public String getNome(){
          return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTituloOriginal(){
        return this.tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal){
        this.tituloOriginal = tituloOriginal;
    }

    public Date getDataDeLancamento(){
        return this.dataDeLancamento;
    }

    public void setDataDeLancamento(Date dataDeLancamento){
        this.dataDeLancamento = dataDeLancamento;
    }

    public int getDuracao(){
        return this.duracao;
    }

    public void setDuracao(int duracao){
        this.duracao = duracao;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getIdiomaOriginal(){
        return this.idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal){
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getSituacao(){
        return this.situacao;
    }

    public void setSituacao(String situacao){
        this.situacao = situacao;
    }

    public float getOrcamento(){
        return this.orcamento;
    }

    public void setOrcamento(Float orcamento){
        this.orcamento = orcamento;
    }

    public String[] getPalavrasChave(){
		return this.palavrasChave;
    }

    public void setPalavrasChave(String[] palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

    public Filmes clone(){
        Filmes copia = new Filmes();
        copia.nome = nome;
        copia.tituloOriginal = tituloOriginal;
        copia.dataDeLancamento = dataDeLancamento;
        copia.duracao = duracao;
        copia.genero = genero;
        copia.idiomaOriginal = idiomaOriginal;
        copia.situacao = situacao;
        copia.orcamento = orcamento;
        copia.palavrasChave = palavrasChave;
  	return copia;
    
    }

	//	percorre a string procurando o caracter '&' e quando acha ignora ate achar o ';' e salva o restante na String resp;
	//  pensando bem agora, daria pra fazer um simples replace :(

    public String removeCaracter(String str){
	    String resp = "";
	    int i = 0;		
	    while(i < str.length()){
	    	if(str.charAt(i) == '&'){
			while(str.charAt(i) != ';'){
				i++;
			}
		}else {
			resp += str.charAt(i);
		}
	    	i++;
	    }
	    return resp;
    }
    
	// percorre a string salvando os caracteres na nova string limpa até achar o caracter '('
    public String buscaAteParentese(String str){
	    String limpa = "";
	    
	    for(int i = 0; str.charAt(i)!= '(';i++){
	    	limpa += str.charAt(i);
	    }
	    return limpa;
    }	   

	//percorre a string retornando apenas o conteudo que está fora das tags <>
    public String removeTags(String str){
	    String resp = "";
	    int i = 0;
	    while(i <str.length()){
		    if(str.charAt(i) == '<'){
			    while(str.charAt(i) != '>'){
					i++;
				}
		    }else{
		    	resp += str.charAt(i);
		    }
		    i++;
	    }
	    return resp;
    }

    	//recebe uma string contendo o tempo de duração do filme e converte para minutos (inteiro)
	
	public String removeLetras(String str){
		String resp = "";
		for(int i = 0; i < str.length(); i++){
			if((str.charAt(i) >= 48 && str.charAt(i) <= 57) || str.charAt(i) == ' ' || str.charAt(i) == '.'){
				resp += str.charAt(i);
			}
		}
		return resp;
	}

    public int horaToMin(String str){
	    int min  = 0, resp = 0;
		String[] splitValue = str.split(" ");
	    
	    if(splitValue.length > 1){
			
			int hora = Integer.parseInt(removeLetras(splitValue[0]));
			min = Integer.parseInt(removeLetras(splitValue[1]));
			resp = (hora * 60) + min;
	    }else {
			if(splitValue[0].contains("h")){
				min = Integer.parseInt(removeLetras(splitValue[0]))*60;
			}else{
				min = Integer.parseInt(removeLetras(splitValue[0]));
			}
			resp = min;
		}

	   return resp; 
    }
	//percorre todo o arquivo salvando os atributos

    public void ler (String arquivo) throws IOException{
     
	    String caminho = "/tmp/filmes/";
	    BufferedReader br = new BufferedReader(new FileReader(caminho+arquivo));
	    String linha = br.readLine();

		//linha com o nome do filme
	    
	    while(!linha.contains("<title>")){
	   		linha = br.readLine();
	    }        
	    this.nome = buscaAteParentese(removeTags(linha)).trim();

	
		//linha com a data de lançamento
		//trata a linha com a data de lançamento e converte de string pra date
	    	   
	    while(!linha.contains("<span class=\"release\">")){
			linha = br.readLine();
	    }
	    linha = buscaAteParentese(br.readLine().trim());
				
	    try{
	   	   this.dataDeLancamento = formatter.parse(linha);
	    }catch (ParseException e){
	    }
	    
		//linha com o genero do filme

	    while(!linha.contains("/genre")){
		    linha = br.readLine();
	    }

	    this.genero = removeCaracter(removeTags(linha)).trim();
		
		
	   //linha com o tempo do filme
	   
	    while(!linha.contains("runtime")){
		    linha = br.readLine();
	    }
	    br.readLine();
	    linha = br.readLine().trim();
	    this.duracao = horaToMin(linha);
		

	   
 	    //linha com o título original do filme
	    //se o arquivo não possui a tag com título original, atribui o nome do filme ao título original
		
	    while(!linha.contains("<div class=\"social_links\">")){
		    linha = br.readLine();
	    }
		
	    while(!linha.contains("<strong>")){
		    linha = br.readLine();
	    }
	    if(linha.contains("Título original")){
		    linha = removeTags(linha).trim();
		    //linha = linha.replace("Título original", "");
		    this.tituloOriginal = linha.replace("Título original", "").trim();
	    }else{
		    this.tituloOriginal = this.nome;
	    }
		
		    
		//linha com a situação do filme

	    while(!linha.contains("Situação")){
		    linha = br.readLine();
	    }
		
	    linha = removeTags(linha).trim();
	    //linha = linha.replace("Situação","");
	    this.situacao = linha.replace("Situação", "").trim();

	   	//linha com o idioma original do filme

	    while(!linha.contains("Idioma original")){
    		    linha = br.readLine();
	    }
	    linha = removeTags(linha).trim();
	    //linha = linha.replace("Idioma original", "");	
	    this.idiomaOriginal = linha.replace("Idioma original","").trim();	    

	    //linha com o orçamento o filme
	     
        while(!linha.contains("Orçamento")){
	    	linha = br.readLine();
	    }
		linha = removeTags(linha).trim();
		linha = linha.replace("Orçamento","");

		if(linha.contains("-")){
			this.orcamento = Float.parseFloat("0");
		}else{	
			linha = linha.replaceAll("\\$","");
			linha = linha.replaceAll(",","");
			this.orcamento = Float.parseFloat(linha);
		}	    
	    
	   //linha com as palavras chave do filme
	   	    
	    while(!linha.contains("Palavras-chave")){
	    	   linha = br.readLine();
	    }
		String[] palavrasTemp = new String[30];
		int count = 0;

		while(!linha.contains("</section>")){
			linha = br.readLine();
			if(linha.contains("<li>")){
				palavrasTemp[count++] = removeTags(linha).trim();
			}
		}			
		if(count > 0){
			this.palavrasChave = new String[count];
			for(int i = 0; i < count; i++){
				this.palavrasChave[i] = palavrasTemp[i];
			}
		}else{
			this.palavrasChave = new String[1];
			this.palavrasChave[0] = "";	
		}
		br.close();
    	
	}	  

    public void imprimir (){
		MyIO.print(this.nome +" "+this.tituloOriginal + " " + formatter.format(this.dataDeLancamento) + " " + this.duracao + " " + this.genero + " " + this.idiomaOriginal + " "+this.situacao+ " "+ this.orcamento+ " [" +this.palavrasChave[0]);
		for(int i = 1; i < this.palavrasChave.length; i++){
			MyIO.print(", "+this.palavrasChave[i]);
		}
		MyIO.println("]");
    }

    public boolean isFim(String str){
            return (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M');
        }
}

class Lista{

    Filmes[] filmes;
    int n;
    int numComparacoes = 0;
    int numMovimentacoes = 0;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Lista(){
    	this(100);
    }

    public Lista(int tamanho){
        filmes = new Filmes[tamanho];
        n = 0;
    }

    public void inserirInicio(Filmes filme)throws Exception{
	    if(n >= filmes.length){
	    	throw new Exception("Erro ao inserir!");
	    }

	    for(int i = n; i > 0; i--){
	    	filmes[i] = filmes[i-1];
	    }

	    filmes[0] = filme;
	    n++;
    }    

    public void inserirFim(Filmes filme) throws Exception{
        if(n >= filmes.length){
            throw new Exception("Erro ao inserir");
        }
        filmes[n] = filme;
        n++;
    }

    public void inserir(Filmes filme, int pos)throws Exception{
    
	    if(n >= filmes.length || pos < 0 || pos > n){
	    	throw new Exception("Erro ao inserir");
	    }

	    for(int i = n; i > pos; i--){
	    	filmes[i] = filmes[i-1];
	    }

	    filmes[pos] = filme;
	    n++;
    }

    public Filmes removerInicio()throws Exception{
	    if(n == 0){
	    	throw new Exception("Erro ao remover");
	    }

	    Filmes resp = filmes[0];
	    n--;

	    for(int i = 0; i < n; i++){
	    	filmes[i] =  filmes[i+1];
	    }

	    return resp;
    }

    public Filmes removerFim()throws Exception{
    	if(n == 0){
		throw new Exception("Erro ao remover");
		}

		return filmes[--n];
    }

    public Filmes remover(int pos)throws Exception{
    	if(n == 0 || pos < 0 || pos >= n){
			throw new Exception ("Erro ao remover");
		}

		Filmes resp = filmes[pos];
		n--;
	
		for(int i = pos; i < n; i++){
			filmes[i] = filmes[i+1];	
		}

		return resp;
    }

    public void mostrar(){
		for(int i = 0; i < n; i++){			
			MyIO.print(filmes[i].getNome()    + " " + filmes[i].getTituloOriginal() + " "  + formatter.format(filmes[i].getDataDeLancamento()) + 
						" " + filmes[i].getDuracao() + " " + filmes[i].getGenero()         + " "  + filmes[i].getIdiomaOriginal()   +
						" " +filmes[i].getSituacao() + " " + filmes[i].getOrcamento()      + " [" + filmes[i].getPalavrasChave()[0]);
			for(int j = 1; j < filmes[i].getPalavrasChave().length; j++){
				MyIO.print(", "+filmes[i].getPalavrasChave()[j]);
			}
			MyIO.println("]");
		}
    }
	
	/**
    * Algoritmo de ordenacao Mergesort.
    */

	public void sort() {
	   mergesort(0, n-1);
	}
 
	/**
	 * Algoritmo de ordenacao Mergesort.
	 * @param int esq inicio do array a ser ordenado
	 * @param int dir fim do array a ser ordenado
	 */
	private void mergesort(int inicio, int fim) {
	   if (inicio < fim){
		  int meio = (inicio + fim) / 2;
		  mergesort(inicio, meio);
		  mergesort(meio + 1, fim);
		  intercalar(inicio, meio, fim);
	   }
	}
 
	public void intercalar(int inicio, int meio, int fim){
		Filmes[] aux = new Filmes[filmes.length];
		
		for(int i = inicio; i <= fim; i++){
			aux[i] = filmes[i];
		}

		int esq = inicio;
		int dir = meio + 1;

		for (int i = inicio; i <= fim; i++) {
			if(esq > meio){
				filmes[i] = aux[dir++];
			}else if(dir > fim){
				filmes[i] = aux[esq++];
			}else if(aux[esq].getOrcamento() < aux[dir].getOrcamento()){
				filmes[i] = aux[esq++];
			}else if(aux[esq].getOrcamento() == aux[dir].getOrcamento() && aux[esq].getNome().compareTo(aux[dir].getNome()) < 0){
				filmes[i] = aux[esq++];
			}else{
				filmes[i] = aux[dir++];
			}
		}

	}

    


}

public class TP03Q09{

	public static boolean isFim(String str){
	    return (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M');
	}

	public static boolean isLetra(char caracter){
		return(Character.toUpperCase(caracter) >= 65 && Character.toUpperCase(caracter) <= 90);
	}
	public static boolean isNumero(char caracter){
		return (caracter >= 48 && caracter <= 57);
	}

	public static String ignoraNumero(String str){
		String resp = "";
		for(int i = 2; i < str.length(); i++){
			if(isNumero(str.charAt(i))){
					i++;				
			}else{
				resp += str.charAt(i);
			}
		}
		return resp.trim();
	}


	public static void main(String[] args) throws Exception{

		long tempoInicial = System.currentTimeMillis();

		MyIO.setCharset("UTF-8");
		String[] entrada = new String[100];
	
		int count = 0;
		String linha = MyIO.readLine();

		while(!isFim(linha)){
			entrada[count++] = linha;
			linha = MyIO.readLine();
		}

        Lista lista = new Lista(100);

		//insere os elementos na lista de filmes
		for(int i = 0; i < count; i++){
			Filmes aux = new Filmes();
            aux.ler(entrada[i]);
			lista.inserirFim(aux);
		}
		
		lista.sort();
		long tempoFinal = System.currentTimeMillis();

		FileWriter fw = new FileWriter ("750077_bolha.txt");
		PrintWriter gravarArq = new PrintWriter(fw);
		gravarArq.printf("750077\t" + "\t"+ lista.numComparacoes +"\t"+ lista.numMovimentacoes + "\t" + (tempoFinal-tempoInicial)/1000d);
		fw.close();

		lista.mostrar();

		
    }
}