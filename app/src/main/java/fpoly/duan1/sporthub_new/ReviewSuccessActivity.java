package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReviewSuccessActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView btnViewMyReviews, btnRebookPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_success);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainReviewSuccess), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnViewMyReviews = findViewById(R.id.btnViewMyReviews);
        btnRebookPitch = findViewById(R.id.btnRebookPitch);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnViewMyReviews.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewSuccessActivity.this, MenuActivity.class);
            intent.putExtra("OPEN_TAB", 3); // Tab Cá nhân
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnRebookPitch.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewSuccessActivity.this, PitchDetailActivity.class);
            intent.putExtra("pitch_name", "Cụm sân Thắng Long");
            startActivity(intent);
            finish();
        });
    }
}
