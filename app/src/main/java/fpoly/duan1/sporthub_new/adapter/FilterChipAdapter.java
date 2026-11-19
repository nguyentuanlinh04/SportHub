package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.FilterChip;

public class FilterChipAdapter extends RecyclerView.Adapter<FilterChipAdapter.ChipViewHolder> {

    private final Context context;
    private final List<FilterChip> chipList;
    private OnChipClickListener listener;

    public interface OnChipClickListener {
        void onChipClick(FilterChip chip, int position);
    }

    public FilterChipAdapter(Context context, List<FilterChip> chipList, OnChipClickListener listener) {
        this.context = context;
        this.chipList = chipList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_chip, parent, false);
        return new ChipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChipViewHolder holder, int position) {
        FilterChip chip = chipList.get(position);
        holder.tvChipTitle.setText(chip.getTitle());

        if (chip.isSelected()) {
            holder.tvChipTitle.setBackgroundResource(R.drawable.bg_chip_selected);
            holder.tvChipTitle.setTextColor(Color.WHITE);
        } else {
            holder.tvChipTitle.setBackgroundResource(R.drawable.bg_chip_unselected);
            holder.tvChipTitle.setTextColor(ContextCompat.getColor(context, R.color.text_title));
        }

        holder.itemView.setOnClickListener(v -> {
            if (chip.isSingleChoice()) {
                if (chip.isSelected()) {
                    chip.setSelected(false);
                } else {
                    for (FilterChip c : chipList) {
                        if (c.isSingleChoice()) {
                            c.setSelected(false);
                        }
                    }
                    chip.setSelected(true);
                }
                notifyDataSetChanged();
            } else {
                chip.setSelected(!chip.isSelected());
                notifyItemChanged(position);
            }

            if (listener != null) {
                listener.onChipClick(chip, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chipList != null ? chipList.size() : 0;
    }

    public static class ChipViewHolder extends RecyclerView.ViewHolder {
        TextView tvChipTitle;

        public ChipViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChipTitle = itemView.findViewById(R.id.tvChipTitle);
        }
    }
}
