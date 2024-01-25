package com.fjpaniagua.pocs;

import com.internetitem.logback.elasticsearch.ElasticsearchAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class in charge of logging messaging which are going to be sent directly to ElasticSearch
 * (due to the {@link ElasticsearchAppender} defined into the logback.xml file).
 */
public class ElasticStackExample {

    private static final Logger log = LoggerFactory.getLogger(ElasticStackExample.class);

    public static void main(String[] args) {

        log.info("Esto es un log a INFO");
        log.debug("Esto es un log a DEBUG");
        log.warn("Esto es un log a WARN");
    }
}