package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class StaffShiftFragment extends Fragment {

    private TextView tvShiftCheckins;
    private TextView tvShiftCollected;
    private TextView tvShiftManual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_shift, container, false);

        tvShiftCheckins = view.findViewById(R.id.tv_shift_checkins);
        tvShiftCollected = view.findViewById(R.id.tv_shift_collected);
        tvShiftManual = view.findViewById(R.id.tv_shift_manual);

        Button btnEndShiftReport = view.findViewById(R.id.btn_end_shift_report);
        if (btnEndShiftReport != null) {
            btnEndShiftReport.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ShiftHandoverActivity.class);
                startActivity(intent);
            });
        }

        Button btnReceiveShift = view.findViewById(R.id.btn_receive_shift);
        if (btnReceiveShift != null) {
            btnReceiveShift.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Nhận ca & Đối soát quỹ")
                        .setMessage("Quỹ tiền mặt đầu ca: 1.500.000đ\nTrang thiết bị: Đầy đủ\nTrạng thái sân: Sẵn sàng\n\nBạn có xác nhận nhận ca trực?")
                        .setPositiveButton("Xác nhận", (dialog, which) -> {
                            Toast.makeText(getContext(), "Đã xác nhận nhận ca và đối soát quỹ thành công!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        Button btnViewHandoverHistory = view.findViewById(R.id.btn_view_handover_history);
        if (btnViewHandoverHistory != null) {
            btnViewHandoverHistory.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), HandoverHistoryActivity.class);
                startActivity(intent);
            });
        }

        updateUI();
        return view;
    }

    private void updateUI() {
        if (tvShiftCheckins != null) {
            tvShiftCheckins.setText(String.valueOf(StaffDataStore.getCheckInCount()));
        }
        if (tvShiftCollected != null) {
            tvShiftCollected.setText(String.valueOf(StaffDataStore.getCollectedOrders()));
        }
        if (tvShiftManual != null) {
            tvShiftManual.setText(String.valueOf(StaffDataStore.getManualOrders()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
