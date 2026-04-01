# Passagem Memories Club

Sistema interno para controle de passagem da empresa Memories Club. Esta aplicação gerencia reservas de passagens aéreas, autenticação de usuários e integração com serviços externos como AWS S3, Kafka e monitoramento com Prometheus/Grafana.

## 📋 Descrição do Projeto

O Passagem Memories Club é uma aplicação Spring Boot que implementa arquitetura hexagonal para gerenciar passagens aéreas. O sistema permite cadastro de clientes, reservas de passagens, autenticação JWT e comunicação assíncrona via Kafka.

## 🏗️ Arquitetura

O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)**, organizada nas seguintes camadas:

- **Domain**: Contém as regras de negócio, modelos de domínio e interfaces de repositório
- **Application**: Camada de aplicação com services e use cases
- **Adapter**: 
  - **In**: Controllers REST e adaptadores de entrada
  - **Out**: Repositórios JPA, entidades e adaptadores de saída
- **Infrastructure**: Configurações de infraestrutura e integrações externas
- **Integration**: Clientes Feign e produtores Kafka

### Diagrama de Arquitetura
```
[Controllers] → [Use Cases] → [Domain Services] → [Repositories]
     ↓              ↓              ↓              ↓
[REST API]    [Business Logic] [Domain Models] [JPA Entities]
```

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Kafka** - Mensageria assíncrona
- **Spring Cloud OpenFeign** - Cliente HTTP declarativo
- **MySQL** - Banco de dados principal
- **AWS SDK** - Integração com S3, DynamoDB e SQS
- **JWT** - Tokens de autenticação
- **Swagger/OpenAPI** - Documentação da API
- **Prometheus** - Coleta de métricas
- **Loki** - Agregação de logs
- **Grafana** - Dashboards de monitoramento
- **Docker & Docker Compose** - Containerização
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento entre objetos

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose
- Conta AWS (para S3, DynamoDB, SQS)

## 🛠️ Instalação e Configuração

### 1. Clonagem do Repositório
```bash
git clone <url-do-repositorio>
cd passagemMemoriesClub
```

### 2. Configuração do Ambiente

#### Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto:
```env
MYSQL_HOST=localhost
JWT_SECRET=sua-chave-secreta-jwt
AWS_ACCESS_KEY_ID=sua-access-key
AWS_SECRET_ACCESS_KEY=sua-secret-key
AWS_REGION=us-east-2
```

#### Configuração AWS
- Configure suas credenciais AWS no `application.yml` ou via variáveis de ambiente
- Certifique-se de que o bucket S3 `meu-bucket-passagem-memories` existe

### 3. Executar com Docker Compose

Para desenvolvimento completo com todas as dependências:
```bash
docker-compose up -d
```

Isso iniciará:
- MySQL (porta 3306)
- Zookeeper (porta 2181)
- Kafka (porta 9092)
- Kafdrop (porta 19000) - Interface web para Kafka
- Prometheus (porta 9090)
- Loki (porta 3100)
- Grafana (porta 3000)

### 4. Compilar e Executar

```bash
# Compilar
mvn clean compile

# Executar testes
mvn test

# Executar aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## 📚 Documentação da API

### Swagger UI
Acesse a documentação interativa da API em:
http://localhost:8080/swagger-ui/index.html

### Endpoints Principais

#### Autenticação
- `POST /auth/login` - Login de usuário
- `POST /auth/register` - Registro de usuário

#### Passagens
- `GET /passagens` - Listar todas as passagens
- `POST /passagens` - Criar nova passagem
- `GET /passagens/{id}` - Buscar passagem por ID
- `PUT /passagens/{id}` - Atualizar passagem
- `DELETE /passagens/{id}` - Deletar passagem
- `GET /passagens/cpf/{cpf}` - Buscar passagens por CPF

#### Endereços
- `GET /enderecos` - Listar endereços
- `POST /enderecos` - Criar endereço
- `GET /enderecos/{id}` - Buscar endereço por ID

### Autenticação JWT
Para acessar endpoints protegidos, inclua o token JWT no header:
```
Authorization: Bearer <token>
```

## 📊 Monitoramento

### Prometheus
Métricas disponíveis em: http://localhost:9090
- Health checks: `/actuator/health`
- Métricas: `/actuator/metrics`
- Prometheus endpoint: `/actuator/prometheus`

### Grafana
Interface de dashboards: http://localhost:3000
- **Login:** admin
- **Senha:** admin

### Loki
Agregação de logs: http://localhost:3100

### Kafdrop
Interface para Kafka: http://localhost:19000

## 🧪 Testes

Executar todos os testes:
```bash
mvn test
```

Executar testes específicos:
```bash
mvn test -Dtest=PassagemControllerTest
mvn test -Dtest=PassagemServiceImplTest
```

## 🔧 Comandos Úteis

### Maven
```bash
# Limpar e compilar
mvn clean compile

# Executar aplicação
mvn spring-boot:run

# Criar JAR
mvn clean package

# Executar JAR
java -jar target/passagem-0.0.1-SNAPSHOT.jar
```

### Docker
```bash
# Construir imagem
docker build -t passagem-memories .

# Executar container
docker run -p 8080:8080 passagem-memories

# Ver logs dos serviços
docker-compose logs -f

# Parar serviços
docker-compose down
```

## 📁 Estrutura do Projeto

```
src/main/java/com/devjoao/passagem/
├── adapter/
│   ├── in/controller/          # Controllers REST
│   └── out/
│       ├── entity/             # Entidades JPA
│       ├── message/            # Produtores Kafka
│       └── repositories/       # Repositórios
├── application/
│   ├── service/                # Serviços de aplicação
│   └── usecases/               # Casos de uso
├── domain/
│   ├── dto/                    # Data Transfer Objects
│   ├── enums/                  # Enums do domínio
│   ├── event/                  # Interfaces de domínio
│   └── model/                  # Modelos de domínio
├── infrastructure/             # Configurações infra
├── integration/                # Clientes externos
└── utils/                      # Utilitários
```

## 🔐 Segurança

- Autenticação baseada em JWT
- Senhas criptografadas
- Controle de acesso baseado em roles
- Validação de entrada de dados

## 🚀 Deploy

### Produção
```bash
# Build da aplicação
mvn clean package -DskipTests

# Build da imagem Docker
docker build -t passagem-memories:latest .

# Deploy com docker-compose
docker-compose -f docker-compose.prod.yml up -d
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📝 Convenções do Projeto

### Nomenclatura
- Classes: PascalCase
- Métodos: camelCase
- Variáveis: camelCase
- Constantes: UPPER_SNAKE_CASE
- Pacotes: lowercase

### Commits
- Use mensagens descritivas em português
- Siga o padrão: `tipo: descrição`
  - `feat:` - Nova funcionalidade
  - `fix:` - Correção de bug
  - `docs:` - Documentação
  - `style:` - Formatação
  - `refactor:` - Refatoração
  - `test:` - Testes

### Branches
- `main` - Branch principal
- `develop` - Branch de desenvolvimento
- `feature/*` - Novas funcionalidades
- `bugfix/*` - Correções de bugs
- `hotfix/*` - Correções urgentes

## 📞 Suporte

Para dúvidas ou problemas:
- Abra uma issue no repositório
- Contate a equipe de desenvolvimento

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

---

**Desenvolvido por DevJoão** 🚀
