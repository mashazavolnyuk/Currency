package com.mashazavolnyuk.currency.event;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
