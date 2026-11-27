package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.model.PitchComplex;
import fpoly.duan1.sporthub_new.utils.FavoriteManager;

public class PitchDetailActivity extends AppCompatActivity {

    private ImageButton btnBack, btnFavorite;
    private TextView tvPhotoCount, tvDetailPitchName, tvDetailRating;
    private TextView tvDetailAddress, tvDetailDistance, btnDirections;
    private TextView tvDetailBottomPrice, btnSelectBookingTime;

    private boolean isFavorite = false;
    private String pitchName = "Cụm sân Thắng Long";
    private String pitchAddressText = "12 Trần Thái Tông, Cầu Giấy, Hà Nội";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pitch_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPitchDetail), (v, insets) -> {
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
        btnFavorite = findViewById(R.id.btnFavorite);
        tvPhotoCount = findViewById(R.id.tvPhotoCount);

        tvDetailPitchName = findViewById(R.id.tvDetailPitchName);
        tvDetailRating = findViewById(R.id.tvDetailRating);

        tvDetailAddress = findViewById(R.id.tvDetailAddress);
        tvDetailDistance = findViewById(R.id.tvDetailDistance);
        btnDirections = findViewById(R.id.btnDirections);

        tvDetailBottomPrice = findViewById(R.id.tvDetailBottomPrice);
        btnSelectBookingTime = findViewById(R.id.btnSelectBookingTime);
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("pitch_name");
            if (name != null && !name.isEmpty()) {
                pitchName = name;
                tvDetailPitchName.setText(pitchName);
            }

            double rating = intent.getDoubleExtra("pitch_rating", 4.8);
            int reviews = intent.getIntExtra("pitch_review_count", 126);
            tvDetailRating.setText(String.format("%s (%d đánh giá)", rating, reviews));

            String addr = intent.getStringExtra("pitch_address");
            if (addr != null && !addr.isEmpty()) {
                if (addr.contains("·")) {
                    String[] parts = addr.split("·");
                    tvDetailDistance.setText("Cách bạn " + parts[0].trim());
                    pitchAddressText = parts[1].trim() + ", Hà Nội";
                    tvDetailAddress.setText(pitchAddressText);
                } else {
                    tvDetailAddress.setText(addr);
                }
            }

            String price = intent.getStringExtra("pitch_price");
            if (price != null && !price.isEmpty()) {
                tvDetailBottomPrice.setText(price);
            }
        }

        isFavorite = FavoriteManager.getInstance().isFavorite(this, pitchName);
        btnFavorite.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnFavorite.setOnClickListener(v -> {
            List<String> tags = new ArrayList<>();
            tags.add("Sân 5");
            tags.add("Sân 7");
            PitchComplex dummy = new PitchComplex("p_detail", pitchName, pitchAddressText, 4.8, 126, tags, tvDetailBottomPrice.getText().toString(), "Còn trống", true);

            isFavorite = FavoriteManager.getInstance().toggleFavorite(this, dummy);
            btnFavorite.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            Toast.makeText(this, isFavorite ? "Đã thêm vào danh sách yêu thích!" : "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
        });

        btnDirections.setOnClickListener(v -> {
            try {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(pitchAddressText));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Chỉ đường tới: " + pitchAddressText, Toast.LENGTH_SHORT).show();
            }
        });

        btnSelectBookingTime.setOnClickListener(v -> {
            Intent intent = new Intent(PitchDetailActivity.this, BookingTimeActivity.class);
            intent.putExtra("pitch_name", pitchName);
            startActivity(intent);
        });
    }
}
