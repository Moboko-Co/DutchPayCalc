package com.moboko.myapplication.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

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
        final PrePayAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            holder = new PrePayAdapter.ViewHolder(convertView.findViewById(R.id.prepay_sp)
                    , convertView.findViewById(R.id.detail_input_et)
                    , convertView.findViewById(R.id.detail_delete_bt));
            convertView.setTag(holder);
        } else holder = (PrePayAdapter.ViewHolder) convertView.getTag();

        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add(PEOPLE_0);
        spinnerItems.add(PEOPLE_1);
        spinnerItems.add(PEOPLE_2);
        spinnerItems.add(PEOPLE_3);
        spinnerItems.add(PEOPLE_4);

        // ArrayAdapter
        ArrayAdapter<String> spinnerAdapter
                = new ArrayAdapter<String>(convertView.getContext(), R.layout.custom_spinner, spinnerItems);

        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        holder.setListItem(spinnerAdapter, itemLists.get(position));

        holder.detailDeleteBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemLists.remove(position);
                notifyDataSetChanged();
            }
        });
        ;
        holder.prepaySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int pos, long id) {
                itemLists.get(position).setId(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        holder.detailInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

//        detailInputEditText.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                ((ListView)parent).performItemClick(v,position,R.id.detail_input_et);
//            }
//        });;

        return convertView;
    }

    static class ViewHolder {
        private final Spinner prepaySp;
        private final EditText detailInputEditText;
        private final ImageButton detailDeleteBt;
        private ItemList item;


        ViewHolder(Spinner prepaySp, EditText detailInputEditText, ImageButton detailDeleteBt) {
            this.prepaySp = prepaySp;
            this.detailInputEditText = detailInputEditText;
            this.detailDeleteBt = detailDeleteBt;
            detailInputEditText.addTextChangedListener(textWatcher);
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (item != null) {
                    if (editable.toString().isEmpty()) {
                        item.setPrice(0);
                    } else item.setPrice(Integer.parseInt(editable.toString()));
                }
            }
        };

        void setListItem(ArrayAdapter<String> spinnerAdapter, ItemList item) {
            this.item = item;
            // spinner に adapter をセット
            prepaySp.setAdapter(spinnerAdapter);
            int id = item.getId();
            prepaySp.setSelection(id);
            detailInputEditText.setText(String.valueOf(item.getPrice()));
        }
    }
}
