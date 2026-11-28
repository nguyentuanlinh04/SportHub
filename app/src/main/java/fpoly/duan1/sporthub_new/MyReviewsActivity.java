package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.adapter.UserReviewAdapter;
import fpoly.duan1.sporthub_new.model.UserReviewItem;

public class MyReviewsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvMyReviews;
    private LinearLayout llEmptyReviews;

    private UserReviewAdapter adapter;
    private final List<UserReviewItem> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_reviews);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMyReviews), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupSampleData();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        rvMyReviews = findViewById(R.id.rvMyReviews);
        llEmptyReviews = findViewById(R.id.llEmptyReviews);
    }

    private void setupSampleData() {
        reviewList.clear();

        // Item 1 (Cụm sân Thắng Long)
        reviewList.add(new UserReviewItem(
                "r1",
                "Cụm sân Thắng Long",
                "SH-1101-0120 · 01/11/2026 · Sân 5-A",
                "Mặt cỏ 5/5 · Ánh sáng 5/5 · Phục vụ 5/5",
                "Mặt cỏ tốt · Đèn sáng",
                "“Sân đẹp, mặt cỏ êm và đèn sáng. Nhân viên nhiệt tình, đội mình sẽ quay lại!”",
                "Đã đăng ngày 01/11/2026",
                5
        ));

        // Item 2 (Sân bóng Mai Dịch)
        reviewList.add(new UserReviewItem(
                "r2",
                "Sân bóng Mai Dịch",
                "SH-1025-0066 · 25/10/2026 · Sân 5",
                "Mặt cỏ 5/5 · Ánh sáng 5/5 · Phục vụ 5/5",
                "Giá hợp lý · Dễ gửi xe",
                "“Sân rộng rãi, thoáng mát, chỗ gửi xe rộng rãi không lo hết chỗ.”",
                "Đã đăng ngày 25/10/2026",
                5
        ));
    }

    private void setupRecyclerView() {
        adapter = new UserReviewAdapter(this, reviewList);
        rvMyReviews.setLayoutManager(new LinearLayoutManager(this));
        rvMyReviews.setAdapter(adapter);

        if (reviewList.isEmpty()) {
            rvMyReviews.setVisibility(View.GONE);
            llEmptyReviews.setVisibility(View.VISIBLE);
        } else {
            rvMyReviews.setVisibility(View.VISIBLE);
            llEmptyReviews.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}
