package com.protoype.osindex.currencyexchange.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.protoype.osindex.currencyexchange.adapters.SpinnerAdapter;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;
import com.protoype.osindex.currencyexchange.networks.CurrencyExchangeRate;
import com.protoype.osindex.currencyexchange.util.ConversionManager;
import com.transitionseverywhere.*;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.protoype.osindex.currencyexchange.R;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.Currency;

import cat.xojan.numpad.NumPadButton;
import cat.xojan.numpad.NumPadView;
import cat.xojan.numpad.OnNumPadClickListener;


public class ConversionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView txtViewrealCurrencySymbol, txtViewCryptoCurrencySymbol, txtViewrealCurrencyValue, txtViewCryptoCurrencyValue;
    private LinearLayout realCurrencyLinearLayout, crytoCurrencyLinearLayout;
    private ConversionManager conversionManager;
    private boolean hasSwaped;
    private RealWorldCurrency realWorldCurrency;
    private ViewGroup transitionsContainer;
    private Spinner crytoCurrenciesSpinner;
    private int selectedCryptoCurency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView realCurrencyImage = (ImageView)findViewById(R.id.imageview_real_currency_conversion_flag);

        transitionsContainer = (FrameLayout)findViewById(R.id.frameLayoutCurrencyConversion);

        realCurrencyLinearLayout = (LinearLayout)findViewById(R.id.real_currency_linear_layout);
        crytoCurrencyLinearLayout = (LinearLayout)findViewById(R.id.cryto_currency_linear_layout);


        txtViewrealCurrencySymbol = (TextView) findViewById(R.id.textview_real_currency_symbol);
        txtViewCryptoCurrencySymbol = (TextView)findViewById(R.id.text_view_cryto_currency_symbol);
        txtViewrealCurrencyValue = (TextView) findViewById(R.id.textview_real_currency_value);
        txtViewCryptoCurrencyValue = (TextView)findViewById(R.id.text_view_cryto_currency_value);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switch_between_crypto_and_real_currencies);


        crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);
        hasSwaped = false;

        this.realWorldCurrency = RealWorldCurrency.findById(RealWorldCurrency.class, getIntent().getLongExtra("REAL_CURRENCY_ID", -1l));
        realCurrencyImage.setImageResource(this.realWorldCurrency.getResource());
        txtViewrealCurrencySymbol.setText(realWorldCurrency.getSymbol());

        conversionManager = new ConversionManager();
        conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC())), hasSwaped);
        selectedCryptoCurency = CurrencyExchangeRate.BITCOIN;



        crytoCurrenciesSpinner.setOnItemSelectedListener(this);

        crytoCurrenciesSpinner.setAdapter(new SpinnerAdapter(this, R.layout.single_spinner_item, new Integer[]{R.drawable.btc, R.drawable.eth}));
        initNumpadView();
        Drawable fabSwitchDrawableIcon = MaterialDrawableBuilder.with(getApplicationContext())
                .setIcon(MaterialDrawableBuilder.IconValue.SWAP_VERTICAL)
                .setColor(Color.WHITE)
                .setToActionbarSize()
                .build();

        fab.setImageDrawable(fabSwitchDrawableIcon);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasSwaped){
                    switchCurrenciesBack();
                    hasSwaped = false;
                }else{
                    switchCurencies();
                    hasSwaped = true;
                }
                switch (selectedCryptoCurency){
                    case CurrencyExchangeRate.BITCOIN:
                        conversionManager.setFromRate(1/Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC()), hasSwaped);
                        break;
                    case CurrencyExchangeRate.ETHEREUM:
                        conversionManager.setFromRate(1/Double.parseDouble(realWorldCurrency.getExchangeRateAgainstEth()), hasSwaped);
                        break;

                }
            }
        });
    }
    private void switchCurencies(){
        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams from = (FrameLayout.LayoutParams)realCurrencyLinearLayout.getLayoutParams();
        FrameLayout.LayoutParams to = (FrameLayout.LayoutParams)crytoCurrencyLinearLayout.getLayoutParams();

        from.gravity = Gravity.BOTTOM|Gravity.FILL_HORIZONTAL;
        to.gravity = Gravity.TOP|Gravity.FILL_HORIZONTAL;

        realCurrencyLinearLayout.setLayoutParams(from);
        crytoCurrencyLinearLayout.setLayoutParams(to);
    }
    private void switchCurrenciesBack(){

        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams from = (FrameLayout.LayoutParams)realCurrencyLinearLayout.getLayoutParams();
        FrameLayout.LayoutParams to = (FrameLayout.LayoutParams)crytoCurrencyLinearLayout.getLayoutParams();

        from.gravity = Gravity.TOP|Gravity.FILL_HORIZONTAL;
        to.gravity = Gravity.BOTTOM|Gravity.FILL_HORIZONTAL;

        realCurrencyLinearLayout.setLayoutParams(from);
        crytoCurrencyLinearLayout.setLayoutParams(to);
    }

    private void convertCurrency(){
        String stringAmount = "0";
        if(hasSwaped){
            stringAmount = txtViewCryptoCurrencyValue.getText().toString();
        }else{
            stringAmount = txtViewrealCurrencyValue.getText().toString();
        }

        if(stringAmount.equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
            return;
        }

        double amount = Double.parseDouble(stringAmount);
        amount = (conversionManager.convert(amount));
        //textViewToAmount.setText(String.format("%.3f", amount));
        if(hasSwaped){
            txtViewrealCurrencyValue.setText(String.format("%.3f", amount));
        }else{
            txtViewCryptoCurrencyValue.setText(String.format("%.3f", amount));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            selectedCryptoCurency = CurrencyExchangeRate.BITCOIN;
            txtViewCryptoCurrencySymbol.setText("BTC");
            conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC())), hasSwaped);
        }
        else if(position == 1){
            selectedCryptoCurency = CurrencyExchangeRate.ETHEREUM;
            txtViewCryptoCurrencySymbol.setText("ETH");
            conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstEth())), hasSwaped);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initNumpadView(){
        NumPadView numPadView = (NumPadView)findViewById(R.id.numpad);
        numPadView.setNumberPadClickListener(new OnNumPadClickListener(){
            @Override
            public void onPadClicked(NumPadButton button) {
                String buttonClicked            = getNumberClicked(button.name());
                String currentValueOnTextView   = getCurrenctCurrencyValue();
                if(hasSwaped){
                    if(buttonClicked.equalsIgnoreCase("D")){
                        if(currentValueOnTextView.length() > 0){
                            txtViewCryptoCurrencyValue.setText(currentValueOnTextView.substring(0, currentValueOnTextView.length()-1));
                        }
                        convertCurrency();
                        return;
                    }
                    txtViewCryptoCurrencyValue.append(buttonClicked);
                }else{
                    if(buttonClicked.equalsIgnoreCase("D")){
                        if(currentValueOnTextView.length() > 0){
                            txtViewrealCurrencyValue.setText(currentValueOnTextView.substring(0, currentValueOnTextView.length()-1));
                        }
                        convertCurrency();
                        return;
                    }
                    txtViewrealCurrencyValue.append(getNumberClicked(button.name()));
                }
                convertCurrency();
            }
        });
    }

    /**
     * Get the number that was clicked on the keypad as string
     * @param btnName
     * @return
     */
    private String getNumberClicked(String btnName){
        switch (btnName){
            case "NUM_0":
                return "0";
            case "NUM_1":
                return "1";
            case "NUM_2":
                return "2";
            case "NUM_3":
                return "3";
            case "NUM_4":
                return "4";
            case "NUM_5":
                return "5";
            case "NUM_6":
                return "6";
            case "NUM_7":
                return "7";
            case "NUM_8":
                return "8";
            case "NUM_9":
                return "9";
            case "CUSTOM_BUTTON_2":
                return ".";
            case "CUSTOM_BUTTON_1":
                return "D";

            default:
                return "";
        }
    }

    private String getCurrenctCurrencyValue(){
        if(hasSwaped){
            return txtViewCryptoCurrencyValue.getText().toString();
        }else{
            return txtViewrealCurrencyValue.getText().toString();
        }
    }
}