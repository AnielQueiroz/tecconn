# Projeto Back-End Java Spring Boot

Este é um projeto back-end desenvolvido em Java Spring Boot para gerenciar eventos e inscrições de usuários. O projeto inclui funcionalidades como criação de eventos, inscrição de usuários, geração de links de indicação e visualização de rankings de indicações.

## Requisitos Funcionais

1. **Inscrição**: O usuário pode se inscrever no evento usando nome e e-mail.
2. **Geração de Link de Indicação**: O usuário pode gerar um link de indicação (um por inscrito).
3. **Ranking de Indicações**: O usuário pode ver o ranking de indicações.
4. **Visualização de Indicações**: O usuário pode ver a quantidade de inscritos que ingressaram com seu link.

## User Stories
### US00 - CRUD de Evento
Este User Story atende ao requisito funcional RF01 e RF02.

- **Criação de um novo evento**
- **Listagem de todos os eventos disponíveis**
- **Recuperação dos detalhes de um determinado evento pelo ID**
- **Recuperação dos detalhes de um determinado evento pelo seu Pretty Name**

## Endpoints
### Criação de Evento
- **Endpoint**: `POST /api/v1/events/new`
- **Descrição**: Cria um novo evento.
- **Requisição**:
  ```json
  {
    "title": "Intensivão de Java - O maior do ano",
    "location": "Online",
    "price": 0,
    "startDate": "2025-12-07",
    "endDate": "2025-12-07",
    "startTime": "16:00:00",
    "endTime": "23:00:00"
  }
- **Resposta**:
  ```json
  {
    "eventId": 7,
    "title": "Intensivão de Java - O maior do ano",
    "prettyName": "intensivão-de-java---o-maior-do-ano",
    "location": "Online",
    "price": 0.0,
    "startDate": "2025-12-07",
    "endDate": "2025-12-07",
    "startTime": "16:00:00",
    "endTime": "23:00:00"
  }

### Listagem de todos os eventos
- **Endpoint**: `GET /api/v1/events`
- **Descrição**: Lista todos os eventos existentes.
- **Resposta**:
  ```json
  [
    {
      "eventId": 5,
      "title": "Evento do Nenel",
      "prettyName": "evento-do-nenel",
      "location": "Online",
      "price": 0.0,
      "startDate": "2025-08-07",
      "endDate": "2025-08-07",
      "startTime": "18:00:00",
      "endTime": "23:00:00"
    },
    {
      "eventId": 7,
        "title": "Intensivão de Java - O maior do ano",
        "prettyName": "intensivão-de-java---o-maior-do-ano",
        "location": "Online",
        "price": 0.0,
        "startDate": "2025-12-07",
        "endDate": "2025-12-07",
        "startTime": "16:00:00",
        "endTime": "23:00:00"
    },
  ]

### Inscrição no evento
- **Endpoint**: `POST /api/v1/subscriptions/create/{prettyName}`
- **Descrição**: Se inscreve em um evento.
- **Requisição**:
  ```json
  {
    "name": "Aniel Queiroz",
    "email": "anielqsilva@gmail.com"
  }
- **Resposta**: 
  ```json
  {
    "subscriptionNumber": 11,
    "designation": "https://anieldev.pro/intensivão-de-java---o-maior-do-ano/14"
  }

### Inscrição no evento por indicação
- **Endpoint**: `POST /api/v1/subscriptions/create/{prettyName}/{userId}`
- **Descrição**: Se inscreve em um evento atráves de um link de indicação.
- **Requisição**:
  ```json
  {
    "name": "Aniel Queiroz",
    "email": "anielqsilva@gmail.com"
  }
- **Resposta**:
  ```json
  {
    "subscriptionNumber": 11,
    "designation": "https://anieldev.pro/intensivão-de-java---o-maior-do-ano/14"
  }

### Listagem de um evento pelo seu Pretty Name
- **Endpoint**: `GET /api/v1/events/{prettyName}`
- **Descrição**: Lista um evento pelo seu Pretty Name.
- **Resposta**:
  ```json
  {
    "eventId": 7,
    "title": "Intensivão de Java - O maior do ano",
    "prettyName": "intensivão-de-java---o-maior-do-ano",
    "location": "Online",
    "price": 0.0,
    "startDate": "2025-12-07",
    "endDate": "2025-12-07",
    "startTime": "16:00:00",
    "endTime": "23:00:00"
  }

### US01 - Gerar Ranking de Inscritos
Este User Story atende ao requisito funcional RF03.
- **Endpoint**: `GET /api/v1/subscriptions/{prettyName}/ranking`
- **Descrição**: Lista um ranking de número de inscritos por indicação (ou seja, ordenado pela somatória de inscritos por indicação).
- **Ideal**: O ranking deve exibir os 3 primeiros colocados.
- **Resposta**:
  ```json
  [
    {
        "subscribers": 8,
        "userId": 17,
        "name": "Aniel Queiroz"
    },
    {
        "subscribers": 4,
        "userId": 11,
        "name": "Gon"
    },
    {
        "subscribers": 3,
        "userId": 7,
        "name": "Jackie Chan"
    }
  ]

### Lista a Posição do Usuário no Ranking e a Quantidade de Seus Indicados
Este User Story atende ao requisito funcional RF04.
- **Endpoint**: `GET /api/v1/subscriptions/{prettyName}/ranking/{userId}`
- **Descrição**: Lista a posição do usuário no ranking e o número de inscritos através dele.
- **Resposta**:
  ```json
  {
    "item": {
        "subscribers": 8,
        "userId": 17,
        "name": "Aniel Queiroz"
    },
    "position": 1
  }

## Configuração do Projeto
### Pré-requisitos
- **Banco de dados: MySQL em Docker**: https://docs.docker.com/desktop/
- **MySQL Workbench para você poder manipular seu banco de Dados**: https://dev.mysql.com/downloads/installer/
- **Heidi SQL (Alternativa mais leve para o MySQL Workbench)**: https://www.heidisql.com/download.php
- **Java 21**
- **IDE de sua escolha**

## Recursos
- **Script da criação do banco de dados**: https://file.notion.so/f/f/08f749ff-d06d-49a8-a488-9846e081b224/e2a29b02-60d6-466c-8778-95aacb09399e/db_events.sql?table=block&id=0dd3ac4f-7f14-482c-bcd1-f0b2cf00dcfb&spaceId=08f749ff-d06d-49a8-a488-9846e081b224&expirationTimestamp=1740096000000&signature=ec5BdVp0BtK-MWNIvORdKOrMz-qOfKwqukZ3Qu8TrG8&downloadName=db_events.sql
- **Spring Initializer (para você poder criar seu primeiro projeto Spring)**: https://start.spring.io/
- **Docker Compose**: 
  ```yaml
    services:
      mysql:
        image: mysql:8.4
        restart: always
        container_name: mysql-tecconn
        environment:
          - MYSQL_ROOT_PASSWORD=mysql
        ports: 
          - '3336:3306'
        networks: 
          - tecconn-network
    networks:
      tecconn-network:
        driver: bridge

