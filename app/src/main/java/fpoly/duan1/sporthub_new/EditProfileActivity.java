package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainEditProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> finish());
        }

        Button btnSave = findViewById(R.id.btnSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(this, "Đã lưu thay đổi hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        View tvChangePhone = findViewById(R.id.tvChangePhone);
        if (tvChangePhone != null) {
            tvChangePhone.setOnClickListener(v -> {
                EditText input = new EditText(this);
                input.setHint("Nhập số điện thoại mới (VD: 0987654321)");
                input.setPadding(40, 30, 40, 30);

                new AlertDialog.Builder(this)
                        .setTitle("Đổi số điện thoại")
                        .setMessage("Hệ thống sẽ gửi mã xác minh OTP đến số điện thoại mới.")
                        .setView(input)
                        .setPositiveButton("Tiếp tục", (dialog, which) -> {
                            String newPhone = input.getText().toString().trim();
                            if (!newPhone.isEmpty()) {
                                Intent intent = new Intent(this, VerifyOtpActivity.class);
                                intent.putExtra("phone", newPhone);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Vui lòng nhập số điện thoại hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        View cardSecurity = findViewById(R.id.cardSecurity);
        if (cardSecurity != null) {
            cardSecurity.setOnClickListener(v -> {
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
            });
        }
    }
}
