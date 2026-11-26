package fpoly.duan1.sporthub_new;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShiftHandoverActivity extends AppCompatActivity {

    private boolean isCheck1 = true;
    private boolean isCheck2 = true;
    private boolean isCheck3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shift_handover);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainShiftHandover), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Receiver selection
        View btnSelectReceiver = findViewById(R.id.btnSelectReceiver);
        TextView tvReceiverName = findViewById(R.id.tvReceiverName);
        if (btnSelectReceiver != null && tvReceiverName != null) {
            btnSelectReceiver.setOnClickListener(v -> {
                String[] receivers = {"Lan (minh họa)", "Hoàng (Nhân viên ca sáng)", "Tuấn (Nhân viên ca đêm)"};
                new AlertDialog.Builder(this)
                        .setTitle("Chọn người nhận ca")
                        .setItems(receivers, (dialog, which) -> {
                            tvReceiverName.setText(receivers[which]);
                        })
                        .show();
            });
        }

        // Checkboxes toggling
        View rowCheck1 = findViewById(R.id.rowCheck1);
        ImageView ivCheck1 = findViewById(R.id.ivCheck1);
        if (rowCheck1 != null && ivCheck1 != null) {
            rowCheck1.setOnClickListener(v -> {
                isCheck1 = !isCheck1;
                ivCheck1.setImageResource(isCheck1 ? R.drawable.ic_check_box_checked : R.drawable.ic_radio_unselected);
            });
        }

        View rowCheck2 = findViewById(R.id.rowCheck2);
        ImageView ivCheck2 = findViewById(R.id.ivCheck2);
        if (rowCheck2 != null && ivCheck2 != null) {
            rowCheck2.setOnClickListener(v -> {
                isCheck2 = !isCheck2;
                ivCheck2.setImageResource(isCheck2 ? R.drawable.ic_check_box_checked : R.drawable.ic_radio_unselected);
            });
        }

        View rowCheck3 = findViewById(R.id.rowCheck3);
        ImageView ivCheck3 = findViewById(R.id.ivCheck3);
        if (rowCheck3 != null && ivCheck3 != null) {
            rowCheck3.setOnClickListener(v -> {
                isCheck3 = !isCheck3;
                ivCheck3.setImageResource(isCheck3 ? R.drawable.ic_check_box_checked : R.drawable.ic_radio_unselected);
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

        // Save draft button
        Button btnSaveDraft = findViewById(R.id.btnSaveDraft);
        if (btnSaveDraft != null) {
            btnSaveDraft.setOnClickListener(v -> {
                Toast.makeText(this, "Đã lưu bản nháp bàn giao ca!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        // Send handover button
        Button btnSendHandover = findViewById(R.id.btnSendHandover);
        if (btnSendHandover != null) {
            btnSendHandover.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận bàn giao")
                        .setMessage("Bạn có chắc chắn muốn gửi báo cáo bàn giao ca cho người nhận?")
                        .setPositiveButton("Gửi ngay", (dialog, which) -> {
                            Toast.makeText(this, "Gửi bàn giao ca thành công! Chờ người nhận xác nhận.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ShiftHandoverActivity.this, HandoverHistoryActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }
    }
}
