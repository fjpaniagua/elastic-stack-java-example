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

## Use case 3. Showing data in Kibana

Once we have load our logs in ElasticSearch, we can use the Kibana service in order to explore all this data.
For that, we can start the Kibana docker container as follows:
```bash
docker-compose up -d kibana
```
so we can navigate through the application accessing to `http://localhost:5601/` (we need to configure Kibana in order to
indicate which is the *index* we want to monitor).

> NOTE: if we want to explore a larger dataset, we can try to download a sample dataset provided by
> Elastic (curl -O https://download.elastic.co/demos/kibana/gettingstarted/7.x/logs.jsonl.gz), then uncompress it and
> finally load it to ElasticSearch by using the API (curl -XPOST http://localhost:9200/_bulk -H 'Content-Type:application/x-ndjson' --data-binary @logs.jsonl)