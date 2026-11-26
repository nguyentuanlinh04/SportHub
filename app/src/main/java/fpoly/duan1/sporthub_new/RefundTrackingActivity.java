package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RefundTrackingActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvRefundStatusBadge;

    private ProgressBar progressBarStep2, progressBarStep3;
    private ImageView ivCheckStep2, ivOutlineStep3, ivCheckStep3;

    private TextView tvTitleStep2, tvSubStep2;
    private TextView tvTitleStep3, tvSubStep3;
    private TextView tvBankNoticeTitle, tvBankNoticeSub;
    private TextView btnSupportRefund;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnableStep2, runnableStep3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_refund_tracking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainRefundTracking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        startRefundTimelineAnimation();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvRefundStatusBadge = findViewById(R.id.tvRefundStatusBadge);

        progressBarStep2 = findViewById(R.id.progressBarStep2);
        ivCheckStep2 = findViewById(R.id.ivCheckStep2);
        tvTitleStep2 = findViewById(R.id.tvTitleStep2);
        tvSubStep2 = findViewById(R.id.tvSubStep2);

        ivOutlineStep3 = findViewById(R.id.ivOutlineStep3);
        progressBarStep3 = findViewById(R.id.progressBarStep3);
        ivCheckStep3 = findViewById(R.id.ivCheckStep3);
        tvTitleStep3 = findViewById(R.id.tvTitleStep3);
        tvSubStep3 = findViewById(R.id.tvSubStep3);

        tvBankNoticeTitle = findViewById(R.id.tvBankNoticeTitle);
        tvBankNoticeSub = findViewById(R.id.tvBankNoticeSub);
        btnSupportRefund = findViewById(R.id.btnSupportRefund);
    }

    private void startRefundTimelineAnimation() {
        // Sau 4 giây (khoảng 3-5s): Vòng quay ở Bước 2 hoàn thành -> Vẽ dấu tích xanh!
        runnableStep2 = () -> {
            if (isFinishing() || isDestroyed()) return;

            progressBarStep2.setVisibility(View.GONE);
            ivCheckStep2.setVisibility(View.VISIBLE);
            animateCheckmark(ivCheckStep2);

            tvTitleStep2.setText("Đã chuyển tiền hoàn");
            tvSubStep2.setText("Lệnh chuyển cọc 255.000đ đã được gửi tới ngân hàng.");

            // Bước 3 bắt đầu quay
            ivOutlineStep3.setVisibility(View.GONE);
            progressBarStep3.setVisibility(View.VISIBLE);
            tvTitleStep3.setText("Đang chờ ghi có");
            tvSubStep3.setText("Ngân hàng đang xử lý hạch toán vào tài khoản của bạn.");
        };

        // Sau 7 giây: Bước 3 hoàn thành -> Hoàn tất 100%!
        runnableStep3 = () -> {
            if (isFinishing() || isDestroyed()) return;

            progressBarStep3.setVisibility(View.GONE);
            ivCheckStep3.setVisibility(View.VISIBLE);
            animateCheckmark(ivCheckStep3);

            tvRefundStatusBadge.setText("Đã hoàn tiền thành công 🎉");

            tvTitleStep3.setText("Hoàn tất thành công");
            tvSubStep3.setText("Khoản cọc 255.000đ đã được hoàn trả thành công.");

            tvBankNoticeTitle.setText("Đã ghi nhận tiền vào tài khoản!");
            tvBankNoticeSub.setText("Hệ thống đã nhận được xác nhận chuyển khoản hoàn tất từ ngân hàng.");

            Toast.makeText(RefundTrackingActivity.this, "Hoàn tiền 255.000đ thành công! 🎉", Toast.LENGTH_SHORT).show();
        };

        handler.postDelayed(runnableStep2, 4000);
        handler.postDelayed(runnableStep3, 7500);
    }

    private void animateCheckmark(View view) {
        view.setScaleX(0.2f);
        view.setScaleY(0.2f);
        view.setAlpha(0f);
        view.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(400)
                .start();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnSupportRefund.setOnClickListener(v ->
                Toast.makeText(this, "Liên hệ tổng đài hỗ trợ CSKH Sporthub...", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (runnableStep2 != null) handler.removeCallbacks(runnableStep2);
        if (runnableStep3 != null) handler.removeCallbacks(runnableStep3);
    }
}
