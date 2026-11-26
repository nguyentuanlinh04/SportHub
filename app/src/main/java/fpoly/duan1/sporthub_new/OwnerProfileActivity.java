package fpoly.duan1.sporthub_new;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OwnerProfileActivity extends AppCompatActivity {

    private View btnBackProfile;
    private View btnShareProfile;
    private View btnProfileAvatar;
    private TextView tvAvatarInitials;
    private TextView tvOwnerProfileName;
    private TextView tvOwnerProfileSubtitle;
    private TextView btnEditOwnerProfile;

    // Contact info
    private TextView tvOwnerContactPhone;
    private TextView tvOwnerContactEmail;
    private TextView tvOwnerCallHours;

    // Complexes
    private View itemComplexQ7;
    private View itemComplexThuDuc;
    private View itemComplexBinhThanh;

    // Verifications
    private View btnVerifyCccd;
    private View btnVerifyGpkd;
    private View btnVerifyPhone;
    private View btnVerifyEmail;

    // Billing Cycle Switcher
    private TextView tabCycleMonthly;
    private TextView tabCycleYearly;
    private TextView tvPriceBasic;
    private TextView tvBillingDescBasic;
    private TextView tvPricePro;
    private TextView tvBillingDescPro;
    private TextView tvPriceEnterprise;
    private TextView tvBillingDescEnterprise;

    private SwitchCompat switchAutoRenew;
    private Button btnDowngradeBasic;
    private Button btnRenewPro;
    private Button btnUpgradeEnterprise;

    // Invoices
    private View itemPaymentInvoice1;
    private View itemPaymentInvoice2;

    // Settings
    private View itemOwnerBankAccount;
    private TextView tvOwnerBankAccount;
    private SwitchCompat switchNotifyNewBooking;
    private SwitchCompat switchNotifyCancelBooking;
    private SwitchCompat switchNotifyReview;
    private SwitchCompat switchTwoFactorAuth;

    private Button btnOwnerLogout;

    private boolean isYearlySelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOwnerProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupListeners();
        setupBillingCycleToggle();
    }

    private void initViews() {
        btnBackProfile = findViewById(R.id.btnBackProfile);
        btnShareProfile = findViewById(R.id.btnShareProfile);
        btnProfileAvatar = findViewById(R.id.btnProfileAvatar);
        tvAvatarInitials = findViewById(R.id.tvAvatarInitials);
        tvOwnerProfileName = findViewById(R.id.tvOwnerProfileName);
        tvOwnerProfileSubtitle = findViewById(R.id.tvOwnerProfileSubtitle);
        btnEditOwnerProfile = findViewById(R.id.btnEditOwnerProfile);

        tvOwnerContactPhone = findViewById(R.id.tvOwnerContactPhone);
        tvOwnerContactEmail = findViewById(R.id.tvOwnerContactEmail);
        tvOwnerCallHours = findViewById(R.id.tvOwnerCallHours);

        itemComplexQ7 = findViewById(R.id.itemComplexQ7);
        itemComplexThuDuc = findViewById(R.id.itemComplexThuDuc);
        itemComplexBinhThanh = findViewById(R.id.itemComplexBinhThanh);

        btnVerifyCccd = findViewById(R.id.btnVerifyCccd);
        btnVerifyGpkd = findViewById(R.id.btnVerifyGpkd);
        btnVerifyPhone = findViewById(R.id.btnVerifyPhone);
        btnVerifyEmail = findViewById(R.id.btnVerifyEmail);

        switchAutoRenew = findViewById(R.id.switchAutoRenew);
        tabCycleMonthly = findViewById(R.id.tabCycleMonthly);
        tabCycleYearly = findViewById(R.id.tabCycleYearly);
        tvPriceBasic = findViewById(R.id.tvPriceBasic);
        tvBillingDescBasic = findViewById(R.id.tvBillingDescBasic);
        tvPricePro = findViewById(R.id.tvPricePro);
        tvBillingDescPro = findViewById(R.id.tvBillingDescPro);
        tvPriceEnterprise = findViewById(R.id.tvPriceEnterprise);
        tvBillingDescEnterprise = findViewById(R.id.tvBillingDescEnterprise);

        btnDowngradeBasic = findViewById(R.id.btnDowngradeBasic);
        btnRenewPro = findViewById(R.id.btnRenewPro);
        btnUpgradeEnterprise = findViewById(R.id.btnUpgradeEnterprise);

        itemPaymentInvoice1 = findViewById(R.id.itemPaymentInvoice1);
        itemPaymentInvoice2 = findViewById(R.id.itemPaymentInvoice2);

        itemOwnerBankAccount = findViewById(R.id.itemOwnerBankAccount);
        tvOwnerBankAccount = findViewById(R.id.tvOwnerBankAccount);
        switchNotifyNewBooking = findViewById(R.id.switchNotifyNewBooking);
        switchNotifyCancelBooking = findViewById(R.id.switchNotifyCancelBooking);
        switchNotifyReview = findViewById(R.id.switchNotifyReview);
        switchTwoFactorAuth = findViewById(R.id.switchTwoFactorAuth);

        btnOwnerLogout = findViewById(R.id.btnOwnerLogout);
    }

    private void setupListeners() {
        btnBackProfile.setOnClickListener(v -> finish());

        btnShareProfile.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hệ thống sân bóng Star Arena - Chủ sở hữu: " + tvOwnerProfileName.getText() + "\nHotline: " + tvOwnerContactPhone.getText() + "\nĐặt sân nhanh chóng qua ứng dụng SportHub!");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Chia sẻ hồ sơ chủ sân"));
        });

        // Edit Profile Dialog
        View.OnClickListener editProfileListener = v -> showEditProfileDialog();
        if (btnProfileAvatar != null) btnProfileAvatar.setOnClickListener(editProfileListener);
        if (btnEditOwnerProfile != null) btnEditOwnerProfile.setOnClickListener(editProfileListener);
        if (tvOwnerProfileName != null) tvOwnerProfileName.setOnClickListener(editProfileListener);

        // Contact actions
        if (tvOwnerContactPhone != null) {
            tvOwnerContactPhone.setOnClickListener(v -> {
                String phone = tvOwnerContactPhone.getText().toString();
                String[] options = {"📞 Gọi ngay: " + phone, "📋 Sao chép số điện thoại", "✏️ Thay đổi số điện thoại"};
                new AlertDialog.Builder(this)
                        .setTitle("Tùy chọn liên hệ")
                        .setItems(options, (dialog, which) -> {
                            if (which == 0) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(callIntent);
                            } else if (which == 1) {
                                copyToClipboard("Số điện thoại", phone);
                            } else {
                                showEditSingleFieldDialog("Đổi số điện thoại", phone, InputType.TYPE_CLASS_PHONE, newText -> {
                                    tvOwnerContactPhone.setText(newText);
                                    Toast.makeText(this, "Đã cập nhật số điện thoại!", Toast.LENGTH_SHORT).show();
                                });
                            }
                        })
                        .show();
            });
        }

        if (tvOwnerContactEmail != null) {
            tvOwnerContactEmail.setOnClickListener(v -> {
                String email = tvOwnerContactEmail.getText().toString();
                String[] options = {"✉️ Gửi email tới: " + email, "📋 Sao chép email", "✏️ Thay đổi email"};
                new AlertDialog.Builder(this)
                        .setTitle("Tùy chọn Email")
                        .setItems(options, (dialog, which) -> {
                            if (which == 0) {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                                startActivity(emailIntent);
                            } else if (which == 1) {
                                copyToClipboard("Email", email);
                            } else {
                                showEditSingleFieldDialog("Đổi Email", email, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, newText -> {
                                    tvOwnerContactEmail.setText(newText);
                                    Toast.makeText(this, "Đã cập nhật email!", Toast.LENGTH_SHORT).show();
                                });
                            }
                        })
                        .show();
            });
        }

        if (tvOwnerCallHours != null) {
            tvOwnerCallHours.setOnClickListener(v -> {
                String[] hours = {"06:00 – 22:00", "07:00 – 22:00 (Mặc định)", "08:00 – 23:00", "24/24h (Mọi khung giờ)"};
                new AlertDialog.Builder(this)
                        .setTitle("Chọn khung giờ nhận cuộc gọi")
                        .setItems(hours, (dialog, which) -> {
                            tvOwnerCallHours.setText(hours[which].replace(" (Mặc định)", ""));
                            Toast.makeText(this, "Đã cập nhật khung giờ nhận cuộc gọi!", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            });
        }

        // Complexes Click Navigation
        if (itemComplexQ7 != null) {
            itemComplexQ7.setOnClickListener(v -> {
                Intent intent = new Intent(this, OwnerComplexDetailActivity.class);
                intent.putExtra("complex_name", "Star Arena Quận 7");
                startActivity(intent);
            });
        }

        if (itemComplexThuDuc != null) {
            itemComplexThuDuc.setOnClickListener(v -> {
                Intent intent = new Intent(this, OwnerComplexDetailActivity.class);
                intent.putExtra("complex_name", "Star Arena Thủ Đức");
                startActivity(intent);
            });
        }

        if (itemComplexBinhThanh != null) {
            itemComplexBinhThanh.setOnClickListener(v ->
                    new AlertDialog.Builder(this)
                            .setTitle("Star Arena Bình Thạnh")
                            .setMessage("Cơ sở hiện đang trong giai đoạn hoàn thiện sân cỏ nhân tạo và lắp đặt hệ thống đèn LED cao áp.\n\nThời gian khai trương dự kiến: 01/12/2026.\nQuy mô: 3 sân 7 người tiêu chuẩn.")
                            .setPositiveButton("Xem chi tiết", (dialog, which) -> {
                                Intent intent = new Intent(this, OwnerComplexDetailActivity.class);
                                intent.putExtra("complex_name", "Star Arena Bình Thạnh");
                                startActivity(intent);
                            })
                            .setNegativeButton("Đóng", null)
                            .show()
            );
        }

        // Identity Verification Click Handlers
        View.OnClickListener verifyListener = v ->
                new AlertDialog.Builder(this)
                        .setTitle("Xác thực chủ sân")
                        .setMessage("Hồ sơ pháp lý của bạn đã được kiểm duyệt & phê duyệt chính thức bởi SportHub:\n\n• CCCD gắn chip: Đã xác thực\n• GPKD số: 0316889922 (Sở KH&ĐT TP.HCM)\n• Số điện thoại & Email: Đã liên kết OTP\n• Huy hiệu: Chủ sân chính chủ tin cậy.")
                        .setPositiveButton("Tuyệt vời", null)
                        .show();

        if (btnVerifyCccd != null) btnVerifyCccd.setOnClickListener(verifyListener);
        if (btnVerifyGpkd != null) btnVerifyGpkd.setOnClickListener(verifyListener);
        if (btnVerifyPhone != null) btnVerifyPhone.setOnClickListener(verifyListener);
        if (btnVerifyEmail != null) btnVerifyEmail.setOnClickListener(verifyListener);

        // Auto Renew Switch
        if (switchAutoRenew != null) {
            switchAutoRenew.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã bật tự động gia hạn gói cước trước ngày 31/12/2026", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Tắt tự động gia hạn?")
                            .setMessage("Gói Chuyên nghiệp của bạn sẽ không tự động trừ tiền và hết hạn vào ngày 31/12/2026. Các tính năng nâng cao có thể bị giới hạn sau ngày này.")
                            .setPositiveButton("Tắt gia hạn", (dialog, which) -> {
                                Toast.makeText(this, "Đã tắt tự động gia hạn.", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Giữ bật", (dialog, which) -> switchAutoRenew.setChecked(true))
                            .show();
                }
            });
        }

        // Subscriptions buttons
        btnRenewPro.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Gia hạn Gói Chuyên nghiệp")
                        .setMessage("Bạn đang chọn gia hạn Gói Chuyên nghiệp (559.200đ/tháng - thanh toán 6.710.400đ/năm).\n\nSố lượng: 5 cụm sân, 30 sân con, 10 nhân viên, giá giờ vàng tự động và xuất báo cáo CSV.\n\nXác nhận thanh toán tiếp tục?")
                        .setPositiveButton("Thanh toán ngay", (dialog, which) -> {
                            Toast.makeText(this, "Yêu cầu gia hạn thành công! Hạn dùng kéo dài đến 31/12/2027.", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        btnDowngradeBasic.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Chuyển xuống Gói Cơ bản?")
                        .setMessage("Gói Cơ bản giới hạn 1 cụm sân, 5 sân và 2 nhân viên.\n\nCác tính năng nâng cao như giờ vàng tự động sẽ bị tạm khóa khi hết chu kỳ hiện tại.")
                        .setPositiveButton("Xác nhận", (dialog, which) -> {
                            Toast.makeText(this, "Đã ghi nhận yêu cầu thay đổi gói cước cho kỳ tiếp theo.", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        btnUpgradeEnterprise.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Nâng cấp Gói Doanh nghiệp")
                        .setMessage("Gói Doanh nghiệp mở khóa: KHÔNG GIỚI HẠN cụm sân, sân con và nhân viên, hỗ trợ riêng 24/7.\n\nChi phí: 1.279.200đ/tháng (15.350.400đ/năm).")
                        .setPositiveButton("Liên hệ nâng cấp", (dialog, which) -> {
                            Toast.makeText(this, "Chuyên viên hỗ trợ SportHub sẽ liên hệ trong 15 phút!", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss())
                        .show()
        );

        // Invoices click
        if (itemPaymentInvoice1 != null) {
            itemPaymentInvoice1.setOnClickListener(v -> showInvoiceDialog(
                    "SH-2026-001",
                    "Gói Chuyên nghiệp · 12 tháng",
                    "6.710.400đ",
                    "01/01/2026 - 10:25",
                    "Thẻ tín dụng Vietcombank ••••6789"
            ));
        }

        if (itemPaymentInvoice2 != null) {
            itemPaymentInvoice2.setOnClickListener(v -> showInvoiceDialog(
                    "SH-2025-001",
                    "Gói Cơ bản · 12 tháng",
                    "2.870.400đ",
                    "01/01/2025 - 09:14",
                    "Chuyển khoản QR Napas 24/7"
            ));
        }

        // Bank Account Setting
        if (itemOwnerBankAccount != null) {
            itemOwnerBankAccount.setOnClickListener(v -> {
                String[] banks = {"Vietcombank (Hiện tại: ••••6789)", "Techcombank", "MB Bank", "BIDV", "ACB"};
                new AlertDialog.Builder(this)
                        .setTitle("Tài khoản thụ hưởng doanh thu")
                        .setItems(banks, (dialog, which) -> {
                            String selectedBank = banks[which].split(" ")[0];
                            showEditSingleFieldDialog("Nhập số tài khoản " + selectedBank, "1028394857", InputType.TYPE_CLASS_NUMBER, newStk -> {
                                if (tvOwnerBankAccount != null) {
                                    String masked = newStk.length() > 4 ? "••••" + newStk.substring(newStk.length() - 4) : newStk;
                                    tvOwnerBankAccount.setText(selectedBank + " " + masked);
                                }
                                Toast.makeText(this, "Đã cập nhật tài khoản nhận tiền!", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .show();
            });
        }

        // Notification and Security Switches
        if (switchNotifyNewBooking != null) {
            switchNotifyNewBooking.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Toast.makeText(this, isChecked ? "Đã bật thông báo đặt sân mới" : "Đã tắt thông báo đặt sân mới", Toast.LENGTH_SHORT).show();
            });
        }

        if (switchNotifyCancelBooking != null) {
            switchNotifyCancelBooking.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Toast.makeText(this, isChecked ? "Đã bật cảnh báo khách hủy sân" : "Đã tắt cảnh báo khách hủy sân", Toast.LENGTH_SHORT).show();
            });
        }

        if (switchNotifyReview != null) {
            switchNotifyReview.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Toast.makeText(this, isChecked ? "Đã bật thông báo khi có đánh giá mới" : "Đã tắt thông báo đánh giá", Toast.LENGTH_SHORT).show();
            });
        }

        if (switchTwoFactorAuth != null) {
            switchTwoFactorAuth.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã kích hoạt bảo mật 2 lớp qua SMS/OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã tắt xác thực hai lớp", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Logout
        btnOwnerLogout.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản Chủ sân không?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            Intent intent = new Intent(OwnerProfileActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show()
        );
    }

    private void showEditProfileDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 10);

        final EditText edtName = new EditText(this);
        edtName.setHint("Họ và tên chủ sân");
        edtName.setText(tvOwnerProfileName != null ? tvOwnerProfileName.getText() : "");
        layout.addView(edtName);

        final EditText edtSubtitle = new EditText(this);
        edtSubtitle.setHint("Chức danh / Hệ thống sân");
        edtSubtitle.setText(tvOwnerProfileSubtitle != null ? tvOwnerProfileSubtitle.getText() : "");
        layout.addView(edtSubtitle);

        new AlertDialog.Builder(this)
                .setTitle("Chỉnh sửa hồ sơ chủ sân")
                .setView(layout)
                .setPositiveButton("Lưu thay đổi", (dialog, which) -> {
                    String newName = edtName.getText().toString().trim();
                    String newSub = edtSubtitle.getText().toString().trim();

                    if (!newName.isEmpty() && tvOwnerProfileName != null) {
                        tvOwnerProfileName.setText(newName);

                        // Update initials
                        String[] parts = newName.split(" ");
                        if (parts.length >= 2 && tvAvatarInitials != null) {
                            String initials = ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
                            tvAvatarInitials.setText(initials);
                        }
                    }

                    if (!newSub.isEmpty() && tvOwnerProfileSubtitle != null) {
                        tvOwnerProfileSubtitle.setText(newSub);
                    }

                    Toast.makeText(this, "Đã lưu cập nhật thông tin chủ sân thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditSingleFieldDialog(String title, String currentValue, int inputType, OnFieldSavedListener listener) {
        final EditText input = new EditText(this);
        input.setInputType(inputType);
        input.setText(currentValue);
        input.setSelection(input.getText().length());

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(50, 20, 50, 10);
        container.addView(input);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(container)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    if (!text.isEmpty()) {
                        listener.onSaved(text);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showInvoiceDialog(String code, String plan, String amount, String date, String method) {
        String msg = "• Mã hóa đơn: #" + code + "\n"
                + "• Gói dịch vụ: " + plan + "\n"
                + "• Tổng thanh toán: " + amount + " (đã gồm VAT 10%)\n"
                + "• Ngày giao dịch: " + date + "\n"
                + "• Hình thức: " + method + "\n"
                + "• Trạng thái: Giao dịch thành công ✓\n\n"
                + "Hóa đơn điện tử hợp lệ đã được gửi vào hòm thư: " + (tvOwnerContactEmail != null ? tvOwnerContactEmail.getText() : "quang.le@stararena.vn");

        new AlertDialog.Builder(this)
                .setTitle("Chi tiết hóa đơn #" + code)
                .setMessage(msg)
                .setPositiveButton("Tải biên lai (PDF)", (dialog, which) -> {
                    Toast.makeText(this, "Đã lưu hóa đơn PDF vào bộ nhớ máy!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Đóng", null)
                .show();
    }

    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Đã sao chép " + label + ": " + text, Toast.LENGTH_SHORT).show();
        }
    }

    private interface OnFieldSavedListener {
        void onSaved(String newText);
    }

    private void setupBillingCycleToggle() {
        tabCycleMonthly.setOnClickListener(v -> {
            if (isYearlySelected) {
                isYearlySelected = false;
                updateBillingUI();
            }
        });

        tabCycleYearly.setOnClickListener(v -> {
            if (!isYearlySelected) {
                isYearlySelected = true;
                updateBillingUI();
            }
        });
    }

    private void updateBillingUI() {
        if (isYearlySelected) {
            tabCycleYearly.setBackgroundResource(R.drawable.bg_profile_btn_dark);
            tabCycleYearly.setTextColor(Color.WHITE);

            tabCycleMonthly.setBackground(null);
            tabCycleMonthly.setTextColor(Color.parseColor("#9CA3AF"));

            tvPriceBasic.setText("239.200đ");
            tvBillingDescBasic.setText("Thanh toán 2.870.400đ mỗi năm");

            tvPricePro.setText("559.200đ");
            tvBillingDescPro.setText("Thanh toán 6.710.400đ mỗi năm");

            tvPriceEnterprise.setText("1.279.200đ");
            tvBillingDescEnterprise.setText("Thanh toán 15.350.400đ mỗi năm");
        } else {
            tabCycleMonthly.setBackgroundResource(R.drawable.bg_profile_btn_dark);
            tabCycleMonthly.setTextColor(Color.WHITE);

            tabCycleYearly.setBackground(null);
            tabCycleYearly.setTextColor(Color.parseColor("#9CA3AF"));

            tvPriceBasic.setText("299.000đ");
            tvBillingDescBasic.setText("Thanh toán 299.000đ mỗi tháng");

            tvPricePro.setText("699.000đ");
            tvBillingDescPro.setText("Thanh toán 699.000đ mỗi tháng");

            tvPriceEnterprise.setText("1.599.000đ");
            tvBillingDescEnterprise.setText("Thanh toán 1.599.000đ mỗi tháng");
        }
    }
}
