package fpoly.duan1.sporthub_new.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminGrowthReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_growth_report);

        ImageView btnBack = findViewById(R.id.btnBackGrowth);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnExport = findViewById(R.id.btnExportReport);
        if (btnExport != null) {
            btnExport.setOnClickListener(v ->
                Toast.makeText(AdminGrowthReportActivity.this, "Đã xuất báo cáo tăng trưởng (PDF/Excel) thành công!", Toast.LENGTH_LONG).show()
            );
        }
    }
}