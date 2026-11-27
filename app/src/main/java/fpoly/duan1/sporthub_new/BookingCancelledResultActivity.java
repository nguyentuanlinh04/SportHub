package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookingCancelledResultActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView btnViewRefundProgress, btnReturnToSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_cancelled_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBookingCancelledResult), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnViewRefundProgress = findViewById(R.id.btnViewRefundProgress);
        btnReturnToSchedule = findViewById(R.id.btnReturnToSchedule);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnViewRefundProgress.setOnClickListener(v -> {
            Intent intent = new Intent(BookingCancelledResultActivity.this, RefundTrackingActivity.class);
            startActivity(intent);
        });

        btnReturnToSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(BookingCancelledResultActivity.this, MenuActivity.class);
            intent.putExtra("OPEN_TAB", 2); // Tab Lịch đặt
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
