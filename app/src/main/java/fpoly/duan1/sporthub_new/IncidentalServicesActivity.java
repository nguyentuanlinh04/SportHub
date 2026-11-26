package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class IncidentalServicesActivity extends AppCompatActivity {

    private int q1 = 4, q2 = 0, q3 = 1, q4 = 0, q5 = 0;
    private final int p1 = 10000, p2 = 15000, p3 = 20000, p4 = 30000, p5 = 150000;
    private final int baseOrderFee = 850000; // Tiền sân 700k + Áo bib 30k + Thùng nước 120k = 850k

    private TextView tvQty1, tvQty2, tvQty3, tvQty4, tvQty5;
    private TextView tvSubtotalAmount, tvTotalAmount, tvSubtotalDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_incidental_services);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainIncidental), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        tvQty1 = findViewById(R.id.tvQty1);
        tvQty2 = findViewById(R.id.tvQty2);
        tvQty3 = findViewById(R.id.tvQty3);
        tvQty4 = findViewById(R.id.tvQty4);
        tvQty5 = findViewById(R.id.tvQty5);

        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvSubtotalDesc = findViewById(R.id.tvSubtotalDesc);

        // Item 1: Nước suối
        findViewById(R.id.btnMinus1).setOnClickListener(v -> {
            if (q1 > 0) q1--;
            updateUI();
        });
        findViewById(R.id.btnPlus1).setOnClickListener(v -> {
            q1++;
            updateUI();
        });

        // Item 2: Nước ngọt
        findViewById(R.id.btnMinus2).setOnClickListener(v -> {
            if (q2 > 0) q2--;
            updateUI();
        });
        findViewById(R.id.btnPlus2).setOnClickListener(v -> {
            q2++;
            updateUI();
        });

        // Item 3: Thuê bóng thi đấu
        findViewById(R.id.btnMinus3).setOnClickListener(v -> {
            if (q3 > 0) q3--;
            updateUI();
        });
        findViewById(R.id.btnPlus3).setOnClickListener(v -> {
            q3++;
            updateUI();
        });

        // Item 4: Thuê áo tập
        findViewById(R.id.btnMinus4).setOnClickListener(v -> {
            if (q4 > 0) q4--;
            updateUI();
        });
        findViewById(R.id.btnPlus4).setOnClickListener(v -> {
            q4++;
            updateUI();
        });

        // Item 5: Trọng tài điều hành
        findViewById(R.id.btnMinus5).setOnClickListener(v -> {
            if (q5 > 0) q5--;
            updateUI();
        });
        findViewById(R.id.btnPlus5).setOnClickListener(v -> {
            q5++;
            updateUI();
        });

        Button btnAddToInvoice = findViewById(R.id.btnAddToInvoice);
        if (btnAddToInvoice != null) {
            btnAddToInvoice.setOnClickListener(v -> {
                int serviceTotal = (q1 * p1) + (q2 * p2) + (q3 * p3) + (q4 * p4) + (q5 * p5);
                StaffDataStore.setIncidentalServiceAdded(serviceTotal);
                StaffDataStore.addIncidentalService();
                Toast.makeText(this, "Đã cộng dịch vụ phát sinh vào hóa đơn thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_schedule);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                finish();
                return true;
            });
        }

        updateUI();
    }

    private void updateUI() {
        if (tvQty1 != null) tvQty1.setText(String.valueOf(q1));
        if (tvQty2 != null) tvQty2.setText(String.valueOf(q2));
        if (tvQty3 != null) tvQty3.setText(String.valueOf(q3));
        if (tvQty4 != null) tvQty4.setText(String.valueOf(q4));
        if (tvQty5 != null) tvQty5.setText(String.valueOf(q5));

        int serviceTotal = (q1 * p1) + (q2 * p2) + (q3 * p3) + (q4 * p4) + (q5 * p5);
        int grandTotal = baseOrderFee + serviceTotal;

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedServiceTotal = formatter.format(serviceTotal) + "đ";
        String formattedGrandTotal = formatter.format(grandTotal) + "đ";

        // Generate description breakdown
        List<String> parts = new ArrayList<>();
        if (q1 > 0) parts.add(q1 > 1 ? q1 + " × 10.000đ" : "10.000đ");
        if (q2 > 0) parts.add(q2 > 1 ? q2 + " × 15.000đ" : "15.000đ");
        if (q3 > 0) parts.add(q3 > 1 ? q3 + " × 20.000đ" : "20.000đ");
        if (q4 > 0) parts.add(q4 > 1 ? q4 + " × 30.000đ" : "30.000đ");
        if (q5 > 0) parts.add(q5 > 1 ? q5 + " × 150.000đ" : "150.000đ");

        if (tvSubtotalDesc != null) {
            if (parts.isEmpty()) {
                tvSubtotalDesc.setText("Phụ phí phát sinh (Chưa chọn dịch vụ)");
            } else {
                tvSubtotalDesc.setText("Phụ phí phát sinh (" + String.join(" + ", parts) + ")");
            }
        }
        if (tvSubtotalAmount != null) {
            tvSubtotalAmount.setText(formattedServiceTotal);
        }
        if (tvTotalAmount != null) {
            tvTotalAmount.setText(formattedGrandTotal);
        }
    }
}
