package fpoly.duan1.sporthub_new;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerMainActivity extends AppCompatActivity {

    private TextView chipFilterAll;
    private TextView chipFilterActive;
    private TextView chipFilterPaused;

    private View cardThangLong;
    private View cardMaiDich;
    private View cardRiverside;
    private TextView tvComplexCountHeader;

    private String currentFilter = "ALL"; // ALL, ACTIVE, PAUSED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwner), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupFilters();
        setupActionButtons();
        setupBottomNav();
    }

    private void initViews() {
        chipFilterAll = findViewById(R.id.chipFilterAll);
        chipFilterActive = findViewById(R.id.chipFilterActive);
        chipFilterPaused = findViewById(R.id.chipFilterPaused);

        cardThangLong = findViewById(R.id.cardThangLong);
        cardMaiDich = findViewById(R.id.cardMaiDich);
        cardRiverside = findViewById(R.id.cardRiverside);
        tvComplexCountHeader = findViewById(R.id.tvComplexCountHeader);
    }

    private void setupFilters() {
        chipFilterAll.setOnClickListener(v -> setFilter("ALL"));
        chipFilterActive.setOnClickListener(v -> setFilter("ACTIVE"));
        chipFilterPaused.setOnClickListener(v -> setFilter("PAUSED"));
    }

    private void setFilter(String filter) {
        currentFilter = filter;
        int unselectedColor = ContextCompat.getColor(this, R.color.owner_chip_unselected_text);

        // Reset All Chips
        chipFilterAll.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chipFilterAll.setTextColor(unselectedColor);
        chipFilterAll.setTypeface(null, Typeface.NORMAL);

        chipFilterActive.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chipFilterActive.setTextColor(unselectedColor);
        chipFilterActive.setTypeface(null, Typeface.NORMAL);

        chipFilterPaused.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chipFilterPaused.setTextColor(unselectedColor);
        chipFilterPaused.setTypeface(null, Typeface.NORMAL);

        // Highlight Selected Chip
        switch (filter) {
            case "ALL":
                chipFilterAll.setBackgroundResource(R.drawable.bg_owner_chip_selected);
                chipFilterAll.setTextColor(Color.WHITE);
                chipFilterAll.setTypeface(null, Typeface.BOLD);
                break;
            case "ACTIVE":
                chipFilterActive.setBackgroundResource(R.drawable.bg_owner_chip_selected);
                chipFilterActive.setTextColor(Color.WHITE);
                chipFilterActive.setTypeface(null, Typeface.BOLD);
                break;
            case "PAUSED":
                chipFilterPaused.setBackgroundResource(R.drawable.bg_owner_chip_selected);
                chipFilterPaused.setTextColor(Color.WHITE);
                chipFilterPaused.setTypeface(null, Typeface.BOLD);
                break;
        }

        applySearchAndFilter();
    }

    private void applySearchAndFilter() {
        boolean showThangLong = true;
        boolean showMaiDich = true;
        boolean showRiverside = true;

        if ("ACTIVE".equals(currentFilter)) {
            showRiverside = false; // Riverside is Paused
        } else if ("PAUSED".equals(currentFilter)) {
            showThangLong = false; // Thăng Long is Active
            showMaiDich = false;   // Mai Dịch is Active
        }

        cardThangLong.setVisibility(showThangLong ? View.VISIBLE : View.GONE);
        cardMaiDich.setVisibility(showMaiDich ? View.VISIBLE : View.GONE);
        cardRiverside.setVisibility(showRiverside ? View.VISIBLE : View.GONE);

        int count = 0;
        if (showThangLong) count++;
        if (showMaiDich) count++;
        if (showRiverside) count++;

        tvComplexCountHeader.setText(String.format(java.util.Locale.getDefault(), "%d cơ sở", count));
    }

    private void setupActionButtons() {
        // + Thêm cụm sân
        View btnAddNewComplex = findViewById(R.id.btnAddNewComplex);
        if (btnAddNewComplex != null) {
            btnAddNewComplex.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, AddComplexActivity.class);
                startActivity(intent);
            });
        }

        // Nút Quản lý & bấm vào toàn bộ Card từng cụm sân -> Mở OwnerComplexDetailActivity
        if (cardThangLong != null) {
            cardThangLong.setOnClickListener(v -> openComplexDetail("Cụm Sân Thăng Long"));
        }
        View btnManageThangLong = findViewById(R.id.btnManageThangLong);
        if (btnManageThangLong != null) {
            btnManageThangLong.setOnClickListener(v -> openComplexDetail("Cụm Sân Thăng Long"));
        }

        if (cardMaiDich != null) {
            cardMaiDich.setOnClickListener(v -> openComplexDetail("Cụm Sân Mai Dịch"));
        }
        View btnManageMaiDich = findViewById(R.id.btnManageMaiDich);
        if (btnManageMaiDich != null) {
            btnManageMaiDich.setOnClickListener(v -> openComplexDetail("Cụm Sân Mai Dịch"));
        }

        if (cardRiverside != null) {
            cardRiverside.setOnClickListener(v -> openComplexDetail("Cụm Sân Riverside"));
        }
        View btnManageRiverside = findViewById(R.id.btnManageRiverside);
        if (btnManageRiverside != null) {
            btnManageRiverside.setOnClickListener(v -> openComplexDetail("Cụm Sân Riverside"));
        }

        // Badge "Chủ sân" trên Top Bar -> Mở OwnerProfileActivity
        View badgeRoleOwner = findViewById(R.id.badgeRoleOwner);
        if (badgeRoleOwner != null) {
            badgeRoleOwner.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void openComplexDetail(String complexName) {
        android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerComplexDetailActivity.class);
        intent.putExtra("COMPLEX_NAME", complexName);
        startActivity(intent);
    }

    private void setupBottomNav() {
        View navFacilities = findViewById(R.id.navFacilities);
        View navSchedule = findViewById(R.id.navSchedule);
        View navStaff = findViewById(R.id.navStaff);
        View navFinance = findViewById(R.id.navFinance);
        View navReviews = findViewById(R.id.navReviews);

        if (navFacilities != null) {
            navFacilities.setOnClickListener(v -> {
                // Đang ở tab Cơ sở
            });
        }
        if (navSchedule != null) {
            navSchedule.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerScheduleActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navStaff != null) {
            navStaff.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerStaffActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navFinance != null) {
            navFinance.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerFinanceActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navReviews != null) {
            navReviews.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerReviewActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
    }
}
