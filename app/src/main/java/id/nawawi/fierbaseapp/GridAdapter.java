package id.nawawi.fierbaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
//    declare
    Context context;
    int image[];
    LayoutInflater inflter;

    public GridAdapter(Context applicationContext, int[] images) {
        this.context = applicationContext;
        this.image = images;
//        inflate layout
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //mengambil view
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.grid_view_items, viewGroup, false); // inflate view
        ImageView ivs = (ImageView) view.findViewById(R.id.imageView); // get the reference of ImageView
        ivs.setImageResource(image[i]); // set logo images
        return view;
    }
}