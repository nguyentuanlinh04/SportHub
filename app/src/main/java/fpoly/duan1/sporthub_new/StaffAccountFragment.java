package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import fpoly.duan1.sporthub_new.helper.FacilityDialogHelper;
import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class StaffAccountFragment extends Fragment {

    private TextView tvBadgeFacility;
    private TextView tvFacilityNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_account, container, false);

        tvBadgeFacility = view.findViewById(R.id.tv_badge_facility);
        tvFacilityNames = view.findViewById(R.id.tv_facility_names);

        refreshFacilityUI();

        // Row 1: Chuyển vai trò
        View rowSwitchRole = view.findViewById(R.id.row_switch_role);
        if (rowSwitchRole != null) {
            rowSwitchRole.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Chuyển vai trò")
                        .setMessage("Bạn đang ở vai trò Nhân viên. Bạn có muốn chuyển sang chế độ Người chơi không?")
                        .setPositiveButton("Chuyển", (dialog, which) -> {
                            Toast.makeText(getContext(), "Đã chuyển sang vai trò Người chơi!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        // Row 2: Cụm sân phụ trách
        View rowFacilities = view.findViewById(R.id.row_facilities);
        if (rowFacilities != null) {
            rowFacilities.setOnClickListener(v -> {
                FacilityDialogHelper.show(getContext(), facilityName -> refreshFacilityUI());
            });
        }

        // Row 3: Nhắc lịch làm việc
        View rowReminderToggle = view.findViewById(R.id.row_reminder_toggle);
        SwitchCompat switchReminder = view.findViewById(R.id.switch_reminder);
        if (rowReminderToggle != null && switchReminder != null) {
            rowReminderToggle.setOnClickListener(v -> {
                switchReminder.setChecked(!switchReminder.isChecked());
                Toast.makeText(getContext(), "Nhắc lịch làm việc: " + (switchReminder.isChecked() ? "Bật" : "Tắt"), Toast.LENGTH_SHORT).show();
            });
            switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Toast.makeText(getContext(), "Nhắc lịch làm việc: " + (isChecked ? "Bật" : "Tắt"), Toast.LENGTH_SHORT).show();
            });
        }

        // Row 4: Bảo mật & đăng nhập
        View rowSecurity = view.findViewById(R.id.row_security);
        if (rowSecurity != null) {
            rowSecurity.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            });
        }

        // Row 5: Hỗ trợ & hướng dẫn
        View rowSupport = view.findViewById(R.id.row_support);
        if (rowSupport != null) {
            rowSupport.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Hỗ trợ & Hướng dẫn")
                        .setMessage("Trung tâm trợ giúp SportHub:\n• Hotline hỗ trợ quản trị viên: 1900 6868\n• Email hỗ trợ: support@sporthub.vn\n• Quản lý trực tiếp: Nguyễn Văn Chủ Sân")
                        .setPositiveButton("Đã hiểu", null)
                        .show();
            });
        }

        // Sửa hồ sơ
        MaterialButton btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            });
        }

        // Đăng xuất
        MaterialButton btnLogout = view.findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản nhân viên?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            if (getActivity() != null) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        return view;
    }

    private void refreshFacilityUI() {
        String current = StaffDataStore.getCurrentFacility();
        if (tvBadgeFacility != null) {
            String shortName = current.replace("Cụm sân ", "").replace("Sân bóng ", "");
            tvBadgeFacility.setText(shortName);
        }
        if (tvFacilityNames != null) {
            tvFacilityNames.setText("Thăng Long, Mai Dịch");
        }
    }
}
