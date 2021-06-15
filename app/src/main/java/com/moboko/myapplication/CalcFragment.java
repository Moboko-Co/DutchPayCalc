package com.moboko.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.moboko.myapplication.utils.CommonConsts.*;

public class CalcFragment extends Fragment implements View.OnClickListener {

    // 1. ボタンが押された時に呼ぶメソッドを持つインターフェイス
    public interface CalcFragmentListener {
        void onClickButton(String value, int mode, int pos);
    }

    // 2. 上記インターフェイスのフィールド
    private CalcFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 3. context を listener に代入
        if (context instanceof CalcFragmentListener) {
            listener = (CalcFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.CalcFragTheme);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View view = inflater.inflate(R.layout.calc_fragment, container, false);

        Button calcDivisionBt = view.findViewById(R.id.calc_division_bt);
        Button calcSevenBt = view.findViewById(R.id.calc_seven_bt);
        Button calcEightBt = view.findViewById(R.id.calc_eight_bt);
        Button calcNineBt = view.findViewById(R.id.calc_nine_bt);
        Button calcMultipleBt = view.findViewById(R.id.calc_multiple_bt);
        Button calcFourBt = view.findViewById(R.id.calc_four_bt);
        Button calcFiveBt = view.findViewById(R.id.calc_five_bt);
        Button calcSixBt = view.findViewById(R.id.calc_six_bt);
        Button calcMinusBt = view.findViewById(R.id.calc_minus_bt);
        Button calcOneBt = view.findViewById(R.id.calc_one_bt);
        Button calcTwoBt = view.findViewById(R.id.calc_two_bt);
        Button calcThreeBt = view.findViewById(R.id.calc_three_bt);
        Button calcPlusBt = view.findViewById(R.id.calc_plus_bt);
        Button calcZeroBt = view.findViewById(R.id.calc_zero_bt);
        Button calcTwoZeroBt = view.findViewById(R.id.calc_two_zero_bt);
//        Button calcThreeZeroBt = view.findViewById(R.id.calc_three_zero_bt);
        Button calcEqualBt = view.findViewById(R.id.calc_equal_bt);
        Button calcClearBt = view.findViewById(R.id.calc_clear_bt);
        Button calcCommitBt = view.findViewById(R.id.calc_commit_bt);

        calcDivisionBt.setOnClickListener(this);
        calcSevenBt.setOnClickListener(this);
        calcEightBt.setOnClickListener(this);
        calcNineBt.setOnClickListener(this);
        calcMultipleBt.setOnClickListener(this);
        calcFourBt.setOnClickListener(this);
        calcFiveBt.setOnClickListener(this);
        calcSixBt.setOnClickListener(this);
        calcMinusBt.setOnClickListener(this);
        calcOneBt.setOnClickListener(this);
        calcTwoBt.setOnClickListener(this);
        calcThreeBt.setOnClickListener(this);
        calcPlusBt.setOnClickListener(this);
        calcZeroBt.setOnClickListener(this);
        calcTwoZeroBt.setOnClickListener(this);
//        calcThreeZeroBt.setOnClickListener(this);
        calcEqualBt.setOnClickListener(this);
        calcClearBt.setOnClickListener(this);
        calcCommitBt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        String digit = new String();
        TextView calcResultText = getView().findViewById(R.id.calc_result_text);
        TextView calcRecentText = getView().findViewById(R.id.calc_recent_text);

        String resultDigit = calcResultText.getText().toString();
        String recentDigit = calcRecentText.getText().toString();

        boolean execCalc = false, execSign = false;
        boolean execClear = false;
        boolean replaceSign = false;
        switch (view.getId()) {
            case R.id.calc_division_bt:
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) {
                    digit = DIGIT_DIVISION;
                    execSign = true;
                    if (cntSignStr(resultDigit) > 0) execCalc = true;
                }
                break;
            case R.id.calc_seven_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_7;
                break;
            case R.id.calc_eight_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_8;
                break;
            case R.id.calc_nine_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_9;
                break;
            case R.id.calc_multiple_bt:
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) {
                    digit = DIGIT_MULTIPLE;
                    execSign = true;
                    if (cntSignStr(resultDigit) > 0) execCalc = true;
                }
                break;
            case R.id.calc_four_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_4;
                break;
            case R.id.calc_five_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_5;
                break;
            case R.id.calc_six_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_6;
                break;
            case R.id.calc_minus_bt:
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) {
                    digit = DIGIT_MINUS;
                    execSign = true;
                    if (cntSignStr(resultDigit) > 0) execCalc = true;
                }
                break;
            case R.id.calc_one_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_1;
                break;
            case R.id.calc_two_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_2;
                break;
            case R.id.calc_three_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                digit = DIGIT_3;
                break;
            case R.id.calc_plus_bt:
                if (resultDigit.length() > 0){
                    digit = DIGIT_PLUS;
                    if(isLastStrNum(resultDigit)) {
                        execSign = true;
                        if (cntSignStr(resultDigit) > 0) execCalc = true;
                    } else {
                        replaceSign = true;
                    }
                }
                break;
            case R.id.calc_zero_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) digit = DIGIT_0;
                break;
            case R.id.calc_two_zero_bt:
                if (afterEqual(resultDigit, recentDigit)) break;
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) digit = DIGIT_00;
                break;
//            case R.id.calc_three_zero_bt:
//            if(afterEqual(resultDigit,recentDigit)) break;
//                if (resultDigit.length() > 0 && isLastStrNum(resultDigit)) digit = DIGIT_000;
//                break;
            case R.id.calc_equal_bt:
                if (resultDigit.length() > 0 && isLastStrNum(resultDigit) && cntSignStr(resultDigit) > 0)
                    execCalc = true;
                break;
            case R.id.calc_clear_bt:
                execClear = true;
                break;
            case R.id.calc_commit_bt:
                // 4. listener のメソッドを呼ぶ
                if (listener != null) {
                    resultDigit = calcResultText.getText().toString();

                    Bundle args = getArguments();
                    listener.onClickButton(resultDigit, args.getInt(FRAGMENT_MODE), args.getInt(DETAIL_MODE));
                }
                break;
        }
        if (execCalc == true) {
            recentDigit = "";
            calcRecentText.setText(recentDigit);
            resultDigit = String.valueOf(calcNum(resultDigit));
            calcResultText.setText(resultDigit);
        }
        if (execClear == true) {
            if (recentDigit.length() == 0 || cntSignStr(resultDigit) == 0) {
                calcResultText.setText("");
            } else {
                StringBuilder sb = new StringBuilder(resultDigit);
                sb.setLength(sb.length() - recentDigit.length() - 1);
                calcResultText.setText(sb);
            }
            calcRecentText.setText("");
        } else if (recentDigit.length() < MAX_DIGIT_LEN) {
            resultDigit = resultDigit + digit;
            calcResultText.setText(resultDigit);

            if (execSign == true) {
                recentDigit = "";
                calcRecentText.setText(recentDigit);
            } else if (execSign == false) {
                recentDigit = recentDigit + digit;
                calcRecentText.setText(recentDigit);
            }
            if(replaceSign == true){
                resultDigit = resultDigit.substring(0,resultDigit.length()-1);
                resultDigit = resultDigit + digit;
                calcResultText.setText(resultDigit);
            }
        }
    }

    private boolean afterEqual(String resultDigit, String recentDigit) {
        if (resultDigit.length() > 0 && cntSignStr(resultDigit) == 0 && recentDigit.length() == 0) {
            return true;
        }
        return false;
    }

    int calcNum(String str) {
        String firstNum = new String();
        String secondNum = new String();
        String sign = new String();
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            String temp = new String();
            temp = str.substring(i, i + 1);
            if (i != 0 && (temp.equals(DIGIT_PLUS) || temp.equals(DIGIT_MINUS) || temp.equals(DIGIT_DIVISION) || temp.equals(DIGIT_MULTIPLE))) {
                sign = temp;
                flag = true;
            } else {
                if (flag == false) {
                    firstNum = firstNum + temp;
                } else {
                    secondNum = secondNum + temp;
                }
            }
        }
        int calcResult = 0;
        if (sign.equals(DIGIT_PLUS)) {
            calcResult = Integer.parseInt(firstNum) + Integer.parseInt(secondNum);
        } else if (sign.equals(DIGIT_MINUS)) {
            calcResult = Integer.parseInt(firstNum) - Integer.parseInt(secondNum);
        } else if (sign.equals(DIGIT_MULTIPLE)) {
            calcResult = Integer.parseInt(firstNum) * Integer.parseInt(secondNum);
        } else if (sign.equals(DIGIT_DIVISION)) {
            calcResult = Integer.parseInt(firstNum) / Integer.parseInt(secondNum);
        }
        return calcResult;
    }

    boolean isLastStrNum(String str) {
        String lastStr = str.substring(str.length() - 1);
        boolean isNumeric = true;
        if (lastStr.equals(DIGIT_PLUS) || lastStr.equals(DIGIT_MINUS) || lastStr.equals(DIGIT_DIVISION) || lastStr.equals(DIGIT_MULTIPLE)) {
            isNumeric = false;
        }
        return isNumeric;
    }

    int cntSignStr(String str) {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            String checkStr = str.substring(i, i + 1);
            if (checkStr.equals(DIGIT_PLUS) || checkStr.equals(DIGIT_MINUS) || checkStr.equals(DIGIT_DIVISION) || checkStr.equals(DIGIT_MULTIPLE)) {
                cnt++;
            }
        }
        return cnt;
    }
};
