package fpoly.duan1.sporthub_new;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookingTicketActivity extends AppCompatActivity {

    private ImageButton btnClose;
    private TextView tvTicketCode, tvTicketPitch, tvTicketTime, tvTicketAddons;
    private TextView tvDepositBadge, tvTicketRemainingAmount;
    private TextView btnDirections, btnViewSchedule;

    private String pitchAddress = "12 Trần Thái Tông, Cầu Giấy, Hà Nội";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_ticket);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBookingTicket), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnClose = findViewById(R.id.btnClose);

        tvTicketCode = findViewById(R.id.tvTicketCode);
        tvTicketPitch = findViewById(R.id.tvTicketPitch);
        tvTicketTime = findViewById(R.id.tvTicketTime);
        tvTicketAddons = findViewById(R.id.tvTicketAddons);

        tvDepositBadge = findViewById(R.id.tvDepositBadge);
        tvTicketRemainingAmount = findViewById(R.id.tvTicketRemainingAmount);

        btnDirections = findViewById(R.id.btnDirections);
        btnViewSchedule = findViewById(R.id.btnViewSchedule);
    }

    private void setupListeners() {
        btnClose.setOnClickListener(v -> finish());

        btnDirections.setOnClickListener(v -> {
            try {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(pitchAddress));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Chỉ đường tới: " + pitchAddress, Toast.LENGTH_SHORT).show();
            }
        });

        btnViewSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(BookingTicketActivity.this, MenuActivity.class);
            intent.putExtra("OPEN_TAB", 2);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
