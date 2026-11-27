package fpoly.duan1.sporthub_new.model;

import java.io.Serializable;

public class OwnerComplex implements Serializable {
    private String id;
    private String name;
    private String hotline;
    private String address;
    private String hours;
    private String status; // "ACTIVE" or "PAUSED"
    private int subPitchesCount;
    private String description;
    private boolean isCustom;
    private double latitude;
    private double longitude;
    private String coordinates;

    public OwnerComplex(String id, String name, String hotline, String address, String hours, String status, int subPitchesCount, String description, boolean isCustom) {
        this(id, name, hotline, address, hours, status, subPitchesCount, description, isCustom, 21.0285, 105.8542, "21.0285° B, 105.8542° Đ");
    }

    public OwnerComplex(String id, String name, String hotline, String address, String hours, String status, int subPitchesCount, String description, boolean isCustom, double latitude, double longitude, String coordinates) {
        this.id = id;
        this.name = name;
        this.hotline = hotline;
        this.address = address;
        this.hours = hours;
        this.status = status;
        this.subPitchesCount = subPitchesCount;
        this.description = description;
        this.isCustom = isCustom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSubPitchesCount() {
        return subPitchesCount;
    }

    public void setSubPitchesCount(int subPitchesCount) {
        this.subPitchesCount = subPitchesCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
