package pl.driver.exceptions;

public class MediaFileNotFoundException extends RuntimeException {

    private final Long mediaFileId;

    public MediaFileNotFoundException(long id) {
        this.mediaFileId = id;
    }

    @Override
    public String getMessage() {
        return "MediaFile with ID: " + mediaFileId + " was not found!";
    }
}
