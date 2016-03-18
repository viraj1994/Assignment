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

public class Featured extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListAdptr mAdapter;
    private View rootView;
    private static int position;
    public static TextView featurePageCount;
    private Button featureNext, featurePrevious;
    private ApiCall apiCall;

    public Featured() {
    }

    public static Featured newInstance(int sectionNumber) {
        Featured fragment = new Featured();
        position = (sectionNumber - 1);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_featured, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ListAdptr(getActivity());
        featurePageCount = (TextView) rootView.findViewById(R.id.featurePageCount);
        ListView listView = (ListView) rootView.findViewById(R.id.list_featured);
        listView.setAdapter(mAdapter);

        featureNext = (Button) rootView.findViewById(R.id.featureNext);
        featurePrevious = (Button) rootView.findViewById(R.id.featurePrevious);

        featureNext.setOnClickListener(this);
        featurePrevious.setOnClickListener(this);


        apiCall = new ApiCall(mAdapter, position);
        apiCall.fetch(getContext());

    }

    // Set the page count in the TextView
    public static void setPageCount(int currentPage, int pageCount) {
        String text = "Page " + (currentPage + 1) + " of " +pageCount;
        featurePageCount.setText(text);
    }


    //implementation on Button Click
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.featureNext:
                buttonState(apiCall.changePage(1));
                break;

            case R.id.featurePrevious:
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
                featureNext.setEnabled(false);
                break;

            case 1:
                featurePrevious.setEnabled(false);
                break;

            case 2:
                featureNext.setEnabled(true);
                featurePrevious.setEnabled(true);
                break;
        }
    }
}
