package com.protoype.osindex.currencyexchange.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.protoype.osindex.currencyexchange.R;
import com.protoype.osindex.currencyexchange.interfaces.CurrencyClickListener;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;

import java.util.List;

/**
 * Created by osindex on 10/7/17.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<RealWorldCurrency> realWorldCurrencies;
    private CurrencyClickListener currencyClickListener;

    public CurrencyAdapter(List<RealWorldCurrency> realWorldCurrencyList, CurrencyClickListener currencyClickListener){
        this.realWorldCurrencies = realWorldCurrencyList;
        this.currencyClickListener = currencyClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView currencyImage;
        public TextView textViewCurrencyFullname, textViewCurrencyShortName,
                textViewCurrencyShortNameToBtc, textViewCurrencyShortNameToEth,
                textViewCurrencyRateToBtc, textViewCurrencyRateToEth;
        public ViewHolder(View view){
            super(view);
            textViewCurrencyFullname        = (TextView)view.findViewById(R.id.single_currency_fullname);
            textViewCurrencyShortName       = (TextView)view.findViewById(R.id.single_currency_short_name);
            currencyImage                   = (ImageView)view.findViewById(R.id.single_currency_image);
            textViewCurrencyShortNameToBtc  = (TextView)view.findViewById(R.id.single_currency_short_name_to_btc);
            textViewCurrencyShortNameToEth  = (TextView)view.findViewById(R.id.single_currency_short_name_to_eth);
            textViewCurrencyRateToBtc       = (TextView)view.findViewById(R.id.single_currency_rate_to_btc);
            textViewCurrencyRateToEth       = (TextView)view.findViewById(R.id.single_currency_rate_to_eth);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.single_currency_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currencyClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.ViewHolder holder, int position) {
        RealWorldCurrency realWorldCurrency = realWorldCurrencies.get(position);

        holder.currencyImage.setImageResource(realWorldCurrency.getResource());
        holder.textViewCurrencyShortName.setText(realWorldCurrency.getShortName());
        holder.textViewCurrencyFullname.setText(realWorldCurrency.getFullname());
        holder.textViewCurrencyShortNameToBtc.setText(realWorldCurrency.getShortName());
        holder.textViewCurrencyShortNameToEth.setText(realWorldCurrency.getShortName());
        holder.textViewCurrencyRateToEth.setText(realWorldCurrency.getExchangeRateAgainstEth()+" ETH");
        holder.textViewCurrencyRateToBtc.setText(realWorldCurrency.getExchangeRateAgainstBTC()+" BTC");
    }

    @Override
    public int getItemCount() {
        return realWorldCurrencies.size();
    }
}
