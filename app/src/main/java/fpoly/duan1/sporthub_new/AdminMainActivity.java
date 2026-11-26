package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import fpoly.duan1.sporthub_new.R;

public class AdminMainActivity extends AppCompatActivity {

    private View tabDashboard;
    private View tabApproval;
    private View tabAccount;
    private View tabCommunity;
    private View tabSystem;

    private ImageView imgTabDashboard;
    private ImageView imgTabApproval;
    private ImageView imgTabAccount;
    private ImageView imgTabCommunity;
    private ImageView imgTabSystem;

    private TextView tvTabDashboard;
    private TextView tvTabApproval;
    private TextView tvTabAccount;
    private TextView tvTabCommunity;
    private TextView tvTabSystem;

    private final int COLOR_ACTIVE = Color.parseColor("#0E9F6E");
    private final int COLOR_INACTIVE = Color.parseColor("#9CA3AF");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        View mainRoot = findViewById(R.id.adminMainRoot);
        if (mainRoot != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainRoot, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }

        initViews();
        setupTabListeners();

        int targetTab = getIntent().getIntExtra("OPEN_TAB", 0);
        switchToTab(targetTab);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int targetTab = intent.getIntExtra("OPEN_TAB", 0);
        switchToTab(targetTab);
    }

    private void initViews() {
        tabDashboard = findViewById(R.id.tabDashboard);
        tabApproval = findViewById(R.id.tabApproval);
        tabAccount = findViewById(R.id.tabAccount);
        tabCommunity = findViewById(R.id.tabCommunity);
        tabSystem = findViewById(R.id.tabSystem);

        imgTabDashboard = findViewById(R.id.imgTabDashboard);
        imgTabApproval = findViewById(R.id.imgTabApproval);
        imgTabAccount = findViewById(R.id.imgTabAccount);
        imgTabCommunity = findViewById(R.id.imgTabCommunity);
        imgTabSystem = findViewById(R.id.imgTabSystem);

        tvTabDashboard = findViewById(R.id.tvTabDashboard);
        tvTabApproval = findViewById(R.id.tvTabApproval);
        tvTabAccount = findViewById(R.id.tvTabAccount);
        tvTabCommunity = findViewById(R.id.tvTabCommunity);
        tvTabSystem = findViewById(R.id.tvTabSystem);
    }

    private void setupTabListeners() {
        tabDashboard.setOnClickListener(v -> switchToTab(0));
        tabApproval.setOnClickListener(v -> switchToTab(1));
        tabAccount.setOnClickListener(v -> switchToTab(2));
        tabCommunity.setOnClickListener(v -> switchToTab(3));
        tabSystem.setOnClickListener(v -> switchToTab(4));
    }

    public void switchToTab(int position) {
        updateTabUI(position);

        Fragment selectedFragment;
        switch (position) {
            case 1:
                selectedFragment = new AdminApprovalFragment();
                break;
            case 2:
                selectedFragment = new AdminAccountFragment();
                break;
            case 3:
                selectedFragment = new AdminCommunityFragment();
                break;
            case 4:
                selectedFragment = new AdminSystemFragment();
                break;
            case 0:
            default:
                selectedFragment = new AdminDashboardFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.adminContainer, selectedFragment);
        ft.commit();
    }

    private void updateTabUI(int activePos) {
        // Reset all to inactive
        tvTabDashboard.setTextColor(COLOR_INACTIVE);
        tvTabApproval.setTextColor(COLOR_INACTIVE);
        tvTabAccount.setTextColor(COLOR_INACTIVE);
        tvTabCommunity.setTextColor(COLOR_INACTIVE);
        tvTabSystem.setTextColor(COLOR_INACTIVE);

        imgTabDashboard.setColorFilter(COLOR_INACTIVE);
        imgTabApproval.setColorFilter(COLOR_INACTIVE);
        imgTabAccount.setColorFilter(COLOR_INACTIVE);
        imgTabCommunity.setColorFilter(COLOR_INACTIVE);
        imgTabSystem.setColorFilter(COLOR_INACTIVE);

        // Highlight active
        switch (activePos) {
            case 0:
                tvTabDashboard.setTextColor(COLOR_ACTIVE);
                imgTabDashboard.setColorFilter(COLOR_ACTIVE);
                break;
            case 1:
                tvTabApproval.setTextColor(COLOR_ACTIVE);
                imgTabApproval.setColorFilter(COLOR_ACTIVE);
                break;
            case 2:
                tvTabAccount.setTextColor(COLOR_ACTIVE);
                imgTabAccount.setColorFilter(COLOR_ACTIVE);
                break;
            case 3:
                tvTabCommunity.setTextColor(COLOR_ACTIVE);
                imgTabCommunity.setColorFilter(COLOR_ACTIVE);
                break;
            case 4:
                tvTabSystem.setTextColor(COLOR_ACTIVE);
                imgTabSystem.setColorFilter(COLOR_ACTIVE);
                break;
        }
    }
}