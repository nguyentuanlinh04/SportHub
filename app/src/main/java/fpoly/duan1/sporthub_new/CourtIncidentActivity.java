package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourtIncidentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_court_incident);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCourtIncident), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnSaveDraft = findViewById(R.id.btnSaveDraft);
        if (btnSaveDraft != null) {
            btnSaveDraft.setOnClickListener(v -> {
                Toast.makeText(this, "Đã lưu nháp phiếu báo sự cố!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        Button btnSubmitIncident = findViewById(R.id.btnSubmitIncident);
        if (btnSubmitIncident != null) {
            btnSubmitIncident.setOnClickListener(v -> {
                Toast.makeText(this, "Đã gửi báo cáo sự cố tới Chủ sân & Kỹ thuật!", Toast.LENGTH_LONG).show();
                finish();
            });
        }
    }
}
