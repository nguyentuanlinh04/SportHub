package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvPitchSubPitch, tvDateTime, tvTotalPrice;
    private RelativeLayout llOptionDeposit, llOptionFull, llOptionCash;
    private ImageView ivRadioDeposit, ivRadioFull, ivRadioCash;
    private TextView btnPayNow;

    private int selectedPaymentOption = 0; // 0: Đặt cọc 30%, 1: Thanh toán toàn phần, 2: Trả sau tiền mặt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_confirmation);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPaymentConfirmation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupPaymentOptions();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvPitchSubPitch = findViewById(R.id.tvPitchSubPitch);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        llOptionDeposit = findViewById(R.id.llOptionDeposit);
        llOptionFull = findViewById(R.id.llOptionFull);
        llOptionCash = findViewById(R.id.llOptionCash);

        ivRadioDeposit = findViewById(R.id.ivRadioDeposit);
        ivRadioFull = findViewById(R.id.ivRadioFull);
        ivRadioCash = findViewById(R.id.ivRadioCash);

        btnPayNow = findViewById(R.id.btnPayNow);
    }

    private void setupPaymentOptions() {
        llOptionDeposit.setOnClickListener(v -> selectPaymentOption(0));
        llOptionFull.setOnClickListener(v -> selectPaymentOption(1));
        llOptionCash.setOnClickListener(v -> selectPaymentOption(2));
    }

    private void selectPaymentOption(int index) {
        selectedPaymentOption = index;

        // Option 0: Deposit 30% (255.000đ)
        updateOptionUI(llOptionDeposit, ivRadioDeposit, index == 0);
        // Option 1: Full Payment (850.000đ)
        updateOptionUI(llOptionFull, ivRadioFull, index == 1);
        // Option 2: Cash Later (0đ)
        updateOptionUI(llOptionCash, ivRadioCash, index == 2);

        switch (index) {
            case 0:
                btnPayNow.setText("Thanh toán cọc 255.000đ");
                break;
            case 1:
                btnPayNow.setText("Thanh toán 850.000đ");
                break;
            case 2:
                btnPayNow.setText("Xác nhận đặt sân (0đ)");
                break;
        }
    }

    private void updateOptionUI(RelativeLayout container, ImageView radioIcon, boolean isSelected) {
        container.setBackgroundResource(isSelected ? R.drawable.bg_checkbox_checked : R.drawable.bg_checkbox_unchecked);
        radioIcon.setImageResource(isSelected ? R.drawable.ic_radio_checked : R.drawable.ic_radio_unchecked);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnPayNow.setOnClickListener(v -> {
            if (selectedPaymentOption == 0) {
                Intent intent = new Intent(PaymentConfirmationActivity.this, PaymentDepositActivity.class);
                startActivity(intent);
            } else {
                showSuccessDialog();
            }
        });
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đặt sân thành công! 🎉")
                .setMessage("Đơn đặt sân của bạn đã được ghi nhận hệ thống.\nBạn có thể kiểm tra chi tiết trong mục Lịch đặt sân.")
                .setCancelable(false)
                .setPositiveButton("Đã hiểu", (dialog, which) -> {
                    Intent intent = new Intent(PaymentConfirmationActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
