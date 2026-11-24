package fpoly.duan1.sporthub_new.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.duan1.sporthub_new.R;

public class AdminApprovalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn1 = view.findViewById(R.id.btnViewAudit1);
        if (btn1 != null) {
            btn1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminOwnerAuditActivity.class);
                startActivity(intent);
            });
        }

        Button btn2 = view.findViewById(R.id.btnViewAudit2);
        if (btn2 != null) {
            btn2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminOwnerAuditActivity.class);
                startActivity(intent);
            });
        }

        Button btn3 = view.findViewById(R.id.btnViewAudit3);
        if (btn3 != null) {
            btn3.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AdminOwnerAuditActivity.class);
                startActivity(intent);
            });
        }
    }
}