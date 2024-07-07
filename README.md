# tp1_tppe

# Descrição

O Sistema de Gestão de Vendas é uma aplicação Java para gerenciar vendas, clientes e produtos. O sistema permite registrar vendas, calcular impostos e fretes, e gerar relatórios para identificar clientes especiais com base no valor das vendas realizadas no último mês.

- As classes estão em [src/main/java](https://github.com/nando3d3/tp1_tppe/tree/main/tp1/src/main/java)
- Os arquivos de teste estão em [src/main/test/java](https://github.com/nando3d3/tp1_tppe/tree/main/tp1/src/test/java)


## Funcionalidades

- Cadastrar clientes de diferentes tipos (padrão, especial, e prime), com endereços em diferentes regiões do país;
- Cadastrar produtos com um código, descrição, valor de venda e unidade;
- Cadastrar uma venda com uma data, cliente, itens vendidos e método de pagamento;
- Calcular o total das vendas do último mês para cada cliente e verificar se ele é elegível para ser um cliente especial;
- Calcular do saldo de cashback para clientes prime durante a criação da venda.
- O projeto inclui testes parametrizados para os métodos das classes, garantindo a cobertura de diferentes cenários e condições de entrada.

## Execução dos testes
Na pasta tp1, digite no terminal:
mvn test

ou

para executar suite de testes
mvn test -Dtest=AllTests

