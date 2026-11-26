package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duan1.sporthub_new.R;

public class AdminOwnerAuditActivity extends AppCompatActivity {

    private TextView tvStepTitle;
    private TextView tvStepSubtitle;
    private TextView tvStepNumber;

    private View lineStep1, lineStep2, lineStep3;
    private TextView tvStep1Label, tvStep2Label, tvStep3Label;

    private View layoutStep1Content;
    private View layoutStep2Content;
    private View layoutStep3Content;
    private View layoutResultContent;

    private Button btnAuditBack;
    private Button btnAuditNext;

    private int currentStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_owner_audit);

        initViews();
        setupListeners();
        showStep(1);
    }

    private void initViews() {
        tvStepTitle = findViewById(R.id.tvStepTitle);
        tvStepSubtitle = findViewById(R.id.tvStepSubtitle);
        tvStepNumber = findViewById(R.id.tvStepNumber);

        lineStep1 = findViewById(R.id.lineStep1);
        lineStep2 = findViewById(R.id.lineStep2);
        lineStep3 = findViewById(R.id.lineStep3);

        tvStep1Label = findViewById(R.id.tvStep1Label);
        tvStep2Label = findViewById(R.id.tvStep2Label);
        tvStep3Label = findViewById(R.id.tvStep3Label);

        layoutStep1Content = findViewById(R.id.layoutStep1Content);
        layoutStep2Content = findViewById(R.id.layoutStep2Content);
        layoutStep3Content = findViewById(R.id.layoutStep3Content);
        layoutResultContent = findViewById(R.id.layoutResultContent);

        btnAuditBack = findViewById(R.id.btnAuditBack);
        btnAuditNext = findViewById(R.id.btnAuditNext);
    }

    private void setupListeners() {
        btnAuditBack.setOnClickListener(v -> {
            if (currentStep == 1) {
                finish();
            } else if (currentStep == 2) {
                showStep(1);
            } else if (currentStep == 3) {
                // Button 1 in step 3 acts as "Từ chối" (Reject)
                Toast.makeText(this, "Hồ sơ đã bị từ chối thẩm định!", Toast.LENGTH_SHORT).show();
                finish();
            } else if (currentStep == 4) {
                // Button 1 in result screen acts as "Về danh sách"
                finish();
            }
        });

        btnAuditNext.setOnClickListener(v -> {
            if (currentStep == 1) {
                showStep(2);
            } else if (currentStep == 2) {
                showStep(3);
            } else if (currentStep == 3) {
                // Button 2 in step 3 acts as "Duyệt và kích hoạt"
                showStep(4);
            } else if (currentStep == 4) {
                // Button 2 in result screen acts as "Xem hồ sơ" -> Chuyển sang màn hình Tài khoản (Tab 2: Tài khoản)
                Intent intent = new Intent(AdminOwnerAuditActivity.this, AdminMainActivity.class);
                intent.putExtra("OPEN_TAB", 2); // Tab Tài khoản
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showStep(int step) {
        currentStep = step;
        layoutStep1Content.setVisibility(View.GONE);
        layoutStep2Content.setVisibility(View.GONE);
        layoutStep3Content.setVisibility(View.GONE);
        layoutResultContent.setVisibility(View.GONE);

        int colorActive = Color.parseColor("#059669");
        int colorInactive = Color.parseColor("#E5E7EB");
        int textActive = Color.parseColor("#059669");
        int textInactive = Color.parseColor("#6B7280");

        if (step == 1) {
            tvStepTitle.setText("Kiểm tra giấy tờ");
            tvStepSubtitle.setText("Đối chiếu thông tin trước khi kiểm tra cơ sở");
            tvStepNumber.setText("BƯỚC 1/3");

            lineStep1.setBackgroundColor(colorActive);
            lineStep2.setBackgroundColor(colorInactive);
            lineStep3.setBackgroundColor(colorInactive);

            tvStep1Label.setText("1 Giấy tờ");
            tvStep1Label.setTextColor(textActive);
            tvStep2Label.setText("2 Cơ sở");
            tvStep2Label.setTextColor(textInactive);
            tvStep3Label.setText("3 Quyết định");
            tvStep3Label.setTextColor(textInactive);

            layoutStep1Content.setVisibility(View.VISIBLE);

            btnAuditBack.setText("Quay lại");
            btnAuditBack.setTextColor(Color.parseColor("#111827"));
            btnAuditBack.setBackgroundColor(Color.parseColor("#FFFFFF"));

            btnAuditNext.setText("Tiếp tục");
            btnAuditNext.setBackgroundColor(Color.parseColor("#046A38"));
            btnAuditNext.setTextColor(Color.parseColor("#FFFFFF"));

        } else if (step == 2) {
            tvStepTitle.setText("Kiểm tra cơ sở");
            tvStepSubtitle.setText("Cụm sân Thăng Long · Nguyễn Văn An");
            tvStepNumber.setText("BƯỚC 2/3");

            lineStep1.setBackgroundColor(colorActive);
            lineStep2.setBackgroundColor(colorActive);
            lineStep3.setBackgroundColor(colorInactive);

            tvStep1Label.setText("✓ Giấy tờ");
            tvStep1Label.setTextColor(textActive);
            tvStep2Label.setText("2 Cơ sở");
            tvStep2Label.setTextColor(textActive);
            tvStep3Label.setText("3 Quyết định");
            tvStep3Label.setTextColor(textInactive);

            layoutStep2Content.setVisibility(View.VISIBLE);

            btnAuditBack.setText("Quay lại");
            btnAuditBack.setTextColor(Color.parseColor("#111827"));

            btnAuditNext.setText("Tới Quyết định");
            btnAuditNext.setBackgroundColor(Color.parseColor("#046A38"));

        } else if (step == 3) {
            tvStepTitle.setText("Thẩm định hồ sơ");
            tvStepSubtitle.setText("Cụm sân Thăng Long · Nguyễn Văn An");
            tvStepNumber.setText("BƯỚC 3/3");

            lineStep1.setBackgroundColor(colorActive);
            lineStep2.setBackgroundColor(colorActive);
            lineStep3.setBackgroundColor(colorActive);

            tvStep1Label.setText("✓ Giấy tờ");
            tvStep1Label.setTextColor(textActive);
            tvStep2Label.setText("✓ Cơ sở");
            tvStep2Label.setTextColor(textActive);
            tvStep3Label.setText("3 Quyết định");
            tvStep3Label.setTextColor(textActive);

            layoutStep3Content.setVisibility(View.VISIBLE);

            btnAuditBack.setText("Từ chối");
            btnAuditBack.setTextColor(Color.parseColor("#DC2626"));

            btnAuditNext.setText("Duyệt và kích hoạt");
            btnAuditNext.setBackgroundColor(Color.parseColor("#046A38"));

        } else if (step == 4) {
            // RESULT SCREEN
            tvStepTitle.setText("Kết quả thẩm định");
            tvStepSubtitle.setText("Hồ sơ đã được duyệt và kích hoạt thành công!");
            tvStepNumber.setText("HOÀN TẤT");

            layoutResultContent.setVisibility(View.VISIBLE);

            btnAuditBack.setText("Về danh sách");
            btnAuditBack.setTextColor(Color.parseColor("#111827"));

            btnAuditNext.setText("Xem hồ sơ");
            btnAuditNext.setBackgroundColor(Color.parseColor("#046A38"));
        }
    }
}