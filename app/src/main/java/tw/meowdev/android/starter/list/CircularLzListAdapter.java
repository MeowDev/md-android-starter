package tw.meowdev.android.starter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.util.Log;

public class CircularLzListAdapter<E> extends BaseAdapter {

    private CircularLzList<E> clzList;
    private LayoutInflater inflater;

    public CircularLzListAdapter(Context context, CircularLzList<E> clzList) {
        this.inflater = LayoutInflater.from(context);
        this.clzList = clzList;
    }

    @Override
    public int getCount() {
        return clzList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        E e = clzList.get(clzList.start()+position);
        Log.d("clz", "pos:"+position);
        Log.d("clz", "start:"+clzList.start());
        Log.d("clz", "size:"+clzList.size());
        Log.d("clz", "e:"+e);
        TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
        textView.setText(e.toString());

        return convertView;
    }

    public long getGlobalIndex(int position)
    {
        return clzList.start()+position;
    }
}
