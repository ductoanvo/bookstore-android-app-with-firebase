package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final List<Product> products;


//    ProgressDialog progressDialog;


    public ProductAdapter(Context context, int layout, List<Product> products) {
        this.context = context;
        this.layout = layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        ImageView imgThumbnail = view.findViewById(R.id.imgThumbnail_ProductScreen);
        TextView txtBookName = view.findViewById(R.id.txtBookName_ProductScreen);
        TextView txtCost = view.findViewById(R.id.txtCost_ProductScreen);
        TextView txtAuthor = view.findViewById(R.id.txtAuthor_ProductScreen);


        Product product = products.get(i);


        txtBookName.setText(product.getBookName());
        txtCost.setText(String.valueOf(product.getCost()));
        txtAuthor.setText(product.getAuthor());

        StorageReference storageRef = FirebaseStorage.getInstance().getReference(product.getThumbnail());

//        StorageReference spaceRef = storageRef.child(product.getThumbnail());

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", (Serializable) product);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
