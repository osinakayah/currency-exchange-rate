package com.protoype.osindex.currencyexchange.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
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
        public SimpleDraweeView currencyImage;
        public TextView textViewCurrencyFullname, textViewCurrencyShortName,
                textViewCurrencyShortNameToBtc, textViewCurrencyShortNameToEth,
                textViewCurrencyRateToBtc, textViewCurrencyRateToEth;
        public ViewHolder(View view){
            super(view);
            textViewCurrencyFullname        = (TextView)view.findViewById(R.id.single_currency_fullname);
            textViewCurrencyShortName       = (TextView)view.findViewById(R.id.single_currency_short_name);
            currencyImage                   = (SimpleDraweeView)view.findViewById(R.id.single_currency_image);
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
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(realWorldCurrency.getResource()))
                .build();
        holder.currencyImage.setImageURI(uri);
        holder.textViewCurrencyShortName.setText(realWorldCurrency.getShortName());
        holder.textViewCurrencyFullname.setText(realWorldCurrency.getFullname());
//        holder.textViewCurrencyShortNameToBtc.setText();
//        holder.textViewCurrencyShortNameToEth.setText();
        holder.textViewCurrencyRateToEth.setText(realWorldCurrency.getExchangeRateAgainstEth()+" "+realWorldCurrency.getShortName());
        holder.textViewCurrencyRateToBtc.setText(realWorldCurrency.getExchangeRateAgainstBTC()+" "+realWorldCurrency.getShortName());
    }

    @Override
    public int getItemCount() {
        return realWorldCurrencies.size();
    }
}
