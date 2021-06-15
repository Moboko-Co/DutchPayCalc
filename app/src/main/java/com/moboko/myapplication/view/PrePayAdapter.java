package com.moboko.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;

import com.moboko.myapplication.R;
import com.moboko.myapplication.entity.ItemList;

import java.util.ArrayList;

import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_0;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_1;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_2;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_3;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_4;

public class PrePayAdapter extends BaseAdapter {
    private Context mContext;
    private int layout = 0;
    private ArrayList<ItemList> itemLists;
    private LayoutInflater inflater = null;
    Spinner prepaySp;
    Button detailInputBt;
    ImageButton detailDeleteBt;

    public PrePayAdapter(Context c, int l, ArrayList<ItemList> itemLists) {
        this.mContext = c;
        this.layout = l;
        this.itemLists = itemLists;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return itemLists.size();
    }

    @Override
    public Object getItem(int position) {
        return itemLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }

        detailDeleteBt = convertView.findViewById(R.id.detail_delete_bt);
        detailDeleteBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemLists.remove(position);
                notifyDataSetChanged();
            }
        });;


        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add(PEOPLE_0);
        spinnerItems.add(PEOPLE_1);
        spinnerItems.add(PEOPLE_2);
        spinnerItems.add(PEOPLE_3);
        spinnerItems.add(PEOPLE_4);

        // ArrayAdapter
        ArrayAdapter<String> spinnerAdapter
                = new ArrayAdapter<String>(convertView.getContext(), android.R.layout.simple_spinner_item, spinnerItems);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinner に adapter をセット
        prepaySp = convertView.findViewById(R.id.prepay_sp);
        prepaySp.setAdapter(spinnerAdapter);
        int id = itemLists.get(position).getId();
        prepaySp.setSelection(id);

        prepaySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int pos, long id)
            {
                ItemList item = new ItemList();
                item.setId(pos);
                item.setPrice(itemLists.get(position).getPrice());
                itemLists.set(position,item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {}
        });

        detailInputBt = convertView.findViewById(R.id.detail_input_bt);

        int price = itemLists.get(position).getPrice();
        detailInputBt.setText(String.valueOf(price));

        detailInputBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v,position,R.id.detail_input_bt);
            }
        });;

        return convertView;
    }

}
