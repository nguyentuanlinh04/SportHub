package fpoly.duan1.sporthub_new;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.List;
import java.util.Locale;

import fpoly.duan1.sporthub_new.helper.OwnerComplexManager;
import fpoly.duan1.sporthub_new.model.OwnerComplex;

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

        initImagePickers();
        initViews();
        setupTabs();
        setupPitchActions();
        setupBottomNav();
    }

    private void initImagePickers() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success) {
                        customPhotosCount++;
                        if (tvDetailPhotosTitle != null) {
                            tvDetailPhotosTitle.setText("Hình ảnh (" + customPhotosCount + ")");
                        }
                        Toast.makeText(this, "Đã chụp và tải ảnh cơ sở lên thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        pickGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        customPhotosCount++;
                        if (tvDetailPhotosTitle != null) {
                            tvDetailPhotosTitle.setText("Hình ảnh (" + customPhotosCount + ")");
                        }
                        Toast.makeText(this, "Đã chọn và tải ảnh cơ sở từ máy thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestCameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        launchCamera();
                    } else {
                        Toast.makeText(this, "Cần cấp quyền máy ảnh để chụp hình", Toast.LENGTH_SHORT).show();
                    }
                }
        );
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

        // Nút Ghim lại vị trí bản đồ
        View btnViewMap = findViewById(R.id.btnViewMap);
        if (btnViewMap != null) {
            btnViewMap.setOnClickListener(v -> showPinMapDialog());
        }

        // Nút Xóa cụm sân này
        View btnDeleteCurrentComplex = findViewById(R.id.btnDeleteCurrentComplex);
        if (btnDeleteCurrentComplex != null) {
            btnDeleteCurrentComplex.setOnClickListener(v -> confirmDeleteCurrentComplex());
        }

        // Thêm hình ảnh cơ sở (+)
        tvDetailPhotosTitle = findViewById(R.id.tvDetailPhotosTitle);
        View btnAddDetailPhoto = findViewById(R.id.btnAddDetailPhoto);
        if (btnAddDetailPhoto != null) {
            btnAddDetailPhoto.setOnClickListener(v -> showChooseImageSourceDialog());
        }

        View badgeRoleDetail = findViewById(R.id.badgeRoleDetail);
        if (badgeRoleDetail != null) {
            badgeRoleDetail.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerComplexDetailActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void showChooseImageSourceDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_choose_image_source, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvImageSourceTitle);
        if (tvTitle != null) {
            tvTitle.setText("Thêm hình ảnh cho " + currentComplexName);
        }

        // Chụp ảnh mới
        dialogView.findViewById(R.id.btnOptionCamera).setOnClickListener(v -> {
            dialog.dismiss();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        // Chọn ảnh từ máy
        dialogView.findViewById(R.id.btnOptionGallery).setOnClickListener(v -> {
            dialog.dismiss();
            pickGalleryLauncher.launch("image/*");
        });

        dialogView.findViewById(R.id.btnCancelImageSource).setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void launchCamera() {
        try {
            File cacheDir = new File(getCacheDir(), "camera_cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File imageFile = File.createTempFile("detail_" + System.currentTimeMillis(), ".jpg", cacheDir);
            tempCameraUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
            takePictureLauncher.launch(tempCameraUri);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở máy ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteCurrentComplex() {
        if (currentComplexName.contains("Tất cả")) {
            Toast.makeText(this, "Không thể xóa mục 'Tất cả các sân'. Vui lòng chọn một cụm sân cụ thể để xóa.", Toast.LENGTH_LONG).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa cụm sân")
                .setMessage("Bạn có chắc chắn muốn xóa \"" + currentComplexName + "\" không?\n\nToàn bộ thông tin cơ sở và các sân con thuộc cụm này sẽ bị xóa khỏi hệ thống.")
                .setPositiveButton("Xóa cụm sân", (dialog, which) -> {
                    OwnerComplexManager.getInstance().deleteComplexByName(currentComplexName);
                    Toast.makeText(this, "Đã xóa cụm sân \"" + currentComplexName + "\" thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showComplexSwitcherDialog() {
        List<String> optionsList = OwnerComplexManager.getInstance().getAllComplexOptionsForSwitcher();
        String[] options = optionsList.toArray(new String[0]);
        new AlertDialog.Builder(this)
                .setTitle("Chọn cụm sân quản lý")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        bindComplexData("Tất cả các sân");
                    } else {
                        String selected = options[which];
                        String extracted = selected.replace("⚽ ", "").replaceAll(" \\(.*\\)", "").trim();
                        bindComplexData(extracted);
                    }
                    Toast.makeText(this, "Đã chuyển sang: " + currentComplexName, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void bindComplexData(String name) {
        if (name == null || name.isEmpty()) {
            name = "Cụm Sân Thăng Long";
        }

        OwnerComplex found = OwnerComplexManager.getInstance().getComplexByName(name);
        if (found != null) {
            currentComplexName = found.getName();
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText(found.getName());
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText(found.getName() + " · " + found.getSubPitchesCount() + " sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText(found.getAddress());
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · " + found.getHours());
            if (tvComplexDetailCoordinates != null) {
                tvComplexDetailCoordinates.setText(found.getCoordinates() != null && !found.getCoordinates().isEmpty()
                        ? found.getCoordinates()
                        : "21.0285° B, 105.8542° Đ");
            }
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText(found.getHotline());
            if (tabPitchList != null) tabPitchList.setText("Sân con (" + found.getSubPitchesCount() + ")");
            if (cardPitch11 != null) cardPitch11.setVisibility(found.getSubPitchesCount() >= 5 ? View.VISIBLE : View.GONE);
            return;
        }

        if (name.contains("Tất cả") || name.contains("Toàn hệ thống")) {
            currentComplexName = "Tất cả các sân";
            int total = OwnerComplexManager.getInstance().getTotalCount();
            int pitches = OwnerComplexManager.getInstance().getTotalSubPitches();
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText("Tất cả các sân");
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText("Tất cả các sân · " + total + " cơ sở · " + pitches + " sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("Toàn bộ các cơ sở trong hệ thống");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 05:30 - 23:30");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("Toàn quốc · " + total + " cụm sân");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("1900 6868");
            if (tabPitchList != null) tabPitchList.setText("Sân con (" + pitches + ")");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.VISIBLE);
        } else {
            currentComplexName = name;
            if (tvComplexDetailTitle != null) tvComplexDetailTitle.setText(name);
            if (tvComplexDetailSub != null) tvComplexDetailSub.setText(name + " · 4 sân ▾");
            if (tvComplexDetailAddress != null) tvComplexDetailAddress.setText("Hà Nội, Việt Nam");
            if (tvComplexDetailHours != null) tvComplexDetailHours.setText("Khung giờ mở cửa · 06:00 - 23:00");
            if (tvComplexDetailCoordinates != null) tvComplexDetailCoordinates.setText("21.0285° B, 105.8542° Đ");
            if (tvComplexDetailHotline != null) tvComplexDetailHotline.setText("0908 123 456");
            if (tabPitchList != null) tabPitchList.setText("Sân con (4)");
            if (cardPitch11 != null) cardPitch11.setVisibility(View.GONE);
        }
    }

    private void showPinMapDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pin_map_location, null);
        dialog.setContentView(dialogView);

        EditText edtAddress = dialogView.findViewById(R.id.edtPinAddress);
        EditText edtLat = dialogView.findViewById(R.id.edtPinLatitude);
        EditText edtLng = dialogView.findViewById(R.id.edtPinLongitude);
        TextView tvBadge = dialogView.findViewById(R.id.tvPinBadgeCoordinates);
        View simulatedCanvas = dialogView.findViewById(R.id.layoutSimulatedMapCanvas);

        OwnerComplex complex = OwnerComplexManager.getInstance().getComplexByName(currentComplexName);
        double initialLat = (complex != null && complex.getLatitude() != 0) ? complex.getLatitude() : 21.028511;
        double initialLng = (complex != null && complex.getLongitude() != 0) ? complex.getLongitude() : 105.854167;
        String initialAddress = (complex != null && complex.getAddress() != null)
                ? complex.getAddress()
                : (tvComplexDetailAddress != null ? tvComplexDetailAddress.getText().toString() : "Hà Nội, Việt Nam");

        edtAddress.setText(initialAddress);
        final double[] tempCoords = new double[]{initialLat, initialLng};
        edtLat.setText(String.format(Locale.US, "%.6f", tempCoords[0]));
        edtLng.setText(String.format(Locale.US, "%.6f", tempCoords[1]));
        tvBadge.setText(String.format(Locale.US, "%.4f° B, %.4f° Đ", tempCoords[0], tempCoords[1]));

        // Auto geocode button
        dialogView.findViewById(R.id.btnAutoGeocode).setOnClickListener(v -> {
            String target = edtAddress.getText().toString().trim().toLowerCase();
            if (target.contains("hồ chí minh") || target.contains("hcm") || target.contains("thủ đức") || target.contains("quận 1") || target.contains("quận 7")) {
                tempCoords[0] = 10.776889 + (Math.abs(target.hashCode() % 1000) / 100000.0);
                tempCoords[1] = 106.700806 + (Math.abs(target.hashCode() % 800) / 100000.0);
            } else if (target.contains("đà nẵng")) {
                tempCoords[0] = 16.054407 + (Math.abs(target.hashCode() % 1000) / 100000.0);
                tempCoords[1] = 108.202167 + (Math.abs(target.hashCode() % 800) / 100000.0);
            } else if (target.contains("hải phòng")) {
                tempCoords[0] = 20.844912 + (Math.abs(target.hashCode() % 1000) / 100000.0);
                tempCoords[1] = 106.688084 + (Math.abs(target.hashCode() % 800) / 100000.0);
            } else if (target.contains("cần thơ")) {
                tempCoords[0] = 10.045162 + (Math.abs(target.hashCode() % 1000) / 100000.0);
                tempCoords[1] = 105.746853 + (Math.abs(target.hashCode() % 800) / 100000.0);
            } else {
                tempCoords[0] = 21.028511 + (Math.abs(target.hashCode() % 1000) / 100000.0);
                tempCoords[1] = 105.854167 + (Math.abs(target.hashCode() % 800) / 100000.0);
            }
            edtLat.setText(String.format(Locale.US, "%.6f", tempCoords[0]));
            edtLng.setText(String.format(Locale.US, "%.6f", tempCoords[1]));
            String coordsStr = String.format(Locale.US, "%.4f° B, %.4f° Đ", tempCoords[0], tempCoords[1]);
            tvBadge.setText(coordsStr);
            Toast.makeText(this, "Đã tự động xác định tọa độ từ địa chỉ cơ sở!", Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.btnChange11).setOnClickListener(v -> showMaintenanceDialog("Sân 11"));
    }

    // Modal: Thêm sân con mới
    private void showAddSubPitchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_sub_pitch, null);
        dialog.setContentView(dialogView);

        EditText edtPitchName = dialogView.findViewById(R.id.edtDialogPitchName);
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

    // Modal: Chuyển sân sang bảo trì
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
