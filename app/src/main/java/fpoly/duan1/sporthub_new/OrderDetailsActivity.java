package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOrderDetails), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        TextView btnAddServiceInvoice = findViewById(R.id.btnAddServiceInvoice);
        if (btnAddServiceInvoice != null) {
            btnAddServiceInvoice.setOnClickListener(v -> {
                Intent intent = new Intent(OrderDetailsActivity.this, IncidentalServicesActivity.class);
                startActivity(intent);
            });
        }

        LinearLayout btnMethodCash = findViewById(R.id.btnMethodCash);
        LinearLayout btnMethodTransfer = findViewById(R.id.btnMethodTransfer);

        if (btnMethodCash != null && btnMethodTransfer != null) {
            btnMethodCash.setOnClickListener(v -> {
                btnMethodCash.setBackgroundResource(R.drawable.bg_btn_cash_active);
                btnMethodTransfer.setBackgroundResource(R.drawable.bg_btn_transfer_inactive);
            });

            btnMethodTransfer.setOnClickListener(v -> {
                btnMethodTransfer.setBackgroundResource(R.drawable.bg_btn_cash_active);
                btnMethodCash.setBackgroundResource(R.drawable.bg_btn_transfer_inactive);
            });
        }

        Button btnConfirmCollected = findViewById(R.id.btnConfirmCollected);
        if (btnConfirmCollected != null) {
            btnConfirmCollected.setOnClickListener(v -> {
                Toast.makeText(this, "Đã xác nhận thu thành công 655.000đ tại sân!", Toast.LENGTH_LONG).show();
                finish();
            });
        }
    }
}
