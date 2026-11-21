package fpoly.duan1.sporthub_new.model;

public class NotificationItem {
    private String id;
    private String title;
    private String content;
    private boolean isRead;
    private long timestamp;

    public NotificationItem(String id, String title, String content, boolean isRead, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
