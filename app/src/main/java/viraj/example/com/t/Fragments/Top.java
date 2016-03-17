package viraj.example.com.t.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import viraj.example.com.t.ItemInfo;
import viraj.example.com.t.ListAdptr;
import viraj.example.com.t.MySingleton;
import viraj.example.com.t.R;

/**
 * Created by viraj on 17-03-2016.
 */
public class Top extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListAdptr mAdapter;
    View rootView;

    public Top() {
    }

    public static Top newInstance(int sectionNumber) {
        Top fragment = new Top();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_top, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ListAdptr(getActivity());

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        fetch();
    }

    private void fetch() {

        String url = "http://rails4.desidime.com/v1/deals/top.json";

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ItemInfo> list = parse(response);
                            mAdapter.notifyAdapter(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.v("e" ,"="+error.getMessage());
                        Toast.makeText(getActivity(), "Unable to fetch data " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Accept", "text/javascript");
                params.put("X-Desidime-Client", "0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0");


                return params;
            }
        };




        MySingleton.getInstance(getContext()).getRequestQueue().add(request);
    }


    private List<ItemInfo> parse(JSONArray jsonO) throws JSONException {
        ArrayList<ItemInfo> records = new ArrayList<ItemInfo>();


        for(int i =0; i < jsonO.length(); i++) {

            JSONObject json = jsonO.getJSONObject(i);

            String title = json.getString("title");
            title =  Html.fromHtml(title).toString();

            String description = json.getString("deal_detail");
            description = Html.fromHtml(description).toString();

            String imageUrl = json.getString("image_thumb");


            ItemInfo record = new ItemInfo(imageUrl, title, description);
            records.add(record);
        }

        return records;
    }

}

