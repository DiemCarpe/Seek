//package com.seek;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//
//
//public class FruitAdapter extends ArrayAdapter<Fruit> {
//    private int resourceId;
//
//    public FruitAdapter(Context context, int textViewResourceId,
//                        List<Fruit> objects) {
//        super(context, textViewResourceId, objects);
//        resourceId = textViewResourceId;
//
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Fruit fruit = getItem(position);//获取当前的Fruit实例
//        View view;
//        ViewHolder viewHolder;
//        //如果convertView等于null则重新加载
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//            //创建一个ViewHolder方法将控件加入缓存中 2
//            viewHolder = new ViewHolder();
//            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//            viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
//            //将viewHolder对象存在view中
//            view.setTag(viewHolder);
//
//            //否则直接对convertView进行重用，并获取缓存的控件方法
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();//重新获取viewHolder3
//        }
//
//        viewHolder.fruitImage.setImageResource(fruit.getImageId());
//        viewHolder.fruitName.setText(fruit.getName());
//        return view;
//    }
//    //新建一个内部类对控件实例进行缓存 1
//    class ViewHolder{
//        ImageView fruitImage;
//        TextView fruitName;
//    }
//}
