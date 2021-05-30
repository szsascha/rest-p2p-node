# REST P2P Node

Proof-of-concept of an P2P network node based on Spring Boot and REST API. The nodes communicate with each other only via HTTP (REST).

## Installation

1. clone repository
2. mvn clean install
3. mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080

## Usage

You need to start multiple clients with different ports in order to test it correctly. The seed servers are hardcoded. So you have to start a server with port 8080 or 8081 on localhost.

Now you're ready to call the REST API:

GET http://localhost:8082/v1/node/status
Get node status information

GET http://localhost:8080/v1/node/nodelist
Get all connected nodes

GET http://localhost:8080/v1/node/transactions
Get a list of all transactions

POST http://localhost:8083/v1/node/transactions
```json
{
    "type": "Transaction",
    "version": 100,
    "data": "Transaction data"
}
```
Submit a transaction to the network

Usually all nodes communicate every 5 minutes with each other to load the newest informations. Transactions are propagated immediately.

You can find an overview of all possible operations on http://localhost:8080/swagger-ui.html

Only http is supported at the moment.