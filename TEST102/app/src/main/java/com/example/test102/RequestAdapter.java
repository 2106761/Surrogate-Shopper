package com.example.test102;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    //Context object for including the recycle view layout
    private Context mCtx;

    //To display data we need a list
    private List<RequestClass> requestList;

    //Create an object of the interface
    private RecycleViewClickListener listener;

    //Constructor
    //Object of listener(instant)
    public RequestAdapter(Context mCtx, List<RequestClass> requestList,RecycleViewClickListener listener) {
        this.mCtx = mCtx;
        this.listener = listener;
        this.requestList = requestList;
    }

    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //we need inflater to create a view object for list_layout
        //To create a layout inflater object we need a context object
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        //Product helps us to bind(put data together) the data
        RequestClass product = requestList.get(position);
        //Loading data
        holder.textViewName.setText("Name: "+product.getName());
        holder.textViewLocation.setText("Location: "+product.getLocation());
        holder.textViewList.setText(String.valueOf("List: "+product.getList()));
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
    //We pass the list_layout to this class since it takes a view
    //Implement view onclick on our holder class
    class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewLocation, textViewList;
        public RequestViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewList = itemView.findViewById(R.id.textViewList);
       }
        //Onclick method
        @Override
        public void onClick(View v) {
            //Pass the view and the array position
            listener.onClick(itemView,getAdapterPosition());
        }
    }

    //Create an interface for recycle view
    public interface RecycleViewClickListener{
        //Position defines the position in the array
        void onClick(View view,int position);
    }

}

