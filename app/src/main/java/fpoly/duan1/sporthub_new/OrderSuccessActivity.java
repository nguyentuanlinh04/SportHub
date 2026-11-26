package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_success);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOrderSuccess), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnViewScheduleToday = findViewById(R.id.btnViewScheduleToday);
        if (btnViewScheduleToday != null) {
            btnViewScheduleToday.setOnClickListener(v -> finish());
        }

        Button btnAddService = findViewById(R.id.btnAddService);
        if (btnAddService != null) {
            btnAddService.setOnClickListener(v -> {
                Intent intent = new Intent(OrderSuccessActivity.this, IncidentalServicesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        Button btnCreateAnother = findViewById(R.id.btnCreateAnother);
        if (btnCreateAnother != null) {
            btnCreateAnother.setOnClickListener(v -> {
                Intent intent = new Intent(OrderSuccessActivity.this, CreateOrderActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
