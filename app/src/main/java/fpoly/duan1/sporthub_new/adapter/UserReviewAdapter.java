package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.UserReviewItem;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private final List<UserReviewItem> reviewList;

    public UserReviewAdapter(Context context, List<UserReviewItem> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        UserReviewItem item = reviewList.get(position);

        holder.tvPitchName.setText(item.getPitchName());
        holder.tvMatchDetails.setText(item.getMatchDetails());
        holder.tvScoreText.setText(item.getScoreText());
        holder.tvTagBadge.setText(item.getTagText());
        holder.tvComment.setText(item.getCommentText());
        holder.tvCreatedDate.setText(item.getCreatedDate());

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < item.getStarCount(); i++) {
            stars.append("★ ");
        }
        for (int i = item.getStarCount(); i < 5; i++) {
            stars.append("☆ ");
        }
        holder.tvStarRating.setText(stars.toString().trim());
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvPitchName, tvMatchDetails, tvStarRating, tvScoreText, tvTagBadge, tvComment, tvCreatedDate;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPitchName = itemView.findViewById(R.id.tvPitchName);
            tvMatchDetails = itemView.findViewById(R.id.tvMatchDetails);
            tvStarRating = itemView.findViewById(R.id.tvStarRating);
            tvScoreText = itemView.findViewById(R.id.tvScoreText);
            tvTagBadge = itemView.findViewById(R.id.tvTagBadge);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
        }
    }
}
