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

import com.example.examenfinal.entities.Entranador;
import com.example.examenfinal.entities.Pokemon;
import com.example.examenfinal.services.EntrenadorService;
import com.example.examenfinal.services.PokemonService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPokemon extends AppCompatActivity {

    EditText etNombre;
    EditText etTipo;
    EditText imagenPoke;
    EditText etLatitud;
    EditText etLongitud;
    ImageView imageView;
    Button btnImagenPoke;
    Button btnCrearPoke;
    String base64img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pokemon);

        solicitarPermisos();

        etNombre = findViewById(R.id.etNamePoke);
        etTipo = findViewById(R.id.etTipoPoke);
        imagenPoke = findViewById(R.id.etimagePoke);
        etLatitud = findViewById(R.id.etlatitudPoke);
        etLongitud = findViewById(R.id.etlongitudPoke);
        imageView = findViewById(R.id.imageViewPoke);
        btnImagenPoke = findViewById(R.id.btnCargarPoke);
        btnCrearPoke = findViewById(R.id.btnCrearPoke);

        btnImagenPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,101);
            }
        });

        btnCrearPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString();
                String tipo = etTipo.getText().toString();
                String etImagen = imagenPoke.getText().toString();
                String latitude = etLatitud.getText().toString();
                String longitud = etLongitud.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk/pokemons/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PokemonService service = retrofit.create(PokemonService.class);

                Pokemon pokemons = new Pokemon();
                pokemons.setNombre(nombre);
                pokemons.setTipo(tipo);
                pokemons.setImagen(etImagen);
                pokemons.setLatitude(latitude);
                pokemons.setLongitude(longitud);

                Call<Pokemon> call = service.create(pokemons);

                call.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {

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
        imagenPoke.setText(base64img);
        return base64img;
    }
}