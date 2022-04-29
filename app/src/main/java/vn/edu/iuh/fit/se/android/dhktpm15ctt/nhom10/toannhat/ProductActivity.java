package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {

    ListView lvProduct;
    ProductAdapter productAdapter;
    ArrayList<Product> products;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        db = FirebaseFirestore.getInstance();

        lvProduct = findViewById(R.id.lvProduct);
        products = new ArrayList<>();

//        getData();

        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = new Product();
                                product.setId(document.getId());
                                product.setAuthor(Objects.requireNonNull(document.getData().get("author")).toString());
                                product.setBookName(Objects.requireNonNull(document.getData().get("bookName")).toString());
                                product.setDescription(Objects.requireNonNull(document.getData().get("description")).toString());
                                product.setCost(Double.parseDouble(Objects.requireNonNull(document.getData().get("cost")).toString()));
                                product.setThumbnail(Objects.requireNonNull(document.getData().get("thumbnail")).toString());
                                products.add(product);
                            }
                            productAdapter = new ProductAdapter(ProductActivity.this, R.layout.product_view, products);
                            lvProduct.setAdapter(productAdapter);
                        }
                    }
                });
    }

    private void getData() {
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("data", document.getId() + " => " + document.getData());
                                Product product = new Product();
                                product.setId(document.getId());
                                product.setAuthor(Objects.requireNonNull(document.getData().get("author")).toString());
                                product.setBookName(Objects.requireNonNull(document.getData().get("bookName")).toString());
                                product.setDescription(Objects.requireNonNull(document.getData().get("description")).toString());
                                product.setCost(Double.parseDouble(Objects.requireNonNull(document.getData().get("cost")).toString()));

                                Log.d("product", product.toString());

                                products.add(product);
                            }
                        }
                    }
                });
    }
}