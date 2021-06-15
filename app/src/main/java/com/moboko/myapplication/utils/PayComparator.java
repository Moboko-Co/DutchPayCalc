package com.moboko.myapplication.utils;

import com.moboko.myapplication.entity.PaymentItemList;

import java.util.Comparator;

import static com.moboko.myapplication.utils.CommonConsts.A_PAY;

public class PayComparator implements Comparator<PaymentItemList> {
    @Override
    public int compare(PaymentItemList obj1, PaymentItemList obj2) {

        if (obj1.getTypeDiv() < obj2.getTypeDiv()) {
            return -1;
        } else if (obj1.getTypeDiv() > obj2.getTypeDiv()) {
            return 1;
        } else {
            if (obj1.getTypeDiv() == A_PAY) {
                if (obj1.getToId() < obj2.getToId()) {
                    return -1;
                } else if (obj1.getToId() > obj2.getToId()) {
                    return 1;
                } else {
                    if (obj1.getFromId() < obj2.getFromId()) {
                        return -1;
                    } else if (obj1.getFromId() > obj2.getFromId()) {
                        return 1;
                    } else {
                        if (obj1.getPrice() < obj2.getPrice()) {
                            return -1;
                        } else if (obj1.getPrice() > obj2.getPrice()) {
                            return 1;
                        }
                    }
                }
            } else {
                if (obj1.getFromId() < obj2.getFromId()) {
                    return -1;
                } else if (obj1.getFromId() > obj2.getFromId()) {
                    return 1;
                } else {
                    if (obj1.getToId() < obj2.getToId()) {
                        return -1;
                    } else if (obj1.getToId() > obj2.getToId()) {
                        return 1;
                    } else {
                        if (obj1.getPrice() < obj2.getPrice()) {
                            return -1;
                        } else if (obj1.getPrice() > obj2.getPrice()) {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }
}