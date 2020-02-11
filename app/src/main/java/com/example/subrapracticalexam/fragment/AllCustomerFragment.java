package com.example.subrapracticalexam.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.subrapracticalexam.R;
import com.example.subrapracticalexam.adapter.CustomerAdapter;
import com.example.subrapracticalexam.model.Customer;
import com.example.subrapracticalexam.service.ApiClient;
import com.example.subrapracticalexam.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCustomerFragment extends DialogFragment implements CustomerAdapter.OnCustomerClick {

    private static final String TAG = "AllCustomerFragment";
    private static AllCustomerFragment allCustomerFragment;

    private RecyclerView customerRecyclerView;
    private RelativeLayout progressBar;

    public AllCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_customer, container, false);

        allCustomerFragment = this;

        customerRecyclerView = view.findViewById(R.id.recycler_view_customer_list);
        progressBar = view.findViewById(R.id.progress_bar);

        view.findViewById(R.id.image_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getAllCustomer();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void customerCallBack(Customer customer) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Customer", customer);

        AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
        addCustomerFragment.setArguments(bundle);
        addCustomerFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    private void getAllCustomer() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<Customer>> call = apiService.getAllCustomer(8989);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                try {
                    if (response.isSuccessful() && response.body().size() > 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        customerRecyclerView.setLayoutManager(layoutManager);
                        customerRecyclerView.setHasFixedSize(true);

                        CustomerAdapter adapter = new CustomerAdapter(getContext(), response.body());
                        customerRecyclerView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "onResponse: " + response);
                        Toast.makeText(getContext(), "Couldn't get customer list!", Toast.LENGTH_SHORT).show();
                    }

                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong! Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Couldn't get customer list!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static AllCustomerFragment getInstance() {
        return allCustomerFragment;
    }
}
