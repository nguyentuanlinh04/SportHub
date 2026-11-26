package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.adapter.PitchAdapter;
import fpoly.duan1.sporthub_new.model.PitchComplex;
import fpoly.duan1.sporthub_new.utils.FavoriteManager;

public class FavoritePitchesActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvFavoritePitches;
    private LinearLayout llEmptyFavorites;

    private PitchAdapter adapter;
    private final List<PitchComplex> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_pitches);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainFavoritePitches), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavoriteData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        rvFavoritePitches = findViewById(R.id.rvFavoritePitches);
        llEmptyFavorites = findViewById(R.id.llEmptyFavorites);
    }

    private void setupRecyclerView() {
        adapter = new PitchAdapter(this, favoriteList, pitch -> {
            Intent intent = new Intent(FavoritePitchesActivity.this, PitchDetailActivity.class);
            intent.putExtra("pitch_name", pitch.getName());
            intent.putExtra("pitch_address", pitch.getDistanceAndAddress());
            intent.putExtra("pitch_rating", pitch.getRating());
            intent.putExtra("pitch_review_count", pitch.getReviewCount());
            intent.putExtra("pitch_price", pitch.getPriceText());
            startActivity(intent);
        });

        rvFavoritePitches.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritePitches.setAdapter(adapter);
    }

    private void loadFavoriteData() {
        favoriteList.clear();
        favoriteList.addAll(FavoriteManager.getInstance().getFavoritePitches(this));

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (favoriteList.isEmpty()) {
            rvFavoritePitches.setVisibility(View.GONE);
            llEmptyFavorites.setVisibility(View.VISIBLE);
        } else {
            rvFavoritePitches.setVisibility(View.VISIBLE);
            llEmptyFavorites.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}
