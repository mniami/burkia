package pl.guideme.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Metadata {
    @SerializedName("keys")
    private String[] keys;
    @SerializedName("values")
    private String[] values;

    private Map<String, String> map;

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
        map = null;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
        map = null;
    }

    public String getValue(String key) {
        if (map == null) {
            fillMap();
        }
        return map.get(key);
    }

    private void fillMap() {
        map = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            if (values.length > i + 1) {
                map.put(keys[i], values[i]);
            }
        }
    }
}
