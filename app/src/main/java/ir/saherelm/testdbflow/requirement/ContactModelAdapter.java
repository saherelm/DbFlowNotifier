package ir.saherelm.testdbflow.requirement;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saherelm.testdbflow.R;
import ir.saherelm.testdbflow.database.model.ContactModel;

/**
 * a General Recycler View Adapter for Showing ContactModelLists ...
 * Created by SaherElm on 17/01/2018.
 */

public class ContactModelAdapter extends RecyclerView.Adapter<ContactModelAdapter.ContactModelViewHolder> {

    private LayoutInflater mInflater;
    private List<ContactModel> mData;

    public ContactModelAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mInflater = null;
    }

    @Override
    public ContactModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactModelViewHolder(parent, viewType, R.layout.contact_item_layout);
    }

    @Override
    public void onBindViewHolder(ContactModelViewHolder holder, int position) {
        holder.bindTo(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public ContactModel getItem(int position) {
        return mData.get(position);
    }

    public List<ContactModel> getData() {
        return mData;
    }



    public void clear() {
        if (mData == null) {
            return;
        }

        synchronized (mData) {
            mData.clear();
        }

        notifyDataSetChanged();
    }

    public void add(ContactModel item) {
        int mPosition = mData.size();
        synchronized (mData) {
            mData.add(item);
        }

        notifyItemInserted(mPosition);
    }

    public void addRange(List<ContactModel> itemList) {
        int mStartPosition = mData.size();
        synchronized (mData) {
            mData.addAll(itemList);
        }

        notifyItemRangeInserted(mStartPosition, itemList.size());
    }

    public void remove(int position) {
        if (getItemCount() <= 0
                || position < 0
                || position > mData.size()) {
            return;
        }

        synchronized (mData) {
            mData.remove(position);
        }

        notifyItemRemoved(position);
    }



    public class ContactModelViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvId;
        public TextView mTvFullName;
        public TextView mTvPhoneNumber;

        public ContactModelViewHolder(ViewGroup parent, int viewType, @LayoutRes int layoutRes) {
            super(mInflater.inflate(layoutRes, parent, false));

            mTvId = itemView.findViewById(R.id.tvId);
            mTvFullName = itemView.findViewById(R.id.tvFullName);
            mTvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
        }

        public void bindTo(ContactModel item, int position) {
            mTvId.setText(item.id.toString());
            mTvFullName.setText(item.getFullName());
            mTvPhoneNumber.setText(item.phoneNumber);
        }

    }

}
