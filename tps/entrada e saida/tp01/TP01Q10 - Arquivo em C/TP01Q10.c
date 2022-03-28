#include <stdio.h>
#include <string.h>

long Escrever(double entrada, int quantidade, long posi) 
{

  FILE *p;
  p = fopen("teste.txt", "w");

  for (int i = 0; i < quantidade; i++)
  {
    scanf("%lf", &entrada);


    fwrite(&entrada, sizeof(double), 1, p);

    fseek(p, posi = posi + 8, SEEK_SET);
  }

  //fclose(p);

  return posi;
}

void ler(double entrada, int quantidade, long posi) 
{


  FILE *p;
  p = fopen("teste.txt", "r");

  for (int i = 0; i < quantidade; i++)
  {
    posi -= 8;
    fseek(p, posi, SEEK_SET);
    fread(&entrada, sizeof(double), 1, p);
    printf("%.3lf\n", entrada);
  }
}

int main()
{
  FILE *p;
  p = fopen("teste.txt", "wb+");

  int quantidade = 0;
  int contador = 0;
  double entrada = 0;
  long posi = 0;

  scanf("%d", &quantidade);

  long receberposi = Escrever(entrada, quantidade, posi);

  ler(entrada, quantidade, receberposi);

  return 0;
}