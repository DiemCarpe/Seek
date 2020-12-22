package com.seek.msg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.seek.R;

import java.util.ArrayList;
import java.util.List;

public class MsgActivity extends AppCompatActivity {

    private List<Msg>  msgList = new ArrayList<Msg>();
    private EditText inputText;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        //初始化假数据
        initMsg();
        final RecyclerView msgRecyclerView = (RecyclerView) findViewById(R.id.message_view);
        final EditText inputText = (EditText)findViewById(R.id.input_text);
        Button send = (Button)findViewById(R.id.send);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg  msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    //当有新消失时，刷新ListView中的显示
                    adapter.notifyItemChanged(msgList.size()- 1 );
                    //将ListView 定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    //清空输入框
                    inputText.setText("");


                }
            }
        });



    }

    //初始化消息数据

    public void initMsg(){
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("Are you a Chinese?", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("是的，我是", Msg.TYPE_SENT);
        msgList.add(msg4);
        Msg msg5 = new Msg("哈哈，我也是！", Msg.TYPE_RECEIVED);
        msgList.add(msg5);    }
}