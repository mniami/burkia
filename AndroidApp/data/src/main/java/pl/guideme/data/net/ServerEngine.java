package pl.guideme.data.net;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.EBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import pl.guideme.data.api.FeedsApi;
import pl.guideme.data.logs.AndroidLogger;
import pl.guideme.data.BuildConfig;
import pl.guideme.data.logs.Log;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

@EBean(scope = EBean.Scope.Singleton)
public class ServerEngine {
    private static final Log sLogger = Log.withName("ServerEngine");
    private static final Charset usAsciiCharset = Charset.forName("US-ASCII");
    private static final RestAdapter.Log androidLogger = new AndroidLogger("PrizeEngine");
    private boolean isInitialized;
    private static LenientGsonConverter lenientGsonConverter;
    private ConnectivityAwareUrlClient connectivityAwareUrlClient;
    private RestAdapter apiAdapter;

    public synchronized void initialize(String serverEndpoint) {
        if (isInitialized) return;

        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());

        Gson gson = builder.create();
        lenientGsonConverter = new LenientGsonConverter(gson);
        connectivityAwareUrlClient = new ConnectivityAwareUrlClient();
        connectivityAwareUrlClient.setWrappedClient(new OkHttpClient());
        connectivityAwareUrlClient.setReadTimeout(30, TimeUnit.SECONDS);
        connectivityAwareUrlClient.setConnectTimeout(30, TimeUnit.SECONDS);
        connectivityAwareUrlClient.setWriteTimeout(30, TimeUnit.SECONDS);

        apiAdapter = build(serverEndpoint, connectivityAwareUrlClient, BuildConfig.DEBUG);

        isInitialized = true;
    }

    public FeedsApi getFeedsApi(){
        return apiAdapter.create(FeedsApi.class);
    }

    private void initializeIfNeeded() {
        if (!isInitialized){
            initialize("");
        }
    }

    private static RestAdapter build(String endpointUrl, ConnectivityAwareUrlClient connectivityAwareUrlClient, boolean isDebug) {
        GsonBuilder builder = new GsonBuilder();

        builder.disableHtmlEscaping();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());

        Gson gson = builder.create();
        RestAdapter.Builder adapterBuilder = new RestAdapter.Builder();
        adapterBuilder
                .setConverter(new LenientGsonConverter(gson))
                .setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE).setLog(androidLogger)
                .setExecutors(createDefaultHttpExecutor(THREAD_PRIORITY_BACKGROUND), createDefaultHttpExecutor(THREAD_PRIORITY_BACKGROUND))
                .setEndpoint(endpointUrl)
                .setClient(new OkClient(connectivityAwareUrlClient));

        return adapterBuilder.build();
    }


    private static String readString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = bufferedReader.readLine();
        StringBuilder sb = new StringBuilder();
        boolean isFirstLine = true;

        while (line != null) {
            if (isFirstLine) {
                isFirstLine = false;
            } else {
                sb.append('\n');
            }
            sb.append(line);
            line = bufferedReader.readLine();
        }
        return sb.toString();
    }
    private static Executor createDefaultHttpExecutor(int threadCount) {
        return Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(() -> {
                    android.os.Process.setThreadPriority(threadCount);
                    try {
                        r.run();
                    }catch (Exception ex){
                        sLogger.error(ex, ()->"PrizeEngine response executor thread raised an exception");
                    }
                }, "PrizeEngine");
            }
        });
    }

    private RestAdapter buildBasicAuthentication(String endpointUrl, String username, String password) {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());

        String credentials = username + ":" + password;
        // create Base64 encoded string
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(usAsciiCharset), Base64.NO_WRAP);

        Gson gson = builder.create();
        RestAdapter.Builder adapterBuilder = new RestAdapter.Builder();

        adapterBuilder.setRequestInterceptor(request -> {
            request.addHeader("Authorization", basic);
            request.addHeader("Accept", "application/json");
        });

        adapterBuilder
                .setConverter(new GsonConverter(gson))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setLog(androidLogger)
                .setExecutors(createDefaultHttpExecutor(THREAD_PRIORITY_BACKGROUND), createDefaultHttpExecutor(THREAD_PRIORITY_BACKGROUND))
                .setEndpoint(endpointUrl)
                .setClient(new OkClient(connectivityAwareUrlClient));

        return adapterBuilder.build();
    }
}
