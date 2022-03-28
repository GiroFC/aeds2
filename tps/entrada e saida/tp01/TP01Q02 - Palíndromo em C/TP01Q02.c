#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>

// Condição de parada da leitura
bool isFim(char s[]){
    return(strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}                                                       

//Verificador de palindromo   
bool isPalindromo(char s[]){
    int tam = strlen(s);
    bool resp = false;
    for(int i = 0; i < tam; i++){
        if(s[i] == s[tam - i - 1]){
            resp = true;
        } else{
            resp = false;
            i = tam;
        }
    }
    return resp;
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
        if (isPalindromo(entrada[i])){
            printf("SIM\n"); 
        }
        else{
            printf("NAO\n");
        }
    }
    return 0;
}                                        

