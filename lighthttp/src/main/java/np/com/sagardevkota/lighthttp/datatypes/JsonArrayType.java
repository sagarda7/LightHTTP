package np.com.sagardevkota.lighthttp.datatypes;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.ArrayList;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.tasks.JsonArrayRequestTask;
import np.com.sagardevkota.lighthttp.utils.CacheManagerInterface;

/**
 * Created by Dell on 7/11/2016.
 */
public class JsonArrayType extends Type<JSONArray> {
    private String mUrl;
    private HttpListener<JSONArray> mListener;
    private LightHTTP.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private JsonArrayRequestTask mTask;
    private CacheManagerInterface<JSONArray> mCacheManager;

    /**
     * Constructor to load json datatyes
     * @param url
     * @param params
     * @param headers
     */
    public JsonArrayType(LightHTTP.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
        this.mUrl=url;
        this.mMethod=m;
        this.headers=headers;
        this.params=params;
    }

    /**
     *
     * Sets future callback after Http response is received
     * @param listener
     */
    public JsonArrayType setCallback(HttpListener<JSONArray> listener){
        this.mListener=listener;
        mListener.onRequest();
        JSONArray data;
        if(mCacheManager!=null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mListener.onResponse(data);
                return this;
            }
        }

        mTask = new JsonArrayRequestTask(mMethod, mUrl, params, headers, mListener);
        mTask.setmCachemanager(mCacheManager);
        mTask.execute();
        return this;
    }

    /**
     * Cancels the current request
     * @return True if cancelled
     */
    public boolean cancel(){
        if(mTask!=null){
            mTask.cancel(true);
            if(mTask.isCancelled()) {
                mListener.onCancel();
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Lets depend on abstraction
     * Sets CacheManager for this
     * @param cache
     * @return JsonObjectType
     */
    public JsonArrayType setCacheManager(CacheManagerInterface<JSONArray> cache){
        this.mCacheManager=cache;
        return this;
    }


}
