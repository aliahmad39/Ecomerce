package com.ali.pf_monthly_task.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.pf_monthly_task.Activities.MainActivity;
import com.ali.pf_monthly_task.utils.MyAsyncClass;
import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.utils.Singleton;

import java.util.ArrayList;

// The adapter class which
// extends RecyclerView Adapter
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyView> {
    Singleton singleton = Singleton.getInstance();
    // List with String type
    private ArrayList<String> list = singleton.categoryList;
    Context context;
    public static int selectedPos = 0;



    public CategoryAdapter(Context context ) {
        this.context = context;

    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    //
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        // Set the text of each item of
        // Recycler view with the list items
        holder.textView.setText(list.get(position));
      //  holder.layout.setBackgroundColor(R.color.white);


        holder.itemView.setSelected(selectedPos == position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   holder.layout.setBackgroundColor(colorPrimary);
            //   Toast.makeText(context, ""+list.get(position), Toast.LENGTH_SHORT).show();
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
//               MainActivity.setLimitSpinner(context);
//               MainActivity.setSortSpinnner(context);
           //     notifyDataSetChanged();

              MyAsyncClass myAsyncClass = new MyAsyncClass(context , list.get(position));
                myAsyncClass.setInterface((MainActivity)context);
                myAsyncClass.execute();

            }
        });

    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        TextView textView;
        CardView layout;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view.findViewById(R.id.textview);
            layout =  view.findViewById(R.id.layout_category);
        }
    }
}

