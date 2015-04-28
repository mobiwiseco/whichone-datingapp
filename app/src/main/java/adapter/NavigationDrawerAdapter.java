package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import akilliyazilim.whichone.R;
import model.DrawerItem;
import view.RobotoLightTextView;

/**
 * Created by mertsimsek on 11/01/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter{

    private ArrayList<DrawerItem> list;
    private Context context;
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context,ArrayList<DrawerItem> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null)
            v = inflater.inflate(R.layout.item_drawer_list,parent,false);

        RobotoLightTextView textview_item = (RobotoLightTextView) v.findViewById(R.id.textview_drawer_item);
        ImageView imageview_item = (ImageView) v.findViewById(R.id.imageview_drawer_item);

        DrawerItem drawer_item = list.get(position);
        if(drawer_item != null){
            textview_item.setText(drawer_item.getHeader());
            imageview_item.setBackgroundResource(drawer_item.getIcon_resource());
        }

        return v;
    }
}
