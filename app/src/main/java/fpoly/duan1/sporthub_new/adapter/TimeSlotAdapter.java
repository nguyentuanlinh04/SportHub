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
import fpoly.duan1.sporthub_new.model.TimeSlotItem;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private final Context context;
    private final List<TimeSlotItem> slotList;
    private OnTimeSlotClickListener listener;

    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(TimeSlotItem item, int position);
    }

    public TimeSlotAdapter(Context context, List<TimeSlotItem> slotList, OnTimeSlotClickListener listener) {
        this.context = context;
        this.slotList = slotList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        TimeSlotItem item = slotList.get(position);

        holder.tvSlotTime.setText(item.getTimeStr());

        if (item.getStatus() == TimeSlotItem.STATUS_BOOKED) {
            holder.itemView.setBackgroundResource(R.drawable.bg_time_slot_booked);
            holder.tvSlotTime.setTextColor(Color.parseColor("#806B7280"));
            holder.tvSlotPrice.setText("Đã đặt");
            holder.tvSlotPrice.setTextColor(Color.parseColor("#806B7280"));
            holder.itemView.setClickable(false);
        } else if (item.getStatus() == TimeSlotItem.STATUS_SELECTED) {
            holder.itemView.setBackgroundResource(R.drawable.bg_time_slot_selected);
            holder.tvSlotTime.setTextColor(Color.WHITE);
            holder.tvSlotPrice.setText(item.getPriceStr());
            holder.tvSlotPrice.setTextColor(Color.WHITE);
            holder.itemView.setClickable(true);
        } else { // Available
            holder.itemView.setBackgroundResource(R.drawable.bg_time_slot_available);
            holder.tvSlotTime.setTextColor(ContextCompat.getColor(context, R.color.text_title));
            holder.tvSlotPrice.setText(item.getPriceStr());
            holder.tvSlotPrice.setTextColor(ContextCompat.getColor(context, R.color.text_sub));
            holder.itemView.setClickable(true);
        }

        holder.itemView.setOnClickListener(v -> {
            if (item.getStatus() == TimeSlotItem.STATUS_BOOKED) return;

            if (item.getStatus() == TimeSlotItem.STATUS_SELECTED) {
                item.setStatus(TimeSlotItem.STATUS_AVAILABLE);
            } else {
                item.setStatus(TimeSlotItem.STATUS_SELECTED);
            }
            notifyItemChanged(position);

            if (listener != null) {
                listener.onTimeSlotClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slotList != null ? slotList.size() : 0;
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        TextView tvSlotTime;
        TextView tvSlotPrice;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlotTime = itemView.findViewById(R.id.tvSlotTime);
            tvSlotPrice = itemView.findViewById(R.id.tvSlotPrice);
        }
    }
}
