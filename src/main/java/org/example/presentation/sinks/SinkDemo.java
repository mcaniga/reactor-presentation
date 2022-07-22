package org.example.presentation.sinks;

import org.example.presentation.util.Util;

public class SinkDemo {

    public static void main(String[] args) {

        // create room
        SlackRoom slackRoom = new SlackRoom("reactor");

        // make 3 members
        SlackMember sam = new SlackMember("sam");
        SlackMember jake = new SlackMember("jake");
        SlackMember mike = new SlackMember("mike");

        // 2 members join - sam, jake
        slackRoom.joinRoom(sam);
        slackRoom.joinRoom(jake);

        sam.says("Hi all..");
        Util.sleepSeconds(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi.."); // message is sent to mike
        Util.sleepSeconds(4);

        // mike joins
        slackRoom.joinRoom(mike); // mike recieves cached message history
        mike.says("Hey guys..glad to be here..."); // message is sent to sam and jake
    }
}
