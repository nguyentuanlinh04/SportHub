package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private boolean isOwnerRole = false;

    private TextView tvRegisterSubtitle, tvRoleSelected, tvNameLabel;
    private EditText edtFullName, edtPhoneNumber, edtCccd, edtAddress, edtBusinessName, edtTaxCode, edtPassword, edtConfirmPassword;
    private LinearLayout layoutOwnerCccd, layoutOwnerAddress, layoutOwnerBusiness, layoutOwnerTax, layoutConfirmPassword;
    private CheckBox cbTerms;
    private Button btnSubmitRegister;
    private TextView tvNoticeTitle, tvNoticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainRegister), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        updateRoleUI(false);
    }

    private void initViews() {
        tvRegisterSubtitle = findViewById(R.id.tvRegisterSubtitle);
        tvRoleSelected = findViewById(R.id.tvRoleSelected);
        tvNameLabel = findViewById(R.id.tvNameLabel);

        edtFullName = findViewById(R.id.edtFullName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtCccd = findViewById(R.id.edtCccd);
        edtAddress = findViewById(R.id.edtAddress);
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtTaxCode = findViewById(R.id.edtTaxCode);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        layoutOwnerCccd = findViewById(R.id.layoutOwnerCccd);
        layoutOwnerAddress = findViewById(R.id.layoutOwnerAddress);
        layoutOwnerBusiness = findViewById(R.id.layoutOwnerBusiness);
        layoutOwnerTax = findViewById(R.id.layoutOwnerTax);
        layoutConfirmPassword = findViewById(R.id.layoutConfirmPassword);

        cbTerms = findViewById(R.id.cbTerms);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);
        tvNoticeTitle = findViewById(R.id.tvNoticeTitle);
        tvNoticeContent = findViewById(R.id.tvNoticeContent);
    }

    private void setupListeners() {
        View btnBack = findViewById(R.id.btnBackRegister);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View tvBackToLogin = findViewById(R.id.tvBackToLogin);
        if (tvBackToLogin != null) {
            tvBackToLogin.setOnClickListener(v -> finish());
        }

        View layoutRoleSelector = findViewById(R.id.layoutRoleSelector);
        if (layoutRoleSelector != null) {
            layoutRoleSelector.setOnClickListener(v -> showRoleSelectionDialog());
        }

        if (btnSubmitRegister != null) {
            btnSubmitRegister.setOnClickListener(v -> handleSubmitRegister());
        }
    }

    private void showRoleSelectionDialog() {
        String[] roles = {"Khách hàng", "Chủ sân"};
        new AlertDialog.Builder(this)
                .setTitle("Chọn vai trò tài khoản")
                .setItems(roles, (dialog, which) -> updateRoleUI(which == 1))
                .show();
    }

    private void updateRoleUI(boolean isOwner) {
        this.isOwnerRole = isOwner;

        if (isOwner) {
            tvRoleSelected.setText("Chủ sân");
            tvRegisterSubtitle.setText("Cung cấp thông tin cá nhân, doanh nghiệp để đăng ký.");
            tvNameLabel.setText("Họ và tên (theo giấy tờ)");

            layoutOwnerCccd.setVisibility(View.VISIBLE);
            layoutOwnerAddress.setVisibility(View.VISIBLE);
            layoutOwnerBusiness.setVisibility(View.VISIBLE);
            layoutOwnerTax.setVisibility(View.VISIBLE);
            layoutConfirmPassword.setVisibility(View.GONE);

            btnSubmitRegister.setText("Gửi yêu cầu đăng ký");
            tvNoticeTitle.setText("Lưu ý:");
            tvNoticeContent.setText("Tài khoản cần được quản trị viên duyệt (khoảng 24 giờ) trước khi đăng sản. Thông tin cụm sân được bổ sung sau khi duyệt.");
        } else {
            tvRoleSelected.setText("Khách hàng");
            tvRegisterSubtitle.setText("Gia nhập Sports Hub, đặt sân bóng cùng đội của bạn.");
            tvNameLabel.setText("Họ và tên");

            layoutOwnerCccd.setVisibility(View.GONE);
            layoutOwnerAddress.setVisibility(View.GONE);
            layoutOwnerBusiness.setVisibility(View.GONE);
            layoutOwnerTax.setVisibility(View.GONE);
            layoutConfirmPassword.setVisibility(View.VISIBLE);

            btnSubmitRegister.setText("Tạo tài khoản");
            tvNoticeTitle.setText("Tiếp theo: xác thực số điện thoại");
            tvNoticeContent.setText("Nhập mã OTP 6 số được gửi đến SĐT trên để hoàn tất đăng ký.");
        }
    }

    private void handleSubmitRegister() {
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý Điều khoản sử dụng và Chính sách bảo mật.", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = edtFullName.getText().toString().trim();
        String phone = edtPhoneNumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ các thông tin bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isOwnerRole) {
            String cccd = edtCccd.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String business = edtBusinessName.getText().toString().trim();
            String tax = edtTaxCode.getText().toString().trim();

            if (cccd.isEmpty() || address.isEmpty() || business.isEmpty() || tax.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin doanh nghiệp / cá nhân chủ sân.", Toast.LENGTH_SHORT).show();
                return;
            }

            fpoly.duan1.sporthub_new.model.User ownerUser = new fpoly.duan1.sporthub_new.model.User(
                    "", phone, name, password,
                    fpoly.duan1.sporthub_new.model.User.ROLE_CHU_SAN,
                    fpoly.duan1.sporthub_new.model.User.STATUS_PENDING,
                    cccd, address, business, tax, System.currentTimeMillis()
            );

            fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.registerUser(this, ownerUser, new fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.OnUserActionListener() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(RegisterActivity.this, "Đã gửi yêu cầu đăng ký Chủ sân! Quản trị viên sẽ duyệt trong 24h.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            String confirmPass = edtConfirmPassword.getText().toString().trim();
            if (!password.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp.", Toast.LENGTH_SHORT).show();
                return;
            }

            fpoly.duan1.sporthub_new.model.User customerUser = new fpoly.duan1.sporthub_new.model.User(
                    "", phone, name, password,
                    fpoly.duan1.sporthub_new.model.User.ROLE_KHACH_HANG,
                    fpoly.duan1.sporthub_new.model.User.STATUS_ACTIVE,
                    System.currentTimeMillis()
            );

            fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.registerUser(this, customerUser, new fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.OnUserActionListener() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(RegisterActivity.this, "Tạo tài khoản Khách hàng thành công!", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
