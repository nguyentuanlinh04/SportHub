package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fpoly.duan1.sporthub_new.R;

public class AdminDashboardFragment extends Fragment {

    private TextView tvCurrentDate;
    private TextView tvActiveCourtsCount;
    private TextView tvNewUsersCount;
    private TextView tvSuccessTxCount;
    private TextView tvRevenueTotal;
    private ImageView btnNotification;
    private View btnPendingOwners;
    private View btnDuyetOwners;
    private View btnPendingPosts;
    private View btnPendingReports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupDateText();
        setupClickListeners();
    }

    private void initViews(View view) {
        tvCurrentDate = view.findViewById(R.id.tvCurrentDate);
        tvActiveCourtsCount = view.findViewById(R.id.tvActiveCourtsCount);
        tvNewUsersCount = view.findViewById(R.id.tvNewUsersCount);
        tvSuccessTxCount = view.findViewById(R.id.tvSuccessTxCount);
        tvRevenueTotal = view.findViewById(R.id.tvRevenueTotal);
        btnNotification = view.findViewById(R.id.btnNotification);

        btnPendingOwners = view.findViewById(R.id.btnPendingOwners);
        btnDuyetOwners = view.findViewById(R.id.btnDuyetOwners);
        btnPendingPosts = view.findViewById(R.id.btnPendingPosts);
        btnPendingReports = view.findViewById(R.id.btnPendingReports);
    }

    private void setupDateText() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("'Chủ nhật', dd/MM/yyyy", new Locale("vi", "VN"));
            String dateStr = sdf.format(new Date());
            tvCurrentDate.setText(dateStr);
        } catch (Exception e) {
            tvCurrentDate.setText("Chủ nhật, 09/11/2026");
        }
    }

    private void setupClickListeners() {
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v ->
                AdminNotificationHelper.showNotificationDialog(getContext())
            );
        }

        View.OnClickListener openAuditListener = v -> {
            Intent intent = new Intent(getActivity(), AdminOwnerAuditActivity.class);
            startActivity(intent);
        };

        if (btnPendingOwners != null) {
            btnPendingOwners.setOnClickListener(openAuditListener);
        }

        if (btnDuyetOwners != null) {
            btnDuyetOwners.setOnClickListener(openAuditListener);
        }

        if (btnPendingPosts != null) {
            btnPendingPosts.setOnClickListener(v -> {
                if (getActivity() instanceof AdminMainActivity) {
                    ((AdminMainActivity) getActivity()).switchToTab(3); // Community tab
                }
            });
        }

        if (btnPendingReports != null) {
            btnPendingReports.setOnClickListener(v ->
                AdminNotificationHelper.showNotificationDialog(getContext())
            );
        }
    }
}