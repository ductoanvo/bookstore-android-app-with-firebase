package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProductDetailActivity extends AppCompatActivity {
    private static final String TAG = "ProductDetailActivity";
    private Product product;
    private ImageView ivBookThumbnail;
    private ImageView ivPlus;
    private ImageView ivMinus;
    private TextView tvBookName;
    private TextView tvBookAuthor;
    private TextView tvBookPrice;
    private TextView tvBookDescription;
    private TextView tvQty;
    private Button btnAddToCart;
    private int qty = 1;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        firebaseAuth = FirebaseAuth.getInstance();

        ivBookThumbnail = findViewById(R.id.ivBookThumbnail_ProductDetailScreen);
        tvBookName = findViewById(R.id.tvBookName_ProductDetailScreen);
        tvBookAuthor = findViewById(R.id.tvBookAuthor_ProductDetailScreen);
        tvBookPrice = findViewById(R.id.tvCost_ProductDetailScreen);
        tvBookDescription = findViewById(R.id.tvBookDesc_ProductDetailScreen);
        tvQty = findViewById(R.id.tvQty_ProductDetailScreen);
        ivPlus = findViewById(R.id.ivPlus_ProductDetailScreen);
        ivMinus = findViewById(R.id.ivMinus_ProductDetailScreen);
        btnAddToCart = findViewById(R.id.btnAddToCart_ProductDetailScreen);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        setImage(product.getThumbnail());
        tvBookName.setText(product.getBookName());
        tvBookAuthor.setText(product.getAuthor());
        tvBookPrice.setText(String.format("%s VND", product.getCost()));
        tvBookDescription.setText(product.getDescription());

        ivPlus.setOnClickListener(v -> {
            qty++;
            tvQty.setText(String.valueOf(qty));
        });

        ivMinus.setOnClickListener(v -> {
            if (qty == 1) {
                Toast.makeText(this, "Must be equal or greater than 1!", Toast.LENGTH_SHORT).show();
                return;
            }
            qty--;
            tvQty.setText(String.valueOf(qty));
        });

        btnAddToCart.setOnClickListener(v -> {
            // Add to shared preference
            SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (sharedPreferences.contains(product.getId())) {
                int qty = sharedPreferences.getInt(product.getId(), 0);
                editor.putInt(product.getId(), qty + this.qty);
            } else {
                editor.putInt(product.getId(), this.qty);
            }

            editor.apply();

            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setImage(String imageUrl) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(imageUrl);

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        ivBookThumbnail.setImageBitmap(bitmap);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

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