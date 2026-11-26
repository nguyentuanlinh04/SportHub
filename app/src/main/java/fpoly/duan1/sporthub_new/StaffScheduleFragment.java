package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.helper.FacilityDialogHelper;
import fpoly.duan1.sporthub_new.helper.StaffDataStore;

public class StaffScheduleFragment extends Fragment {

    private CardView card1, card2, card3, card4;
    private TextView tvScheduleFacility;
    private TextView tvFilterCourt;
    private TextView tvFilterStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_schedule, container, false);

        tvScheduleFacility = view.findViewById(R.id.tv_schedule_facility);
        if (tvScheduleFacility != null) {
            tvScheduleFacility.setText(StaffDataStore.getCurrentFacility());
        }

        View layoutFacilitySelector = view.findViewById(R.id.layout_facility_selector);
        if (layoutFacilitySelector != null) {
            layoutFacilitySelector.setOnClickListener(v -> showFacilitySelectorDialog());
        }

        tvFilterCourt = view.findViewById(R.id.tv_filter_court);
        View layoutFilterCourt = view.findViewById(R.id.layout_filter_court);
        if (layoutFilterCourt != null) {
            layoutFilterCourt.setOnClickListener(v -> {
                String[] courts = {"Tất cả sân", "Sân 5-A", "Sân 5-B", "Sân 7-1", "Sân 11"};
                new AlertDialog.Builder(requireContext())
                        .setTitle("Lọc theo sân")
                        .setItems(courts, (dialog, which) -> {
                            if (tvFilterCourt != null) tvFilterCourt.setText(courts[which]);
                            filterByCourt(courts[which]);
                        })
                        .show();
            });
        }

        tvFilterStatus = view.findViewById(R.id.tv_filter_status);
        View layoutFilterStatus = view.findViewById(R.id.layout_filter_status);
        if (layoutFilterStatus != null) {
            layoutFilterStatus.setOnClickListener(v -> {
                String[] statuses = {"Tất cả trạng thái", "Đang đá", "Chờ check-in", "Sắp tới", "Hoàn thành"};
                new AlertDialog.Builder(requireContext())
                        .setTitle("Lọc theo trạng thái")
                        .setItems(statuses, (dialog, which) -> {
                            if (tvFilterStatus != null) tvFilterStatus.setText(statuses[which]);
                            filterByStatus(statuses[which]);
                        })
                        .show();
            });
        }

        Button btnCreateOrder = view.findViewById(R.id.btn_create_order);
        if (btnCreateOrder != null) {
            btnCreateOrder.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                startActivity(intent);
            });
        }

        card1 = view.findViewById(R.id.card_booking_1);
        card2 = view.findViewById(R.id.card_booking_2);
        card3 = view.findViewById(R.id.card_booking_3);
        card4 = view.findViewById(R.id.card_booking_4);

        View.OnClickListener openDetails = v -> {
            Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
            startActivity(intent);
        };

        if (card1 != null) card1.setOnClickListener(openDetails);
        if (card2 != null) card2.setOnClickListener(openDetails);
        if (card3 != null) card3.setOnClickListener(openDetails);
        if (card4 != null) card4.setOnClickListener(openDetails);

        EditText edtSearchBooking = view.findViewById(R.id.edtSearchBooking);
        if (edtSearchBooking != null) {
            edtSearchBooking.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterBookings(s.toString().trim().toLowerCase());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        return view;
    }

    private void filterByCourt(String court) {
        if ("Tất cả sân".equals(court)) {
            if (card1 != null) card1.setVisibility(View.VISIBLE);
            if (card2 != null) card2.setVisibility(View.VISIBLE);
            if (card3 != null) card3.setVisibility(View.VISIBLE);
            if (card4 != null) card4.setVisibility(View.VISIBLE);
        } else if ("Sân 5-A".equals(court)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.VISIBLE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Sân 5-B".equals(court)) {
            if (card1 != null) card1.setVisibility(View.VISIBLE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Sân 7-1".equals(court)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.VISIBLE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Sân 11".equals(court)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.VISIBLE);
        }
    }

    private void filterByStatus(String status) {
        if ("Tất cả trạng thái".equals(status)) {
            if (card1 != null) card1.setVisibility(View.VISIBLE);
            if (card2 != null) card2.setVisibility(View.VISIBLE);
            if (card3 != null) card3.setVisibility(View.VISIBLE);
            if (card4 != null) card4.setVisibility(View.VISIBLE);
        } else if ("Hoàn thành".equals(status)) {
            if (card1 != null) card1.setVisibility(View.VISIBLE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Đang đá".equals(status)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.VISIBLE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Chờ check-in".equals(status)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.VISIBLE);
            if (card4 != null) card4.setVisibility(View.GONE);
        } else if ("Sắp tới".equals(status)) {
            if (card1 != null) card1.setVisibility(View.GONE);
            if (card2 != null) card2.setVisibility(View.GONE);
            if (card3 != null) card3.setVisibility(View.GONE);
            if (card4 != null) card4.setVisibility(View.VISIBLE);
        }
    }

    private void filterBookings(String query) {
        if (query.isEmpty()) {
            if (card1 != null) card1.setVisibility(View.VISIBLE);
            if (card2 != null) card2.setVisibility(View.VISIBLE);
            if (card3 != null) card3.setVisibility(View.VISIBLE);
            if (card4 != null) card4.setVisibility(View.VISIBLE);
            return;
        }

        boolean match1 = "chị hoa".contains(query) || query.contains("chị hoa") || "0127".contains(query) || query.contains("0127") || "sân 5".contains(query) || query.contains("sân 5");
        if (card1 != null) card1.setVisibility(match1 ? View.VISIBLE : View.GONE);

        boolean match2 = "anh tuấn".contains(query) || query.contains("anh tuấn") || "anh tuan".contains(query) || query.contains("anh tuan") || "0141".contains(query) || query.contains("0141") || "sân 7".contains(query) || query.contains("sân 7");
        if (card2 != null) card2.setVisibility(match2 ? View.VISIBLE : View.GONE);

        boolean match3 = "minh".contains(query) || query.contains("minh") || "0152".contains(query) || query.contains("0152") || "sân 5".contains(query) || query.contains("sân 5");
        if (card3 != null) card3.setVisibility(match3 ? View.VISIBLE : View.GONE);

        boolean match4 = "mai dịch".contains(query) || query.contains("mai dịch") || "mai dich".contains(query) || query.contains("mai dich") || "fc".contains(query) || query.contains("fc") || "sân 11".contains(query) || query.contains("sân 11");
        if (card4 != null) card4.setVisibility(match4 ? View.VISIBLE : View.GONE);
    }

    private void showFacilitySelectorDialog() {
        FacilityDialogHelper.show(getContext(), facilityName -> {
            if (tvScheduleFacility != null) {
                tvScheduleFacility.setText(facilityName);
            }
        });
    }
}
