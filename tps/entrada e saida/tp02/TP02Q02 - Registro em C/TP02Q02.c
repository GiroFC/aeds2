#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct Date{
    char *dia;
    char *mes;
    char *ano;
}Date;

typedef struct Filme{
     char *nome;
     char *tituloOriginal;
     Date dataDeLancamento;
     int duracao;
     char *genero;
     char *idiomaOriginal;
     char *situacao;
     float orcamento;
     char **palavrasChave;
} Filme;


char removeTags(char line[]){
    char newline[200];
    int i = 0;
    int j = 0;
    while (i < strlen(line)){
        if(line[i] == '<'){
            i++;
            while(line[i] != '>')i++;
        }else{
            newline[j] = line[i];
            j++;
        }
        i++;
    }

    
    return newline;
}

void trim(cha * s){
    if(null == s){
        return;
    }
    int n = 0;

    for(int i = 0; i < strlen(S); ++i){
        if(s[i] != ' ')
            s[n++] = s[i]; 
        
    s[n] = '\0';
    }
}

char ateParenteses(char * line){
    char* nova = "";
    for(int i = 0; i < strlen(line); i++){
        if(line[i] != '('){
            nova+=line[i];
        } else { 
            return nova;
        }
    }
    return nova;
        
}

// strtsr == contains
void ler(char nomeArquivo, Filme* x){
    FILE *filme = fopen ("../tmp/filmes/","r");
    char linha[100];
    fgets(linha, 100, filme);


    //Lendo o título
    while (!strstr(linha, "<title>")){
        fgets(linha, 100, filme);
    }
    strcpy(x->nome, trim(removeTags(fgets(linha,100, filme))));

    fgets(linha, 100, filme);


    //lendo a data
    while (!strstr(linha, "span class=\"release\"")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    char dataString[20];
    strcpy(dataString, trim(removeTags(fgets(linha,100, filme))));
    char* aux [3];
    aux[0] = strtok(dataString, /);
    for(int i = 1; aux[i] != NULL; i++){
        aux[i] = strtok(NULL, /);
    }

    Date data;
    data->dia = aux[0];
    data->dia = aux[1];
    data->dia = aux[2];

    fgets(linha, 100, filme);

    //lendo os generos
    while (!strstr(linha, "genres")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);
    strcpy(x->genero, trim(removeTags(fgets(linha,100, filme))));

    fgets(linha, 100, filme);

    //lendo a duração
    while (!strstr(linha, "runtime")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);
    strcpy(x->duracao, trim(removeTags(fgets(linha,100, filme))));

    fgets(linha, 100, filme);

    //lendo o Título Original
    while (!strstr(linha, "<section class=\"facts left_column\">")){
        fgets(linha, 100, filme);
    }
    while (!strstr(linha, "<strong><bdi>Situação</bdi></strong>")){
        fgets(linha, 100, filme);
        if(strstr(linha, "Título original"){
            fgets(linha, 100, filme);
            strcpy(x->tituloOriginal, trim(removeTags(fgets(linha,100, filme))));
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
    strcpy(x->idiomaOriginal, trim(removeTags(fgets(linha,100, filme))));

    fgets(linha, 100, filme);

    //lendo orçamento
    while (!strstr(linha, "Orçamento")){
        fgets(linha, 100, filme);
    }
    if(strstr(linha, "<p><strong><bdi>Orçamento</bdi></strong> -</p>")){
        removeTags(fgets(linha,100, filme);
        x->orcamento = 0;
    }else{
        strcpy(x->orcamento, trim(removeTags(fgets(linha,100, filme))));
    }
    

    fgets(linha, 100, filme);
    while (!strstr(linha, "Palavras-chave")){
        fgets(linha, 100, filme);
    }
    linha = "";
    fgets(linha, 100, filme);
    fgets(linha, 100, filme);

    if(strstr(linha, "Nenhuma palavra-chave foi adicionada.")){
        strcpy(x->palavrasChave, trim(removeTags(fgets(linha,100, filme))));
    }else{
        strcpy(x->numTemporadas, trim(removeTags(fgets(linha,100, filme))));
    }

    fclose(filme);
}


void imprimir(Filme *z){
    printf("%s %s %s %i %s %s %s %i %i", z->nome , z->tituloOriginal , z->dataLancamento->datacompleta , z->duracao , z->genero , z->idiomaOriginal , z->situacao , z->orcamento , z->palavrasChave);
}



bool isFim(char *s){
    return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main(){
    char entrada[1000][100];
    int numEntrada = 0;
    Filme filme;
    // Leitura da entrada padrao
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

    // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
    // maiusculas da entrada
    for (int i = 0; i < numEntrada; i++){
        ler(entrada[i], filme);
        imprimir(filme);
    }
    
    
    return 0;
}