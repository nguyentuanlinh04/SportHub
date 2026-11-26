package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourtChangeSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_court_change_success);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCourtChangeSuccess), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Apply Strike-Through paint flag to "TRƯỚC" items
        TextView tvBeforeCourt = findViewById(R.id.tvBeforeCourt);
        TextView tvBeforeTime = findViewById(R.id.tvBeforeTime);
        if (tvBeforeCourt != null) {
            tvBeforeCourt.setPaintFlags(tvBeforeCourt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (tvBeforeTime != null) {
            tvBeforeTime.setPaintFlags(tvBeforeTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        Button btnBackToSchedule = findViewById(R.id.btnBackToSchedule);
        if (btnBackToSchedule != null) {
            btnBackToSchedule.setOnClickListener(v -> finish());
        }

        Button btnViewOrderDetails = findViewById(R.id.btnViewOrderDetails);
        if (btnViewOrderDetails != null) {
            btnViewOrderDetails.setOnClickListener(v -> {
                Intent intent = new Intent(CourtChangeSuccessActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
