package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Locale;

import fpoly.duan1.sporthub_new.helper.OwnerComplexManager;
import fpoly.duan1.sporthub_new.model.OwnerComplex;

public class OwnerMainActivity extends AppCompatActivity {

    private TextView chipFilterAll;
    private TextView chipFilterActive;
    private TextView chipFilterPaused;

    private TextView tvTotalComplexes;
    private TextView tvActiveComplexes;
    private TextView tvSubPitchesCount;
    private TextView tvComplexCountHeader;
    private LinearLayout containerComplexCards;

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

    @Override
    protected void onResume() {
        super.onResume();
        renderComplexCards();
    }

    private void initViews() {
        chipFilterAll = findViewById(R.id.chipFilterAll);
        chipFilterActive = findViewById(R.id.chipFilterActive);
        chipFilterPaused = findViewById(R.id.chipFilterPaused);

        tvTotalComplexes = findViewById(R.id.tvTotalComplexes);
        tvActiveComplexes = findViewById(R.id.tvActiveComplexes);
        tvSubPitchesCount = findViewById(R.id.tvSubPitchesCount);
        tvComplexCountHeader = findViewById(R.id.tvComplexCountHeader);
        containerComplexCards = findViewById(R.id.containerComplexCards);
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

        renderComplexCards();
    }

    private void renderComplexCards() {
        if (containerComplexCards == null) return;
        containerComplexCards.removeAllViews();

        OwnerComplexManager manager = OwnerComplexManager.getInstance();
        List<OwnerComplex> list = manager.getComplexList();

        int countShown = 0;

        for (OwnerComplex c : list) {
            boolean shouldShow = true;
            if ("ACTIVE".equals(currentFilter) && !"ACTIVE".equalsIgnoreCase(c.getStatus())) {
                shouldShow = false;
            } else if ("PAUSED".equals(currentFilter) && !"PAUSED".equalsIgnoreCase(c.getStatus())) {
                shouldShow = false;
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

            // Xóa cụm sân
            if (btnDelete != null) {
                btnDelete.setOnClickListener(v -> confirmDeleteComplex(c));
            }

            containerComplexCards.addView(cardView);
        }

        // Cập nhật số liệu thống kê
        if (tvTotalComplexes != null) {
            tvTotalComplexes.setText(String.valueOf(manager.getTotalCount()));
        }
        if (tvActiveComplexes != null) {
            tvActiveComplexes.setText(String.valueOf(manager.getActiveCount()));
        }
        if (tvSubPitchesCount != null) {
            tvSubPitchesCount.setText(String.valueOf(manager.getTotalSubPitches()));
        }

        if (chipFilterAll != null) {
            chipFilterAll.setText(String.format(Locale.getDefault(), "Tất cả (%d)", manager.getTotalCount()));
        }
        if (chipFilterActive != null) {
            chipFilterActive.setText(String.format(Locale.getDefault(), "Hoạt động (%d)", manager.getActiveCount()));
        }
        if (chipFilterPaused != null) {
            chipFilterPaused.setText(String.format(Locale.getDefault(), "Tạm ngưng (%d)", manager.getPausedCount()));
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

        // Badge "Chủ sân" trên Top Bar -> Mở OwnerProfileActivity
        View badgeRoleOwner = findViewById(R.id.badgeRoleOwner);
        if (badgeRoleOwner != null) {
            badgeRoleOwner.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerMainActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void openComplexDetail(String complexName) {
        Intent intent = new Intent(OwnerMainActivity.this, OwnerComplexDetailActivity.class);
        intent.putExtra("COMPLEX_NAME", complexName);
        startActivity(intent);
    }

    private void confirmDeleteComplex(OwnerComplex c) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa cụm sân")
                .setMessage("Bạn có chắc chắn muốn xóa cụm sân \"" + c.getName() + "\" không?\n\nToàn bộ thông tin cơ sở và các sân con thuộc cụm này sẽ bị xóa khỏi hệ thống.")
                .setPositiveButton("Xóa cụm sân", (dialog, which) -> {
                    OwnerComplexManager.getInstance().deleteComplex(c);
                    renderComplexCards();
                    Toast.makeText(this, "Đã xóa cụm sân \"" + c.getName() + "\" thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
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
