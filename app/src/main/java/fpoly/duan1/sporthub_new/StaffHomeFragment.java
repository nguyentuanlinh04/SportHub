package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class StaffHomeFragment extends Fragment {

    private TextView tvStatTotalCount;
    private TextView tvStatPlayingCount;
    private TextView tvStatWaitingCount;
    private TextView tvDebtAmount;
    private TextView tvUpcomingBadge;
    private Button btnCheckInNow;
    private TextView tvFacilityName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_home, container, false);

        tvStatTotalCount = view.findViewById(R.id.tv_stat_total_count);
        tvStatPlayingCount = view.findViewById(R.id.tv_stat_playing_count);
        tvStatWaitingCount = view.findViewById(R.id.tv_stat_waiting_count);
        tvDebtAmount = view.findViewById(R.id.tv_debt_amount);
        tvUpcomingBadge = view.findViewById(R.id.tv_upcoming_badge);
        btnCheckInNow = view.findViewById(R.id.btn_check_in_now);
        tvFacilityName = view.findViewById(R.id.tv_facility_name);
        if (tvFacilityName != null) {
            tvFacilityName.setText(StaffDataStore.getCurrentFacility());
        }

        // Facility selector dropdown
        View layoutFacility = view.findViewById(R.id.layout_facility_selector);
        if (layoutFacility != null && tvFacilityName != null) {
            layoutFacility.setOnClickListener(v -> {
                fpoly.duan1.sporthub_new.helper.FacilityDialogHelper.show(getContext(), facilityName -> {
                    tvFacilityName.setText(facilityName);
                });
            });
        }

        // Notification Bell
        FrameLayout btnNotification = view.findViewById(R.id.btn_notification);
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(new StaffNotificationsFragment());
                }
            });
        }

        // Active Shift Card buttons
        View btnHandover = view.findViewById(R.id.btn_handover_shift);
        if (btnHandover != null) {
            btnHandover.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(new StaffShiftFragment());
                    ((MainActivity) getActivity()).updateBottomNavSelection(R.id.nav_shift);
                }
            });
        }

        View btnViewReport = view.findViewById(R.id.btn_view_report);
        if (btnViewReport != null) {
            btnViewReport.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), ShiftReportActivity.class));
            });
        }

        // Quick action: Quét QR
        View actionQr = view.findViewById(R.id.action_qr);
        if (actionQr != null) {
            actionQr.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(new StaffQrFragment());
                    ((MainActivity) getActivity()).updateBottomNavSelection(R.id.nav_qr);
                }
            });
        }

        // Quick action: Tạo đơn
        View actionCreateOrder = view.findViewById(R.id.action_create_order);
        if (actionCreateOrder != null) {
            actionCreateOrder.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), CreateOrderActivity.class));
            });
        }

        // Quick action: Đổi giờ
        View actionChangeTime = view.findViewById(R.id.action_change_time);
        if (actionChangeTime != null) {
            actionChangeTime.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), ChangeTimeCourtActivity.class));
            });
        }

        // Quick action: Dịch vụ
        View layoutService = view.findViewById(R.id.layout_service);
        if (layoutService != null) {
            layoutService.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), IncidentalServicesActivity.class));
            });
        }

        // Upcoming Booking: Check-in ngay button
        if (btnCheckInNow != null) {
            btnCheckInNow.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CheckInScannerActivity.class);
                startActivity(intent);
            });
        }

        View cardUpcoming = view.findViewById(R.id.card_upcoming_booking);
        if (cardUpcoming != null) {
            cardUpcoming.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CustomerLateActivity.class);
                startActivity(intent);
            });
        }

        // Click stat cards to open schedule
        View.OnClickListener goToSchedule = v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new StaffScheduleFragment());
                ((MainActivity) getActivity()).updateBottomNavSelection(R.id.nav_schedule);
            }
        };
        View cardTotal = view.findViewById(R.id.card_stat_total);
        if (cardTotal != null) cardTotal.setOnClickListener(goToSchedule);
        View cardPlaying = view.findViewById(R.id.card_stat_playing);
        if (cardPlaying != null) cardPlaying.setOnClickListener(goToSchedule);
        View cardWaiting = view.findViewById(R.id.card_stat_waiting);
        if (cardWaiting != null) cardWaiting.setOnClickListener(goToSchedule);

        // Debt card: Xem đơn
        View btnViewDebtOrders = view.findViewById(R.id.btn_view_debt_orders);
        if (btnViewDebtOrders != null) {
            btnViewDebtOrders.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
            });
        }

        updateUI();
        return view;
    }

    private void updateUI() {
        if (tvStatTotalCount != null) {
            tvStatTotalCount.setText(String.valueOf(StaffDataStore.getTotalOrders()));
        }
        if (tvStatPlayingCount != null) {
            tvStatPlayingCount.setText(String.valueOf(StaffDataStore.getActiveOrders()));
        }
        if (tvStatWaitingCount != null) {
            tvStatWaitingCount.setText(String.valueOf(StaffDataStore.getPendingCheckIns()));
        }
        if (tvDebtAmount != null) {
            tvDebtAmount.setText(String.format(java.util.Locale.GERMANY, "%,dđ", StaffDataStore.getDebtAmount()));
        }

        if (StaffDataStore.isCheckedIn()) {
            if (tvUpcomingBadge != null) {
                tvUpcomingBadge.setText("Đang đá");
                tvUpcomingBadge.setTextColor(0xFF15803D);
                tvUpcomingBadge.setBackgroundTintList(ColorStateList.valueOf(0xFFDCFCE7));
            }
            if (btnCheckInNow != null) {
                btnCheckInNow.setText("Đã check-in");
                btnCheckInNow.setEnabled(false);
                btnCheckInNow.setBackgroundTintList(ColorStateList.valueOf(0xFF9CA3AF));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
