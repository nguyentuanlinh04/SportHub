package fpoly.duan1.sporthub_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fpoly.duan1.sporthub_new.adapter.AddonServiceAdapter;
import fpoly.duan1.sporthub_new.adapter.DateAdapter;
import fpoly.duan1.sporthub_new.adapter.FilterChipAdapter;
import fpoly.duan1.sporthub_new.adapter.TimeSlotAdapter;
import fpoly.duan1.sporthub_new.model.AddonServiceItem;
import fpoly.duan1.sporthub_new.model.DateItem;
import fpoly.duan1.sporthub_new.model.FilterChip;
import fpoly.duan1.sporthub_new.model.TimeSlotItem;

public class BookingTimeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvSubTitle, tvSummaryText, tvTotalPrice, btnContinue;
    private ImageButton btnPrevMonth, btnNextMonth;
    private TextView tvMonthYear;
    private View btnOpenCalendar;

    private RecyclerView rvDates, rvSubPitches, rvTimeSlots, rvAddons;

    private DateAdapter dateAdapter;
    private FilterChipAdapter pitchChipAdapter;
    private TimeSlotAdapter timeSlotAdapter;
    private AddonServiceAdapter addonAdapter;

    private final List<DateItem> dateList = new ArrayList<>();
    private final List<FilterChip> pitchChips = new ArrayList<>();
    private final List<TimeSlotItem> timeSlotList = new ArrayList<>();
    private final List<AddonServiceItem> addonList = new ArrayList<>();

    private final Calendar currentCalendar = Calendar.getInstance();
    private String pitchName = "Cụm sân Thắng Long";
    private String selectedSubPitch = "Sân 5-A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_time);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBookingTime), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        extractIntentData();
        updateMonthYearHeader();
        setupDates();
        setupSubPitches();
        setupTimeSlots();
        setupAddons();
        setupListeners();
        calculateTotalPrice();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvSubTitle = findViewById(R.id.tvSubTitle);

        btnPrevMonth = findViewById(R.id.btnPrevMonth);
        btnNextMonth = findViewById(R.id.btnNextMonth);
        tvMonthYear = findViewById(R.id.tvMonthYear);
        btnOpenCalendar = findViewById(R.id.btnOpenCalendar);

        rvDates = findViewById(R.id.rvDates);
        rvSubPitches = findViewById(R.id.rvSubPitches);
        rvTimeSlots = findViewById(R.id.rvTimeSlots);
        rvAddons = findViewById(R.id.rvAddons);

        tvSummaryText = findViewById(R.id.tvSummaryText);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("pitch_name");
            if (name != null && !name.isEmpty()) {
                pitchName = name;
                tvSubTitle.setText(pitchName);
            }
        }
    }

    private void updateMonthYearHeader() {
        int month = currentCalendar.get(Calendar.MONTH) + 1;
        int year = currentCalendar.get(Calendar.YEAR);
        tvMonthYear.setText(String.format(Locale.getDefault(), "Tháng %d, %d", month, year));
    }

    private void setupDates() {
        dateAdapter = new DateAdapter(this, dateList, (item, position) -> {
            try {
                int day = Integer.parseInt(item.getDayOfMonth());
                currentCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateMonthYearHeader();
                rebuildDateListForCalendar(currentCalendar);
            } catch (Exception ignored) {}
        });

        rvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDates.setAdapter(dateAdapter);

        rebuildDateListForCalendar(currentCalendar);
    }

    private void rebuildDateListForCalendar(Calendar targetCal) {
        dateList.clear();

        Calendar cal = (Calendar) targetCal.clone();

        int targetYear = targetCal.get(Calendar.YEAR);
        int targetMonth = targetCal.get(Calendar.MONTH);
        int targetDay = targetCal.get(Calendar.DAY_OF_MONTH);

        // Đưa lịch về Thứ 2 của tuần chứa ngày targetCal
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1 = Sunday, 2 = Monday ... 7 = Saturday
        int daysFromMonday = (dayOfWeek == Calendar.SUNDAY) ? 6 : (dayOfWeek - Calendar.MONDAY);
        cal.add(Calendar.DAY_OF_MONTH, -daysFromMonday);

        String[] vietnameseDays = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};

        // Tạo 7 ngày liên tiếp từ Thứ 2 đến Chủ Nhật khớp 100% lịch thực tế
        for (int i = 0; i < 7; i++) {
            int dow = cal.get(Calendar.DAY_OF_WEEK);
            String dayLabel = vietnameseDays[dow - 1];
            int dayNum = cal.get(Calendar.DAY_OF_MONTH);
            String dayNumStr = String.format(Locale.getDefault(), "%02d", dayNum);

            boolean isSelected = (cal.get(Calendar.YEAR) == targetYear)
                    && (cal.get(Calendar.MONTH) == targetMonth)
                    && (dayNum == targetDay);

            dateList.add(new DateItem(dayLabel, dayNumStr, isSelected));

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (dateAdapter != null) {
            dateAdapter.notifyDataSetChanged();
        }
    }

    private void setupSubPitches() {
        pitchChips.clear();
        pitchChips.add(new FilterChip("Sân 5-A", true, true));
        pitchChips.add(new FilterChip("Sân 5-B", false, true));
        pitchChips.add(new FilterChip("Sân 7-1", false, true));
        pitchChips.add(new FilterChip("Sân 11", false, true));

        pitchChipAdapter = new FilterChipAdapter(this, pitchChips, (chip, position) -> {
            selectedSubPitch = chip.getTitle();
            calculateTotalPrice();
        });

        rvSubPitches.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSubPitches.setAdapter(pitchChipAdapter);
    }

    private void setupTimeSlots() {
        timeSlotList.clear();
        timeSlotList.add(new TimeSlotItem("s1", "08:00", "200k", 200000, TimeSlotItem.STATUS_AVAILABLE));
        timeSlotList.add(new TimeSlotItem("s2", "15:00", "200k", 200000, TimeSlotItem.STATUS_AVAILABLE));
        timeSlotList.add(new TimeSlotItem("s3", "16:30", "250k", 250000, TimeSlotItem.STATUS_AVAILABLE));
        timeSlotList.add(new TimeSlotItem("s4", "18:00", "Đã đặt", 0, TimeSlotItem.STATUS_BOOKED));
        timeSlotList.add(new TimeSlotItem("s5", "19:30", "350k", 350000, TimeSlotItem.STATUS_SELECTED));
        timeSlotList.add(new TimeSlotItem("s6", "21:00", "350k", 350000, TimeSlotItem.STATUS_SELECTED));
        timeSlotList.add(new TimeSlotItem("s7", "22:30", "350k", 350000, TimeSlotItem.STATUS_AVAILABLE));
        timeSlotList.add(new TimeSlotItem("s8", "23:00", "Đã đặt", 0, TimeSlotItem.STATUS_BOOKED));

        timeSlotAdapter = new TimeSlotAdapter(this, timeSlotList, (item, position) ->
                calculateTotalPrice()
        );

        rvTimeSlots.setAdapter(timeSlotAdapter);
    }

    private void setupAddons() {
        addonList.clear();
        addonList.add(new AddonServiceItem("a1", "Áo bib phân đội", "30.000đ / bộ", 30000, 1));
        addonList.add(new AddonServiceItem("a2", "Bóng tiêu chuẩn", "20.000đ / trận", 20000, 0));
        addonList.add(new AddonServiceItem("a3", "Thùng nước giải khát", "120.000đ / thùng", 120000, 1));
        addonList.add(new AddonServiceItem("a4", "Trọng tài điều hành", "150.000đ / trận", 150000, 0));

        addonAdapter = new AddonServiceAdapter(this, addonList, (item, position) ->
                calculateTotalPrice()
        );

        rvAddons.setLayoutManager(new LinearLayoutManager(this));
        rvAddons.setAdapter(addonAdapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnPrevMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateMonthYearHeader();
            rebuildDateListForCalendar(currentCalendar);
        });

        btnNextMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateMonthYearHeader();
            rebuildDateListForCalendar(currentCalendar);
        });

        btnOpenCalendar.setOnClickListener(v -> showDatePickerDialog());

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(BookingTimeActivity.this, PaymentConfirmationActivity.class);
            intent.putExtra("pitch_name", pitchName);
            intent.putExtra("sub_pitch", selectedSubPitch);
            startActivity(intent);
        });
    }

    private void showDatePickerDialog() {
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    currentCalendar.set(selectedYear, selectedMonth, selectedDay);
                    updateMonthYearHeader();
                    rebuildDateListForCalendar(currentCalendar);
                },
                year,
                month,
                day
        );
        dialog.show();
    }

    private void calculateTotalPrice() {
        long pitchTotal = 0;
        String firstTime = "";
        String lastTime = "";

        for (TimeSlotItem slot : timeSlotList) {
            if (slot.getStatus() == TimeSlotItem.STATUS_SELECTED) {
                pitchTotal += slot.getPriceValue();
                if (firstTime.isEmpty()) {
                    firstTime = slot.getTimeStr();
                }
                lastTime = slot.getTimeStr();
            }
        }

        long addonsTotal = 0;
        for (AddonServiceItem addon : addonList) {
            addonsTotal += addon.getUnitPrice() * addon.getQuantity();
        }

        long grandTotal = pitchTotal + addonsTotal;

        if (!firstTime.isEmpty()) {
            tvSummaryText.setText(selectedSubPitch + " · " + firstTime + " – " + lastTime);
        } else {
            tvSummaryText.setText(selectedSubPitch + " · Chưa chọn giờ");
        }

        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalPrice.setText(formatter.format(grandTotal).replace(",", ".") + "đ");
    }
}
