package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CashPaymentActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView btnConfirmCashBooking, btnChangeMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cash_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCashPayment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnConfirmCashBooking = findViewById(R.id.btnConfirmCashBooking);
        btnChangeMethod = findViewById(R.id.btnChangeMethod);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnChangeMethod.setOnClickListener(v -> finish());

        btnConfirmCashBooking.setOnClickListener(v -> showSuccessDialog());
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đặt sân thành công! 🎉")
                .setMessage("Đơn đặt sân trả sau bằng tiền mặt đã được xác nhận.\nVui lòng thanh toán 850.000đ cho nhân viên khi đến sân.")
                .setCancelable(false)
                .setPositiveButton("Xem vé đặt sân", (dialog, which) -> {
                    Intent intent = new Intent(CashPaymentActivity.this, BookingTicketActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Về trang chủ", (dialog, which) -> {
                    Intent intent = new Intent(CashPaymentActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
