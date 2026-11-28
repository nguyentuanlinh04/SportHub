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
    private TextView tvHeaderTitle;
    private TextView tvPitchSubPitch, tvOrderCodeName, tvFullDateTime;
    private TextView tvDepositNoteLabel, tvDepositAmount, tvRemainingAmount;
    private TextView tvTabBank, tvTabEWallet, tvTabQR;
    private TextView tvTimer;
    private TextView btnIHavePaid, btnCancelPayment;

    private CountDownTimer countDownTimer;
    private int selectedTab = 2; // 0: Bank, 1: E-Wallet, 2: QR (default)
    private boolean isFullPayment = false;

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
        extractIntentData();
        setupMethodTabs();
        startCountdownTimer();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);

        tvPitchSubPitch = findViewById(R.id.tvPitchSubPitch);
        tvOrderCodeName = findViewById(R.id.tvOrderCodeName);
        tvFullDateTime = findViewById(R.id.tvFullDateTime);

        tvDepositNoteLabel = findViewById(R.id.tvDepositNoteLabel);
        tvDepositAmount = findViewById(R.id.tvDepositAmount);
        tvRemainingAmount = findViewById(R.id.tvRemainingAmount);

        tvTabBank = findViewById(R.id.tvTabBank);
        tvTabEWallet = findViewById(R.id.tvTabEWallet);
        tvTabQR = findViewById(R.id.tvTabQR);

        tvTimer = findViewById(R.id.tvTimer);
        btnIHavePaid = findViewById(R.id.btnIHavePaid);
        btnCancelPayment = findViewById(R.id.btnCancelPayment);
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            isFullPayment = intent.getBooleanExtra("IS_FULL_PAYMENT", false);
        }

        if (isFullPayment) {
            tvHeaderTitle.setText("Thanh toán toàn phần");
            tvDepositNoteLabel.setText("Thanh toán 100% · Tổng 850.000đ");
            tvDepositAmount.setText("850.000đ");
            tvRemainingAmount.setText("0đ");
        } else {
            tvHeaderTitle.setText("Thanh toán cọc");
            tvDepositNoteLabel.setText("Cọc 30% · Tổng 850.000đ");
            tvDepositAmount.setText("255.000đ");
            tvRemainingAmount.setText("595.000đ");
        }
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
        // 2 phút (120,000 ms)
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                Intent intent = new Intent(PaymentDepositActivity.this, PaymentExpiredActivity.class);
                startActivity(intent);
                finish();
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
                .setTitle("Thanh toán thành công! 🎉")
                .setMessage("Cảm ơn bạn! Hệ thống đã ghi nhận khoản thanh toán.\nĐơn đặt sân của bạn đã được xác nhận giữ chỗ thành công.")
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
