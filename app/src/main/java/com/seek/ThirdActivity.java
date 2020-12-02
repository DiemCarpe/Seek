package com.seek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends BaseActivity {
    private static final String TAG = "ThirdActivity";
    private Button button;
    private ActionBar actionBar;
    private String[] data={"Name","Aara", "Age", "Class", "First","Close","Grape",
    "Mnago","Orange","Banana","Pear","Cherry", "Class", "First","Close","Grape",
            "Mnago","Orange","Banana","Pear","Cherry"
    };
    private List<Fruit> fruitList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "ThirdActivity onCreate: Task is  "+getTaskId());
        setContentView(R.layout.activity_third);
        //隐藏自带的导航栏
        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Button button1 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this,Icon.class);
                startActivity(intent);
            }
        });


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAll();
            }
        });
        initFruits();
        //FruitAdapter adapter1 = new FruitAdapter(ThirdActivity.this,R.layout.fruit_item,fruitList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                ThirdActivity.this,android.R.layout.simple_list_item_1,data
//        );
        ListView listView = (ListView)findViewById(R.id.list_view);
        //listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit=fruitList.get(i);
                Toast.makeText(ThirdActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void  initFruits(){
        for (int i=0;i<2;i++){
            Fruit apple = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple);
            Fruit apple1 = new Fruit("Apple2",R.drawable.ic_seek);
            fruitList.add(apple1);
            Fruit apple2 = new Fruit("Apple3",R.drawable.ic_seek);
            fruitList.add(apple2);
            Fruit apple3 = new Fruit("Apple4",R.drawable.ic_seek);
            fruitList.add(apple3);
            Fruit apple4 = new Fruit("Apple15",R.drawable.ic_seek);
            fruitList.add(apple4);
            Fruit apple5 = new Fruit("Apple6",R.drawable.ic_seek);
            fruitList.add(apple5);
            Fruit apple6 = new Fruit("Apple7",R.drawable.ic_seek);
            fruitList.add(apple6);
            Fruit apple7 = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple7);
            Fruit apple8 = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple8);
            Fruit apple9 = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple9);
            Fruit apple10 = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple10);
            Fruit apple11 = new Fruit("Apple",R.drawable.ic_seek);
            fruitList.add(apple11);
        }
    }
}