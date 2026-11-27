package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.duan1.sporthub_new.fragment.AcceptMatchBottomSheetFragment;

public class MatchDetailActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvDetailTeamAvatar, tvDetailTeamName, tvDetailCaptainDistance;
    private TextView tvStatusBadge, tvSkillBadge;
    private TextView tvDetailDate, tvDetailTime, tvDetailPitchType, tvDetailSplitFee;
    private TextView tvDetailLocationName, tvDetailLocationAddress, tvDetailNote;
    private TextView btnAcceptMatch;

    private String teamName = "FC Cầu Giấy United";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMatchDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        extractIntentData();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);

        tvDetailTeamAvatar = findViewById(R.id.tvDetailTeamAvatar);
        tvDetailTeamName = findViewById(R.id.tvDetailTeamName);
        tvDetailCaptainDistance = findViewById(R.id.tvDetailCaptainDistance);

        tvStatusBadge = findViewById(R.id.tvStatusBadge);
        tvSkillBadge = findViewById(R.id.tvSkillBadge);

        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailTime = findViewById(R.id.tvDetailTime);
        tvDetailPitchType = findViewById(R.id.tvDetailPitchType);
        tvDetailSplitFee = findViewById(R.id.tvDetailSplitFee);

        tvDetailLocationName = findViewById(R.id.tvDetailLocationName);
        tvDetailLocationAddress = findViewById(R.id.tvDetailLocationAddress);
        tvDetailNote = findViewById(R.id.tvDetailNote);

        btnAcceptMatch = findViewById(R.id.btnAcceptMatch);
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("team_name");
            if (name != null && !name.isEmpty()) {
                teamName = name;
                tvDetailTeamName.setText(teamName);
            }

            String avatar = intent.getStringExtra("avatar_text");
            if (avatar != null && !avatar.isEmpty()) {
                tvDetailTeamAvatar.setText(avatar);
            }

            String captain = intent.getStringExtra("captain_distance");
            if (captain != null && !captain.isEmpty()) {
                tvDetailCaptainDistance.setText(captain);
            }

            String level = intent.getStringExtra("skill_level");
            if (level != null && !level.isEmpty()) {
                tvSkillBadge.setText("Trình độ " + level);
            }

            String time = intent.getStringExtra("match_time");
            if (time != null && !time.isEmpty()) {
                tvDetailTime.setText(time);
            }

            String pitch = intent.getStringExtra("pitch_type");
            if (pitch != null && !pitch.isEmpty()) {
                tvDetailPitchType.setText(pitch);
            }

            String split = intent.getStringExtra("split_fee");
            if (split != null && !split.isEmpty()) {
                tvDetailSplitFee.setText(split);
            }
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnAcceptMatch.setOnClickListener(v -> showAcceptConfirmation());
    }

    private void showAcceptConfirmation() {
        AcceptMatchBottomSheetFragment fragment = AcceptMatchBottomSheetFragment.newInstance(teamName);
        fragment.show(getSupportFragmentManager(), "AcceptMatchBottomSheet");
    }
}
