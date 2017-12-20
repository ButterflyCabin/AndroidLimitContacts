package com.yehu.androidlimitcontacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yehu.androidlimitcontacts.bean.ContactsPerson;
import com.yehu.androidlimitcontacts.widget.CircleImageView;
import com.yehu.androidlimtcontacts.R;

import java.util.List;

import com.yehu.androidlimitcontacts.utils.ImageLoaderUtils;

/**
 * 创建日期：2017/12/20 17:14
 *
 * @author yehu
 *         类说明：
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> {
    private Context mContext;
    private List<ContactsPerson> lists;

    public ContactsAdapter(Context context, List<ContactsPerson> list) {
        mContext = context;
        lists = list;
    }


    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsHolder(View.inflate(mContext, R.layout.item_contacts_person, null));
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        ContactsPerson person = lists.get(position);
        if (null != person) {
            holder.position.setText(String.valueOf(position));
            holder.name.setText(getMaskStr(person.getName()));
            holder.phoneNumber.setText(getMaskStr(person.getPhoneNumber()));
            ImageLoader.getInstance().displayImage(person.getHeadUrl(), holder.headIcon, ImageLoaderUtils.getOptions(R.mipmap.ic_launcher_round));
        }

    }

    public String getMaskStr(String str) {
        String showStr = str.substring(str.length() - 1, str.length());
        return "******" + showStr;
    }

    @Override
    public int getItemCount() {
        return null == lists ? 0 : lists.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder {
        TextView position;
        TextView name;
        TextView phoneNumber;
        CircleImageView headIcon;

        public ContactsHolder(View itemView) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.tv_position);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phoneNumber = (TextView) itemView.findViewById(R.id.tv_phone);
            headIcon = (CircleImageView) itemView.findViewById(R.id.iv_headIcon);
        }
    }
}
