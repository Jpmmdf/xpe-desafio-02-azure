# Configuração do Terraform para Serviços Azure

## Visão Geral

Este projeto contém scripts Terraform para a implantação de vários serviços na Azure, incluindo Cosmos DB, Redis Cache e Linux Web Apps.

## Pré-requisitos

- Terraform instalado
- Conta Azure e CLI configurados

## Recursos

- **Grupo de Recursos Azure**: Define o grupo de recursos na Azure.
- **Conta Cosmos DB**: Cria uma conta Cosmos DB.
- **Redis Cache**: Configura uma instância de Redis Cache.
- **Linux Web App**: Implanta um contêiner Docker como um web app na Azure.

## Uso

1. Inicialize o Terraform: `terraform init`
2. Planeje a implantação: `terraform plan`
3. Aplique a configuração: `terraform apply`

## Variáveis

- `environment`: Especifica o ambiente de implantação.
- `location`: Região da Azure para os recursos.

## Notas

Substitua os valores em `variables.tf` conforme necessário para o seu ambiente.

## Licença

[Licença MIT](LICENSE)
