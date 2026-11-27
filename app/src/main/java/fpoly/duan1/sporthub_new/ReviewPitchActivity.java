package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReviewPitchActivity extends AppCompatActivity {

    private TextView btnSkip;
    private TextView tvTagGrass, tvTagLight, tvTagPrice, tvTagParking;
    private EditText etReviewComment;
    private TextView btnSubmitReview;

    private boolean isTagGrassSelected = true;
    private boolean isTagLightSelected = false;
    private boolean isTagPriceSelected = false;
    private boolean isTagParkingSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_pitch);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainReviewPitch), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupTagListeners();
        setupListeners();
    }

    private void initViews() {
        btnSkip = findViewById(R.id.btnSkip);

        tvTagGrass = findViewById(R.id.tvTagGrass);
        tvTagLight = findViewById(R.id.tvTagLight);
        tvTagPrice = findViewById(R.id.tvTagPrice);
        tvTagParking = findViewById(R.id.tvTagParking);

        etReviewComment = findViewById(R.id.etReviewComment);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
    }

    private void setupTagListeners() {
        tvTagGrass.setOnClickListener(v -> {
            isTagGrassSelected = !isTagGrassSelected;
            updateTagUI(tvTagGrass, isTagGrassSelected);
        });

        tvTagLight.setOnClickListener(v -> {
            isTagLightSelected = !isTagLightSelected;
            updateTagUI(tvTagLight, isTagLightSelected);
        });

        tvTagPrice.setOnClickListener(v -> {
            isTagPriceSelected = !isTagPriceSelected;
            updateTagUI(tvTagPrice, isTagPriceSelected);
        });

        tvTagParking.setOnClickListener(v -> {
            isTagParkingSelected = !isTagParkingSelected;
            updateTagUI(tvTagParking, isTagParkingSelected);
        });
    }

    private void updateTagUI(TextView tagView, boolean isSelected) {
        if (isSelected) {
            tagView.setBackgroundResource(R.drawable.bg_tab_selected);
            tagView.setTextColor(ContextCompat.getColor(this, R.color.text_title));
            tagView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tagView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            tagView.setTextColor(ContextCompat.getColor(this, R.color.text_sub));
            tagView.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void setupListeners() {
        btnSkip.setOnClickListener(v -> finish());

        btnSubmitReview.setOnClickListener(v -> {
            fpoly.duan1.sporthub_new.utils.ReviewManager.getInstance().setReviewed(this, "b3", true);
            Intent intent = new Intent(ReviewPitchActivity.this, ReviewSuccessActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
