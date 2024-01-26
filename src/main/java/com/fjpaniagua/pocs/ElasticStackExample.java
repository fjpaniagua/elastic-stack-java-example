package com.fjpaniagua.pocs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class in charge of logging simple messages.
 * These logs are going to be sent to both Console output and ElasticSearch service (depends on
 * the logback.xml configuration, these logs would be sent to ElasticSearch through the Logstash
 * docker or directly to the ElasticSearch API).
 */
public class ElasticStackExample {

    private static final Logger log = LoggerFactory.getLogger(ElasticStackExample.class);

    public static void main(String[] args) {

        log.info("This is a log INFO for both elastic and logstash use cases");
        log.warn("This is a log WARN for both elastic and logstash use cases");
        log.debug("This is a log DEBUG for ONLY elastic use case");
    }
}