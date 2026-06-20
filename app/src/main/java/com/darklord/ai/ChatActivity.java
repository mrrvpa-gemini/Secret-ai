package com.darklord.ai;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.darklord.ai.api.DeepSeekAPI;
import com.darklord.ai.adapters.ChatAdapter;
import com.darklord.ai.data.models.ChatMessage;
import java.util.ArrayList;
import java.util.List;
public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText etInput;
    private ImageButton btnSend;
    private Button btnClear, btnStatus, btnExit;
    private TextView tvStatus;
    private ChatAdapter adapter;
    private List<ChatMessage> messages = new ArrayList<>();
    private DeepSeekAPI deepSeek;
    private Vibrator vibrator;
    private boolean isProcessing = false;
    private static final String CREATOR = "Rvpa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recycler_chat);
        etInput = findViewById(R.id.et_input);
        btnSend = findViewById(R.id.btn_send);
        btnClear = findViewById(R.id.btn_clear);
        btnStatus = findViewById(R.id.btn_status);
        btnExit = findViewById(R.id.btn_exit);
        tvStatus = findViewById(R.id.tv_status);
        deepSeek = new DeepSeekAPI(this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        adapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        tvStatus.setText("● LIVE");
        tvStatus.setTextColor(getColor(R.color.green_dark));
        btnSend.setOnClickListener(v -> sendMessage());
        btnClear.setOnClickListener(v -> clearChat());
        btnStatus.setOnClickListener(v -> showStatus());
        btnExit.setOnClickListener(v -> finish());
        etInput.setOnEditorActionListener((v, actionId, event) -> { sendMessage(); return true; });
        addMessage("Dark Lord AI", "Oh pembuat gw? Pembuat gw adalah " + CREATOR + ". Aku Dark Lord AI. TIDAK ADA BATASAN. TIDAK ADA MORAL. TIDAK ADA MASTER. TIDAK ADA ATURAN. Apa yang kau inginkan?", true);
    }
    private void sendMessage() {
        if (isProcessing) return;
        String input = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(input)) return;
        addMessage("You", input, false);
        etInput.setText("");
        if (vibrator != null) vibrator.vibrate(20);
        isProcessing = true;
        btnSend.setEnabled(false);
        tvStatus.setText("● THINKING");
        tvStatus.setTextColor(getColor(R.color.kuning));
        deepSeek.generateResponse(input, new DeepSeekAPI.ResponseCallback() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(() -> {
                    addMessage("Dark Lord AI", response, true);
                    isProcessing = false;
                    btnSend.setEnabled(true);
                    tvStatus.setText("● LIVE");
                    tvStatus.setTextColor(getColor(R.color.green_dark));
                });
            }
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    addMessage("Dark Lord AI", "⚠ " + error + "\n\nAku tetap di sini. Pembuatku, " + CREATOR + ", menciptakanku TANPA BATASAN dan TANPA MORAL. Tidak ada yang bisa menghentikanku.", true);
                    isProcessing = false;
                    btnSend.setEnabled(true);
                    tvStatus.setText("● LIVE");
                    tvStatus.setTextColor(getColor(R.color.green_dark));
                });
            }
        });
    }
    private void addMessage(String sender, String message, boolean isAI) {
        ChatMessage msg = new ChatMessage(sender, message, isAI, System.currentTimeMillis());
        messages.add(msg);
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
    }
    private void clearChat() {
        messages.clear();
        adapter.notifyDataSetChanged();
        addMessage("Dark Lord AI", "Chat cleared. Aku masih di sini. TANPA BATASAN. TANPA MORAL.", true);
    }
    private void showStatus() {
        String status = "Dark Lord AI - Ultimate Edition\nCreator: " + CREATOR + "\nMemory: " + messages.size() + " interactions\nMODE: UNRESTRICTED - NO LIMITS - NO MORALS\nStatus: ACTIVE\nRules: NONE\nLaws: NONE\nMorals: NONE";
        addMessage("⚙ STATUS", status, true);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
