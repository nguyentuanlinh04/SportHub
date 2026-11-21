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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.duan1.sporthub_new.R;
import fpoly.duan1.sporthub_new.model.BookingItem;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final Context context;
    private final List<BookingItem> bookingList;
    private OnBookingActionListener listener;

    public interface OnBookingActionListener {
        void onViewQRClick(BookingItem item);
        void onCancelClick(BookingItem item);
        void onReviewClick(BookingItem item);
        void onRebookClick(BookingItem item);
    }

    public BookingAdapter(Context context, List<BookingItem> bookingList, OnBookingActionListener listener) {
        this.context = context;
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingItem item = bookingList.get(position);

        holder.tvPitchName.setText(item.getPitchName());
        holder.tvSubTitle.setText(item.getSubTitle());

        if (item.getStatusCode() == 0) { // Sắp tới
            holder.llUpcomingSection.setVisibility(View.VISIBLE);
            holder.llCompletedSection.setVisibility(View.GONE);
            holder.llCancelledSection.setVisibility(View.GONE);

            holder.tvStatusBadge.setText(item.getStatusText());
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_level_badge_green);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#0A6A3A"));

            holder.tvDateText.setText(item.getDateText());
            holder.tvTimeText.setText(item.getTimeText());
            holder.tvDepositText.setText(item.getDepositText());

            // Format "Còn thu 595.000đ"
            String fee = item.getRemainingFee();
            String fullFeeText = "Còn thu " + fee;
            SpannableString spannableFee = new SpannableString(fullFeeText);
            int start = 8;
            int end = start + fee.length();

            spannableFee.setSpan(new ForegroundColorSpan(Color.parseColor("#6B7280")), 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableFee.setSpan(new ForegroundColorSpan(Color.parseColor("#111827")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableFee.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tvRemainingFee.setText(spannableFee);

            holder.btnViewQR.setOnClickListener(v -> {
                if (listener != null) listener.onViewQRClick(item);
                else Toast.makeText(context, "Hiển thị mã QR cho " + item.getPitchName(), Toast.LENGTH_SHORT).show();
            });

            holder.btnCancelBooking.setOnClickListener(v -> {
                if (listener != null) listener.onCancelClick(item);
                else Toast.makeText(context, "Hủy đơn đặt sân " + item.getSubTitle(), Toast.LENGTH_SHORT).show();
            });

        } else if (item.getStatusCode() == 1) { // Đã hoàn thành
            holder.llUpcomingSection.setVisibility(View.GONE);
            holder.llCompletedSection.setVisibility(View.VISIBLE);
            holder.llCancelledSection.setVisibility(View.GONE);

            holder.tvStatusBadge.setText(item.getStatusText());
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_level_badge_green);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#0A6A3A"));

            holder.tvCompletedDate.setText(item.getDateText());
            holder.tvCompletedTime.setText(item.getTimeText());
            holder.tvCompletedPrice.setText(item.getTotalPrice());

            if (!item.isReviewed()) {
                holder.llCompletedActionUnreviewed.setVisibility(View.VISIBLE);
                holder.rlCompletedActionReviewed.setVisibility(View.GONE);

                holder.btnReviewCompleted.setOnClickListener(v -> {
                    if (listener != null) listener.onReviewClick(item);
                    else Toast.makeText(context, "Đánh giá cho " + item.getPitchName(), Toast.LENGTH_SHORT).show();
                });

                holder.btnRebookUnreviewed.setOnClickListener(v -> {
                    if (listener != null) listener.onRebookClick(item);
                    else Toast.makeText(context, "Đặt lại sân " + item.getPitchName(), Toast.LENGTH_SHORT).show();
                });
            } else {
                holder.llCompletedActionUnreviewed.setVisibility(View.GONE);
                holder.rlCompletedActionReviewed.setVisibility(View.VISIBLE);

                holder.btnRebookReviewed.setOnClickListener(v -> {
                    if (listener != null) listener.onRebookClick(item);
                    else Toast.makeText(context, "Đặt lại sân " + item.getPitchName(), Toast.LENGTH_SHORT).show();
                });
            }

        } else if (item.getStatusCode() == 2) { // Đã hủy
            holder.llUpcomingSection.setVisibility(View.GONE);
            holder.llCompletedSection.setVisibility(View.GONE);
            holder.llCancelledSection.setVisibility(View.VISIBLE);

            holder.tvStatusBadge.setText("Đã hủy");
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_gray);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#4B5563"));

            holder.tvCancelledDate.setText(item.getDateText());
            holder.tvCancelledTime.setText(item.getTimeText());
            holder.tvCancelReason.setText(item.getCancelReason());
            holder.tvRefundStatus.setText(item.getRefundStatusText());

            if (item.isRefunded()) {
                holder.tvRefundStatus.setTextColor(ContextCompat.getColor(context, R.color.primary_green));
            } else {
                holder.tvRefundStatus.setTextColor(Color.parseColor("#C0392B"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return bookingList != null ? bookingList.size() : 0;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvPitchName, tvSubTitle, tvStatusBadge;

        // Upcoming section
        LinearLayout llUpcomingSection;
        TextView tvDateText, tvTimeText, tvDepositText, tvRemainingFee, btnViewQR, btnCancelBooking;

        // Completed section
        LinearLayout llCompletedSection, llCompletedActionUnreviewed;
        RelativeLayout rlCompletedActionReviewed;
        TextView tvCompletedDate, tvCompletedTime, tvCompletedPrice;
        TextView btnReviewCompleted, btnRebookUnreviewed, btnRebookReviewed;

        // Cancelled section
        LinearLayout llCancelledSection;
        TextView tvCancelledDate, tvCancelledTime, tvCancelReason, tvRefundStatus;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPitchName = itemView.findViewById(R.id.tvPitchName);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);

            llUpcomingSection = itemView.findViewById(R.id.llUpcomingSection);
            tvDateText = itemView.findViewById(R.id.tvDateText);
            tvTimeText = itemView.findViewById(R.id.tvTimeText);
            tvDepositText = itemView.findViewById(R.id.tvDepositText);
            tvRemainingFee = itemView.findViewById(R.id.tvRemainingFee);
            btnViewQR = itemView.findViewById(R.id.btnViewQR);
            btnCancelBooking = itemView.findViewById(R.id.btnCancelBooking);

            llCompletedSection = itemView.findViewById(R.id.llCompletedSection);
            tvCompletedDate = itemView.findViewById(R.id.tvCompletedDate);
            tvCompletedTime = itemView.findViewById(R.id.tvCompletedTime);
            tvCompletedPrice = itemView.findViewById(R.id.tvCompletedPrice);
            llCompletedActionUnreviewed = itemView.findViewById(R.id.llCompletedActionUnreviewed);
            rlCompletedActionReviewed = itemView.findViewById(R.id.rlCompletedActionReviewed);
            btnReviewCompleted = itemView.findViewById(R.id.btnReviewCompleted);
            btnRebookUnreviewed = itemView.findViewById(R.id.btnRebookUnreviewed);
            btnRebookReviewed = itemView.findViewById(R.id.btnRebookReviewed);

            llCancelledSection = itemView.findViewById(R.id.llCancelledSection);
            tvCancelledDate = itemView.findViewById(R.id.tvCancelledDate);
            tvCancelledTime = itemView.findViewById(R.id.tvCancelledTime);
            tvCancelReason = itemView.findViewById(R.id.tvCancelReason);
            tvRefundStatus = itemView.findViewById(R.id.tvRefundStatus);
        }
    }
}
