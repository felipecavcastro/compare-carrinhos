# 🛒 Compare Carrinhos - API de Comparação de Preços

O **Compare Carrinhos** é uma aplicação backend desenvolvida em **Java** e **Spring Boot** que ajuda usuários a encontrarem a opção mais econômica para suas compras de supermercado. A API permite cadastrar produtos, mercados e preços, calculando automaticamente qual estabelecimento oferece o melhor valor total para uma lista de compras específica.

## 🚀 Funcionalidades Principais

- **Comparação Inteligente:** Calcula o mercado mais barato considerando quantidades de itens e ignorando estabelecimentos que não possuam o estoque completo.
- **Importação em Massa (CSV):** Permite o upload de arquivos CSV para cadastro rápido de centenas de produtos e preços.
- **Cálculo de Economia:** Retorna o valor total por mercado e o potencial de economia gerada.
- **Documentação Interativa:** Interface Swagger UI para teste de todos os endpoints sem necessidade de ferramentas externas.
- **Segurança e Robustez:** Tratamento global de erros e validação de entradas JSON.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.3.0
- **Banco de Dados:** SQLite (persistência local simplificada)
- **Documentação:** OpenAPI / Swagger UI
- **Testes:** JUnit 5 e Mockito (Testes de Integração)
- **Gerenciamento de Dependências:** Maven

## 📋 Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com
