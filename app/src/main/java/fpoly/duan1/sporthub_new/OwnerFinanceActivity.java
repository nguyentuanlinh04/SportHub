package fpoly.duan1.sporthub_new;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerFinanceActivity extends AppCompatActivity {

    private TextView chipFinanceAll, chipFinanceThangLong, chipFinanceMaiDich, chipFinanceRiverside;
    private TextView tvChartTitle, tvChartGrowth;
    private TextView tvBarT2, tvBarT3, tvBarT4, tvBarT5, tvBarT6, tvBarT7, tvBarCN;
    private View viewBarT2, viewBarT3, viewBarT4, viewBarT5, viewBarT6, viewBarT7, viewBarCN;

    private TextView tvEfficiencyTitle;
    private ProgressBar pbPitch5, pbPitch7, pbPitch11;
    private TextView tvPitch5Stat, tvPitch7Stat, tvPitch11Stat;

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
        setupFilterChips();
        setupInvoiceButtons();
        setupBottomNav();
    }

    private void initViews() {
        chipFinanceAll = findViewById(R.id.chipFinanceAll);
        chipFinanceThangLong = findViewById(R.id.chipFinanceThangLong);
        chipFinanceMaiDich = findViewById(R.id.chipFinanceMaiDich);
        chipFinanceRiverside = findViewById(R.id.chipFinanceRiverside);

        tvChartTitle = findViewById(R.id.tvChartTitle);
        tvChartGrowth = findViewById(R.id.tvChartGrowth);

        tvBarT2 = findViewById(R.id.tvBarT2);
        tvBarT3 = findViewById(R.id.tvBarT3);
        tvBarT4 = findViewById(R.id.tvBarT4);
        tvBarT5 = findViewById(R.id.tvBarT5);
        tvBarT6 = findViewById(R.id.tvBarT6);
        tvBarT7 = findViewById(R.id.tvBarT7);
        tvBarCN = findViewById(R.id.tvBarCN);

        viewBarT2 = findViewById(R.id.viewBarT2);
        viewBarT3 = findViewById(R.id.viewBarT3);
        viewBarT4 = findViewById(R.id.viewBarT4);
        viewBarT5 = findViewById(R.id.viewBarT5);
        viewBarT6 = findViewById(R.id.viewBarT6);
        viewBarT7 = findViewById(R.id.viewBarT7);
        viewBarCN = findViewById(R.id.viewBarCN);

        tvEfficiencyTitle = findViewById(R.id.tvEfficiencyTitle);
        pbPitch5 = findViewById(R.id.pbPitch5);
        pbPitch7 = findViewById(R.id.pbPitch7);
        pbPitch11 = findViewById(R.id.pbPitch11);

        tvPitch5Stat = findViewById(R.id.tvPitch5Stat);
        tvPitch7Stat = findViewById(R.id.tvPitch7Stat);
        tvPitch11Stat = findViewById(R.id.tvPitch11Stat);
    }

    private void setupFilterChips() {
        chipFinanceAll.setOnClickListener(v -> selectFinanceTab(0));
        chipFinanceThangLong.setOnClickListener(v -> selectFinanceTab(1));
        chipFinanceMaiDich.setOnClickListener(v -> selectFinanceTab(2));
        chipFinanceRiverside.setOnClickListener(v -> selectFinanceTab(3));
    }

    private void selectFinanceTab(int index) {
        resetChip(chipFinanceAll);
        resetChip(chipFinanceThangLong);
        resetChip(chipFinanceMaiDich);
        resetChip(chipFinanceRiverside);

        if (index == 0) {
            setChipSelected(chipFinanceAll);
            tvChartTitle.setText("Doanh thu 7 ngày qua (Tất cả 3 cụm)");
            tvChartGrowth.setText("+24.5%");
            setChartBars("48.2", "51.5", "46.0", "58.0", "69.5", "86.0", "76.0");
            tvEfficiencyTitle.setText("Hiệu suất lấp đầy theo loại sân (3 Cụm)");
            updateEfficiency(74, "74.2% (486 trận)", 88, "88.5% (432 trận)", 56, "56.0% (130 trận)");
        } else if (index == 1) {
            setChipSelected(chipFinanceThangLong);
            tvChartTitle.setText("Doanh thu 7 ngày qua (Cụm Thăng Long)");
            tvChartGrowth.setText("+18.2%");
            setChartBars("18.2", "19.5", "17.8", "22.0", "28.5", "35.0", "32.5");
            tvEfficiencyTitle.setText("Hiệu suất lấp đầy (Cụm Thăng Long)");
            updateEfficiency(68, "68.4% (168 trận)", 85, "85.0% (192 trận)", 62, "62.0% (52 trận)");
        } else if (index == 2) {
            setChipSelected(chipFinanceMaiDich);
            tvChartTitle.setText("Doanh thu 7 ngày qua (Cụm Mai Dịch)");
            tvChartGrowth.setText("+25.1%");
            setChartBars("16.5", "18.0", "15.2", "20.5", "24.0", "30.5", "27.0");
            tvEfficiencyTitle.setText("Hiệu suất lấp đầy (Cụm Mai Dịch)");
            updateEfficiency(78, "78.0% (180 trận)", 82, "82.5% (160 trận)", 45, "45.0% (40 trận)");
        } else {
            setChipSelected(chipFinanceRiverside);
            tvChartTitle.setText("Doanh thu 7 ngày qua (Cụm Riverside)");
            tvChartGrowth.setText("+22.0%");
            setChartBars("13.5", "14.0", "13.0", "15.5", "17.0", "20.5", "16.5");
            tvEfficiencyTitle.setText("Hiệu suất lấp đầy (Cụm Riverside)");
            updateEfficiency(70, "70.5% (138 trận)", 75, "75.0% (80 trận)", 42, "42.0% (38 trận)");
        }
    }

    private void resetChip(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chip.setTextColor(ContextCompat.getColor(this, R.color.owner_chip_unselected_text));
    }

    private void setChipSelected(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_selected);
        chip.setTextColor(Color.WHITE);
    }

    private void setChartBars(String t2, String t3, String t4, String t5, String t6, String t7, String cn) {
        tvBarT2.setText(t2);
        tvBarT3.setText(t3);
        tvBarT4.setText(t4);
        tvBarT5.setText(t5);
        tvBarT6.setText(t6);
        tvBarT7.setText(t7);
        tvBarCN.setText(cn);
    }

    private void updateEfficiency(int p5, String s5, int p7, String s7, int p11, String s11) {
        pbPitch5.setProgress(p5);
        tvPitch5Stat.setText(s5);

        pbPitch7.setProgress(p7);
        tvPitch7Stat.setText(s7);

        pbPitch11.setProgress(p11);
        tvPitch11Stat.setText(s11);
    }

    private void setupInvoiceButtons() {
        findViewById(R.id.btnCreateInvoiceHeader).setOnClickListener(v -> showCreateInvoiceDialog("Cụm Thăng Long - Sân 5-A"));
        findViewById(R.id.btnCollectThangLong).setOnClickListener(v -> showCreateInvoiceDialog("Cụm Thăng Long - Sân 5-A"));
        findViewById(R.id.btnCollectMaiDich).setOnClickListener(v -> showCreateInvoiceDialog("Cụm Mai Dịch - Sân 7-1"));
        findViewById(R.id.btnCollectRiverside).setOnClickListener(v -> showCreateInvoiceDialog("Cụm Riverside - Sân 5-1"));
    }

    private void showCreateInvoiceDialog(String defaultPitch) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_owner_create_invoice, null);
        dialog.setContentView(dialogView);

        EditText edtPitch = dialogView.findViewById(R.id.edtInvoicePitch);
        if (edtPitch != null) {
            edtPitch.setText(defaultPitch);
        }

        EditText edtCustomer = dialogView.findViewById(R.id.edtInvoiceCustomer);
        EditText edtPitchFee = dialogView.findViewById(R.id.edtInvoicePitchFee);
        EditText edtServiceFee = dialogView.findViewById(R.id.edtInvoiceServiceFee);

        dialogView.findViewById(R.id.btnCloseInvoice).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnCancelInvoice).setOnClickListener(v -> dialog.dismiss());

        dialogView.findViewById(R.id.btnSubmitInvoice).setOnClickListener(v -> {
            String customer = edtCustomer != null ? edtCustomer.getText().toString().trim() : "";
            if (customer.isEmpty()) {
                customer = "Khách đặt tại quầy";
            }
            String feeStr = edtPitchFee != null ? edtPitchFee.getText().toString().trim() : "350000";
            Toast.makeText(this, "Đã lập phiếu thu cho " + customer + " (" + feeStr + "đ) thành công!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void setupBottomNav() {
        findViewById(R.id.navFinanceFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navFinanceSchedule).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerScheduleActivity.class));
            finish();
        });
        findViewById(R.id.navFinanceStaff).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerStaffActivity.class));
            finish();
        });
        findViewById(R.id.navFinanceTab).setOnClickListener(v -> {
            // Đang ở tab Thu chi
        });
        findViewById(R.id.navFinanceReviews).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerReviewActivity.class));
            finish();
        });
    }
}
