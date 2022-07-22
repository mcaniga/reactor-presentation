package org.example.presentation.sinks;

import java.util.function.Consumer;

/**
 * Member of slack with name and consumer
 */
public class SlackMember {

    private String name;
    private Consumer<String> messagePoster;

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
        this.messagePoster.accept(message);
    }

    void setMessagePoster(Consumer<String> messagePoster) {
        this.messagePoster = messagePoster;
    }
}
