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

public class PaymentFailedActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView btnRetry, btnChangeMethod, btnViewOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_failed);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPaymentFailed), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnRetry = findViewById(R.id.btnRetry);
        btnChangeMethod = findViewById(R.id.btnChangeMethod);
        btnViewOrderDetails = findViewById(R.id.btnViewOrderDetails);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentFailedActivity.this, CheckTransactionActivity.class);
            startActivity(intent);
            finish();
        });

        btnChangeMethod.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentFailedActivity.this, PaymentConfirmationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnViewOrderDetails.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentFailedActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
