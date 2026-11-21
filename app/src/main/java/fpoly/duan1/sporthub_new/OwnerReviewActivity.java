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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerReviewActivity extends AppCompatActivity {

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
        findViewById(R.id.btnReplyKhang).setOnClickListener(v -> showReplyDialog("Nguyễn Hoàng Khang"));
        findViewById(R.id.btnReplyTuanAnh).setOnClickListener(v -> showReplyDialog("Trần Tuấn Anh"));
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
            String reply = edtContent.getText().toString().trim();
            if (reply.isEmpty()) {
                edtContent.setError("Vui lòng nhập nội dung phản hồi");
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
            startActivity(new Intent(this, OwnerScheduleActivity.class));
            finish();
        });
        findViewById(R.id.navReviewStaff).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerStaffActivity.class));
            finish();
        });
        findViewById(R.id.navReviewFinance).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerFinanceActivity.class));
            finish();
        });
        findViewById(R.id.navReviewTab).setOnClickListener(v -> {
            // Đang ở tab Đánh giá
        });
    }
}
