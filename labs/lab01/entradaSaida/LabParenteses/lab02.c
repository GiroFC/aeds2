#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>

// Condição de parada da leitura
bool isFim(char s[]){
    return(strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}                                                       

//Verificador de palindromo   
bool verifica(char s[]){
    int aux = 0;
    for(int i = 0; i < strlen(s) && aux>=0; i++){
        if(s[i] == '(')aux++;
        if(s[i] == ')')aux--;
    }

    return(aux==0);
}                                   

int main()
{
    char entrada[1000][100];
    int numEntrada = 0;

    // Leitura da entrada padrao
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--; 
    
    for (int i = 0; i < numEntrada; i++){
        if (verifica(entrada[i])){
            printf("correto\n"); 
        }
        else{
            printf("incorreto\n");
        }
    }
    return 0;
}       