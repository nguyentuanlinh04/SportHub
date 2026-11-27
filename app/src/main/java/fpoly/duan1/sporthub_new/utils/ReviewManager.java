package fpoly.duan1.sporthub_new.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ReviewManager {

    private static final String PREF_NAME = "sporthub_review_pref";

    private static ReviewManager instance;

    private ReviewManager() {}

    public static synchronized ReviewManager getInstance() {
        if (instance == null) {
            instance = new ReviewManager();
        }
        return instance;
    }

    private SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isReviewed(Context context, String bookingId) {
        // Default b4 is reviewed for demo, b3 is reviewed if set in pref
        if ("b4".equals(bookingId)) {
            return true;
        }
        return getPrefs(context).getBoolean("reviewed_" + bookingId, false);
    }

    public void setReviewed(Context context, String bookingId, boolean reviewed) {
        getPrefs(context).edit().putBoolean("reviewed_" + bookingId, reviewed).apply();
    }
}
