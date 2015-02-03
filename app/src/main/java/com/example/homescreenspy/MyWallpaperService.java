package com.example.homescreenspy;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;

/**
 * Created by arunesh on 2/2/15.
 */
public class MyWallpaperService extends WallpaperService {
    private static final String TAG = "HomeScreen";
    @Override
    public Engine onCreateEngine() {
        return new MyWallpaperEngine();
    }

    private class MyWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private long lastVisibilityStartTs = -1;
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
            }
        };

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(MyWallpaperService.this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                Log.i(TAG, "HomeScreen made visible.");
                lastVisibilityStartTs = System.currentTimeMillis();
                handler.post(drawRunner);
            } else {
                if (lastVisibilityStartTs > 0) {
                    long delta = System.currentTimeMillis() - lastVisibilityStartTs;
                    Log.i(TAG, "Delta = " + delta);
                }
                handler.removeCallbacks(drawRunner);
            }
        }
    }
}
