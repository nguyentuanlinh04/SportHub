package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentExpiredActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView btnReselectTime, btnCheckTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_expired);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPaymentExpired), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnReselectTime = findViewById(R.id.btnReselectTime);
        btnCheckTransaction = findViewById(R.id.btnCheckTransaction);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnReselectTime.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentExpiredActivity.this, BookingTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnCheckTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentExpiredActivity.this, PaymentDepositActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
