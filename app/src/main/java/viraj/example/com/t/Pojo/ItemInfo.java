package viraj.example.com.t.Pojo;

/**
 * Created by viraj on 17-03-2016.
 */
public class ItemInfo {

    private String imageUrl;
    private String title;
    private String description;

    public ItemInfo(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}