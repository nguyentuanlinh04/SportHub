package fpoly.duan1.sporthub_new.model;

public class DateItem {
    private String dayOfWeek;
    private String dayOfMonth;
    private boolean isSelected;

    public DateItem(String dayOfWeek, String dayOfMonth, boolean isSelected) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.isSelected = isSelected;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
