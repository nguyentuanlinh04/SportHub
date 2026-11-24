package fpoly.duan1.sporthub_new.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        ImageView btnBack = findViewById(R.id.btnBackCategory);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnSave = findViewById(R.id.btnSaveCategory);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(AdminCategoryActivity.this, "Đã lưu danh mục loại sân và tiện ích!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}