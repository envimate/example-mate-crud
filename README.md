# Architecture

## Domain Organisation

Since there have been no specific requirements to the domain yet, and as far as I understand the aim was to build a REST
endpoint, the domain layer is basically missing, and instead the usecase package contains DTO objects that are used both for persistence and representation for the sake of this example.
Upon more requirements to the domain, these would be separated into Domain objects with the business logic, and the DTOs
would stay as a representation layer.
These DTOs are modeled after the json example provided in the PDF of the assignment, with some assumptions regarding validation that may or may not be true.

The so-called CustomTypes however may be reused between domain objects and DTOs. These are things like "Address" and "BankId", "Amount", etc. By introducing this simplistic objects, we avoid primitive obsession and provide a foundation for validated domain in the future. 

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

## How to run on AWS

The infrastructure is provided using CloudFormation templates in 'src/main/aws'
Shell scripts are provided as convenience to create those.
Here is a quick-start to run the application in aws.

0. make sure you are running on a CLI that has aws credentials configured.
1. execute 
```
docker build --build-arg VERSION=0.0.1  -f src/main/docker/Dockerfile . -t example-mate-crud
``` 
2. ensure you have a pre-created ECR repository you want to push the image to and use the name in the command:

```
docker tag example-mate-crud:latest <YOUR_AWS_ACCOUNT_ID>.dkr.ecr.eu-central-1.amazonaws.com/example-mate-crud:latest
docker push <YOUR_AWS_ACCOUNT_ID>.dkr.ecr.eu-central-1.amazonaws.com/example-mate-crud:latest
```

3. 
