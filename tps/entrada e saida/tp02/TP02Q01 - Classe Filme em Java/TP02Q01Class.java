import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Filme{
    private String nome;
    private String tituloOriginal;
    private Date dataDeLancamento;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    //primeiro construtor
    Filme(){
        nome = "";
        tituloOriginal = "";
        dataDeLancamento = new Date();
        duracao = 0;
        genero = "";
        idiomaOriginal = "";
        situacao = "";
        orcamento = 0;
        palavrasChave = null;
    }

    //Segundo construtor, agora recebendo argumentos
    Filme(String nome, String tituloOriginal, Date dataDeLançamento, int duracao, String genero, String idiomaOriginal, String situacao, float orcamento, String palavrasChave[]){
        this.nome = nome;
        this.tituloOriginal = tituloOriginal;
        this.dataDeLancamento = new Date();
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

    public void setPalavrasChave(String[] palavrasChave) {
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

    /*
    * método pra remover as tags dos html
    * @param line string passada pelo terminal 
     */
    public String removeTags(String line){
        String newline="";
        int i = 0;
        while(i<line.length()){
            if(line.charAt(i)== '<'){
                i++;
                while(line.charAt(i) != '>')i++;
            }else{
                newline += line.charAt(i);
            }
            i++;
        }
        return newline;
    }

    //Leitor
    public void ler(String nomeArquivo) throws Exception{
        InputStreamReader isr = new InputStreamReader(new FileInputStream(nomeArquivo));
        BufferedReader br = new  BufferedReader(isr);

        while(!br.readLine().contains("infobox_v2"));
        br.readLine();
        this.nome = removeTags(br.readLine());

        while(!br.readLine().contains("Título original"));
        this.tituloOriginal = removeTags(br.readLine());

        while(!br.readLine().contains("Data de lançamento"));
        try{
            SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
        this.dataDeLancamento = formate.parse(removeTags(br.readLine()));
        }catch(ParseException e){
            e.printStackTrace();
        }

        while(!br.readLine().contains("Duração"));
        this.duracao = Integer.parseInt(removeTags(br.readLine()));

        while(!br.readLine().contains("Gênero"));
        this.genero = removeTags(br.readLine());

        while(!br.readLine().contains("Idioma original"));
        this.idiomaOriginal = removeTags(br.readLine());

        while(!br.readLine().contains("Situação"));
        this.situacao = removeTags(br.readLine());

        while(!br.readLine().contains("Orçamento"));
        this.orcamento = Float.parseFloat(removeTags(br.readLine()));

        int i = 0;
        while(!br.readLine().contains("Keyword")){
            this.palavrasChave[i] = removeTags(br.readLine());
            i++;
        }
        

        br.close();
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
        System.out.print(nome + " " + tituloOriginal + " " + dataDeLancamento + " " + duracao + " " + genero + " " + idiomaOriginal + " " + situacao + " " + orcamento + " ");
        System.out.print("[");
        for(int i = 0; i < palavrasChave.size; i++){
            System.out.print(palavrasChave[i]);
        }
        System.out.println("]");
    }
    

    
}

public class TP02Q01Class{

        public static boolean isFim(String s) {
            return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
        }
    
        public static void main(String[] args){
            Filme filme = new Filme();
            String[] input = new String[1000];
            int numInput = 0;
    
            do{
                input[numInput] = MyIO.readLine();
            }while(isFim(input[numInput++]) == false);
            numInput--;//Desconsiderar a palavra FIM
    
    
            for(int i = 0; i < numInput;i++){
                try{
                    filme.ler("/tmp/Filmes/"+input[i]);
                }catch(Exception e){
                }
                filme.Imprimir();
            }
        }
}

