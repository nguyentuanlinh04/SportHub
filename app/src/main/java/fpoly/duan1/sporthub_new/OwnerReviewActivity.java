package fpoly.duan1.sporthub_new;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerReviewActivity extends AppCompatActivity {

    private String currentComplexName = "Cụm Sân Thăng Long";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_review);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerReview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        setupActions();
        setupBottomNav();
    }

    private void setupActions() {
        String passedComplex = getIntent().getStringExtra("COMPLEX_NAME");
        if (passedComplex != null && !passedComplex.isEmpty()) {
            currentComplexName = passedComplex;
        }

        updateReviewHeaderSubtitle();

        TextView tvSub = findViewById(R.id.tvReviewHeaderSubtitle);
        if (tvSub != null) {
            tvSub.setOnClickListener(v -> showComplexSwitcherDialog());
        }

        View btnReplyKhang = findViewById(R.id.btnReplyKhang);
        if (btnReplyKhang != null) {
            btnReplyKhang.setOnClickListener(v -> showReplyDialog("Nguyễn Hoàng Khang"));
        }

        View btnBack = findViewById(R.id.btnBackReview);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View badgeRole = findViewById(R.id.badgeRoleReview);
        if (badgeRole != null) {
            badgeRole.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerReviewActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void updateReviewHeaderSubtitle() {
        TextView tvSub = findViewById(R.id.tvReviewHeaderSubtitle);
        if (tvSub != null) {
            if (currentComplexName.contains("Tất cả") || currentComplexName.contains("Toàn hệ thống")) {
                tvSub.setText("Tất cả các sân · 3 cơ sở (9 sân) ▾");
            } else if (currentComplexName.contains("Mai Dịch")) {
                tvSub.setText("Cụm sân Mai Dịch · 4 sân ▾");
            } else if (currentComplexName.contains("Riverside")) {
                tvSub.setText("Cụm sân Riverside · 0 sân ▾");
            } else {
                tvSub.setText("Cụm sân Thăng Long · 5 sân ▾");
            }
        }
    }

    private void showComplexSwitcherDialog() {
        String[] options = {
                "🌐 Tất cả các sân (Toàn hệ thống · 9 sân)",
                "⚽ Cụm sân Thăng Long (5 sân con)",
                "⚽ Cụm sân Mai Dịch (4 sân con)",
                "⚽ Cụm sân Riverside (Tạm ngưng)"
        };
        new AlertDialog.Builder(this)
                .setTitle("Chọn cụm sân xem đánh giá")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        currentComplexName = "Tất cả các sân";
                    } else if (which == 1) {
                        currentComplexName = "Cụm Sân Thăng Long";
                    } else if (which == 2) {
                        currentComplexName = "Cụm Sân Mai Dịch";
                    } else {
                        currentComplexName = "Cụm Sân Riverside";
                    }
                    updateReviewHeaderSubtitle();
                    Toast.makeText(this, "Đã chuyển sang đánh giá: " + currentComplexName, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showReplyDialog(String customerName) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reply_review, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvReplyCustomerName);
        if (tvTitle != null) {
            tvTitle.setText("Trả lời " + customerName);
        }

        EditText edtContent = dialogView.findViewById(R.id.edtReplyReviewContent);

        dialogView.findViewById(R.id.btnCloseReplyReview).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitReplyReview).setOnClickListener(v -> {
            String reply = edtContent != null ? edtContent.getText().toString().trim() : "";
            if (reply.isEmpty()) {
                if (edtContent != null) edtContent.setError("Vui lòng nhập nội dung phản hồi");
                return;
            }

            Toast.makeText(this, "Đã gửi phản hồi đến " + customerName + " thành công!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void setupBottomNav() {
        findViewById(R.id.navReviewFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navReviewSchedule).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerScheduleActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navReviewStaff).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerStaffActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navReviewFinance).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerFinanceActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navReviewTab).setOnClickListener(v -> {
            // Đang ở tab Đánh giá
        });
    }
}
