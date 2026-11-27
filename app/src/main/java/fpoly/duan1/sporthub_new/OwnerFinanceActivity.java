package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerFinanceActivity extends AppCompatActivity {

    private String currentComplexName = "Cụm Sân Thăng Long";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_finance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerFinance), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupBottomNav();
    }

    private void initViews() {
        String passedComplex = getIntent().getStringExtra("COMPLEX_NAME");
        if (passedComplex != null && !passedComplex.isEmpty()) {
            currentComplexName = passedComplex;
        }

        updateFinanceData();

        TextView tvSub = findViewById(R.id.tvFinanceHeaderSubtitle);
        if (tvSub != null) {
            tvSub.setOnClickListener(v -> showComplexSwitcherDialog());
        }

        View btnBack = findViewById(R.id.btnBackFinance);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View badgeRoleFinance = findViewById(R.id.badgeRoleFinance);
        if (badgeRoleFinance != null) {
            badgeRoleFinance.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerFinanceActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }

        View btnExcel = findViewById(R.id.btnExportExcel);
        if (btnExcel != null) {
            btnExcel.setOnClickListener(v ->
                    Toast.makeText(this, "Đang xuất báo cáo Excel (" + currentComplexName + ")...", Toast.LENGTH_SHORT).show()
            );
        }

        View btnCsv = findViewById(R.id.btnExportCsv);
        if (btnCsv != null) {
            btnCsv.setOnClickListener(v ->
                    Toast.makeText(this, "Đang xuất tệp CSV (" + currentComplexName + ")...", Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void updateFinanceData() {
        TextView tvSub = findViewById(R.id.tvFinanceHeaderSubtitle);
        TextView tvTotalRevenueMonth = findViewById(R.id.tvTotalRevenueMonth);
        TextView tvGoldHourFillRate = findViewById(R.id.tvGoldHourFillRate);
        TextView tvCompletedMatches = findViewById(R.id.tvCompletedMatches);

        if (currentComplexName.contains("Tất cả") || currentComplexName.contains("Toàn hệ thống")) {
            if (tvSub != null) tvSub.setText("Tất cả các sân · 3 cơ sở (9 sân) ▾");
            if (tvTotalRevenueMonth != null) tvTotalRevenueMonth.setText("285.650.000đ");
            if (tvGoldHourFillRate != null) tvGoldHourFillRate.setText("94,8%");
            if (tvCompletedMatches != null) tvCompletedMatches.setText("798 trận");
        } else if (currentComplexName.contains("Mai Dịch")) {
            if (tvSub != null) tvSub.setText("Cụm sân Mai Dịch · 4 sân ▾");
            if (tvTotalRevenueMonth != null) tvTotalRevenueMonth.setText("137.000.000đ");
            if (tvGoldHourFillRate != null) tvGoldHourFillRate.setText("93,5%");
            if (tvCompletedMatches != null) tvCompletedMatches.setText("386 trận");
        } else if (currentComplexName.contains("Riverside")) {
            if (tvSub != null) tvSub.setText("Cụm sân Riverside · 0 sân ▾");
            if (tvTotalRevenueMonth != null) tvTotalRevenueMonth.setText("0đ (Tạm ngưng)");
            if (tvGoldHourFillRate != null) tvGoldHourFillRate.setText("0%");
            if (tvCompletedMatches != null) tvCompletedMatches.setText("0 trận");
        } else {
            if (tvSub != null) tvSub.setText("Cụm sân Thăng Long · 5 sân ▾");
            if (tvTotalRevenueMonth != null) tvTotalRevenueMonth.setText("148.650.000đ");
            if (tvGoldHourFillRate != null) tvGoldHourFillRate.setText("96,2%");
            if (tvCompletedMatches != null) tvCompletedMatches.setText("412 trận");
        }
    }

    private void showComplexSwitcherDialog() {
        java.util.List<String> optionsList = fpoly.duan1.sporthub_new.helper.OwnerComplexManager.getInstance().getAllComplexOptionsForSwitcher();
        String[] options = optionsList.toArray(new String[0]);
        new AlertDialog.Builder(this)
                .setTitle("Chọn cụm sân xem thu chi")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        currentComplexName = "Tất cả các sân";
                    } else {
                        String selected = options[which];
                        currentComplexName = selected.replace("⚽ ", "").replaceAll(" \\(.*\\)", "").trim();
                    }
                    updateFinanceData();
                    Toast.makeText(this, "Đã chuyển sang thu chi: " + currentComplexName, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void setupBottomNav() {
        findViewById(R.id.navFinanceFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navFinanceSchedule).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerScheduleActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navFinanceStaff).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerStaffActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navFinanceTab).setOnClickListener(v -> {
            // Đang ở tab Thu chi
        });
        findViewById(R.id.navFinanceReviews).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerReviewActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
    }
}
