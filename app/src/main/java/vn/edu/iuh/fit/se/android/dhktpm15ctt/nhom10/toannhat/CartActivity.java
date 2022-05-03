package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private List<CartDetail> cartDetails;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        cartDetails = getCartDetails();
        Log.d("carts", cartDetails.toString());
        CartAdapter cartAdapter = new CartAdapter(this, R.layout.cart_view, cartDetails);
        ListView listView = findViewById(R.id.listViewCart_CartScreen);
        listView.setAdapter(cartAdapter);
    }

    private List<CartDetail> getCartDetails() {
        List<CartDetail> cartDetails = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        Map<String, ?> cartDetailsMap = (Map<String, ?>) sharedPreferences.getAll();
        if (cartDetailsMap.size() > 0) {
            for (Map.Entry<String, ?> entry : cartDetailsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                Log.d("cart", key + " " + value);
                Product product = getProduct(key);
                CartDetail cartDetail = new CartDetail(product, Integer.parseInt(value));
                cartDetails.add(cartDetail);
            }
        }
        return cartDetails;
    }

    private Product getProduct(String id) {
        Product product = new Product();
        db.collection("books")
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> data = document.getData();
                                    product.setId(Objects.requireNonNull(Objects.requireNonNull(data).get("id")).toString());
                                    product.setBookName(Objects.requireNonNull(data.get("bookName")).toString());
                                    product.setThumbnail(Objects.requireNonNull(data.get("thumbnail")).toString());
                                    product.setAuthor(Objects.requireNonNull(data.get("author")).toString());
                                    product.setCost(Double.parseDouble(Objects.requireNonNull(data.get("cost")).toString()));
                                    product.setDescription(Objects.requireNonNull(data.get("description")).toString());
                                } else
                                    Log.d("data", "No such document");
                            }
                        }
                );
        return product;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_cart);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_item_product_list) {
            startActivity(new Intent(this, ProductActivity.class));
        }

        if (item.getItemId() == R.id.menu_item_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}