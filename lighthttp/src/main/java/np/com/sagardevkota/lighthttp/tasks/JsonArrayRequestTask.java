package np.com.sagardevkota.lighthttp.tasks;

import android.util.Log;

import org.json.JSONArray;
import java.util.ArrayList;
import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.models.Response;


/**
 * Created by Dell on 7/11/2016.
 */
public class JsonArrayRequestTask extends BaseTask<String, Void, JSONArray> {
    private LightHTTP.Method mMethod;
    private String mUrl;
    private HttpListener<JSONArray> mCallback;
    private boolean error=false;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;


    public JsonArrayRequestTask(LightHTTP.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, HttpListener<JSONArray> callback){
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
    protected JSONArray doInBackground(String... urls) {
        try {

            Response response=makeRequest(mUrl,mMethod,params,headers);
            JSONArray json= new JSONArray(response.getDataAsString());
            if(this.mCacheManager!=null){
                if(this.mCacheManager.getDataFromCache(mUrl)==null)
                    this.mCacheManager.addDataToCache(mUrl,json);
            }
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray data) {
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
