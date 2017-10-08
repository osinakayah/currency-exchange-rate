package com.protoype.osindex.currencyexchange.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.protoype.osindex.currencyexchange.R;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;

import java.util.List;

/**
 * Created by osindex on 10/7/17.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<RealWorldCurrency> realWorldCurrencies;

    public CurrencyAdapter(List<RealWorldCurrency> realWorldCurrencyList){
        this.realWorldCurrencies = realWorldCurrencyList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView currencyImage;
        public TextView textViewCurrencyFullname, textViewCurrencyShortName;
        public ViewHolder(View view){
            super(view);
            textViewCurrencyFullname    = (TextView)view.findViewById(R.id.single_currency_fullname);
            textViewCurrencyShortName           = (TextView)view.findViewById(R.id.single_currency_short_name);
            currencyImage               = (ImageView)view.findViewById(R.id.single_currency_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.single_currency_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.ViewHolder holder, int position) {
        RealWorldCurrency realWorldCurrency = realWorldCurrencies.get(position);

        holder.currencyImage.setImageResource(realWorldCurrency.getResource());
        holder.textViewCurrencyShortName.setText(realWorldCurrency.getShortName());
        holder.textViewCurrencyFullname.setText(realWorldCurrency.getFullname());
    }

    @Override
    public int getItemCount() {
        return realWorldCurrencies.size();
    }
}
