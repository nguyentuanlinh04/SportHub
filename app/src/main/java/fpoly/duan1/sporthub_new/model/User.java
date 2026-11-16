package fpoly.duan1.sporthub_new.model;

import java.io.Serializable;

public class User implements Serializable {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CHU_SAN = "CHU_SAN";
    public static final String ROLE_NHAN_VIEN = "NHAN_VIEN";
    public static final String ROLE_KHACH_HANG = "KHACH_HANG";

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PENDING = "PENDING_APPROVAL";

    private String userId;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String role;
    private String status;
    private String cccd;
    private String address;
    private String businessName;
    private String taxCode;
    private long createdAt;

    // Constructor rỗng bắt buộc cho Firebase Deserialization
    public User() {
    }

    public User(String userId, String phoneNumber, String fullName, String password, String role, String status, long createdAt) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public User(String userId, String phoneNumber, String fullName, String password, String role, String status,
                String cccd, String address, String businessName, String taxCode, long createdAt) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.cccd = cccd;
        this.address = address;
        this.businessName = businessName;
        this.taxCode = taxCode;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
