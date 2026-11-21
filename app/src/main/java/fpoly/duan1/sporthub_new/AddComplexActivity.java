package fpoly.duan1.sporthub_new;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class AddComplexActivity extends AppCompatActivity {

    private EditText edtComplexName;
    private EditText edtComplexHotline;
    private EditText edtComplexAddress;
    private EditText edtComplexDescription;

    private Spinner spCity, spDistrict, spWard;
    private TextView tvOpenTime, tvCloseTime;
    private RadioGroup rgStatus;
    private RadioButton rbStatusActive, rbStatusPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_complex);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainAddComplex), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupSpinners();
        setupTimePickers();
        setupStatusRadio();
        setupActions();
    }

    private void initViews() {
        edtComplexName = findViewById(R.id.edtComplexName);
        edtComplexHotline = findViewById(R.id.edtComplexHotline);
        edtComplexAddress = findViewById(R.id.edtComplexAddress);
        edtComplexDescription = findViewById(R.id.edtComplexDescription);

        spCity = findViewById(R.id.spCity);
        spDistrict = findViewById(R.id.spDistrict);
        spWard = findViewById(R.id.spWard);

        tvOpenTime = findViewById(R.id.tvOpenTime);
        tvCloseTime = findViewById(R.id.tvCloseTime);

        rgStatus = findViewById(R.id.rgStatus);
        rbStatusActive = findViewById(R.id.rbStatusActive);
        rbStatusPaused = findViewById(R.id.rbStatusPaused);
    }

    private void setupSpinners() {
        String[] cities = {"Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spCity.setAdapter(cityAdapter);

        String[] districts = {"Cầu Giấy", "Nam Từ Liêm", "Bắc Từ Liêm", "Thanh Xuân", "Đống Đa", "Ba Đình"};
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);
        spDistrict.setAdapter(districtAdapter);

        String[] wards = {"Mai Dịch", "Dịch Vọng", "Dịch Vọng Hậu", "Nghĩa Đô", "Nghĩa Tân", "Yên Hòa"};
        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wards);
        spWard.setAdapter(wardAdapter);
    }

    private void setupTimePickers() {
        tvOpenTime.setOnClickListener(v -> showTimePicker(5, 30, (view, hourOfDay, minute) -> {
            tvOpenTime.setText(String.format(Locale.getDefault(), "🕒 %02d:%02d", hourOfDay, minute));
        }));

        tvCloseTime.setOnClickListener(v -> showTimePicker(23, 30, (view, hourOfDay, minute) -> {
            tvCloseTime.setText(String.format(Locale.getDefault(), "🕒 %02d:%02d", hourOfDay, minute));
        }));
    }

    private void showTimePicker(int defaultHour, int defaultMinute, TimePickerDialog.OnTimeSetListener listener) {
        new TimePickerDialog(this, listener, defaultHour, defaultMinute, true).show();
    }

    private void setupStatusRadio() {
        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbStatusActive) {
                rbStatusActive.setBackgroundResource(R.drawable.bg_radio_option_active);
                rbStatusPaused.setBackgroundResource(R.drawable.bg_radio_option_inactive);
            } else if (checkedId == R.id.rbStatusPaused) {
                rbStatusActive.setBackgroundResource(R.drawable.bg_radio_option_inactive);
                rbStatusPaused.setBackgroundResource(R.drawable.bg_radio_option_active);
            }
        });
    }

    private void setupActions() {
        findViewById(R.id.btnBackAddComplex).setOnClickListener(v -> finish());
        findViewById(R.id.btnCancelAddComplex).setOnClickListener(v -> finish());

        findViewById(R.id.btnSelectMapLocation).setOnClickListener(v -> {
            Toast.makeText(this, "Đã ghim vị trí tọa độ cơ sở trên bản đồ!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSubmitCreateComplex).setOnClickListener(v -> {
            String name = edtComplexName.getText().toString().trim();
            String phone = edtComplexHotline.getText().toString().trim();
            String address = edtComplexAddress.getText().toString().trim();

            if (name.isEmpty()) {
                edtComplexName.setError("Vui lòng nhập tên cụm sân");
                edtComplexName.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                edtComplexHotline.setError("Vui lòng nhập hotline liên hệ");
                edtComplexHotline.requestFocus();
                return;
            }

            if (address.isEmpty()) {
                edtComplexAddress.setError("Vui lòng nhập số nhà, tên đường");
                edtComplexAddress.requestFocus();
                return;
            }

            Toast.makeText(this, "Tạo cụm sân \"" + name + "\" thành công!", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
