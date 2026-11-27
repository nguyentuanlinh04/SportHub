package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

            if (!shouldShow) continue;
            countShown++;

            View cardView = getLayoutInflater().inflate(R.layout.item_owner_complex_card, containerComplexCards, false);

            TextView tvName = cardView.findViewById(R.id.tvItemComplexName);
            TextView tvStatusBadge = cardView.findViewById(R.id.tvItemComplexStatusBadge);
            TextView tvSubBadge = cardView.findViewById(R.id.tvItemComplexSubBadge);
            TextView tvAddress = cardView.findViewById(R.id.tvItemComplexAddress);
            TextView tvHours = cardView.findViewById(R.id.tvItemComplexHours);
            TextView tvSubPitches = cardView.findViewById(R.id.tvItemComplexSubPitches);
            View btnManage = cardView.findViewById(R.id.btnItemManageComplex);
            View btnDelete = cardView.findViewById(R.id.btnItemDeleteComplex);

            if (tvName != null) tvName.setText(c.getName());
            if (tvAddress != null) tvAddress.setText(c.getAddress());
            if (tvHours != null) tvHours.setText(c.getHours());

            if (tvStatusBadge != null) {
                if ("ACTIVE".equalsIgnoreCase(c.getStatus())) {
                    tvStatusBadge.setText("Hoạt động");
                    tvStatusBadge.setBackgroundResource(R.drawable.bg_owner_status_active);
                    tvStatusBadge.setTextColor(Color.parseColor("#166534"));
                } else {
                    tvStatusBadge.setText("Tạm ngưng");
                    tvStatusBadge.setBackgroundResource(R.drawable.bg_owner_status_paused);
                    tvStatusBadge.setTextColor(Color.parseColor("#991B1B"));
                }
            }

            if (tvSubBadge != null) {
                if (c.isCustom()) {
                    tvSubBadge.setVisibility(View.VISIBLE);
                    tvSubBadge.setText("Mới thêm");
                    tvSubBadge.setBackgroundResource(R.drawable.bg_owner_status_active);
                    tvSubBadge.setTextColor(Color.parseColor("#166534"));
                } else if ("rs".equals(c.getId())) {
                    tvSubBadge.setVisibility(View.VISIBLE);
                    tvSubBadge.setText("Mẫu minh họa");
                    tvSubBadge.setBackground(null);
                    tvSubBadge.setTextColor(Color.parseColor("#64748B"));
                } else {
                    tvSubBadge.setVisibility(View.GONE);
                }
            }

            if (tvSubPitches != null) {
                if (c.getSubPitchesCount() > 0) {
                    tvSubPitches.setText(String.format(Locale.getDefault(), "%d sân con", c.getSubPitchesCount()));
                } else {
                    tvSubPitches.setText("0 sân con · Chưa thiết lập");
                }
            }

            // Click vào thẻ hoặc nút Quản lý
            View.OnClickListener clickListener = v -> openComplexDetail(c.getName());
            cardView.setOnClickListener(clickListener);
            if (btnManage != null) {
                btnManage.setOnClickListener(clickListener);
            }

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

        if (tvComplexCountHeader != null) {
            tvComplexCountHeader.setText(String.format(Locale.getDefault(), "%d cơ sở", countShown));
        }
    }

    private void setupActionButtons() {
        // + Thêm cụm sân
        View btnAddNewComplex = findViewById(R.id.btnAddNewComplex);
        if (btnAddNewComplex != null) {
            btnAddNewComplex.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerMainActivity.this, AddComplexActivity.class);
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
                Intent intent = new Intent(OwnerMainActivity.this, OwnerScheduleActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navStaff != null) {
            navStaff.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerMainActivity.this, OwnerStaffActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navFinance != null) {
            navFinance.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerMainActivity.this, OwnerFinanceActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
        if (navReviews != null) {
            navReviews.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerMainActivity.this, OwnerReviewActivity.class);
                intent.putExtra("COMPLEX_NAME", "Tất cả các sân");
                startActivity(intent);
            });
        }
    }
}
