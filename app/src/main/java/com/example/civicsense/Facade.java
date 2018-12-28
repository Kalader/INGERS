package com.example.civicsense;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;


public class Facade {
    private Sorveglianza info2;
    private Sorveglianza info3;

    private EditText et;
    private ImageView iv;
    private RadioGroup rg;

    public Facade(EditText et, ImageView iv, RadioGroup rg) {
        this.et = et;
        this.iv = iv;
        this.rg = rg;

        info2 = new Info2();
        info3 = new Info3();
    }

    public boolean check2(){
        return(info2.controllo(et,iv,rg));
    }

    public boolean check3(){
        return(info3.controllo(et,iv,rg));
    }
}
