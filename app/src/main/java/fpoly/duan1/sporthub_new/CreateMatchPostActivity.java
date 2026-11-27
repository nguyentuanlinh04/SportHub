package fpoly.duan1.sporthub_new;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

import fpoly.duan1.sporthub_new.model.MatchTeam;
import fpoly.duan1.sporthub_new.utils.MatchRepository;

public class CreateMatchPostActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private View llCardDate, llCardTime, llCardLocation;
    private TextView tvMatchDate, tvMatchTime, tvMatchLocation;

    private TextView tvPitchType5, tvPitchType7, tvPitchType11;
    private TextView tvLevelFun, tvLevelMedium, tvLevelGood, tvLevelStrong;
    private TextView tvSplit50, tvSplitLoserPays, tvSplitWePay;

    private TextView btnSubmitPost;

    private final Calendar calendar = Calendar.getInstance();

    private int selectedPitchType = 1; // 0: Sân 5, 1: Sân 7, 2: Sân 11
    private int selectedSkillLevel = 2; // 0: Vui vẻ, 1: TB, 2: Khá, 3: Mạnh
    private int selectedSplitFee = 0; // 0: 50/50, 1: Thua trả, 2: Đội mình trả

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_match_post);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCreateMatchPost), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupTabs();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);

        llCardDate = findViewById(R.id.llCardDate);
        llCardTime = findViewById(R.id.llCardTime);
        llCardLocation = findViewById(R.id.llCardLocation);

        tvMatchDate = findViewById(R.id.tvMatchDate);
        tvMatchTime = findViewById(R.id.tvMatchTime);
        tvMatchLocation = findViewById(R.id.tvMatchLocation);

        tvPitchType5 = findViewById(R.id.tvPitchType5);
        tvPitchType7 = findViewById(R.id.tvPitchType7);
        tvPitchType11 = findViewById(R.id.tvPitchType11);

        tvLevelFun = findViewById(R.id.tvLevelFun);
        tvLevelMedium = findViewById(R.id.tvLevelMedium);
        tvLevelGood = findViewById(R.id.tvLevelGood);
        tvLevelStrong = findViewById(R.id.tvLevelStrong);

        tvSplit50 = findViewById(R.id.tvSplit50);
        tvSplitLoserPays = findViewById(R.id.tvSplitLoserPays);
        tvSplitWePay = findViewById(R.id.tvSplitWePay);

        btnSubmitPost = findViewById(R.id.btnSubmitPost);
    }

    private void setupTabs() {
        // Pitch Type Tabs
        tvPitchType5.setOnClickListener(v -> selectPitchType(0));
        tvPitchType7.setOnClickListener(v -> selectPitchType(1));
        tvPitchType11.setOnClickListener(v -> selectPitchType(2));

        // Skill Level Tabs
        tvLevelFun.setOnClickListener(v -> selectSkillLevel(0));
        tvLevelMedium.setOnClickListener(v -> selectSkillLevel(1));
        tvLevelGood.setOnClickListener(v -> selectSkillLevel(2));
        tvLevelStrong.setOnClickListener(v -> selectSkillLevel(3));

        // Split Fee Tabs
        tvSplit50.setOnClickListener(v -> selectSplitFee(0));
        tvSplitLoserPays.setOnClickListener(v -> selectSplitFee(1));
        tvSplitWePay.setOnClickListener(v -> selectSplitFee(2));
    }

    private void selectPitchType(int index) {
        selectedPitchType = index;
        updateTabUI(tvPitchType5, index == 0);
        updateTabUI(tvPitchType7, index == 1);
        updateTabUI(tvPitchType11, index == 2);
    }

    private void selectSkillLevel(int index) {
        selectedSkillLevel = index;
        updateTabUI(tvLevelFun, index == 0);
        updateTabUI(tvLevelMedium, index == 1);
        updateTabUI(tvLevelGood, index == 2);
        updateTabUI(tvLevelStrong, index == 3);
    }

    private void selectSplitFee(int index) {
        selectedSplitFee = index;
        updateTabUI(tvSplit50, index == 0);
        updateTabUI(tvSplitLoserPays, index == 1);
        updateTabUI(tvSplitWePay, index == 2);
    }

    private void updateTabUI(TextView tabView, boolean isSelected) {
        if (isSelected) {
            tabView.setBackgroundResource(R.drawable.bg_tab_selected);
            tabView.setTextColor(ContextCompat.getColor(this, R.color.text_title));
            tabView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tabView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            tabView.setTextColor(ContextCompat.getColor(this, R.color.text_sub));
            tabView.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        llCardDate.setOnClickListener(v -> showDatePicker());
        llCardTime.setOnClickListener(v -> showTimeSlotPicker());
        llCardLocation.setOnClickListener(v -> showLocationPicker());

        btnSubmitPost.setOnClickListener(v -> submitNewPost());
    }

    private void submitNewPost() {
        String pitchTypeStr = "Sân 7";
        if (selectedPitchType == 0) pitchTypeStr = "Sân 5";
        else if (selectedPitchType == 2) pitchTypeStr = "Sân 11";

        String levelStr = "Khá";
        if (selectedSkillLevel == 0) levelStr = "Vui vẻ";
        else if (selectedSkillLevel == 1) levelStr = "TB";
        else if (selectedSkillLevel == 3) levelStr = "Mạnh";

        String splitStr = "50 / 50";
        if (selectedSplitFee == 1) splitStr = "Thua trả";
        else if (selectedSplitFee == 2) splitStr = "Đội mình trả";

        String dateRaw = tvMatchDate.getText().toString();
        String dayShort = "T4";
        if (dateRaw.contains("Thứ 2")) dayShort = "T2";
        else if (dateRaw.contains("Thứ 3")) dayShort = "T3";
        else if (dateRaw.contains("Thứ 4")) dayShort = "T4";
        else if (dateRaw.contains("Thứ 5")) dayShort = "T5";
        else if (dateRaw.contains("Thứ 6")) dayShort = "T6";
        else if (dateRaw.contains("Thứ 7")) dayShort = "T7";
        else if (dateRaw.contains("Chủ nhật")) dayShort = "CN";

        String timeRaw = tvMatchTime.getText().toString();
        String startTime = timeRaw.contains("–") ? timeRaw.split("–")[0].trim() : timeRaw;

        String timeTextFormatted = dayShort + " · " + startTime;

        MatchTeam newMatch = new MatchTeam(
                "m_" + System.currentTimeMillis(),
                "Minh FC",
                "Minh",
                "Gần bạn",
                levelStr,
                timeTextFormatted,
                pitchTypeStr,
                splitStr,
                "MF",
                R.drawable.bg_team_avatar_green,
                R.drawable.bg_skill_badge_orange,
                Color.parseColor("#8F5300")
        );

        MatchRepository.getInstance().addMatch(newMatch);

        Toast.makeText(this, "Đăng tin tìm đối thành công! 🎉", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    String[] vietnameseDays = {"Chủ nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
                    int dow = calendar.get(Calendar.DAY_OF_WEEK);
                    String dayLabel = vietnameseDays[dow - 1];
                    String formatted = String.format(Locale.getDefault(), "%s, %d/%d", dayLabel, selectedDay, selectedMonth + 1);
                    tvMatchDate.setText(formatted);
                },
                year,
                month,
                day
        );
        dialog.show();
    }

    private void showTimeSlotPicker() {
        String[] timeSlots = {
                "08:00 – 09:30",
                "15:00 – 16:30",
                "16:30 – 18:00",
                "18:00 – 19:30",
                "19:00 – 20:30",
                "19:30 – 21:00",
                "20:00 – 21:30",
                "20:30 – 22:00",
                "21:00 – 22:30"
        };

        new AlertDialog.Builder(this)
                .setTitle("Chọn khung giờ đá")
                .setItems(timeSlots, (dialog, which) -> tvMatchTime.setText(timeSlots[which]))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showLocationPicker() {
        String[] locations = {
                "Cụm sân Thắng Long · Sân 7-1",
                "Cụm sân Thắng Long · Sân 5-A",
                "Sân bóng Thượng Đình · Sân 7",
                "Cụm sân Mai Dịch · Sân 5",
                "Khu vực Cầu Giấy (Chưa đặt sân)"
        };

        new AlertDialog.Builder(this)
                .setTitle("Chọn sân đã đặt / khu vực")
                .setItems(locations, (dialog, which) -> tvMatchLocation.setText(locations[which]))
                .setNegativeButton("Hủy", null)
                .show();
    }
}
