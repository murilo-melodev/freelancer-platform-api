# freelancer-platform-api

API REST de gestão de freelancers construída com Spring Boot e PostgreSQL.  
Projeto de estudo parte de uma progressão deliberada rumo ao mercado backend Java.

---

## Por que esse projeto existe

Esse projeto tem o objetivo de consolidar Spring Boot, banco de dados relacional e API REST de forma aplicada, não teórica.

Estou em transição de carreira para desenvolvimento backend Java, estudando de forma autodidata e progressiva. Cada projeto que construo repete tudo que foi feito no anterior e adiciona uma camada nova em cima. Esse foi o projeto onde juntei tudo ao mesmo tempo: Spring Boot com anotações, PostgreSQL com JPA, DTOs, regras de negócio encadeadas no Service, tratamento global de exceções e um fluxo de negócio com múltiplas entidades relacionadas.

O próximo ciclo vai manter esse padrão e adicionar os quatro pilares de OOP aplicados em contexto real.

---

## O que foi mais difícil

Entender como cada camada se conecta com as outras.

No começo ficava confuso sobre o que vai em cada lugar, quando usar DTO, quando ir direto no Model, quando o Service precisa de outro Service. Com o tempo fui entendendo que cada decisão tem um motivo: o DTO existe porque os dados da requisição não batem com o Model quando há relacionamentos. O `ProposalService` injeta o `FreelancerService` e não o `FreelancerRepository` porque o Service já tem as validações prontas — ir direto no Repository seria ignorar tudo que já foi construído.

Criar métodos com lógica encadeada ainda exige atenção — buscar a entidade, validar, verificar regras, criar, salvar, retornar. Cada passo depende do anterior.

---

## O que ainda está frágil — sendo honesto

- Criar métodos com lógica encadeada do zero, sem referência
- Imaginar os atributos corretos para cada entidade sem pesquisar
- Saber exatamente quando usar DTO sem precisar pensar muito

Estou documentando isso porque faz parte do processo. Repetição é o que fixa.

---

## Decisões de engenharia

**Por que usar DTO em `Project` e `Proposal` mas não em `Client` e `Freelancer`?**  
O DTO é necessário quando os dados da requisição não batem com o Model. `Client` e `Freelancer` têm campos simples, o usuário manda exatamente o que o Model tem. `Project` precisa de `clientId`, mas o Model guarda um objeto `Client`. `Proposal` precisa de `freelancerId` e `projectId`, mas o Model guarda objetos. Usar o Model direto nesse caso faria o usuário ter que mandar o objeto inteiro, o que não faz sentido numa API.

**Por que `ProposalService` injeta `FreelancerService` e `ProjectService` e não os Repositories diretamente?**  
Cada Service é dono das regras da sua entidade. O `FreelancerService` já sabe buscar e validar um freelancer. Ir direto no Repository quebraria o SRP — o `ProposalService` teria que replicar lógica que já existe.

**Por que `acceptProposal` e `rejectProposal` são `@PutMapping` e não `@PostMapping`?**  
Porque estamos modificando um recurso existente, não criando um novo. POST cria. PUT atualiza. A proposta já existe, estamos mudando o status dela.

**Por que o status da proposta começa como `PENDING` e não vem como parâmetro?**  
O usuário não define o status inicial, o sistema define. Toda proposta nasce como `PENDING`. Permitir que o usuário mande o status seria abrir espaço para inconsistência.

---

## Arquitetura

```
com.murilo.freelancer_platform_api
├── model
│   ├── Client.java
│   ├── Freelancer.java
│   ├── Project.java
│   ├── ProjectStatus.java       (enum: ACTIVE, COMPLETED, CANCELED)
│   ├── Proposal.java
│   ├── ProposalStatus.java      (enum: PENDING, ACCEPTED, REJECTED)
│   ├── ProjectRequest.java      (DTO)
│   ├── ProposalRequest.java     (DTO)
│   └── AcceptProposalRequest.java (DTO)
├── repository
│   ├── ClientRepository.java
│   ├── FreelancerRepository.java
│   ├── ProjectRepository.java
│   └── ProposalRepository.java
├── service
│   ├── ClientService.java       (valida nome e formato de email)
│   ├── FreelancerService.java   (valida nome, especialidade e preço positivo)
│   ├── ProjectService.java      (status sempre ACTIVE ao criar)
│   └── ProposalService.java     (status PENDING ao criar, fluxo de aceite e rejeição)
└── controller
    ├── ClientController.java
    ├── FreelancerController.java
    ├── ProjectController.java
    ├── ProposalController.java
    └── GlobalExceptionHandler.java
```

---

## Regras de negócio

- Nome do cliente não pode ser vazio
- Email do cliente precisa conter `@`
- Nome, especialidade e preço do freelancer são obrigatórios, preço não pode ser zero ou negativo
- Projeto nasce sempre com status `ACTIVE`
- Proposta nasce sempre com status `PENDING`
- Não é possível criar proposta para freelancer ou projeto inexistente
- Ao aceitar uma proposta, o cliente define a data de término

---

## Endpoints

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/client` | Lista todos os clientes |
| GET | `/client/{id}` | Busca cliente por ID |
| POST | `/client` | Cadastra um cliente |
| GET | `/freelancer` | Lista todos os freelancers |
| GET | `/freelancer/{id}` | Busca freelancer por ID |
| POST | `/freelancer` | Cadastra um freelancer |
| GET | `/project` | Lista todos os projetos |
| GET | `/project/{id}` | Busca projeto por ID |
| POST | `/project` | Cria um projeto |
| GET | `/proposal` | Lista todas as propostas |
| GET | `/proposal/{id}` | Busca proposta por ID |
| POST | `/proposal` | Envia uma proposta |
| PUT | `/proposal/accept` | Aceita uma proposta |
| PUT | `/proposal/reject/{id}` | Rejeita uma proposta |
| DELETE | `/proposal/{id}` | Remove uma proposta |

---

## Exemplos de uso

**Cadastrar um cliente:**
```json
POST /client

{
  "clientName": "Murilo",
  "email": "murilo@gmail.com"
}
```

**Resposta:**
```json
{
  "clientName": "Murilo",
  "email": "murilo@gmail.com",
  "id": "da0b2852-7e19-4529-b188-2c504184f3be"
}
```

---

**Criar uma proposta:**
```json
POST /proposal

{
  "description": "Desenvolvimento de sistema de gestão agrícola",
  "freelancerId": "4f67555d-fe0f-4e4a-8549-5aaba818fe3f",
  "projectId": "89b62337-5f0f-4f21-bc8c-9a62f3dee571"
}
```

**Resposta:**
```json
{
  "description": "Desenvolvimento de sistema de gestão agrícola",
  "startDate": "2026-04-05",
  "status": "PENDING",
  "freelancer": {
    "name": "freelancerteste1",
    "specialty": "Backend Java",
    "pricePerHour": 119.99,
    "id": "4f67555d-fe0f-4e4a-8549-5aaba818fe3f"
  },
  "project": {
    "name": "projectv1",
    "description": "Sistema para administrar fazenda",
    "status": "ACTIVE",
    "client": {
      "clientName": "Murilo",
      "email": "murilo@gmail.com",
      "id": "da0b2852-7e19-4529-b188-2c504184f3be"
    },
    "id": "89b62337-5f0f-4f21-bc8c-9a62f3dee571"
  },
  "endDate": null,
  "id": "0fad5431-ad5b-43e1-8f7d-824ed09220ad"
}
```

---

**Aceitar uma proposta:**
```json
PUT /proposal/accept

{
  "proposalId": "0fad5431-ad5b-43e1-8f7d-824ed09220ad",
  "endDate": "2026-06-01"
}
```

**Resposta:**
```json
{
  "description": "Desenvolvimento de sistema de gestão agrícola",
  "startDate": "2026-04-05",
  "status": "ACCEPTED",
  "endDate": "2026-06-01",
  "id": "0fad5431-ad5b-43e1-8f7d-824ed09220ad"
}
```

---

## Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Boot DevTools
- Maven

---

## O que vem a seguir

Os próximos projetos vão manter a mesma base e adicionar os quatro pilares de OOP aplicados em contexto real, herança, polimorfismo, encapsulamento e abstração não como teoria, mas como decisões de arquitetura. Depois disso: autenticação JWT e autorização por roles.

Objetivo de longo prazo: backend Java com perfil de mercado brasileiro — Java, Spring Boot e AWS.

---

## Sobre o estudo

Este projeto foi desenvolvido como parte de um processo de aprendizado autodidata e progressivo, com foco em consolidar conceitos de backend na prática.
Ferramentas de apoio foram utilizadas para organização e refinamento da documentação, enquanto toda modelagem, decisões de arquitetura e implementação foram feitas de forma consciente e incremental.
---

## Autor

Murilo Vinicius Alves Melo  
GitHub: github.com/murilo-melodev  
LinkedIn: linkedin.com/in/murilovinicius-devmelo