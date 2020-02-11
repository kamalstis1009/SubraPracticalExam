package com.example.subrapracticalexam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subrapracticalexam.R;
import com.example.subrapracticalexam.fragment.AllCustomerFragment;
import com.example.subrapracticalexam.model.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private static final String TAG = "CustomerAdapter";

    private Context context;
    private List<Customer> customerList;

    private OnCustomerClick onCustomerClick;
    public interface OnCustomerClick {
        void customerCallBack(Customer customer);
    }

    public CustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
        onCustomerClick = AllCustomerFragment.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.single_customer_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.nameTextView.setText("Name: " + customerList.get(position).getCustomerName());
            holder.emailTextView.setText("Email: " + customerList.get(position).getEmail());
            holder.phoneNumberTextView.setText("Contact No: " + customerList.get(position).getContactNo());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: " + customerList.get(position).getCustomerId());
                    onCustomerClick.customerCallBack(customerList.get(position));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView emailTextView;
        private TextView phoneNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view_name);
            emailTextView = itemView.findViewById(R.id.text_view_email);
            phoneNumberTextView = itemView.findViewById(R.id.text_view_phone_number);
        }
    }
}
