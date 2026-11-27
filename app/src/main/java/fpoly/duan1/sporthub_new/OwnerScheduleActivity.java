package fpoly.duan1.sporthub_new;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class OwnerScheduleActivity extends AppCompatActivity {

    public static class SlotData {
        public String type; // "AVAILABLE", "MAINTENANCE", "BOOKED_SINGLE", "BOOKED_FIXED", "BOOKED_LIVE"
        public String team;
        public String statusLabel; // e.g. "Đặt lẻ", "Cố định", "● LIVE · Đang đá", "Bảo trì"
        public String sub; // e.g. "S7", "V-Bank", "FC Tân Thuận"
        public String phone;
        public String fee;

        public SlotData(String type, String team, String statusLabel, String sub, String phone, String fee) {
            this.type = type;
            this.team = team;
            this.statusLabel = statusLabel;
            this.sub = sub;
            this.phone = phone;
            this.fee = fee;
        }
    }

    private String currentComplexName = "Cụm Sân Thăng Long";

    // Column header TextViews
    private TextView tvColPitch1, tvColPitch2, tvColPitch3, tvColPitch4;

    // Grid slots: 7 rows x 4 columns
    private final int[][] gridSlotIds = {
            {R.id.slot_0600_5A, R.id.slot_0600_5B, R.id.slot_0600_71, R.id.slot_0600_72},
            {R.id.slot_0730_5A, R.id.slot_0730_5B, R.id.slot_0730_71, R.id.slot_0730_72},
            {R.id.slot_1500_5A, R.id.slot_1500_5B, R.id.slot_1500_71, R.id.slot_1500_72},
            {R.id.slot_1630_5A, R.id.slot_1630_5B, R.id.slot_1630_71, R.id.slot_1630_72},
            {R.id.slot_1800_5A, R.id.slot_1800_5B, R.id.slot_1800_71, R.id.slot_1800_72},
            {R.id.slot_1930_5A, R.id.slot_1930_5B, R.id.slot_1930_71, R.id.slot_1930_72},
            {R.id.slot_2100_5A, R.id.slot_2100_5B, R.id.slot_2100_71, R.id.slot_2100_72}
    };

    private final String[] timeSlots = {
            "06:00 - 07:30",
            "07:30 - 09:00",
            "15:00 - 16:30",
            "16:30 - 18:00",
            "18:00 - 19:30 (Giờ vàng)",
            "19:30 - 21:00 (Giờ vàng)",
            "21:00 - 22:30"
    };

    // Store schedule state for each complex independently
    private final Map<String, Map<Integer, SlotData>> complexSchedules = new HashMap<>();

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

        initScheduleData();
        initViews();
        loadScheduleForComplex(currentComplexName);
        setupBottomNav();
    }

    private void initScheduleData() {
        // --- 1. CỤM SÂN THĂNG LONG ---
        Map<Integer, SlotData> tlMap = new HashMap<>();
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 4; c++) {
                tlMap.put(gridSlotIds[r][c], new SlotData("AVAILABLE", "", "+", "", "", ""));
            }
        }
        // Thăng Long: Matches
        tlMap.put(R.id.slot_1630_5A, new SlotData("BOOKED_SINGLE", "FC Bán Trà", "Đặt lẻ", "S7", "0912 345 678 (Anh Trà)", "240.000đ · Đã cọc 100k"));
        tlMap.put(R.id.slot_1800_5A, new SlotData("BOOKED_LIVE", "Ngân hàng", "● LIVE · Đang đá", "V-Bank", "0978 111 222 (Anh Tùng)", "310.000đ · Đã thanh toán QR"));
        tlMap.put(R.id.slot_1800_5B, new SlotData("BOOKED_LIVE", "FC Tân Thuận", "● LIVE · Đang đá", "", "0933 445 566 (Anh Thuận)", "310.000đ · Đã thanh toán tiền mặt"));
        tlMap.put(R.id.slot_1930_5A, new SlotData("BOOKED_FIXED", "BĐS Khang Điền", "Cố định", "", "0903 888 999 (Anh Điền)", "310.000đ · Hợp đồng tháng"));
        tlMap.put(R.id.slot_1930_71, new SlotData("BOOKED_SINGLE", "Liên doanh Nhật Việt", "Đặt lẻ", "", "0966 777 888 (Anh Kenji)", "580.000đ · Đã cọc 300k"));
        // Thăng Long: Sân 7-2 bảo trì thay mặt cỏ
        for (int r = 0; r < 7; r++) {
            tlMap.put(gridSlotIds[r][3], new SlotData("MAINTENANCE", "", "Bảo trì", "", "", ""));
        }
        complexSchedules.put("THANG_LONG", tlMap);

        // --- 2. CỤM SÂN MAI DỊCH (Lịch khác biệt hoàn toàn, Sân 7-2 hoạt động bình thường) ---
        Map<Integer, SlotData> mdMap = new HashMap<>();
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 4; c++) {
                mdMap.put(gridSlotIds[r][c], new SlotData("AVAILABLE", "", "+", "", "", ""));
            }
        }
        // Mai Dịch: Matches
        mdMap.put(R.id.slot_0730_5A, new SlotData("BOOKED_SINGLE", "FC Sinh Viên", "Đặt lẻ", "ĐHQG", "0988 234 567 (Anh Nam)", "200.000đ · Đã cọc 100k"));
        mdMap.put(R.id.slot_1500_5B, new SlotData("BOOKED_SINGLE", "FC Giao Hữu", "Đặt lẻ", "FPoly", "0945 112 233 (Anh Thắng)", "220.000đ · Đã thanh toán"));
        mdMap.put(R.id.slot_1630_71, new SlotData("BOOKED_FIXED", "FC Văn Phòng", "Cố định", "FPT", "0909 333 444 (Anh Hoàng)", "450.000đ · Hợp đồng cố định"));
        mdMap.put(R.id.slot_1800_5B, new SlotData("BOOKED_SINGLE", "FC Kiến Trúc", "Đặt lẻ", "Sân 5", "0918 888 777 (Anh Duy)", "310.000đ · Đã thanh toán QR"));
        mdMap.put(R.id.slot_1800_71, new SlotData("BOOKED_FIXED", "FC Cựu Cầu Thủ", "Cố định", "Sân 7", "0977 665 544 (Anh Bảo)", "490.000đ · Hợp đồng tháng"));
        mdMap.put(R.id.slot_1930_72, new SlotData("BOOKED_LIVE", "FC Cầu Giấy", "● LIVE · Đang đá", "Sân 7-2", "0972 123 456 (Anh Quân)", "580.000đ · Đã thanh toán"));
        mdMap.put(R.id.slot_2100_5A, new SlotData("BOOKED_SINGLE", "CLB Đêm Muộn", "Đặt lẻ", "Sân 5-1", "0936 999 888 (Anh Tuấn)", "240.000đ · Đã cọc 100k"));
        complexSchedules.put("MAI_DICH", mdMap);

        // --- 3. TẤT CẢ CÁC SÂN (Tổng hợp các trận tiêu biểu của toàn hệ thống) ---
        Map<Integer, SlotData> allMap = new HashMap<>();
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 4; c++) {
                allMap.put(gridSlotIds[r][c], new SlotData("AVAILABLE", "", "+", "", "", ""));
            }
        }
        allMap.put(R.id.slot_0730_71, new SlotData("BOOKED_SINGLE", "FC Sinh Viên", "Đặt lẻ", "MD 5-1", "0988 234 567", "200.000đ"));
        allMap.put(R.id.slot_1630_5A, new SlotData("BOOKED_SINGLE", "FC Bán Trà", "Đặt lẻ", "TL 5-A", "0912 345 678", "240.000đ"));
        allMap.put(R.id.slot_1800_5A, new SlotData("BOOKED_LIVE", "Ngân hàng", "● LIVE", "TL 5-A", "0978 111 222", "310.000đ"));
        allMap.put(R.id.slot_1930_5A, new SlotData("BOOKED_FIXED", "BĐS Khang Điền", "Cố định", "TL 5-A", "0903 888 999", "310.000đ"));
        allMap.put(R.id.slot_1930_5B, new SlotData("BOOKED_SINGLE", "Nhật Việt", "Đặt lẻ", "TL 7-1", "0966 777 888", "580.000đ"));
        allMap.put(R.id.slot_1930_72, new SlotData("BOOKED_LIVE", "FC Cầu Giấy", "● LIVE", "MD 7-2", "0972 123 456", "580.000đ"));
        allMap.put(R.id.slot_2100_71, new SlotData("BOOKED_SINGLE", "Đêm Muộn", "Đặt lẻ", "MD 5-1", "0936 999 888", "240.000đ"));
        complexSchedules.put("ALL", allMap);

        // --- 4. CỤM SÂN RIVERSIDE (Đang tạm ngưng nâng cấp) ---
        Map<Integer, SlotData> rsMap = new HashMap<>();
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 4; c++) {
                rsMap.put(gridSlotIds[r][c], new SlotData("MAINTENANCE", "", "Tạm ngưng", "", "", ""));
            }
        }
        complexSchedules.put("RIVERSIDE", rsMap);
    }

    private void initViews() {
        String passedComplex = getIntent().getStringExtra("COMPLEX_NAME");
        if (passedComplex != null && !passedComplex.isEmpty()) {
            currentComplexName = passedComplex;
        }

        tvColPitch1 = findViewById(R.id.tvColPitch1);
        tvColPitch2 = findViewById(R.id.tvColPitch2);
        tvColPitch3 = findViewById(R.id.tvColPitch3);
        tvColPitch4 = findViewById(R.id.tvColPitch4);

        updateHeaderSubtitle();

        TextView tvSub = findViewById(R.id.tvScheduleHeaderSubtitle);
        if (tvSub != null) {
            tvSub.setOnClickListener(v -> showComplexSwitcherDialog());
        }

        View btnBack = findViewById(R.id.btnBackSchedule);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View btnCreateNew = findViewById(R.id.btnCreateNewBooking);
        if (btnCreateNew != null) {
            btnCreateNew.setOnClickListener(v -> {
                String[] pitches = getPitchNamesForComplex(currentComplexName);
                showCreateScheduleDialog(pitches[0], "18:00 - 19:30 (Giờ vàng)", null);
            });
        }

        View btnFixed = findViewById(R.id.btnFixedBooking);
        if (btnFixed != null) {
            btnFixed.setOnClickListener(v -> showFixedBookingDialog());
        }

        View badgeRoleSchedule = findViewById(R.id.badgeRoleSchedule);
        if (badgeRoleSchedule != null) {
            badgeRoleSchedule.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerScheduleActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private String normalizeComplexKey(String name) {
        if (name.contains("Mai Dịch")) return "MAI_DICH";
        if (name.contains("Tất cả") || name.contains("Toàn hệ thống")) return "ALL";
        if (name.contains("Riverside")) return "RIVERSIDE";
        return "THANG_LONG";
    }

    private String[] getPitchNamesForComplex(String complexName) {
        if (complexName.contains("Mai Dịch")) {
            return new String[]{"Sân 5-1", "Sân 5-2", "Sân 7-1", "Sân 7-2"};
        } else if (complexName.contains("Tất cả") || complexName.contains("Toàn hệ thống")) {
            return new String[]{"TL 5-A", "TL 7-1", "MD 5-1", "MD 7-2"};
        } else if (complexName.contains("Riverside")) {
            return new String[]{"RS 5-1", "RS 5-2", "RS 7-1", "RS 7-2"};
        } else {
            return new String[]{"Sân 5-A", "Sân 5-B", "Sân 7-1", "Sân 7-2"};
        }
    }

    private void updateHeaderSubtitle() {
        TextView tvSub = findViewById(R.id.tvScheduleHeaderSubtitle);
        if (tvSub != null) {
            if (currentComplexName.contains("Tất cả") || currentComplexName.contains("Toàn hệ thống")) {
                tvSub.setText("Tất cả các sân · 9 sân (3 cơ sở) ▾");
            } else if (currentComplexName.contains("Mai Dịch")) {
                tvSub.setText("Cụm sân Mai Dịch · 4 sân ▾");
            } else if (currentComplexName.contains("Riverside")) {
                tvSub.setText("Cụm sân Riverside · 0 sân (Tạm ngưng) ▾");
            } else {
                tvSub.setText("Cụm sân Thăng Long · 5 sân ▾");
            }
        }
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
        dialog.show();
    }

    /**
     * Hộp thoại chọn lý do hủy lịch và xác nhận hoàn cọc
     */
    private void showCancelBookingReasonDialog(SlotData data, String pitch, String time, ViewGroup slot, Dialog parentDialog) {
        String[] reasons = {
                "Khách hàng báo bận / xin dời lịch",
                "Thời tiết mưa bão sân ngập nước",
                "Trùng lịch sự kiện / giải đấu nội bộ",
                "Ban quản lý điều chỉnh lịch sân",
                "Lý do khác"
        };
        final int[] selectedReasonIndex = {0};

        new AlertDialog.Builder(this)
                .setTitle("Lý do hủy lịch đặt")
                .setSingleChoiceItems(reasons, 0, (d, which) -> selectedReasonIndex[0] = which)
                .setPositiveButton("Tiếp tục", (d, which) -> {
                    String chosenReason = reasons[selectedReasonIndex[0]];

                    // Xác nhận hủy và giải phóng ô sân
                    new AlertDialog.Builder(this)
                            .setTitle("Xác nhận hủy trận: " + data.team)
                            .setMessage("• Đội: " + data.team + "\n• Sân: " + pitch + " (" + time + ")\n• Lý do: " + chosenReason + "\n\nKhung giờ này sẽ được mở lại cho khách khác đặt ngay lập tức.")
                            .setPositiveButton("Hủy trận ngay", (d2, which2) -> {
                                // 1. Chuyển slot thành AVAILABLE
                                SlotData availableData = new SlotData("AVAILABLE", "", "+", "", "", "");

                                String key = normalizeComplexKey(currentComplexName);
                                Map<Integer, SlotData> map = complexSchedules.get(key);
                                if (map != null) {
                                    map.put(slot.getId(), availableData);
                                }

                                // 2. Render lại slot trên UI thành màu xanh lime trống
                                renderSlot(slot, availableData, pitch, time);

                                // 3. Đóng dialog chi tiết
                                if (parentDialog != null && parentDialog.isShowing()) {
                                    parentDialog.dismiss();
                                }

                                Toast.makeText(this, "Đã hủy lịch trận đấu của \"" + data.team + "\". Khung giờ " + time + " tại " + pitch + " đã được mở lại!", Toast.LENGTH_LONG).show();
                            })
                            .setNegativeButton("Quay lại", null)
                            .show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    /**
     * Bottom Sheet Dialog: Tạo lịch sân mới (Gắn trực tiếp vào ô lịch)
     */
    private void showCreateScheduleDialog(String defaultPitch, String defaultTime, ViewGroup targetSlot) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_schedule, null);
        dialog.setContentView(dialogView);

        TextView tvPitch = dialogView.findViewById(R.id.tvSchedulePitch);
        TextView tvTime = dialogView.findViewById(R.id.tvScheduleTime);
        EditText edtTeam = dialogView.findViewById(R.id.edtScheduleTeam);
        EditText edtPhone = dialogView.findViewById(R.id.edtSchedulePhone);
        EditText edtFee = dialogView.findViewById(R.id.edtScheduleFee);

        final String[] bookingType = {"Đặt lẻ"};

        if (tvPitch != null) {
            tvPitch.setText(defaultPitch);
            dialogView.findViewById(R.id.layoutSchedulePitch).setOnClickListener(v -> {
                String[] pitches = getPitchNamesForComplex(currentComplexName);
                new AlertDialog.Builder(this)
                        .setTitle("Chọn sân đặt")
                        .setItems(pitches, (d, which) -> {
                            String chosen = pitches[which];
                            tvPitch.setText(chosen);
                            if (edtFee != null) {
                                if (chosen.contains("7")) edtFee.setText("360.000đ");
                                else edtFee.setText("220.000đ");
                            }
                        })
                        .show();
            });
        }

        if (tvTime != null) {
            tvTime.setText(defaultTime);
            dialogView.findViewById(R.id.layoutScheduleTime).setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Chọn khung giờ đá")
                        .setItems(timeSlots, (d, which) -> tvTime.setText(timeSlots[which]))
                        .show();
            });
        }

        // Chọn hình thức đặt (Đặt lẻ, Cố định, Toàn sân)
        View chipSingle = dialogView.findViewById(R.id.chipTypeSingle);
        View chipFixed = dialogView.findViewById(R.id.chipTypeFixed);
        View chipFull = dialogView.findViewById(R.id.chipTypeFull);

        if (chipSingle != null) {
            chipSingle.setOnClickListener(v -> {
                bookingType[0] = "Đặt lẻ";
                Toast.makeText(this, "Hình thức: Đặt sân theo trận (Đặt lẻ)", Toast.LENGTH_SHORT).show();
            });
        }
        if (chipFixed != null) {
            chipFixed.setOnClickListener(v -> {
                bookingType[0] = "Cố định";
                Toast.makeText(this, "Hình thức: Đặt lịch cố định hàng tuần", Toast.LENGTH_SHORT).show();
            });
        }
        if (chipFull != null) {
            chipFull.setOnClickListener(v -> {
                bookingType[0] = "Đặt lẻ";
                if (tvPitch != null) tvPitch.setText("Sân 11 (Toàn sân)");
                if (edtFee != null) edtFee.setText("1.200.000đ");
                Toast.makeText(this, "Đã chọn đặt Toàn sân (Sân 11 chuẩn)!", Toast.LENGTH_SHORT).show();
            });
        }

        dialogView.findViewById(R.id.btnCloseCreateSchedule).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitCreateSchedule).setOnClickListener(v -> {
            String team = edtTeam != null ? edtTeam.getText().toString().trim() : "";
            String phone = edtPhone != null ? edtPhone.getText().toString().trim() : "";
            String pitch = tvPitch != null ? tvPitch.getText().toString() : defaultPitch;
            String time = tvTime != null ? tvTime.getText().toString() : defaultTime;
            String fee = edtFee != null && !edtFee.getText().toString().trim().isEmpty() ? edtFee.getText().toString().trim() : "240.000đ";

            if (team.isEmpty()) {
                if (edtTeam != null) edtTeam.setError("Vui lòng nhập tên đội bóng");
                return;
            }
            if (phone.isEmpty()) {
                if (edtPhone != null) edtPhone.setError("Vui lòng nhập số điện thoại");
                return;
            }

            // Nếu người dùng bấm trực tiếp vào 1 ô lịch cụ thể
            if (targetSlot != null) {
                String slotType = "Cố định".equals(bookingType[0]) ? "BOOKED_FIXED" : "BOOKED_SINGLE";
                SlotData newSlot = new SlotData(slotType, team, bookingType[0], pitch, phone, fee);

                String key = normalizeComplexKey(currentComplexName);
                Map<Integer, SlotData> map = complexSchedules.get(key);
                if (map != null) {
                    map.put(targetSlot.getId(), newSlot);
                }

                renderSlot(targetSlot, newSlot, pitch, time);
            }

            Toast.makeText(this, "Đã tạo lịch sân thành công cho \"" + team + "\" (" + pitch + " · " + time + ")!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    /**
     * Modal: Bảo trì sân con
     */
    private void showMaintenanceDialog(String pitchName) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_maintenance_pitch, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvMaintenanceTitle);
        if (tvTitle != null) {
            tvTitle.setText(pitchName + " đang bảo trì");
        }

        dialogView.findViewById(R.id.btnCloseMaintenance).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnConfirmMaintenance).setOnClickListener(v -> {
            Toast.makeText(this, "Đã kiểm tra trạng thái bảo trì cho " + pitchName + "!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
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

        if (tvComplex != null) {
            tvComplex.setText(currentComplexName);
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

        if (tvPitch != null) {
            tvPitch.setOnClickListener(v -> {
                String currentComplex = tvComplex != null ? tvComplex.getText().toString() : "";
                String[] pitches;
                if (currentComplex.contains("Mai Dịch")) {
                    pitches = new String[]{"MD · Sân 5-1", "MD · Sân 5-2", "MD · Sân 7-1", "MD · Sân 7-2"};
                } else {
                    pitches = new String[]{"TL · Sân 5-A", "TL · Sân 5-B", "TL · Sân 7-1", "TL · Sân 7-2", "TL · Sân 11 (Toàn sân)"};
                }

                new AlertDialog.Builder(this)
                        .setTitle("Chọn sân con")
                        .setItems(pitches, (d, which) -> tvPitch.setText(pitches[which]))
                        .show();
            });
        }

        if (tvTime != null) {
            tvTime.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Chọn khung giờ cố định")
                        .setItems(timeSlots, (d, which) -> tvTime.setText(timeSlots[which]))
                        .show();
            });
        }

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
            Intent intent = new Intent(this, OwnerStaffActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navScheduleFinance).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerFinanceActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navScheduleReviews).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerReviewActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
    }
}
