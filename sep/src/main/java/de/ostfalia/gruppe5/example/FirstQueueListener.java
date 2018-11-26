package de.ostfalia.gruppe5.example;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "FirstQueue", activationConfig =
@ActivationConfigProperty( propertyName = " destinationLookup ",
        propertyValue = "queue/FirstQueue"))
public class FirstQueueListener implements MessageListener {

    public void onMessage ( Message rcvMessage ) {
        TextMessage msg = null ;
        try {
            if ( rcvMessage instanceof TextMessage ) {
                msg = ( TextMessage ) rcvMessage ;
                System.out.println(msg.getText());
                // work with message
            } else {
            // error handling
            }
        } catch ( JMSException e) {
            throw new RuntimeException (e);
        }
    }
}