package fpoly.duan1.sporthub_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.DistrictItem;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {

    private final Context context;
    private final List<DistrictItem> districtList;
    private OnDistrictClickListener listener;

    public interface OnDistrictClickListener {
        void onDistrictClick(DistrictItem item, int position);
    }

    public DistrictAdapter(Context context, List<DistrictItem> districtList, OnDistrictClickListener listener) {
        this.context = context;
        this.districtList = districtList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_district, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        DistrictItem item = districtList.get(position);

        holder.tvDistrictName.setText(item.getName());

        if (item.isSelected()) {
            holder.ivCheckmark.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheckmark.setVisibility(View.GONE);
        }

        // Hide divider for last item
        if (position == getItemCount() - 1) {
            holder.vDivider.setVisibility(View.GONE);
        } else {
            holder.vDivider.setVisibility(View.VISIBLE);
        }

        View.OnClickListener clickListener = v -> {
            boolean wasSelected = item.isSelected();
            for (DistrictItem d : districtList) {
                d.setSelected(false);
            }
            if (!wasSelected) {
                item.setSelected(true);
            }
            notifyDataSetChanged();

            if (listener != null) {
                listener.onDistrictClick(item, position);
            }
        };

        holder.itemView.setOnClickListener(clickListener);
        if (holder.rlDistrictContainer != null) {
            holder.rlDistrictContainer.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return districtList != null ? districtList.size() : 0;
    }

    public static class DistrictViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistrictName;
        ImageView ivCheckmark;
        View rlDistrictContainer;
        View vDivider;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDistrictName = itemView.findViewById(R.id.tvDistrictName);
            ivCheckmark = itemView.findViewById(R.id.ivCheckmark);
            rlDistrictContainer = itemView.findViewById(R.id.rlDistrictContainer);
            vDivider = itemView.findViewById(R.id.vDivider);
        }
    }
}
