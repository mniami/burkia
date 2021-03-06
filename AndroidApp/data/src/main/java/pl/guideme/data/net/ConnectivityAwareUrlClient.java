package pl.guideme.data.net;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSink;
import pl.guideme.data.ConnectivityReceiver;
import pl.guideme.data.logs.Log;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by szymon on 26.02.16.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ConnectivityAwareUrlClient extends OkHttpClient {

    private final static Log logger = Log.withName("ConnectivityAwareUrlClient");

    @Bean
    protected ConnectivityReceiver connectivityReceiver;
    private OkHttpClient wrappedClient;

    public void setWrappedClient(OkHttpClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    public Response execute(Request request) throws IOException {
        if (!connectivityReceiver.isInternetConnection()) {
            throw new IOException("No connectivity");
        }
        return parseResponse(wrappedClient.newCall(createRequest(request)).execute());
    }

    static com.squareup.okhttp.Request createRequest(Request request) {
        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder()
                .url(request.getUrl())
                .method(request.getMethod(), createRequestBody(request.getBody()));

        List<Header> headers = request.getHeaders();
        for (int i = 0, size = headers.size(); i < size; i++) {
            Header header = headers.get(i);
            String value = header.getValue();
            if (value == null) value = "";
            builder.addHeader(header.getName(), value);
        }

        return builder.build();
    }

    static Response parseResponse(com.squareup.okhttp.Response response) {
        return new Response(response.request().urlString(), response.code(), response.message(),
                createHeaders(response.headers()), createResponseBody(response.body()));
    }

    private static RequestBody createRequestBody(final TypedOutput body) {
        if (body == null) {
            return null;
        }
        final MediaType mediaType = MediaType.parse(body.mimeType());
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                body.writeTo(sink.outputStream());
            }

            @Override
            public long contentLength() {
                return body.length();
            }
        };
    }

    private static TypedInput createResponseBody(final ResponseBody body) {
        try {
            if (body.contentLength() == 0) {
                return null;
            }
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
        return new TypedInput() {
            @Override
            public String mimeType() {
                MediaType mediaType = body.contentType();
                return mediaType == null ? null : mediaType.toString();
            }

            @Override
            public long length() {
                try {
                    return body.contentLength();
                } catch (IOException e) {
                    logger.error(e);
                    return 0;
                }
            }

            @Override
            public InputStream in() throws IOException {
                return body.byteStream();
            }
        };
    }

    private static List<Header> createHeaders(Headers headers) {
        int size = headers.size();
        List<Header> headerList = new ArrayList<Header>(size);
        for (int i = 0; i < size; i++) {
            headerList.add(new Header(headers.name(i), headers.value(i)));
        }
        return headerList;
    }
}