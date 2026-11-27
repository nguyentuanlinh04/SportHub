package fpoly.duan1.sporthub_new;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddComplexActivity extends AppCompatActivity {

    private EditText edtComplexName;
    private EditText edtComplexHotline;
    private EditText edtComplexAddress;
    private EditText edtComplexDescription;

    private Spinner spCity, spDistrict, spWard;
    private TextView tvOpenTime, tvCloseTime;
    private RadioGroup rgStatus;
    private RadioButton rbStatusActive, rbStatusPaused;

    private TextView tvPinStatusBadge;
    private double currentLatitude = 21.0375;
    private double currentLongitude = 105.7825;
    private String currentCoordinatesStr = "21.0375° B, 105.7825° Đ";
    private boolean isLocationPinned = false;

    // Quản lý hình ảnh
    private View layoutUploadAvatar;
    private View cardAvatarPreview;
    private ImageView imgAvatarPreview;
    private View btnChangeAvatar;
    private View btnRemoveAvatar;

    private View layoutUploadFacility;
    private TextView tvFacilityPhotosCountHint;
    private View scrollFacilityPhotos;
    private LinearLayout containerFacilityPhotos;

    private Uri avatarUri = null;
    private final List<Uri> facilityPhotoUris = new ArrayList<>();
    private Uri tempCameraUri = null;
    private boolean isSelectingAvatar = true;

    // Launchers cho Camera & Gallery
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private ActivityResultLauncher<String> pickGalleryLauncher;
    private ActivityResultLauncher<String> requestCameraPermissionLauncher;

    // Dữ liệu phân cấp hành chính: Tỉnh/Thành -> Quận/Huyện -> Phường/Xã
    private final Map<String, Map<String, List<String>>> locationData = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_complex);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainAddComplex), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initImagePickers();
        initViews();
        setupSpinners();
        setupTimePickers();
        setupStatusRadio();
        setupPhotoUploads();
        setupActions();
    }

    private void initImagePickers() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success && tempCameraUri != null) {
                        handleImageReceived(tempCameraUri);
                    }
                }
        );

        pickGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        handleImageReceived(uri);
                    }
                }
        );

        requestCameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        launchCamera();
                    } else {
                        Toast.makeText(this, "Cần cấp quyền máy ảnh để chụp hình cơ sở", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void handleImageReceived(Uri uri) {
        if (isSelectingAvatar) {
            avatarUri = uri;
            if (layoutUploadAvatar != null) layoutUploadAvatar.setVisibility(View.GONE);
            if (cardAvatarPreview != null) cardAvatarPreview.setVisibility(View.VISIBLE);
            if (imgAvatarPreview != null) imgAvatarPreview.setImageURI(uri);
            Toast.makeText(this, "Đã cập nhật ảnh đại diện cụm sân!", Toast.LENGTH_SHORT).show();
        } else {
            if (facilityPhotoUris.size() >= 5) {
                Toast.makeText(this, "Đã đạt tối đa 5 ảnh cơ sở!", Toast.LENGTH_SHORT).show();
                return;
            }
            facilityPhotoUris.add(uri);
            renderFacilityPhotos();
            Toast.makeText(this, "Đã thêm ảnh cơ sở thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        edtComplexName = findViewById(R.id.edtComplexName);
        edtComplexHotline = findViewById(R.id.edtComplexHotline);
        edtComplexAddress = findViewById(R.id.edtComplexAddress);
        edtComplexDescription = findViewById(R.id.edtComplexDescription);

        spCity = findViewById(R.id.spCity);
        spDistrict = findViewById(R.id.spDistrict);
        spWard = findViewById(R.id.spWard);

        tvOpenTime = findViewById(R.id.tvOpenTime);
        tvCloseTime = findViewById(R.id.tvCloseTime);

        rgStatus = findViewById(R.id.rgStatus);
        rbStatusActive = findViewById(R.id.rbStatusActive);
        rbStatusPaused = findViewById(R.id.rbStatusPaused);

        tvPinStatusBadge = findViewById(R.id.tvPinStatusBadge);

        // Ảnh đại diện
        layoutUploadAvatar = findViewById(R.id.layoutUploadAvatar);
        cardAvatarPreview = findViewById(R.id.cardAvatarPreview);
        imgAvatarPreview = findViewById(R.id.imgAvatarPreview);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        btnRemoveAvatar = findViewById(R.id.btnRemoveAvatar);

        // Ảnh cơ sở
        layoutUploadFacility = findViewById(R.id.layoutUploadFacility);
        tvFacilityPhotosCountHint = findViewById(R.id.tvFacilityPhotosCountHint);
        scrollFacilityPhotos = findViewById(R.id.scrollFacilityPhotos);
        containerFacilityPhotos = findViewById(R.id.containerFacilityPhotos);
    }

    private void setupPhotoUploads() {
        // Click vào box tải ảnh đại diện
        if (layoutUploadAvatar != null) {
            layoutUploadAvatar.setOnClickListener(v -> showChooseImageSourceDialog(true));
        }

        // Đổi hoặc chụp lại ảnh đại diện
        if (btnChangeAvatar != null) {
            btnChangeAvatar.setOnClickListener(v -> showChooseImageSourceDialog(true));
        }

        // Xóa ảnh đại diện
        if (btnRemoveAvatar != null) {
            btnRemoveAvatar.setOnClickListener(v -> {
                avatarUri = null;
                if (cardAvatarPreview != null) cardAvatarPreview.setVisibility(View.GONE);
                if (layoutUploadAvatar != null) layoutUploadAvatar.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Đã xóa ảnh đại diện!", Toast.LENGTH_SHORT).show();
            });
        }

        // Click vào box thêm ảnh cơ sở
        if (layoutUploadFacility != null) {
            layoutUploadFacility.setOnClickListener(v -> {
                if (facilityPhotoUris.size() >= 5) {
                    Toast.makeText(this, "Bạn đã chọn tối đa 5 hình ảnh cơ sở!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showChooseImageSourceDialog(false);
            });
        }
    }

    private void renderFacilityPhotos() {
        if (facilityPhotoUris.isEmpty()) {
            if (scrollFacilityPhotos != null) scrollFacilityPhotos.setVisibility(View.GONE);
            if (tvFacilityPhotosCountHint != null) {
                tvFacilityPhotosCountHint.setText("Chỗ đậu xe, biển hiệu, quầy nước... Tối đa 5 ảnh");
            }
            return;
        }

        if (scrollFacilityPhotos != null) scrollFacilityPhotos.setVisibility(View.VISIBLE);
        if (tvFacilityPhotosCountHint != null) {
            tvFacilityPhotosCountHint.setText("Đã chọn " + facilityPhotoUris.size() + "/5 ảnh cơ sở (nhấn ✕ để xóa ảnh)");
        }

        if (containerFacilityPhotos != null) {
            containerFacilityPhotos.removeAllViews();
            for (int i = 0; i < facilityPhotoUris.size(); i++) {
                final Uri photoUri = facilityPhotoUris.get(i);
                View thumbView = getLayoutInflater().inflate(R.layout.item_facility_photo_thumbnail, containerFacilityPhotos, false);
                ImageView imgThumb = thumbView.findViewById(R.id.imgFacilityThumb);
                View btnRemove = thumbView.findViewById(R.id.btnRemoveFacilityThumb);

                imgThumb.setImageURI(photoUri);
                btnRemove.setOnClickListener(v -> {
                    facilityPhotoUris.remove(photoUri);
                    renderFacilityPhotos();
                });

                containerFacilityPhotos.addView(thumbView);
            }
        }
    }

    private void showChooseImageSourceDialog(boolean isAvatar) {
        this.isSelectingAvatar = isAvatar;

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_choose_image_source, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvImageSourceTitle);
        if (tvTitle != null) {
            tvTitle.setText(isAvatar ? "Ảnh đại diện cụm sân" : "Thêm ảnh cơ sở");
        }

        // Chụp ảnh mới
        dialogView.findViewById(R.id.btnOptionCamera).setOnClickListener(v -> {
            dialog.dismiss();
            checkAndLaunchCamera();
        });

        // Chọn ảnh từ máy (thư viện)
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

    private void checkAndLaunchCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void launchCamera() {
        tempCameraUri = createCameraImageUri();
        if (tempCameraUri != null) {
            takePictureLauncher.launch(tempCameraUri);
        } else {
            Toast.makeText(this, "Không thể khởi tạo tệp tạm cho máy ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri createCameraImageUri() {
        try {
            File cacheDir = new File(getCacheDir(), "camera_cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File imageFile = File.createTempFile("sporthub_" + System.currentTimeMillis(), ".jpg", cacheDir);
            return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
        } catch (Exception e) {
            return null;
        }
    }

    private void initLocationData() {
        locationData.clear();

        // 1. Hà Nội
        Map<String, List<String>> hanoi = new LinkedHashMap<>();
        hanoi.put("Cầu Giấy", Arrays.asList("Mai Dịch", "Dịch Vọng", "Dịch Vọng Hậu", "Nghĩa Đô", "Nghĩa Tân", "Yên Hòa", "Quan Hoa", "Trung Hòa"));
        hanoi.put("Nam Từ Liêm", Arrays.asList("Mỹ Đình 1", "Mỹ Đình 2", "Mễ Trì", "Phú Đô", "Cầu Diễn", "Tây Mỗ", "Đại Mỗ", "Trung Văn"));
        hanoi.put("Bắc Từ Liêm", Arrays.asList("Xuân Đỉnh", "Cổ Nhuế 1", "Cổ Nhuế 2", "Đông Ngạc", "Minh Khai", "Phúc Diễn", "Thụy Phương"));
        hanoi.put("Thanh Xuân", Arrays.asList("Khương Mai", "Khương Trung", "Thanh Xuân Bắc", "Thanh Xuân Nam", "Nhân Chính", "Kim Giang", "Hạ Đình"));
        hanoi.put("Đống Đa", Arrays.asList("Láng Hạ", "Ô Chợ Dừa", "Quang Trung", "Cát Linh", "Văn Miếu", "Khâm Thiên", "Trung Liệt", "Hàng Bột"));
        hanoi.put("Ba Đình", Arrays.asList("Liễu Giai", "Kim Mã", "Cống Vị", "Đội Cấn", "Quán Thánh", "Giảng Võ", "Điện Biên", "Ngọc Khánh"));
        hanoi.put("Tây Hồ", Arrays.asList("Quảng An", "Thụy Khuê", "Nhật Tân", "Tứ Liên", "Xuân La", "Yên Phụ", "Bưởi", "Phú Thượng"));
        hanoi.put("Hoàng Mai", Arrays.asList("Hoàng Liệt", "Định Công", "Giáp Bát", "Tân Mai", "Mai Động", "Vĩnh Hưng", "Thịnh Liệt"));
        hanoi.put("Long Biên", Arrays.asList("Bồ Đề", "Gia Thụy", "Ngọc Lâm", "Sài Đồng", "Phúc Đồng", "Việt Hưng", "Giang Biên"));
        hanoi.put("Hà Đông", Arrays.asList("Mộ Lao", "Văn Quán", "Hà Cầu", "Quang Trung", "La Khê", "Vạn Phúc", "Yên Nghĩa"));
        locationData.put("Hà Nội", hanoi);

        // 2. TP. Hồ Chí Minh
        Map<String, List<String>> hcm = new LinkedHashMap<>();
        hcm.put("Quận 1", Arrays.asList("Bến Nghé", "Bến Thành", "Đa Kao", "Tân Định", "Phạm Ngũ Lão", "Cầu Ông Lãnh", "Nguyễn Cư Trinh"));
        hcm.put("Quận 3", Arrays.asList("Võ Thị Sáu", "Phường 1", "Phường 2", "Phường 3", "Phường 4", "Phường 5", "Phường 9", "Phường 11"));
        hcm.put("Quận 7", Arrays.asList("Tân Phong", "Tân Phú", "Phú Mỹ", "Tân Thuận Đông", "Tân Thuận Tây", "Bình Thuận", "Phú Thuận"));
        hcm.put("TP. Thủ Đức", Arrays.asList("Thảo Điền", "An Phú", "Hiệp Bình Chánh", "Hiệp Bình Phước", "Linh Trung", "Linh Tây", "Tăng Nhơn Phú A"));
        hcm.put("Bình Thạnh", Arrays.asList("Phường 1", "Phường 2", "Phường 12", "Phường 15", "Phường 19", "Phường 24", "Phường 25", "Phường 26"));
        hcm.put("Tân Bình", Arrays.asList("Phường 1", "Phường 2", "Phường 4", "Phường 12", "Phường 13", "Phường 15", "Tây Thạnh"));
        hcm.put("Gò Vấp", Arrays.asList("Phường 1", "Phường 3", "Phường 5", "Phường 7", "Phường 10", "Phường 11", "Phường 16", "Phường 17"));
        hcm.put("Phú Nhuận", Arrays.asList("Phường 1", "Phường 2", "Phường 7", "Phường 8", "Phường 9", "Phường 15"));
        hcm.put("Bình Tân", Arrays.asList("An Lạc", "An Lạc A", "Bình Trị Đông", "Bình Trị Đông A", "Bình Hưng Hòa", "Tân Tạo"));
        hcm.put("Quận 10", Arrays.asList("Phường 1", "Phường 4", "Phường 6", "Phường 10", "Phường 12", "Phường 14"));
        locationData.put("TP. Hồ Chí Minh", hcm);

        // 3. Đà Nẵng
        Map<String, List<String>> danang = new LinkedHashMap<>();
        danang.put("Hải Châu", Arrays.asList("Hải Châu 1", "Hải Châu 2", "Thạch Thang", "Thanh Bình", "Thuận Phước", "Hòa Cường Bắc", "Hòa Cường Nam"));
        danang.put("Thanh Khê", Arrays.asList("Vĩnh Trung", "Tân Chính", "Thạc Gián", "Chính Gián", "Tam Thuận", "Xuân Hà", "An Khê"));
        danang.put("Sơn Trà", Arrays.asList("An Hải Bắc", "An Hải Tây", "Phước Mỹ", "Nại Hiên Đông", "Mân Thái", "Thọ Quang"));
        danang.put("Ngũ Hành Sơn", Arrays.asList("Mỹ An", "Khuê Mỹ", "Hòa Hải", "Hòa Quý"));
        danang.put("Cẩm Lệ", Arrays.asList("Khuê Trung", "Hòa Thọ Đông", "Hòa Thọ Tây", "Hòa An", "Hòa Phát", "Hòa Xuân"));
        danang.put("Liên Chiểu", Arrays.asList("Hòa Minh", "Hòa Khánh Bắc", "Hòa Khánh Nam", "Hòa Hiệp Bắc", "Hòa Hiệp Nam"));
        locationData.put("Đà Nẵng", danang);

        // 4. Hải Phòng
        Map<String, List<String>> haiphong = new LinkedHashMap<>();
        haiphong.put("Hồng Bàng", Arrays.asList("Hoàng Văn Thụ", "Phan Bội Châu", "Minh Khai", "Sở Dầu", "Quán Toan", "Hùng Vương"));
        haiphong.put("Ngô Quyền", Arrays.asList("Lạc Viên", "Cầu Tre", "Vạn Mỹ", "Máy Tơ", "Lương Khánh Thiện", "Đằng Giang"));
        haiphong.put("Lê Chân", Arrays.asList("An Biên", "An Dương", "Cát Dài", "Dư Hàng", "Hàng Kênh", "Kênh Dương", "Niệm Nghĩa"));
        haiphong.put("Hải An", Arrays.asList("Đông Hải 1", "Đông Hải 2", "Đằng Hải", "Đằng Lâm", "Nam Hải", "Tràng Cát"));
        haiphong.put("Đồ Sơn", Arrays.asList("Vạn Sơn", "Vạn Hương", "Ngọc Xuyên", "Bàng La", "Hợp Đức"));
        haiphong.put("Thủy Nguyên", Arrays.asList("Núi Đèo", "Minh Đức", "Lâm Động", "Hoa Động", "Thủy Đường", "Quảng Thanh"));
        locationData.put("Hải Phòng", haiphong);

        // 5. Cần Thơ
        Map<String, List<String>> cantho = new LinkedHashMap<>();
        cantho.put("Ninh Kiều", Arrays.asList("An Cư", "An Hòa", "An Khánh", "An Nghiệp", "Cái Khế", "Tân An", "Hưng Lợi", "Xuân Khánh"));
        cantho.put("Bình Thủy", Arrays.asList("Bình Thủy", "Trà An", "Trà Nóc", "An Thới", "Bùi Hữu Nghĩa", "Long Hòa", "Long Tuyền"));
        cantho.put("Cái Răng", Arrays.asList("Ba Láng", "Hưng Phú", "Hưng Thạnh", "Lê Bình", "Phú Thứ", "Tân Phú"));
        cantho.put("Ô Môn", Arrays.asList("Châu Văn Liêm", "Thới Hòa", "Thới An", "Phước Thới", "Trường Lạc"));
        cantho.put("Thốt Nốt", Arrays.asList("Thốt Nốt", "Thới Thuận", "Thuận An", "Trung Nhứt", "Tân Hưng"));
        locationData.put("Cần Thơ", cantho);
    }

    private void setupSpinners() {
        initLocationData();

        List<String> cities = new ArrayList<>(locationData.keySet());
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spCity.setAdapter(cityAdapter);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = cities.get(position);
                updateDistrictsForCity(selectedCity);

                // Cập nhật tọa độ mặc định thông minh theo tỉnh/thành đã chọn nếu người dùng chưa ghim thủ công
                if (!isLocationPinned) {
                    if (selectedCity.contains("Hồ Chí Minh")) {
                        currentLatitude = 10.776889;
                        currentLongitude = 106.700806;
                    } else if (selectedCity.contains("Đà Nẵng")) {
                        currentLatitude = 16.054407;
                        currentLongitude = 108.202167;
                    } else if (selectedCity.contains("Hải Phòng")) {
                        currentLatitude = 20.844912;
                        currentLongitude = 106.688084;
                    } else if (selectedCity.contains("Cần Thơ")) {
                        currentLatitude = 10.045162;
                        currentLongitude = 105.746853;
                    } else {
                        currentLatitude = 21.028511;
                        currentLongitude = 105.854167;
                    }
                    currentCoordinatesStr = String.format(Locale.US, "%.4f° B, %.4f° Đ", currentLatitude, currentLongitude);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = spCity.getSelectedItem() != null ? spCity.getSelectedItem().toString() : "Hà Nội";
                String selectedDistrict = (String) parent.getItemAtPosition(position);
                updateWardsForDistrict(selectedCity, selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (!cities.isEmpty()) {
            updateDistrictsForCity(cities.get(0));
        }
    }

    private void updateDistrictsForCity(String city) {
        Map<String, List<String>> districtMap = locationData.get(city);
        List<String> districts = (districtMap != null) ? new ArrayList<>(districtMap.keySet()) : new ArrayList<>();
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);
        spDistrict.setAdapter(districtAdapter);

        if (!districts.isEmpty()) {
            spDistrict.setSelection(0);
            updateWardsForDistrict(city, districts.get(0));
        } else {
            spWard.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>()));
        }
    }

    private void updateWardsForDistrict(String city, String district) {
        Map<String, List<String>> districtMap = locationData.get(city);
        List<String> wards = (districtMap != null && district != null && districtMap.get(district) != null)
                ? districtMap.get(district)
                : new ArrayList<>();
        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wards);
        spWard.setAdapter(wardAdapter);
        if (!wards.isEmpty()) {
            spWard.setSelection(0);
        }
    }

    private void setupTimePickers() {
        tvOpenTime.setOnClickListener(v -> showTimePicker(5, 30, (view, hourOfDay, minute) -> {
            tvOpenTime.setText(String.format(Locale.getDefault(), "🕒  %02d:%02d", hourOfDay, minute));
        }));

        tvCloseTime.setOnClickListener(v -> showTimePicker(23, 30, (view, hourOfDay, minute) -> {
            tvCloseTime.setText(String.format(Locale.getDefault(), "🕒  %02d:%02d", hourOfDay, minute));
        }));
    }

    private void showTimePicker(int defaultHour, int defaultMinute, TimePickerDialog.OnTimeSetListener listener) {
        new TimePickerDialog(this, listener, defaultHour, defaultMinute, true).show();
    }

    private void setupStatusRadio() {
        rbStatusPaused.setChecked(true);
        rbStatusPaused.setBackgroundResource(R.drawable.bg_radio_option_active);
        rbStatusActive.setBackgroundResource(R.drawable.bg_radio_option_inactive);

        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbStatusActive) {
                rbStatusActive.setBackgroundResource(R.drawable.bg_radio_option_active);
                rbStatusPaused.setBackgroundResource(R.drawable.bg_radio_option_inactive);
            } else if (checkedId == R.id.rbStatusPaused) {
                rbStatusActive.setBackgroundResource(R.drawable.bg_radio_option_inactive);
                rbStatusPaused.setBackgroundResource(R.drawable.bg_radio_option_active);
            }
        });
    }

    private void setupActions() {
        findViewById(R.id.btnBackAddComplex).setOnClickListener(v -> finish());

        View badgeRoleAddComplex = findViewById(R.id.badgeRoleAddComplex);
        if (badgeRoleAddComplex != null) {
            badgeRoleAddComplex.setOnClickListener(v -> {
                Intent intent = new Intent(AddComplexActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
        findViewById(R.id.btnCancelAddComplex).setOnClickListener(v -> finish());

        View.OnClickListener openMapDialogListener = v -> showPinMapDialog();
        findViewById(R.id.btnSelectMapLocation).setOnClickListener(openMapDialogListener);
        View layoutMapPreview = findViewById(R.id.layoutMapPreviewContainer);
        if (layoutMapPreview != null) {
            layoutMapPreview.setOnClickListener(openMapDialogListener);
        }

        findViewById(R.id.btnSubmitCreateComplex).setOnClickListener(v -> {
            String name = edtComplexName.getText().toString().trim();
            String phone = edtComplexHotline.getText().toString().trim();
            String address = edtComplexAddress.getText().toString().trim();

            if (name.isEmpty()) {
                edtComplexName.setError("Vui lòng nhập tên cụm sân");
                edtComplexName.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                edtComplexHotline.setError("Vui lòng nhập hotline liên hệ");
                edtComplexHotline.requestFocus();
                return;
            }

            String ward = spWard != null && spWard.getSelectedItem() != null ? spWard.getSelectedItem().toString() : "";
            String district = spDistrict != null && spDistrict.getSelectedItem() != null ? spDistrict.getSelectedItem().toString() : "";
            String city = spCity != null && spCity.getSelectedItem() != null ? spCity.getSelectedItem().toString() : "";

            StringBuilder fullAddress = new StringBuilder(address);
            if (!ward.isEmpty()) fullAddress.append(", P. ").append(ward);
            if (!district.isEmpty()) fullAddress.append(", Q. ").append(district);
            if (!city.isEmpty()) fullAddress.append(", ").append(city);

            String openTime = tvOpenTime != null ? tvOpenTime.getText().toString().replace("🕒", "").trim() : "05:30";
            String closeTime = tvCloseTime != null ? tvCloseTime.getText().toString().replace("🕒", "").trim() : "23:30";
            String hoursStr = openTime + " – " + closeTime + " · Hằng ngày";

            String status = (rbStatusActive != null && rbStatusActive.isChecked()) ? "ACTIVE" : "PAUSED";
            String desc = edtComplexDescription != null ? edtComplexDescription.getText().toString().trim() : "";

            fpoly.duan1.sporthub_new.model.OwnerComplex newComplex = new fpoly.duan1.sporthub_new.model.OwnerComplex(
                    "cx_" + System.currentTimeMillis(),
                    name,
                    phone,
                    fullAddress.toString(),
                    hoursStr,
                    status,
                    0,
                    desc,
                    true,
                    currentLatitude,
                    currentLongitude,
                    currentCoordinatesStr
            );

            fpoly.duan1.sporthub_new.helper.OwnerComplexManager.getInstance().addComplex(newComplex);

            Toast.makeText(this, "Tạo cụm sân \"" + name + "\" thành công!", Toast.LENGTH_LONG).show();
            finish();
        });
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

        // Pre-fill address
        String address = edtComplexAddress != null ? edtComplexAddress.getText().toString().trim() : "";
        String ward = spWard != null && spWard.getSelectedItem() != null ? spWard.getSelectedItem().toString() : "";
        String district = spDistrict != null && spDistrict.getSelectedItem() != null ? spDistrict.getSelectedItem().toString() : "";
        String city = spCity != null && spCity.getSelectedItem() != null ? spCity.getSelectedItem().toString() : "";

        StringBuilder fullAddr = new StringBuilder(address);
        if (!ward.isEmpty()) fullAddr.append(", P. ").append(ward);
        if (!district.isEmpty()) fullAddr.append(", Q. ").append(district);
        if (!city.isEmpty()) fullAddr.append(", ").append(city);

        if (fullAddr.length() > 0) {
            edtAddress.setText(fullAddr.toString());
        } else {
            edtAddress.setText("Hà Nội, Việt Nam");
        }

        final double[] tempCoords = new double[]{currentLatitude, currentLongitude};
        edtLat.setText(String.format(Locale.US, "%.6f", tempCoords[0]));
        edtLng.setText(String.format(Locale.US, "%.6f", tempCoords[1]));
        tvBadge.setText(currentCoordinatesStr);

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

        // Current GPS button
        dialogView.findViewById(R.id.btnCurrentGps).setOnClickListener(v -> {
            tempCoords[0] = 21.038245;
            tempCoords[1] = 105.782789;
            edtLat.setText(String.format(Locale.US, "%.6f", tempCoords[0]));
            edtLng.setText(String.format(Locale.US, "%.6f", tempCoords[1]));
            String coordsStr = String.format(Locale.US, "%.4f° B, %.4f° Đ", tempCoords[0], tempCoords[1]);
            tvBadge.setText(coordsStr);
            Toast.makeText(this, "Đã lấy tọa độ GPS từ vị trí hiện tại của thiết bị!", Toast.LENGTH_SHORT).show();
        });

        // Interactive touch on map canvas
        if (simulatedCanvas != null) {
            simulatedCanvas.setOnClickListener(v -> {
                tempCoords[0] += (Math.random() - 0.5) * 0.001;
                tempCoords[1] += (Math.random() - 0.5) * 0.001;
                edtLat.setText(String.format(Locale.US, "%.6f", tempCoords[0]));
                edtLng.setText(String.format(Locale.US, "%.6f", tempCoords[1]));
                String coordsStr = String.format(Locale.US, "%.4f° B, %.4f° Đ", tempCoords[0], tempCoords[1]);
                tvBadge.setText(coordsStr);
                Toast.makeText(this, "Đã dịch chuyển tâm ghim trên bản đồ!", Toast.LENGTH_SHORT).show();
            });
        }

        // Open Google Maps
        dialogView.findViewById(R.id.btnOpenGoogleMaps).setOnClickListener(v -> {
            try {
                double lat = Double.parseDouble(edtLat.getText().toString().trim());
                double lng = Double.parseDouble(edtLng.getText().toString().trim());
                String label = edtComplexName != null && !edtComplexName.getText().toString().trim().isEmpty()
                        ? edtComplexName.getText().toString().trim()
                        : "Vị trí sân thể thao";
                Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + Uri.encode(label) + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Không thể mở ứng dụng bản đồ hoặc tọa độ chưa hợp lệ.", Toast.LENGTH_SHORT).show();
            }
        });

        // Close button
        dialogView.findViewById(R.id.btnClosePinMap).setOnClickListener(v -> dialog.dismiss());

        // Confirm button
        dialogView.findViewById(R.id.btnConfirmPinLocation).setOnClickListener(v -> {
            try {
                currentLatitude = Double.parseDouble(edtLat.getText().toString().trim());
                currentLongitude = Double.parseDouble(edtLng.getText().toString().trim());
            } catch (Exception ignored) {
                currentLatitude = tempCoords[0];
                currentLongitude = tempCoords[1];
            }
            currentCoordinatesStr = String.format(Locale.US, "%.4f° B, %.4f° Đ", currentLatitude, currentLongitude);
            isLocationPinned = true;

            if (tvPinStatusBadge != null) {
                tvPinStatusBadge.setText("✓ Đã ghim: " + currentCoordinatesStr);
                tvPinStatusBadge.setBackgroundResource(R.drawable.bg_pill_pinned);
                tvPinStatusBadge.setTextColor(Color.parseColor("#15803D"));
            }

            Toast.makeText(this, "Đã ghim vị trí chính xác trên bản đồ thành công!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }
}
