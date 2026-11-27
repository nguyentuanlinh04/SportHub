package fpoly.duan1.sporthub_new.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.BookingTicketActivity;
import fpoly.duan1.sporthub_new.CancelBookingActivity;
import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.ReviewPitchActivity;
import fpoly.duan1.sporthub_new.adapter.BookingAdapter;
import fpoly.duan1.sporthub_new.model.BookingItem;

public class BookingScheduleFragment extends Fragment {

    private TextView tvTabUpcoming, tvTabCompleted, tvTabCancelled;
    private RecyclerView rvBookings;
    private RelativeLayout rlReviewBanner;
    private TextView tvPolicyFooterNote;

    private BookingAdapter adapter;
    private final List<BookingItem> fullList = new ArrayList<>();
    private final List<BookingItem> displayList = new ArrayList<>();

    private int selectedTab = 0; // 0: Sắp tới, 1: Đã hoàn thành, 2: Đã hủy

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupSampleData();
        setupAdapter();
        setupTabs();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupSampleData();
    }

    private void initViews(View view) {
        tvTabUpcoming = view.findViewById(R.id.tvTabUpcoming);
        tvTabCompleted = view.findViewById(R.id.tvTabCompleted);
        tvTabCancelled = view.findViewById(R.id.tvTabCancelled);
        rvBookings = view.findViewById(R.id.rvBookings);
        rlReviewBanner = view.findViewById(R.id.rlReviewBanner);
        tvPolicyFooterNote = view.findViewById(R.id.tvPolicyFooterNote);

        TextView btnReview = view.findViewById(R.id.btnReview);
        if (btnReview != null) {
            btnReview.setOnClickListener(v ->
                    startActivity(new Intent(requireContext(), ReviewPitchActivity.class))
            );
        }
    }

    private void setupSampleData() {
        fullList.clear();
        // --- TAB 0: SẮP TỚI ---
        fullList.add(new BookingItem(
                "b1",
                "Cụm sân Thắng Long",
                "Sân 5-A · SH-1109-0187",
                "Sắp tới",
                "Thứ 2, 09/11",
                "18:00 – 20:00",
                "Đã cọc 255.000đ",
                "595.000đ",
                0
        ));

        fullList.add(new BookingItem(
                "b2",
                "Cụm sân Thắng Long",
                "Sân 7-1 · SH-1115-0233",
                "Sắp tới",
                "Chủ nhật, 15/11",
                "17:00 – 18:00",
                "Trả sau tiền mặt tại sân",
                "400.000đ",
                0
        ));

        boolean b3Reviewed = fpoly.duan1.sporthub_new.utils.ReviewManager.getInstance().isReviewed(requireContext(), "b3");

        // --- TAB 1: ĐÃ HOÀN THÀNH ---
        fullList.add(new BookingItem(
                "b3",
                "Cụm sân Thắng Long",
                "Sân 5-A · SH-1101-0120",
                "Hoàn thành",
                "Chủ nhật, 01/11",
                "18:00 – 20:00",
                "Đã thanh toán",
                "0đ",
                "850.000đ",
                b3Reviewed,
                1
        ));

        fullList.add(new BookingItem(
                "b4",
                "Sân bóng Mai Dịch",
                "Sân 5 · SH-1025-0066",
                "Hoàn thành",
                "Chủ nhật, 25/10",
                "19:00 – 20:00",
                "Đã thanh toán",
                "0đ",
                "200.000đ",
                true,
                1
        ));

        // --- TAB 2: ĐÃ HỦY (MATCHING EXACT SCREENSHOT 3) ---
        fullList.add(new BookingItem(
                "b5",
                "Cụm sân Thắng Long",
                "Sân 5-B · SH-1030-0098",
                "Đã hủy",
                "Thứ 6, 30/10",
                "20:00 – 21:00",
                "Hủy trước 12 giờ",
                "Đã hoàn cọc 75.000đ",
                true,
                2
        ));

        fullList.add(new BookingItem(
                "b6",
                "Cụm sân Thắng Long",
                "Sân 7-1 · SH-1022-0045",
                "Đã hủy",
                "Thứ 4, 22/10",
                "19:00 – 20:00",
                "Hủy sau mốc 12 giờ",
                "Không hoàn cọc",
                false,
                2
        ));

        filterDisplayList();
    }

    private void setupAdapter() {
        adapter = new BookingAdapter(requireContext(), displayList, new BookingAdapter.OnBookingActionListener() {
            @Override
            public void onViewQRClick(BookingItem item) {
                Intent intent = new Intent(requireContext(), BookingTicketActivity.class);
                intent.putExtra("pitch_name", item.getPitchName());
                intent.putExtra("booking_code", item.getSubTitle());
                startActivity(intent);
            }

            @Override
            public void onCancelClick(BookingItem item) {
                Intent intent = new Intent(requireContext(), CancelBookingActivity.class);
                intent.putExtra("pitch_name", item.getPitchName());
                intent.putExtra("booking_code", item.getSubTitle());
                startActivity(intent);
            }

            @Override
            public void onReviewClick(BookingItem item) {
                Intent intent = new Intent(requireContext(), ReviewPitchActivity.class);
                intent.putExtra("pitch_name", item.getPitchName());
                intent.putExtra("booking_code", item.getSubTitle());
                startActivity(intent);
            }

            @Override
            public void onRebookClick(BookingItem item) {
                Toast.makeText(requireContext(), "Đặt lại sân " + item.getPitchName(), Toast.LENGTH_SHORT).show();
            }
        });
        rvBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvBookings.setAdapter(adapter);
    }

    private void setupTabs() {
        tvTabUpcoming.setOnClickListener(v -> selectTab(0));
        tvTabCompleted.setOnClickListener(v -> selectTab(1));
        tvTabCancelled.setOnClickListener(v -> selectTab(2));
    }

    private void selectTab(int index) {
        selectedTab = index;
        updateTabUI(tvTabUpcoming, index == 0);
        updateTabUI(tvTabCompleted, index == 1);
        updateTabUI(tvTabCancelled, index == 2);

        // Hiển thị/Ẩn banner nhắc đánh giá chỉ ở Tab Sắp tới (0)
        if (rlReviewBanner != null) {
            rlReviewBanner.setVisibility(selectedTab == 0 ? View.VISIBLE : View.GONE);
        }

        // Hiển thị/Ẩn câu ghi chú chính sách cọc ở Tab Đã hủy (2)
        if (tvPolicyFooterNote != null) {
            tvPolicyFooterNote.setVisibility(selectedTab == 2 ? View.VISIBLE : View.GONE);
        }

        filterDisplayList();
    }

    private void updateTabUI(TextView tabView, boolean isSelected) {
        if (isSelected) {
            tabView.setBackgroundResource(R.drawable.bg_tab_selected);
            tabView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_title));
            tabView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tabView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
            tabView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_sub));
            tabView.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void filterDisplayList() {
        displayList.clear();
        for (BookingItem item : fullList) {
            if (item.getStatusCode() == selectedTab) {
                displayList.add(item);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
