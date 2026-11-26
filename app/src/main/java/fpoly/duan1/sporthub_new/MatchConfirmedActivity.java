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

public class MatchConfirmedActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvBannerTitle, tvOpponentTeamName;
    private TextView btnViewInSchedule, btnCancelMatch;

    private String opponentName = "FC Cầu Giấy United";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match_confirmed);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMatchConfirmed), (v, insets) -> {
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
        tvBannerTitle = findViewById(R.id.tvBannerTitle);
        tvOpponentTeamName = findViewById(R.id.tvOpponentTeamName);

        btnViewInSchedule = findViewById(R.id.btnViewInSchedule);
        btnCancelMatch = findViewById(R.id.btnCancelMatch);
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("opponent_name");
            if (name != null && !name.isEmpty()) {
                opponentName = name;
                tvOpponentTeamName.setText(opponentName);
            }
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnViewInSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(MatchConfirmedActivity.this, MenuActivity.class);
            intent.putExtra("OPEN_TAB", 2);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnCancelMatch.setOnClickListener(v -> showCancelConfirmation());
    }

    private void showCancelConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Hủy kèo giao hữu")
                .setMessage("Bạn có chắc chắn muốn hủy kèo đấu với " + opponentName + " không?")
                .setPositiveButton("Hủy kèo", (dialog, which) -> {
                    Toast.makeText(this, "Đã hủy kèo giao hữu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Quay lại", null)
                .show();
    }
}
