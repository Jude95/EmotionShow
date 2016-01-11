package com.jude.emotionshow.presentation.shop;

import android.content.Context;
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
    public List<Address> mItems;
    private Context mContext;
    private int addressId;

    public AddressAdapter(Context context, List<Address> items) {
        mContext = context;
        mItems = items;
        addressId = JUtils.getSharedPreference().getInt("default_address_id", 0);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        Address address = mItems.get(position);
        holder.name.setText(address.getName());
        holder.address.setText(address.getAddress());
        holder.phone.setText(address.getPhone());
        holder.mRadio.setChecked(address.getId() == addressId);
        holder.itemView.setOnLongClickListener(v -> showDeleteDialog(address.getId()));
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
                notifyItemRangeChanged(0, mItems.size());
                if (listener != null)
                    listener.onSelectedChanged(mSelectedItem);
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }

    private boolean showDeleteDialog(int id) {
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
