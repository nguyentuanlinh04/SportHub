package fpoly.duan1.sporthub_new.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import fpoly.duan1.sporthub_new.R;

public class FacilityDialogHelper {

    public interface OnFacilitySelectedListener {
        void onFacilitySelected(String facilityName);
    }

    public static void show(Context context, OnFacilitySelectedListener listener) {
        if (context == null) return;

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View sheetView = LayoutInflater.from(context).inflate(R.layout.dialog_select_facility, null);
        dialog.setContentView(sheetView);

        // Make background transparent for rounded corners
        if (sheetView.getParent() != null) {
            ((View) sheetView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        }

        View btnClose = sheetView.findViewById(R.id.btnClose);
        MaterialCardView cardThangLong = sheetView.findViewById(R.id.card_facility_thang_long);
        MaterialCardView cardMaiDich = sheetView.findViewById(R.id.card_facility_mai_dich);
        ImageView ivCheckThangLong = sheetView.findViewById(R.id.iv_check_thang_long);
        ImageView ivCheckMaiDich = sheetView.findViewById(R.id.iv_check_mai_dich);
        MaterialButton btnKeepFacility = sheetView.findViewById(R.id.btnKeepFacility);

        final String[] selected = {StaffDataStore.getCurrentFacility()};

        final float density = context.getResources().getDisplayMetrics().density;
        final int strokeSelected = (int) (1.5f * density);
        final int strokeUnselected = (int) density;

        Runnable updateUI = () -> {
            boolean isThangLong = "Cụm sân Thăng Long".equals(selected[0]);
            if (isThangLong) {
                if (cardThangLong != null) {
                    cardThangLong.setStrokeColor(Color.parseColor("#0C4A2B"));
                    cardThangLong.setStrokeWidth(strokeSelected);
                }
                if (cardMaiDich != null) {
                    cardMaiDich.setStrokeColor(Color.parseColor("#E5E7EB"));
                    cardMaiDich.setStrokeWidth(strokeUnselected);
                }
                if (ivCheckThangLong != null) ivCheckThangLong.setImageResource(R.drawable.ic_check_circle_green);
                if (ivCheckMaiDich != null) ivCheckMaiDich.setImageResource(R.drawable.ic_radio_unselected);
                if (btnKeepFacility != null) btnKeepFacility.setText("Giữ cụm sân Thăng Long");
            } else {
                if (cardThangLong != null) {
                    cardThangLong.setStrokeColor(Color.parseColor("#E5E7EB"));
                    cardThangLong.setStrokeWidth(strokeUnselected);
                }
                if (cardMaiDich != null) {
                    cardMaiDich.setStrokeColor(Color.parseColor("#0C4A2B"));
                    cardMaiDich.setStrokeWidth(strokeSelected);
                }
                if (ivCheckThangLong != null) ivCheckThangLong.setImageResource(R.drawable.ic_radio_unselected);
                if (ivCheckMaiDich != null) ivCheckMaiDich.setImageResource(R.drawable.ic_check_circle_green);
                if (btnKeepFacility != null) btnKeepFacility.setText("Chuyển sang Sân bóng Mai Dịch");
            }
        };

        updateUI.run();

        if (cardThangLong != null) {
            cardThangLong.setOnClickListener(v -> {
                selected[0] = "Cụm sân Thăng Long";
                updateUI.run();
            });
        }

        if (cardMaiDich != null) {
            cardMaiDich.setOnClickListener(v -> {
                selected[0] = "Sân bóng Mai Dịch";
                updateUI.run();
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        if (btnKeepFacility != null) {
            btnKeepFacility.setOnClickListener(v -> {
                StaffDataStore.setCurrentFacility(selected[0]);
                Toast.makeText(context, "Đã chọn: " + selected[0], Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onFacilitySelected(selected[0]);
                }
                dialog.dismiss();
            });
        }

        dialog.show();
    }
}
