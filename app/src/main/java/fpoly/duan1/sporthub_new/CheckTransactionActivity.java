package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckTransactionActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private FrameLayout flLoadingContainer;
    private ProgressBar progressBar;
    private ImageView ivSuccessCheck;
    private TextView tvStatusTitle, tvStatusSubTitle;
    private TextView btnViewTicket, btnRecheckStatus;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_transaction);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCheckTransaction), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        startVerificationProcess();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        flLoadingContainer = findViewById(R.id.flLoadingContainer);
        progressBar = findViewById(R.id.progressBar);
        ivSuccessCheck = findViewById(R.id.ivSuccessCheck);

        tvStatusTitle = findViewById(R.id.tvStatusTitle);
        tvStatusSubTitle = findViewById(R.id.tvStatusSubTitle);

        btnViewTicket = findViewById(R.id.btnViewTicket);
        btnRecheckStatus = findViewById(R.id.btnRecheckStatus);
    }

    private void startVerificationProcess() {
        // Trạng thái ban đầu: Vòng xoay chạy trong 5 giây
        progressBar.setVisibility(View.VISIBLE);
        ivSuccessCheck.setVisibility(View.GONE);
        flLoadingContainer.setBackgroundResource(R.drawable.bg_loading_circle);

        tvStatusTitle.setText("Thanh toán đang xử lý");
        tvStatusTitle.setTextColor(ContextCompat.getColor(this, R.color.text_title));
        tvStatusSubTitle.setText("Sports Hub đang kiểm tra giao dịch cọc của bạn.\nĐơn chưa được xác nhận.");

        // Sau 5 giây -> Chuyển sang vẽ dấu tích xanh hoàn thành
        timerRunnable = () -> {
            progressBar.setVisibility(View.GONE);
            flLoadingContainer.setBackgroundResource(R.drawable.bg_success_circle);
            ivSuccessCheck.setVisibility(View.VISIBLE);

            // Hiệu ứng phồng to & mờ dần (Scale & Alpha Animation) cho dấu tích xanh
            ivSuccessCheck.setScaleX(0.2f);
            ivSuccessCheck.setScaleY(0.2f);
            ivSuccessCheck.setAlpha(0f);
            ivSuccessCheck.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(400)
                    .start();

            tvStatusTitle.setText("Thanh toán thành công! 🎉");
            tvStatusTitle.setTextColor(ContextCompat.getColor(this, R.color.primary_green));
            tvStatusSubTitle.setText("Giao dịch cọc 255.000đ của bạn đã được xác nhận.\nVé đặt sân của bạn đã sẵn sàng!");

            Toast.makeText(CheckTransactionActivity.this, "Giao dịch đã được xác nhận thành công!", Toast.LENGTH_SHORT).show();
        };

        handler.postDelayed(timerRunnable, 5000);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnViewTicket.setOnClickListener(v -> {
            Intent intent = new Intent(CheckTransactionActivity.this, BookingTicketActivity.class);
            startActivity(intent);
        });

        btnRecheckStatus.setOnClickListener(v -> {
            if (timerRunnable != null) {
                handler.removeCallbacks(timerRunnable);
            }
            startVerificationProcess();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }
    }
}
