# Coworking Reservation API

## Sobre o Projeto

API REST desenvolvida para gerenciamento de reservas de salas em um ambiente de coworking.

O sistema foi criado para solucionar problemas de controle manual de reservas, como:

- conflitos de horário;
- inconsistência de dados;
- dificuldade de rastreamento;
- baixa confiabilidade no gerenciamento de agendas.

A aplicação permite:

- cadastro de salas;
- criação de reservas para um determinado dia;
- validação de conflitos de horário;
- cancelamento de reservas;
- consulta de agenda diária;
- consulta de salas disponíveis por dia e horário.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Validation
- Lombok
- SpringDoc OpenAPI / Swagger
- Maven
- Postman

---

## Modelagem das Salas

Para evitar inconsistência nos tipos de salas, foi utilizado `Enum` ao invés de texto livre.

### RoomType

| Enum |
|---|
| MEETING_ROOM |
| PRIVATE_ROOM |
| AUDITORIUM |

### Implementação

```java
public enum RoomType {

    MEETING_ROOM,
    PRIVATE_ROOM,
    AUDITORIUM

}
```

---

## Modelagem de Reservas

Uma reserva pertence a uma única sala.

Relacionamento utilizado:

```java
@ManyToOne
```

---

## Cancelamento de Reservas

O cancelamento foi implementado utilizando soft delete lógico ao invés de remover registros do banco.

```java
canceled = true
```

A reserva permanece armazenada para:

- manter histórico;
- garantir rastreabilidade;
- evitar perda de informações.

### Campo utilizado

```java
@Builder.Default
private Boolean canceled = false;
```

Reservas canceladas:

- não aparecem na agenda diária;
- não bloqueiam horários;
- liberam novamente a sala para novas reservas.

---

## Validação de Conflito de Horários

Para impedir reservas sobrepostas, o sistema utiliza a seguinte lógica:

```java
startTime < endTimeNova
AND
endTime > startTimeNova
```

Essa condição garante que dois intervalos só entram em conflito quando existe interseção entre horários.

### Exemplo

| Reserva Atual | Nova Reserva | Resultado |
|---|---|---|
| 09:00 - 10:00 | 09:30 - 11:00 | Conflito |
| 09:00 - 10:00 | 10:00 - 11:00 | Permitido |

---

## Melhoria Implementada

O desafio solicitava consulta de salas livres em um determinado dia.

A solução foi expandida para considerar também intervalo de horário:

```http
GET /rooms/available?date=2026-05-13&startTime=14:00&endTime=18:00
```

A implementação reutiliza a mesma lógica de conflito de reservas já existente no sistema, tornando a verificação de disponibilidade mais consistente com as regras de negócio.

---

## Swagger / OpenAPI

Documentação disponível em:

```bash
http://localhost:8080/swagger-ui/index.html
```

---

## H2 Database Console

Console do banco H2:

```bash
http://localhost:8080/h2-console
```

---

## Endpoints

### 1. Cadastro de Salas

#### POST `/rooms`

```http
http://localhost:8080/rooms
```

### Entrada

```json
{
  "name": "Sala Reuniao",
  "type": "MEETING_ROOM",
  "capacity": 10
}
```

---

### 2. Realização de Reservas para um Dado Dia

#### POST `/reservations`

```http
http://localhost:8080/reservations
```

### Entrada

```json
{
  "responsibleName": "Gabriel Braga",
  "date": "2026-05-13",
  "startTime": "09:00",
  "endTime": "10:00",
  "roomId": 1
}
```

---

### 3. Validação de Conflitos de Horário

O sistema impede reservas com horários sobrepostos para a mesma sala.

#### POST `/reservations`

```http
http://localhost:8080/reservations
```

### Request

```json
{
  "responsibleName": "Gabriel Braga",
  "date": "2026-05-13",
  "startTime": "09:30",
  "endTime": "11:00",
  "roomId": 1
}
```

### Response

```json
{
  "timestamp": "2026-05-13T19:28:59.8784869",
  "status": 400,
  "error": "Business Rule Error",
  "message": "There is already a reservation for this time"
}
```

---

### 4. Cancelamento de Reservas

#### DELETE `/reservations/{id}`

```http
http://localhost:8080/reservations/{id}
```

Realiza o cancelamento lógico da reserva.

A reserva não é removida do banco de dados:

```java
canceled = true
```

Após o cancelamento:

- a reserva deixa de bloquear horários;
- a sala volta a ficar disponível.

---

### 5. Consulta de Agenda Diária

#### GET `/reservations/daily`

```http
http://localhost:8080/reservations/daily?date=2026-05-13
```

Retorna todas as reservas ativas de uma determinada data.

Reservas canceladas não aparecem na listagem.

### Response

```json
[
  {
    "id": 1,
    "responsibleName": "Gabriel Braga",
    "date": "2026-05-13",
    "startTime": "09:00:00",
    "endTime": "10:00:00",
    "roomName": "Sala Reuniao"
  }
]
```

---

### 6. Consulta de Salas Livres em um Dado Dia

#### GET `/rooms/available`

```http
http://localhost:8080/rooms/available?date=2026-05-13&startTime=11:00&endTime=14:00
```

A implementação foi expandida para verificar também:

- data;
- horário inicial;
- horário final.

Isso permite uma verificação de disponibilidade mais consistente com a Regra de Conflito de Reservas e retornar as salas disponíveis no dia e horário.

### Response

```json
[
  {
    "id": 1,
    "name": "Sala Reuniao",
    "type": "MEETING_ROOM",
    "capacity": 10
  },
  {
    "id": 2,
    "name": "Auditorio 1",
    "type": "AUDITORIUM",
    "capacity": 240
  }
]
```

## Tratamento de Erros

A API possui tratamento global de exceções utilizando:

```java
@RestControllerAdvice
```

---

### Conflito de Horário

```json
{
  "timestamp": "2026-05-13T16:18:06.0035416",
  "status": 400,
  "error": "Business Rule Error",
  "message": "There is already a reservation for this time"
}
```

---

### Sala Inexistente

```json
{
  "timestamp": "2026-05-13T16:20:11.1089963",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Room not found"
}
```

---

### Erro de Validação

```json
{
  "timestamp": "2026-05-13T16:21:39.3994135",
  "status": 400,
  "error": "Validation Error",
  "message": "Reservation date is required"
}
```

---

## Autor

Desenvolvido por Gabriel Braga.
