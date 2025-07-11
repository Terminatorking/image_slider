package ghazimoradi.soheil.imageslider;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

public class G extends Application {
    public static Context context;
    public static LayoutInflater layoutInflater;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
