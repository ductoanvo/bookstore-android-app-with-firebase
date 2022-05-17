package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        Button btnCheckout = findViewById(R.id.btnCheckout_CartScreen);
        if (cartDetails.size() == 0) {
            btnCheckout.setEnabled(false);
        }
        Log.d("carts", cartDetails.toString());

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
        Log.d("product_id", id);
        Product product = new Product();
        db.collection("books")
                .whereEqualTo("_id", id)
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("product", document.getId() + " => " + document.getData());
                                    product.setId(document.getId());
                                    product.setBookName(Objects.requireNonNull(document.get("bookName")).toString());
                                    product.setThumbnail(Objects.requireNonNull(document.get("thumbnail")).toString());
                                    product.setAuthor(Objects.requireNonNull(document.get("author")).toString());
                                    product.setCost(Double.parseDouble(Objects.requireNonNull(document.get("cost")).toString()));
                                    product.setDescription(Objects.requireNonNull(document.get("description")).toString());
                                }

                                CartAdapter cartAdapter = new CartAdapter(this, R.layout.cart_view, cartDetails);
                                ListView listView = findViewById(R.id.listViewCart_CartScreen);
                                listView.setAdapter(cartAdapter);



                                    Log.d("cart_detail", "empty");
                                    TextView tvTotal = findViewById(R.id.tvTotal_CartScreen);
                                    double total = 0.0;
                                    for (CartDetail cartDetail: cartDetails
                                    ) {
                                        total += cartDetail.getTotalPrice();
                                        Log.d("card_detail", cartDetail.toString());
                                    }
                                    tvTotal.setText(String.valueOf(total));

                                    double finalTotal = total;
                                    Button btnCheckout = findViewById(R.id.btnCheckout_CartScreen);
                                    btnCheckout.setEnabled(true);
                                    btnCheckout.setOnClickListener(view -> {
                                        Order order = new Order(
                                                firebaseAuth.getUid(),
                                                finalTotal,
                                                cartDetails
                                        );

                                        db.collection("orders")
                                                .add(order)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        // Xóa cart
                                                        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.clear();
                                                        editor.apply();

                                                        Intent intent = new Intent(CartActivity.this, ProductActivity.class);

                                                        Toast.makeText(CartActivity.this, "Order successfully", Toast.LENGTH_LONG).show();
                                                        startActivity(intent);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    });
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