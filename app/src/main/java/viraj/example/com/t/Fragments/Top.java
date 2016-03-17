package viraj.example.com.t.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import viraj.example.com.t.Adapter.ListAdptr;
import viraj.example.com.t.Network.ApiCall;
import viraj.example.com.t.R;

/**
 * Created by viraj on 17-03-2016.
 */
public class Top extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListAdptr mAdapter;
    View rootView;
    private static int position;

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

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mAdapter);
        new ApiCall().fetch(getContext(), position, mAdapter);
    }


}

