package fpoly.duan1.sporthub_new.model;

public class AddonServiceItem {
    private String id;
    private String title;
    private String unitPriceText;
    private long unitPrice;
    private int quantity;

    public AddonServiceItem(String id, String title, String unitPriceText, long unitPrice, int quantity) {
        this.id = id;
        this.title = title;
        this.unitPriceText = unitPriceText;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUnitPriceText() {
        return unitPriceText;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
