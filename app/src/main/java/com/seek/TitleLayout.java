package com.seek;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;
//自定义布局1
public class TitleLayout extends LinearLayout {
    private Button titleBack, titleEdit;
    private TextView title_text;

    public TitleLayout(Context context, AttributeSet attres) {
        super(context, attres);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        titleBack = (Button) findViewById(R.id.title_back);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭当前activity
                ((Activity) getContext()).finish();
            }
        });

        titleEdit = (Button) findViewById(R.id.title_edit);
        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "title_edit", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.title_back:
//                titleback = (Button) findViewById(R.id.title_back);
//                ((Activity) getContext()).finish();
//                break;
//            case R.id.title_edit:
//                titleedit = (Button) findViewById(R.id.title_edit);
//                Toast.makeText(getContext(), "title_edit", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//        }
//    }
//}
