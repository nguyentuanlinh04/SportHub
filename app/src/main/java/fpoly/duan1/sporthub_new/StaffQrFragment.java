package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class StaffQrFragment extends Fragment {

    private boolean isTorchOn = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_qr, container, false);

        // Torch toggle
        FrameLayout btnTorch = view.findViewById(R.id.btn_torch_toggle);
        if (btnTorch != null) {
            btnTorch.setOnClickListener(v -> {
                isTorchOn = !isTorchOn;
                if (isTorchOn) {
                    btnTorch.setBackgroundColor(0xFF3B82F6);
                    Toast.makeText(getContext(), "Đã bật đèn pin", Toast.LENGTH_SHORT).show();
                } else {
                    btnTorch.setBackgroundResource(R.drawable.bg_torch_btn);
                    Toast.makeText(getContext(), "Đã tắt đèn pin", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Tabs
        TextView tabScanQr = view.findViewById(R.id.tab_scan_qr);
        TextView tabEnterCode = view.findViewById(R.id.tab_enter_code);

        if (tabScanQr != null && tabEnterCode != null) {
            tabScanQr.setOnClickListener(v -> {
                tabScanQr.setBackgroundResource(R.drawable.bg_qr_tab_active);
                tabScanQr.setTextColor(0xFF0C4A2B);
                tabEnterCode.setBackground(null);
                tabEnterCode.setTextColor(0xFF4B5563);
            });

            tabEnterCode.setOnClickListener(v -> {
                tabEnterCode.setBackgroundResource(R.drawable.bg_qr_tab_active);
                tabEnterCode.setTextColor(0xFF0C4A2B);
                tabScanQr.setBackground(null);
                tabScanQr.setTextColor(0xFF4B5563);

                // Show enter code dialog
                EditText input = new EditText(requireContext());
                input.setHint("Ví dụ: SH-1108-0152");
                input.setPadding(40, 30, 40, 30);

                new AlertDialog.Builder(requireContext())
                        .setTitle("Nhập mã đặt sân thủ công")
                        .setView(input)
                        .setPositiveButton("Xác nhận", (dialog, which) -> {
                            String code = input.getText().toString().trim();
                            if (!code.isEmpty()) {
                                Toast.makeText(getContext(), "Đã tìm thấy đơn " + code + ". Đang chuyển tới chi tiết...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Vui lòng nhập mã hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> {
                            tabScanQr.performClick();
                        })
                        .show();
            });
        }

        // Recent items click
        View item1 = view.findViewById(R.id.card_scan_item_1);
        if (item1 != null) {
            item1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
            });
        }

        View item2 = view.findViewById(R.id.card_scan_item_2);
        if (item2 != null) {
            item2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
            });
        }

        View item3 = view.findViewById(R.id.card_scan_item_3);
        if (item3 != null) {
            item3.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Chi tiết mã không hợp lệ")
                        .setMessage("Mã này được cấp bởi hệ thống Sân bóng Mai Dịch. Vui lòng hướng dẫn khách hàng kiểm tra lại đúng cơ sở đã đặt.")
                        .setPositiveButton("Đã hiểu", null)
                        .show();
            });
        }

        return view;
    }
}
