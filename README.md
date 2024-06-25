# taxi-online-project

Este projeto é um serviço de táxi online desenvolvido como parte do curso [Clean Code e Clean Architecture](https://www.branas.io/index.html) dividido em 8 aulas. O serviço permite que passageiros e motoristas criem contas, solicitem corridas, aceitem corridas e atualizem a posição durante a corrida. A tarifa é calculada com base na distância e no horário do dia, proporcionando uma cobrança justa tanto para o passageiro quanto para o motorista. 

## Regras
- Passageiros e motoristas podem criar contas.
- É necessário informar nome, email, CPF, senha e placa do carro (somente para motoristas).
- Um email não pode estar associado a mais de uma conta.
- Não há restrições quanto ao CPF.

## Endpoints

#### Signup (Criação de Conta)
**Endpoint:** /api/accounts/signup
**Método:** POST
**Descrição:** Cria uma nova conta de passageiro ou motorista.
**Parâmetros:**
- **name (String):** Nome do usuário.
- **email (String):** Email do usuário.
- **cpf (String):** CPF do usuário.
- **password (String):** Senha do usuário.
- **carPlate (String, opcional):** Placa do carro (somente para motoristas).
- **isPassenger (Boolean):** Indica se é passageiro.
- **isDriver (Boolean):** Indica se é motorista.

```
curl --location 'http://localhost:8080/api/accounts/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
        "name": "John Doe",
        "email": "johndoe@example.com",
        "cpf": "97456321558",
        "carPlate": "ABC1234",
        "password": "Password123!",
        "isPassenger": true,
        "isDriver": false
    }'
```
