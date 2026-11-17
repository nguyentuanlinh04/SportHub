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
import fpoly.duan1.sporthub_new.model.NotificationItem;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final Context context;
    private final List<NotificationItem> notificationList;
    private OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationItem item, int position);
    }

    public NotificationAdapter(Context context, List<NotificationItem> notificationList, OnNotificationClickListener listener) {
        this.context = context;
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());

        if (item.isRead()) {
            holder.itemView.setBackgroundResource(R.drawable.bg_notification_read);
            holder.vRedDot.setVisibility(View.GONE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_notification_unread);
            holder.vRedDot.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (!item.isRead()) {
                item.setRead(true);
                notifyItemChanged(position);
            }
            if (listener != null) {
                listener.onNotificationClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        View vRedDot;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNotificationTitle);
            tvContent = itemView.findViewById(R.id.tvNotificationContent);
            vRedDot = itemView.findViewById(R.id.vRedDot);
        }
    }
}
