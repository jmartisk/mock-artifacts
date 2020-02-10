package org.example;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Subscriber;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Downstream {

    // doesn't get a metric created!
/*
    @Incoming("strings")
    public void consume(String s) {
        System.out.println(s);
    }
*/

    @Incoming("strings")
    @Outgoing("uppercase")
    PublisherBuilder<Message<String>> toUpperCase(PublisherBuilder<Message<String>> input) {
        return input.map(message -> Message.of(message.getPayload().toUpperCase()));
    }

    @Incoming("uppercase")
    Subscriber<Message<String>> listenToUpperCaseStrings() {
        return ReactiveStreams.<Message<String>>builder().forEach(message -> {
            System.out.println(message.getPayload());
        }).build();
    }

}
