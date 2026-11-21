package fpoly.duan1.sporthub_new;

import android.app.AlertDialog;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerScheduleActivity extends AppCompatActivity {

    private TextView chipFilterAllComplex, chipFilterThangLong, chipFilterMaiDich, chipFilterRiverside;
    private View containerThangLongSchedule, containerMaiDichSchedule, containerRiversideSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_schedule);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerSchedule), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupFilters();
        setupPausedComplexHandling();
        setupBottomNav();
    }

    private void initViews() {
        chipFilterAllComplex = findViewById(R.id.chipFilterAllComplex);
        chipFilterThangLong = findViewById(R.id.chipFilterThangLong);
        chipFilterMaiDich = findViewById(R.id.chipFilterMaiDich);
        chipFilterRiverside = findViewById(R.id.chipFilterRiverside);

        containerThangLongSchedule = findViewById(R.id.containerThangLongSchedule);
        containerMaiDichSchedule = findViewById(R.id.containerMaiDichSchedule);
        containerRiversideSchedule = findViewById(R.id.containerRiversideSchedule);

        findViewById(R.id.btnBackSchedule).setOnClickListener(v -> finish());
        findViewById(R.id.btnFixedBooking).setOnClickListener(v -> showFixedBookingDialog());
    }

    private void setupFilters() {
        chipFilterAllComplex.setOnClickListener(v -> selectFilter(0));
        chipFilterThangLong.setOnClickListener(v -> selectFilter(1));
        chipFilterMaiDich.setOnClickListener(v -> selectFilter(2));
        chipFilterRiverside.setOnClickListener(v -> selectFilter(3));
    }

    private void selectFilter(int index) {
        resetChip(chipFilterAllComplex);
        resetChip(chipFilterThangLong);
        resetChip(chipFilterMaiDich);
        resetChip(chipFilterRiverside);

        containerThangLongSchedule.setVisibility(View.GONE);
        containerMaiDichSchedule.setVisibility(View.GONE);
        containerRiversideSchedule.setVisibility(View.GONE);

        if (index == 0) {
            setChipSelected(chipFilterAllComplex);
            containerThangLongSchedule.setVisibility(View.VISIBLE);
            containerMaiDichSchedule.setVisibility(View.VISIBLE);
            containerRiversideSchedule.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            setChipSelected(chipFilterThangLong);
            containerThangLongSchedule.setVisibility(View.VISIBLE);
        } else if (index == 2) {
            setChipSelected(chipFilterMaiDich);
            containerMaiDichSchedule.setVisibility(View.VISIBLE);
        } else {
            setChipSelected(chipFilterRiverside);
            containerRiversideSchedule.setVisibility(View.VISIBLE);
        }
    }

    private void resetChip(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chip.setTextColor(ContextCompat.getColor(this, R.color.owner_chip_unselected_text));
    }

    private void setChipSelected(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_selected);
        chip.setTextColor(Color.WHITE);
    }

    private void setupPausedComplexHandling() {
        // Nút Kích hoạt lại Cụm Riverside
        View btnReactivate = findViewById(R.id.btnReactivateRiverside);
        if (btnReactivate != null) {
            btnReactivate.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Kích hoạt lại Cụm Sân Riverside?")
                        .setMessage("Cụm sân Riverside đang tạm ngưng để bảo trì mặt cỏ nhân tạo (từ 15/11 - 25/11).\n\nBạn có chắc chắn muốn chuyển sang trạng thái ĐANG HOẠT ĐỘNG và mở lại quyền nhận đặt sân không?")
                        .setPositiveButton("Kích hoạt ngay", (dialog, which) -> {
                            Toast.makeText(this, "Đã kích hoạt lại Cụm Sân Riverside! Trạng thái: Đang hoạt động.", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        // Bắt sự kiện khi chạm vào bảng lịch của cụm đang tạm ngưng
        View tableRiverside = findViewById(R.id.tableRiversideSchedule);
        if (tableRiverside != null) {
            tableRiverside.setOnClickListener(v -> notifyComplexPaused());
        }
    }

    private void notifyComplexPaused() {
        new AlertDialog.Builder(this)
                .setTitle("⚠️ Cụm sân Tạm ngưng hoạt động")
                .setMessage("Cụm sân Riverside hiện đang TẠM NGƯNG HOẠT ĐỘNG để thay cỏ nhân tạo & nâng cấp đèn chiếu sáng (từ 15/11 - 25/11).\n\nTất cả các khung giờ sân con đang bị khóa, hệ thống không cho phép nhận lịch đá mới.\n\nNếu muốn nhận khách, vui lòng bấm \"⚙️ Kích hoạt lại cụm sân\" ở trên.")
                .setPositiveButton("Đã hiểu", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showFixedBookingDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_fixed_booking, null);
        dialog.setContentView(dialogView);

        EditText edtTeam = dialogView.findViewById(R.id.edtFixedBookingTeam);
        EditText edtPhone = dialogView.findViewById(R.id.edtFixedBookingPhone);
        TextView tvComplex = dialogView.findViewById(R.id.tvFixedBookingComplex);
        TextView tvPitch = dialogView.findViewById(R.id.tvFixedBookingPitch);
        TextView tvTime = dialogView.findViewById(R.id.tvFixedBookingTime);
        TextView tvDays = dialogView.findViewById(R.id.tvFixedBookingDays);

        // 1. Chọn Cụm sân
        if (tvComplex != null) {
            tvComplex.setOnClickListener(v -> {
                String[] complexes = {"Cụm Sân Thăng Long", "Cụm Sân Mai Dịch", "Cụm Sân Riverside (Tạm ngưng)"};
                new AlertDialog.Builder(this)
                        .setTitle("Chọn cụm sân đặt lịch cố định")
                        .setItems(complexes, (d, which) -> {
                            if (which == 2) {
                                new AlertDialog.Builder(this)
                                        .setTitle("⚠️ Cụm sân đang Tạm ngưng")
                                        .setMessage("Cụm sân Riverside đang tạm ngưng hoạt động để bảo trì cỏ. Vui lòng chọn Cụm Thăng Long hoặc Cụm Mai Dịch để đặt lịch cố định!")
                                        .setPositiveButton("Đã hiểu", (dialog1, which1) -> dialog1.dismiss())
                                        .show();
                                return;
                            }
                            tvComplex.setText(complexes[which]);
                            if (which == 0 && tvPitch != null) {
                                tvPitch.setText("TL · Sân 5-A");
                            } else if (which == 1 && tvPitch != null) {
                                tvPitch.setText("MD · Sân 5-1");
                            }
                        })
                        .show();
            });
        }

        // 2. Chọn Sân con
        if (tvPitch != null) {
            tvPitch.setOnClickListener(v -> {
                String currentComplex = tvComplex != null ? tvComplex.getText().toString() : "";
                String[] pitches;
                if (currentComplex.contains("Mai Dịch")) {
                    pitches = new String[]{"MD · Sân 5-1", "MD · Sân 5-2", "MD · Sân 7-1", "MD · Sân 7-2"};
                } else {
                    pitches = new String[]{"TL · Sân 5-A", "TL · Sân 5-B", "TL · Sân 7-1", "TL · Sân 7-2", "TL · Sân 11 Chuẩn"};
                }

                new AlertDialog.Builder(this)
                        .setTitle("Chọn sân con")
                        .setItems(pitches, (d, which) -> tvPitch.setText(pitches[which]))
                        .show();
            });
        }

        // 3. Chọn Khung giờ
        if (tvTime != null) {
            tvTime.setOnClickListener(v -> {
                String[] times = {
                        "06:00 - 07:30",
                        "07:30 - 09:00",
                        "16:30 - 18:00",
                        "18:00 - 19:30",
                        "19:00 - 20:30",
                        "19:30 - 21:00",
                        "21:00 - 22:30"
                };
                new AlertDialog.Builder(this)
                        .setTitle("Chọn khung giờ cố định")
                        .setItems(times, (d, which) -> tvTime.setText(times[which]))
                        .show();
            });
        }

        // 4. Chọn Ngày cố định trong tuần
        if (tvDays != null) {
            tvDays.setOnClickListener(v -> {
                String[] days = {
                        "🗓️ Thứ Hai & Thứ Sáu hàng tuần",
                        "🗓️ Thứ Ba & Thứ Bảy hàng tuần",
                        "🗓️ Thứ Tư & Chủ Nhật hàng tuần",
                        "🗓️ Thứ Hai, Tư, Sáu (3 buổi/tuần)",
                        "🗓️ Thứ Ba, Năm, Bảy (3 buổi/tuần)",
                        "🗓️ Hàng ngày trong tuần (T2 - T6)",
                        "🗓️ Cuối tuần (Thứ Bảy & Chủ Nhật)"
                };
                new AlertDialog.Builder(this)
                        .setTitle("Chọn ngày cố định trong tuần")
                        .setItems(days, (d, which) -> tvDays.setText(days[which]))
                        .show();
            });
        }

        dialogView.findViewById(R.id.btnCloseFixedBooking).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitFixedBooking).setOnClickListener(v -> {
            String team = edtTeam != null ? edtTeam.getText().toString().trim() : "";
            String phone = edtPhone != null ? edtPhone.getText().toString().trim() : "";
            String complex = tvComplex != null ? tvComplex.getText().toString() : "Cụm Thăng Long";
            String pitch = tvPitch != null ? tvPitch.getText().toString() : "Sân 5-A";
            String time = tvTime != null ? tvTime.getText().toString() : "19:00 - 20:30";
            String days = tvDays != null ? tvDays.getText().toString() : "Thứ Hai & Thứ Sáu";

            if (team.isEmpty()) {
                if (edtTeam != null) edtTeam.setError("Vui lòng nhập tên đội bóng");
                return;
            }
            if (phone.isEmpty()) {
                if (edtPhone != null) edtPhone.setError("Vui lòng nhập số điện thoại");
                return;
            }

            Toast.makeText(this, "Đã giữ lịch cố định cho \"" + team + "\" (" + pitch + " · " + time + " · " + days + ") tại " + complex + " thành công!", Toast.LENGTH_LONG).show();
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
        findViewById(R.id.navScheduleFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navScheduleTab).setOnClickListener(v -> {
            // Đang ở tab Lịch sân
        });
        findViewById(R.id.navScheduleStaff).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerStaffActivity.class));
            finish();
        });
        findViewById(R.id.navScheduleFinance).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerFinanceActivity.class));
            finish();
        });
        findViewById(R.id.navScheduleReviews).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerReviewActivity.class));
            finish();
        });
    }
}
