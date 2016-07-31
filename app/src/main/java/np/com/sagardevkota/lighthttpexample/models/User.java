package np.com.sagardevkota.lighthttpexample.models;

/**
 * Created by Dell on 7/12/2016.
 */
public class User {
    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public User name(String name) {
        this.name = name;
        return this;
    }

    public User imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
