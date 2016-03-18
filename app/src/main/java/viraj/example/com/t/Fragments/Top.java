package viraj.example.com.t.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import viraj.example.com.t.Adapter.ListAdptr;
import viraj.example.com.t.Network.ApiCall;
import viraj.example.com.t.R;

/**
 * Created by viraj on 17-03-2016.
 */
public class Top extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListAdptr mAdapter;
    View rootView;
    private static int position;
    public static TextView topPageCount;
    private Button topNext, topPrevious;
    private ApiCall apiCall;

    public Top() {
    }

    public static Top newInstance(int sectionNumber) {
        Top fragment = new Top();
        position = (sectionNumber - 1);
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
        topPageCount = (TextView) rootView.findViewById(R.id.topPageCount);

        topNext = (Button) rootView.findViewById(R.id.topNext);
        topPrevious = (Button) rootView.findViewById(R.id.topPrevious);

        topNext.setOnClickListener(this);
        topPrevious.setOnClickListener(this);


        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        apiCall = new ApiCall(mAdapter, position);
        apiCall.fetch(getContext());

    }


    // Set the page count in the TextView
    public static void setPageCount(int currentPage, int pageCount) {
        String text = "Page " + (currentPage + 1) + " of " +pageCount;
        topPageCount.setText(text);
    }



    //implementation on Button Click
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topNext:
                buttonState(apiCall.changePage(1));
                break;

            case R.id.topPrevious:
                buttonState(apiCall.changePage(0));
                break;
        }

    }

    /*
        0-next button
        1-previous button
        2-both buttons
    */

    private void buttonState(int getStatus) {
        switch (getStatus) {
            case 0:
                topNext.setEnabled(false);
                break;

            case 1:
                topPrevious.setEnabled(false);
                break;

            case 2:
                topNext.setEnabled(true);
                topPrevious.setEnabled(true);
                break;
        }
    }

}

