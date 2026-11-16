package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Random;

public class VerifyOtpActivity extends AppCompatActivity {

    private EditText[] otpBoxes;
    private TextView tvOtpSubtitle, tvResendTimer;
    private String phoneNumber = "";
    private String expectedOtp = "";
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainVerifyOtp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent() != null) {
            phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
            expectedOtp = getIntent().getStringExtra("OTP_CODE");
        }

        tvOtpSubtitle = findViewById(R.id.tvOtpSubtitle);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        Button btnConfirmOtp = findViewById(R.id.btnConfirmOtp);
        View btnBack = findViewById(R.id.btnBackVerifyOtp);
        View tvChangePhone = findViewById(R.id.tvChangePhone);

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            tvOtpSubtitle.setText("Mã gồm 6 số đã được gửi tới +84 " + phoneNumber);
        }

        otpBoxes = new EditText[]{
                findViewById(R.id.edtOtp1),
                findViewById(R.id.edtOtp2),
                findViewById(R.id.edtOtp3),
                findViewById(R.id.edtOtp4),
                findViewById(R.id.edtOtp5),
                findViewById(R.id.edtOtp6)
        };

        setupOtpInputListeners();
        startResendTimer();

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (tvChangePhone != null) {
            tvChangePhone.setOnClickListener(v -> finish());
        }

        if (btnConfirmOtp != null) {
            btnConfirmOtp.setOnClickListener(v -> handleConfirmOtp());
        }
    }

    private void setupOtpInputListeners() {
        for (int i = 0; i < otpBoxes.length; i++) {
            final int index = i;
            otpBoxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpBoxes.length - 1) {
                        otpBoxes[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            otpBoxes[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (otpBoxes[index].getText().toString().isEmpty() && index > 0) {
                        otpBoxes[index - 1].requestFocus();
                    }
                }
                return false;
            });
        }
    }

    private void startResendTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                tvResendTimer.setText(String.format(Locale.getDefault(), "Gửi lại mã sau 00:%02d", seconds));
                tvResendTimer.setClickable(false);
            }

            @Override
            public void onFinish() {
                tvResendTimer.setText("Gửi lại mã xác thực");
                tvResendTimer.setClickable(true);
                tvResendTimer.setOnClickListener(v -> {
                    int randomOtpNum = 100000 + new Random().nextInt(900000);
                    expectedOtp = String.valueOf(randomOtpNum);
                    Toast.makeText(VerifyOtpActivity.this, "[SMS Mô phỏng] Mã xác thực mới là: " + expectedOtp, Toast.LENGTH_LONG).show();
                    startResendTimer();
                });
            }
        }.start();
    }

    private void handleConfirmOtp() {
        StringBuilder enteredOtp = new StringBuilder();
        for (EditText box : otpBoxes) {
            enteredOtp.append(box.getText().toString().trim());
        }

        if (enteredOtp.length() < 6) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ 6 số OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredOtp.toString().equals(expectedOtp)) {
            Toast.makeText(this, "Xác thực mã OTP thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VerifyOtpActivity.this, ResetPasswordActivity.class);
            intent.putExtra("PHONE_NUMBER", phoneNumber);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Mã xác thực không chính xác. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
