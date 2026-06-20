package com.darklord.ai.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.darklord.ai.R;
import com.darklord.ai.data.models.ChatMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_USER = 0;
    private static final int VIEW_TYPE_AI = 1;
    private List<ChatMessage> messages;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public ChatAdapter(List<ChatMessage> messages) { this.messages = messages; }
    @Override public int getItemViewType(int position) { return messages.get(position).isAI() ? VIEW_TYPE_AI : VIEW_TYPE_USER; }
    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_USER) {
            return new UserViewHolder(inflater.inflate(R.layout.item_chat_user, parent, false));
        } else {
            return new AIViewHolder(inflater.inflate(R.layout.item_chat_ai, parent, false));
        }
    }
    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage msg = messages.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).tvMessage.setText(msg.getMessage());
            ((UserViewHolder) holder).tvTime.setText(timeFormat.format(new Date(msg.getTimestamp())));
        } else {
            ((AIViewHolder) holder).tvMessage.setText(msg.getMessage());
            ((AIViewHolder) holder).tvTime.setText(timeFormat.format(new Date(msg.getTimestamp())));
        }
    }
    @Override public int getItemCount() { return messages.size(); }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        UserViewHolder(View itemView) { super(itemView); tvMessage = itemView.findViewById(R.id.tv_message); tvTime = itemView.findViewById(R.id.tv_time); }
    }
    static class AIViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        AIViewHolder(View itemView) { super(itemView); tvMessage = itemView.findViewById(R.id.tv_message); tvTime = itemView.findViewById(R.id.tv_time); }
    }
}
