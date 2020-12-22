package com.seek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seek.msg.MsgAdapter;
import com.seek.msg.Msg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//事件监听1
public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent;
    private TextView textView;
    private Button button2, send;
    private static final String TAG = "设置页面";
    private ActionBar actionBar;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String data = "设置页面的最后一口气";
        outState.putString("set", data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "SettingsActivity onCreate: Task is " + getTaskId());
        setContentView(R.layout.settings_activity);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("set");
            Log.e(TAG, "Main传递的临时数据为: " + tempData);
        } else {
            Log.e(TAG, "Main未传递临时数据 ");

        }
        //事件监听2  绑定事件
//        Button button2 = (Button) findViewById(R.id.button2);
//        button2.setOnClickListener(this);
//
//        Button button3 = (Button) findViewById(R.id.button3);
//        button3.setOnClickListener(this);


//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings, new SettingsFragment())
//                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //传参2  接受参数并通过Toast及TextView显示
        final Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        String data1 = intent.getStringExtra("extra_data1");
//        Log.e(TAG, "extra_data: " + data + data1);
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(data);
        //初始化消息数据
//        initMsga();
//        inputText = (EditText) findViewById(R.id.input_text);
//        send = (Button) findViewById(R.id.send);
//        send.setOnClickListener(this);
//        msgRecyclerView = (RecyclerView) findViewById(R.id.message_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        msgRecyclerView.setLayoutManager(layoutManager);
//
//        adapter = new MsgAdapter(msgList);
//        msgRecyclerView.setAdapter(adapter);

    }



    //事件监听3
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                //回传2
                Intent intent1 = new Intent();
                intent1.putExtra("data_return", "你好啊，启动页");
                //一般只返回RESULT_OK和RESULT_CANCELED
                setResult(RESULT_OK, intent1);
                finish();
                break;
            //启动模式
            case R.id.button3:
                Intent intent2 = new Intent(SettingsActivity.this, ThirdActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

    //重写back方法，使back也可以回传intent 回传3
    @Override
    public void onBackPressed() {
        Intent backintent = new Intent();
        backintent.putExtra("data_return", "你好啊，启动页，我是物理返回键");
        setResult(RESULT_OK, backintent);
        finish();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: SettingsActivity");
    }

//    //消息类
//    public class Msg {
//        public static final int TYPE_RECEIVED = 0;
//        public static final int TYPE_SENT = 1;
//        private String content;
//        private int type;
//
//        public Msg(String content, int type) {
//            this.content = content;
//            this.type = type;
//        }
//
//        //消息内容
//        public String getContent() {
//            return content;
//        }
//
//        //消息类型
//        public int getType() {
//            return type;
//        }
//    }
//
//    //Recycler适配器类
//    public  class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
//        private List<Msg> mMsgList;
//
//        static class ViewHolder extends RecyclerView.ViewHolder {
//            LinearLayout leftLayout;
//            LinearLayout rightLayout;
//            TextView leftMsg;
//            TextView rightMsg;
//
//            public ViewHolder(View view) {
//                super(view);
//                leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
//                rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
//                leftMsg = (TextView) view.findViewById(R.id.left_msg);
//                rightMsg = (TextView) view.findViewById(R.id.right_msg);
//            }
//        }
//
//        public MsgAdapter(List<Msg> msgList) {
//            mMsgList = msgList;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Msg msg = mMsgList.get(position);
//            //收到消息显示左边隐藏右边
//            if (msg.getType() == Msg.TYPE_RECEIVED) {
//                holder.leftLayout.setVisibility(View.VISIBLE);
//                holder.rightLayout.setVisibility(View.GONE);
//                holder.leftMsg.setText(msg.getContent());
//                //发送消息显示右边隐藏左边
//            } else if (msg.getType() == Msg.TYPE_SENT) {
//                holder.leftLayout.setVisibility(View.GONE);
//                holder.rightLayout.setVisibility(View.VISIBLE);
//                holder.rightMsg.setText(msg.getContent());
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mMsgList.size();
//        }
//
//
//    }
}