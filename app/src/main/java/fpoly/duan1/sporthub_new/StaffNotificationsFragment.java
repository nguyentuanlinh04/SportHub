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
import androidx.fragment.app.Fragment;

public class StaffNotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_notifications, container, false);

        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(new StaffHomeFragment());
                } else if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
        }

        View dot1 = view.findViewById(R.id.dot_noti_1);
        View dot2 = view.findViewById(R.id.dot_noti_2);
        TextView tabUnread = view.findViewById(R.id.tab_unread_notifications);
        TextView tabAll = view.findViewById(R.id.tab_all_notifications);

        TextView btnMarkAllRead = view.findViewById(R.id.btn_mark_all_read);
        if (btnMarkAllRead != null) {
            btnMarkAllRead.setOnClickListener(v -> {
                if (dot1 != null) dot1.setVisibility(View.GONE);
                if (dot2 != null) dot2.setVisibility(View.GONE);
                if (tabUnread != null) tabUnread.setText("Chưa đọc · 0");
                Toast.makeText(getContext(), "Đã đánh dấu tất cả là đã đọc", Toast.LENGTH_SHORT).show();
            });
        }

        if (tabAll != null && tabUnread != null) {
            tabAll.setOnClickListener(v -> {
                tabAll.setBackgroundResource(R.drawable.bg_chip_active);
                tabAll.setTextColor(0xFFFFFFFF);
                tabUnread.setBackground(null);
                tabUnread.setTextColor(0xFF111827);

                view.findViewById(R.id.card_noti_1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_3).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_4).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_5).setVisibility(View.VISIBLE);
            });

            tabUnread.setOnClickListener(v -> {
                tabUnread.setBackgroundResource(R.drawable.bg_chip_active);
                tabUnread.setTextColor(0xFFFFFFFF);
                tabAll.setBackground(null);
                tabAll.setTextColor(0xFF111827);

                view.findViewById(R.id.card_noti_1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_noti_3).setVisibility(View.GONE);
                view.findViewById(R.id.card_noti_4).setVisibility(View.GONE);
                view.findViewById(R.id.card_noti_5).setVisibility(View.GONE);
            });
        }

        View card1 = view.findViewById(R.id.card_noti_1);
        if (card1 != null) {
            card1.setOnClickListener(v -> {
                if (dot1 != null) dot1.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
            });
        }

        View card2 = view.findViewById(R.id.card_noti_2);
        if (card2 != null) {
            card2.setOnClickListener(v -> {
                if (dot2 != null) dot2.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), ShiftReportActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }
}
