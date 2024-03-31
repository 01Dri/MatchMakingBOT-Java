package me.dri.core.exceptions;

public class QueueFullException extends RuntimeException {
    public QueueFullException(String s) {
        super(s);
    }
}
