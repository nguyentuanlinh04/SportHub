package fpoly.duan1.sporthub_new.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminReportAuditActivity extends AppCompatActivity {

    private View headerReportLight;
    private View headerReportDark;

    private TextView tvReportStepTitleLight;
    private TextView tvReportStepSubtitleLight;
    private TextView tvReportStepNumLight;

    private View lineRep1, lineRep2, lineRep3;
    private TextView tvRep1Label, tvRep2Label, tvRep3Label;

    private View contentReportStep1;
    private View contentReportStep2;
    private View contentReportStep3;
    private View contentReportResult;

    private Button btnReportBack;
    private Button btnReportNext;

    private ImageView btnBackReportLight;
    private ImageView btnBackReportDark;
    private ImageView btnBackReportResult;

    private int currentStep = 1; // 1: Tiếp nhận, 2: Xác minh, 3: Xử lý, 4: Kết quả

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_audit);

        initViews();
        setupListeners();
        showStep(1);
    }

    private void initViews() {
        headerReportLight = findViewById(R.id.headerReportLight);
        headerReportDark = findViewById(R.id.headerReportDark);

        tvReportStepTitleLight = findViewById(R.id.tvReportStepTitleLight);
        tvReportStepSubtitleLight = findViewById(R.id.tvReportStepSubtitleLight);
        tvReportStepNumLight = findViewById(R.id.tvReportStepNumLight);

        lineRep1 = findViewById(R.id.lineRep1);
        lineRep2 = findViewById(R.id.lineRep2);
        lineRep3 = findViewById(R.id.lineRep3);

        tvRep1Label = findViewById(R.id.tvRep1Label);
        tvRep2Label = findViewById(R.id.tvRep2Label);
        tvRep3Label = findViewById(R.id.tvRep3Label);

        contentReportStep1 = findViewById(R.id.contentReportStep1);
        contentReportStep2 = findViewById(R.id.contentReportStep2);
        contentReportStep3 = findViewById(R.id.contentReportStep3);
        contentReportResult = findViewById(R.id.contentReportResult);

        btnReportBack = findViewById(R.id.btnReportBack);
        btnReportNext = findViewById(R.id.btnReportNext);

        btnBackReportLight = findViewById(R.id.btnBackReportLight);
        btnBackReportDark = findViewById(R.id.btnBackReportDark);
        btnBackReportResult = findViewById(R.id.btnBackReportResult);
    }

    private void setupListeners() {
        View.OnClickListener backClickListener = v -> {
            if (currentStep == 1) {
                finish();
            } else if (currentStep == 2) {
                showStep(1);
            } else if (currentStep == 3) {
                showStep(2);
            } else if (currentStep == 4) {
                finish();
            }
        };

        if (btnBackReportLight != null) btnBackReportLight.setOnClickListener(backClickListener);
        if (btnBackReportDark != null) btnBackReportDark.setOnClickListener(backClickListener);
        if (btnBackReportResult != null) btnBackReportResult.setOnClickListener(backClickListener);
        btnReportBack.setOnClickListener(backClickListener);

        btnReportNext.setOnClickListener(v -> {
            if (currentStep == 1) {
                showStep(2);
            } else if (currentStep == 2) {
                showStep(3);
            } else if (currentStep == 3) {
                showStep(4);
            } else if (currentStep == 4) {
                Toast.makeText(this, "Đang mở chi tiết báo cáo khiếu nại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showStep(int step) {
        currentStep = step;

        headerReportLight.setVisibility(View.GONE);
        headerReportDark.setVisibility(View.GONE);

        contentReportStep1.setVisibility(View.GONE);
        contentReportStep2.setVisibility(View.GONE);
        contentReportStep3.setVisibility(View.GONE);
        contentReportResult.setVisibility(View.GONE);

        int colorActive = Color.parseColor("#059669");
        int colorInactive = Color.parseColor("#E5E7EB");

        if (step == 1) {
            headerReportLight.setVisibility(View.VISIBLE);
            tvReportStepTitleLight.setText("Tiếp nhận khiếu nại");
            tvReportStepSubtitleLight.setText("Ngôn từ xúc phạm · 09/11 08:15");
            tvReportStepNumLight.setText("BƯỚC 1/3");

            lineRep1.setBackgroundColor(colorActive);
            lineRep2.setBackgroundColor(colorInactive);
            lineRep3.setBackgroundColor(colorInactive);

            tvRep1Label.setText("1 Tiếp nhận");
            tvRep1Label.setTextColor(colorActive);
            tvRep2Label.setText("2 Xác minh");
            tvRep2Label.setTextColor(Color.parseColor("#6B7280"));
            tvRep3Label.setText("3 Xử lý");
            tvRep3Label.setTextColor(Color.parseColor("#6B7280"));

            contentReportStep1.setVisibility(View.VISIBLE);

            btnReportBack.setText("Quay lại");
            btnReportNext.setText("Tiếp nhận & tiếp tục");

        } else if (step == 2) {
            headerReportLight.setVisibility(View.VISIBLE);
            tvReportStepTitleLight.setText("Xác minh khiếu nại");
            tvReportStepSubtitleLight.setText("Ngôn từ xúc phạm · Quân · báo bởi Minh");
            tvReportStepNumLight.setText("BƯỚC 2/3");

            lineRep1.setBackgroundColor(colorActive);
            lineRep2.setBackgroundColor(colorActive);
            lineRep3.setBackgroundColor(colorInactive);

            tvRep1Label.setText("✓ Tiếp nhận");
            tvRep1Label.setTextColor(colorActive);
            tvRep2Label.setText("2 Xác minh");
            tvRep2Label.setTextColor(colorActive);
            tvRep3Label.setText("3 Xử lý");
            tvRep3Label.setTextColor(Color.parseColor("#6B7280"));

            contentReportStep2.setVisibility(View.VISIBLE);

            btnReportBack.setText("Quay lại");
            btnReportNext.setText("Chuyển tới xử lý");

        } else if (step == 3) {
            headerReportDark.setVisibility(View.VISIBLE);
            contentReportStep3.setVisibility(View.VISIBLE);

            btnReportBack.setText("Quay lại");
            btnReportNext.setText("Hoàn tất xử lý");

        } else if (step == 4) {
            contentReportResult.setVisibility(View.VISIBLE);

            btnReportBack.setText("Về danh sách");
            btnReportNext.setText("Xem chi tiết");
        }
    }
}