package fpoly.duan1.sporthub_new;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomerLateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_late);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCustomerLate), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View btnCallCustomer = findViewById(R.id.btnCallCustomer);
        if (btnCallCustomer != null) {
            btnCallCustomer.setOnClickListener(v -> {
                Toast.makeText(this, "Đang kết nối cuộc gọi tới khách hàng Minh (+84 912 345 678)...", Toast.LENGTH_SHORT).show();
            });
        }

        View btnMessageCustomer = findViewById(R.id.btnMessageCustomer);
        if (btnMessageCustomer != null) {
            btnMessageCustomer.setOnClickListener(v -> {
                Toast.makeText(this, "Đang mở tin nhắn tới khách hàng Minh (+84 912 345 678)...", Toast.LENGTH_SHORT).show();
            });
        }

        View btnOptionLate = findViewById(R.id.btnOptionLate);
        if (btnOptionLate != null) {
            btnOptionLate.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn hướng: Ghi nhận tới trễ", Toast.LENGTH_SHORT).show();
            });
        }

        View btnOptionReschedule = findViewById(R.id.btnOptionReschedule);
        if (btnOptionReschedule != null) {
            btnOptionReschedule.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn hướng: Đổi lịch sân", Toast.LENGTH_SHORT).show();
            });
        }

        View btnSelectDepositAction = findViewById(R.id.btnSelectDepositAction);
        TextView tvDepositAction = findViewById(R.id.tvDepositAction);
        if (btnSelectDepositAction != null && tvDepositAction != null) {
            btnSelectDepositAction.setOnClickListener(v -> {
                String[] options = {
                        "Đề nghị hoàn cọc 255.000đ",
                        "Giữ lại 50% tiền cọc (127.500đ)",
                        "Mất toàn bộ tiền cọc do hủy sát giờ"
                };
                new AlertDialog.Builder(this)
                        .setTitle("Đề xuất xử lý cọc")
                        .setItems(options, (dialog, which) -> {
                            tvDepositAction.setText(options[which]);
                        })
                        .show();
            });
        }

        Button btnCancelRequest = findViewById(R.id.btnCancelRequest);
        if (btnCancelRequest != null) {
            btnCancelRequest.setOnClickListener(v -> finish());
        }

        Button btnSubmitRequest = findViewById(R.id.btnSubmitRequest);
        if (btnSubmitRequest != null) {
            btnSubmitRequest.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận gửi yêu cầu")
                        .setMessage("Bạn có chắc chắn muốn gửi yêu cầu hủy đơn này cho Chủ sân duyệt?")
                        .setPositiveButton("Gửi ngay", (dialog, which) -> {
                            Toast.makeText(this, "Đã gửi yêu cầu xử lý hủy đơn tới Chủ sân thành công!", Toast.LENGTH_LONG).show();
                            finish();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }
    }
}
