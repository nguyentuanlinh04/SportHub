package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fpoly.duan1.sporthub_new.fragment.BookingScheduleFragment;
import fpoly.duan1.sporthub_new.fragment.HomeFragment;
import fpoly.duan1.sporthub_new.fragment.MatchmakingFragment;
import fpoly.duan1.sporthub_new.fragment.ProfileFragment;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout navHome, navMatch, navCalendar, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMenu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        initViews();
        setupBottomNavigation();

        int targetTab = getIntent().getIntExtra("OPEN_TAB", 0);

        // Load target tab or HomeFragment by default
        if (savedInstanceState == null) {
            selectBottomTab(targetTab);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int targetTab = intent.getIntExtra("OPEN_TAB", -1);
        if (targetTab != -1) {
            selectBottomTab(targetTab);
        }
    }

    private void initViews() {
        navHome = findViewById(R.id.navHome);
        navMatch = findViewById(R.id.navMatch);
        navCalendar = findViewById(R.id.navCalendar);
        navProfile = findViewById(R.id.navProfile);
    }

    private void setupBottomNavigation() {
        navHome.setOnClickListener(v -> selectBottomTab(0));
        navMatch.setOnClickListener(v -> selectBottomTab(1));
        navCalendar.setOnClickListener(v -> selectBottomTab(2));
        navProfile.setOnClickListener(v -> selectBottomTab(3));
    }

    public void selectBottomTab(int index) {
        updateTabUI(navHome, R.drawable.ic_nav_home, index == 0);
        updateTabUI(navMatch, R.drawable.ic_nav_match, index == 1);
        updateTabUI(navCalendar, R.drawable.ic_nav_calendar, index == 2);
        updateTabUI(navProfile, R.drawable.ic_nav_profile, index == 3);

        switch (index) {
            case 0:
                loadFragment(new HomeFragment());
                break;
            case 1:
                loadFragment(new MatchmakingFragment());
                break;
            case 2:
                loadFragment(new BookingScheduleFragment());
                break;
            case 3:
                loadFragment(new ProfileFragment());
                break;
        }
    }

    public void selectBottomTabExternal(int index) {
        selectBottomTab(index);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void updateTabUI(LinearLayout tabLayout, int iconRes, boolean isSelected) {
        ImageView iconView = (ImageView) tabLayout.getChildAt(0);
        TextView textView = (TextView) tabLayout.getChildAt(1);

        iconView.setImageResource(iconRes);
        int color = ContextCompat.getColor(this, isSelected ? R.color.primary_green : R.color.nav_inactive);
        iconView.setColorFilter(color);
        textView.setTextColor(color);
        textView.setTypeface(null, isSelected ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
    }
}
