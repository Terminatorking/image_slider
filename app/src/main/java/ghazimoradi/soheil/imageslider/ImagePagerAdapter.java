package ghazimoradi.soheil.imageslider;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    public ArrayList<Integer> imageIds;

    public ImagePagerAdapter(ArrayList<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = G.layoutInflater.inflate(R.layout.sample, null);
        ImageView image = view.findViewById(R.id.image);
        image.setImageResource(imageIds.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}