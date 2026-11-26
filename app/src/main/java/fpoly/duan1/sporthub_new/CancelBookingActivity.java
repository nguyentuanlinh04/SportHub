package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CancelBookingActivity extends AppCompatActivity {

    private TextView tvReasonSchedule, tvReasonMembers, tvReasonWeather, tvReasonOther;
    private TextView btnKeepBooking, btnConfirmCancel;

    private int selectedReasonIndex = 0; // 0: Đổi lịch, 1: Thiếu người, 2: Thời tiết, 3: Khác

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cancel_booking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCancelBooking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupReasonTabs();
        setupListeners();
    }

    private void initViews() {
        tvReasonSchedule = findViewById(R.id.tvReasonSchedule);
        tvReasonMembers = findViewById(R.id.tvReasonMembers);
        tvReasonWeather = findViewById(R.id.tvReasonWeather);
        tvReasonOther = findViewById(R.id.tvReasonOther);

        btnKeepBooking = findViewById(R.id.btnKeepBooking);
        btnConfirmCancel = findViewById(R.id.btnConfirmCancel);
    }

    private void setupReasonTabs() {
        tvReasonSchedule.setOnClickListener(v -> selectReason(0));
        tvReasonMembers.setOnClickListener(v -> selectReason(1));
        tvReasonWeather.setOnClickListener(v -> selectReason(2));
        tvReasonOther.setOnClickListener(v -> selectReason(3));
    }

    private void selectReason(int index) {
        selectedReasonIndex = index;
        updateTabUI(tvReasonSchedule, index == 0);
        updateTabUI(tvReasonMembers, index == 1);
        updateTabUI(tvReasonWeather, index == 2);
        updateTabUI(tvReasonOther, index == 3);
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

    private void setupListeners() {
        btnKeepBooking.setOnClickListener(v -> finish());

        btnConfirmCancel.setOnClickListener(v -> {
            Intent intent = new Intent(CancelBookingActivity.this, BookingCancelledResultActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showCancelConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận hủy đơn đặt sân")
                .setMessage("Bạn có chắc chắn muốn hủy đơn này và nhận lại 255.000đ tiền cọc không?")
                .setPositiveButton("Xác nhận hủy", (dialog, which) -> {
                    Toast.makeText(this, "Đã hủy đơn đặt sân và hoàn cọc 255.000đ thành công! 🎉", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CancelBookingActivity.this, MenuActivity.class);
                    intent.putExtra("OPEN_TAB", 2); // Tab Lịch đặt
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Giữ lại đặt sân", null)
                .show();
    }
}
