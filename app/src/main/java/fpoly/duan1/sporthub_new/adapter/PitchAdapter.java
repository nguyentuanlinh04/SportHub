package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.PitchComplex;

public class PitchAdapter extends RecyclerView.Adapter<PitchAdapter.PitchViewHolder> {

    private final Context context;
    private final List<PitchComplex> pitchList;
    private OnPitchClickListener listener;

    public interface OnPitchClickListener {
        void onPitchClick(PitchComplex pitch);
    }

    public PitchAdapter(Context context, List<PitchComplex> pitchList, OnPitchClickListener listener) {
        this.context = context;
        this.pitchList = pitchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PitchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch, parent, false);
        return new PitchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchViewHolder holder, int position) {
        PitchComplex pitch = pitchList.get(position);

        holder.tvPitchName.setText(pitch.getName());
        holder.tvDistanceAddress.setText(pitch.getDistanceAndAddress());
        holder.tvRating.setText(String.format("%s (%d đánh giá)", pitch.getRating(), pitch.getReviewCount()));

        // Status badge
        holder.tvStatusBadge.setText(pitch.getStatusText());
        if (pitch.isAvailable()) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_available);
            holder.tvStatusBadge.setTextColor(ContextCompat.getColor(context, R.color.badge_available_text));
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_busy);
            holder.tvStatusBadge.setTextColor(ContextCompat.getColor(context, R.color.badge_busy_text));
        }

        // Tags
        holder.llTagsContainer.removeAllViews();
        if (pitch.getTags() != null) {
            for (String tag : pitch.getTags()) {
                TextView tvTag = new TextView(context);
                tvTag.setText(tag);
                tvTag.setTextSize(11);
                tvTag.setTextColor(ContextCompat.getColor(context, R.color.tag_text));
                tvTag.setBackgroundResource(R.drawable.bg_tag_pill);
                tvTag.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMarginEnd(dpToPx(6));
                tvTag.setLayoutParams(params);

                holder.llTagsContainer.addView(tvTag);
            }
        }

        // Price String format: "từ 250.000đ / giờ"
        String price = pitch.getPriceText();
        String fullText = "từ " + price + " / giờ";
        SpannableString spannable = new SpannableString(fullText);
        int start = 3;
        int end = start + price.length();

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6B7280")), 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#0A8B4D")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6B7280")), end, fullText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvPriceText.setText(spannable);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPitchClick(pitch);
            }
        });
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public int getItemCount() {
        return pitchList != null ? pitchList.size() : 0;
    }

    public static class PitchViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatusBadge;
        TextView tvPitchName;
        TextView tvDistanceAddress;
        TextView tvRating;
        LinearLayout llTagsContainer;
        TextView tvPriceText;

        public PitchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvPitchName = itemView.findViewById(R.id.tvPitchName);
            tvDistanceAddress = itemView.findViewById(R.id.tvDistanceAddress);
            tvRating = itemView.findViewById(R.id.tvRating);
            llTagsContainer = itemView.findViewById(R.id.llTagsContainer);
            tvPriceText = itemView.findViewById(R.id.tvPriceText);
        }
    }
}
