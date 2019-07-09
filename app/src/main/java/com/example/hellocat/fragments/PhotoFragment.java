package com.example.hellocat.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hellocat.R;


public class PhotoFragment extends Fragment {

    private ListFragment.OnFragmentInteractionListener mListener;

    public PhotoFragment() {
        // Required empty public constructor
    }

    private ImageView ivPhoto;
    private Button btnPhoto;
    private Button btnSavePhoto;
    private TextView tvPhoto;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPhoto = inflater.inflate(R.layout.fragment_photo, container, false);
        ivPhoto = viewPhoto.findViewById(R.id.ivPhoto);
        tvPhoto = viewPhoto.findViewById(R.id.tvPhoto);
        btnPhoto = viewPhoto.findViewById(R.id.btnPhoto);
        btnSavePhoto = viewPhoto.findViewById(R.id.btnSavePhoto);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Es una versión mayor que la 6.0
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });


        return viewPhoto;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permisos de la cámara concedidos", Toast.LENGTH_LONG).show();

                // LANZO LA CÁMARA DE FORMA NATIVA
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "Permisos de la cámara denegados", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");  // CREO UN BITMAP A PARTIR DE LOS DATOS RECOGIDOS POR LA CÁMARA
            ivPhoto.setImageBitmap(photo);
        }

    }
}
