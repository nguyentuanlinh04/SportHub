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

public class AdminNotificationActivity extends AppCompatActivity {

    private TextView tvNotifStepTitle;
    private TextView tvNotifStepSubtitle;
    private TextView tvNotifStepNumber;

    private View lineNotif1, lineNotif2, lineNotif3;
    private TextView tvNotif1Label, tvNotif2Label, tvNotif3Label;

    private View contentNotifStep1;
    private View contentNotifStep2;
    private View contentNotifStep3;
    private View contentNotifHistory;

    private Button btnNotifBack;
    private Button btnNotifNext;

    private ImageView btnBackNotifWizard;

    private int currentStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        initViews();
        setupListeners();
        showStep(1);
    }

    private void initViews() {
        tvNotifStepTitle = findViewById(R.id.tvNotifStepTitle);
        tvNotifStepSubtitle = findViewById(R.id.tvNotifStepSubtitle);
        tvNotifStepNumber = findViewById(R.id.tvNotifStepNumber);

        lineNotif1 = findViewById(R.id.lineNotif1);
        lineNotif2 = findViewById(R.id.lineNotif2);
        lineNotif3 = findViewById(R.id.lineNotif3);

        tvNotif1Label = findViewById(R.id.tvNotif1Label);
        tvNotif2Label = findViewById(R.id.tvNotif2Label);
        tvNotif3Label = findViewById(R.id.tvNotif3Label);

        contentNotifStep1 = findViewById(R.id.contentNotifStep1);
        contentNotifStep2 = findViewById(R.id.contentNotifStep2);
        contentNotifStep3 = findViewById(R.id.contentNotifStep3);
        contentNotifHistory = findViewById(R.id.contentNotifHistory);

        btnNotifBack = findViewById(R.id.btnNotifBack);
        btnNotifNext = findViewById(R.id.btnNotifNext);
        btnBackNotifWizard = findViewById(R.id.btnBackNotifWizard);
    }

    private void setupListeners() {
        View.OnClickListener backListener = v -> {
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

        if (btnBackNotifWizard != null) btnBackNotifWizard.setOnClickListener(backListener);
        btnNotifBack.setOnClickListener(backListener);

        btnNotifNext.setOnClickListener(v -> {
            if (currentStep == 1) {
                showStep(2);
            } else if (currentStep == 2) {
                showStep(3);
            } else if (currentStep == 3) {
                showStep(4);
            } else if (currentStep == 4) {
                // Restart wizard
                showStep(1);
            }
        });
    }

    private void showStep(int step) {
        currentStep = step;

        contentNotifStep1.setVisibility(View.GONE);
        contentNotifStep2.setVisibility(View.GONE);
        contentNotifStep3.setVisibility(View.GONE);
        contentNotifHistory.setVisibility(View.GONE);

        int colorActive = Color.parseColor("#059669");
        int colorInactive = Color.parseColor("#E5E7EB");

        if (step == 1) {
            tvNotifStepTitle.setText("Chọn đối tượng nhận");
            tvNotifStepSubtitle.setText("Xác định phạm vi trước khi soạn thông báo");
            tvNotifStepNumber.setText("BƯỚC 1/3");

            lineNotif1.setBackgroundColor(colorActive);
            lineNotif2.setBackgroundColor(colorInactive);
            lineNotif3.setBackgroundColor(colorInactive);

            tvNotif1Label.setText("1 Đối tượng");
            tvNotif1Label.setTextColor(colorActive);
            tvNotif2Label.setText("2 Nội dung");
            tvNotif2Label.setTextColor(Color.parseColor("#6B7280"));
            tvNotif3Label.setText("3 Gửi");
            tvNotif3Label.setTextColor(Color.parseColor("#6B7280"));

            contentNotifStep1.setVisibility(View.VISIBLE);

            btnNotifBack.setText("Quay lại");
            btnNotifNext.setText("Tiếp tục");

        } else if (step == 2) {
            tvNotifStepTitle.setText("Thông báo hệ thống");
            tvNotifStepSubtitle.setText("Soạn và gửi đến người dùng");
            tvNotifStepNumber.setText("BƯỚC 2/3");

            lineNotif1.setBackgroundColor(colorActive);
            lineNotif2.setBackgroundColor(colorActive);
            lineNotif3.setBackgroundColor(colorInactive);

            tvNotif1Label.setText("✓ Đối tượng");
            tvNotif1Label.setTextColor(colorActive);
            tvNotif2Label.setText("2 Nội dung");
            tvNotif2Label.setTextColor(colorActive);
            tvNotif3Label.setText("3 Gửi");
            tvNotif3Label.setTextColor(Color.parseColor("#6B7280"));

            contentNotifStep2.setVisibility(View.VISIBLE);

            btnNotifBack.setText("Quay lại");
            btnNotifNext.setText("Tiếp tục");

        } else if (step == 3) {
            tvNotifStepTitle.setText("Xác nhận gửi thông báo");
            tvNotifStepSubtitle.setText("Rà soát nội dung và thời điểm gửi");
            tvNotifStepNumber.setText("BƯỚC 3/3");

            lineNotif1.setBackgroundColor(colorActive);
            lineNotif2.setBackgroundColor(colorActive);
            lineNotif3.setBackgroundColor(colorActive);

            tvNotif1Label.setText("✓ Đối tượng");
            tvNotif1Label.setTextColor(colorActive);
            tvNotif2Label.setText("✓ Nội dung");
            tvNotif2Label.setTextColor(colorActive);
            tvNotif3Label.setText("3 Gửi");
            tvNotif3Label.setTextColor(colorActive);

            contentNotifStep3.setVisibility(View.VISIBLE);

            btnNotifBack.setText("Quay lại sửa");
            btnNotifNext.setText("Xác nhận gửi");

        } else if (step == 4) {
            tvNotifStepTitle.setText("Lịch sử thông báo");
            tvNotifStepSubtitle.setText("Tra cứu nội dung và trạng thái gửi");
            tvNotifStepNumber.setText("ĐÃ GỬI");

            contentNotifHistory.setVisibility(View.VISIBLE);

            btnNotifBack.setText("Về hệ thống");
            btnNotifNext.setText("+ Soạn thông báo");
        }
    }
}