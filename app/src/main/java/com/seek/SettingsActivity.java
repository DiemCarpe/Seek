package com.seek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    private  Intent intent;
    private TextView textView;
    private Button button2;
    private static final String TAG = "设置页面";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //接受参数并通过Toast及TextView显示
        final Intent intent = getIntent();
        String data= intent.getStringExtra("extra_data");
        Log.e(TAG, "extra_data: "+data);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(data);
        //回传
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","你好啊，启动页");
                //一般只返回RESULT_OK和RESULT_CANCELED
                setResult(RESULT_OK,intent1);
                finish();
            }
        });

    }
    //重写back方法，使back也可以回传intent
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent();
        backintent.putExtra("data_return","你好啊，启动页，我是物理返回键");
        setResult(RESULT_OK,backintent);
        finish();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}