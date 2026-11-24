package fpoly.duan1.sporthub_new.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminFeeConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fee_config);

        ImageView btnBack = findViewById(R.id.btnBackFee);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnSave = findViewById(R.id.btnSaveFee);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(AdminFeeConfigActivity.this, "Đã lưu cấu hình biểu phí sàn thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}