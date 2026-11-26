package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShiftReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shift_report);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainShiftReport), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Setup Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_report);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_shift);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_shift) {
                    return true;
                }
                finish();
                return true;
            });
        }

        // Cash counting dynamic calculation
        EditText etActualCash = findViewById(R.id.etActualCash);
        TextView tvDifferenceValue = findViewById(R.id.tvDifferenceValue);
        LinearLayout boxDifference = findViewById(R.id.boxDifference);
        if (etActualCash != null && tvDifferenceValue != null && boxDifference != null) {
            etActualCash.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String clean = s.toString().replaceAll("[^0-9]", "");
                    if (!clean.isEmpty()) {
                        try {
                            long actual = Long.parseLong(clean);
                            long expected = 1325000;
                            long diff = actual - expected;
                            if (diff == 0) {
                                tvDifferenceValue.setText("0đ · Khớp");
                                tvDifferenceValue.setTextColor(0xFF047857);
                                boxDifference.setBackgroundResource(R.drawable.bg_badge_green_pill);
                            } else {
                                String diffStr = (diff > 0 ? "+" : "") + String.format("%,dđ", diff).replace(',', '.');
                                tvDifferenceValue.setText(diffStr + " · Lệch");
                                tvDifferenceValue.setTextColor(0xFFDC2626);
                                boxDifference.setBackgroundResource(R.drawable.bg_box_peach_soft);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            });
        }
    }
}
