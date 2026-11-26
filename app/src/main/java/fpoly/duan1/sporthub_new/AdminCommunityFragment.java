package fpoly.duan1.sporthub_new.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.R;

public class AdminCommunityFragment extends Fragment {

    private TextView tabPendingPosts;
    private TextView tabApprovedPosts;
    private TextView tabRemovedPosts;
    private TextView tvPendingPostCount;

    private View cardPost1, cardPost2, cardPost3;
    private View layoutPost1Actions, layoutPost2Actions, layoutPost3Actions;

    private Button btnApprovePost1, btnRemovePost1;
    private Button btnApprovePost2, btnRemovePost2;
    private Button btnApprovePost3, btnRemovePost3;

    // Status: 0 = Pending, 1 = Approved, 2 = Removed
    private int statusPost1 = 0;
    private int statusPost2 = 0;
    private int statusPost3 = 0;

    private int activeTab = 0; // 0 = Pending, 1 = Approved, 2 = Removed

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupTabListeners();
        setupActionListeners();
        filterPosts(0);
    }

    private void initViews(View view) {
        tabPendingPosts = view.findViewById(R.id.tabPendingPosts);
        tabApprovedPosts = view.findViewById(R.id.tabApprovedPosts);
        tabRemovedPosts = view.findViewById(R.id.tabRemovedPosts);
        tvPendingPostCount = view.findViewById(R.id.tvPendingPostCount);

        cardPost1 = view.findViewById(R.id.cardPost1);
        cardPost2 = view.findViewById(R.id.cardPost2);
        cardPost3 = view.findViewById(R.id.cardPost3);

        layoutPost1Actions = view.findViewById(R.id.layoutPost1Actions);
        layoutPost2Actions = view.findViewById(R.id.layoutPost2Actions);
        layoutPost3Actions = view.findViewById(R.id.layoutPost3Actions);

        btnApprovePost1 = view.findViewById(R.id.btnApprovePost1);
        btnRemovePost1 = view.findViewById(R.id.btnRemovePost1);

        btnApprovePost2 = view.findViewById(R.id.btnApprovePost2);
        btnRemovePost2 = view.findViewById(R.id.btnRemovePost2);

        btnApprovePost3 = view.findViewById(R.id.btnApprovePost3);
        btnRemovePost3 = view.findViewById(R.id.btnRemovePost3);
    }

    private void setupTabListeners() {
        tabPendingPosts.setOnClickListener(v -> filterPosts(0));
        tabApprovedPosts.setOnClickListener(v -> filterPosts(1));
        tabRemovedPosts.setOnClickListener(v -> filterPosts(2));
    }

    private void setupActionListeners() {
        // POST 1: FC Cầu Giấy United
        if (btnApprovePost1 != null) {
            btnApprovePost1.setOnClickListener(v -> {
                statusPost1 = 1; // Approved
                Toast.makeText(getContext(), "Đã duyệt bài đăng FC Cầu Giấy United!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }
        if (btnRemovePost1 != null) {
            btnRemovePost1.setOnClickListener(v -> {
                statusPost1 = 2; // Removed
                Toast.makeText(getContext(), "Đã gỡ bài đăng FC Cầu Giấy United!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }

        // POST 2: Thăng Long Rangers
        if (btnApprovePost2 != null) {
            btnApprovePost2.setOnClickListener(v -> {
                statusPost2 = 1; // Approved
                Toast.makeText(getContext(), "Đã duyệt bài đăng Thăng Long Rangers!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }
        if (btnRemovePost2 != null) {
            btnRemovePost2.setOnClickListener(v -> {
                statusPost2 = 2; // Removed
                Toast.makeText(getContext(), "Đã gỡ bài đăng Thăng Long Rangers!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }

        // POST 3: Mai Dịch Old Boys
        if (btnApprovePost3 != null) {
            btnApprovePost3.setOnClickListener(v -> {
                statusPost3 = 1; // Approved
                Toast.makeText(getContext(), "Đã duyệt bài đăng Mai Dịch Old Boys!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }
        if (btnRemovePost3 != null) {
            btnRemovePost3.setOnClickListener(v -> {
                statusPost3 = 2; // Removed
                Toast.makeText(getContext(), "Đã gỡ bài đăng Mai Dịch Old Boys!", Toast.LENGTH_SHORT).show();
                filterPosts(activeTab);
            });
        }
    }

    private void filterPosts(int tab) {
        activeTab = tab;

        // Reset tab styling
        tabPendingPosts.setBackgroundResource(0);
        tabPendingPosts.setTextColor(Color.parseColor("#6B7280"));

        tabApprovedPosts.setBackgroundResource(0);
        tabApprovedPosts.setTextColor(Color.parseColor("#6B7280"));

        tabRemovedPosts.setBackgroundResource(0);
        tabRemovedPosts.setTextColor(Color.parseColor("#6B7280"));

        if (tab == 0) {
            tabPendingPosts.setBackgroundResource(R.drawable.bg_btn_duyet);
            tabPendingPosts.setTextColor(Color.parseColor("#046A38"));
        } else if (tab == 1) {
            tabApprovedPosts.setBackgroundResource(R.drawable.bg_btn_duyet);
            tabApprovedPosts.setTextColor(Color.parseColor("#046A38"));
        } else if (tab == 2) {
            tabRemovedPosts.setBackgroundResource(R.drawable.bg_btn_duyet);
            tabRemovedPosts.setTextColor(Color.parseColor("#046A38"));
        }

        // Calculate pending count
        int pendingCount = 0;
        if (statusPost1 == 0) pendingCount++;
        if (statusPost2 == 0) pendingCount++;
        if (statusPost3 == 0) pendingCount++;

        tabPendingPosts.setText("Chờ duyệt " + pendingCount);
        if (tvPendingPostCount != null) {
            tvPendingPostCount.setText(pendingCount + " bài đăng chờ duyệt");
        }

        // Show/hide cards based on status matching tab
        cardPost1.setVisibility(statusPost1 == tab ? View.VISIBLE : View.GONE);
        cardPost2.setVisibility(statusPost2 == tab ? View.VISIBLE : View.GONE);
        cardPost3.setVisibility(statusPost3 == tab ? View.VISIBLE : View.GONE);

        // Hide action buttons when viewing Approved or Removed tabs
        if (layoutPost1Actions != null) layoutPost1Actions.setVisibility(tab == 0 ? View.VISIBLE : View.GONE);
        if (layoutPost2Actions != null) layoutPost2Actions.setVisibility(tab == 0 ? View.VISIBLE : View.GONE);
        if (layoutPost3Actions != null) layoutPost3Actions.setVisibility(tab == 0 ? View.VISIBLE : View.GONE);
    }
}