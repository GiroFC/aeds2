import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;


public class Filme{

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
        MyIO.println(this.nome + this.tituloOriginal + " " + sdf.format(this.dataDeLancamento) + " " + this.duracao + " " + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento  + " [" + palavraToString(this.palavrasChave) + "]");
    }
    

    
}

// public class TP02Q01Class{

//         public static boolean isFim(String s) {
//             return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
//         }
    
//         public static void main(String[] args) throws Exception{
//             String[] input = new String[1000];
//             int numInput = 0;
//             MyIO.setCharset("UTF-8");
//             do{
//                 input[numInput] = MyIO.readLine();
//             }while(isFim(input[numInput++]) == false);
//             numInput--;//Desconsiderar a palavra FIM
            
//             Filme[] filmes = new Filme[numInput];
    
//             for(int i = 0; i < numInput;i++){
//                 filmes[i] = new Filme();
//                 filmes[i].ler("/tmp/filmes/"+input[i]);
//                 filmes[i].Imprimir();
                
//             }
//     }
// }

