package com.moboko.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moboko.myapplication.R;
import com.moboko.myapplication.entity.PaymentItemList;
import com.moboko.myapplication.entity.ResultsItemList;

import java.util.ArrayList;

import static com.moboko.myapplication.utils.CommonConsts.A_PAY;
import static com.moboko.myapplication.utils.CommonConsts.D_PAY;
import static com.moboko.myapplication.utils.CommonConsts.N_PAY;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_0;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_1;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_2;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_3;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_4;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_CNT;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_ETC;
import static com.moboko.myapplication.utils.CommonConsts.PEOPLE_FRA;
import static com.moboko.myapplication.utils.CommonConsts.PRICE_YEN;

public class PaymentAdapter extends BaseAdapter {
    private Context mContext;
    private int layout = 0;
    private ArrayList<PaymentItemList> paymentItemLists;
    private LayoutInflater inflater = null;

    TextView tvFromPayPeople, tvToPayPeople, tvPayPrice;

    public PaymentAdapter(Context c, int l, ArrayList<PaymentItemList> paymentItemLists) {
        this.mContext = c;
        this.layout = l;
        this.paymentItemLists = paymentItemLists;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return paymentItemLists.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentItemLists.get(position);
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

        int fromId = paymentItemLists.get(position).getFromId();
        int toId = paymentItemLists.get(position).getToId();
        int price = paymentItemLists.get(position).getPrice();
        int typeDiv = paymentItemLists.get(position).getTypeDiv();

        String fromPayPeopleId = new String();
        String toPayPeopleId = new String();

        switch (toId) {
            case 0:
                toPayPeopleId = PEOPLE_0;
                break;
            case 1:
                toPayPeopleId = PEOPLE_1;
                break;
            case 2:
                toPayPeopleId = PEOPLE_2;
                break;
            case 3:
                toPayPeopleId = PEOPLE_3;
                break;
            case 4:
                toPayPeopleId = PEOPLE_4;
                break;
        }

        switch (typeDiv) {
            case N_PAY:
                switch (fromId) {
                    case 0:
                        fromPayPeopleId = PEOPLE_0;
                        break;
                    case 1:
                        fromPayPeopleId = PEOPLE_1;
                        break;
                    case 2:
                        fromPayPeopleId = PEOPLE_2;
                        break;
                    case 3:
                        fromPayPeopleId = PEOPLE_3;
                        break;
                    case 4:
                        fromPayPeopleId = PEOPLE_4;
                        break;
                }
                break;
            case D_PAY:
                if (position != 0
                        && paymentItemLists.get(position - 1).getTypeDiv() == D_PAY
                        && paymentItemLists.get(position - 1).getFromId() == fromId) {
                    fromPayPeopleId = " ";

                } else {
                    fromPayPeopleId = "1名";
                }
                break;
            case A_PAY:
                fromPayPeopleId = Integer.toString(fromId) + "名";
                break;
        }

        tvFromPayPeople = convertView.findViewById(R.id.tv_from_pay_people);
        tvToPayPeople = convertView.findViewById(R.id.tv_to_pay_people);
        tvPayPrice = convertView.findViewById(R.id.tv_pay_price);

        tvFromPayPeople.setText(fromPayPeopleId);
        tvToPayPeople.setText(toPayPeopleId);
        if (price < 0) {
            price = price * (-1);
        }
        String priceText = String.format("%,d" + PRICE_YEN, price);
        tvPayPrice.setText(priceText);

        return convertView;
    }

}
