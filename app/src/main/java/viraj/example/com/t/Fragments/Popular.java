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

public  class Popular extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListAdptr mAdapter;
    private View rootView;
    private static int position;
    private static TextView popularPageCount;
    private ApiCall apiCall;
    private Button popularNext, popularPrevious;

    public Popular() {
    }

    public static Popular newInstance(int sectionNumber) {
        Popular fragment = new Popular();
        position = (sectionNumber - 1);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ListAdptr(getActivity());
        popularPageCount = (TextView) rootView.findViewById(R.id.popularPageCount);

        ListView listView = (ListView) rootView.findViewById(R.id.list_popular);
        listView.setAdapter(mAdapter);

        popularNext = (Button) rootView.findViewById(R.id.popularNext);
        popularPrevious = (Button) rootView.findViewById(R.id.popularPrevious);

        popularNext.setOnClickListener(this);
        popularPrevious.setOnClickListener(this);

        changeButtonState(1);

        apiCall = new ApiCall(mAdapter, position);
        apiCall.fetch(getContext());



    }

    // Set the page count in the TextView
    public static void setPageCount(int currentPage, int pageCount) {
        String text = "Page " + (currentPage + 1) + " of " +pageCount;
        popularPageCount.setText(text);
    }


     /*
    implementation on Button Click send
    0- if previous button is clicked
     1- if next button is clicked
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.popularNext:
                apiCall.changePage(1);
                changeButtonState(apiCall.getButtonState());
                break;

            case R.id.popularPrevious:
                apiCall.changePage(0);
                changeButtonState(apiCall.getButtonState());
                break;
        }
    }


    /*
        0-next button
        1-previous button
        2-both buttons
     */

    private void changeButtonState(int getStatus) {
        switch (getStatus) {
            case 0:
                popularNext.setEnabled(false);
                popularPrevious.setEnabled(true);
                break;

            case 1:
                popularPrevious.setEnabled(false);
                popularNext.setEnabled(true);
                break;

            case 2:
                popularNext.setEnabled(true);
                popularPrevious.setEnabled(true);
                break;
        }
    }
}
