package fpoly.duan1.sporthub_new.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.MatchTeam;

public class MatchRepository {

    private static MatchRepository instance;
    private final List<MatchTeam> matchTeamList = new ArrayList<>();

    private MatchRepository() {
        initSampleData();
    }

    public static synchronized MatchRepository getInstance() {
        if (instance == null) {
            instance = new MatchRepository();
        }
        return instance;
    }

    private void initSampleData() {
        matchTeamList.clear();

        // Item 1
        matchTeamList.add(new MatchTeam(
                "m1",
                "FC Cầu Giấy United",
                "Hoàng Nam",
                "1,2 km",
                "Khá",
                "T4 · 19:00",
                "Sân 7",
                "50 / 50",
                "FC",
                R.drawable.bg_team_avatar_green,
                R.drawable.bg_skill_badge_orange,
                Color.parseColor("#8F5300")
        ));

        // Item 2
        matchTeamList.add(new MatchTeam(
                "m2",
                "Thăng Long Rangers",
                "Minh Đức",
                "2,6 km",
                "Trung bình",
                "T6 · 20:00",
                "Sân 5",
                "Thua trả",
                "TL",
                R.drawable.bg_team_avatar_green,
                R.drawable.bg_skill_badge_green,
                Color.parseColor("#0A6A3A")
        ));

        // Item 3
        matchTeamList.add(new MatchTeam(
                "m3",
                "Mai Dịch Old Boys",
                "Tuấn Anh",
                "3,5 km",
                "Phong trào",
                "T7 · 18:30",
                "Sân 7",
                "50 / 50",
                "MD",
                R.drawable.bg_team_avatar_green,
                R.drawable.bg_skill_badge_green,
                Color.parseColor("#0A6A3A")
        ));
    }

    public List<MatchTeam> getMatches() {
        return matchTeamList;
    }

    public void addMatch(MatchTeam match) {
        matchTeamList.add(0, match);
    }
}
