package fpoly.duan1.sporthub_new;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class OwnerStaffActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "owner_staff_storage";

    private TextView chipStaffThangLong, chipStaffMaiDich, chipStaffRiverside;
    private View layoutStaffListThangLong, layoutStaffListMaiDich, layoutStaffListRiverside;
    private TextView tvStaffHeaderSubtitle;

    // Staff Card Views
    private View cardStaffHung, cardStaffThao, cardStaffLoc;
    private View cardStaffNam, cardStaffMai;
    private View cardStaffTrong, cardStaffTuan;

    private SharedPreferences prefs;

    // Track active complex index (0: Thăng Long, 1: Mai Dịch, 2: Riverside)
    private int currentComplexIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_staff);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerStaff), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupComplexTabs();
        setupStaffActions();
        applyStaffVisibilityAndCounts();
        setupBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyStaffVisibilityAndCounts();
    }

    private void initViews() {
        chipStaffThangLong = findViewById(R.id.chipStaffThangLong);
        chipStaffMaiDich = findViewById(R.id.chipStaffMaiDich);
        chipStaffRiverside = findViewById(R.id.chipStaffRiverside);

        layoutStaffListThangLong = findViewById(R.id.layoutStaffListThangLong);
        layoutStaffListMaiDich = findViewById(R.id.layoutStaffListMaiDich);
        layoutStaffListRiverside = findViewById(R.id.layoutStaffListRiverside);

        tvStaffHeaderSubtitle = findViewById(R.id.tvStaffHeaderSubtitle);

        // Staff cards
        cardStaffHung = findViewById(R.id.cardStaffHung);
        cardStaffThao = findViewById(R.id.cardStaffThao);
        cardStaffLoc = findViewById(R.id.cardStaffLoc);

        cardStaffNam = findViewById(R.id.cardStaffNam);
        cardStaffMai = findViewById(R.id.cardStaffMai);

        cardStaffTrong = findViewById(R.id.cardStaffTrong);
        cardStaffTuan = findViewById(R.id.cardStaffTuan);
    }

    private void setupComplexTabs() {
        chipStaffThangLong.setOnClickListener(v -> selectComplexTab(0));
        chipStaffMaiDich.setOnClickListener(v -> selectComplexTab(1));
        chipStaffRiverside.setOnClickListener(v -> selectComplexTab(2));
    }

    private void selectComplexTab(int index) {
        currentComplexIndex = index;

        // Reset all chips style
        resetChip(chipStaffThangLong);
        resetChip(chipStaffMaiDich);
        resetChip(chipStaffRiverside);

        // Hide all lists
        layoutStaffListThangLong.setVisibility(View.GONE);
        layoutStaffListMaiDich.setVisibility(View.GONE);
        layoutStaffListRiverside.setVisibility(View.GONE);

        if (index == 0) {
            setChipSelected(chipStaffThangLong);
            layoutStaffListThangLong.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            setChipSelected(chipStaffMaiDich);
            layoutStaffListMaiDich.setVisibility(View.VISIBLE);
        } else {
            setChipSelected(chipStaffRiverside);
            layoutStaffListRiverside.setVisibility(View.VISIBLE);
        }

        applyStaffVisibilityAndCounts();
    }

    private void resetChip(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        chip.setTextColor(ContextCompat.getColor(this, R.color.owner_chip_unselected_text));
    }

    private void setChipSelected(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_owner_chip_selected);
        chip.setTextColor(Color.WHITE);
    }

    /**
     * Đồng bộ trạng thái đã xóa vào SharedPreferences và cập nhật số lượng nhân sự hiển thị
     */
    private void applyStaffVisibilityAndCounts() {
        // Cụm Thăng Long
        boolean isHungDeleted = prefs.getBoolean("deleted_hung", false);
        boolean isThaoDeleted = prefs.getBoolean("deleted_thao", false);
        boolean isLocDeleted = prefs.getBoolean("deleted_loc", false);

        if (cardStaffHung != null) cardStaffHung.setVisibility(isHungDeleted ? View.GONE : View.VISIBLE);
        if (cardStaffThao != null) cardStaffThao.setVisibility(isThaoDeleted ? View.GONE : View.VISIBLE);
        if (cardStaffLoc != null) cardStaffLoc.setVisibility(isLocDeleted ? View.GONE : View.VISIBLE);

        int countTL = (isHungDeleted ? 0 : 1) + (isThaoDeleted ? 0 : 1) + (isLocDeleted ? 0 : 1);
        chipStaffThangLong.setText("Cụm Thăng Long (" + countTL + ")");

        // Cụm Mai Dịch
        boolean isNamDeleted = prefs.getBoolean("deleted_nam", false);
        boolean isMaiDeleted = prefs.getBoolean("deleted_mai", false);

        if (cardStaffNam != null) cardStaffNam.setVisibility(isNamDeleted ? View.GONE : View.VISIBLE);
        if (cardStaffMai != null) cardStaffMai.setVisibility(isMaiDeleted ? View.GONE : View.VISIBLE);

        int countMD = (isNamDeleted ? 0 : 1) + (isMaiDeleted ? 0 : 1);
        chipStaffMaiDich.setText("Cụm Mai Dịch (" + countMD + ")");

        // Cụm Riverside
        boolean isTrongDeleted = prefs.getBoolean("deleted_trong", false);
        boolean isTuanDeleted = prefs.getBoolean("deleted_tuan", false);

        if (cardStaffTrong != null) cardStaffTrong.setVisibility(isTrongDeleted ? View.GONE : View.VISIBLE);
        if (cardStaffTuan != null) cardStaffTuan.setVisibility(isTuanDeleted ? View.GONE : View.VISIBLE);

        int countRS = (isTrongDeleted ? 0 : 1) + (isTuanDeleted ? 0 : 1);
        chipStaffRiverside.setText("Cụm Riverside (" + countRS + ")");

        // Cập nhật Subtitle trên Header
        if (currentComplexIndex == 0) {
            tvStaffHeaderSubtitle.setText("Cụm Sân Thăng Long · " + countTL + " nhân viên");
        } else if (currentComplexIndex == 1) {
            tvStaffHeaderSubtitle.setText("Cụm Sân Mai Dịch · " + countMD + " nhân viên");
        } else {
            tvStaffHeaderSubtitle.setText("Cụm Sân Riverside · " + countRS + " nhân viên");
        }
    }

    private void setupStaffActions() {
        // Nút "+ Thêm nhân viên"
        findViewById(R.id.btnAddStaff).setOnClickListener(v -> showAddStaffDialog());

        // --- CỤM THĂNG LONG ---
        findViewById(R.id.btnPermHung).setOnClickListener(v -> showPermissionDialog("Nguyễn Văn Hùng", "Quản lý cơ sở · Ca sáng"));
        findViewById(R.id.btnDeleteHung).setOnClickListener(v -> confirmDeleteStaff("hung", "Nguyễn Văn Hùng", "Cụm Thăng Long"));

        findViewById(R.id.btnPermThao).setOnClickListener(v -> showPermissionDialog("Trần Thị Thu Thảo", "Thu ngân & CSKH · Ca chiều tối"));
        findViewById(R.id.btnDeleteThao).setOnClickListener(v -> confirmDeleteStaff("thao", "Trần Thị Thu Thảo", "Cụm Thăng Long"));

        findViewById(R.id.btnPermLoc).setOnClickListener(v -> {
            Toast.makeText(this, "Đã mở khóa tài khoản cho Phạm Đức Lộc thành công!", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.btnDeleteLoc).setOnClickListener(v -> confirmDeleteStaff("loc", "Phạm Đức Lộc", "Cụm Thăng Long"));

        // --- CỤM MAI DỊCH ---
        findViewById(R.id.btnPermNam).setOnClickListener(v -> showPermissionDialog("Hoàng Văn Nam", "Giám sát ca trực & Đặt sân"));
        findViewById(R.id.btnDeleteNam).setOnClickListener(v -> confirmDeleteStaff("nam", "Hoàng Văn Nam", "Cụm Mai Dịch"));

        findViewById(R.id.btnPermMai).setOnClickListener(v -> showPermissionDialog("Lê Thị Mai", "Thu ngân & Chăm sóc khách hàng"));
        findViewById(R.id.btnDeleteMai).setOnClickListener(v -> confirmDeleteStaff("mai", "Lê Thị Mai", "Cụm Mai Dịch"));

        // --- CỤM RIVERSIDE ---
        findViewById(R.id.btnPermTrong).setOnClickListener(v -> showPermissionDialog("Vũ Đình Trọng", "Quản lý ca trực · Cụm Riverside"));
        findViewById(R.id.btnDeleteTrong).setOnClickListener(v -> confirmDeleteStaff("trong", "Vũ Đình Trọng", "Cụm Riverside"));

        findViewById(R.id.btnPermTuan).setOnClickListener(v -> showPermissionDialog("Đặng Minh Tuấn", "Kỹ thuật & Hỗ trợ sân bóng"));
        findViewById(R.id.btnDeleteTuan).setOnClickListener(v -> confirmDeleteStaff("tuan", "Đặng Minh Tuấn", "Cụm Riverside"));
    }

    private void confirmDeleteStaff(String staffId, String staffName, String complexName) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa nhân viên")
                .setMessage("Bạn có chắc chắn muốn xóa vĩnh viễn nhân viên \"" + staffName + "\" khỏi " + complexName + " không?\n\nNhân viên này sẽ bị xóa khỏi danh sách và hủy toàn bộ quyền vận hành.")
                .setPositiveButton("Xóa ngay", (dialog, which) -> {
                    // Lưu vĩnh viễn trạng thái đã xóa vào SharedPreferences
                    prefs.edit().putBoolean("deleted_" + staffId, true).apply();
                    // Cập nhật giao diện và số lượng
                    applyStaffVisibilityAndCounts();
                    Toast.makeText(this, "Đã xóa nhân viên \"" + staffName + "\" thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showAddStaffDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_staff, null);
        dialog.setContentView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtAddStaffName);
        EditText edtPhone = dialogView.findViewById(R.id.edtAddStaffPhone);
        TextView tvComplex = dialogView.findViewById(R.id.tvAddStaffComplex);
        TextView tvRole = dialogView.findViewById(R.id.tvAddStaffRole);
        TextView tvShift = dialogView.findViewById(R.id.tvAddStaffShift);

        // Đặt mặc định cụm sân theo tab hiện tại
        String initialComplex = (currentComplexIndex == 0) ? "Cụm Sân Thăng Long" : (currentComplexIndex == 1 ? "Cụm Sân Mai Dịch" : "Cụm Sân Riverside");
        if (tvComplex != null) {
            tvComplex.setText(initialComplex);
            tvComplex.setOnClickListener(v -> {
                String[] complexes = {"Cụm Sân Thăng Long", "Cụm Sân Mai Dịch", "Cụm Sân Riverside"};
                new AlertDialog.Builder(this)
                        .setTitle("Chọn cụm sân làm việc")
                        .setItems(complexes, (d, which) -> tvComplex.setText(complexes[which]))
                        .show();
            });
        }

        // Chọn Vị trí / Vai trò
        if (tvRole != null) {
            tvRole.setOnClickListener(v -> {
                String[] roles = {
                        "Thu ngân & CSKH",
                        "Quản lý cơ sở",
                        "Giám sát ca trực",
                        "Kỹ thuật & bảo trì sân",
                        "Nhân viên hỗ trợ sân bóng"
                };
                new AlertDialog.Builder(this)
                        .setTitle("Chọn vị trí / vai trò")
                        .setItems(roles, (d, which) -> tvRole.setText(roles[which]))
                        .show();
            });
        }

        // Chọn Ca làm việc
        if (tvShift != null) {
            tvShift.setOnClickListener(v -> {
                String[] shifts = {
                        "Ca sáng (06:00 - 14:00)",
                        "Ca chiều (14:00 - 22:00)",
                        "Ca tối (18:00 - 24:00)",
                        "Ca gãy / Linh hoạt (Theo ca đặt)",
                        "Toàn thời gian (Cả ngày)"
                };
                new AlertDialog.Builder(this)
                        .setTitle("Chọn ca làm việc")
                        .setItems(shifts, (d, which) -> tvShift.setText(shifts[which]))
                        .show();
            });
        }

        dialogView.findViewById(R.id.btnCloseAddStaff).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitAddStaff).setOnClickListener(v -> {
            String name = edtName != null ? edtName.getText().toString().trim() : "";
            String phone = edtPhone != null ? edtPhone.getText().toString().trim() : "";
            String complex = tvComplex != null ? tvComplex.getText().toString() : initialComplex;
            String role = tvRole != null ? tvRole.getText().toString() : "Nhân viên";
            String shift = tvShift != null ? tvShift.getText().toString() : "Ca sáng";

            if (name.isEmpty()) {
                if (edtName != null) edtName.setError("Vui lòng nhập họ tên");
                return;
            }
            if (phone.isEmpty()) {
                if (edtPhone != null) edtPhone.setError("Vui lòng nhập số điện thoại");
                return;
            }

            Toast.makeText(this, "Đã thêm \"" + name + "\" (" + role + " - " + shift + ") vào " + complex + " thành công!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void showPermissionDialog(String staffName, String role) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_permission_staff, null);
        dialog.setContentView(dialogView);

        TextView tvName = dialogView.findViewById(R.id.tvStaffPermName);
        if (tvName != null) {
            tvName.setText("Phân quyền: " + staffName);
        }

        View btnClose = dialogView.findViewById(R.id.btnCloseStaffPerm);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        View btnSave = dialogView.findViewById(R.id.btnSaveStaffPerm);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(this, "Đã cập nhật phân quyền cho " + staffName + " thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        }

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void setupBottomNav() {
        findViewById(R.id.navStaffFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navStaffSchedule).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerScheduleActivity.class));
            finish();
        });
        findViewById(R.id.navStaffTab).setOnClickListener(v -> {
            // Đang ở tab Nhân sự
        });
        findViewById(R.id.navStaffFinance).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerFinanceActivity.class));
            finish();
        });
        findViewById(R.id.navStaffReviews).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerReviewActivity.class));
            finish();
        });
    }
}
