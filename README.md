# Architecture

## Domain Organisation (com.envimate.examples.example_mate_crud.domain)

Since there have been no specific requirements to the domain yet, and as far as I understand the aim was to build a REST
endpoint, the domain layer is basically missing, and instead the usecase package contains DTO objects that are used both for persistence and representation for the sake of this example.
Upon more requirements to the domain, these would be separated into Domain objects with the business logic, and the DTOs
would stay as a representation layer.
These DTOs are modeled after the json example provided in the PDF of the assignment, with some assumptions regarding validation that may or may not be true.

The so-called CustomTypes however may be reused between domain objects and DTOs. These are things like "Address" and "BankId", "Amount", etc. By introducing this simplistic objects, we avoid primitive obsession and provide a foundation for validated domain in the future. 

Example validations are provided in the DTOs. Those can be easily modified per real requirement (e.g. String lengths, int vs long, etc have been guesses from my side).

## Usecases (com.envimate.examples.example_mate_crud.usecases)

As the name suggests Usecases represent the use cases offered by the application. They define the input/output DTOs and 
provide the glue between repositories, domain and DTOs. Validation of DTOs is handled in the corresponding DTO's 
factory method. Hence every created object is known to be valid. 


Usecases do not have outgoing dependencies to the low-level 
technology-dependant implementations (a.k.a infrastructure package, a.k.a AWS/DynamoDB/Hibernate/HTTP/Spring framework
annotations).
Together with the domain, these represent the core business logic of the application and can be ported into any HTTP framework, 
as well as run on any persistence layer. 

## Infrastructure (com.envimate.examples.example_mate_crud.infrastructure)

Here we have the glue to the concrete technologies - implementation of the Repositories, the HTTP endpoint configuration, 
DI frameworks, etc. 

### HTTP server (com.envimate.examples.example_mate_crud.infrastructure.http) 

Each UseCase is exported as a REST endpoint using the HttpMate framework. DTOs reflect the structure of the JSON documents
that are consumed and returned and are mapped from/to json/object using the MapMate framework. These frameworks enable
for easy exception handling, which allows proper propagation of validation exception originating from DTO and CustomType
factories.

Currently the following endpoints have been mapped:

* CheckHealth.class handles "/health" with RequestMethod(GET)
* ListResource.class handles "api/resource" with RequestMethod(GET)
* CreateResource.class handles "api/resource" with RequestMethod(POST)
* FetchResource.class handles "api/resource/<id>" with RequestMethod(GET)
* UpdateResource.class handles "api/resource/<id>" with RequestMethod(PUT)

All of the communication is done with Content-Type application/json.

### DI

Guice is the DI framework of choice here.

### Repository (com.envimate.examples.example_mate_crud.infrastructure.db)  

DynamoDB is the persistence layer of choice here. As mentioned, this can be easily replaced with another implementation, should the
requirements of the application demand the change of persistence technology.

## Tests

Tests are implemented using JUnit5 and it's extension model. In particular ParameterResolver and ExecutionCondition are 
used to provide the test modes: 

* local backend: providing fast feedback tests using an in memory database (just a map)
* dynamodb local: providing medium speed tests using a "real" database (dynamodb setup with cloudformation in AWS)
* remote: providing slow but realistic feedback fully-blown integration tests (calling an already deployed http endpoint somewhere - in this case AWS LB)

The default test mode is "local backend".
If the AWS env variables are present the "dynamodb local" will be executed.
If the ENVIMATE_CRUD_ENDPOINT env variable is present (should point to a deployed endpoint e.g. the LB), the remote will
be executed. 
(mvn clean verify respects this)

## BUILD

* java version 12
* maven configuration inherited from envimate-opensource-parent provides best practices for checkstyle, spotbugs, multiple 
useful maven profiles, etc.
* Dockerfile 


## How to run locally

### With in-memory database

```bash

mvn clean verify
INMEMORY_MODE_ENABLED="" java -jar target/example-mate-crud-0.0.6-jar-with-dependencies.jar

curl http://localhost:1337/health
curl http://localhost:1337/api/resource

```

### With DynamoDB (needs AWS credentials in env variables)

```bash

mvn clean verify
java -jar target/example-mate-crud-0.0.5-jar-with-dependencies.jar

curl http://localhost:1337/health
curl http://localhost:1337/api/resource

```

## How to run on AWS

ECS was the choice. HttpMate framework provides a nice integration with AWS Lambda as well, however since java9+ is not 
yet supported by lambda, ECS has been chosen to deploy to AWS.

The infrastructure is provided using CloudFormation templates in 'src/main/aws'
Those are devided into "cluster" definition and the service definition itself.

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

3. You would need a vpc, public subnets and a basic understanding of AWS to run this example. 

You can choose to create the infrastructure with AWS console (apply the src/main/aws/envimate-cluster.yaml and then the src/main/aws/example-mate-crud-service.yaml) with the parameters of choice. 
Or you can use the shell scripts provided in  src/main/shell/create_cluster.sh and src/main/shell/create_service.sh
Those would require you to create an env.sh file, similar to the template_env.sh and configure your parameters there.

