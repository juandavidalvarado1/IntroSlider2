package com.shubham.introslider;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class SecondFragment extends Fragment {

    Button btnCamara;
    ImageView imageView;
    String rutaImagen;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_second, container, false);

       // btnCamara = root.findViewById(R.id.btnCamara);
        imageView = root.findViewById(R.id.imageView);

        //btnCamara.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
               abrirCamara();
        //    }
        //});

        return root;


    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

         // if(intent.resolveActivity(getActivity().getPackageManager())!=null){
              File imagenArchivo= null;

              try {
                  imagenArchivo = crearImagen();
              }
              catch (IOException ex){
                Log.e("error",ex.toString());
              }

              if(imagenArchivo != null){

                Uri fotoUri = FileProvider.getUriForFile(this.getContext(),"com.shubham.introslider.fileprovider",imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                  startActivityForResult(intent,1);
              }

         //}
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == RESULT_OK){
          //  Bundle extras = data.getExtras();
          //  Bitmap imgBitmap = (Bitmap) extras.get("data");
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imageView.setImageBitmap(imgBitmap);

        }
    }

    private File crearImagen() throws IOException {

        String nombreImagen = "foto_";

        File directorio = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }



}