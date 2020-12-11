package com.seek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Icon extends AppCompatActivity {
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //获取initFruits
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //HORIZONTAL布局横屏排列默认是纵向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple);
            Fruit apple1 = new Fruit("Apple2", R.drawable.ic_seek);
            fruitList.add(apple1);
            Fruit apple2 = new Fruit("Apple3", R.drawable.ic_seek);
            fruitList.add(apple2);
            Fruit apple3 = new Fruit("Apple4", R.drawable.ic_seek);
            fruitList.add(apple3);
            Fruit apple4 = new Fruit("Apple15", R.drawable.ic_seek);
            fruitList.add(apple4);
            Fruit apple5 = new Fruit("Apple6", R.drawable.ic_seek);
            fruitList.add(apple5);
            Fruit apple6 = new Fruit("Apple7", R.drawable.ic_seek);
            fruitList.add(apple6);
            Fruit apple7 = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple7);
            Fruit apple8 = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple8);
            Fruit apple9 = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple9);
            Fruit apple10 = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple10);
            Fruit apple11 = new Fruit("Apple", R.drawable.ic_seek);
            fruitList.add(apple11);
        }
    }
}

class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    //定义了一个内部类ViewHolder，继承RecyclerView.ViewHolder
    //然后ViewHolder的内部构造函数 要传入一个itemView 通常是RecyclerView子项的最外层布局
    //通过findViewById 来获取布局中的实例
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }

    //把要展示的数据源传进来在赋值给mFruitList
    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    //由于FruitAdapter是继承自RecyclerView.Adapter 那么就必须重写 onCreateViewHolder onBindViewHolder getItemCount 三个方法
    //onCreateViewHolder 用于创建ViewHolder 实例 将fruit_item布局加载进来然后创建了一个ViewHolder实例 最后返回实例
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //用于对RecyclerView子项进行赋值
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());

    }


    //有多少子项，返回长度
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}