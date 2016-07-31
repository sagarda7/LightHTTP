package np.com.sagardevkota.lighthttp.datatypes;

import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.utils.CacheManagerInterface;

/**
 * Created by HP on 7/12/2016.
 */
public abstract class Type<T> {
    public abstract Type setCacheManager(CacheManagerInterface<T> cacheManager);
    public abstract Type setCallback(HttpListener<T> callback);
    public abstract boolean cancel();
}
