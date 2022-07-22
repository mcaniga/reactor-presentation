package org.example.presentation.sinks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {

    private String name;
    private Sinks.Many<SlackMessage> sink; // sink = Processor = composed from Producer and Consumer
    private Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks
                .many() // broadcast to many subscribers
                .replay() // use cached items for new subscribers
                .all();
        this.flux = this.sink.asFlux(); // get Producer part of the sink for subscribing (pull from sink)
    }

    public void joinRoom(SlackMember slackMember) {
        System.out.println(slackMember.getName() + "------------- Joined ---------------" + this.name);
        this.subscribeAndRecieveOldMessages(slackMember);
        slackMember.setMessageSayer(
                msg -> this.postMessage(msg, slackMember)
        );
    }

    private void subscribeAndRecieveOldMessages(SlackMember slackMember) {
        this.flux
                .filter(message -> !message.getSender().equals(slackMember.getName()))
                .doOnNext(message -> message.setReceiver(slackMember.getName()))
                .map(SlackMessage::toString)
                .subscribe(slackMember::receives);
    }

    private void postMessage(String msg, SlackMember sender) {
        SlackMessage message = new SlackMessage();
        message.setSender(sender.getName());
        message.setMessage(msg);
        this.sink.tryEmitNext(message); // send message to sink (push to sink)
    }
}
