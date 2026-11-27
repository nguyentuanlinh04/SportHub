package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.MatchTeam;

public class MatchTeamAdapter extends RecyclerView.Adapter<MatchTeamAdapter.MatchViewHolder> {

    private final Context context;
    private final List<MatchTeam> teamList;
    private OnMatchActionListener listener;

    public interface OnMatchActionListener {
        void onRequestClick(MatchTeam team);
    }

    public MatchTeamAdapter(Context context, List<MatchTeam> teamList, OnMatchActionListener listener) {
        this.context = context;
        this.teamList = teamList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match_team, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        MatchTeam team = teamList.get(position);

        holder.tvTeamAvatar.setText(team.getAvatarText());
        if (team.getAvatarBgRes() != 0) {
            holder.tvTeamAvatar.setBackgroundResource(team.getAvatarBgRes());
        }

        holder.tvTeamName.setText(team.getTeamName());
        holder.tvCaptainDistance.setText(String.format("Đội trưởng: %s · %s", team.getCaptainName(), team.getDistance()));

        holder.tvLevelBadge.setText(team.getLevelText());
        if (team.getLevelBgRes() != 0) {
            holder.tvLevelBadge.setBackgroundResource(team.getLevelBgRes());
        }
        if (team.getLevelTextColor() != 0) {
            holder.tvLevelBadge.setTextColor(team.getLevelTextColor());
        }

        holder.tvTime.setText(team.getTimeText());
        holder.tvPitchType.setText(team.getPitchType());
        holder.tvSplitFee.setText(team.getSplitFee());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRequestClick(team);
            }
        });

        holder.btnSendRequest.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRequestClick(team);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamList != null ? teamList.size() : 0;
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamAvatar;
        TextView tvTeamName;
        TextView tvCaptainDistance;
        TextView tvLevelBadge;
        TextView tvTime;
        TextView tvPitchType;
        TextView tvSplitFee;
        TextView btnSendRequest;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamAvatar = itemView.findViewById(R.id.tvTeamAvatar);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            tvCaptainDistance = itemView.findViewById(R.id.tvCaptainDistance);
            tvLevelBadge = itemView.findViewById(R.id.tvLevelBadge);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPitchType = itemView.findViewById(R.id.tvPitchType);
            tvSplitFee = itemView.findViewById(R.id.tvSplitFee);
            btnSendRequest = itemView.findViewById(R.id.btnSendRequest);
        }
    }
}
