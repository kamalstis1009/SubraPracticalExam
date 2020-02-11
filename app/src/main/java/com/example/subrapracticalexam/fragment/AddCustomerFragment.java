package com.example.subrapracticalexam.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subrapracticalexam.R;
import com.example.subrapracticalexam.model.Customer;
import com.example.subrapracticalexam.service.ApiClient;
import com.example.subrapracticalexam.service.ApiService;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomerFragment extends DialogFragment {

    private static final String TAG = "AddCustomerFragment";

    private RelativeLayout progressBar;
    private boolean isUpdate = false;
    private String customerId;

    public AddCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);

        final TextView titleTextView = view.findViewById(R.id.text_view_title);
        final EditText nameEditText = view.findViewById(R.id.edit_text_name);
        final EditText emailEditText = view.findViewById(R.id.edit_text_email);
        final EditText contactNoEditText = view.findViewById(R.id.edit_text_contact_no);
        final EditText contactPersonEditText = view.findViewById(R.id.edit_text_contact_person);
        Button saveButton = view.findViewById(R.id.button_save);
        progressBar = view.findViewById(R.id.progress_bar);

        Bundle bundle = getArguments();
        if (bundle != null) {
            titleTextView.setText("Update Customre");
            saveButton.setText("Update");
            isUpdate = true;

            try {
                Customer customer = (Customer) bundle.getSerializable("Customer");
                customerId = customer.getCustomerId();

                nameEditText.setText(customer.getCustomerName());
                emailEditText.setText(customer.getEmail());
                contactNoEditText.setText(customer.getContactNo());
                contactPersonEditText.setText(customer.getContactPerson());
            } catch (Exception e) {
                Log.e(TAG, "onCreateView: " + e);
                Toast.makeText(getContext(), "Couldn't load data!", Toast.LENGTH_SHORT).show();
            }
        } else {
            isUpdate = false;
        }

        view.findViewById(R.id.image_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                    nameEditText.setError("Empty!");
                } else {
                    try {
                        Customer customer = new Customer();
                        customer.setCustomerId(customerId);
                        customer.setCustomerName(nameEditText.getText().toString());
                        customer.setEmail(emailEditText.getText().toString());
                        customer.setContactNo(contactNoEditText.getText().toString());
                        customer.setContactPerson(contactPersonEditText.getText().toString());
                        customer.setoCode("8989");

                        if (isUpdate) {
                            updateCustomer(customer);
                        } else {
                            saveCustomer(customer);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: " + e);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Something went wrong! Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme);
    }

    private void saveCustomer(Customer customer) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<String> call = apiService.saveCustomer(customer);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Customer saved successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "onResponse: " + response);
                        Toast.makeText(getContext(), "Couldn't save customer!", Toast.LENGTH_SHORT).show();
                    }

                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong! Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Couldn't save customer!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCustomer(Customer customer) {
        progressBar.setVisibility(View.VISIBLE);

        String customerId = customer.getCustomerId();

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<String> call = apiService.updateCustomer(customerId, customer);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Customer updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "onResponse: " + response);
                        Toast.makeText(getContext(), "Couldn't update customer!", Toast.LENGTH_SHORT).show();
                    }

                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong! Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Couldn't save customer!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
