package com.example.myapplication;

public class products {
    String userid;
    String productcatgeory;
    String productdesc;
    public products() {
    }

    public products(String userid, String productcatgeory, String productdesc) {
        this.userid = userid;
        this.productcatgeory = productcatgeory;
        this.productdesc = productdesc;
    }

    public String getUserid() {
        return userid;
    }

    public String getProductcatgeory() {
        return productcatgeory;
    }

    public String getProductdesc() {
        return productdesc;
    }
}
