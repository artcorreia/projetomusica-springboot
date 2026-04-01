# projetomusica-springboot
# 🎵 Projeto Música

> API RESTful para gestão de músicas, artistas e integrantes de bandas.  
> Desenvolvido com Java 21 + Spring Boot 4 | Projeto em desenvolvimento ativo 🚧

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Status do Projeto](#status-do-projeto)
- [Tecnologias e Dependências](#tecnologias-e-dependências)
- [Arquitetura e Estrutura de Pastas](#arquitetura-e-estrutura-de-pastas)
- [Modelo de Dados](#modelo-de-dados)
- [Endpoints da API](#endpoints-da-api)
- [Recursos Implementados](#recursos-implementados)
- [Como Executar](#como-executar)
- [Banco de Dados H2 (Console)](#banco-de-dados-h2-console)
- [Dados de Exemplo](#dados-de-exemplo)
- [Validações](#validações)
- [Próximos Passos](#próximos-passos)

---

## Sobre o Projeto

O **Projeto Música** é uma API REST desenvolvida para gerenciar um catálogo musical contendo músicas, artistas/bandas e seus respectivos integrantes. A API oferece operações CRUD completas para músicas, buscas por diferentes critérios, paginação de resultados e navegação HATEOAS entre os recursos.

O projeto foi desenvolvido como trabalho acadêmico na **FIAP** (Turma 2TDSPI) e segue evoluindo com novas funcionalidades e otimizações.

---

## Status do Projeto

🚧 **Em desenvolvimento** — O projeto está em constante evolução. Funcionalidades são adicionadas e aprimoradas continuamente.

| Funcionalidade | Status |
|---|---|
| CRUD de Músicas | ✅ Implementado |
| Busca por Substring (título, artista, integrante) | ✅ Implementado |
| Busca por Duração | ✅ Implementado |
| Busca por Data de Lançamento | ✅ Implementado |
| Busca por Gênero ordenada | ✅ Implementado |
| Paginação | ✅ Implementado |
| Cache de Resultados | ✅ Implementado |
| HATEOAS | ✅ Implementado |
| CRUD de Artistas | 🔜 Planejado |
| CRUD de Integrantes | 🔜 Planejado |
| Autenticação / Segurança | 🔜 Planejado |
| Banco de dados externo (ex: PostgreSQL) | 🔜 Planejado |
| Documentação Swagger/OpenAPI | 🔜 Planejado |
| Testes unitários e de integração | 🔜 Planejado |

---

## Tecnologias e Dependências

### Core

| Tecnologia | Versão | Descrição |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework base da aplicação |
| Spring Web MVC | — | Camada HTTP e controllers REST |
| Spring Data JPA | — | Abstração de persistência com Hibernate |
| Spring Cache | 4.0.4 | Gerenciamento de cache em memória |
| Spring HATEOAS | — | Hypermedia As The Engine Of Application State |
| Spring Validation | — | Validação de dados nas entidades |
| Lombok | 1.18.42 | Redução de boilerplate (getters, setters, etc.) |
| H2 Database | — | Banco de dados em memória para desenvolvimento |

### Build

| Ferramenta | Versão |
|---|---|
| Maven | (via Maven Wrapper) |
| Spring Boot Maven Plugin | — |
| Maven Resources Plugin | 3.1.0 |

---

## Arquitetura e Estrutura de Pastas

O projeto segue a arquitetura em camadas clássica do Spring Boot:

```
projeto_musica/
├── src/
│   ├── main/
│   │   ├── java/br/com/fiap/projeto_musica/
│   │   │   ├── ProjetoMusicaApplication.java   # Classe principal / ponto de entrada
│   │   │   ├── control/
│   │   │   │   └── MusicaController.java        # Endpoints REST da API
│   │   │   ├── model/
│   │   │   │   ├── Musica.java                  # Entidade Música (JPA + HATEOAS)
│   │   │   │   ├── Artista.java                 # Entidade Artista/Banda
│   │   │   │   ├── Integrante.java              # Entidade Integrante da banda
│   │   │   │   └── EnumFuncao.java              # Enum: funções dos integrantes
│   │   │   ├── dto/
│   │   │   │   └── MusicaDTO.java               # DTO para paginação com HATEOAS
│   │   │   ├── projection/
│   │   │   │   └── MusicaProjection.java        # Projeção para buscas filtradas (SQL nativo)
│   │   │   ├── repository/
│   │   │   │   └── MusicaRepository.java        # Acesso a dados + queries customizadas
│   │   │   ├── service/
│   │   │   │   ├── MusicaCachingService.java    # Service de cache (@Cacheable / @CacheEvict)
│   │   │   │   └── MusicaPaginacaoService.java  # Service de paginação + conversão p/ DTO
│   │   │   └── validation/
│   │   │       └── GerenciadorValidacoes.java   # Handler global de erros de validação
│   │   └── resources/
│   │       ├── application.properties           # Configurações da aplicação
│   │       └── import.sql                       # Dados iniciais (seed)
│   └── test/
│       └── java/br/com/fiap/projeto_musica/
│           └── ProjetoMusicaApplicationTests.java
├── pom.xml
└── mvnw / mvnw.cmd
```

### Fluxo de uma requisição

```
Cliente HTTP
    │
    ▼
MusicaController  (camada de controle / endpoints)
    │
    ├──► MusicaCachingService  (cache @Cacheable / @CacheEvict)
    │         │
    │         ▼
    │    MusicaRepository  (JPA + queries nativas)
    │         │
    │         ▼
    │      H2 Database (mem:testdb)
    │
    └──► MusicaPaginacaoService  (paginação + mapeamento para DTO)
              │
              ▼
         MusicaCachingService → MusicaRepository
```

---

## Modelo de Dados

### Diagrama de Entidades

```
┌─────────────────────────────┐
│           ARTISTA           │
├─────────────────────────────┤
│ id            BIGINT (PK)   │
│ nome          VARCHAR(65)   │
│ data_fundacao DATE          │
│ pais_origem   VARCHAR(20)   │
│ ativo         BOOLEAN       │
└────────────┬────────────────┘
             │ 1
             │
             │ N
┌────────────▼────────────────┐        ┌──────────────────────────────┐
│         INTEGRANTE          │        │           MUSICA             │
├─────────────────────────────┤        ├──────────────────────────────┤
│ id             BIGINT (PK)  │        │ id             BIGINT (PK)   │
│ fk_artista     BIGINT (FK)  │        │ fk_artista     BIGINT (FK) ──┘
│ nome           VARCHAR(70)  │        │ titulo         VARCHAR(80)   │
│ data_nascimento DATE        │        │ data_lancamento DATE         │
│ funcao         ENUM         │        │ duracao        DOUBLE        │
└─────────────────────────────┘        │ genero         VARCHAR(30)   │
                                       └──────────────────────────────┘
```

### Enum `EnumFuncao`

Representa as funções possíveis de um integrante dentro de uma banda:

| Valor | Descrição |
|---|---|
| `VOCALISTA` | Vocalista |
| `BATERISTA` | Baterista |
| `GUITARRISTA` | Guitarrista |
| `BAIXISTA` | Baixista |
| `FLAUTISTA` | Flautista |

---

## Endpoints da API

Base URL: `http://localhost:8080`

### 🎵 Músicas

| Método | Endpoint | Descrição | Parâmetros |
|---|---|---|---|
| `GET` | `/musicas/todas` | Lista todas as músicas | — |
| `GET` | `/musicas/paginadas` | Lista músicas paginadas (com DTO + HATEOAS) | `page` (default: 0), `size` (default: 2) |
| `GET` | `/musicas/{id}` | Busca música por ID | `id` (path) |
| `GET` | `/musicas/substring` | Busca por substring no título, artista ou integrante | `substring` (query) |
| `GET` | `/musicas/substring_caching` | Mesma busca por substring, com resultado em cache | `substring` (query) |
| `GET` | `/musicas/duracao` | Busca músicas com duração ≥ X minutos (filtragem em memória) | `x` (query, Double) |
| `GET` | `/musicas/duracao_otimizado` | Mesma busca por duração, com cache | `x` (query, Double) |
| `GET` | `/musicas/data_lancamento` | Busca músicas lançadas até a data informada (filtragem em memória) | `x` (query, LocalDate: `YYYY-MM-DD`) |
| `GET` | `/musicas/data_lancamento_otimizado` | Mesma busca por data, usando query nativa otimizada | `x` (query, LocalDate: `YYYY-MM-DD`) |
| `GET` | `/musicas/genero_ordenado` | Busca músicas por gênero, ordenadas por duração decrescente | `substring` (query) |
| `POST` | `/musicas/inserir` | Cadastra nova música | Body JSON (Musica) |
| `PUT` | `/musicas/{id}` | Atualiza uma música existente | `id` (path) + Body JSON (Musica) |
| `DELETE` | `/musicas/{id}` | Remove uma música | `id` (path) |

### Exemplos de Payload

**POST `/musicas/inserir`**
```json
{
  "artista": { "id": 1 },
  "titulo": "Tempo Perdido",
  "data_lancamento": "1986-01-01",
  "duracao": 5.30,
  "genero": "Rock"
}
```

**Resposta de busca por projeção (`/substring`, `/data_lancamento_otimizado`, `/genero_ordenado`)**
```json
[
  {
    "musica_titulo": "Faroeste Caboclo",
    "artista_nome": "Legião Urbana",
    "musica_duracao": 9.56,
    "musica_genero": "Rock"
  }
]
```

---

## Recursos Implementados

### Cache com Spring Cache

A classe `MusicaCachingService` centraliza o gerenciamento de cache usando as anotações `@Cacheable` e `@CacheEvict`. Toda operação de escrita (inserção, atualização, remoção) invalida todos os caches cadastrados, garantindo consistência dos dados.

Caches disponíveis:

- `todasMusicas`
- `musicasPorID`
- `musicasPorPaginacao`
- `retornarMusicasPorSubstring`
- `retornarMusicasPorDuracao`
- `retornarMusicasOrdenadasPorGenero`

### HATEOAS

Todos os endpoints de leitura retornam links de navegação para os demais endpoints da API, facilitando a descoberta e consumo da API por clientes. Exemplo de links incluídos em cada recurso:

- Link para listagem paginada
- Link para listagem completa
- Link para busca por substring (com e sem cache)
- Link para busca por duração (com e sem cache)
- Link para busca por data
- Links de atualização e remoção do próprio registro

### Paginação

O endpoint `/musicas/paginadas` utiliza `PageRequest` do Spring Data com conversão automática para `MusicaDTO`, suportando os parâmetros `page` e `size` via query string.

### Projeções (Projections)

A interface `MusicaProjection` é usada nas queries nativas para retornar apenas os campos relevantes, evitando over-fetching:

```java
public interface MusicaProjection {
    String getMusica_titulo();
    String getArtista_nome();
    Double getMusica_duracao();
    String getMusica_genero();
}
```

### Gerenciamento de Erros de Validação

A classe `GerenciadorValidacoes` captura globalmente exceções do tipo `MethodArgumentNotValidException` e retorna um mapa `campo → mensagem de erro` com status HTTP `400 Bad Request`.

---

## Como Executar

### Pré-requisitos

- **Java 21** instalado 
- **Maven** (ou use o Maven Wrapper incluso no projeto: `./mvnw`)
- Nenhum banco de dados externo necessário (H2 em memória)

### Passo a passo

1. Clone ou extraia o projeto:
```bash
git clone <url-do-repositorio>
cd projeto_musica
```

2. Execute com Maven Wrapper:
```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

3. Ou compile e execute o JAR:
```bash
./mvnw clean package
java -jar target/projeto_musica-0.0.1-SNAPSHOT.jar
```

4. A aplicação estará disponível em:
```
http://localhost:8080
```

---

## Banco de Dados H2 (Console)

Durante o desenvolvimento, o console web do H2 fica disponível para inspeção direta do banco:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `sa`
- **Senha:** *(deixar em branco)*

> ⚠️ O banco é em memória (`mem:testdb`), ou seja, os dados são perdidos a cada reinicialização da aplicação. Os dados de seed são recarregados automaticamente via `import.sql`.

---

## Dados de Exemplo

O arquivo `import.sql` é executado automaticamente na inicialização e popula o banco com dados de demonstração:

**Artistas:**

| ID | Nome | País | Ativo |
|---|---|---|---|
| 1 | Legião Urbana | Brasil | Não |
| 2 | Oasis | Inglaterra | Sim |
| 3 | Iron Maiden | Inglaterra | Sim |
| 4 | ACDC | Austrália | Sim |
| 5 | Megadeth | EUA | Sim |

**Músicas:**

| Título | Artista | Duração (min) | Gênero |
|---|---|---|---|
| Faroeste Caboclo | Legião Urbana | 9.56 | Rock |
| Live Forever | Oasis | 3.23 | Rock |
| Run to The Hills | Iron Maiden | 8.17 | Metal |
| Back in Black | ACDC | 7.28 | Rock |
| Tipping Point | Megadeth | 6.35 | Metal |

---

## Validações

As entidades possuem validação automática via Bean Validation (Jakarta Validation). Erros são retornados no formato:

```json
{
  "titulo": "O título é um campo obrigatório",
  "duracao": "must be less than or equal to 60.0"
}
```

### Regras da entidade `Musica`

| Campo | Regra |
|---|---|
| `titulo` | Obrigatório, 1–80 caracteres |
| `data_lancamento` | Data atual ou passada |
| `duracao` | Entre 1.0 e 60.0 minutos |
| `genero` | Obrigatório, 3–30 caracteres |

### Regras da entidade `Artista`

| Campo | Regra |
|---|---|
| `nome` | Obrigatório, 1–65 caracteres |
| `data_fundacao` | Data atual ou passada |
| `pais_origem` | Obrigatório, 1–20 caracteres |

### Regras da entidade `Integrante`

| Campo | Regra |
|---|---|
| `nome` | Obrigatório, 2–70 caracteres |
| `data_nascimento` | Data atual ou passada |
| `funcao` | Valor do enum `EnumFuncao` |

---

## Próximos Passos

Funcionalidades planejadas para as próximas iterações:

- [ ] Endpoints de CRUD completo para `Artista` e `Integrante`
- [ ] Migração para banco de dados externo (PostgreSQL / MySQL)
- [ ] Autenticação com Spring Security (JWT)
- [ ] Documentação automática com Springdoc OpenAPI (Swagger UI)
- [ ] Testes unitários com JUnit 5 e Mockito
- [ ] Testes de integração com MockMvc
- [ ] Containerização com Docker / Docker Compose
- [ ] Profiles de ambiente (`dev`, `test`, `prod`)
- [ ] Paginação e filtros para artistas e integrantes

---

## Informações do Projeto

| Item | Valor |
|---|---|
| Instituição | FIAP |
| Turma | Turma 2TDSPI |
| GroupId | `br.com.fiap` |
| ArtifactId | `projeto_musica` |
| Versão | `0.0.1-SNAPSHOT` |
| Data | 31/03/2026 |
