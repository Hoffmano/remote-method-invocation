# Remote Method Implementation

# Feito Por:

- Caio Rodrigues Gomes  - 11208012
- Eric Batista da Silva - 10783114
- Gabriel Hoffman Silva - 10783250
- Júlia Cristina de Brito Passos - 10723840

# Sobre o escopo do projeto

Basicamente a proposta do projeto era a elaboração de um sistema de informação simples sobre peças usando RMI de Java.

O sistema pode ser distribuído em múltiplos servidores, os quais implementam repositórios de informações sobre peças.

---
# Sobre a implementação
## Servidores

Primeiramente, abordando o lado dos servidores, vamos falar sobre o componente base, as peças.

### Part

Para as peças implementamos uma classe com os seguintes atributos:

- code: representa o código da peça em questão
- name: representa o nome da peça
- description: descrição sobre caracteristicas da peça
- subParts: lista contendo as subpeças da peça corrente e suas respectivas quantidades e repositório de origem
- serverName: nome do repositório de origem

### PartRepository

O PartRepository nada mais é do que a implementação propriamente dita dos métodos do servidor, o qual é criado pelo `Server.java`.

Decidimos que nossos servidores teriam os seguintes métodos:

- listParts: responsável por retornar todas as peças contidas no repositório
- repo: responsável por retornar dados e estatísticas do repositório
- getPart: responsável por verificar se uma determinada peça existe no repositório
- showPartAttributes: responsável por retornar os atributos de uma determinada peça
- addPart: responsável por adicionar uma determinada peça ao repositório

## Cliente

Apesar de todo o código usado para implementar a parte acima, de nada ele é util se não tivermos um client para consumir esses métodos.

Para isso temos o nosso `Client.java` que é o arquivo responsável pela implementação do nosso `CLI` (Command-Line Interface) e pela comunicação com os servidores.

Para a elaboração do CLI escolhemos adotar um padrão semelhante ao do `git`, o qual ja é um modelo bastante estruturado e bem aceito pela comunidade.

Portanto, tido isso em mente temos os seguintes comandos disponíveis para nossos usuários:

Comandos relacionados ao repositório:

- `repo bind \<serverName>`
  - Conecta o cliente com um determinado repositório
- `repo parts`
  - Lista os códigos das peças presentes no repositório corrente
- `repo`
  - Mostra dados e estatísticas do repositório corrente

Comandos relacionados as peças:

- `part get \<partCode>`
  - Busca uma determinada peça no repositório corrente e caso encontre, a torna a peça corrente
- ``part add \<partCode>, \<partName>, \<partDescription>``
  - Adiciona uma nova peça no repositório corrente
- ``part``
  - Mostra os dados da peça corrente

Comandos relacionados a lista de subpeças:

- ``subparts add \<quantity>``
  - Adiciona `n` quantidades da peça corrente numa lista de subpeças
- `subparts clean`
  - Limpa a lista de subpeças
- ``subparts remove \<partCode>``
  - Remove uma determinada peça da lista de subpeças
- ``subparts``
  - Lista todas as peças presentes na lista de subpeças

# Como usar

1. Primeiramente clone o repositório:

    `git clone https://github.com/Hoffmano/remote-method-invocation.git`

2. Vá para a pasta do repositório clonado e execute um dos seguintes comandos:
- Caso você esteja no linux rode o seguinte comando:

    `rmiregistry &`

- Se estiver no Windows rode o seguinte comando:

    `start rmiregistry`

3. Abra um novo terminal
4. Vá para a pasta do repositório clonado
5. Execute o comando `javac *.java`
6. Execute o comando `java Server <serverName>` substituindo \<serverName> com o nome desejado para o seu repositório
7. Repita a partir do passo 4 para criar novos repositórios
8. Abra um novo terminal
9. Vá para a pasta do repositório clonado
10. Execute o comando `java Client`
11. Execute qualquer um dos comandos especificados pela nossa CLI

# Caso de uso




# Arquitetura

