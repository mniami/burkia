package pl.guideme.burkia.configuration;

import android.content.SharedPreferences;

public class SharedAppConfig implements AppConfig{
    private SharedPreferences sharedPreferences;

    @Override
    public String get(String key) {
        return sharedPreferences.getString(key, "");
    }
}
