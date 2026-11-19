package fpoly.duan1.sporthub_new;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.adapter.NotificationAdapter;
import fpoly.duan1.sporthub_new.model.NotificationItem;

public class NotificationActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvTabAll, tvTabUnread;
    private RecyclerView rvNotifications;

    private NotificationAdapter adapter;
    private final List<NotificationItem> fullList = new ArrayList<>();
    private final List<NotificationItem> displayList = new ArrayList<>();

    private boolean isFilterUnreadOnly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainNotification), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupSampleData();
        setupAdapter();
        setupTabs();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvTabAll = findViewById(R.id.tvTabAll);
        tvTabUnread = findViewById(R.id.tvTabUnread);
        rvNotifications = findViewById(R.id.rvNotifications);

        btnBack.setOnClickListener(v -> finish());
    }

    private void setupSampleData() {
        fullList.clear();
        // Item 1 (Unread)
        fullList.add(new NotificationItem(
                "n1",
                "Nhắc lịch: ngày mai 18:00",
                "Cụm sân Thắng Long · Sân 5-A · Còn thu 595.000đ tại sân",
                false,
                System.currentTimeMillis() - 1000 * 60 * 30
        ));

        // Item 2 (Unread)
        fullList.add(new NotificationItem(
                "n2",
                "Hoàng Nam đã nhận yêu cầu giao lưu",
                "FC Cầu Giấy United · T4 19:00 · Mở chat để chốt kèo",
                false,
                System.currentTimeMillis() - 1000 * 60 * 120
        ));

        // Item 3 (Read)
        fullList.add(new NotificationItem(
                "n3",
                "Đặt sân thành công",
                "Mã SH-1109-0187 · Đã cọc 255.000đ",
                true,
                System.currentTimeMillis() - 1000 * 60 * 60 * 24
        ));

        // Item 4 (Read)
        fullList.add(new NotificationItem(
                "n4",
                "Đã hoàn cọc 75.000đ",
                "Đơn SH-1030-0098 đã hủy trước 12 giờ",
                true,
                System.currentTimeMillis() - 1000 * 60 * 60 * 48
        ));

        // Item 5 (Read)
        fullList.add(new NotificationItem(
                "n5",
                "Trận ngày 01/11 đã xong",
                "Đánh giá sân để giúp đội khác chọn sân",
                true,
                System.currentTimeMillis() - 1000 * 60 * 60 * 72
        ));

        updateDisplayList();
    }

    private void setupAdapter() {
        adapter = new NotificationAdapter(this, displayList, (item, position) -> {
            Toast.makeText(this, "Đã chọn: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            if (isFilterUnreadOnly) {
                updateDisplayList();
            }
        });
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);
    }

    private void setupTabs() {
        tvTabAll.setOnClickListener(v -> {
            isFilterUnreadOnly = false;
            updateTabUI();
            updateDisplayList();
        });

        tvTabUnread.setOnClickListener(v -> {
            isFilterUnreadOnly = true;
            updateTabUI();
            updateDisplayList();
        });
    }

    private void updateTabUI() {
        if (!isFilterUnreadOnly) {
            tvTabAll.setBackgroundResource(R.drawable.bg_tab_selected);
            tvTabAll.setTextColor(ContextCompat.getColor(this, R.color.text_title));
            tvTabAll.setTypeface(null, android.graphics.Typeface.BOLD);

            tvTabUnread.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            tvTabUnread.setTextColor(ContextCompat.getColor(this, R.color.text_sub));
            tvTabUnread.setTypeface(null, android.graphics.Typeface.NORMAL);
        } else {
            tvTabUnread.setBackgroundResource(R.drawable.bg_tab_selected);
            tvTabUnread.setTextColor(ContextCompat.getColor(this, R.color.text_title));
            tvTabUnread.setTypeface(null, android.graphics.Typeface.BOLD);

            tvTabAll.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            tvTabAll.setTextColor(ContextCompat.getColor(this, R.color.text_sub));
            tvTabAll.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void updateDisplayList() {
        displayList.clear();
        for (NotificationItem item : fullList) {
            if (!isFilterUnreadOnly || !item.isRead()) {
                displayList.add(item);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
