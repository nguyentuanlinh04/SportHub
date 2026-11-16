package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainForgotPassword), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        Button btnSendOtp = findViewById(R.id.btnSendOtp);
        View btnBack = findViewById(R.id.btnBackForgotPassword);
        View tvBackToLogin = findViewById(R.id.tvBackToLogin);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (tvBackToLogin != null) {
            tvBackToLogin.setOnClickListener(v -> finish());
        }

        if (btnSendOtp != null) {
            btnSendOtp.setOnClickListener(v -> {
                String phone = edtPhoneNumber.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sinh ngẫu nhiên mã OTP 6 chữ số
                int randomOtpNum = 100000 + new Random().nextInt(900000);
                String generatedOtp = String.valueOf(randomOtpNum);

                Toast.makeText(ForgotPasswordActivity.this, "[SMS Mô phỏng] Mã xác thực OTP của bạn là: " + generatedOtp, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ForgotPasswordActivity.this, VerifyOtpActivity.class);
                intent.putExtra("PHONE_NUMBER", phone);
                intent.putExtra("OTP_CODE", generatedOtp);
                startActivity(intent);
            });
        }
    }
}
