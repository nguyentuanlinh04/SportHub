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
    private TextView tvComplexDetailTitle;

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

        String complexName = getIntent().getStringExtra("COMPLEX_NAME");
        if (complexName != null && !complexName.isEmpty()) {
            tvComplexDetailTitle.setText(complexName);
        }

        findViewById(R.id.btnBackDetail).setOnClickListener(v -> finish());
        findViewById(R.id.btnViewMap).setOnClickListener(v -> {
            Toast.makeText(this, "Xem vị trí cơ sở trên Google Maps", Toast.LENGTH_SHORT).show();
        });
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
        findViewById(R.id.btnChange11).setOnClickListener(v -> showMaintenanceDialog("Sân 11 Chuẩn"));
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
            startActivity(new Intent(this, OwnerScheduleActivity.class));
            finish();
        });
        findViewById(R.id.navDetailStaff).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerStaffActivity.class));
            finish();
        });
        findViewById(R.id.navDetailFinance).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerFinanceActivity.class));
            finish();
        });
        findViewById(R.id.navDetailReviews).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerReviewActivity.class));
            finish();
        });
    }
}
