#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct Date{
    int dia;
    int mes;
    int ano;
}Date;

typedef struct Filme{
     char nome[];
     char tituloOriginal[];
     Date dataDeLancamento[];
     int duracao;
     char genero[];
     char idiomaOriginal[];
     char situacao[];
     float orcamento;
     char palavrasChave[][];
} Filme;

//construtor
void Filme (Filme* s){
    strcpy(s->nome, "");
    strcpy(s->tituloOriginal, "");
    s->duracao = 0;
    strcpy(s->genero, "");
    strcpy(s->idiomaOriginal, "");
    strcpy(s->situacao, "");
    s->orcamento = 0.0;
}

char* removeTags(char line[]){
    char newline = "";
    int i = 0;
    while (i < strlen(line)){
        if(line[i] == '<'){
            i++;
            while(line[i] != '>')i++;
        }else{
            newline += line[i];
        }
        i++;
    }

    
    return newline;
}
// strtsr == contains
void ler(char nomeArquivo, Filme* x){
    FILE *filme = fopen ("/tmp/filmes/","r");
    char linha[100];
    fgets(linha, 100, filme);


    //Lendo o título
    while (!strstr(linha, "<title>")){
        fgets(linha, 100, filme);
    }
    strcpy(x->nome, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);


    //lendo a data
    while (!strstr(linha, "span class=\"release\"")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    strcpy(x->dataLancamento, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);

    //lendo os generos
    while (!strstr(linha, "genres")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);
    strcpy(x->genero, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);

    //lendo a duração
    while (!strstr(linha, "runtime")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);
    strcpy(x->duracao, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);

    //lendo o Título Original
    while (!strstr(linha, "<section class=\"facts left_column\">")){
        fgets(linha, 100, filme);
    }
    while (!strstr(linha, "<strong><bdi>Situação</bdi></strong>")){
        fgets(linha, 100, filme);
        if(strstr(linha, "Título original"){
            fgets(linha, 100, filme);
            strcpy(x->tituloOriginal, removeTags(fgets(linha,100, filme)));
        }
    }
    if(x->tituloOriginal == ""){
        x->tituloOriginal == x->nome;
    }

    //lendo situacao
    strcpy(x->situacao, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);

    //lendo idioma
    while (!strstr(linha, "Idioma original")){
        fgets(linha, 100, filme);
    }
    strcpy(x->idiomaOriginal, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);

    //lendo orçamento
    while (!strstr(linha, "Orçamento")){
        fgets(linha, 100, filme);
    }
    if(strstr(linha, "<p><strong><bdi>Orçamento</bdi></strong> -</p>")){
        removeTags(fgets(linha,100, filme);
        x->orcamento = 0;
    }else{
        strcpy(x->orcamento, removeTags(fgets(linha,100, filme)));
    }
    

    fgets(linha, 100, filme);
    while (!strstr(linha, "Palavras-chave")){
        fgets(linha, 100, filme);
    }
    linha = "";
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);

    if(strstr(linha, "Nenhuma palavra-chave foi adicionada.")){
        strcpy(x->palavrasChave, removeTags(fgets(linha,100, filme)));
    }else{
        strcpy(x->numTemporadas, removeTags(fgets(linha,100, filme)));
    }

    fclose(filme);
}

void imprimir(Filme *z){
    printf("%s %s %s %s %s %s %s %i %i", z->nome,z->formato,z->duracao,z->paisDeOrigem,z->idioma,z->emissora,z->transmissao,z->numTemporadas,z->numEpisodios);
}



bool isFim(char *s){
    return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main(){
    char entrada[1000][100];
    int numEntrada = 0;
    Filme teste;
    Filme(&teste);
    // Leitura da entrada padrao
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

    // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
    // maiusculas da entrada
    for (int i = 0; i < numEntrada; i++){
        
    }
    
    
    return 0;
}