package fpoly.duan1.sporthub_new.model;

import java.util.List;

public class PitchComplex {
    private String id;
    private String name;
    private String distanceAndAddress;
    private double rating;
    private int reviewCount;
    private List<String> tags;
    private String priceText;
    private String statusText;
    private boolean isAvailable; // true: Còn trống, false: Sắp hết giờ

    public PitchComplex(String id, String name, String distanceAndAddress, double rating, int reviewCount, List<String> tags, String priceText, String statusText, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.distanceAndAddress = distanceAndAddress;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.tags = tags;
        this.priceText = priceText;
        this.statusText = statusText;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDistanceAndAddress() {
        return distanceAndAddress;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getPriceText() {
        return priceText;
    }

    public String getStatusText() {
        return statusText;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
