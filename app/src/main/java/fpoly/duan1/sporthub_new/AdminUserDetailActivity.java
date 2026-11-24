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

public class AdminUserDetailActivity extends AppCompatActivity {

    private View headerDetailView;
    private View headerLockStepper;

    private TextView tvLockStepTitle;
    private TextView tvLockStepSubtitle;
    private TextView tvLockStepNumber;

    private View lineLockStep1, lineLockStep2, lineLockStep3;
    private TextView tvLockStep1Label, tvLockStep2Label, tvLockStep3Label;

    private View contentUserDetailView;
    private View contentLockStep1;
    private View contentLockStep2;
    private View contentLockStep3;
    private View contentLockResult;

    private Button btnUserAction1;
    private Button btnUserAction2;

    private ImageView btnBackUserDetail;
    private ImageView btnBackLockStep;

    private int currentStep = 0; // 0: View Profile, 1: Lock Duration, 2: Lock Reason, 3: Lock Confirm, 4: Lock Result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_detail);

        initViews();
        setupListeners();
        showStep(0);
    }

    private void initViews() {
        headerDetailView = findViewById(R.id.headerDetailView);
        headerLockStepper = findViewById(R.id.headerLockStepper);

        tvLockStepTitle = findViewById(R.id.tvLockStepTitle);
        tvLockStepSubtitle = findViewById(R.id.tvLockStepSubtitle);
        tvLockStepNumber = findViewById(R.id.tvLockStepNumber);

        lineLockStep1 = findViewById(R.id.lineLockStep1);
        lineLockStep2 = findViewById(R.id.lineLockStep2);
        lineLockStep3 = findViewById(R.id.lineLockStep3);

        tvLockStep1Label = findViewById(R.id.tvLockStep1Label);
        tvLockStep2Label = findViewById(R.id.tvLockStep2Label);
        tvLockStep3Label = findViewById(R.id.tvLockStep3Label);

        contentUserDetailView = findViewById(R.id.contentUserDetailView);
        contentLockStep1 = findViewById(R.id.contentLockStep1);
        contentLockStep2 = findViewById(R.id.contentLockStep2);
        contentLockStep3 = findViewById(R.id.contentLockStep3);
        contentLockResult = findViewById(R.id.contentLockResult);

        btnUserAction1 = findViewById(R.id.btnUserAction1);
        btnUserAction2 = findViewById(R.id.btnUserAction2);

        btnBackUserDetail = findViewById(R.id.btnBackUserDetail);
        btnBackLockStep = findViewById(R.id.btnBackLockStep);
    }

    private void setupListeners() {
        if (btnBackUserDetail != null) {
            btnBackUserDetail.setOnClickListener(v -> finish());
        }
        if (btnBackLockStep != null) {
            btnBackLockStep.setOnClickListener(v -> showStep(0));
        }

        btnUserAction1.setOnClickListener(v -> {
            if (currentStep == 0) {
                // Mở khóa
                Toast.makeText(this, "Đã mở khóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else if (currentStep == 1) {
                // Hủy
                showStep(0);
            } else if (currentStep == 2) {
                // Quay lại
                showStep(1);
            } else if (currentStep == 3) {
                // Quay lại sửa
                showStep(2);
            } else if (currentStep == 4) {
                // Về danh sách
                finish();
            }
        });

        btnUserAction2.setOnClickListener(v -> {
            if (currentStep == 0) {
                // Click "Khóa vĩnh viễn" -> Bắt đầu quy trình khóa tài khoản BƯỚC 1
                showStep(1);
            } else if (currentStep == 1) {
                // Tiếp tục -> Sang Bước 2
                showStep(2);
            } else if (currentStep == 2) {
                // Tiếp tục -> Sang Bước 3
                showStep(3);
            } else if (currentStep == 3) {
                // Xác nhận khóa -> Sang Màn hình Kết quả (Bước 4)
                showStep(4);
            } else if (currentStep == 4) {
                // Xem tài khoản -> Trở về xem thông tin chi tiết
                showStep(0);
            }
        });
    }

    private void showStep(int step) {
        currentStep = step;

        headerDetailView.setVisibility(View.GONE);
        headerLockStepper.setVisibility(View.GONE);

        contentUserDetailView.setVisibility(View.GONE);
        contentLockStep1.setVisibility(View.GONE);
        contentLockStep2.setVisibility(View.GONE);
        contentLockStep3.setVisibility(View.GONE);
        contentLockResult.setVisibility(View.GONE);

        int colorActive = Color.parseColor("#86EFAC");
        int colorInactive = Color.parseColor("#046A38");

        if (step == 0) {
            // VIEW USER DETAIL PROFILE
            headerDetailView.setVisibility(View.VISIBLE);
            contentUserDetailView.setVisibility(View.VISIBLE);

            btnUserAction1.setText("Mở khóa");
            btnUserAction1.setBackgroundColor(Color.parseColor("#046A38"));
            btnUserAction1.setTextColor(Color.parseColor("#FFFFFF"));

            btnUserAction2.setText("Khóa vĩnh viễn");
            btnUserAction2.setBackgroundColor(Color.parseColor("#DC2626"));
            btnUserAction2.setTextColor(Color.parseColor("#FFFFFF"));

        } else if (step == 1) {
            // LOCK STEP 1: CHỌN THỜI HẠN KHÓA
            headerLockStepper.setVisibility(View.VISIBLE);
            tvLockStepTitle.setText("Chọn thời hạn khóa");
            tvLockStepSubtitle.setText("Thuyết lập thời hạn cho quyết định khóa mới");
            tvLockStepNumber.setText("BƯỚC 1/3");

            lineLockStep1.setBackgroundColor(colorActive);
            lineLockStep2.setBackgroundColor(colorInactive);
            lineLockStep3.setBackgroundColor(colorInactive);

            contentLockStep1.setVisibility(View.VISIBLE);

            btnUserAction1.setText("Hủy");
            btnUserAction1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnUserAction1.setTextColor(Color.parseColor("#111827"));

            btnUserAction2.setText("Tiếp tục");
            btnUserAction2.setBackgroundColor(Color.parseColor("#046A38"));
            btnUserAction2.setTextColor(Color.parseColor("#FFFFFF"));

        } else if (step == 2) {
            // LOCK STEP 2: CHỌN LÝ DO
            headerLockStepper.setVisibility(View.VISIBLE);
            tvLockStepTitle.setText("Khóa tài khoản");
            tvLockStepSubtitle.setText("Kiểm tra quyết định trước khi xác nhận");
            tvLockStepNumber.setText("BƯỚC 2/3");

            lineLockStep1.setBackgroundColor(colorActive);
            lineLockStep2.setBackgroundColor(colorActive);
            lineLockStep3.setBackgroundColor(colorInactive);

            contentLockStep2.setVisibility(View.VISIBLE);

            btnUserAction1.setText("Quay lại");
            btnUserAction1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnUserAction1.setTextColor(Color.parseColor("#111827"));

            btnUserAction2.setText("Tiếp tục");
            btnUserAction2.setBackgroundColor(Color.parseColor("#046A38"));
            btnUserAction2.setTextColor(Color.parseColor("#FFFFFF"));

        } else if (step == 3) {
            // LOCK STEP 3: XÁC NHẬN KHÓA
            headerLockStepper.setVisibility(View.VISIBLE);
            tvLockStepTitle.setText("Xác nhận khóa tài khoản");
            tvLockStepSubtitle.setText("Kiểm tra quyết định trước khi xác nhận");
            tvLockStepNumber.setText("BƯỚC 3/3");

            lineLockStep1.setBackgroundColor(colorActive);
            lineLockStep2.setBackgroundColor(colorActive);
            lineLockStep3.setBackgroundColor(colorActive);

            contentLockStep3.setVisibility(View.VISIBLE);

            btnUserAction1.setText("Quay lại sửa");
            btnUserAction1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnUserAction1.setTextColor(Color.parseColor("#111827"));

            btnUserAction2.setText("Xác nhận khóa");
            btnUserAction2.setBackgroundColor(Color.parseColor("#046A38"));
            btnUserAction2.setTextColor(Color.parseColor("#FFFFFF"));

        } else if (step == 4) {
            // LOCK RESULT SCREEN
            contentLockResult.setVisibility(View.VISIBLE);

            btnUserAction1.setText("Về danh sách");
            btnUserAction1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnUserAction1.setTextColor(Color.parseColor("#111827"));

            btnUserAction2.setText("Xem tài khoản");
            btnUserAction2.setBackgroundColor(Color.parseColor("#046A38"));
            btnUserAction2.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }
}