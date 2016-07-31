package np.com.sagardevkota.lighthttp.listeners;

/**
 * Created by Dell on 7/11/2016.
 */
public interface HttpListener<T> {
    /**
     * callback for request start
     */
    public void onRequest();

    /**
     *  Callback which is fired after response
     * @param data  it holds respons data
     */
    public void onResponse(T data);

    public void onError();

    public void onCancel();

}
