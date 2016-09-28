package pl.guideme.burkia.view.components.feeds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.guideme.burkia.R;
import pl.guideme.burkia.vo.FeedItem;

public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.ViewHolder> {
    private FeedItem[] mDataSet;
    private View.OnClickListener clickListener;
    private View.OnClickListener wrappedClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one mView per item, and
    // you provide access to all the views for a data item in a mView holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        private ImageView mImageView;
        private View.OnClickListener clickListener;
        private Context mContext;

        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext().getApplicationContext();
            mTextView = (TextView)v.findViewById(R.id.main_recycleview_item_textview);
            mImageView = (ImageView)v.findViewById(R.id.main_recycleview_item_imageview);

        }
        public void setClickListener(View.OnClickListener clickListener){
            this.clickListener = clickListener;

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder.this.clickListener.onClick(view);
                }
            });
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder.this.clickListener.onClick(view);
                }
            });
        }
    }

    public void setTextClickListener(final View.OnClickListener listener){
        this.clickListener = listener;
        this.wrappedClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (clickListener != null){
                    clickListener.onClick(view);
                }
            }
        };
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public FeedsRecyclerViewAdapter(FeedItem[] myDataSet) {
        mDataSet = myDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new mView
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycleview_item, parent, false);
        // set the mView's size, margins, padding's and layout parameters
        ViewHolder vh = new ViewHolder(v);
        vh.setClickListener(wrappedClickListener);
        return vh;
    }

    // Replace the contents of a mView (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataSet at this position
        // - replace the contents of the mView with that element
        FeedItem feedItem = mDataSet[position];
        holder.mTextView.setText(feedItem.getName());

        Picasso.with(holder.mContext)
                .load(feedItem.getImageUrl())
                .into(holder.mImageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.clickListener = null;
        this.wrappedClickListener = null;
    }

}
