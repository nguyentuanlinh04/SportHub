package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.LoginActivity;
import fpoly.duan1.sporthub_new.R;

public class AdminSystemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_system, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View btnCategory = view.findViewById(R.id.btnMenuCategory);
        if (btnCategory != null) {
            btnCategory.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminCategoryActivity.class);
                startActivity(intent);
            });
        }

        View btnFee = view.findViewById(R.id.btnMenuFee);
        if (btnFee != null) {
            btnFee.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminFeeConfigActivity.class);
                startActivity(intent);
            });
        }

        View btnGrowth = view.findViewById(R.id.btnMenuGrowth);
        if (btnGrowth != null) {
            btnGrowth.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminGrowthReportActivity.class);
                startActivity(intent);
            });
        }

        Button btnLogout = view.findViewById(R.id.btnAdminLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Đã đăng xuất khỏi tài khoản Admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        }
    }
}