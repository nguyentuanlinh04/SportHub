package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.R;

public class AdminAccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ACTIVE STATUS USERS -> Open Role & Permission Screen (AdminRolePermissionActivity)
        View userTuan = view.findViewById(R.id.btnUserTuan);
        if (userTuan != null) {
            userTuan.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminRolePermissionActivity.class);
                intent.putExtra("USER_NAME", "Anh Tuấn");
                intent.putExtra("USER_PHONE", "+84 903 456 789");
                startActivity(intent);
            });
        }

        View userHoa = view.findViewById(R.id.btnUserHoa);
        if (userHoa != null) {
            userHoa.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminRolePermissionActivity.class);
                intent.putExtra("USER_NAME", "Chị Hoa");
                intent.putExtra("USER_PHONE", "+84 977 222 333");
                startActivity(intent);
            });
        }

        // LOCKED / REPORTED USERS -> Open User Lock Detail Flow (AdminUserDetailActivity)
        View userQuan = view.findViewById(R.id.btnUserQuan);
        if (userQuan != null) {
            userQuan.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminUserDetailActivity.class);
                startActivity(intent);
            });
        }

        View userKhang = view.findViewById(R.id.btnUserKhang);
        if (userKhang != null) {
            userKhang.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminUserDetailActivity.class);
                startActivity(intent);
            });
        }
    }
}