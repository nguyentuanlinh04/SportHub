package fpoly.duan1.sporthub_new.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fpoly.duan1.sporthub_new.model.PitchComplex;

public class FavoriteManager {

    private static final String PREF_NAME = "sporthub_favorites_pref";
    private static final String KEY_FAVORITES = "favorite_pitch_names";

    private static FavoriteManager instance;

    private FavoriteManager() {}

    public static synchronized FavoriteManager getInstance() {
        if (instance == null) {
            instance = new FavoriteManager();
        }
        return instance;
    }

    private SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFavorite(Context context, String pitchName) {
        Set<String> favorites = getPrefs(context).getStringSet(KEY_FAVORITES, null);
        if (favorites == null) {
            // Default demo favorites
            return "Cụm sân Thắng Long".equals(pitchName) || "Sân bóng Thượng Đình".equals(pitchName);
        }
        return favorites.contains(pitchName);
    }

    public boolean toggleFavorite(Context context, PitchComplex pitch) {
        Set<String> favorites = getPrefs(context).getStringSet(KEY_FAVORITES, null);
        if (favorites == null) {
            favorites = new HashSet<>();
            favorites.add("Cụm sân Thắng Long");
            favorites.add("Sân bóng Thượng Đình");
        } else {
            favorites = new HashSet<>(favorites);
        }

        boolean nowFavorite;
        if (favorites.contains(pitch.getName())) {
            favorites.remove(pitch.getName());
            nowFavorite = false;
        } else {
            favorites.add(pitch.getName());
            nowFavorite = true;
        }

        getPrefs(context).edit().putStringSet(KEY_FAVORITES, favorites).apply();
        return nowFavorite;
    }

    public List<PitchComplex> getFavoritePitches(Context context) {
        List<PitchComplex> allPitches = getAllSamplePitches();
        List<PitchComplex> favoriteList = new ArrayList<>();

        for (PitchComplex pitch : allPitches) {
            if (isFavorite(context, pitch.getName())) {
                pitch.setFavorite(true);
                favoriteList.add(pitch);
            }
        }
        return favoriteList;
    }

    public int getFavoriteCount(Context context) {
        return getFavoritePitches(context).size();
    }

    private List<PitchComplex> getAllSamplePitches() {
        List<PitchComplex> list = new ArrayList<>();

        List<String> tags1 = new ArrayList<>();
        tags1.add("Sân 5");
        tags1.add("Sân 7");
        tags1.add("Cỏ nhân tạo");

        list.add(new PitchComplex(
                "p1",
                "Cụm sân Thắng Long",
                "1.2 km · 12 Trần Thái Tông, Cầu Giấy",
                4.8,
                126,
                tags1,
                "250.000đ/giờ",
                "Còn trống",
                true
        ));

        List<String> tags2 = new ArrayList<>();
        tags2.add("Sân 7");
        tags2.add("Sân 11");
        tags2.add("Đèn chiếu sáng tốt");

        list.add(new PitchComplex(
                "p2",
                "Sân bóng Thượng Đình",
                "2.5 km · 132 Nguyễn Trãi, Thanh Xuân",
                4.7,
                98,
                tags2,
                "200.000đ/giờ",
                "Còn trống",
                true
        ));

        List<String> tags3 = new ArrayList<>();
        tags3.add("Sân 5");
        tags3.add("Căn tin");

        list.add(new PitchComplex(
                "p3",
                "Cụm sân Mai Dịch",
                "3.1 km · Phố Trần Vĩ, Cầu Giấy",
                4.9,
                210,
                tags3,
                "300.000đ/giờ",
                "Còn trống",
                true
        ));

        return list;
    }
}
