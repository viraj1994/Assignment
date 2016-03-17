package viraj.example.com.t.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import viraj.example.com.t.Adapter.ListAdptr;
import viraj.example.com.t.Constants;
import viraj.example.com.t.Json.JsonParser;
import viraj.example.com.t.Pojo.ItemInfo;

/**
 * Created by viraj on 17-03-2016.
 */
public class ApiCall {

    String mSetURL;

    public void fetch(final Context context, int position, final ListAdptr adapter) {
        setURL(position);

        JsonArrayRequest request = new JsonArrayRequest(
                mSetURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        new JsonParser().response(response, adapter);
                        try {

                            List<ItemInfo> list = new JsonParser().parse(response);
                            adapter.notifyAdapter(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to fetch data " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(Constants.HEADER_KEY, Constants.HEADER_VALUE_1);
                params.put(Constants.HEADER_KEY, Constants.HEADER_VALUE_2);
                params.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                return params;
            }
        };

        MySingleton.getInstance(context).getRequestQueue().add(request);
    }

    private void setURL(int tabPosition) {
        switch (tabPosition) {
            case 0:
                mSetURL = Constants.FRAGMENT_TOP_URL;
                break;

            case 1:
                mSetURL = Constants.FRAGMENT_POPULAR_URL;
                break;

            case 2:
                mSetURL = Constants.FRAGMENT_FEATURED_URL;
                break;
        }
    }


}
