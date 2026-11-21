package fpoly.duan1.sporthub_new.model;

public class BookingItem {
    private String id;
    private String pitchName;
    private String subTitle;
    private String statusText;
    private String dateText;
    private String timeText;
    private String depositText;
    private String remainingFee;
    private String totalPrice;
    private boolean isReviewed;
    private String cancelReason;
    private String refundStatusText;
    private boolean isRefunded;
    private int statusCode; // 0: Sắp tới, 1: Đã hoàn thành, 2: Đã hủy

    // Constructor cho Sắp tới
    public BookingItem(String id, String pitchName, String subTitle, String statusText,
                       String dateText, String timeText, String depositText, String remainingFee, int statusCode) {
        this(id, pitchName, subTitle, statusText, dateText, timeText, depositText, remainingFee, remainingFee, false, "", "", false, statusCode);
    }

    // Constructor cho Đã hoàn thành
    public BookingItem(String id, String pitchName, String subTitle, String statusText,
                       String dateText, String timeText, String depositText, String remainingFee,
                       String totalPrice, boolean isReviewed, int statusCode) {
        this(id, pitchName, subTitle, statusText, dateText, timeText, depositText, remainingFee, totalPrice, isReviewed, "", "", false, statusCode);
    }

    // Constructor cho Đã hủy
    public BookingItem(String id, String pitchName, String subTitle, String statusText,
                       String dateText, String timeText, String cancelReason, String refundStatusText,
                       boolean isRefunded, int statusCode) {
        this(id, pitchName, subTitle, statusText, dateText, timeText, "", "", "", false, cancelReason, refundStatusText, isRefunded, statusCode);
    }

    // Constructor đầy đủ
    public BookingItem(String id, String pitchName, String subTitle, String statusText,
                       String dateText, String timeText, String depositText, String remainingFee,
                       String totalPrice, boolean isReviewed, String cancelReason, String refundStatusText,
                       boolean isRefunded, int statusCode) {
        this.id = id;
        this.pitchName = pitchName;
        this.subTitle = subTitle;
        this.statusText = statusText;
        this.dateText = dateText;
        this.timeText = timeText;
        this.depositText = depositText;
        this.remainingFee = remainingFee;
        this.totalPrice = totalPrice;
        this.isReviewed = isReviewed;
        this.cancelReason = cancelReason;
        this.refundStatusText = refundStatusText;
        this.isRefunded = isRefunded;
        this.statusCode = statusCode;
    }

    public String getId() {
        return id;
    }

    public String getPitchName() {
        return pitchName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getDateText() {
        return dateText;
    }

    public String getTimeText() {
        return timeText;
    }

    public String getDepositText() {
        return depositText;
    }

    public String getRemainingFee() {
        return remainingFee;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getRefundStatusText() {
        return refundStatusText;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
