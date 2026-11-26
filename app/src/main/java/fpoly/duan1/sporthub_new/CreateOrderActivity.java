package fpoly.duan1.sporthub_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Locale;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView btnSourceWalkIn, btnSourceHotline;
    private TextView chip5a, chip5b, chip71, chip11;
    private TextView tvBookingDate, tvBookingTime;
    private TextView tvTotalPrice;
    private EditText edtCustomerName, edtCustomerPhone, edtNote;

    private String selectedSource = "walk_in";
    private String selectedCourt = "Sân 5-B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCreateOrder), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Top Facility text
        TextView tvOrderFacility = findViewById(R.id.tv_order_facility);
        if (tvOrderFacility != null) {
            tvOrderFacility.setText(StaffDataStore.getCurrentFacility());
        }

        // Back button
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Tabs
        TextView tabChangeTime = findViewById(R.id.tab_change_time);
        if (tabChangeTime != null) {
            tabChangeTime.setOnClickListener(v -> {
                Intent intent = new Intent(CreateOrderActivity.this, ChangeTimeCourtActivity.class);
                startActivity(intent);
                finish();
            });
        }

        // Source buttons
        btnSourceWalkIn = findViewById(R.id.btnSourceWalkIn);
        btnSourceHotline = findViewById(R.id.btnSourceHotline);

        if (btnSourceWalkIn != null) {
            btnSourceWalkIn.setOnClickListener(v -> selectSource("walk_in"));
        }
        if (btnSourceHotline != null) {
            btnSourceHotline.setOnClickListener(v -> selectSource("hotline"));
        }

        // Court chips
        chip5a = findViewById(R.id.chip_court_5a);
        chip5b = findViewById(R.id.chip_court_5b);
        chip71 = findViewById(R.id.chip_court_71);
        chip11 = findViewById(R.id.chip_court_11);
        tvTotalPrice = findViewById(R.id.tv_total_price);

        if (chip5a != null) chip5a.setOnClickListener(v -> selectCourt("Sân 5-A", 270000));
        if (chip5b != null) chip5b.setOnClickListener(v -> selectCourt("Sân 5-B", 270000));
        if (chip71 != null) chip71.setOnClickListener(v -> selectCourt("Sân 7-1", 350000));
        if (chip11 != null) chip11.setOnClickListener(v -> selectCourt("Sân 11", 600000));

        // Date & Time
        tvBookingDate = findViewById(R.id.tvBookingDate);
        if (tvBookingDate != null) {
            tvBookingDate.setOnClickListener(v -> showDatePicker());
        }

        tvBookingTime = findViewById(R.id.tvBookingTime);
        if (tvBookingTime != null) {
            tvBookingTime.setOnClickListener(v -> showTimeSelector());
        }

        // Customer Inputs
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtCustomerPhone = findViewById(R.id.edtCustomerPhone);
        edtNote = findViewById(R.id.edtNote);

        // Submit Button
        MaterialButton btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        if (btnSubmitOrder != null) {
            btnSubmitOrder.setOnClickListener(v -> {
                String name = edtCustomerName != null ? edtCustomerName.getText().toString().trim() : "";
                String phone = edtCustomerPhone != null ? edtCustomerPhone.getText().toString().trim() : "";

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên và số điện thoại khách hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                StaffDataStore.addNewOrder();
                Toast.makeText(this, "Tạo đơn đặt sân thành công!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateOrderActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void selectSource(String source) {
        selectedSource = source;
        if ("walk_in".equals(source)) {
            if (btnSourceWalkIn != null) {
                btnSourceWalkIn.setBackgroundResource(R.drawable.bg_btn_option_selected);
                btnSourceWalkIn.setTextColor(Color.WHITE);
            }
            if (btnSourceHotline != null) {
                btnSourceHotline.setBackgroundResource(R.drawable.bg_btn_option_unselected);
                btnSourceHotline.setTextColor(Color.parseColor("#111827"));
            }
        } else {
            if (btnSourceWalkIn != null) {
                btnSourceWalkIn.setBackgroundResource(R.drawable.bg_btn_option_unselected);
                btnSourceWalkIn.setTextColor(Color.parseColor("#111827"));
            }
            if (btnSourceHotline != null) {
                btnSourceHotline.setBackgroundResource(R.drawable.bg_btn_option_selected);
                btnSourceHotline.setTextColor(Color.WHITE);
            }
        }
    }

    private void selectCourt(String court, int price) {
        selectedCourt = court;

        resetChip(chip5a);
        resetChip(chip5b);
        resetChip(chip71);
        resetChip(chip11);

        if ("Sân 5-A".equals(court)) activateChip(chip5a);
        else if ("Sân 5-B".equals(court)) activateChip(chip5b);
        else if ("Sân 7-1".equals(court)) activateChip(chip71);
        else if ("Sân 11".equals(court)) activateChip(chip11);

        if (tvTotalPrice != null) {
            tvTotalPrice.setText(String.format(Locale.GERMANY, "%,dđ", price));
        }
    }

    private void resetChip(TextView chip) {
        if (chip != null) {
            chip.setBackgroundResource(R.drawable.bg_chip_white_pill);
            chip.setTextColor(Color.parseColor("#111827"));
        }
    }

    private void activateChip(TextView chip) {
        if (chip != null) {
            chip.setBackgroundResource(R.drawable.bg_chip_green_pill);
            chip.setTextColor(Color.WHITE);
        }
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
            if (tvBookingDate != null) tvBookingDate.setText(date);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimeSelector() {
        String[] timeSlots = {"17:00 – 18:00", "18:00 – 19:00", "19:00 – 20:00", "20:00 – 21:00", "21:00 – 22:00"};
        new AlertDialog.Builder(this)
                .setTitle("Chọn khung giờ")
                .setItems(timeSlots, (dialog, which) -> {
                    if (tvBookingTime != null) {
                        tvBookingTime.setText(timeSlots[which]);
                    }
                })
                .show();
    }
}
