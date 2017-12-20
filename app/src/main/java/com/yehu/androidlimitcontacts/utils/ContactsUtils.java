package com.yehu.androidlimitcontacts.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.yehu.androidlimitcontacts.bean.ContactsPerson;
import com.yehu.androidlimitcontacts.bean.Page;

/**
 * 创建日期：2017/12/19 15:27
 *
 * @author yehu
 *         类说明：
 */
public class ContactsUtils {

    private Context mContext;

    public ContactsUtils(Context context) {
        mContext = context;
    }

    /**
     * 获取系统联系人信息
     *
     * @return
     */
    public List<ContactsPerson> getSystemContactsPersons() {
        List<ContactsPerson> infos = new ArrayList<ContactsPerson>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        // 使用ContentResolver查找联系人数据  
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, "ASC");
        // 遍历查询结果，获取系统中所有联系人  
        while (cursor.moveToNext()) {
            ContactsPerson info = new ContactsPerson();
            // 获取联系人ID  
            String contactId = cursor.getString(0);
            // 获取联系人的名字  
            info.setName(cursor.getString(1));
            // 使用ContentResolver查找联系人的电话号码  
            Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, "ASC");
            // 遍历查询结果，获取该联系人的多个电话号码
            if (null != phones) {
                while (phones.moveToNext()) {
                    // 获取查询结果中电话号码列中数据。
                    info.setPhoneNumber(phones.getString(0));
                    infos.add(info);
                }
            } else {
                infos.add(info);
            }
            info = null;
            phones.close();
        }
        cursor.close();
        return infos;

    }

    /**
     * 分页查询系统联系人信息
     *
     * @param pageSize 每页最大的数目
     * @param page     页数
     * @return
     */
    public Page<List<ContactsPerson>> getContactsByPage(int pageSize, int page) {
        Page<List<ContactsPerson>> tempPage = new Page<>();
        tempPage.data = new ArrayList<ContactsPerson>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
        tempPage.count = getAllPhoneNums();
        tempPage.pages = tempPage.count / pageSize + (tempPage.count % pageSize == 0 ? 0 : 1);
        if (page < 1) {
            page = 1;
        } else if (page > tempPage.pages) {
            page = tempPage.pages;
        }
        tempPage.page = page;
        tempPage.limit = pageSize <= 0 ? 10 : pageSize;
        int currentOffset = (tempPage.page - 1) * tempPage.limit;
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, ContactsContract.Contacts._ID + " ASC limit " + pageSize + " offset " + currentOffset);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactsPerson info = new ContactsPerson();
                info.setName(cursor.getString(0));
                info.setPhoneNumber(cursor.getString(1));
                info.setId(cursor.getLong(2));
                info.setHeadUrl(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, info.getId()).toString());
                tempPage.data.add(info);
                Log.e("ContactsPerson: ", info.toString());
                info = null;
            }
            Log.e("ContactsPerson: ", tempPage.toString());
            cursor.close();
        }
        return tempPage;
    }

    /**
     * 获得系统联系人的所有记录数目
     *
     * @return
     */

    public int getAllCounts() {
        int num = 0;
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        // 使用ContentResolver查找联系人数据
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        // 遍历查询结果，获取系统中所有联系人
        if (null != cursor) {
            num = cursor.getCount();
            cursor.close();
        }
        return num;
    }

    public int getAllPhoneNums() {
        int num = 0;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.DATA1,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if (null != cursor) {
            num = cursor.getCount();
            cursor.close();
        }
        return num;
    }

}
