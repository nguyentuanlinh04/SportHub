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
import fpoly.duan1.sporthub_new.model.DateItem;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private final Context context;
    private final List<DateItem> dateList;
    private OnDateClickListener listener;

    public interface OnDateClickListener {
        void onDateClick(DateItem item, int position);
    }

    public DateAdapter(Context context, List<DateItem> dateList, OnDateClickListener listener) {
        this.context = context;
        this.dateList = dateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        DateItem item = dateList.get(position);

        holder.tvDayOfWeek.setText(item.getDayOfWeek());
        holder.tvDayOfMonth.setText(item.getDayOfMonth());

        if (item.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.bg_chip_selected);
            holder.tvDayOfWeek.setTextColor(Color.WHITE);
            holder.tvDayOfMonth.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_chip_unselected);
            holder.tvDayOfWeek.setTextColor(ContextCompat.getColor(context, R.color.text_sub));
            holder.tvDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.text_title));
        }

        holder.itemView.setOnClickListener(v -> {
            for (DateItem d : dateList) {
                d.setSelected(false);
            }
            item.setSelected(true);
            notifyDataSetChanged();

            if (listener != null) {
                listener.onDateClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList != null ? dateList.size() : 0;
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfWeek;
        TextView tvDayOfMonth;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            tvDayOfMonth = itemView.findViewById(R.id.tvDayOfMonth);
        }
    }
}
