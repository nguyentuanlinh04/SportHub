package fpoly.duan1.sporthub_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.adapter.DistrictAdapter;
import fpoly.duan1.sporthub_new.model.DistrictItem;

public class SelectLocationActivity extends AppCompatActivity {

    public static final String PREF_SELECTED_LOCATION = "selected_user_location";
    public static final String DEFAULT_LOCATION_HINT = "Chọn vị trí của bạn...";

    private ImageButton btnBack;
    private EditText edtLocation;
    private RelativeLayout rlUseCurrentLocation;
    private RecyclerView rvDistricts;
    private TextView btnApplyLocation;

    private DistrictAdapter adapter;
    private final List<DistrictItem> districtList = new ArrayList<>();
    private String selectedLocationText = "";
    private boolean isUpdatingFromCode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_location);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSelectLocation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupDistricts();
        setupSearchInput();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        edtLocation = findViewById(R.id.edtLocation);
        rlUseCurrentLocation = findViewById(R.id.rlUseCurrentLocation);
        rvDistricts = findViewById(R.id.rvDistricts);
        btnApplyLocation = findViewById(R.id.btnApplyLocation);

        SharedPreferences pref = getSharedPreferences("sporthub_users_db", Context.MODE_PRIVATE);
        String savedLoc = pref.getString(PREF_SELECTED_LOCATION, "");
        if (!savedLoc.isEmpty() && !DEFAULT_LOCATION_HINT.equals(savedLoc)) {
            selectedLocationText = savedLoc;
        } else {
            selectedLocationText = "";
        }

        isUpdatingFromCode = true;
        edtLocation.setText(selectedLocationText);
        isUpdatingFromCode = false;

        updateApplyButtonText();
    }

    private void setupDistricts() {
        districtList.clear();
        // Mặc định ban đầu KHÔNG tick quận nào (tất cả false)
        districtList.add(new DistrictItem("Cầu Giấy", false));
        districtList.add(new DistrictItem("Nam Từ Liêm", false));
        districtList.add(new DistrictItem("Bắc Từ Liêm", false));
        districtList.add(new DistrictItem("Đống Đa", false));
        districtList.add(new DistrictItem("Tây Hồ", false));

        adapter = new DistrictAdapter(this, districtList, (item, position) -> {
            if (item.isSelected()) {
                selectedLocationText = item.getName() + ", Hà Nội";
            } else {
                selectedLocationText = "";
            }

            isUpdatingFromCode = true;
            edtLocation.setText(selectedLocationText);
            if (!selectedLocationText.isEmpty()) {
                edtLocation.setSelection(selectedLocationText.length());
            }
            isUpdatingFromCode = false;

            updateApplyButtonText();
        });

        rvDistricts.setLayoutManager(new LinearLayoutManager(this));
        rvDistricts.setAdapter(adapter);
    }

    private void setupSearchInput() {
        edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingFromCode) return;

                selectedLocationText = s.toString().trim();
                updateApplyButtonText();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        rlUseCurrentLocation.setOnClickListener(v -> {
            selectedLocationText = "Cầu Giấy, Hà Nội";
            isUpdatingFromCode = true;
            edtLocation.setText(selectedLocationText);
            isUpdatingFromCode = false;
            updateApplyButtonText();
            Toast.makeText(this, "Đã chọn vị trí hiện tại của bạn", Toast.LENGTH_SHORT).show();
        });

        btnApplyLocation.setOnClickListener(v -> {
            String textToSave = selectedLocationText.trim();
            if (textToSave.isEmpty() || ", hà nội".equalsIgnoreCase(textToSave)) {
                textToSave = DEFAULT_LOCATION_HINT;
            }
            saveLocationToPreferences(textToSave);
            Toast.makeText(this, "Đã cập nhật vị trí: " + textToSave, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateApplyButtonText() {
        String text = selectedLocationText.trim();
        if (text.isEmpty() || ", hà nội".equalsIgnoreCase(text)) {
            btnApplyLocation.setText("Áp dụng vị trí");
        } else {
            btnApplyLocation.setText("Áp dụng " + text);
        }
    }

    private void saveLocationToPreferences(String location) {
        SharedPreferences pref = getSharedPreferences("sporthub_users_db", Context.MODE_PRIVATE);
        pref.edit().putString(PREF_SELECTED_LOCATION, location).apply();
    }
}
