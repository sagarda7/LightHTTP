package np.com.sagardevkota.lighthttp;

import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import np.com.sagardevkota.lighthttp.datatypes.JsonArrayType;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.models.HeaderParameter;
import np.com.sagardevkota.lighthttp.models.RequestParameter;
import np.com.sagardevkota.lighthttp.tasks.JsonArrayRequestTask;
import np.com.sagardevkota.lighthttp.utils.CacheManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dell on 7/13/2016.
 */
public class MindValleyLibraryTest {
    String responseId;
    @Test
    public void headerset_isCorrect() throws Exception {
        String key="test";
        HeaderParameter headerParameter=new HeaderParameter().setKey(key);
        assertEquals(key,headerParameter.getKey());
    }

    @Test
    public void requestparamset_isCorrect() throws Exception {
        String key="foo";
        RequestParameter requestParameter=new RequestParameter().setKey(key);
        assertEquals(key,requestParameter.getKey());
    }


    @Test
    public void cache_isCorrect() throws Exception {
        String key="foo";
        JSONObject d=new JSONObject();
        d.put("name","Sagar");
        CacheManager<JSONObject> cacheManager=mock(CacheManager.class);
        when(cacheManager.get(key)).thenReturn(d);
        cacheManager.put(key,d);
        assertEquals(d,cacheManager.get(key));
    }
}
