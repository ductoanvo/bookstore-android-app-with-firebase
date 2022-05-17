package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CartDetail> details;

    public CartAdapter(Context context, int layout, List<CartDetail> details) {
        this.context = context;
        this.layout = layout;
        this.details = details;
    }

    @Override
    public int getCount() {
        return details.size();
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

        final CartDetail cartDetail = details.get(i);

        ImageView imageView = view.findViewById(R.id.bookThumbnail_CartScreen);
        TextView bookName = view.findViewById(R.id.tvBookName_CartScreen);
        TextView quantity = view.findViewById(R.id.tvCartQuantity_CartScreen);
        TextView totalLine = view.findViewById(R.id.tvTotalLine_CartScreen);


        StorageReference storageRef = FirebaseStorage.getInstance().getReference(cartDetail.getProduct().getThumbnail());

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        bookName.setText(cartDetail.getProduct().getBookName());
        quantity.setText(String.format("Quantity: %s", cartDetail.getQuantity()));
        totalLine.setText(String.format("Total price: %s", cartDetail.getTotalPrice()));

        view.findViewById(R.id.btnDetaleProduct_CartScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(cartDetail.getProduct().getId()).commit();
                Toast.makeText(view.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ProductActivity.class));
            }
        });

        return view;
    }

}
