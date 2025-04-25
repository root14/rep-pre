
# repsy.io - demo project

REST API Implementation for an imaginary software package system.

This project contain 2 Storage Strategy libraries which is deployed on _repsy.io_ private maven repo and Spring boot application
 use it from Repsy.

This demo project also track the deployed packages with using PostgreSQL.

### Note
Given Json file is not valid. There was comma on 20th line. This comma removed. With this, Objectmapper successfully deserialized all the Json.



## API Reference

#### Deployment Endpoint

```http
  POST localhost:8080/{packageName}/{version}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `packageName` | `string` | **Required**. Name of package |
| `version` | `string` | **Required**. Version of package |

#### Download Endpoint

```http
  GET localhost:8080/{packageName}/{version}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `packageName` | `string` | **Required**. Name of package |
| `version` | `string` | **Required**. Version of package |

Download enpoint return `files.zip`. Which is contain `file.rep` and `metadata.json`.


## File Storage Strategy
 This demo uses Strategy pattern as storage method. 
 
 Object storage (minio) and file-system.

 
### application.properties
You set several flags and default values for this demo;
```
# 0|1 -> object-storage | file-system
storage.strategy=0
```
You can switch between object-storage (minio) and file-system.

Also, you can define default location for file-system storage like this;
```
# default path for file-storage
storage.default.bucketName=default-bucket-name
```
You can change your default `bucketName` with this parameter;
```
minio.bucket.name=miniobucket
```

## Sample files
I also added sample files under resource/sample for quick tests.

## Installation

You can directly pull Springboot app image from _reps.io_ like this;
````
docker pull repo.repsy.io/root14/ilkay-repsy/ilkay-rep-app:latest
````

But this application need PostgreSQL and Minio also. So i recommended clone the project;
````
https://github.com/root14/rep-pre.git
````

then

````
docker-compose up
````

## Prerequisites
- Java 21 LTS
- Docker
