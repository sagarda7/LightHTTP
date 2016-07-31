package np.com.sagardevkota.lighthttp;

import android.content.Context;


import java.util.ArrayList;

import np.com.sagardevkota.lighthttp.datatypes.BitmapType;
import np.com.sagardevkota.lighthttp.datatypes.JsonArrayType;
import np.com.sagardevkota.lighthttp.datatypes.JsonObjectType;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;

/**
 * Created by Dell on 7/11/2016.
 */
public class LightHTTP {
    public static enum Method {
        GET,
        POST,
        PUT,
        DELETE
    }
    private Context mContext;
    private String mUrl;
    private Method mMethod;
    private static LightHTTP instance = null;

    private ArrayList<RequestParameter> params=new ArrayList<>();
    private ArrayList<HeaderParameter> headers=new ArrayList<>();

    public static LightHTTP from(Context c){
        return getInstance(c);
    }

    /**
     * Constructor
     * @param c it is the context
     */
    public LightHTTP(Context c){
        this.mContext =c;
    }

    /**
     * Returns singleton instance for network call
     * @param context it is the context of activity
     * @author Sagar Devkota
     * @return Singleton instance
     */
    public static LightHTTP getInstance(Context context) {
        if (context == null)
            throw new NullPointerException("Can not pass null context in to retrieve lighthttp instance");

        // lets make it thread safe
        synchronized (LightHTTP.class) {
            if (instance == null)
                instance = new LightHTTP(context);
        }

        return instance;
    }

    /**
     * Assigns Url to be loaded
     * @param method, url
     * @return lighthttp instance
     */
    public LightHTTP load(Method method, String url){
        this.mUrl = url;
        this.mMethod = method;
        return this;
    }

    /**
     * Sets json datatype for request
     * @return Json Type
     */
    public JsonObjectType asJsonObject(){
        return new JsonObjectType(mMethod, mUrl, params, headers);
    }

    /**
     * Sets json datatype for request
     * @return Json Array Type
     */
    public JsonArrayType asJsonArray(){
        return new JsonArrayType(mMethod, mUrl, params, headers);
    }


    /**
     * Sets bitmap type for request
     * @return Bitmap Type
     */
    public BitmapType asBitmap(){
        return new BitmapType(mMethod, mUrl, params, headers);
    }



    /**
     * Sets request body parameters
     * @param key Parameter key
     * @param value Parameter value
     * @return lighthttp instance
     */
    public LightHTTP setRequestParameter(String key, String value){
        RequestParameter param=new RequestParameter();
        param.setKey(key);
        param.setValue(value);
        this.params.add(param);
        return this;
    }


    /**
     * Sets request header parameters
     * @param key Parameter key
     * @param value Parameter value
     * @return lighthttp instance
     */
    public LightHTTP setHeaderParameter(String key, String value){
        HeaderParameter param=new HeaderParameter();
        param.setKey(key);
        param.setValue(value);
        this.headers.add(param);
        return this;
    }



}
