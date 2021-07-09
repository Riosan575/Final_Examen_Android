package com.example.examenfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenfinal.entities.Entranador;
import com.example.examenfinal.services.EntrenadorService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearEntrenador extends AppCompatActivity {

    EditText etNombre;
    EditText etPueblo;
    EditText imagen;
    ImageView imageView;
    Button btnImagen;
    Button btnCrear;
    String base64img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenador);

        solicitarPermisos();


        etNombre = findViewById(R.id.etNombre);
        imagen = findViewById(R.id.imagen);
        etPueblo = findViewById(R.id.etPueblo);
        imageView = findViewById(R.id.imgVer);
        btnImagen = findViewById(R.id.btnGallery);
        btnCrear = findViewById(R.id.btnCrear);

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,101);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString();
                String pueblo = etPueblo.getText().toString();
                String images = imagen.getText().toString();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk/entrenador/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EntrenadorService service = retrofit.create(EntrenadorService.class);

                Entranador entranador = new Entranador();

                entranador.setNombres(nombre);
                entranador.setPueblo(pueblo);
                entranador.setImagen(images);

                Call<Entranador> call = service.create(entranador);

                call.enqueue(new Callback<Entranador>() {
                    @Override
                    public void onResponse(Call<Entranador> call, Response<Entranador> response) {

                    }

                    @Override
                    public void onFailure(Call<Entranador> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
        }

            try {
                if (resultCode == RESULT_OK) {
                    Uri path = data.getData();
                    imageView.setImageURI(path);

                    final InputStream imageStream = getContentResolver().openInputStream(path);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    base64img = convertBase64(selectedImage);


                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    private void solicitarPermisos() {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10001);
        }
    }
    private String convertBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        base64img = Base64.encodeToString(b, Base64.DEFAULT);
        imagen.setText(base64img);
        return base64img;
    }

}