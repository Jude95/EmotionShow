package com.jude.emotionshow.presentation.shop;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/28.
 */
@Deprecated
public class AddressViewHolder extends BaseViewHolder<Address> {
    @Bind(R.id.tv_name)
    TextView name;
    @Bind(R.id.tv_phone)
    TextView phone;
    @Bind(R.id.tv_address)
    TextView address;
    @Bind(R.id.cb)
    CheckBox checkBox;
    private int checkedId = 0;

    private OnSelectedListener listener;

    public AddressViewHolder(ViewGroup parent,OnSelectedListener listener) {
        super(parent, R.layout.item_address);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Address data) {
        name.setText(data.getName());
        phone.setText(data.getPhone());
        address.setText(data.getCity() + data.getAddress());
        itemView.setOnLongClickListener(v -> showDeleteDialog(data.getId()));
        checkBox.setChecked(data.getId()==checkedId);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                JUtils.Log("isChecked:"+isChecked+" "+data.getId()+" "+checkedId);
                if (isChecked) {
                    checkedId = data.getId();
                    listener.onSelectedChanged(data);
                }
            }
        });
    }

    private boolean showDeleteDialog(int id) {
        new MaterialDialog.Builder(getContext())
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

    public interface OnSelectedListener{
        void onSelectedChanged(Address id);
    }
}
