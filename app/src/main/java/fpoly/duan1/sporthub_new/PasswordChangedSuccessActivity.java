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

public class PasswordChangedSuccessActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvAccountName, tvAccountPhone, btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_changed_success);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPasswordChangedSuccess), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvAccountName = findViewById(R.id.tvAccountName);
        tvAccountPhone = findViewById(R.id.tvAccountPhone);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finishToLogin());
        btnBackToLogin.setOnClickListener(v -> finishToLogin());
    }

    private void finishToLogin() {
        Intent intent = new Intent(PasswordChangedSuccessActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
