package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.login.controllr.ExpandableList_Adaptor;
import com.example.login.controllr.Listdata_fruits;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Orders;
import com.example.login.modal.Products;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fruits_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fruits_Fragment extends Fragment {
    DBHandler dbHandler=new DBHandler(getActivity());
    View get_Fragment;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Orders>od_items;
    public Fruits_Fragment() {

    }

    public static Fruits_Fragment newInstance(String param1,ArrayList<Orders>od_items) {
        Fruits_Fragment fragment = new Fruits_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Place_order tmpAccess = (Place_order)getActivity();

        od_items = tmpAccess.getDetails();

        if(od_items== null){
            od_items = new ArrayList<>();
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

        ExpandableListView expandableListView;
        ExpandableListAdapter expandableList_Adapter;
        ArrayList<String>expandableList_Title;
        HashMap<String,ArrayList<Products>> expandableList_Details;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            od_items = (ArrayList<Orders>)getActivity().getIntent().getSerializableExtra("od_details");
        }
        get_Fragment = inflater.inflate(R.layout.fragment_fruits_, container, false);
        expandableListView = get_Fragment.findViewById(R.id.expandableListView_Main);
        ArrayList<Products> tempList= Listdata_fruits.getProductsByCatId(getContext(),1);
        expandableList_Details =Listdata_fruits.getData(getContext(),tempList, 1);

        for(int i=0;i<expandableList_Details.size();i++){
            expandableList_Title = new ArrayList<>(expandableList_Details.keySet());
            expandableList_Adapter = new ExpandableList_Adaptor(getActivity(),expandableList_Title
                    ,expandableList_Details,od_items);
        }

        expandableListView.setAdapter(expandableList_Adapter);

        return get_Fragment;
    }


}