package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.resources.TextAppearance;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AgendaActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "prueba2";
    private TextView texto1;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
   //     Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
    //    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    //            R.array.schedule, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
    //    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
    //    spinner.setAdapter(adapter);

        //texto1 = findViewById(R.id.txt2);

        db.collection("barberos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //texto1.setText(document.get("nombreBarberia").toString());
                                crearCarta(document.get("nombreBarberia").toString());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @SuppressLint("ResourceType")
    private void crearCarta(String nombreBarberia){
        Log.d(TAG, nombreBarberia);
        LinearLayout lLayout;
        StorageReference storageRef = storage.getReference();
        // Imagen
        final long ONE_MEGABYTE = 1024 * 1024;
        ImageView imagen = new ImageView(this);
        imagen.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dpToPx(200))));
        imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);

        StorageReference pathReference = storageRef.child(nombreBarberia);
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen.setImageBitmap(bitmap);
            }
        });

        // card
        MaterialCardView cardBarbero = new MaterialCardView(this);
        LinearLayout.LayoutParams lParam1 = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lParam1.setMargins((int) dpToPx(25),(int) dpToPx(25),(int) dpToPx(25),(int) dpToPx(25));

        cardBarbero.setCardElevation(dpToPx(10));
       // cardBarbero.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
         //       LinearLayout.LayoutParams.WRAP_CONTENT)));
        cardBarbero.setLayoutParams(lParam1);

        // linear layout
        LinearLayout linearBarbero = new LinearLayout(this);
        linearBarbero.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)));
        linearBarbero.setOrientation(LinearLayout.VERTICAL);

        //Texto
        TextView tv1 = new TextView(this);
        LinearLayout.LayoutParams lParam2 = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tv1.setLayoutParams(lParam2);
        //tv1.setTextAppearance(R.attr.textAppearanceHeadline1);
        tv1.setTextAppearance(R.style.TextAppearance_AppCompat_Headline);
        tv1.setText(nombreBarberia);

        //Spinner
        Spinner spinner = new Spinner(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.schedule, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        linearBarbero.addView(imagen);
        linearBarbero.addView(tv1);
        linearBarbero.addView(spinner);
        cardBarbero.addView(linearBarbero);
        lLayout = findViewById(R.id.lLayout1);
        lLayout.addView(cardBarbero);
    }

    private float dpToPx(int dp){
        float px=0;
// Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
// Convert the dps to pixels, based on density scale
        px = (int) (dp * scale + 0.5f);
        return px;
    }
}