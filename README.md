# Java application example sending logs to Elastic Stack in Docker

This repository consists of an example of using the different services that make up what is commonly called ELK, 
for which we will use a very simple *Java* code since the important thing is to test the use and configuration of the 
different services.

For that, we will use a `docker-compose.yml` file which includes the different configurations for using every of these
services (elasticsearch, logstash, filebeat, kibana).

## Use case 1. Calling Elasticsearch directly

In this first example, we will send logs directly to ElasticSearch service (exposed in port 9200).

1. First, we must start the docker container that represents the elasticsearch service.
```bash
docker-compose up -d elasticsearch
```
2. For testing only this communication, we must comment the appender called `LOGSTASH` which is referenced into the `logback.xml`file.
3. Then, launch the application `ElasticStackExample.java`
4. After launching the application, we can check by using the Query DSL language the total of documents added to ElasticSearch
```bash
curl -X GET http://localhost:9200/_search -H 'Content-Type:application/json' -d '{"query":{"match_all":{}}}' | jq
```

## Use case 2. Sending logs to logstash and let it call ElasticSearch

In this example, instead of calling the ElasticSearch API exposed in port 9200, we will use a logback appender to store
the logs into a local file (elasticStackExample.log). 
Then the logstash service, due to the configuration defined into the file `src/main/resources/pipeline/logstash.conf`, 
will call the ElasticSearch service to add the desired logs into an index defined in that file (logstash-logs).

1. First, we must start the docker container that represents the logstash service (we asume the elasticsearch is up).
```bash
docker-compose up -d logstash
```
2. We must check the `logstash.xml` file to ensure the LOGSTASH appender is not commented (we could comment the ELASTIC appender for a more readable example).
3. Then, launch the application `ElasticStackExample.java`
4. Finally, we can check by using the Query DSL language the total of documents added to ElasticSearch
```bash
curl -X GET http://localhost:9200/_search -H 'Content-Type:application/json' -d '{"query":{"match_all":{}}}' | jq
```