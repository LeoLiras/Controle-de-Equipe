# Controle-de-Equipe
Uma aplicação **Java** que facilita o controle de dados pessoais de membros de uma equipe/instituição usando o banco de dados MySQL.
![Logo Java](https://s2.glbimg.com/twoewJmwpMgtGPcRPP8SxFlDVmM=/0x0:695x393/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2021/P/f/y52r4ySZWLkJjEhKLhgw/2014-11-14-java-logo.jpg)
![Logo MySQL](https://s2.glbimg.com/WcVu50imQYm5GntBKg-J5RkOAQA=/1200x/smart/filters:cover():strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2021/y/M/W5GFw3Qh2YwD5XkhUM2Q/2012-04-17-mysql-logos.gif)
__________________________________________________________________________________________________________________________________________________________________

# Instalação

## MySQL

O banco de dados pode ser instalado no seguinte link, assim como o MySQL Workbench, interface necessária para que possamos interagir com o database.

https://www.mysql.com/

## Java 

É necessário ter a versão mais atualizada do Java instalada no computador. 

https://www.java.com/pt-BR/

## Eclipse IDE

Neste caso utilizou-se a Eclipse IDE para o desenvolvimento do projeto. 

https://www.eclipse.org/downloads/

_____________________________________________________________________________________________________________________________________________________________________

# Preparação do Ambiente

Após realizar uma conexão, basta criar um banco de dados e executar o seguinte código SQL para a criação da tabela utilizada no projeto.

USE nome_do_seu_database;

CREATE TABLE colaboradores (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  rg VARCHAR(100) NOT NULL,
  cpf VARCHAR(100) NOT NULL,
  cargo VARCHAR(100) NOT NULL,
  endereco  VARCHAR(100) NOT NULL,
  data_nascimento DATE NOT NULL,
  idade INT NOT NULL,
  data_cadastro DATE NOT NULL,
  email VARCHAR(100) NOT NULL
);

**Atenção:** O método conectarDatabase() deve ser preenchido corretamente com os dados do seu database, ou a conexão falhará.
______________________________________________________________________________________________________________________________________________________________________
