import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class Filme{
    private String nome;
    private String tituloOriginal;
    private Date dataDeLancamento = new Date();
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave = new String();

    //primeiro construtor
    Filme(){
        nome = "";
        tituloOriginal = "";
        dataDeLancamento = "";
        duracao = 0;
        genero = "";
        idiomaOriginal = "";
        situacao = "";
        orcamento = 0;
        palavrasChave = "";
    }

    //Segundo construtor, agora recebendo argumentos
    Filme(String nome, String tituloOriginal, Date dataDeLançamento, int duracao, String genero, String idiomaOriginal, String situacao, float orcamento, String palavrasChave[]){
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

    String removeTags(String line){
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
        this.dataDeLancamento = Date.parseDate(removeTags(br.readLine()));

        while(!br.readLine().contains("Duração"));
        this.duracao = Integer.parseInt(removeTags(br.readLine()));

        while(!br.readLine().contains("Gênero"));
        this.genero = removeTags(br.readLine());

        while(!br.readLine().contains("Idioma original"));
        this.idiomaOriginal = removeTags(br.readLine());

        while(!br.readLine().contains("Situação"));
        this.situacao = removeTags(br.readLine());

        while(!br.readLine().contains("N.º de temporadas"));
        this.numTemporadas = Integer.parseInt(removeTags(br.readLine()));

        while(!br.readLine().contains("N.º de episódios"));
        this.numEpisodios = Integer.parseInt(removeTags(br.readLine()));

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
        System.out.println(nome + " " + tituloOriginal + " " + dataDeLancamento + " " + duracao + " " + genero + " " + idiomaOriginal + " " + situacao + " " + orcamento + " " + palavrasChave);
    }
    

    class TP02Q01{

        public static boolean isFim(String s) {
            return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
        }
    
        public static void main(String[] args) {
            Filme serie = new Filme();
            String[] input = new String[1000];
            int numInput = 0;
    
            do{
                input[numInput] = MyIO.readLine();
            }while(isFim(input[numInput++]) == false);
            numInput--;//Desconsiderar a palavra FIM
    
    
            for(int i = 0; i < numInput;i++){
                try{
                    serie.ler("/tmp/filmes/"+input[i]);
                }catch(Exception e){
                }
                serie.Imprimir();
            }
        }
    }
}
