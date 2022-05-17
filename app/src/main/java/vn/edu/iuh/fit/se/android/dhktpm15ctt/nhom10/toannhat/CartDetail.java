package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import androidx.annotation.NonNull;

public class CartDetail {
    private Product product;
    private int quantity;

    public CartDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getCost() * quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
