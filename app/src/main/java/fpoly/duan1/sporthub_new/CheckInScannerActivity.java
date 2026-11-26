package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class CheckInScannerActivity extends AppCompatActivity {

    private boolean isTorchOn = false;
    private ImageView ivTorch;
    private EditText edtCheckInCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_in_scanner);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCheckInScanner), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        View btnClose = findViewById(R.id.btnClose);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> finish());
        }

        ivTorch = findViewById(R.id.ivTorch);
        View btnTorch = findViewById(R.id.btnTorch);
        if (btnTorch != null) {
            btnTorch.setOnClickListener(v -> {
                isTorchOn = !isTorchOn;
                if (ivTorch != null) {
                    ivTorch.setColorFilter(isTorchOn ? Color.parseColor("#F59E0B") : Color.parseColor("#111827"));
                }
                Toast.makeText(this, isTorchOn ? "Bật đèn flash" : "Tắt đèn flash", Toast.LENGTH_SHORT).show();
            });
        }

        View cardOrderCheckIn = findViewById(R.id.cardOrderCheckIn);
        if (cardOrderCheckIn != null) {
            cardOrderCheckIn.setOnClickListener(v -> {
                Intent intent = new Intent(CheckInScannerActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
            });
        }

        MaterialButton btnConfirmHandoverCourt = findViewById(R.id.btnConfirmHandoverCourt);
        if (btnConfirmHandoverCourt != null) {
            btnConfirmHandoverCourt.setOnClickListener(v -> {
                StaffDataStore.checkInOrder();
                Toast.makeText(this, "Đã xác nhận bàn giao sân thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckInScannerActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
                finish();
            });
        }

        edtCheckInCode = findViewById(R.id.edtCheckInCode);
        MaterialButton btnSearchCheckIn = findViewById(R.id.btnSearchCheckIn);
        if (btnSearchCheckIn != null) {
            btnSearchCheckIn.setOnClickListener(v -> {
                String code = edtCheckInCode != null ? edtCheckInCode.getText().toString().trim() : "";
                Toast.makeText(this, "Đã tra cứu mã: " + code, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
