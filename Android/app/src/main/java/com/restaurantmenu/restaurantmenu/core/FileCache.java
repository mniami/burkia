package com.restaurantmenu.restaurantmenu.core;

/**
 * Created by dszcz_000 on 2015-08-05.
 */
import java.io.File;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.activities.MainActivity;

public class FileCache {
    @Inject private Application application;
    private File cacheDir;

    public File getFile(String url){
        String filename=String.valueOf(url.hashCode());
        File f = new File(getCacheDir(), filename);
        return f;
    }

    public void clear(){
        File[] files=getCacheDir().listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

    private void initializeCacheDir(){
        Context context = application.getApplicationContext();
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TempImages");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists()) {
            if (!cacheDir.mkdirs()) {
                Log.e("ResturantMenu", String.format("Cannot create %s directory.", cacheDir.toString()));
                cacheDir = context.getCacheDir();
                if (!cacheDir.exists()) {
                    if (!cacheDir.mkdirs()) {
                        Log.e("ResturantMenu", String.format("Cannot create %s directory in context cache.", cacheDir.toString()));
                    }
                }
            }
        }
    }

    private File getCacheDir() {
        if (cacheDir == null){
            initializeCacheDir();
        }
        return cacheDir;
    }
}
