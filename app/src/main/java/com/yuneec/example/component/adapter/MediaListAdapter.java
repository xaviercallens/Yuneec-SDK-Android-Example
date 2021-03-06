package com.yuneec.example.component.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuneec.example.R;
import com.yuneec.example.component.fragment.CameraFragment;
import com.yuneec.example.model.MediaInfoEntry;
import com.yuneec.sdk.Camera;

import java.util.ArrayList;

/**
 * Created by sushmas on 8/29/17.
 */

public class MediaListAdapter
    extends BaseAdapter {

    private ArrayList<MediaInfoEntry> entries;

    private LayoutInflater inflater;

    public MediaListAdapter(Context context,
                            ArrayList<MediaInfoEntry> list) {

        entries = list;
        inflater = LayoutInflater.from(context);
    }

    public MediaInfoEntry entryFromMediaInfo(Camera.MediaInfo mediaInfo) {

        MediaInfoEntry entry = new MediaInfoEntry();
        entry.path = mediaInfo.path;
        entry.description = String.format("%.1f MiB", mediaInfo.size_mib);
        // We want to split "100MEDIA/YUN00001.jpg" to "YUN00001.jpg"
        String[] parts = entry.path.split("/");
        entry.title = parts[1];

        return entry;
    }

    public void setEntries(ArrayList<Camera.MediaInfo> list) {

        entries.clear();
        for (Camera.MediaInfo item : list) {
            entries.add(entryFromMediaInfo(item));
        }
    }

    @Override
    public int getCount() {

        return entries.size();
    }

    @Override
    public MediaInfoEntry getItem(int index) {

        return entries.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }

    public View getView(int index,
                        View convertView,
                        ViewGroup parent) {

        MediaListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new MediaListAdapter.ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (MediaListAdapter.ViewHolder) convertView.getTag();
        }

        MediaInfoEntry entry = entries.get(index);
        holder.text1.setText(entry.title);
        holder.text2.setText(entry.description);

        if (entry.downloaded) {
            convertView.setBackgroundColor(Color.parseColor("#F04C24"));
            holder.text1.setBackgroundColor(Color.parseColor("#F04C24"));
            holder.text2.setBackgroundColor(Color.parseColor("#F04C24"));
        } else {
            convertView.setBackgroundColor(Color.WHITE);
            holder.text1.setBackgroundColor(Color.WHITE);
            holder.text2.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    class ViewHolder {

        TextView text1, text2;
    }
}
