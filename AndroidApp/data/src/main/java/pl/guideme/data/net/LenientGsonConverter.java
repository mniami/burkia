package pl.guideme.data.net;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import pl.guideme.data.logs.Log;
import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

public class LenientGsonConverter extends GsonConverter {
    private static final Log sLogger = Log.withName("LenientGsonConverter");

    private Gson mGson;

    public LenientGsonConverter(Gson gson) {
        super(gson);
        mGson = gson;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        boolean willCloseStream = false; // try to close the stream, if there is no exception thrown using tolerant  JsonReader
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(body.in()));
            jsonReader.setLenient(true);
            Object o = mGson.fromJson(jsonReader, type);
            willCloseStream = true;
            return o;
        } catch (IOException e) {
            sLogger.error(e);
        } catch (IllegalStateException ise) {
            if (type != null) {
                sLogger.error(ise, () -> String.format("From body failed for type '%s'.", type.toString()));
            } else {
                sLogger.error(ise, () -> "From body failed.");
            }
        } finally {
            if (willCloseStream) {
                closeStream(body);
            }
        }

        return super.fromBody(body, type);
    }

    private void closeStream(TypedInput body) {
        try {
            InputStream in = body.in();
            in.close();
        } catch (IOException e) {
            sLogger.error(e);
        }
    }
}