package com.peoplecoachingworks.leapstudio20;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NgocTri on 11/15/2015.
 */
public class GoalListAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoalList> mGAT;

    //Constructor

    public GoalListAdapter(Context mContext, List<GoalList> mProductList) {
        this.mContext = mContext;
        this.mGAT = mProductList;
    }

    @Override
    public int getCount() {
        return mGAT.size();
    }

    @Override
    public Object getItem(int position) {
        return mGAT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.goal_tips_list, null);

        TextView tvGoals = view.findViewById(R.id.tvGAT);
        //Set text for TextView
        tvGoals.setText(mGAT.get(position).getGoals());

        //Save product id to tag
        view.setTag(mGAT.get(position).getId());

        return view;
    }

}
