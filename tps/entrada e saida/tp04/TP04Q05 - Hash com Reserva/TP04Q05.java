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
        nova = nova.replace("T�tulo original", " ");
        nova = nova.replace("Idioma original", " ");
        nova = nova.replace("Situa��o", " ");
        nova = nova.replace("(BR)", " ");
        nova = nova.replace("Or�amento", " ");
        nova = nova.replace("$", " ");
        nova = nova.replace(",", "");

        return nova;
    }


    //transformando o array de dura��o em um inteiro de minutos
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


        //Lendo a data de lan�amento
        
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


        //Lendo a dura��o
        while(!(linha=br.readLine()).contains("runtime"));
        br.readLine();
        linha = br.readLine();
        this.duracao = duracaoToInt(removeTags(linha).trim());


        //Lendo o Titulo Original

        while(!(linha=br.readLine()).contains("<section class=\"facts left_column\">"));
        while(!(linha=br.readLine()).contains("<strong><bdi>Situa��o</bdi></strong>")){
            if((linha=br.readLine()).contains("T�tulo original")){
                this.tituloOriginal = removeTags(linha).trim();
            }
        }

        if(tituloOriginal == ""){
            this.tituloOriginal = nome;
        }
        
        
        // while(!(linha=br.readLine()).contains("T�tulo original"));
        // this.tituloOriginal = removeTags(linha).trim();
        

        //lendo a situa��o
        //while(!(linha=br.readLine()).contains("Situa��o</bdi>"));
        this.situacao = removeTags(linha).trim();

        //Lendo o idioma
        while(!(linha=br.readLine()).contains("Idioma original"));
        this.idiomaOriginal = removeTags(linha).trim();

        
        //Lendo o or�amento
        while(!(linha=br.readLine()).contains("Or�amento"));
        if(linha.contains("<p><strong><bdi>Or�amento</bdi></strong> -</p>")){
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

class Hash {
    Filme tabela[];
    int m1, m2, m, reserva;
 
    public Hash() {
       this(21, 9);
    }
 
    public Hash(int m1, int m2) {
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.tabela = new Filme[this.m];
        for (int i = 0; i < m1; i++) {
            tabela[i] = null;
            TP04Q05.contador++;
        }
        reserva = 0;
    }
 
    public int h(int elemento) {
       return elemento % m1;
    }

    public int getValorAscii(Filme elemento) {
        int ascii = 0;
        String nome = elemento.getTituloOriginal();

        for (int i = 0; i < nome.length(); i++) {
            ascii = ascii + (int) nome.charAt(i);
            TP04Q05.contador++;
        }

        return ascii;
    }
 
    public boolean inserir(Filme elemento) {
       boolean resp = false;


       if (elemento != null) {

          int pos = h(getValorAscii(elemento));

          if (tabela[pos] == null) {
             tabela[pos] = elemento;
             resp = true;
             TP04Q05.contador++;
          } else if (reserva < m2) {
             tabela[m1 + reserva] = elemento;
             reserva++;
             resp = true;
             TP04Q05.contador++;
          }
       }
       return resp;
    }
 
    public boolean pesquisar(String elemento) {
       boolean resp = false;
       int ascii = 0;

       for (int i = 0; i < elemento.length(); i++) {
           ascii = ascii + (int) elemento.charAt(i);
           TP04Q05.contador++;
       }
       int pos = h(ascii);


       if ((tabela[pos] != null) && (tabela[pos].getTituloOriginal().compareTo(elemento) == 0)) {
            System.out.println("posicao: "+ pos);
            resp = true;
       } else if(tabela[pos] == null){
            resp = false;
       } else if (tabela[pos].getTituloOriginal() != null) {
          for (int i = 0; i < reserva; i++) {
             if (tabela[m1 + i].getTituloOriginal().compareTo(elemento) == 0) {
                resp = true;
                System.out.println("posicao: " + (21+reserva));
                i = reserva;
             }
          }
       }


       if(resp == false){
        System.out.println("NAO");
       }
       return resp;
    }
 
    boolean remover(Filme elemento) {
       boolean resp = false;
       // ...
       return resp;
    }
 }

// public int getValorAscii(Filme elemento) {
//     int ascii = 0;
//     String nome = elemento.getTituloOriginal();

//     for (int i = 0; i < nome.length(); i++) {
//         ascii = ascii + (int) nome.charAt(i);
//         TP04Q05.contador++;
//     }

//     return ascii;
// }

// for (int i = 0; i < elemento.length(); i++) {
//     ascii = ascii + (int) elemento.charAt(i);
//     TP04Q05.contador++;
// }
// int pos = h(ascii);

public class TP04Q05 {

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

        Hash hash = new Hash();

        // lendo o arquivo dos filmes
        for (int i = 0; i < numInput; i++) {
            Filme aux = new Filme();
            aux.ler("/tmp/filmes/" + input[i]);
            hash.inserir(aux);
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
            TP04Q05.contador++;
        }

        fim = now();
        diferenca = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("750077_hashReserva.txt", "rw");

        Arq.writeChars("750077" + "\t" + diferenca + "\t" + TP04Q05.contador);

        Arq.close();

    }
}

