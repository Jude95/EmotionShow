package com.jude.emotionshow.domain.entities;

/**
 * Created by mike on 2015/12/30.
 */
public class OrderDetail extends Order {
    private String address;
    private String name;
    private String phone;
    private String addcode;//邮编
    private String post_code;//运单号
    private int status;//0已下单 1已处理
    private long time;
    private String post_company;

    public String getPost_company() {
        return post_company;
    }

    public void setPost_company(String post_company) {
        this.post_company = post_company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddcode() {
        return addcode;
    }

    public void setAddcode(String addcode) {
        this.addcode = addcode;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
