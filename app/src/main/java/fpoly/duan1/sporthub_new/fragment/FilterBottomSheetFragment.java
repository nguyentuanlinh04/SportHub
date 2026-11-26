package fpoly.duan1.sporthub_new.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import fpoly.duan1.sporthub_new.R;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    // Radius
    private TextView tvRadius1km, tvRadius3km, tvRadius5km, tvRadius10km;

    // Pitch Size
    private TextView tvSizeSan5, tvSizeSan7, tvSizeSan11;

    // Turf Quality
    private TextView tvTurfStandard, tvTurfHigh;

    // Lighting
    private SwitchCompat switchLighting;

    // Price Range
    private TextView tvPriceUnder200, tvPrice200to300, tvPrice300to400, tvPriceOver400;

    // Amenities custom views
    private View llParking, llDressingRoom, llCanteen;
    private ImageView ivCheckParking, ivCheckDressingRoom, ivCheckCanteen;

    private boolean isParkingChecked = false;
    private boolean isDressingRoomChecked = false;
    private boolean isCanteenChecked = false;

    // Action buttons
    private TextView btnResetFilter, btnApplyFilter;

    public static FilterBottomSheetFragment newInstance() {
        return new FilterBottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRadiusSelection();
        setupSizeSelection();
        setupTurfSelection();
        setupPriceSelection();
        setupAmenityCheckboxes();
        setupActions();
    }

    private void initViews(View view) {
        btnResetFilter = view.findViewById(R.id.btnResetFilter);
        btnApplyFilter = view.findViewById(R.id.btnApplyFilter);

        tvRadius1km = view.findViewById(R.id.tvRadius1km);
        tvRadius3km = view.findViewById(R.id.tvRadius3km);
        tvRadius5km = view.findViewById(R.id.tvRadius5km);
        tvRadius10km = view.findViewById(R.id.tvRadius10km);

        tvSizeSan5 = view.findViewById(R.id.tvSizeSan5);
        tvSizeSan7 = view.findViewById(R.id.tvSizeSan7);
        tvSizeSan11 = view.findViewById(R.id.tvSizeSan11);

        tvTurfStandard = view.findViewById(R.id.tvTurfStandard);
        tvTurfHigh = view.findViewById(R.id.tvTurfHigh);

        switchLighting = view.findViewById(R.id.switchLighting);

        tvPriceUnder200 = view.findViewById(R.id.tvPriceUnder200);
        tvPrice200to300 = view.findViewById(R.id.tvPrice200to300);
        tvPrice300to400 = view.findViewById(R.id.tvPrice300to400);
        tvPriceOver400 = view.findViewById(R.id.tvPriceOver400);

        llParking = view.findViewById(R.id.llParking);
        llDressingRoom = view.findViewById(R.id.llDressingRoom);
        llCanteen = view.findViewById(R.id.llCanteen);

        ivCheckParking = view.findViewById(R.id.ivCheckParking);
        ivCheckDressingRoom = view.findViewById(R.id.ivCheckDressingRoom);
        ivCheckCanteen = view.findViewById(R.id.ivCheckCanteen);
    }

    private void setupRadiusSelection() {
        tvRadius1km.setOnClickListener(v -> selectRadius(0));
        tvRadius3km.setOnClickListener(v -> selectRadius(1));
        tvRadius5km.setOnClickListener(v -> selectRadius(2));
        tvRadius10km.setOnClickListener(v -> selectRadius(3));
    }

    private void selectRadius(int index) {
        updateTabOption(tvRadius1km, index == 0);
        updateTabOption(tvRadius3km, index == 1);
        updateTabOption(tvRadius5km, index == 2);
        updateTabOption(tvRadius10km, index == 3);
    }

    private void setupSizeSelection() {
        tvSizeSan5.setOnClickListener(v -> selectSize(0));
        tvSizeSan7.setOnClickListener(v -> selectSize(1));
        tvSizeSan11.setOnClickListener(v -> selectSize(2));
    }

    private void selectSize(int index) {
        updateTabOption(tvSizeSan5, index == 0);
        updateTabOption(tvSizeSan7, index == 1);
        updateTabOption(tvSizeSan11, index == 2);
    }

    private void setupTurfSelection() {
        tvTurfStandard.setOnClickListener(v -> selectTurf(0));
        tvTurfHigh.setOnClickListener(v -> selectTurf(1));
    }

    private void selectTurf(int index) {
        updateTabOption(tvTurfStandard, index == 0);
        updateTabOption(tvTurfHigh, index == 1);
    }

    private void setupPriceSelection() {
        tvPriceUnder200.setOnClickListener(v -> selectPrice(0));
        tvPrice200to300.setOnClickListener(v -> selectPrice(1));
        tvPrice300to400.setOnClickListener(v -> selectPrice(2));
        tvPriceOver400.setOnClickListener(v -> selectPrice(3));
    }

    private void selectPrice(int index) {
        updateTabOption(tvPriceUnder200, index == 0);
        updateTabOption(tvPrice200to300, index == 1);
        updateTabOption(tvPrice300to400, index == 2);
        updateTabOption(tvPriceOver400, index == 3);
    }

    private void setupAmenityCheckboxes() {
        llParking.setOnClickListener(v -> {
            isParkingChecked = !isParkingChecked;
            updateAmenityUI(llParking, ivCheckParking, isParkingChecked);
        });

        llDressingRoom.setOnClickListener(v -> {
            isDressingRoomChecked = !isDressingRoomChecked;
            updateAmenityUI(llDressingRoom, ivCheckDressingRoom, isDressingRoomChecked);
        });

        llCanteen.setOnClickListener(v -> {
            isCanteenChecked = !isCanteenChecked;
            updateAmenityUI(llCanteen, ivCheckCanteen, isCanteenChecked);
        });
    }

    private void updateAmenityUI(View container, ImageView checkIcon, boolean isChecked) {
        container.setBackgroundResource(isChecked ? R.drawable.bg_checkbox_checked : R.drawable.bg_checkbox_unchecked);
        checkIcon.setImageResource(isChecked ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_unchecked);
    }

    private void setupActions() {
        btnResetFilter.setOnClickListener(v -> {
            selectRadius(2);
            selectSize(0);
            selectTurf(1);
            selectPrice(1);
            switchLighting.setChecked(true);

            isParkingChecked = false;
            isDressingRoomChecked = false;
            isCanteenChecked = false;

            updateAmenityUI(llParking, ivCheckParking, false);
            updateAmenityUI(llDressingRoom, ivCheckDressingRoom, false);
            updateAmenityUI(llCanteen, ivCheckCanteen, false);

            Toast.makeText(requireContext(), "Đã lập lại mặc định bộ lọc", Toast.LENGTH_SHORT).show();
        });

        btnApplyFilter.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Đã áp dụng bộ lọc thành công!", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    private void updateTabOption(TextView textView, boolean isSelected) {
        if (isSelected) {
            textView.setBackgroundResource(R.drawable.bg_tab_selected);
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_title));
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_sub));
            textView.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }
}
