import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
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
    private String palavrasChave[];

    //primeiro construtor

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
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
        newline = newline.replace("&nbsp;", "");
        newline = newline.replace("Título original", "");
        newline = newline.replace("(BR)", "");
        newline = newline.replace("Orçamento", "");

        return newline;
    }

    public String ateParenteses(String line){
        String nova = "";
        for(int i = 0; line.charAt(i) != '('; i++){
            nova+=line.charAt(i);
        }
        return nova;
    }
    //Leitor
    public void ler(String nomeArquivo) throws Exception{
        FileReader fr = new FileReader("/tmp/Filmes"+nomeArquivo);
        BufferedReader br = new  BufferedReader(fr);

        String linha = br.readLine();

        while(!linha.contains("<title>")){
            linha = br.readLine();
        }
        this.nome = ateParenteses(removeTags(linha).trim());

        while(!linha.contains("Título original")){
            linha = br.readLine();
        }
        this.tituloOriginal = removeTags(linha).trim();

        while(!linha.contains("span class=\"release\"")){
            linha = br.readLine();
        }
        linha = br.readLine();
        this.dataDeLancamento = sdf.parse(ateParenteses(removeTags(linha).trim()));

        while(!linha.contains("runtime")){
            linha = br.readLine();
        }
        linha = br.readLine();
        this.duracao = Integer.parseInt(removeTags(linha).trim());

        while(!linha.contains("genres")){
            linha = br.readLine();
        }
        linha = br.readLine();
        this.genero = removeTags(linha).trim();

        while(!linha.contains("Idioma original")){
            linha = br.readLine();
        }
        this.idiomaOriginal = removeTags(linha).trim();

        while(!linha.contains("Situação")){
            linha = br.readLine();
        }
        this.situacao = removeTags(linha).trim();

        while(!linha.contains("Orçamento")){
            linha = br.readLine();
        }
        this.orcamento = Float.parseFloat(removeTags(linha).trim());

        while(!linha.contains("Palavras-chave")){
            linha = br.readLine();
        }
        String aux[] = new String[30];
        int cont = 0;
        while(!linha.contains("</ul>")){
            linha = br.readLine();
            if(linha.contains("<li>")){
                aux[cont++] = removeTags(linha).trim();
            }
        }
        for(int i = 0; i < cont; i++){
            this.palavrasChave[i] = aux[i];
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
    public void imprimir(){
        MyIO.print(getNome() + " " + getTituloOriginal() + " " + sdf.format(getDataDeLancamento()) + " " + getDuracao() + " " + getGenero() + " " + getIdiomaOriginal() + " " + getSituacao() + " " + getOrcamento() + " [");
        int i = 0;
        for(; i < palavrasChave.length - 1; i++){
            MyIO.print(getPalavrasChave()[i] + ", ");
        }
        MyIO.print(getPalavrasChave()[i]);
        MyIO.println("]");
    }
    

    
}

public class TP02Q01Class{

        public static boolean isFim(String s) {
            return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
        }
    
        public static void main(String[] args) throws Exception{
            /*
        *   criar array de String
        */
        String entrada[] = new String[100];
        int contador=0;
        //preencher com pub.in
        String linha = MyIO.readLine();
        while(isFim(linha)==false){
            entrada[contador++]=linha;
            linha = MyIO.readLine();
        }
        contador--;
        //visualizacao -> entrada[batman.html, luca.html, encanto.html, dog.html]
        
        /*
        *  criar array filmes do tamanho da entrada 
        */
        Filme filmes[] = new Filme[contador];
        //visualizacao -> filmes[nome duracao data ..., ....]

        /*
        *   instanciar objetos
        */
        for(int i=0; i<contador; i++){
            filmes[i]= new Filme();
            filmes[i].ler(entrada[i]);
        }
        //visualizacao -> filmes[batman 128 12/04 ..., ...]
        
        /*
        *   percorrer o array imprimindo
        */
        for(int i=0; i<contador; i++){
            filmes[i].imprimir();
        }
    }
}

