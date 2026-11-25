package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerProfileActivity extends AppCompatActivity {

    private View btnBackProfile;
    private View btnShareProfile;

    // Billing Cycle Switcher
    private TextView tabCycleMonthly;
    private TextView tabCycleYearly;
    private TextView tvPriceBasic;
    private TextView tvBillingDescBasic;
    private TextView tvPricePro;
    private TextView tvBillingDescPro;
    private TextView tvPriceEnterprise;
    private TextView tvBillingDescEnterprise;

    private Button btnDowngradeBasic;
    private Button btnRenewPro;
    private Button btnUpgradeEnterprise;
    private Button btnOwnerLogout;

    private boolean isYearlySelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupListeners();
        setupBillingCycleToggle();
    }

    private void initViews() {
        btnBackProfile = findViewById(R.id.btnBackProfile);
        btnShareProfile = findViewById(R.id.btnShareProfile);

        tabCycleMonthly = findViewById(R.id.tabCycleMonthly);
        tabCycleYearly = findViewById(R.id.tabCycleYearly);
        tvPriceBasic = findViewById(R.id.tvPriceBasic);
        tvBillingDescBasic = findViewById(R.id.tvBillingDescBasic);
        tvPricePro = findViewById(R.id.tvPricePro);
        tvBillingDescPro = findViewById(R.id.tvBillingDescPro);
        tvPriceEnterprise = findViewById(R.id.tvPriceEnterprise);
        tvBillingDescEnterprise = findViewById(R.id.tvBillingDescEnterprise);

        btnDowngradeBasic = findViewById(R.id.btnDowngradeBasic);
        btnRenewPro = findViewById(R.id.btnRenewPro);
        btnUpgradeEnterprise = findViewById(R.id.btnUpgradeEnterprise);
        btnOwnerLogout = findViewById(R.id.btnOwnerLogout);
    }

    private void setupListeners() {
        btnBackProfile.setOnClickListener(v -> finish());

        btnShareProfile.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hệ thống sân bóng Star Arena - Chủ sở hữu: Lê Văn Quang\nHotline: 0908 123 456\nĐặt sân nhanh chóng qua ứng dụng SportHub!");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Chia sẻ hồ sơ chủ sân"));
        });

        btnRenewPro.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Gia hạn Gói Chuyên nghiệp")
                        .setMessage("Bạn đang chọn gia hạn Gói Chuyên nghiệp (559.200đ/tháng - thanh toán 6.710.400đ/năm).\n\nSố lượng: 5 cụm sân, 30 sân, 10 nhân viên, giá giờ vàng tự động và xuất báo cáo CSV.\n\nXác nhận tiếp tục?")
                        .setPositiveButton("Thanh toán ngay", (dialog, which) -> {
                            Toast.makeText(this, "Yêu cầu gia hạn đã được gửi tới cổng thanh toán!", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        btnDowngradeBasic.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Chuyển xuống Gói Cơ bản?")
                        .setMessage("Gói Cơ bản giới hạn 1 cụm sân, 5 sân và 2 nhân viên.\n\nCác tính năng nâng cao như giờ vàng tự động sẽ bị tạm khóa khi hết chu kỳ hiện tại.")
                        .setPositiveButton("Xác nhận", (dialog, which) -> {
                            Toast.makeText(this, "Đã ghi nhận yêu cầu thay đổi gói cước cho kỳ tiếp theo.", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        btnUpgradeEnterprise.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Nâng cấp Gói Doanh nghiệp")
                        .setMessage("Gói Doanh nghiệp mở khóa: KHÔNG GIỚI HẠN cụm sân, sân con và nhân viên, hỗ trợ riêng 24/7.\n\nChi phí: 1.279.200đ/tháng (15.350.400đ/năm).")
                        .setPositiveButton("Liên hệ nâng cấp", (dialog, which) -> {
                            Toast.makeText(this, "Chuyên viên hỗ trợ SportHub sẽ liên hệ trong 15 phút!", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        btnOwnerLogout.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản Chủ sân không?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            Intent intent = new Intent(OwnerProfileActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );
    }

    private void setupBillingCycleToggle() {
        tabCycleMonthly.setOnClickListener(v -> {
            if (isYearlySelected) {
                isYearlySelected = false;
                updateBillingUI();
            }
        });

        tabCycleYearly.setOnClickListener(v -> {
            if (!isYearlySelected) {
                isYearlySelected = true;
                updateBillingUI();
            }
        });
    }

    private void updateBillingUI() {
        if (isYearlySelected) {
            tabCycleYearly.setBackgroundResource(R.drawable.bg_profile_btn_dark);
            tabCycleYearly.setTextColor(Color.WHITE);

            tabCycleMonthly.setBackground(null);
            tabCycleMonthly.setTextColor(Color.parseColor("#9CA3AF"));

            tvPriceBasic.setText("239.200đ");
            tvBillingDescBasic.setText("Thanh toán 2.870.400đ mỗi năm");

            tvPricePro.setText("559.200đ");
            tvBillingDescPro.setText("Thanh toán 6.710.400đ mỗi năm");

            tvPriceEnterprise.setText("1.279.200đ");
            tvBillingDescEnterprise.setText("Thanh toán 15.350.400đ mỗi năm");
        } else {
            tabCycleMonthly.setBackgroundResource(R.drawable.bg_profile_btn_dark);
            tabCycleMonthly.setTextColor(Color.WHITE);

            tabCycleYearly.setBackground(null);
            tabCycleYearly.setTextColor(Color.parseColor("#9CA3AF"));

            tvPriceBasic.setText("299.000đ");
            tvBillingDescBasic.setText("Thanh toán 299.000đ mỗi tháng");

            tvPricePro.setText("699.000đ");
            tvBillingDescPro.setText("Thanh toán 699.000đ mỗi tháng");

            tvPriceEnterprise.setText("1.599.000đ");
            tvBillingDescEnterprise.setText("Thanh toán 1.599.000đ mỗi tháng");
        }
    }
}
