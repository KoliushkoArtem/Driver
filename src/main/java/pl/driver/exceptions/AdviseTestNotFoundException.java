package pl.driver.exceptions;

public class AdviseTestNotFoundException extends RuntimeException {

    private final Long testId;

    public AdviseTestNotFoundException(long testId) {
        this.testId = testId;
    }

    @Override
    public String getMessage() {
        return "AdviceTest with ID: " + testId + " was not found!";
    }
}
