package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.duan1.sporthub_new.utils.UserProfileManager;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvAvatarCircle;
    private View btnChangeAvatar;
    private EditText etFullName, etPhoneNumber, etLocationAddress, etEmail;
    private TextView btnSaveProfile, btnCancelProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainEditProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        loadProfileData();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvAvatarCircle = findViewById(R.id.tvAvatarCircle);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);

        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etLocationAddress = findViewById(R.id.etLocationAddress);
        etEmail = findViewById(R.id.etEmail);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnCancelProfile = findViewById(R.id.btnCancelProfile);
    }

    private void loadProfileData() {
        UserProfileManager manager = UserProfileManager.getInstance();

        String name = manager.getFullName(this);
        String phone = manager.getPhoneNumber(this);
        String address = manager.getAddress(this);
        String email = manager.getEmail(this);

        etFullName.setText(name);
        etPhoneNumber.setText(phone);
        etLocationAddress.setText(address);
        etEmail.setText(email);

        if (!name.isEmpty()) {
            tvAvatarCircle.setText(String.valueOf(name.charAt(0)).toUpperCase());
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnCancelProfile.setOnClickListener(v -> finish());

        btnChangeAvatar.setOnClickListener(v ->
                Toast.makeText(this, "Mở thư viện chọn ảnh đại diện mới...", Toast.LENGTH_SHORT).show()
        );

        btnSaveProfile.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();
            String address = etLocationAddress.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập họ và tên.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserProfileManager.getInstance().updateProfile(this, name, phone, address, email);

            Toast.makeText(this, "Cập nhật thông tin hồ sơ thành công! 🎉", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
