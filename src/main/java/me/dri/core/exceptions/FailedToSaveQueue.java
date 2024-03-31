package me.dri.core.exceptions;

public class FailedToSaveQueue extends RuntimeException {
    public FailedToSaveQueue(String message) {
        super(message);
    }
}
