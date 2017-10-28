package com.protoype.osindex.currencyexchange.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.protoype.osindex.currencyexchange.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;


public class ConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.cryptocurrencies_array,
                android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crytoCurrenciesSpinner.setAdapter(charSequenceArrayAdapter);

        Drawable fabSwitchDrawableIcon = MaterialDrawableBuilder.with(getApplicationContext())
                .setIcon(MaterialDrawableBuilder.IconValue.SWAP_HORIZONTAL)
                .setColor(Color.WHITE)
                .setToActionbarSize()
                .build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switch_between_crypto_and_real_currencies);
        fab.setImageDrawable(fabSwitchDrawableIcon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
