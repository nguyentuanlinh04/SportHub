package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HandoverHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_handover_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainHandoverHistory), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Filter chips
        TextView chipThisWeek = findViewById(R.id.chip_this_week);
        TextView chipThisMonth = findViewById(R.id.chip_this_month);
        TextView chipCustom = findViewById(R.id.chip_custom);

        if (chipThisWeek != null && chipThisMonth != null && chipCustom != null) {
            View.OnClickListener filterListener = v -> {
                chipThisWeek.setBackgroundResource(R.drawable.bg_chip_inactive);
                chipThisWeek.setTextColor(0xFF374151);
                chipThisMonth.setBackgroundResource(R.drawable.bg_chip_inactive);
                chipThisMonth.setTextColor(0xFF374151);
                chipCustom.setBackgroundResource(R.drawable.bg_chip_inactive);
                chipCustom.setTextColor(0xFF374151);

                TextView clicked = (TextView) v;
                clicked.setBackgroundResource(R.drawable.bg_chip_active);
                clicked.setTextColor(0xFFFFFFFF);
                Toast.makeText(this, "Đang lọc dữ liệu: " + clicked.getText(), Toast.LENGTH_SHORT).show();
            };

            chipThisWeek.setOnClickListener(filterListener);
            chipThisMonth.setOnClickListener(filterListener);
            chipCustom.setOnClickListener(filterListener);
        }

        // Card clicks
        View card3 = findViewById(R.id.card_history_3);
        if (card3 != null) {
            card3.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Yêu cầu giải trình ca T6 06/11")
                        .setMessage("Ca sáng 06:00 - 14:00 ngày 06/11/2026\nChênh lệch tiền mặt: -20.000đ\n\nGhi chú chủ sân: Vui lòng kiểm tra lại hóa đơn nước đóng chai và biên lai hoàn cọc.")
                        .setPositiveButton("Gửi giải trình", (dialog, which) -> {
                            Toast.makeText(this, "Đã gửi phản hồi giải trình tới chủ sân", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Đóng", null)
                        .show();
            });
        }
    }
}
