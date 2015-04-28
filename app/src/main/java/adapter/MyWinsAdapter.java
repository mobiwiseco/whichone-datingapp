package adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import akilliyazilim.whichone.R;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Win;
import view.RobotoLightTextView;

/**
 * Created by mertsimsek on 11/01/15.
 */
public class MyWinsAdapter extends BaseAdapter{

    private ArrayList<Win> win_list;
    private LayoutInflater inflater;
    private Context context;

    public MyWinsAdapter(Context context) {
        win_list = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<Win> win_list){
        this.win_list = win_list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return win_list.size();
    }

    @Override
    public Object getItem(int position) {
        return win_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WinViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_win,parent,false);
            viewHolder = initializeViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (WinViewHolder) convertView.getTag();

        Win win = win_list.get(position);

        if(win != null){
            Picasso.with(context).load(win.getVoter().getImage_url()).into(viewHolder.imageview_voter);
            viewHolder.textview_voter_name.setText(win.getVoter().getUser_name() + " " + win.getVoter().getUser_surname());
            viewHolder.textview_win_info.setText(Html.fromHtml(context.getResources().getString(R.string.win_subtitle) + ". " +  win.getLoser().getUser_name() + " " + context.getResources().getString(R.string.win_subtitle_lost)));
        }
        return convertView;
    }

    private WinViewHolder initializeViewHolder(View v){
        WinViewHolder view_holder = new WinViewHolder();
        view_holder.imageview_voter = (CircleImageView) v.findViewById(R.id.imageview_voter);
        view_holder.textview_voter_name = (RobotoLightTextView) v.findViewById(R.id.textview_voter_name);
        view_holder.textview_win_info = (RobotoLightTextView) v.findViewById(R.id.textview_loser_info);
        return view_holder;
    }

    private static class WinViewHolder{
        CircleImageView imageview_voter;
        RobotoLightTextView textview_voter_name;
        RobotoLightTextView textview_win_info;
    }
}
