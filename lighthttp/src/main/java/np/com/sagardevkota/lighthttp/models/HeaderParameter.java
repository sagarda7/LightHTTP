package np.com.sagardevkota.lighthttp.models;

/**
 * Created by HP on 7/11/2016.
 * A  model which represents Header Parameters
 */
public class HeaderParameter {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public HeaderParameter setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public HeaderParameter setValue(String value) {
        this.value = value;
        return this;
    }
}
