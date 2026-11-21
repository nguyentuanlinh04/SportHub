package fpoly.duan1.sporthub_new.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.adapter.FilterChipAdapter;
import fpoly.duan1.sporthub_new.adapter.MatchTeamAdapter;
import fpoly.duan1.sporthub_new.model.FilterChip;
import fpoly.duan1.sporthub_new.model.MatchTeam;

public class MatchmakingFragment extends Fragment {

    private RecyclerView rvMatchFilterChips;
    private RecyclerView rvMatchTeams;
    private TextView btnPostMatch;

    private FilterChipAdapter filterChipAdapter;
    private MatchTeamAdapter matchTeamAdapter;

    private final List<FilterChip> filterChips = new ArrayList<>();
    private final List<MatchTeam> teamList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matchmaking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupFilterChips();
        setupMatchTeams();
    }

    private void initViews(View view) {
        rvMatchFilterChips = view.findViewById(R.id.rvMatchFilterChips);
        rvMatchTeams = view.findViewById(R.id.rvMatchTeams);
        btnPostMatch = view.findViewById(R.id.btnPostMatch);

        btnPostMatch.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Mở màn hình Đăng tin ghép kèo", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupFilterChips() {
        filterChips.clear();
        filterChips.add(new FilterChip("Quanh tôi", true, false));
        filterChips.add(new FilterChip("Sân 5", false, true));
        filterChips.add(new FilterChip("Sân 7", false, true));
        filterChips.add(new FilterChip("Trình độ", false, false));
        filterChips.add(new FilterChip("Giờ", false, false));

        filterChipAdapter = new FilterChipAdapter(requireContext(), filterChips, (chip, position) -> {
            Toast.makeText(requireContext(), "Lọc theo: " + chip.getTitle(), Toast.LENGTH_SHORT).show();
        });

        rvMatchFilterChips.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMatchFilterChips.setAdapter(filterChipAdapter);
    }

    private void setupMatchTeams() {
        teamList.clear();

        // Item 1 (FC Cầu Giấy United)
        teamList.add(new MatchTeam(
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
                R.drawable.bg_level_badge_orange,
                Color.parseColor("#8F5300")
        ));

        // Item 2 (Thăng Long Rangers)
        teamList.add(new MatchTeam(
                "m2",
                "Thăng Long Rangers",
                "Minh Đức",
                "2,6 km",
                "Trung bình",
                "T6 · 20:00",
                "Sân 5",
                "Thua trả",
                "TL",
                R.drawable.bg_team_avatar_blue,
                R.drawable.bg_level_badge_green,
                Color.parseColor("#0A6A3A")
        ));

        // Item 3 (Mai Dịch Old Boys)
        teamList.add(new MatchTeam(
                "m3",
                "Mai Dịch Old Boys",
                "Tuấn Anh",
                "3,5 km",
                "Vui vẻ",
                "T7 · 18:30",
                "Sân 7",
                "50 / 50",
                "MD",
                R.drawable.bg_team_avatar_red,
                R.drawable.bg_level_badge_mint,
                Color.parseColor("#0A6A3A")
        ));

        matchTeamAdapter = new MatchTeamAdapter(requireContext(), teamList, new MatchTeamAdapter.OnMatchActionListener() {
            @Override
            public void onRequestClick(MatchTeam team) {
                Toast.makeText(requireContext(), "Đã gửi yêu cầu giao lưu tới " + team.getTeamName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChatClick(MatchTeam team) {
                Toast.makeText(requireContext(), "Mở trò chuyện với đội " + team.getTeamName(), Toast.LENGTH_SHORT).show();
            }
        });

        rvMatchTeams.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMatchTeams.setAdapter(matchTeamAdapter);
    }
}
