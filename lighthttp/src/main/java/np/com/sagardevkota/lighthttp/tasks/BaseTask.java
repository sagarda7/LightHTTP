package np.com.sagardevkota.lighthttp.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.models.Response;
import np.com.sagardevkota.lighthttp.utils.CacheManager;
import np.com.sagardevkota.lighthttp.utils.CacheManagerInterface;

/**
 * Created by HP on 7/11/2016.
 */
public abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    final String TAG = getClass().getSimpleName();
    protected CacheManagerInterface<Result> mCacheManager;
    static final int CONN_READ_TIMEOUT=10000;
    static final int CONN_TIMEOUT=15000;
    HttpURLConnection conn;

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    protected Response makeRequest(String url, LightHTTP.Method m, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        URL mUrl = new URL(url);
        conn = (HttpURLConnection) mUrl.openConnection();
        conn.setReadTimeout(CONN_READ_TIMEOUT /* milliseconds */);
        conn.setConnectTimeout(CONN_TIMEOUT /* milliseconds */);

        switch (m) {
            case GET:
                conn.setRequestMethod("GET");
                break;

            case POST:
                conn.setRequestMethod("POST");
                break;

            case PUT:
                conn.setRequestMethod("PUT");
                break;

            case DELETE:
                conn.setRequestMethod("DELETE");
                break;
        }


        conn.setDoInput(true);
        conn.setDoOutput(m != LightHTTP.Method.GET);


        //write headers if any
        if (headers.size() > 0) {
            for (HeaderParameter header : headers) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }


        Uri.Builder builder = new Uri.Builder();

        //write request parameters
        if (params.size() > 0) {
            for (RequestParameter itm : params) {
                builder.appendQueryParameter(itm.getKey(), itm.getValue());
            }

            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
        }


        conn.connect();


        int response = conn.getResponseCode();
        is = conn.getInputStream();

        Response resp = new Response();
        resp.setCode(response);
        resp.setData(is);
        return resp;
    }


    public void setmCachemanager(CacheManagerInterface<Result> cachemanager){
        this.mCacheManager=cachemanager;
    }


}
