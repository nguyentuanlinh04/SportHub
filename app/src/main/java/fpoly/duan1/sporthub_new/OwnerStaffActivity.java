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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerStaffActivity extends AppCompatActivity {

    private String currentComplexName = "Cụm Sân Thăng Long";
    private boolean isHungLocked = false;
    private boolean isThaoLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_staff);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerStaff), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupStaffActions();
        setupBottomNav();
    }

    private void initViews() {
        String passedComplex = getIntent().getStringExtra("COMPLEX_NAME");
        if (passedComplex != null && !passedComplex.isEmpty()) {
            currentComplexName = passedComplex;
        }

        updateStaffHeaderSubtitle();

        TextView tvSub = findViewById(R.id.tvStaffHeaderSubtitle);
        if (tvSub != null) {
            tvSub.setOnClickListener(v -> showComplexSwitcherDialog());
        }

        View btnBack = findViewById(R.id.btnBackStaff);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View badgeRoleStaff = findViewById(R.id.badgeRoleStaff);
        if (badgeRoleStaff != null) {
            badgeRoleStaff.setOnClickListener(v -> {
                Intent intent = new Intent(OwnerStaffActivity.this, OwnerProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    private void updateStaffHeaderSubtitle() {
        TextView tvSub = findViewById(R.id.tvStaffHeaderSubtitle);
        if (tvSub != null) {
            if (currentComplexName.contains("Tất cả") || currentComplexName.contains("Toàn hệ thống")) {
                tvSub.setText("Tất cả các sân · Toàn hệ thống (3 cơ sở) ▾");
            } else if (currentComplexName.contains("Mai Dịch")) {
                tvSub.setText("Cụm sân Mai Dịch · 4 sân ▾");
            } else if (currentComplexName.contains("Riverside")) {
                tvSub.setText("Cụm sân Riverside · 0 sân ▾");
            } else {
                tvSub.setText("Cụm sân Thăng Long · 5 sân ▾");
            }
        }
    }

    private void showComplexSwitcherDialog() {
        java.util.List<String> optionsList = fpoly.duan1.sporthub_new.helper.OwnerComplexManager.getInstance().getAllComplexOptionsForSwitcher();
        String[] options = optionsList.toArray(new String[0]);
        new AlertDialog.Builder(this)
                .setTitle("Chọn cụm sân xem nhân sự")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        currentComplexName = "Tất cả các sân";
                    } else {
                        String selected = options[which];
                        currentComplexName = selected.replace("⚽ ", "").replaceAll(" \\(.*\\)", "").trim();
                    }
                    updateStaffHeaderSubtitle();
                    Toast.makeText(this, "Đã chuyển sang nhân sự: " + currentComplexName, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void setupStaffActions() {
        View btnAdd = findViewById(R.id.btnAddStaff);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> showAddStaffDialog());
        }

        // --- NHÂN VIÊN: NGUYỄN VĂN HÙNG ---
        View btnPermHung = findViewById(R.id.btnPermHung);
        TextView tvRoleHung = findViewById(R.id.tvRoleHung);
        TextView tvRevenueHung = findViewById(R.id.tvRevenueHung);
        if (btnPermHung != null) {
            btnPermHung.setOnClickListener(v ->
                    showPermissionDialog("Nguyễn Văn Hùng", tvRoleHung != null ? tvRoleHung.getText().toString() : "Quản lý ca tối", tvRevenueHung)
            );
        }

        TextView btnLockHung = findViewById(R.id.btnLockHung);
        TextView tvStatusHung = findViewById(R.id.tvStatusHung);
        if (btnLockHung != null) {
            btnLockHung.setOnClickListener(v -> {
                isHungLocked = !isHungLocked;
                if (isHungLocked) {
                    if (tvStatusHung != null) {
                        tvStatusHung.setText("Đã khóa");
                        tvStatusHung.setBackgroundResource(R.drawable.bg_owner_status_paused);
                        tvStatusHung.setTextColor(Color.parseColor("#991B1B"));
                    }
                    btnLockHung.setText("Mở khóa");
                    Toast.makeText(this, "Đã khóa tài khoản nhân viên Nguyễn Văn Hùng!", Toast.LENGTH_SHORT).show();
                } else {
                    if (tvStatusHung != null) {
                        tvStatusHung.setText("Hoạt động");
                        tvStatusHung.setBackgroundResource(R.drawable.bg_owner_status_active);
                        tvStatusHung.setTextColor(Color.parseColor("#166534"));
                    }
                    btnLockHung.setText("Khóa");
                    Toast.makeText(this, "Đã mở khóa tài khoản nhân viên Nguyễn Văn Hùng!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        View btnDeleteHung = findViewById(R.id.btnDeleteHung);
        if (btnDeleteHung != null) {
            btnDeleteHung.setOnClickListener(v -> confirmDeleteStaff("Nguyễn Văn Hùng", findViewById(R.id.cardStaffHung)));
        }

        View btnChangePassHung = findViewById(R.id.btnChangePassHung);
        if (btnChangePassHung != null) {
            btnChangePassHung.setOnClickListener(v -> showChangePasswordDialog("Nguyễn Văn Hùng", "0918 234 567"));
        }

        // --- NHÂN VIÊN: TRẦN THỊ THU THẢO ---
        View btnPermThao = findViewById(R.id.btnPermThao);
        TextView tvRoleThao = findViewById(R.id.tvRoleThao);
        TextView tvRevenueThao = findViewById(R.id.tvRevenueThao);
        if (btnPermThao != null) {
            btnPermThao.setOnClickListener(v ->
                    showPermissionDialog("Trần Thị Thu Thảo", tvRoleThao != null ? tvRoleThao.getText().toString() : "Thu ngân, tiếp đón", tvRevenueThao)
            );
        }

        TextView btnLockThao = findViewById(R.id.btnLockThao);
        TextView tvStatusThao = findViewById(R.id.tvStatusThao);
        if (btnLockThao != null) {
            btnLockThao.setOnClickListener(v -> {
                isThaoLocked = !isThaoLocked;
                if (isThaoLocked) {
                    if (tvStatusThao != null) {
                        tvStatusThao.setText("Đã khóa");
                        tvStatusThao.setBackgroundResource(R.drawable.bg_owner_status_paused);
                        tvStatusThao.setTextColor(Color.parseColor("#991B1B"));
                    }
                    btnLockThao.setText("Mở khóa");
                    Toast.makeText(this, "Đã khóa tài khoản nhân viên Trần Thị Thu Thảo!", Toast.LENGTH_SHORT).show();
                } else {
                    if (tvStatusThao != null) {
                        tvStatusThao.setText("Hoạt động");
                        tvStatusThao.setBackgroundResource(R.drawable.bg_owner_status_active);
                        tvStatusThao.setTextColor(Color.parseColor("#166534"));
                    }
                    btnLockThao.setText("Khóa");
                    Toast.makeText(this, "Đã mở khóa tài khoản nhân viên Trần Thị Thu Thảo!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        View btnDeleteThao = findViewById(R.id.btnDeleteThao);
        if (btnDeleteThao != null) {
            btnDeleteThao.setOnClickListener(v -> confirmDeleteStaff("Trần Thị Thu Thảo", findViewById(R.id.cardStaffThao)));
        }

        View btnChangePassThao = findViewById(R.id.btnChangePassThao);
        if (btnChangePassThao != null) {
            btnChangePassThao.setOnClickListener(v -> showChangePasswordDialog("Trần Thị Thu Thảo", "0978 654 321"));
        }
    }

    private void confirmDeleteStaff(String staffName, View cardView) {
        new AlertDialog.Builder(this)
                .setTitle("Thu hồi tài khoản")
                .setMessage("Bạn có chắc chắn muốn thu hồi tài khoản của \"" + staffName + "\" không?\n\nNhân viên sẽ mất toàn bộ quyền truy cập vào hệ thống.")
                .setPositiveButton("Thu hồi ngay", (dialog, which) -> {
                    if (cardView != null) {
                        cardView.setVisibility(View.GONE);
                    }
                    Toast.makeText(this, "Đã thu hồi tài khoản \"" + staffName + "\" thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showChangePasswordDialog(String staffName, String phone) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_staff_password, null);
        dialog.setContentView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tvDialogChangePassTitle);
        TextView tvSub = dialogView.findViewById(R.id.tvDialogChangePassSubtitle);
        if (tvTitle != null) {
            tvTitle.setText("Đổi mật khẩu: " + staffName);
        }
        if (tvSub != null && phone != null && !phone.isEmpty()) {
            tvSub.setText("Tài khoản: " + phone + " · Nhập mật khẩu mới");
        }

        EditText edtNewPass = dialogView.findViewById(R.id.edtNewStaffPassword);
        EditText edtConfirmPass = dialogView.findViewById(R.id.edtConfirmStaffPassword);

        dialogView.findViewById(R.id.btnCloseChangePass).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitChangePass).setOnClickListener(v -> {
            String newPass = edtNewPass != null ? edtNewPass.getText().toString().trim() : "";
            String confirmPass = edtConfirmPass != null ? edtConfirmPass.getText().toString().trim() : "";

            if (newPass.isEmpty()) {
                if (edtNewPass != null) edtNewPass.setError("Vui lòng nhập mật khẩu mới");
                return;
            }
            if (newPass.length() < 6) {
                if (edtNewPass != null) edtNewPass.setError("Mật khẩu phải có ít nhất 6 ký tự");
                return;
            }
            if (!newPass.equals(confirmPass)) {
                if (edtConfirmPass != null) edtConfirmPass.setError("Mật khẩu xác nhận không trùng khớp");
                return;
            }

            Toast.makeText(this, "Đã đổi mật khẩu thành công cho nhân viên " + staffName + "!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void showAddStaffDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_staff, null);
        dialog.setContentView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtAddStaffName);
        EditText edtPhone = dialogView.findViewById(R.id.edtAddStaffPhone);
        TextView tvBranch = dialogView.findViewById(R.id.tvAddStaffBranch);
        TextView tvRole = dialogView.findViewById(R.id.tvAddStaffRole);

        if (tvBranch != null) {
            View layoutBranch = dialogView.findViewById(R.id.layoutAddStaffBranch);
            if (layoutBranch != null) {
                layoutBranch.setOnClickListener(v -> {
                    String[] branches = {"Cụm sân Thăng Long", "Cụm sân Mai Dịch", "Cụm sân Riverside", "Toàn hệ thống"};
                    new AlertDialog.Builder(this)
                            .setTitle("Chọn nhánh hoạt động")
                            .setItems(branches, (d, which) -> tvBranch.setText(branches[which]))
                            .show();
                });
            }
        }

        if (tvRole != null) {
            View layoutRole = dialogView.findViewById(R.id.layoutAddStaffRole);
            if (layoutRole != null) {
                layoutRole.setOnClickListener(v -> {
                    String[] roles = {"Quản lý ca tối", "Thu ngân, tiếp đón", "Quản lý ca sáng", "Kỹ thuật sân"};
                    new AlertDialog.Builder(this)
                            .setTitle("Chọn vai trò")
                            .setItems(roles, (d, which) -> tvRole.setText(roles[which]))
                            .show();
                });
            }
        }

        dialogView.findViewById(R.id.btnCloseAddStaff).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSubmitAddStaff).setOnClickListener(v -> {
            String name = edtName != null ? edtName.getText().toString().trim() : "";
            if (name.isEmpty()) name = "Lê Hoàng Nam";
            String phone = edtPhone != null ? edtPhone.getText().toString().trim() : "";
            if (phone.isEmpty()) phone = "0933 555 789";
            String roleText = tvRole != null ? tvRole.getText().toString() : "Quản lý ca sáng";

            addNewStaffCard(name, phone, roleText);

            Toast.makeText(this, "Đã thêm và cấp quyền cho " + name + " thành công!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        dialog.show();
    }

    private void addNewStaffCard(String name, String phone, String role) {
        android.widget.LinearLayout container = findViewById(R.id.layoutStaffListContainer);
        if (container == null) return;

        View newCard = getLayoutInflater().inflate(R.layout.item_staff_card, container, false);

        TextView tvAvatar = newCard.findViewById(R.id.tvStaffAvatar);
        TextView tvName = newCard.findViewById(R.id.tvStaffName);
        TextView tvRole = newCard.findViewById(R.id.tvStaffRole);
        TextView tvStatus = newCard.findViewById(R.id.tvStaffStatus);
        TextView tvPhone = newCard.findViewById(R.id.tvStaffPhone);
        TextView tvRevenue = newCard.findViewById(R.id.tvStaffRevenueStatus);

        TextView btnPerm = newCard.findViewById(R.id.btnStaffPerm);
        TextView btnLock = newCard.findViewById(R.id.btnStaffLock);
        TextView btnDelete = newCard.findViewById(R.id.btnStaffDelete);
        TextView btnChangePass = newCard.findViewById(R.id.btnStaffChangePass);

        if (tvName != null) tvName.setText(name);
        if (tvRole != null) tvRole.setText(role);
        if (tvPhone != null) tvPhone.setText(phone);
        if (tvAvatar != null) {
            String[] parts = name.trim().split("\\s+");
            String initials = parts.length > 1 ? ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)) : name.substring(0, Math.min(2, name.length()));
            tvAvatar.setText(initials.toUpperCase());
        }

        final boolean[] isLocked = {false};
        if (btnLock != null) {
            btnLock.setOnClickListener(v -> {
                isLocked[0] = !isLocked[0];
                if (isLocked[0]) {
                    if (tvStatus != null) {
                        tvStatus.setText("Đã khóa");
                        tvStatus.setBackgroundResource(R.drawable.bg_owner_status_paused);
                        tvStatus.setTextColor(Color.parseColor("#991B1B"));
                    }
                    btnLock.setText("Mở khóa");
                    Toast.makeText(this, "Đã khóa tài khoản nhân viên " + name + "!", Toast.LENGTH_SHORT).show();
                } else {
                    if (tvStatus != null) {
                        tvStatus.setText("Hoạt động");
                        tvStatus.setBackgroundResource(R.drawable.bg_owner_status_active);
                        tvStatus.setTextColor(Color.parseColor("#166534"));
                    }
                    btnLock.setText("Khóa");
                    Toast.makeText(this, "Đã mở khóa tài khoản nhân viên " + name + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnDelete != null) {
            btnDelete.setOnClickListener(v -> confirmDeleteStaff(name, newCard));
        }

        if (btnPerm != null) {
            btnPerm.setOnClickListener(v -> showPermissionDialog(name, role, tvRevenue));
        }

        if (btnChangePass != null) {
            btnChangePass.setOnClickListener(v -> showChangePasswordDialog(name, phone));
        }

        container.addView(newCard);
    }

    private void showPermissionDialog(String staffName, String role, TextView tvRevenueStatus) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_permission_staff, null);
        dialog.setContentView(dialogView);

        TextView tvName = dialogView.findViewById(R.id.tvStaffPermName);
        if (tvName != null) {
            tvName.setText("Phân quyền: " + staffName);
        }
        TextView tvRole = dialogView.findViewById(R.id.tvStaffPermRole);
        if (tvRole != null) {
            tvRole.setText(role);
        }

        com.google.android.material.materialswitch.MaterialSwitch swRevenue = dialogView.findViewById(R.id.swPermRevenue);
        if (swRevenue != null && tvRevenueStatus != null) {
            swRevenue.setChecked("Cho phép".equals(tvRevenueStatus.getText().toString()));
        }

        View btnPermResetPassword = dialogView.findViewById(R.id.btnPermResetPassword);
        if (btnPermResetPassword != null) {
            btnPermResetPassword.setOnClickListener(v -> showChangePasswordDialog(staffName, ""));
        }

        dialogView.findViewById(R.id.btnCloseStaffPerm).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSaveStaffPerm).setOnClickListener(v -> {
            if (swRevenue != null && tvRevenueStatus != null) {
                if (swRevenue.isChecked()) {
                    tvRevenueStatus.setText("Cho phép");
                    tvRevenueStatus.setTextColor(Color.parseColor("#166534"));
                } else {
                    tvRevenueStatus.setText("Đã ẩn");
                    tvRevenueStatus.setTextColor(Color.parseColor("#DC2626"));
                }
            }
            Toast.makeText(this, "Đã cập nhật phân quyền cho " + staffName + " thành công!", Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.navStaffFacilities).setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerMainActivity.class));
            finish();
        });
        findViewById(R.id.navStaffSchedule).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerScheduleActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navStaffTab).setOnClickListener(v -> {
            // Đang ở tab Nhân sự
        });
        findViewById(R.id.navStaffFinance).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerFinanceActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.navStaffReviews).setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerReviewActivity.class);
            intent.putExtra("COMPLEX_NAME", currentComplexName);
            startActivity(intent);
            finish();
        });
    }
}
