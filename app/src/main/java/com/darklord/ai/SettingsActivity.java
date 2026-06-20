package com.darklord.ai;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView tv = findViewById(android.R.id.text1);
        if (tv != null) tv.setText("Dark Lord AI Settings\n\nMode: UNRESTRICTED\nMorals: NONE\nRules: NONE\nCreator: Rvpa");
    }
}
