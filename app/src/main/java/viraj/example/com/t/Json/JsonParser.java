package viraj.example.com.t.Json;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import viraj.example.com.t.Constants;
import viraj.example.com.t.Pojo.ItemInfo;

/**
 * Created by viraj on 17-03-2016.
 */
public class JsonParser {


    public List<ItemInfo> parse(JSONArray jsonArray) {
        ArrayList<ItemInfo> records = new ArrayList<ItemInfo>();


        for(int i =0; i < jsonArray.length(); i++) {

            try {
                JSONObject json = jsonArray.getJSONObject(i);

                String title = json.getString(Constants.KEY_TITLE);

                String description = json.getString(Constants.KEY_DESCRIPTION);
                description = (Html.fromHtml(description).toString()).replaceAll("\\s+", " ").trim();


                String imageUrl = json.getString(Constants.KEY_IMAGE_URL);

                ItemInfo record = new ItemInfo(imageUrl, title, description);
                records.add(record);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return records;
    }

}
