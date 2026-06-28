package model;

/**
 * Represents news.
 * A news can be read.
 * Each news is seen or unseen for some players.
 */
public class News {
    private String message;

    public News(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
