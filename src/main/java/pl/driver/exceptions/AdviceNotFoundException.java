package pl.driver.exceptions;

public class AdviceNotFoundException extends RuntimeException {

    private final Long adviceId;

    public AdviceNotFoundException(long adviceId) {
        this.adviceId = adviceId;
    }

    @Override
    public String getMessage() {
        return "Advice with ID: " + adviceId + " was not found!";
    }
}
