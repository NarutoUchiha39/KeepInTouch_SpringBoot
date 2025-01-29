package com.Messaging.messaging.Class;

import org.Model.shared.User;

public class AddFriendClass {

    public User from;
    public String to;

    @Override
    public String toString(){
        return "from: Username: "+from.getUserID()+" To: "+to;
    }

    public AddFriendClass(User from, String to) {
        this.from = from;
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
