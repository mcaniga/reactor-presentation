package org.example.presentation.sinks;

import java.util.function.Consumer;

/**
 * Member of slack with name and consumer
 */
public class SlackMember {

    private String name;
    private Consumer<String> messageConsumer;

    public SlackMember(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void receives(String message){
        System.out.println(message);
    }

    public void says(String message){
        this.messageConsumer.accept(message);
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
