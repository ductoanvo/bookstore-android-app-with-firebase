package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import java.util.List;

public class Order {
    private String uid;
    private double total;
    private List<CartDetail> cartDetails;

    public Order(String uid, double total, List<CartDetail> cartDetails) {
        this.uid = uid;
        this.total = total;
        this.cartDetails = cartDetails;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uid='" + uid + '\'' +
                ", total=" + total +
                ", cartDetails=" + cartDetails +
                '}';
    }
}
