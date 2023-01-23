package com.example.login.controllr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Place_order;
import com.example.login.R;
import com.example.login.modal.Orders;
import com.example.login.modal.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableList_Adaptor extends BaseExpandableListAdapter {
    String avb_stock;
    String value;
    private Context mContext;
    int order_qty;
    private List<String> expandableListTitle;
    ArrayList <Orders>store_orders=new ArrayList<>();
    ArrayList<Products> expandableDetail_ArrayList=new ArrayList<>();
    HashMap<String, ArrayList<Products>> expandableListDetail_HashMap_new = new HashMap<>();
    private HashMap<String, ArrayList<Products>> expandableListDetail_HashMap;
    DataTransfer dataTransfer;
    Activity activity;


    public ExpandableList_Adaptor(Context mContext, List<String> expandableListTitle, HashMap<String
            , ArrayList<Products>> expandableListDetail_HashMap,ArrayList<Orders>store_orders) {
        this.mContext = mContext;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail_HashMap = expandableListDetail_HashMap;
        this.store_orders=store_orders;
    }
    public ExpandableList_Adaptor(Activity activity,DataTransfer dataTransfer,ArrayList<Orders>store_orders){
        this.dataTransfer=dataTransfer;
        this.activity = activity;
        this.store_orders=store_orders;
        dataTransfer.onSetValues(store_orders);

    }
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String listTitle = (String) getGroup(groupPosition);

        expandableDetail_ArrayList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Products>> map_values :
                expandableListDetail_HashMap.entrySet()) {
            String get_keys;
            ArrayList<Products>get_prices=new ArrayList<>();
            Products products= new Products();
            get_prices=map_values.getValue(); // get hashmap values only

            for(int i=0;i<get_prices.size();i++) {
                products = get_prices.get(i);
                get_keys=map_values.getKey();

                if(listTitle.equalsIgnoreCase(get_keys)){
                    expandableDetail_ArrayList.add(products);
                    expandableListDetail_HashMap_new.put(get_keys,expandableDetail_ArrayList);
                }
            }
        }
        return this.expandableListDetail_HashMap_new.get((this.expandableListTitle
                .get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Products getChild(int groupPosition, int childPosition) {

        return this.expandableListDetail_HashMap_new.get(this.expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);

        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);


        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

            ChildViewHolder childViewHolder;
            if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.list_item, null);


            childViewHolder = new ChildViewHolder();
            childViewHolder.txtPrice = (TextView) view.findViewById(R.id.price);
            childViewHolder.textStock = (TextView) view.findViewById(R.id.od_product_stock);
            childViewHolder.textQuantity = (TextView) view.findViewById(R.id.od_product_qty1);
            childViewHolder.plusButton=(Button)view.findViewById(R.id.od_product_incrementbtn);

            view.setTag(childViewHolder);
            } else {
            childViewHolder = (ChildViewHolder) view.getTag();
            }
            Products se_item = getChild(groupPosition,childPosition);

            updateView(childViewHolder, se_item);
            return view;

        }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private ChildViewHolder  updateView (ChildViewHolder chView, Products pro){
        chView.txtPrice.setText(pro.getPrice()+"");
        chView.textStock.setText(pro.getProduct_stocks()+"");

        String od_qty="0";

        if (store_orders != null && store_orders.size() > 0) {
            for (Orders item : store_orders) {
                if (pro.getProductId() == item.getProductId() && pro.getPrice_id() == item.getPrice_id()) {
                    od_qty = item.getQty();
                    avb_stock = item.getStocks();
                }

            }
            chView.textQuantity.setText(od_qty);

            for (Orders item : store_orders) {
                if (pro.getProductId() == item.getProductId() && pro.getPrice_id() == item.getPrice_id()) {
                    chView.textStock.setText(item.getStocks());
                }

            }
        }

        chView.plusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    boolean update_status = false;
                    order_qty = 0;
                    if (store_orders != null && store_orders.size() > 0) {
                    for (Orders item : store_orders) {
                        if (pro.getProductId() == item.getProductId() && pro.
                                getPrice_id() == item.getPrice_id()) {
                            order_qty = Integer.parseInt(item.getQty());
                            update_status = true;
                        }
                    }
                }

                int dec = Integer.parseInt(chView.textStock.getText().toString());
                if (dec != 0) {
                    dec--;
                    int setDec = (dec);
                    pro.setProduct_stocks(setDec);
                    chView.textStock.setText(String.valueOf(setDec));
                }
                double basePrice = Double.parseDouble(String.valueOf(pro.getPrice()));
                if (dec != 0) {
                    order_qty++;
                    value = Integer.toString(order_qty);
                    chView.textQuantity.setText(value);

                } else {
                    Toast.makeText(mContext, "Out of Stocks", Toast.LENGTH_SHORT).show();
                }
                double Total = basePrice * order_qty;
                String get_total = String.valueOf(Total);

                    if (store_orders != null) {
                    Orders orders = new Orders();
                    orders.setProductId(pro.getProductId());
                    orders.setProductName(pro.getProductDescription());
                    orders.setPrice(pro.getPrice());
                    orders.setPrice_id(pro.getPrice_id());
                    orders.setStocks(chView.textStock.getText().toString());
                    orders.setQty(chView.textQuantity.getText().toString());
                    orders.setTotal(get_total);
                    if (update_status) {
                        if (store_orders != null && store_orders.size() > 0) {
                            for (Orders item : store_orders) {
                                if (pro.getProductId() == item.getProductId() && pro
                                        .getPrice_id() == item.getPrice_id()) {
                                    item.setQty(orders.getQty());
                                    item.setTotal(orders.getTotal());
                                    item.setStocks(orders.getStocks());
                                }
                            }
                        }
                    } else {
                        store_orders.add(orders);

                    }
                }
            }

        });

        return chView;
    }

    private static class ChildViewHolder {
        TextView txtPrice;
        TextView textStock;
        TextView textQuantity;
        Button plusButton;

    }

}