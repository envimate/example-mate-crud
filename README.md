# Architecture

## Domain Organisation

Since there have been no specific requirements to the domain yet, and as far as I understand the aim was to build a REST
endpoint, the domain layer is basically missing, and instead the objects modelled are used both for persistence and representation (are essentially DTOs).
Upon more requirements to the domain, these would be separated into Domain objects with the business logic, and the DTOs
would stay as a representation layer.
These DTOs are modeled after the json example provided in the PDF of the assignment.

## Validation

Example validations are provided in the DTOs. Those can be easily modified per real requirement (e.g. String lengths, int vs long, etc have been guesses from my side).

## Tests

Tests use an inmemory implementation of HttpMate, but the same factory method as in production to construct the HttpMate
 object.

Junit5 has been used, and the BackendClient, which is responsible for executing the requests is injected via
"BackendParameterResolver". This way, the same tests can be executed in several "modes" - local, remote, AWS, etc.

As for persistence layer, tests are running an "in memory database" (just a map), configured via Guice modules. 

## REST endpoints

The supported endpoints are easy to look up in HttpMateFactory.java
There you can see which "UseCase" class, handles which endpoint with which HTTP Method.
