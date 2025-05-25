package hu.balazskokay.poc.human.readable.duration.kata.core.port.in;

public class NegativeNumberException extends RuntimeException {
    public NegativeNumberException(String message) {
        super(message);
    }
}
