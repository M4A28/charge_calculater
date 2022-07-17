package com.mohammed.mosa.eg.chargercalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText price_et;
    EditText peopleCount_et;
    EditText handy;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        price_et = findViewById(R.id.et_pricae);
        peopleCount_et = findViewById(R.id.et_pepble_count);
        handy = findViewById(R.id.et_in_hand);

        tv_result = findViewById(R.id.tv_result);
        tv_result.setVisibility(View.GONE);
    }

    public void calculate(View view) {
        String count = peopleCount_et.getText().toString();
        String in_hand = handy.getText().toString();
        String pr = price_et.getText().toString();
        if(!pr.isEmpty() && !in_hand.isEmpty() && !count.isEmpty()){
            double charge =  Double.parseDouble(pr);
            double c = Double.parseDouble(count);
            double hand = Double.parseDouble(in_hand);
           tv_result.setText(resultString(c, charge, hand));
           tv_result.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(this, getString(R.string.warring), Toast.LENGTH_SHORT).show();
    }

    public String resultString(double passenger, double charge, double handy){
        double calculation = passenger * charge;
        double reminder = handy - calculation;
        String rest = (reminder > 0) ? getString(R.string.increase)
                : (reminder == 0) ? getString(R.string.neutral): getString(R.string.decrease);

        String final_result = getString(R.string.charge) + " " + calculation + " " + "\n"
                + getString(R.string.passenger_count) +  ": " + passenger + "\n"
                + rest + " " + ((reminder == 0) ? "": reminder) ;
        return  final_result;
    }

    // main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void clear(View view) {
        if(!peopleCount_et.getText().toString().isEmpty()
                || !handy.getText().toString().isEmpty()
                || !price_et.getText().toString().isEmpty()) {
            peopleCount_et.setText(null);
            handy.setText(null);
            price_et.setText(null);
            tv_result.setVisibility(View.GONE);
        }
        else
            Toast.makeText(this, getString(R.string.all_field_empty), Toast.LENGTH_SHORT).show();
    }

    public void shareApp(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ConstValue.MY_TWITTER);
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }

    public void about(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    // dialog to conform exit from app
    public void exitDialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_2);
        dialog.setCancelable(false);
        dialog.show();
        Button exit = dialog.findViewById(R.id.yes_button);
        Button dismiss = dialog.findViewById(R.id.no_button);
        exit.setOnClickListener(view -> finish());
        dismiss.setOnClickListener(view -> dialog.dismiss());
    }

    // when press back button
    @Override
    public void onBackPressed() {
        exitDialog();
    }
}