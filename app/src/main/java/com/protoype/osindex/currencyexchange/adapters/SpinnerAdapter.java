package com.protoype.osindex.currencyexchange.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.protoype.osindex.currencyexchange.R;

/**
 * Created by osindex on 11/4/17.
 */

public class SpinnerAdapter extends ArrayAdapter<Integer> {
    private Integer[] images;
    private SpinnerAdapter.ViewHolder holder;
    private LayoutInflater inflater;
    public SpinnerAdapter(Context context, int viewResource, Integer[] images){
        super(context, viewResource, images);
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent){
        Integer  integerItem = images[position];
        View row = convertView;

        if (row == null) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.single_spinner_item, parent, false);

            holder.crptoImage = (ImageView) row.findViewById(R.id.cryptocurrency_spinner_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.crptoImage.setBackgroundResource(integerItem);

        return row;
    }
    static class ViewHolder {
        ImageView crptoImage;
    }
}
