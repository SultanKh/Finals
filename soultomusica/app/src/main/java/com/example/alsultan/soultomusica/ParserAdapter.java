package com.example.alsultan.soultomusica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
    FOR THE SAKE OF USING_YOUTBE
 * Adapter class which includes EntryDao object.
 * It helps to link elements of UI(ListView) with DB elements(DAO)
 */
public class ParserAdapter extends BaseAdapter {

    private List<Entry> entryList;
    private EntryDao entryDao;
    private Context context;
    private LayoutInflater layoutInflater;

    public ParserAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        entryDao = new EntryDao(context);
        entryList = new ArrayList<Entry>(entryDao.selectAll());
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Entry getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);

        Entry entry = getItem(position);
        ((TextView) view.findViewById(R.id.item_text)).setText(entry.toString());
        if (entry.isYoutube())
            ((ImageView) view.findViewById(R.id.item_img)).setImageResource(R.drawable.cryme);
        else
            ((ImageView) view.findViewById(R.id.item_img)).setImageResource(R.drawable.cryme);

        return view;
    }

    public boolean add(Entry entry) {
        return entryList.add(entry);
    }


    public void clear() {
        entryList.clear();
    }

    public void closeAll() {
        entryDao.closeAll();
    }

    public void addItemsToDB() {
        entryDao.insertEntriesToDB(entryList);
    }
}