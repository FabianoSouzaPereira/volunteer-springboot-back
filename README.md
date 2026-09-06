# Volunteer API

API REST em Spring Boot para gerenciar pessoas de um grupo (igreja, ministério ou organização voluntária) e as contas que acessam o sistema.

Uma pessoa é um **membro** e pode ter um ou mais papéis: voluntário, líder, pastor, secretário, funcionário ou parceiro. O **usuário** é só a conta de login (e-mail e senha). Os dois conceitos são separados de propósito.

## Como funciona

O fluxo HTTP é linear:

```
Controller  →  Service  →  Repository  →  MongoDB
     ↓
   DTO (JSON)
```

- **Controller** recebe e devolve DTO, valida entrada e escolhe o status HTTP.
- **Service** aplica a regra: e-mail único, papéis, senha, eventos.
- **Repository** persiste no MongoDB.
- O documento Mongo **nunca** é exposto na API.

Autenticação e cadastro de conta passam por `AuthService`. Cadastro de usuário autenticado e troca de senha passam por `UserService`. Membros passam por `MemberService`.

Quando um usuário é criado, a API publica um evento no tópico Kafka `user-registration-events`. No profile `dev` o Kafka fica desligado e o evento é ignorado.

## Domínio

### Member

Pessoa do grupo. Coleção Mongo: `members`.

| Campo      | Tipo              | Observação                                      |
|------------|-------------------|-------------------------------------------------|
| id         | String            | Gerado pelo Mongo                               |
| name       | String            | Obrigatório                                     |
| age        | Integer           | 0–130                                           |
| group      | String            | Grupo/célula                                    |
| roles      | Set\<MemberRole\> | Pelo menos um papel                             |
| functions  | List\<String\>    | Funções específicas                             |
| status     | MemberStatus      | `ACTIVE` ou `INACTIVE` (padrão `ACTIVE`)        |
| phone      | String            | Opcional                                        |
| email      | String            | Único quando informado                          |
| address    | String            | Opcional                                        |
| job        | String            | Opcional                                        |
| createdAt  | LocalDateTime     |                                                 |
| updatedAt  | LocalDateTime     |                                                 |

Papéis (`MemberRole`):

- `VOLUNTEER`
- `LEADER`
- `PASTOR`
- `SECRETARY`
- `EMPLOYEE`
- `PARTNER`

A mesma pessoa pode ser, por exemplo, voluntária e líder ao mesmo tempo.

### User

Conta de acesso. Coleção Mongo: `users`.

| Campo     | Tipo          | Observação                         |
|-----------|---------------|------------------------------------|
| id        | String        | Gerado pelo Mongo                  |
| name      | String        | Obrigatório                        |
| email     | String        | Único, usado no login              |
| phone     | String        | Opcional                           |
| password  | String        | Hash BCrypt, nunca retornada       |
| enabled   | boolean       | Conta desativada não autentica     |
| createdAt | LocalDateTime |                                    |
| updatedAt | LocalDateTime |                                    |

Membro e usuário não são o mesmo registro. Podem compartilhar o mesmo e-mail, mas um não cria o outro automaticamente.

## Autenticação

Quase toda a API exige JWT. Só estes caminhos são públicos:

- `POST /volunteer/auth/register`
- `POST /volunteer/auth/login`
- `GET /volunteer/actuator/health`
- `GET /volunteer/actuator/info`

O prefixo `/volunteer` é o `context-path` da aplicação.

1. Cadastre ou faça login.
2. A API devolve `token`, `userId`, `name` e `email`.
3. Nas demais chamadas envie:

```http
Authorization: Bearer <token>
```

O token dura 24 horas. A chave JWT vem de `JWT_SECRET` (mínimo 32 caracteres).

A senha é gravada com BCrypt. Login inválido ou usuário inexistente devolvem o mesmo 401, para não revelar se o e-mail existe.

## API

Base local: `http://localhost:8080/volunteer`

### Auth

| Método | Caminho           | Corpo                                      | Resposta      |
|--------|-------------------|--------------------------------------------|---------------|
| POST   | `/auth/register`  | `name`, `email`, `password`, `phone?`      | 200 + token   |
| POST   | `/auth/login`     | `email`, `password`                        | 200 + token   |

Senha: mínimo 6 caracteres.

```json
POST /volunteer/auth/register
{
  "name": "Ana Silva",
  "email": "ana@example.com",
  "password": "secret1",
  "phone": "11999999999"
}
```

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": "66f...",
  "name": "Ana Silva",
  "email": "ana@example.com"
}
```

### Members

Recurso canônico. O campo `role` no JSON é um atalho; `roles` aceita vários papéis.

| Método | Caminho                 | Descrição                                      |
|--------|-------------------------|------------------------------------------------|
| GET    | `/members`              | Lista todos. Filtro: `?role=LEADER`            |
| GET    | `/members/{id}`         | Detalhe                                        |
| POST   | `/members`              | Cria. Exige `name` e pelo menos um papel       |
| PUT    | `/members/{id}`         | Atualiza só os campos enviados                 |
| DELETE | `/members/{id}`         | Remove a pessoa                                |

```json
POST /volunteer/members
{
  "name": "João",
  "age": 32,
  "group": "Grupo A",
  "roles": ["VOLUNTEER", "LEADER"],
  "functions": ["Recepção"],
  "status": "ACTIVE",
  "phone": "11988887777",
  "email": "joao@example.com",
  "address": "Rua 1",
  "job": "Professor"
}
```

### Alias por papel

Os caminhos antigos continuam válidos e filtram pelo papel da URL:

| Caminho                      | Papel        |
|------------------------------|--------------|
| `/volunteers/volunteers`     | `VOLUNTEER`  |
| `/volunteers/leaders`        | `LEADER`     |
| `/volunteers/pastors`        | `PASTOR`     |
| `/volunteers/secretaries`    | `SECRETARY`  |
| `/volunteers/employees`      | `EMPLOYEE`   |
| `/volunteers/partners`       | `PARTNER`    |

No alias:

- `GET` lista só quem tem aquele papel.
- `POST` inclui o papel da URL automaticamente.
- `DELETE /volunteers/leaders/{id}` remove só o papel `LEADER`. Se era o último papel, o membro é apagado.
- `DELETE /members/{id}` sempre apaga a pessoa.

### Users

Disponível em `/users` e `/volunteers/users`. A senha nunca volta no JSON.

| Método | Caminho                      | Descrição                          |
|--------|------------------------------|------------------------------------|
| GET    | `/users`                     | Lista contas                       |
| GET    | `/users/{id}`                | Detalhe sem senha                  |
| POST   | `/users`                     | Cria conta (autenticado)           |
| PUT    | `/users/{id}`                | Atualiza nome, e-mail, telefone    |
| PUT    | `/users/{id}/reset-password` | Troca senha (`oldPassword`, `newPassword`) |
| DELETE | `/users/{id}`                | Remove a conta                     |

## Erros

Resposta padrão:

```json
{
  "timestamp": 1725650000000,
  "status": 400,
  "error": "Validation failed",
  "message": "email: must be a well-formed email address",
  "path": "/volunteer/members"
}
```

| Status | Quando                                      |
|--------|---------------------------------------------|
| 400    | Validação ou JSON inválido                  |
| 401    | Sem token, token inválido ou login errado   |
| 403    | Senha atual não confere no reset            |
| 404    | Recurso inexistente                         |
| 409    | E-mail de membro ou usuário já cadastrado   |

Mensagens aceitam o header `Accept-Language`. Há `messages.properties` (en) e `messages_pt.properties` (pt).

## Stack

- Java 17
- Spring Boot 3.2.5
- Spring Web, Validation, Security, Data MongoDB, Kafka, Actuator
- JWT (JJWT 0.11.5)
- MongoDB 7
- Kafka (opcional no `dev`)
- Firebase Admin (opcional, desligado por padrão)
- Docker / Docker Compose

## Estrutura do código

```
com.fabianospdev.volunteer
├── controller          HTTP e aliases
├── dto                 Contratos de entrada/saída
├── mapper              Model ↔ DTO
├── messaging           Kafka e fallback no-op
├── model               Member, User, enums
├── repositories        Spring Data Mongo
├── security            JWT + filter chain
├── services            Regras de negócio
└── config              i18n
```

## Como rodar

### Local (profile `dev`)

Pré-requisito: MongoDB em `mongodb://localhost:27017/volunteer`.

```bash
./mvnw spring-boot:run
```

Ou rode `VolunteerApplication` no IntelliJ. O profile padrão é `dev`: Kafka e Firebase ficam desligados.

A API sobe em `http://localhost:8080/volunteer`.

### Variáveis de ambiente

| Variável                     | Padrão (dev)                                      | Uso                          |
|------------------------------|---------------------------------------------------|------------------------------|
| `SPRING_PROFILES_ACTIVE`     | `dev`                                             | `dev`, `test` ou `prod`      |
| `MONGODB_URI`                | `mongodb://localhost:27017/volunteer`             | Conexão Mongo                |
| `JWT_SECRET`                 | valor local de 32+ caracteres                     | Assinatura do token          |
| `KAFKA_ENABLED`              | `false` no `dev`                                  | Liga publisher/consumer      |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092`                              | Broker Kafka                 |
| `FIREBASE_ENABLED`           | `false`                                           | Inicializa Firebase          |
| `FIREBASE_CREDENTIALS`       | `classpath:firebase-adminsdk.json`                | Arquivo de credencial        |
| `CORS_ALLOWED_ORIGINS`       | `localhost:3000,4200,5173`                        | Frontends permitidos         |

Em **produção**, `MONGODB_URI` e `JWT_SECRET` são obrigatórios. Não deixe senha ou URI de Atlas no código.

### Docker Compose

Sobe Mongo, Zookeeper, Kafka e a API:

```bash
export JWT_SECRET="uma-chave-com-pelo-menos-32-caracteres"
docker compose up --build
```

A API fica em `http://localhost` (porta 80) e também em `http://localhost:9090`. O context-path continua `/volunteer`.

### Testes

```bash
./mvnw test
```

Os testes de serviço usam Mockito e não precisam de Mongo nem Kafka.

## Profiles

| Profile | Quando                         | Kafka              | Firebase |
|---------|--------------------------------|--------------------|----------|
| `dev`   | Desenvolvimento local          | Desligado          | Desligado |
| `test`  | Testes                         | Desligado          | Desligado |
| `prod`  | Docker / deploy                | Ligado se configurado | Só se `FIREBASE_ENABLED=true` |

## Dados antigos

A unificação de papéis mudou as coleções:

- Antes: `volunteer`, `leader`, `pastor`, `secretary`, `employee`, `partner`, `user`
- Agora: `members` e `users`

Documentos nas coleções antigas **não migram sozinhos**. Se ainda existirem no Mongo, é preciso importá-los para `members` com o campo `roles`.
