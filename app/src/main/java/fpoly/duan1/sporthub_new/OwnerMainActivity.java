package fpoly.duan1.sporthub_new;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
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
    private EditText edtSearchComplex;

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
        setupSearch();
        setupActionButtons();
        setupBottomNav();
    }

    private void initViews() {
        chipFilterAll = findViewById(R.id.chipFilterAll);
        chipFilterActive = findViewById(R.id.chipFilterActive);
        chipFilterPaused = findViewById(R.id.chipFilterPaused);
        edtSearchComplex = findViewById(R.id.edtSearchComplex);

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

    private void setupSearch() {
        edtSearchComplex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySearchAndFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void applySearchAndFilter() {
        String query = edtSearchComplex.getText() != null
                ? edtSearchComplex.getText().toString().trim().toLowerCase() : "";

        boolean showThangLong = matchesQuery("cụm sân thăng long 152 hoàng quốc việt cầu giấy hà nội", query);
        boolean showMaiDich = matchesQuery("cụm sân mai dịch 28 đường số 2 bình an tp hà nội", query);
        boolean showRiverside = matchesQuery("cụm sân riverside 66 nguyễn văn hưởng thảo điền tp thủ đức tphcm", query);

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

    private boolean matchesQuery(String content, String query) {
        if (query.isEmpty()) return true;
        return content.toLowerCase().contains(query);
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

        // Nút Quản lý từng cụm sân -> Mở OwnerComplexDetailActivity
        View btnManageThangLong = findViewById(R.id.btnManageThangLong);
        if (btnManageThangLong != null) {
            btnManageThangLong.setOnClickListener(v -> openComplexDetail("Cụm Sân Thăng Long"));
        }

        View btnManageMaiDich = findViewById(R.id.btnManageMaiDich);
        if (btnManageMaiDich != null) {
            btnManageMaiDich.setOnClickListener(v -> openComplexDetail("Cụm Sân Mai Dịch"));
        }

        View btnManageRiverside = findViewById(R.id.btnManageRiverside);
        if (btnManageRiverside != null) {
            btnManageRiverside.setOnClickListener(v -> openComplexDetail("Cụm Sân Riverside"));
        }

        // 4 Quick Actions trên Hero Dashboard Card
        View btnQuickInvoice = findViewById(R.id.btnQuickInvoice);
        if (btnQuickInvoice != null) {
            btnQuickInvoice.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerFinanceActivity.class);
                startActivity(intent);
            });
        }

        View btnQuickSchedule = findViewById(R.id.btnQuickSchedule);
        if (btnQuickSchedule != null) {
            btnQuickSchedule.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerScheduleActivity.class);
                startActivity(intent);
            });
        }

        View btnQuickStaff = findViewById(R.id.btnQuickStaff);
        if (btnQuickStaff != null) {
            btnQuickStaff.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerStaffActivity.class);
                startActivity(intent);
            });
        }

        View btnQuickFinance = findViewById(R.id.btnQuickFinance);
        if (btnQuickFinance != null) {
            btnQuickFinance.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(OwnerMainActivity.this, OwnerFinanceActivity.class);
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
                startActivity(new android.content.Intent(OwnerMainActivity.this, OwnerScheduleActivity.class));
            });
        }
        if (navStaff != null) {
            navStaff.setOnClickListener(v -> {
                startActivity(new android.content.Intent(OwnerMainActivity.this, OwnerStaffActivity.class));
            });
        }
        if (navFinance != null) {
            navFinance.setOnClickListener(v -> {
                startActivity(new android.content.Intent(OwnerMainActivity.this, OwnerFinanceActivity.class));
            });
        }
        if (navReviews != null) {
            navReviews.setOnClickListener(v -> {
                startActivity(new android.content.Intent(OwnerMainActivity.this, OwnerReviewActivity.class));
            });
        }
    }
}
