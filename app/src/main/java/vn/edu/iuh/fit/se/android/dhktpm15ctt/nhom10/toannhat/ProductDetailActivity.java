package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgThumbnail;
    TextView txtBookName, txtAuthor, txtCost, txtDescription;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mapping();

        Product product =(Product) getIntent().getSerializableExtra("productKey");

        txtBookName.setText(product.getBookName());
        txtCost.setText(String.valueOf(product.getCost()));
        txtAuthor.setText(product.getAuthor());
        txtDescription.setText(product.getDescription());

        StorageReference storageRef = FirebaseStorage.getInstance().getReference(product.getThumbnail());

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imgThumbnail.setImageBitmap(bitmap);
                        }
                    });
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void mapping() {
        imgThumbnail = findViewById(R.id.imgThumbnail_DetailScreen);
        txtBookName = findViewById(R.id.txtBookName_DetailScreen);
        txtAuthor = findViewById(R.id.txtAuthor_DetailScreen);
        txtCost = findViewById(R.id.txtCost_DetailScreen);
        txtDescription = findViewById(R.id.txtDescription_DetailScreen);
        btnAddToCart = findViewById(R.id.btnAddToCart_DetailScreen);
    }
}