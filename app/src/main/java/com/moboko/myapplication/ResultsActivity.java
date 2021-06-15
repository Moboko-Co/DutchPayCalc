package com.moboko.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moboko.myapplication.entity.PaymentItemList;
import com.moboko.myapplication.entity.ResultsItemList;
import com.moboko.myapplication.utils.PayComparator;
import com.moboko.myapplication.view.PaymentAdapter;
import com.moboko.myapplication.view.ResultsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.moboko.myapplication.utils.CommonConsts.*;

public class ResultsActivity extends AppCompatActivity {

    // 遷移元との連結
    Intent intent = new Intent(ResultsActivity.this, MainActivity.class);

    // 画面の部品
    TextView tvAllPriceDetail;
    TextView tvAllPeopleDetail;
    TextView tvFraPeopleDetail;
    TextView tvColPeopleDetail;
    TextView tvOnePeopleDetail;
    TextView tvIgnorePeopleDetail;
    public static ListView collectListView;
    public static ResultsAdapter collectAdapter;

    public static ListView payListView;
    public static PaymentAdapter payAdapter;

    // toolbarにボタン配置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.results_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 戻るボタンをタップ
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = findViewById(R.id.tool_bar_results);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent fromIntent = getIntent();

        int bAllPeople = fromIntent.getIntExtra(RESULTS_ALL_PEOPLE, 0);
        int collDiv = fromIntent.getIntExtra(RESULTS_COLL_DIV_MODE, 1);
        int fractionDiv = fromIntent.getIntExtra(RESULTS_FRACTION_MODE, 1);
        int ignorePeople = fromIntent.getIntExtra(RESULTS_IGNORE_PEOPLE, 0);

        ArrayList<ResultsItemList> beforeCalcList = (ArrayList<ResultsItemList>) fromIntent.getSerializableExtra(RESULTS_DETAIL);

        // 部品へ値セット
        collectListView = findViewById(R.id.lv_collect_item);
        payListView = findViewById(R.id.lv_pay_item);

        tvAllPeopleDetail = findViewById(R.id.tv_all_people_detail);
        tvAllPriceDetail = findViewById(R.id.tv_all_price_detail);
        tvFraPeopleDetail = findViewById(R.id.tv_fra_people_detail);
        tvColPeopleDetail = findViewById(R.id.tv_col_people_detail);
        tvOnePeopleDetail = findViewById(R.id.tv_one_people_detail);
        tvIgnorePeopleDetail = findViewById(R.id.tv_ignore_people_detail);

        int allPeople = bAllPeople - ignorePeople;

        // 立替明細数
        int insteadPayCnt = beforeCalcList.size();

        // 立替明細集約
        Map<Integer, Integer> insteadPayMap = new HashMap<>();

        // 立替者支払い組
        Map<Integer, Integer> notEnoughPayMap = new HashMap<>();
        int notEnoughCnt = 0;

        // 立替者支払い不要組
        int enoughCnt = 0;

        // 支払い組
        ArrayList<PaymentItemList> paymentItemLists = new ArrayList<>();

        // 支払い者で割れる人のID
        int divPayId = 0;
        Map<Integer, Integer> divPayMap = new HashMap<>();
        // 0円設定
        divPayMap.put(divPayId, 0);

        // 集金組
        ArrayList<ResultsItemList> collectsItemLists = new ArrayList<>();
        ArrayList<ResultsItemList> calcItemLists;

        // 支払総額
        int allPrice = 0;

        // 計算前総額
        int bCalcAllPrice = 0;

        // 立替人別金額計算
        for (int i = 0; i < insteadPayCnt; i++) {

            // ID特定
            int pos = beforeCalcList.get(i).getId();

            // PRICE特定
            int tempPrice = beforeCalcList.get(i).getPrice();

            // 総額計算
            bCalcAllPrice += tempPrice;

            // すでに存在する場合、MERGE
            if (insteadPayMap.containsKey(pos)) {
                tempPrice += insteadPayMap.get(pos);
                insteadPayMap.remove(pos);
            }
            insteadPayMap.put(pos, tempPrice);
        }

        // 一人当たり金額
        int onePrice = 0;

        // 端数反映
        int fractionPrice = 0;

        // 計算結果余り
        int calcResultsMod = 0;

        if (bCalcAllPrice % allPeople != 0) {
            switch (collDiv) {
                case COLL_DIV_UP:
                    double tempCeil = (double) bCalcAllPrice / allPeople;
                    onePrice = (int) Math.ceil(tempCeil);
                    fractionPrice = onePrice % fractionDiv;
                    onePrice += (fractionDiv - fractionPrice);
                    calcResultsMod = (onePrice * allPeople) - bCalcAllPrice;
                    break;
                case COLL_DIV_DOWN:
                    double tempFloor = (double) bCalcAllPrice / allPeople;
                    onePrice = (int) Math.floor(tempFloor);
                    fractionPrice = onePrice % fractionDiv;
                    onePrice -= fractionPrice;
                    calcResultsMod = bCalcAllPrice - (onePrice * allPeople);

                    break;
            }
            allPrice = onePrice * allPeople;
        } else {
            onePrice = bCalcAllPrice / allPeople;
            allPrice = bCalcAllPrice;
        }
        // 集金額作成
        for (Map.Entry<Integer, Integer> collectMap : insteadPayMap.entrySet()) {
            if (collectMap.getValue() - onePrice > 0) {
                ResultsItemList itemList = new ResultsItemList(collectMap.getKey(), collectMap.getValue() - onePrice);
                collectsItemLists.add(itemList);
                enoughCnt++;
            } else if (collectMap.getValue() - onePrice < 0) {
                notEnoughCnt++;
                notEnoughPayMap.put(collectMap.getKey(), onePrice - collectMap.getValue());
            } else if (collectMap.getValue() - onePrice == 0) {
                enoughCnt++;
            }
        }

        calcItemLists = collectsItemLists;

        // 立替以外支払者
        int payPeopleCnt = allPeople - notEnoughCnt - enoughCnt;

        // 支払額作成
        for (int i = 0; i < calcItemLists.size(); i++) {
            int collectId = calcItemLists.get(i).getId();
            int collectPrice = calcItemLists.get(i).getPrice();
            for (Map.Entry<Integer, Integer> payMap : notEnoughPayMap.entrySet()) {
                int payId = payMap.getKey();
                int payPrice = payMap.getValue();
                int calcPrice = 0;

                if (collectPrice - payPrice > 0) {
                    // 支払い済みのため、削除
                    collectPrice -= payPrice;
                    calcPrice = payPrice;
                } else if (collectPrice - payPrice == 0) {
                    // 支払い済みのため、削除
                    collectPrice = 0;
                    calcPrice = payPrice;
                } else if (collectPrice - payPrice < 0) {
                    notEnoughPayMap.replace(payId, payPrice - collectPrice);
                    collectPrice = 0;
                    calcPrice = collectPrice;
                }
                PaymentItemList itemList = new PaymentItemList(payId, collectId, calcPrice, N_PAY);
                paymentItemLists.add(itemList);

                // 立替済みのため、パス
                if (collectPrice == 0) {
                    break;
                }
            }// 立替済みのため、パス
            if (collectPrice == 0) {
                continue;
            } else {
                while (true) {
                    // 支払いで割れる場合
                    if (collectPrice < onePrice) {
                        boolean fullPayFlag = false;
                        int calcPrice = 0;
                        // 割れる支払い者作成
                        int divPayPrice = divPayMap.get(divPayId);
                        int divMapPrice = 0;

                        if (collectPrice + divPayPrice > onePrice) {
                            divMapPrice = onePrice;
                            fullPayFlag = true;
                            calcPrice = onePrice - divPayPrice;
                            collectPrice = collectPrice - (onePrice - divPayPrice);
                        } else if (collectPrice + divPayPrice == onePrice) {
                            divMapPrice = onePrice;
                            fullPayFlag = true;
                            calcPrice = collectPrice;
                            collectPrice = 0;
                        } else if (collectPrice + divPayPrice < onePrice) {
                            divMapPrice = collectPrice + divPayPrice;
                            calcPrice = collectPrice;
                            collectPrice = 0;
                        }

                        PaymentItemList itemList = new PaymentItemList(divPayId, collectId, calcPrice, D_PAY);
                        paymentItemLists.add(itemList);

                        // すでに存在する場合、MERGE
                        divPayMap.remove(divPayId);
                        divPayMap.put(divPayId, divMapPrice);

                        if (fullPayFlag == true) {
                            divPayId++;
                            divPayMap.put(divPayId, 0);
                            fullPayFlag = false;
                        }
                    } else if (collectPrice >= onePrice) {
                        int calcPeopleCnt = collectPrice / onePrice;
                        PaymentItemList itemList = new PaymentItemList(calcPeopleCnt, collectId, onePrice, A_PAY);
                        paymentItemLists.add(itemList);
                        collectPrice = collectPrice - (onePrice * calcPeopleCnt);
                    }
                    if (collectPrice == 0) {
                        break;
                    }
                }
            }
        }

        tvAllPeopleDetail.setText(String.valueOf(bAllPeople) + PRICE_NIN);
        tvAllPriceDetail.setText(String.valueOf(bCalcAllPrice) + PRICE_YEN);
        tvFraPeopleDetail.setText(String.valueOf(calcResultsMod) + PRICE_YEN);
        tvColPeopleDetail.setText(String.valueOf(allPeople * onePrice) + PRICE_YEN);
        tvOnePeopleDetail.setText(String.valueOf(onePrice) + PRICE_YEN);
        tvIgnorePeopleDetail.setText(String.valueOf(ignorePeople) + PRICE_NIN);

        collectAdapter = new
                ResultsAdapter(
                ResultsActivity.this, R.layout.results_item_view, collectsItemLists);

        collectListView.setAdapter(collectAdapter);

        // ソート
        Collections.sort(paymentItemLists, new PayComparator());

        payAdapter = new
                PaymentAdapter(
                ResultsActivity.this, R.layout.payment_item_view, paymentItemLists);

        payListView.setAdapter(payAdapter);
    }
}