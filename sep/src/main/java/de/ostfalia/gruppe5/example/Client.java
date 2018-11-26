package de.ostfalia.gruppe5.example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Startup
@Singleton
public class Client {

    private static final int CONST = 20;

    @Inject
    JMSContext context ;
    @Resource( lookup = " java :/ queue / FirstQueue ")
    private Queue queue ;

    @PostConstruct
    public void init () {
        Destination destination = queue ;
        for (int i = 0; i < CONST ; i++) {
            String text = " This is message " + (i + 1);
            context.createProducer().send(destination, text);
        }
    }

}