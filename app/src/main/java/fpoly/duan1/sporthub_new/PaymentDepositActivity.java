package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class PaymentDepositActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvPitchSubPitch, tvOrderCodeName, tvFullDateTime;
    private TextView tvDepositAmount, tvRemainingAmount;
    private TextView tvTabBank, tvTabEWallet, tvTabQR;
    private TextView tvTimer;
    private TextView btnIHavePaid, btnCancelPayment;

    private CountDownTimer countDownTimer;
    private int selectedTab = 2; // 0: Bank, 1: E-Wallet, 2: QR (default)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_deposit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPaymentDeposit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupMethodTabs();
        startCountdownTimer();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvPitchSubPitch = findViewById(R.id.tvPitchSubPitch);
        tvOrderCodeName = findViewById(R.id.tvOrderCodeName);
        tvFullDateTime = findViewById(R.id.tvFullDateTime);

        tvDepositAmount = findViewById(R.id.tvDepositAmount);
        tvRemainingAmount = findViewById(R.id.tvRemainingAmount);

        tvTabBank = findViewById(R.id.tvTabBank);
        tvTabEWallet = findViewById(R.id.tvTabEWallet);
        tvTabQR = findViewById(R.id.tvTabQR);

        tvTimer = findViewById(R.id.tvTimer);
        btnIHavePaid = findViewById(R.id.btnIHavePaid);
        btnCancelPayment = findViewById(R.id.btnCancelPayment);
    }

    private void setupMethodTabs() {
        tvTabBank.setOnClickListener(v -> selectMethodTab(0));
        tvTabEWallet.setOnClickListener(v -> selectMethodTab(1));
        tvTabQR.setOnClickListener(v -> selectMethodTab(2));
    }

    private void selectMethodTab(int index) {
        selectedTab = index;
        updateTabUI(tvTabBank, index == 0);
        updateTabUI(tvTabEWallet, index == 1);
        updateTabUI(tvTabQR, index == 2);
    }

    private void updateTabUI(TextView tabView, boolean isSelected) {
        if (isSelected) {
            tabView.setBackgroundResource(R.drawable.bg_tab_selected);
            tabView.setTextColor(ContextCompat.getColor(this, R.color.text_title));
            tabView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tabView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            tabView.setTextColor(ContextCompat.getColor(this, R.color.text_sub));
            tabView.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void startCountdownTimer() {
        // 4 minutes 32 seconds (272,000 ms) matching screenshot
        countDownTimer = new CountDownTimer(272000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                Toast.makeText(PaymentDepositActivity.this, "Mã hết thời gian giữ chỗ! Vui lòng thao tác lại.", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnCancelPayment.setOnClickListener(v -> finish());

        btnIHavePaid.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentDepositActivity.this, CheckTransactionActivity.class);
            startActivity(intent);
        });
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thanh toán cọc thành công! 🎉")
                .setMessage("Cảm ơn bạn! Hệ thống đã ghi nhận khoản cọc 255.000đ.\nĐơn đặt sân của bạn đã được xác nhận giữ chỗ thành công.")
                .setCancelable(false)
                .setPositiveButton("Về trang chủ", (dialog, which) -> {
                    Intent intent = new Intent(PaymentDepositActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
