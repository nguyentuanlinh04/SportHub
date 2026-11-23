package fpoly.duan1.sporthub_new.model;

public class TimeSlotItem {
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_SELECTED = 1;
    public static final int STATUS_BOOKED = 2;

    private String id;
    private String timeStr;
    private String priceStr;
    private long priceValue;
    private int status; // 0: Available, 1: Selected, 2: Booked

    public TimeSlotItem(String id, String timeStr, String priceStr, long priceValue, int status) {
        this.id = id;
        this.timeStr = timeStr;
        this.priceStr = priceStr;
        this.priceValue = priceValue;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public long getPriceValue() {
        return priceValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
