package fpoly.duan1.sporthub_new.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.FavoritePitchesActivity;
import fpoly.duan1.sporthub_new.LoginActivity;
import fpoly.duan1.sporthub_new.MenuActivity;
import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.utils.FavoriteManager;

public class ProfileFragment extends Fragment {

    private TextView tvProfileAvatar, tvUserName, tvUserPhone, tvUserAddress, tvFavoriteCount;
    private TextView btnEditProfile;
    private View llFavoritePitches, llMyBookings, llMyReviews, llChangePassword, llLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFavoriteCount();
    }

    private void initViews(View view) {
        tvProfileAvatar = view.findViewById(R.id.tvProfileAvatar);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserPhone = view.findViewById(R.id.tvUserPhone);
        tvUserAddress = view.findViewById(R.id.tvUserAddress);
        tvFavoriteCount = view.findViewById(R.id.tvFavoriteCount);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        llFavoritePitches = view.findViewById(R.id.llFavoritePitches);
        llMyBookings = view.findViewById(R.id.llMyBookings);
        llMyReviews = view.findViewById(R.id.llMyReviews);
        llChangePassword = view.findViewById(R.id.llChangePassword);
        llLogout = view.findViewById(R.id.llLogout);
    }

    private void updateFavoriteCount() {
        if (tvFavoriteCount != null && isAdded()) {
            int count = FavoriteManager.getInstance().getFavoriteCount(requireContext());
            tvFavoriteCount.setText(count + " sân đã lưu");
        }
    }

    private void setupListeners() {
        btnEditProfile.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Mở màn hình Sửa hồ sơ", Toast.LENGTH_SHORT).show()
        );

        llFavoritePitches.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), FavoritePitchesActivity.class))
        );

        llMyBookings.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).selectBottomTabExternal(2);
            }
        });

        llMyReviews.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Mở màn hình Đánh giá của tôi", Toast.LENGTH_SHORT).show()
        );

        llChangePassword.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Mở màn hình Đổi mật khẩu", Toast.LENGTH_SHORT).show()
        );

        llLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản không?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    Toast.makeText(requireContext(), "Đã đăng xuất tài khoản!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
