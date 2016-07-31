package np.com.sagardevkota.lighthttp.datatypes;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.tasks.JsonObjectRequestTask;
import np.com.sagardevkota.lighthttp.utils.CacheManagerInterface;

/**
 * Created by Dell on 7/11/2016.
 */
public class JsonObjectType extends Type<JSONObject> {
    private String mUrl;
    private HttpListener<JSONObject> mListener;
    private LightHTTP.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private JsonObjectRequestTask mTask;
    private CacheManagerInterface<JSONObject> mCacheManager;

    /**
     * Constructor to load json datatyes
     * @param url
     * @param params
     * @param headers
     */
    public JsonObjectType(LightHTTP.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
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
    public JsonObjectType setCallback(HttpListener<JSONObject> listener){
        this.mListener=listener;
        mListener.onRequest();
        JSONObject data;
        if(mCacheManager!=null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mListener.onResponse(data);
                return this;
            }
        }

        mTask=new JsonObjectRequestTask(mMethod,mUrl,params,headers,mListener);
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
    public JsonObjectType setCacheManager(CacheManagerInterface<JSONObject> cache){
        this.mCacheManager=cache;
        return this;
    }


}
