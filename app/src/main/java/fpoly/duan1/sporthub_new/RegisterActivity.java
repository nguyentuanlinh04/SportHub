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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private boolean isOwnerRole = false;

    // Regex: Tối thiểu 8 ký tự, ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!._\\-*()<>?/\\[\\]{}|]).{8,}$"
    );

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
        String name = edtFullName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Họ và tên", Toast.LENGTH_SHORT).show();
            edtFullName.requestFocus();
            return;
        }

        String phone = edtPhoneNumber.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Số điện thoại", Toast.LENGTH_SHORT).show();
            edtPhoneNumber.requestFocus();
            return;
        }

        String cccd = "";
        String address = "";
        String business = "";
        String tax = "";

        if (isOwnerRole) {
            cccd = edtCccd.getText().toString().trim();
            if (cccd.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Số CMND / CCCD", Toast.LENGTH_SHORT).show();
                edtCccd.requestFocus();
                return;
            }

            address = edtAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Địa chỉ thường trú", Toast.LENGTH_SHORT).show();
                edtAddress.requestFocus();
                return;
            }

            business = edtBusinessName.getText().toString().trim();
            if (business.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Tên doanh nghiệp / Hộ kinh doanh", Toast.LENGTH_SHORT).show();
                edtBusinessName.requestFocus();
                return;
            }

            tax = edtTaxCode.getText().toString().trim();
            if (tax.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Mã số thuế", Toast.LENGTH_SHORT).show();
                edtTaxCode.requestFocus();
                return;
            }
        }

        String password = edtPassword.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Mật khẩu", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "Mật khẩu phải có tối thiểu 8 ký tự, gồm ít nhất 1 chữ in hoa, 1 chữ thường, 1 chữ số và 1 ký tự đặc biệt.", Toast.LENGTH_LONG).show();
            edtPassword.requestFocus();
            return;
        }

        if (!isOwnerRole) {
            String confirmPass = edtConfirmPassword.getText().toString().trim();
            if (confirmPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                edtConfirmPassword.requestFocus();
                return;
            }

            if (!password.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                edtConfirmPassword.requestFocus();
                return;
            }
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý Điều khoản sử dụng và Chính sách bảo mật.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String finalCccd = cccd;
        final String finalAddress = address;
        final String finalBusiness = business;
        final String finalTax = tax;

        // Kiểm tra Số điện thoại đã tồn tại trên Firebase chưa
        fpoly.duan1.sporthub_new.helper.FirebaseUserHelper.checkPhoneExists(this, phone, exists -> {
            if (exists) {
                Toast.makeText(RegisterActivity.this, "Số điện thoại này đã được đăng ký tài khoản trên hệ thống!", Toast.LENGTH_SHORT).show();
                edtPhoneNumber.requestFocus();
                return;
            }

            fpoly.duan1.sporthub_new.model.User pendingUser;
            if (isOwnerRole) {
                pendingUser = new fpoly.duan1.sporthub_new.model.User(
                        "", phone, name, password,
                        fpoly.duan1.sporthub_new.model.User.ROLE_CHU_SAN,
                        fpoly.duan1.sporthub_new.model.User.STATUS_PENDING,
                        finalCccd, finalAddress, finalBusiness, finalTax, System.currentTimeMillis()
                );
            } else {
                pendingUser = new fpoly.duan1.sporthub_new.model.User(
                        "", phone, name, password,
                        fpoly.duan1.sporthub_new.model.User.ROLE_KHACH_HANG,
                        fpoly.duan1.sporthub_new.model.User.STATUS_ACTIVE,
                        System.currentTimeMillis()
                );
            }

            // Sinh ngẫu nhiên mã OTP 6 chữ số
            int randomOtpNum = 100000 + new java.util.Random().nextInt(900000);
            String generatedOtp = String.valueOf(randomOtpNum);

            Toast.makeText(RegisterActivity.this, "[SMS Mô phỏng] Mã xác thực OTP đăng ký của bạn là: " + generatedOtp, Toast.LENGTH_LONG).show();

            // Chuyển sang màn hình VerifyOtpActivity để người dùng nhập OTP hoàn tất đăng ký
            android.content.Intent intent = new android.content.Intent(RegisterActivity.this, VerifyOtpActivity.class);
            intent.putExtra("IS_REGISTRATION", true);
            intent.putExtra("PHONE_NUMBER", phone);
            intent.putExtra("OTP_CODE", generatedOtp);
            intent.putExtra("REGISTER_USER_DATA", pendingUser);
            startActivity(intent);
            finish();
        });
    }
}
