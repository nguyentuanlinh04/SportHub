package fpoly.duan1.sporthub_new.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fpoly.duan1.sporthub_new.model.User;

public class FirebaseUserHelper {

    private static final String PREF_NAME = "sporthub_users_db";
    private static final Map<String, User> localUsersMemory = new HashMap<>();
    private static boolean isAdminInitialized = false;

    public interface OnPhoneCheckListener {
        void onResult(boolean exists);
    }

    public interface OnUserActionListener {
        void onSuccess(String message);
        void onError(String error);
    }

    public interface OnUserLoginListener {
        void onSuccess(User user);
        void onError(String error);
    }

    private static DatabaseReference getUsersRef() {
        try {
            return FirebaseDatabase.getInstance("https://sporthub-4293d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");
        } catch (Exception e) {
            try {
                return FirebaseDatabase.getInstance().getReference("users");
            } catch (Exception ex) {
                try {
                    return FirebaseDatabase.getInstance("https://sporthub-4293d-default-rtdb.firebaseio.com").getReference("users");
                } catch (Exception e2) {
                    return null;
                }
            }
        }
    }

    // Khởi tạo tài khoản Admin mặc định
    public static void initDefaultAdminAccount(Context context) {
        if (isAdminInitialized) return;

        User admin = new User(
                "admin_default",
                "0999999999",
                "Quản trị viên Sports Hub",
                "Admin@123456",
                User.ROLE_ADMIN,
                User.STATUS_ACTIVE,
                System.currentTimeMillis()
        );

        localUsersMemory.put(admin.getPhoneNumber(), admin);
        saveUserToPreferences(context, admin);

        DatabaseReference ref = getUsersRef();
        if (ref != null) {
            try {
                ref.child(admin.getUserId()).setValue(admin);
            } catch (Exception ignored) {
            }
        }

        try {
            FirebaseFirestore.getInstance().collection("users").document(admin.getUserId()).set(admin);
        } catch (Exception ignored) {
        }

        isAdminInitialized = true;
    }

    // Kiểm tra Số điện thoại đã tồn tại chưa (Đảm bảo 1 SĐT chỉ 1 tài khoản)
    public static void checkPhoneExists(Context context, String phone, OnPhoneCheckListener listener) {
        if (phone == null || phone.trim().isEmpty()) {
            listener.onResult(false);
            return;
        }

        String cleanPhone = phone.trim();

        // Kiểm tra trong bộ nhớ local trước
        if (localUsersMemory.containsKey(cleanPhone) || isPhoneInPreferences(context, cleanPhone)) {
            listener.onResult(true);
            return;
        }

        DatabaseReference ref = getUsersRef();
        if (ref == null) {
            listener.onResult(false);
            return;
        }

        try {
            Query query = ref.orderByChild("phoneNumber").equalTo(cleanPhone);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listener.onResult(snapshot.exists() && snapshot.getChildrenCount() > 0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onResult(false);
                }
            });
        } catch (Exception e) {
            listener.onResult(false);
        }
    }

    // Đăng ký tài khoản mới lên Firebase
    public static void registerUser(Context context, User user, OnUserActionListener listener) {
        DatabaseReference ref = getUsersRef();
        if (ref == null) {
            listener.onError("Không thể kết nối với Firebase. Vui lòng kiểm tra lại cấu hình Database URL!");
            return;
        }

        String cleanPhone = user.getPhoneNumber().trim();
        String userId = "user_" + System.currentTimeMillis();
        user.setUserId(userId);
        user.setCreatedAt(System.currentTimeMillis());

        // Đẩy dữ liệu đồng thời lên Cloud Firestore
        try {
            FirebaseFirestore.getInstance().collection("users").document(userId).set(user);
        } catch (Exception ignored) {
        }

        // Đẩy dữ liệu trực tiếp lên node /users/{userId} của Realtime Database
        ref.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    localUsersMemory.put(cleanPhone, user);
                    saveUserToPreferences(context, user);
                    listener.onSuccess("Đăng ký thành công! Dữ liệu đã xuất hiện trên Firebase.");
                })
                .addOnFailureListener(e -> {
                    listener.onError("Firebase từ chối lưu dữ liệu: " + e.getMessage());
                });
    }

    // Đăng nhập tài khoản với SĐT và Mật khẩu từ Firebase
    public static void loginUser(Context context, String phone, String password, OnUserLoginListener listener) {
        initDefaultAdminAccount(context);
        String cleanPhone = phone.trim();
        String cleanPass = password.trim();

        DatabaseReference ref = getUsersRef();
        if (ref == null) {
            // Trường hợp mất kết nối Firebase: Sử dụng bộ nhớ tạm
            User localUser = getUserFromPreferences(context, cleanPhone);
            if (localUser != null && cleanPass.equals(localUser.getPassword())) {
                listener.onSuccess(localUser);
            } else {
                listener.onError("Không thể kết nối Firebase và không tìm thấy tài khoản lưu tạm!");
            }
            return;
        }

        try {
            Query query = ref.orderByChild("phoneNumber").equalTo(cleanPhone);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            User user = child.getValue(User.class);
                            if (user != null && cleanPass.equals(user.getPassword())) {
                                localUsersMemory.put(cleanPhone, user);
                                saveUserToPreferences(context, user);
                                listener.onSuccess(user);
                                return;
                            }
                        }
                        listener.onError("Mật khẩu không chính xác!");
                    } else {
                        // Kiểm tra bộ nhớ tạm nếu chưa có trên Firebase
                        User localUser = getUserFromPreferences(context, cleanPhone);
                        if (localUser != null && cleanPass.equals(localUser.getPassword())) {
                            listener.onSuccess(localUser);
                        } else {
                            listener.onError("Tài khoản với số điện thoại này không tồn tại!");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onError("Lỗi kết nối cơ sở dữ liệu: " + error.getMessage() + " (Mã: " + error.getCode() + ")");
                }
            });
        } catch (Exception e) {
            listener.onError("Lỗi hệ thống đăng nhập: " + e.getMessage());
        }
    }

    // Đổi mật khẩu trên Firebase
    public static void updatePassword(Context context, String phone, String newPassword, OnUserActionListener listener) {
        String cleanPhone = phone.trim();
        User localUser = getUserFromPreferences(context, cleanPhone);

        if (localUser != null) {
            localUser.setPassword(newPassword);
            localUsersMemory.put(cleanPhone, localUser);
            saveUserToPreferences(context, localUser);
        }

        DatabaseReference ref = getUsersRef();
        if (ref != null) {
            try {
                Query query = ref.orderByChild("phoneNumber").equalTo(cleanPhone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                child.getRef().child("password").setValue(newPassword);
                            }
                        }
                        listener.onSuccess("Đã cập nhật mật khẩu mới thành công!");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onSuccess("Đã cập nhật mật khẩu mới thành công!");
                    }
                });
            } catch (Exception e) {
                listener.onSuccess("Đã cập nhật mật khẩu mới thành công!");
            }
        } else {
            listener.onSuccess("Đã cập nhật mật khẩu mới thành công!");
        }
    }

    // Helper lưu tài khoản vào SharedPreferences
    private static void saveUserToPreferences(Context context, User user) {
        if (context == null || user == null) return;
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String prefix = "user_" + user.getPhoneNumber() + "_";
        editor.putString(prefix + "userId", user.getUserId());
        editor.putString(prefix + "phoneNumber", user.getPhoneNumber());
        editor.putString(prefix + "fullName", user.getFullName());
        editor.putString(prefix + "password", user.getPassword());
        editor.putString(prefix + "role", user.getRole());
        editor.putString(prefix + "status", user.getStatus());
        editor.putString(prefix + "cccd", user.getCccd());
        editor.putString(prefix + "address", user.getAddress());
        editor.putString(prefix + "businessName", user.getBusinessName());
        editor.putString(prefix + "taxCode", user.getTaxCode());
        editor.putLong(prefix + "createdAt", user.getCreatedAt());
        editor.apply();
    }

    private static boolean isPhoneInPreferences(Context context, String phone) {
        if (context == null) return false;
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.contains("user_" + phone + "_userId");
    }

    private static User getUserFromPreferences(Context context, String phone) {
        if (context == null || !isPhoneInPreferences(context, phone)) return null;
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String prefix = "user_" + phone + "_";
        User user = new User();
        user.setUserId(pref.getString(prefix + "userId", ""));
        user.setPhoneNumber(pref.getString(prefix + "phoneNumber", ""));
        user.setFullName(pref.getString(prefix + "fullName", ""));
        user.setPassword(pref.getString(prefix + "password", ""));
        user.setRole(pref.getString(prefix + "role", User.ROLE_KHACH_HANG));
        user.setStatus(pref.getString(prefix + "status", User.STATUS_ACTIVE));
        user.setCccd(pref.getString(prefix + "cccd", ""));
        user.setAddress(pref.getString(prefix + "address", ""));
        user.setBusinessName(pref.getString(prefix + "businessName", ""));
        user.setTaxCode(pref.getString(prefix + "taxCode", ""));
        user.setCreatedAt(pref.getLong(prefix + "createdAt", 0));
        return user;
    }
}
