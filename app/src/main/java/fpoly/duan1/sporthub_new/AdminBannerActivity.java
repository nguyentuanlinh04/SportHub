package fpoly.duan1.sporthub_new.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminBannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_banner);

        ImageView btnBack = findViewById(R.id.btnBackBanner);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        TextView btnAdd = findViewById(R.id.btnAddBanner);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(v ->
                Toast.makeText(AdminBannerActivity.this, "Tạo sự kiện / banner quảng cáo mới", Toast.LENGTH_SHORT).show()
            );
        }
    }
}