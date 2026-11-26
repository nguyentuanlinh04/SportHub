package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.AddonServiceItem;

public class AddonServiceAdapter extends RecyclerView.Adapter<AddonServiceAdapter.AddonViewHolder> {

    private final Context context;
    private final List<AddonServiceItem> addonList;
    private OnAddonQuantityChangeListener listener;

    public interface OnAddonQuantityChangeListener {
        void onQuantityChange(AddonServiceItem item, int position);
    }

    public AddonServiceAdapter(Context context, List<AddonServiceItem> addonList, OnAddonQuantityChangeListener listener) {
        this.context = context;
        this.addonList = addonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_addon_service, parent, false);
        return new AddonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddonViewHolder holder, int position) {
        AddonServiceItem item = addonList.get(position);

        holder.tvAddonTitle.setText(item.getTitle());
        holder.tvAddonUnitPrice.setText(item.getUnitPriceText());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                if (listener != null) {
                    listener.onQuantityChange(item, position);
                }
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            if (listener != null) {
                listener.onQuantityChange(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addonList != null ? addonList.size() : 0;
    }

    public static class AddonViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddonTitle;
        TextView tvAddonUnitPrice;
        TextView tvQuantity;
        ImageButton btnMinus;
        ImageButton btnPlus;

        public AddonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddonTitle = itemView.findViewById(R.id.tvAddonTitle);
            tvAddonUnitPrice = itemView.findViewById(R.id.tvAddonUnitPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}
