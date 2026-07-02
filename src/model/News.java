package model;

/**
 * Represents news.
 * A news can be read.
 * Each news is seen or unseen for some players.
 */
public class News {
    private String id;
    private String title;
    private String message;

    public News(String message) {
        this(message.toLowerCase().replaceAll("[^a-z0-9]+", "_"), "News", message);
    }

    public News(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof News news)) {
            return false;
        }
        return id.equals(news.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
