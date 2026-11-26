package fpoly.duan1.sporthub_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class ChangeTimeCourtActivity extends AppCompatActivity {

    private EditText edtOrderSearchCode;
    private TextView tvNewDate, tvNewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_time_court);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainChangeTimeCourt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        TextView tvChangeFacility = findViewById(R.id.tv_change_facility);
        if (tvChangeFacility != null) {
            tvChangeFacility.setText(StaffDataStore.getCurrentFacility());
        }

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        TextView tabCreateNew = findViewById(R.id.tabCreateNew);
        if (tabCreateNew != null) {
            tabCreateNew.setOnClickListener(v -> {
                Intent intent = new Intent(ChangeTimeCourtActivity.this, CreateOrderActivity.class);
                startActivity(intent);
                finish();
            });
        }

        edtOrderSearchCode = findViewById(R.id.edtOrderSearchCode);
        View btnSearchOrder = findViewById(R.id.btnSearchOrder);
        if (btnSearchOrder != null) {
            btnSearchOrder.setOnClickListener(v -> {
                String code = edtOrderSearchCode != null ? edtOrderSearchCode.getText().toString().trim() : "";
                if (!code.isEmpty()) {
                    Toast.makeText(this, "Đã tra cứu đơn: " + code, Toast.LENGTH_SHORT).show();
                }
            });
        }

        tvNewDate = findViewById(R.id.tvNewDate);
        if (tvNewDate != null) {
            tvNewDate.setOnClickListener(v -> showDatePicker());
        }

        tvNewTime = findViewById(R.id.tvNewTime);
        if (tvNewTime != null) {
            tvNewTime.setOnClickListener(v -> showTimeSelector());
        }

        Button btnKeepOldSchedule = findViewById(R.id.btnKeepOldSchedule);
        if (btnKeepOldSchedule != null) {
            btnKeepOldSchedule.setOnClickListener(v -> {
                Toast.makeText(this, "Đã giữ nguyên lịch cũ", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        Button btnConfirmChange = findViewById(R.id.btnConfirmChange);
        if (btnConfirmChange != null) {
            btnConfirmChange.setOnClickListener(v -> {
                Intent intent = new Intent(ChangeTimeCourtActivity.this, CourtChangeSuccessActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
            if (tvNewDate != null) tvNewDate.setText(date);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimeSelector() {
        String[] timeSlots = {"16:00 – 18:00", "18:00 – 20:00", "20:00 – 22:00"};
        new AlertDialog.Builder(this)
                .setTitle("Chọn khung giờ mới")
                .setItems(timeSlots, (dialog, which) -> {
                    if (tvNewTime != null) {
                        tvNewTime.setText(timeSlots[which]);
                    }
                })
                .show();
    }
}
