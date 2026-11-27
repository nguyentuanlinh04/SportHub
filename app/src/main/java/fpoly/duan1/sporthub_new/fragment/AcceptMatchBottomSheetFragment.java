package fpoly.duan1.sporthub_new.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import fpoly.duan1.sporthub_new.MatchConfirmedActivity;
import fpoly.duan1.sporthub_new.R;

public class AcceptMatchBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_OPPONENT = "arg_opponent_name";

    private TextView tvBottomSheetSubTitle;
    private TextView tvOpponentTeamName;
    private TextView btnConfirmAccept, btnLater;

    private String opponentName = "FC Cầu Giấy United";

    public static AcceptMatchBottomSheetFragment newInstance(String opponentName) {
        AcceptMatchBottomSheetFragment fragment = new AcceptMatchBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OPPONENT, opponentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accept_match_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String name = getArguments().getString(ARG_OPPONENT);
            if (name != null && !name.isEmpty()) {
                opponentName = name;
            }
        }

        initViews(view);
        setupListeners();
    }

    private void initViews(View view) {
        tvBottomSheetSubTitle = view.findViewById(R.id.tvBottomSheetSubTitle);
        tvOpponentTeamName = view.findViewById(R.id.tvOpponentTeamName);
        btnConfirmAccept = view.findViewById(R.id.btnConfirmAccept);
        btnLater = view.findViewById(R.id.btnLater);

        if (tvOpponentTeamName != null) {
            tvOpponentTeamName.setText(opponentName);
        }

        if (tvBottomSheetSubTitle != null) {
            tvBottomSheetSubTitle.setText("Kèo sẽ được chốt ngay, " + opponentName + " nhận thông báo và không cần trao đổi thêm.");
        }
    }

    private void setupListeners() {
        btnLater.setOnClickListener(v -> dismiss());

        btnConfirmAccept.setOnClickListener(v -> {
            dismiss();
            Intent intent = new Intent(requireContext(), MatchConfirmedActivity.class);
            intent.putExtra("opponent_name", opponentName);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }
}
