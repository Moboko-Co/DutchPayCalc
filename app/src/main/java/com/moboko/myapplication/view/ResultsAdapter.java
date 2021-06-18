package com.moboko.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moboko.myapplication.R;
import com.moboko.myapplication.entity.ResultsItemList;

import java.util.ArrayList;

import static com.moboko.myapplication.utils.CommonConsts.COLLECT_FLAG;
import static com.moboko.myapplication.utils.CommonConsts.PAYMENT_FLAG;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_0;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_1;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_2;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_3;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_4;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_CNT;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_ETC;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_FRA;
import static com.moboko.myapplication.utils.CommonConsts.PRICE_YEN;

public class ResultsAdapter extends BaseAdapter {
    private Context mContext;
    private int layout = 0;
    private ArrayList<ResultsItemList> resultsItemLists;
    private LayoutInflater inflater = null;

    TextView tvResultPeople, tvResultPrice;

    public ResultsAdapter(Context c, int l, ArrayList<ResultsItemList> resultsItemLists) {
        this.mContext = c;
        this.layout = l;
        this.resultsItemLists = resultsItemLists;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return resultsItemLists.size();
    }

    @Override
    public Object getItem(int position) {
        return resultsItemLists.get(position);
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

        int id = resultsItemLists.get(position).getId();
        int price = resultsItemLists.get(position).getPrice();

        String resultPeopleId = new String();

        switch (id) {
            case 0:
                resultPeopleId = PEOPLE_0;
                break;
            case 1:
                resultPeopleId = PEOPLE_1;
                break;
            case 2:
                resultPeopleId = PEOPLE_2;
                break;
            case 3:
                resultPeopleId = PEOPLE_3;
                break;
            case 4:
                resultPeopleId = PEOPLE_4;
                break;
            case PEOPLE_CNT:
                resultPeopleId = PEOPLE_ETC;
                break;
            case PEOPLE_CNT + 1:
                resultPeopleId = PEOPLE_FRA;
                break;
        }

        tvResultPeople = convertView.findViewById(R.id.tv_collect_people);
        tvResultPrice = convertView.findViewById(R.id.tv_collect_price);

        tvResultPeople.setText(resultPeopleId);
        if (price > 0) {
        } else if (price < 0) {
            price = price * (-1);
        }
        String priceText = String.format("%,d" + PRICE_YEN, price);
        tvResultPrice.setText(priceText);

        return convertView;
    }

}
