package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText etNewPassword, etConfirmPassword;
    private ImageView ivToggleNewPassword, ivToggleConfirmPassword;

    private ImageView ivCheckMinLength, ivCheckUpperLower, ivCheckDigit, ivCheckMatch;
    private TextView btnSavePassword, btnBackToLogin;

    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainChangePassword), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupPasswordToggles();
        setupValidationWatcher();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        ivToggleNewPassword = findViewById(R.id.ivToggleNewPassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);

        ivCheckMinLength = findViewById(R.id.ivCheckMinLength);
        ivCheckUpperLower = findViewById(R.id.ivCheckUpperLower);
        ivCheckDigit = findViewById(R.id.ivCheckDigit);
        ivCheckMatch = findViewById(R.id.ivCheckMatch);

        btnSavePassword = findViewById(R.id.btnSavePassword);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
    }

    private void setupPasswordToggles() {
        ivToggleNewPassword.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            if (isNewPasswordVisible) {
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etNewPassword.setSelection(etNewPassword.getText().length());
        });

        ivToggleConfirmPassword.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            if (isConfirmPasswordVisible) {
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etConfirmPassword.setSelection(etConfirmPassword.getText().length());
        });
    }

    private void setupValidationWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePasswordRealtime();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etNewPassword.addTextChangedListener(watcher);
        etConfirmPassword.addTextChangedListener(watcher);
    }

    private void validatePasswordRealtime() {
        String newPass = etNewPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();

        boolean hasMinLength = newPass.length() >= 8;
        boolean hasUpper = !newPass.equals(newPass.toLowerCase());
        boolean hasLower = !newPass.equals(newPass.toUpperCase());
        boolean hasUpperAndLower = hasUpper && hasLower;
        boolean hasDigit = newPass.matches(".*\\d.*");
        boolean isMatch = !newPass.isEmpty() && newPass.equals(confirmPass);

        updateCheckIcon(ivCheckMinLength, hasMinLength);
        updateCheckIcon(ivCheckUpperLower, hasUpperAndLower);
        updateCheckIcon(ivCheckDigit, hasDigit);
        updateCheckIcon(ivCheckMatch, isMatch);
    }

    private void updateCheckIcon(ImageView imageView, boolean isPassed) {
        if (isPassed) {
            imageView.setAlpha(1.0f);
            imageView.setImageResource(R.drawable.ic_check_small_green);
        } else {
            imageView.setAlpha(0.3f);
            imageView.setImageResource(R.drawable.ic_check_small_green);
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnSavePassword.setOnClickListener(v -> {
            String newPass = etNewPassword.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();

            if (newPass.length() < 8) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Xác nhận mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save new password persistently via FirebaseUserHelper
            fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.updatePassword(this, "0912345678", newPass, new fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.OnUserActionListener() {
                @Override
                public void onSuccess(String message) {
                    Intent intent = new Intent(ChangePasswordActivity.this, PasswordChangedSuccessActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String error) {
                    Intent intent = new Intent(ChangePasswordActivity.this, PasswordChangedSuccessActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        });

        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
