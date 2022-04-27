package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

        lvProduct = (ListView)findViewById(R.id.lvProduct);
        products = new ArrayList<>();

//        getData();

        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Product product = new Product();
                                product.setId(document.getId());
                                product.setAuthor(document.getData().get("author").toString());
                                product.setBookName(document.getData().get("bookName").toString());
                                product.setDescription(document.getData().get("description").toString());
                                product.setCost(Double.valueOf(document.getData().get("cost").toString()));
                                products.add(product);
                            }
                        }
                    }
                });

//        products.add(new Product("Duc Toan", "Sach tieng anh", 70000, "sach thi la sach thoi", "chua co"));
//        products.add(new Product("Dinh Nhat", "Sach tieng anh", 50000, "sach thi la sach thoi", "chua co"));
//        products.add(new Product("Van Huy", "Sach tieng anh", 60000, "sach thi la sach thoi", "chua co"));
//        for (Product product:
//             products) {
//            Log.d("product", product.toString());
//        }

        Log.d("products size", String.valueOf(products.size()));
        productAdapter = new ProductAdapter(this, R.layout.product_view, products);
        lvProduct.setAdapter(productAdapter);

    }

    private void getData() {
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("data", document.getId() + " => " + document.getData());
                                Product product = new Product();
                                product.setId(document.getId());
                                product.setAuthor(document.getData().get("author").toString());
                                product.setBookName(document.getData().get("bookName").toString());
                                product.setDescription(document.getData().get("description").toString());
                                product.setCost(Double.valueOf(document.getData().get("cost").toString()));

                                Log.d("product", product.toString());

                                products.add(product);
                            }
                        }
                    }
                });
    }
}