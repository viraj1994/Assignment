package viraj.example.com.t.Json;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import viraj.example.com.t.Adapter.ListAdptr;
import viraj.example.com.t.Constants;
import viraj.example.com.t.Pojo.ItemInfo;

/**
 * Created by viraj on 17-03-2016.
 */
public class JsonParser {


    public void response(JSONArray jsonArray, ListAdptr adapter) {
        try {
            List<ItemInfo> list = parse(jsonArray);
            adapter.notifyAdapter(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<ItemInfo> parse(JSONArray jsonO) throws JSONException {
        ArrayList<ItemInfo> records = new ArrayList<ItemInfo>();


        for(int i =0; i < jsonO.length(); i++) {

            JSONObject json = jsonO.getJSONObject(i);

            String title = json.getString(Constants.KEY_TITLE);
            title =  Html.fromHtml(title).toString();

            String description = json.getString(Constants.KEY_DESCRIPTION);
            description = Html.fromHtml(description).toString();

            String imageUrl = json.getString(Constants.KEY_IMAGE_URL);


            ItemInfo record = new ItemInfo(imageUrl, title, description);
            records.add(record);
        }

        return records;
    }

}
