package com.moboko.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.moboko.myapplication.entity.ItemList;
import com.moboko.myapplication.entity.ResultsItemList;
import com.moboko.myapplication.utils.CommonConsts;
import com.moboko.myapplication.utils.ShowErrorToast;
import com.moboko.myapplication.view.PrePayAdapter;

import java.util.ArrayList;

import static com.moboko.myapplication.utils.CommonConsts.*;

public class MainActivity extends AppCompatActivity implements CalcFragment.CalcFragmentListener {

    private FragmentManager fragmentManager;
    public static ListView listView;
    public static PrePayAdapter adapter;
    Button plusButton;
    EditText allPeopleEditText, ignorePeopleEditText;
    RadioGroup rgFraction;
    //RadioGroup rgCollDiv;
    ArrayList<ItemList> itemLists = new ArrayList<>();

    // toolbarにボタン配置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ib_calc_submit:
                //入力された値の取得

                // 総人数
                String allPeopleSt = String.valueOf(allPeopleEditText.getText());
                String ignorePeopleSt = String.valueOf(ignorePeopleEditText.getText());

                if(allPeopleSt.isEmpty()) allPeopleSt = "0";
                if(ignorePeopleSt.isEmpty()) ignorePeopleSt = "0";
                int allPeople = Integer.parseInt(allPeopleSt);
                int ignorePeople = Integer.parseInt(ignorePeopleSt);

                // 明細
                ArrayList<ResultsItemList> resultsItemLists = new ArrayList<>();
                int itemListCnt = adapter.getCount();

                // 総人数 値チェック
                if (allPeople == 0) {
                    ShowErrorToast.printErrorToast(this, RESULTS_ALL_PEOPLE, VALUE_ZERO);
                    return true;
                }
                if(itemListCnt == 0){
                    ShowErrorToast.printErrorToast(this, RESULTS_DETAIL, DETAIL_ZERO);
                    return true;
                }

                if(ignorePeople >= allPeople){
                    ShowErrorToast.printErrorToast(this, RESULTS_IGNORE_PEOPLE, VALUE_MAX);
                    return true;
                }

                // 徴収区分
                int collDiv = COLL_DIV_UP;
//                switch (rgCollDiv.getCheckedRadioButtonId()) {
//                    case R.id.rb_c_d_1:
//                        collDiv = COLL_DIV_UP;
//                        break;
//                    case R.id.rb_c_d_2:
//                        collDiv = COLL_DIV_DOWN;
//                        break;
//                }

                // 端数
                int fractionDiv = FRA_1;
                switch (rgFraction.getCheckedRadioButtonId()) {
                    case R.id.rb_c_1:
                        fractionDiv = FRA_1;
                        break;
                    case R.id.rb_c_2:
                        fractionDiv = FRA_10;
                        break;
                    case R.id.rb_c_3:
                        fractionDiv = FRA_100;
                        break;
                    case R.id.rb_c_4:
                        fractionDiv = FRA_1000;
                        break;
                }

                // 明細取得
                for (int i = 0; i < itemListCnt; i++) {
                    ItemList itemList = (ItemList) adapter.getItem(i);
                    if (itemList.getPrice() == 0) {
                        ShowErrorToast.printErrorToast(this, RESULTS_DETAIL, VALUE_ZERO);
                        return true;
                    }
                    ResultsItemList resultsItem = new ResultsItemList(itemList.getId(), itemList.getPrice());
                    resultsItemLists.add(resultsItem);
                }

                // intentセット
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);

                intent.putExtra(RESULTS_ALL_PEOPLE, allPeople);
                intent.putExtra(RESULTS_COLL_DIV_MODE, collDiv);
                intent.putExtra(RESULTS_FRACTION_MODE, fractionDiv);
                intent.putExtra(RESULTS_DETAIL, resultsItemLists);
                intent.putExtra(RESULTS_IGNORE_PEOPLE, ignorePeople);

                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle(ALERT_TITLE_RESULTS)
                        .setMessage(ALERT_MESSAGE_RESULTS)
                        .setPositiveButton(ALERT_YES, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // activity遷移
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(ALERT_NO, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fragmentManager = getSupportFragmentManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        plusButton = findViewById(R.id.detail_plus_bt);

        allPeopleEditText = findViewById(R.id.all_people_et);
        allPeopleEditText.setOnKeyListener(enterClick);
        allPeopleEditText.setText("0");

        ignorePeopleEditText = findViewById(R.id.ignore_people_et);
        ignorePeopleEditText.setOnKeyListener(enterClick);
        ignorePeopleEditText.setText("0");

        listView = findViewById(R.id.item_lv);
        //rgCollDiv = findViewById(R.id.rg_col_div);
        rgFraction = findViewById(R.id.rg_fraction);
        rgFraction.check(R.id.rb_c_1);
        adapter = new PrePayAdapter(
                MainActivity.this, R.layout.item_view, itemLists);

        plusButton.setOnClickListener(v -> {
            //最大明細数チェック
            if (adapter.getCount() < MAX_PREPAY_CNT) {
                //新しい明細情報セット
                ItemList newData = new ItemList();
                newData.setId(0);
                newData.setPrice(0);

                //明細追加
                itemLists.add(newData);

                //アダプタに反映
                listView.setAdapter(adapter);
            } else {
                ShowErrorToast.printErrorToast(this, RESULTS_DETAIL, DETAIL_MAX);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getId() == R.id.detail_input_et) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(FRAGMENT_MODE, DETAIL_PRICE);
                    bundle.putInt(DETAIL_MODE, i);
                    callFragment(bundle);
                    // edit function for i;
                }
            }
        });
    }

    private View.OnKeyListener enterClick = new View.OnKeyListener(){

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        }
    };

//    private View.OnClickListener buttonClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Bundle bundle = new Bundle();
//
//            switch (view.getId()) {
////                case R.id.all_price_bt:
////                    bundle.putInt(FRAGMENT_MODE, ALL_PRICE);
////                    bundle.putInt(DETAIL_MODE, 0);
////                    break;
//                case R.id.all_people_et:
//                    //　表示するフラグメントクラスのインスタンス
//                    bundle.putInt(FRAGMENT_MODE, ALL_PEOPLE);
//                    bundle.putInt(DETAIL_MODE, 0);
//                    break;
//                case R.id.ignore_people_et:
//                    //　表示するフラグメントクラスのインスタンス
//                    bundle.putInt(FRAGMENT_MODE, IGNORE_PEOPLE);
//                    bundle.putInt(DETAIL_MODE, 0);
//                    break;
//            }
//
//            callFragment(bundle);
//        }
//    };

    @Override
    public void onClickButton(String value, int mode, int pos) {
        switch (mode) {
            case ALL_PEOPLE:
                EditText allPeopleEditText = findViewById(R.id.all_people_et);
                allPeopleEditText.setText(value);
                break;
            case IGNORE_PEOPLE:
                EditText ignorePeopleEditText = findViewById(R.id.ignore_people_et);
                ignorePeopleEditText.setText(value);
                break;
//            case ALL_PRICE:
//                Button allPriceButton = findViewById(R.id.all_price_bt);
//                allPriceButton.setText(value);
//                break;
            case DETAIL_PRICE:
                ItemList item = (ItemList) adapter.getItem(pos);
                item.setPrice(Integer.parseInt(value));
                itemLists.set(pos, item);
                listView.setAdapter(adapter);
                break;
        }
        getSupportFragmentManager().popBackStack();
    }

    public void callFragment(Bundle bundle) {
        //　表示するフラグメントクラスのインスタンス
        final Fragment fragment = new CalcFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // フラグメントに渡す値をセット
        fragment.setArguments(bundle);

        // BackStackを設定
        transaction.addToBackStack(null);

        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }
}