package np.com.sagardevkota.lighthttp.utils;

/**
 * Created by HP on 7/12/2016.
 */
public interface CacheManagerInterface<T> {
    public void addDataToCache(String key, T data);
    public void removeDataFromCache(String key);
    public T getDataFromCache(String key);
    public void evictUnused();

}
