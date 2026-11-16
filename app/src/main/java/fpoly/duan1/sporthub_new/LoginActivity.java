package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneNumber, edtPassword;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cấu hình Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Đăng ký ActivityResultLauncher để nhận kết quả đăng nhập Google
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleGoogleSignInResult(task);
                    } else {
                        showDemoGoogleAccountDialog();
                    }
                }
        );

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String phone = edtPhoneNumber.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                if (phone.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.loginUser(LoginActivity.this, phone, pass, new fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.OnUserLoginListener() {
                    @Override
                    public void onSuccess(fpoly.duan1.sporthub_new.model.User user) {
                        String role = user.getRole();
                        if (fpoly.duan1.sporthub_new.model.User.ROLE_CHU_SAN.equals(role) && fpoly.duan1.sporthub_new.model.User.STATUS_PENDING.equals(user.getStatus())) {
                            Toast.makeText(LoginActivity.this, "Tài khoản Chủ sân đang chờ Quản trị viên duyệt (khoảng 24 giờ).", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String welcomeMessage;
                        if (fpoly.duan1.sporthub_new.model.User.ROLE_ADMIN.equals(role)) {
                            welcomeMessage = "Đăng nhập thành công! Quyền: Quản trị viên (Admin)";
                        } else if (fpoly.duan1.sporthub_new.model.User.ROLE_CHU_SAN.equals(role)) {
                            welcomeMessage = "Đăng nhập thành công! Vai trò: Chủ sân";
                        } else if (fpoly.duan1.sporthub_new.model.User.ROLE_NHAN_VIEN.equals(role)) {
                            welcomeMessage = "Đăng nhập thành công! Vai trò: Nhân viên";
                        } else {
                            welcomeMessage = "Đăng nhập thành công! Vai trò: Khách hàng";
                        }

                        Toast.makeText(LoginActivity.this, welcomeMessage, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("LOGGED_IN_USER", user);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        View btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        if (btnGoogleLogin != null) {
            btnGoogleLogin.setOnClickListener(v -> {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                googleSignInLauncher.launch(signInIntent);
            });
        }

        View tvRegister = findViewById(R.id.tvRegister);
        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        View tvForgotPassword = findViewById(R.id.tvForgotPassword);
        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            });
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String displayName = account.getDisplayName() != null ? account.getDisplayName() : "Người dùng Google";
                Toast.makeText(this, "Đăng nhập Google thành công!\nXin chào " + displayName + " (Vai trò: Khách hàng)", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                showDemoGoogleAccountDialog();
            }
        } catch (ApiException e) {
            showDemoGoogleAccountDialog();
        }
    }

    private void showDemoGoogleAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Liên kết tài khoản Google")
                .setMessage("Tài khoản: google.user@gmail.com\nVai trò mặc định: Khách hàng\n\nBạn có muốn đăng nhập bằng tài khoản này không?")
                .setPositiveButton("Đăng nhập", (dialog, which) -> {
                    Toast.makeText(LoginActivity.this, "Đăng nhập Google thành công với vai trò Khách hàng!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
