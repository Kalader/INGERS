package com.example.civicsense;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;


public class Info3 implements Sorveglianza {
    @Override
    public boolean controllo(EditText et, ImageView iv, RadioGroup rg) {

        if (et.length() == 0 || iv.getDrawable() == null) {
            return false;
        }

        return true;
    }
}
