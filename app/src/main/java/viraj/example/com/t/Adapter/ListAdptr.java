package viraj.example.com.t.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import viraj.example.com.t.Network.BitmapLruCache;
import viraj.example.com.t.Network.MySingleton;
import viraj.example.com.t.Pojo.ItemInfo;
import viraj.example.com.t.R;

public class ListAdptr extends ArrayAdapter<ItemInfo> {
    private ImageLoader mImageLoader;

    public ListAdptr(Context context) {
        super(context, R.layout.custom_view);

        mImageLoader = new ImageLoader(MySingleton.getInstance(context).getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_view, parent, false);
        }

        // NOTE: You would normally use the ViewHolder pattern here
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);


        ItemInfo itemInfo = getItem(position);

        imageView.setImageUrl(itemInfo.getImageUrl(), mImageLoader);
        title.setText(itemInfo.getTitle());
        description.setText(itemInfo.getDescription());

        return convertView;
    }

    public void notifyAdapter(List<ItemInfo> objects) {
        clear();

        for(ItemInfo object : objects) {
            add(object);
        }

        notifyDataSetChanged();
    }
}
