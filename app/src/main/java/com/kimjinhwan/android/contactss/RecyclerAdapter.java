package com.kimjinhwan.android.contactss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimjinhwan.android.contactss.domain.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XPS on 2017-06-01.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

    List<Data> datas;

    public RecyclerAdapter(List<Data> datas){
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Data data = datas.get(position);
        holder.txtNumber.setText(data.getTel());
        holder.txtName.setText(data.getName());
        holder.txtAddress.setText(data.getAddress());
        holder.txtEmail.setText(data.getEmail());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtName, txtNumber, txtAddress, txtEmail;

        public Holder(View itemView) {
            super(itemView);
            txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
        }
    }

}