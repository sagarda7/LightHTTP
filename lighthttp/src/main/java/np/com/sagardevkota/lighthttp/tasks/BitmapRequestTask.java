package np.com.sagardevkota.lighthttp.tasks;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.ArrayList;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.models.Response;

/**
 * Created by Dell on 7/11/2016.
 */
public class BitmapRequestTask extends BaseTask<String, Void, Bitmap> {
    private LightHTTP.Method mMethod;
    private String mUrl;
    private HttpListener<Bitmap> mCallback;
    private boolean error=false;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;

    public BitmapRequestTask(LightHTTP.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, HttpListener<Bitmap> callback){
        this.mUrl=url;
        this.mMethod=method;
        this.mCallback=callback;
        this.params=params;
        this.headers=headers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            Response response=makeRequest(mUrl,mMethod,params,headers);
            Bitmap bitmap= response.getAsBitmap();
            if(this.mCacheManager!=null){
                if(this.mCacheManager.getDataFromCache(mUrl)==null)
                    this.mCacheManager.addDataToCache(mUrl,bitmap);
            }
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap data) {
        super.onPostExecute(data);
        if(!error)
            this.mCallback.onResponse(data);
        else
            this.mCallback.onError();
    }

    /**
     * Sometimes users may cancel at almost end, so lets remove if data is in cache
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(this.mCacheManager!=null){
            this.mCacheManager.removeDataFromCache(mUrl);
        }
    }
}
