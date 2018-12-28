package com.example.civicsense;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Info2 implements Sorveglianza {
    @Override
    public boolean controllo(EditText et, ImageView iv, RadioGroup rg) {
        if(checkEmail(et) == true) {
            if (rg.getCheckedRadioButtonId()==-1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean checkEmail(EditText editTextEmail){
        final String email = editTextEmail.getText().toString().trim();

        if(email.isEmpty()){
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }
        return true;
    }
}
