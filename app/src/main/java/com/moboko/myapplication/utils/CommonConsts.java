package com.moboko.myapplication.utils;

public class CommonConsts {
    public static final int MAX_PREPAY_CNT = 10;

    public static final int PEOPLE_CNT = 5;
    public static final String PEOPLE_0 = "Aさん";
    public static final String PEOPLE_1 = "Bさん";
    public static final String PEOPLE_2 = "Cさん";
    public static final String PEOPLE_3 = "Dさん";
    public static final String PEOPLE_4 = "Eさん";
    public static final String PEOPLE_ETC = "他一人当たり";
    public static final String PEOPLE_FRA = "端数";

    public static final String COLLECT_FLAG = "回収";
    public static final String PAYMENT_FLAG = "支払";

    public static final String DIGIT_1 = "1";
    public static final String DIGIT_2 = "2";
    public static final String DIGIT_3 = "3";
    public static final String DIGIT_4 = "4";
    public static final String DIGIT_5 = "5";
    public static final String DIGIT_6 = "6";
    public static final String DIGIT_7 = "7";
    public static final String DIGIT_8 = "8";
    public static final String DIGIT_9 = "9";
    public static final String DIGIT_0 = "0";
    public static final String DIGIT_00 = "00";
    public static final String DIGIT_000 = "000";
    public static final String DIGIT_DIVISION = "÷";
    public static final String DIGIT_MULTIPLE = "×";
    public static final String DIGIT_PLUS = "+";
    public static final String DIGIT_MINUS = "-";

    public static final String PRICE_YEN = "円";
    public static final String PRICE_NIN = "人";

    public static final int MAX_DIGIT_LEN = 6;

    public static final int COLL_DIV_UP = 1;
    public static final int COLL_DIV_DOWN = 2;

    public static final int FRA_1000 = 1000;
    public static final int FRA_100 = 100;
    public static final int FRA_10 = 10;
    public static final int FRA_1 = 1;




    public static final String FRAGMENT_MODE = "fragment_mode";
    public static final String DETAIL_MODE = "detail_mode";
    public static final int ALL_PRICE = 1;
    public static final int ALL_PEOPLE = 2;
    public static final int DETAIL_PRICE = 3;
    public static final int IGNORE_PEOPLE = 4;

    public static final String RESULTS_ALL_PEOPLE = "総人数";
    public static final String RESULTS_IGNORE_PEOPLE = "免除人数";
    public static final String RESULTS_COLL_DIV_MODE = "徴収区分";
    public static final String RESULTS_FRACTION_MODE = "端数単位";
    public static final String RESULTS_DETAIL = "立替明細";

    public static final String VALUE_ZERO = "value_zero";
    public static final String VALUE_MAX = "value_max";
    public static final String DETAIL_ZERO = "detail_zero";
    public static final String DETAIL_MAX = "detail_max";

    //エラーメッセージ
    public static final String ERROR_MAX_DETAIL = "登録できる明細数は" + MAX_PREPAY_CNT + "件です";

    // 警告メッセージ
    public static final String ALERT_TITLE_RESULTS = "計算結果";
    public static final String ALERT_MESSAGE_RESULTS = "計算結果を表示しますか";
    public static final String ALERT_YES = "はい";
    public static final String ALERT_NO = "いいえ";


    // 支払い区分
    public static final int N_PAY = 1;
    public static final int D_PAY = 2;
    public static final int A_PAY = 3;

}
