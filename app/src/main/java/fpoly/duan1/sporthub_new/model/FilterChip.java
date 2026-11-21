package fpoly.duan1.sporthub_new.model;

public class FilterChip {
    private String title;
    private boolean isSelected;
    private boolean isSingleChoice;

    public FilterChip(String title, boolean isSelected) {
        this(title, isSelected, false);
    }

    public FilterChip(String title, boolean isSelected, boolean isSingleChoice) {
        this.title = title;
        this.isSelected = isSelected;
        this.isSingleChoice = isSingleChoice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSingleChoice() {
        return isSingleChoice;
    }

    public void setSingleChoice(boolean singleChoice) {
        isSingleChoice = singleChoice;
    }
}
