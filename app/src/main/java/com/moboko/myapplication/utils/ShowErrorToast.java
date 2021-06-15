package com.moboko.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

import static com.moboko.myapplication.utils.CommonConsts.*;

public class ShowErrorToast {
    public static void printErrorToast(Context context, String errorParts, String errorReason) {
        String errorMessage = new String();
        switch(errorReason){
            case VALUE_ZERO:
                errorMessage = errorParts + "に0は設定できません";
                break;
            case DETAIL_MAX:
                errorMessage = ERROR_MAX_DETAIL;
                break;
            case VALUE_MAX:
                errorMessage = errorParts + "は総人数より小さい数字を設定してください";
                break;
            default:
                break;
        }
        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_LONG);
        toast.show();
    }
}

