package com.jude.emotionshow.presentation.shop;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2016/1/1.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    public int mSelectedItem = -1;
    private int lastSelectedItem = -1;
    public List<Address> mItems;
    private Activity mContext;
    private int addressId;

    public AddressAdapter(Activity context, List<Address> items) {
        mContext = context;
        mItems = items;
        addressId = JUtils.getSharedPreference().getInt("default_address_id", 0);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        Address address = mItems.get(position);
        holder.name.setText(address.getName());
        holder.address.setText(address.getCity() + address.getAddress());
        holder.phone.setText(address.getPhone());
        if (address.getId() == addressId) {
            holder.mRadio.setChecked(true);
            lastSelectedItem = position;
        } else {
            holder.mRadio.setChecked(false);
        }
        holder.itemView.setOnLongClickListener(v -> showDeleteDialog(address.getId(), position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView name;
        @Bind(R.id.tv_phone)
        TextView phone;
        @Bind(R.id.tv_address)
        TextView address;
        @Bind(R.id.radio_btn)
        RadioButton mRadio;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                addressId = mItems.get(mSelectedItem).getId();
//                notifyItemRangeChanged(0, mItems.size());
                notifyItemChanged(mSelectedItem);
                notifyItemChanged(lastSelectedItem);
                lastSelectedItem = mSelectedItem;
                if (listener != null)
                    listener.onSelectedChanged(mSelectedItem);
            };
            itemView.setOnClickListener(v -> {
                mSelectedItem = getAdapterPosition();
                Intent intent = new Intent(mContext, AddressAddActivity.class);
                intent.putExtra("address", mItems.get(mSelectedItem));
                mContext.startActivityForResult(intent, 120);
            });
            mRadio.setOnClickListener(clickListener);
        }
    }

    private boolean showDeleteDialog(int id, int position) {
        new MaterialDialog.Builder(mContext)
                .title("删除地址")
                .content("是否删除该条地址?")
                .negativeText("取消")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            ShopModel.getInstance().delAddress(id)
                                    .subscribe(new ServiceResponse<Object>() {
                                        @Override
                                        public void onNext(Object o) {
                                            if (JUtils.getSharedPreference().getInt("default_address_id", -1) == mItems.get(position).getId() || getItemCount() <= 1) {
                                                JUtils.getSharedPreference().edit().remove("default_address_id").apply();
                                                JUtils.getSharedPreference().edit().remove("default_address").apply();
                                            }
                                            mItems.remove(position);
                                            notifyItemRemoved(position);
                                            JUtils.Toast("删除成功");
                                        }
                                    });
                        }
                    }
                })
                .show();
        return true;
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    private OnSelectedListener listener;

    //id为item的id
    public interface OnSelectedListener {
        void onSelectedChanged(int id);
    }
}
