package pl.guideme.data.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;

@EBean
public class ImageLoader {
    public void load(String url, Context context, ImageView imageView){
        //TODO refactor to user local cache loader and loading images asynchronously from the local cache
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }
}
