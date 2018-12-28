package com.example.civicsense;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/*
    - Salvataggio dati della segnalazione pt1
    - richiamo dei pattern
*/
public class Activity2 extends AppCompatActivity {
    private Button button;
    private Button button1;
    private RadioButton R1;
    private RadioButton R2;
    private RadioButton R3;
    private RadioButton R4;
    private RadioButton R5;
    private EditText editTextEmail;
    public Model segnalazione = new Model();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        editTextEmail = (EditText)findViewById(R.id.email);
        button = (Button) findViewById(R.id.avanti);
        button1 = (Button) findViewById(R.id.indietro);

        R1 = (RadioButton) findViewById(R.id.P1);
        R2 = (RadioButton) findViewById(R.id.P2);
        R3 = (RadioButton) findViewById(R.id.P3);
        R4 = (RadioButton) findViewById(R.id.P4);
        R5 = (RadioButton) findViewById(R.id.P5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup)findViewById(R.id.typeGroup);
                Facade facade = new Facade(editTextEmail,null,rg);
                if(facade.check2()){
                    openActivity3();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity2.this);
                    builder.setTitle("Avviso");
                    builder.setMessage("Inserire Email e/o definire il tipo di problema");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoActivityMain();
            }
        });
    }




    public void openActivity3(){
        RadioGroup rg = (RadioGroup)findViewById(R.id.typeGroup);
        final String email = editTextEmail.getText().toString();
        final String tipo = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        segnalazione.setTipo(tipo);
        segnalazione.setEmail(email);
        Intent intent = new Intent(Activity2.this, Activity3.class);
        intent.putExtra("dati",segnalazione);
        startActivity(intent);
    }



    public void backtoActivityMain(){
        finish();
    }
}
