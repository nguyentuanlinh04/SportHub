package fpoly.duan1.sporthub_new.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminRolePermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_role_permission);

        ImageView btnBack = findViewById(R.id.btnBackPermission);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        TextView tvName = findViewById(R.id.tvPermUserName);
        TextView tvPhone = findViewById(R.id.tvPermUserPhone);
        TextView avatar = findViewById(R.id.avatarPerm);

        String userName = getIntent().getStringExtra("USER_NAME");
        String userPhone = getIntent().getStringExtra("USER_PHONE");

        if (userName != null && !userName.isEmpty()) {
            if (tvName != null) tvName.setText(userName);
            if (avatar != null && !userName.isEmpty()) {
                avatar.setText(String.valueOf(userName.charAt(0)).toUpperCase());
            }
        }

        if (userPhone != null && !userPhone.isEmpty()) {
            if (tvPhone != null) tvPhone.setText(userPhone);
        }

        Button btnSave = findViewById(R.id.btnSavePermission);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(AdminRolePermissionActivity.this, "Đã lưu phân quyền tài khoản thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}