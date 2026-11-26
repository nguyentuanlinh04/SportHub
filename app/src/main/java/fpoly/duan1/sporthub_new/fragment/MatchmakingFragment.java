package fpoly.duan1.sporthub_new.fragment;

import android.content.Intent;
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

import fpoly.duan1.sporthub_new.CreateMatchPostActivity;
import fpoly.duan1.sporthub_new.MatchDetailActivity;
import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.adapter.FilterChipAdapter;
import fpoly.duan1.sporthub_new.adapter.MatchTeamAdapter;
import fpoly.duan1.sporthub_new.model.FilterChip;
import fpoly.duan1.sporthub_new.model.MatchTeam;
import fpoly.duan1.sporthub_new.utils.MatchRepository;

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

    @Override
    public void onResume() {
        super.onResume();
        loadMatchData();
    }

    private void initViews(View view) {
        rvMatchFilterChips = view.findViewById(R.id.rvMatchFilterChips);
        rvMatchTeams = view.findViewById(R.id.rvMatchTeams);
        btnPostMatch = view.findViewById(R.id.btnPostMatch);

        btnPostMatch.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), CreateMatchPostActivity.class))
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
        matchTeamAdapter = new MatchTeamAdapter(requireContext(), teamList, team -> {
            Intent intent = new Intent(requireContext(), MatchDetailActivity.class);
            intent.putExtra("team_name", team.getTeamName());
            intent.putExtra("avatar_text", team.getAvatarText());
            intent.putExtra("captain_distance", "Đội trưởng: " + team.getCaptainName() + " · Cách bạn " + team.getDistance());
            intent.putExtra("skill_level", team.getLevelText());
            intent.putExtra("match_time", team.getTimeText());
            intent.putExtra("pitch_type", team.getPitchType());
            intent.putExtra("split_fee", team.getSplitFee());
            startActivity(intent);
        });

        rvMatchTeams.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMatchTeams.setAdapter(matchTeamAdapter);

        loadMatchData();
    }

    private void loadMatchData() {
        teamList.clear();
        teamList.addAll(MatchRepository.getInstance().getMatches());

        if (matchTeamAdapter != null) {
            matchTeamAdapter.notifyDataSetChanged();
        }
    }
}
