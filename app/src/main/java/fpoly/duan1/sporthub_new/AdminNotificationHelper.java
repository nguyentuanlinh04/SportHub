package fpoly.duan1.sporthub_new.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import fpoly.duan1.sporthub_new.R;

public class AdminNotificationHelper {

    public static void showNotificationDialog(Context context) {
        if (context == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_admin_notification, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        View itemReport = view.findViewById(R.id.itemNotificationReport);
        if (itemReport != null) {
            itemReport.setOnClickListener(v -> {
                dialog.dismiss();
                Intent intent = new Intent(context, AdminReportAuditActivity.class);
                context.startActivity(intent);
            });
        }

        TextView btnClose = view.findViewById(R.id.btnCloseNotification);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}