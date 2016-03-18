package viraj.example.com.t.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import viraj.example.com.t.Adapter.ListAdptr;
import viraj.example.com.t.Constants;
import viraj.example.com.t.Fragments.Featured;
import viraj.example.com.t.Fragments.Popular;
import viraj.example.com.t.Fragments.Top;
import viraj.example.com.t.Json.JsonParser;
import viraj.example.com.t.Pojo.ItemInfo;

/**
 * Created by viraj on 17-03-2016.
 */
public class ApiCall {

    String mSetURL;

    int totalNumberOfObject;
    int itemsPerPage;
    int pageCount;
    int val;
    int increment = 0;
    ArrayList<ItemInfo> arrayList;
    ListAdptr mAdapter;
    private int getPosition;



    public ApiCall(ListAdptr adapter, int position) {
        this.mAdapter = adapter;
        this.getPosition = position;
    }

    //common method to notify the adapter
    public void notify(List<ItemInfo> list) {
        mAdapter.notifyAdapter(list);
    }


    /*
        fetch() is use to get data from the url
        and forward the data to JsonParser.class
    */

    public void fetch(final Context context) {
        mSetURL = setURL(getPosition);
                JsonArrayRequest request = new JsonArrayRequest(
                mSetURL,
                new Response.Listener<JSONArray>() {

                    // got the json response
                    @Override
                    public void onResponse(JSONArray response) {
                        // send the response to parse
                            pagination(new JsonParser().parse(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error occur
                        Toast.makeText(context, "Unable to fetch data " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
                    //Adding Header to the url
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


    // get the url for the specific tab click
    private String setURL(int tabPosition) {
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
        return mSetURL;
    }

    //get the data for pagination
    private void pagination(List<ItemInfo> list) {

        arrayList = new ArrayList<ItemInfo>();

        totalNumberOfObject = list.size();
        itemsPerPage = Constants.itemPerPage;
        val = totalNumberOfObject % itemsPerPage;
        val = val == 0 ? 0 : 1;

        pageCount = totalNumberOfObject / itemsPerPage;

        for (int i=0; i<list.size(); i++) {
            arrayList.add(list.get(i));
        }

         loadList(0);
    }

    //load the data for each page
    private void loadList(int number) {
        ArrayList<ItemInfo> sort = new ArrayList<ItemInfo>();


        callPageCount(getPosition, number, pageCount);

        int start = number * itemsPerPage;

        for (int i = start; i < (start) + itemsPerPage; i++) {
            if (i < totalNumberOfObject){
                sort.add(arrayList.get(i));
            } else
                break;
        }

        notify(sort);

    }


    // call the "setPageCount()" of the specific class
    private void callPageCount(int tabPosition, int number, int pageCount) {
        switch (tabPosition){
            case 0:
                Top.setPageCount(number, pageCount);
                break;

            case 1:
                Popular.setPageCount(number, pageCount);
                break;

            case 2:
                Featured.setPageCount(number, pageCount);
        }
    }


    /*To move on the next or previous page of the same tab
        1- on next Page
        0- on previous page
     */
    public void changePage(int page) {
        if (page == 1) {
            increment++;
            loadList(increment);
            getButtonState();
        } else if (page == 0){
            increment--;
            loadList(increment);
            getButtonState();
        }

    }


    public int getButtonState() {
        int change;
        if (increment+1 == pageCount) {
            change = 0;
        } else if (increment == 0) {
            change = 1;
        } else {
            change = 2;
        }
        return change;
    }





}
