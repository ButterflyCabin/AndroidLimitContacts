package com.yehu.androidlimitcontacts.bean;

/**
 * 创建日期：2017/12/20 16:21
 *
 * @author yehu
 *         类说明：
 */
public class ContactsPerson {
    private long id;
    private String name;
    private String phoneNumber;
    private String headUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return "ContactsPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
