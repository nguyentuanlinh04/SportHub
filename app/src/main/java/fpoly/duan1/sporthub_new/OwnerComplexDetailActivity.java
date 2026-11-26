package fpoly.duan1.sporthub_new;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerComplexDetailActivity extends AppCompatActivity {

    private TextView tabOverview, tabPitchList, tabPriceTable;
    private View layoutOverviewContainer, layoutPitchListContainer, layoutPriceTableContainer;
    private TextView tvComplexDetailTitle, tvComplexDetailSub, tvComplexDetailAddress, tvComplexDetailHours, tvComplexDetailCoordinates, tvComplexDetailHotline;
    private View cardPitch11;
    private String currentComplexName = "Cụm Sân Thăng Long";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_complex_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerComplexDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupTabs();
        setupPitchActions();
        setupBottomNav();
    }

    private void initViews() {
        tabOverview = findViewById(R.id.tabOverview);
        tabPitchList = findViewById(R.id.tabPitchList);
        tabPriceTable = findViewById(R.id.tabPriceTable);

        layoutOverviewContainer = findViewById(R.id.layoutOverviewContainer);
        layoutPitchListContainer = findViewById(R.id.layoutPitchListContainer);
        layoutPriceTableContainer = findViewById(R.id.layoutPriceTableContainer);

        tvComplexDetailTitle = findViewById(R.id.tvComplexDetailTitle);
        tvComplexDetailSub = findViewById(R.id.tvComplexDetailSub);
        tvComplexDetailAddress = findViewById(R.id.tvComplexDetailAddress);
        tvComplexDetailHours = findViewById(R.id.tvComplexDetailHours);
        tvComplexDetailCoordinates = findViewById(R.id.tvComplexDetailCoordinates);
        tvComplexDetailHotline = findViewById(R.id.tvComplexDetailHotline);
        cardPitch11 = findViewById(R.id.cardPitch11);

        String complexName = getIntent().getStringExtra("COMPLEX_NAME");
        if (complexName != null && !complexName.isEmpty()) {
            currentComplexName = complexName;
        }

        bindComplexData(currentComplexName);

        if (tvComplexDetailSub != null) {
            tvComplexDetailSub.setOnClickListener(v -> showComplexSwitcherDialog());
        }

        findViewById(R.id.btnBackDetail).setOnClickListener(v -> finish());
        findViewById(R.id.btnViewMap).setOnClickListener(v -> {
            Toast.makeText(this, "Xem vị trí cơ sở trên Google Maps", Toast.LENGTH_SHORT).show();
        });

        View badgeRoleDetail = findViewById(R.id.badgeRoleDetail);
        if (badgeRoleDetail != null) {
            badgeRoleDetail.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerComplexDetailActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void showComplexSwitcherDialog() {
        String[] options = {
                "🌐 Tất cả các sân (Toàn hệ thống · 9 sân)",
                "⚽ Cụm sân Thăng Long (5 sân con)",
                "⚽ Cụm sân Mai Dịch (4 sân con)",
                "⚽ Cụm sân Riverside (Tạm ngưng)"
        };
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Chọn cụm sân quản lý")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        bindComplexData("Tất cả các sân");
                    } else if (which == 1) {
                        bindComplexData("Cụm Sân Thăng Long");
                    } else if (which == 2) {
                        bindComplexData("Cụm Sân Mai Dịch");
                    } else {
                        bindComplexData("Cụm Sân Riverside");
                    }
                    Toast.makeText(this, "Đã chuyển sang: " + currentComplexName, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void bindComplexData(String name) {
        if (name.contains("Tất cả") || name.contains("Toàn hệ thống")) {
            currentComplexName = "Tất cả các sân";
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText("Tất cả các sân");
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText("Tất cả các sân · 3 cơ sở · 9 sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("Hà Nội (Thăng Long, Mai Dịch) & TP.HCM (Riverside)");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 05:30 - 23:30");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("Toàn quốc · 3 cụm sân");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("1900 6868");
            if (tabPitchList != null) tabPitchList.setText("Sân con (9)");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.VISIBLE);
        } else if (name.contains("Mai Dịch")) {
            currentComplexName = "Cụm Sân Mai Dịch";
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText("Cụm sân Mai Dịch");
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText("Cụm sân Mai Dịch · 4 sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("28 Đường số 2, P. Bình An, TP. Hà Nội");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 06:00 - 23:00");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("21.0375° B, 105.7825° Đ");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("0918 654 321");
            if (tabPitchList != null) tabPitchList.setText("Sân con (4)");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.GONE);
        } else if (name.contains("Riverside")) {
            currentComplexName = "Cụm Sân Riverside";
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText("Cụm sân Riverside");
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText("Cụm sân Riverside · 0 sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("86 Nguyễn Văn Hưởng, P. Thảo Điền, TP. Thủ Đức, TP.HCM");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 06:00 - 22:00");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("10.8032° B, 106.7324° Đ");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("0909 888 999");
            if (tabPitchList != null) tabPitchList.setText("Sân con (0)");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.GONE);
        } else {
            // Thăng Long
            currentComplexName = "Cụm Sân Thăng Long";
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText("Cụm sân Thăng long");
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText("Cụm sân Thăng long · 5 sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("152 Hoàng Quốc Việt, Cầu Giấy , Hà Nội");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 05:30 - 23:30");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("10,7291° B, 106,7313° Đ");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("0908 123 456");
            if (tabPitchList != null) tabPitchList.setText("Sân con (5)");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.VISIBLE);
        }
    }

    private void setupTabs() {
        tabOverview.setOnClickListener(v -> selectSubTab(0));
        tabPitchList.setOnClickListener(v -> selectSubTab(1));
        tabPriceTable.setOnClickListener(v -> selectSubTab(2));
    }

    private void selectSubTab(int index) {
        int unselectedColor = ContextCompat.getColor(this, R.color.owner_chip_unselected_text);

        // Reset sub tabs
        tabOverview.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        tabOverview.setTextColor(unselectedColor);
        tabOverview.setTypeface(null, Typeface.NORMAL);

        tabPitchList.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        tabPitchList.setTextColor(unselectedColor);
        tabPitchList.setTypeface(null, Typeface.NORMAL);

        tabPriceTable.setBackgroundResource(R.drawable.bg_owner_chip_unselected);
        tabPriceTable.setTextColor(unselectedColor);
        tabPriceTable.setTypeface(null, Typeface.NORMAL);

        layoutOverviewContainer.setVisibility(View.GONE);
        layoutPitchListContainer.setVisibility(View.GONE);
        layoutPriceTableContainer.setVisibility(View.GONE);

        if (index == 0) {
            tabOverview.setBackgroundResource(R.drawable.bg_owner_chip_selected);
            tabOverview.setTextColor(Color.WHITE);
            tabOverview.setTypeface(null, Typeface.BOLD);
            layoutOverviewContainer.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            tabPitchList.setBackgroundResource(R.drawable.bg_owner_chip_selected);
            tabPitchList.setTextColor(Color.WHITE);
            tabPitchList.setTypeface(null, Typeface.BOLD);
            layoutPitchListContainer.setVisibility(View.VISIBLE);
        } else {
            tabPriceTable.setBackgroundResource(R.drawable.bg_owner_chip_selected);
            tabPriceTable.setTextColor(Color.WHITE);
            tabPriceTable.setTypeface(null, Typeface.BOLD);
            layoutPriceTableContainer.setVisibility(View.VISIBLE);
        }
    }

    private void setupPitchActions() {
        // Nút "+ Thêm sân"
        findViewById(R.id.btnAddSubPitch).setOnClickListener(v -> showAddSubPitchDialog());

        // Nút Bảo trì / Đổi lịch các sân con
        findViewById(R.id.btnChange5A).setOnClickListener(v -> showMaintenanceDialog("Sân 5-A"));
        findViewById(R.id.btnChange5B).setOnClickListener(v -> showMaintenanceDialog("Sân 5-B"));
        findViewById(R.id.btnChange71).setOnClickListener(v -> showMaintenanceDialog("Sân 7-1 (ghép)"));
        findViewById(R.id.btnChange72).setOnClickListener(v -> {
            Toast.makeText(this, "Đã mở lại hoạt động cho Sân 7-2 thành công!", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.btnChange11).setOnClickListener(v -> showMaintenanceDialog("Sân 11"));
    }

    // Modal: Thêm sân con mới (khớp thiết kế ảnh 2)
    private void showAddSubPitchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_sub_pitch, null);
        dialog.setContentView(dialogView);

        EditText edtPitchName = dialogView.findViewById(R.id.edtDialogPitchName);
        EditText edtPitchPrice = dialogView.findViewById(R.id.edtDialogPitchPrice);
        Button btnSavePitch = dialogView.findViewById(R.id.btnDialogSavePitch);
        Button btnCancelPitch = dialogView.findViewById(R.id.btnDialogCancelPitch);

        btnSavePitch.setOnClickListener(v -> {
            String name = edtPitchName.getText().toString().trim();
            if (name.isEmpty()) {
                edtPitchName.setError("Vui lòng nhập tên sân con");
                return;
            }
            Toast.makeText(this, "Thêm " + name + " vào cụm sân thành công!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancelPitch.setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    // Modal: Chuyển sân sang bảo trì (khớp thiết kế ảnh 4)
    private void showMaintenanceDialog(String pitchName) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_maintenance_pitch, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvMaintenanceTitle);
        if (tvTitle != null) {
            tvTitle.setText("Chuyển " + pitchName + " sang bảo trì");
        }

        dialogView.findViewById(R.id.btnCloseMaintenance).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnConfirmMaintenance).setOnClickListener(v -> {
            Toast.makeText(this, "Đã xác nhận đưa " + pitchName + " sang trạng thái bảo trì!", Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.navDetailFacilities).setOnClickListener(v -> finish());
        findViewById(R.id.navDetailSchedule).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerScheduleActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navDetailStaff).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerStaffActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navDetailFinance).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerFinanceActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navDetailReviews).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerReviewActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
    }
}
