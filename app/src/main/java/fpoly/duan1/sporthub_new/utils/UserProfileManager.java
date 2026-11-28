package fpoly.duan1.sporthub_new.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserProfileManager {

    private static final String PREF_NAME = "sporthub_user_profile_pref";
    private static final String KEY_NAME = "user_full_name";
    private static final String KEY_PHONE = "user_phone_number";
    private static final String KEY_ADDRESS = "user_address";
    private static final String KEY_EMAIL = "user_email";

    private static UserProfileManager instance;

    private UserProfileManager() {}

    public static synchronized UserProfileManager getInstance() {
        if (instance == null) {
            instance = new UserProfileManager();
        }
        return instance;
    }

    private SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getFullName(Context context) {
        return getPrefs(context).getString(KEY_NAME, "Minh");
    }

    public String getPhoneNumber(Context context) {
        return getPrefs(context).getString(KEY_PHONE, "+84 912 345 678");
    }

    public String getAddress(Context context) {
        return getPrefs(context).getString(KEY_ADDRESS, "Cầu Giấy, Hà Nội");
    }

    public String getEmail(Context context) {
        return getPrefs(context).getString(KEY_EMAIL, "minh.nguyen@gmail.com");
    }

    public void updateProfile(Context context, String name, String phone, String address, String email) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
}
