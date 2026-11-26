package fpoly.duan1.sporthub_new.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.duan1.sporthub_new.NotificationActivity;
import fpoly.duan1.sporthub_new.PitchDetailActivity;
import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.SelectLocationActivity;
import fpoly.duan1.sporthub_new.adapter.FilterChipAdapter;
import fpoly.duan1.sporthub_new.adapter.PitchAdapter;
import fpoly.duan1.sporthub_new.model.FilterChip;
import fpoly.duan1.sporthub_new.model.PitchComplex;

public class HomeFragment extends Fragment {

    private TextView tvLocation;
    private RecyclerView rvFilterChips;
    private RecyclerView rvPitches;
    private EditText edtSearch;
    private ImageButton btnFilter;

    private FilterChipAdapter filterChipAdapter;
    private PitchAdapter pitchAdapter;

    private final List<PitchComplex> fullPitchList = new ArrayList<>();
    private final List<PitchComplex> displayPitchList = new ArrayList<>();
    private final List<FilterChip> filterChips = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupFilterChips();
        setupPitchList();
        setupSearchAndFilter();
        loadSavedLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSavedLocation();
    }

    private void initViews(View view) {
        tvLocation = view.findViewById(R.id.tvLocation);
        rvFilterChips = view.findViewById(R.id.rvFilterChips);
        rvPitches = view.findViewById(R.id.rvPitches);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnFilter = view.findViewById(R.id.btnFilter);

        View llLocationHeader = view.findViewById(R.id.llLocationHeader);
        if (llLocationHeader != null) {
            llLocationHeader.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), SelectLocationActivity.class);
                startActivity(intent);
            });
        }

        view.findViewById(R.id.btnNotification).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NotificationActivity.class);
            startActivity(intent);
        });
    }

    private void loadSavedLocation() {
        if (tvLocation == null || getContext() == null) return;
        SharedPreferences pref = requireContext().getSharedPreferences("sporthub_users_db", Context.MODE_PRIVATE);
        String savedLoc = pref.getString(SelectLocationActivity.PREF_SELECTED_LOCATION, "");
        if (savedLoc == null || savedLoc.trim().isEmpty()) {
            tvLocation.setText(SelectLocationActivity.DEFAULT_LOCATION_HINT);
        } else {
            tvLocation.setText(savedLoc);
        }
    }

    private void setupFilterChips() {
        filterChips.clear();
        filterChips.add(new FilterChip("Sân 5", true, true));
        filterChips.add(new FilterChip("Sân 7", false, true));
        filterChips.add(new FilterChip("Sân 11", false, true));
        filterChips.add(new FilterChip("Có đèn chiếu sáng", false, false));

        filterChipAdapter = new FilterChipAdapter(requireContext(), filterChips, (chip, position) -> {
            filterPitchList();
        });

        rvFilterChips.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFilterChips.setAdapter(filterChipAdapter);
    }

    private void setupPitchList() {
        fullPitchList.clear();
        // Item 1
        fullPitchList.add(new PitchComplex(
                "p1",
                "Cụm sân Thắng Long",
                "0,8 km · 12 Trần Thái Tông",
                4.8,
                126,
                Arrays.asList("Sân 5", "Sân 7", "Đèn LED"),
                "250.000đ",
                "Còn trống",
                true
        ));

        // Item 2
        fullPitchList.add(new PitchComplex(
                "p2",
                "Sân bóng Mai Dịch",
                "2,3 km · 45 Hồ Tùng Mậu",
                4.5,
                84,
                Arrays.asList("Sân 5", "Sân 11"),
                "200.000đ",
                "Sắp hết giờ",
                false
        ));

        // Item 3
        fullPitchList.add(new PitchComplex(
                "p3",
                "Cụm sân Cầu Giấy Sport",
                "3,1 km · 101 Xuân Thủy",
                4.9,
                210,
                Arrays.asList("Sân 7", "Sân 11", "Đèn LED"),
                "300.000đ",
                "Còn trống",
                true
        ));

        displayPitchList.clear();
        displayPitchList.addAll(fullPitchList);

        pitchAdapter = new PitchAdapter(requireContext(), displayPitchList, pitch -> {
            Intent intent = new Intent(requireContext(), PitchDetailActivity.class);
            intent.putExtra("pitch_id", pitch.getId());
            intent.putExtra("pitch_name", pitch.getName());
            intent.putExtra("pitch_address", pitch.getDistanceAndAddress());
            intent.putExtra("pitch_rating", pitch.getRating());
            intent.putExtra("pitch_review_count", pitch.getReviewCount());
            intent.putExtra("pitch_price", pitch.getPriceText());
            startActivity(intent);
        });

        rvPitches.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPitches.setAdapter(pitchAdapter);
    }

    private void setupSearchAndFilter() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPitchList();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnFilter.setOnClickListener(v -> {
            FilterBottomSheetFragment bottomSheet = FilterBottomSheetFragment.newInstance();
            bottomSheet.show(getChildFragmentManager(), "FilterBottomSheet");
        });
    }

    private void filterPitchList() {
        String query = edtSearch.getText().toString().trim().toLowerCase();
        
        // Tìm chip loại sân được chọn (Sân 5, Sân 7, Sân 11)
        String selectedPitchType = null;
        boolean requireLighting = false;

        for (FilterChip chip : filterChips) {
            if (chip.isSelected()) {
                if (chip.isSingleChoice()) {
                    selectedPitchType = chip.getTitle();
                } else if ("Có đèn chiếu sáng".equals(chip.getTitle())) {
                    requireLighting = true;
                }
            }
        }

        displayPitchList.clear();

        for (PitchComplex pitch : fullPitchList) {
            boolean matchesSearch = query.isEmpty() ||
                    pitch.getName().toLowerCase().contains(query) ||
                    pitch.getDistanceAndAddress().toLowerCase().contains(query);

            boolean matchesPitchType = (selectedPitchType == null) ||
                    (pitch.getTags() != null && pitch.getTags().contains(selectedPitchType));

            boolean matchesLighting = !requireLighting ||
                    (pitch.getTags() != null && (pitch.getTags().contains("Đèn LED") || pitch.getTags().contains("Đèn chiếu sáng")));

            if (matchesSearch && matchesPitchType && matchesLighting) {
                displayPitchList.add(pitch);
            }
        }

        if (pitchAdapter != null) {
            pitchAdapter.notifyDataSetChanged();
        }
    }
}
