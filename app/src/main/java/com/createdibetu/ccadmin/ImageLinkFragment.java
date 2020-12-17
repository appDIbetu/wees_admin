package com.createdibetu.ccadmin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ImageLinkFragment extends Fragment {
    private Button ivChoose, ivUpload;
    private EditText etName;
    private ImageView ivResult;
    private TextView tvUrl;
    private String fileName;

    private static final int Selected = 100;
    Uri uriImage;

    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;


    public ImageLinkFragment() {
        // Required empty public constructor
    }

    public static ImageLinkFragment newInstance() {
        ImageLinkFragment fragment = new ImageLinkFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image_link, container, false);
        ivChoose = root.findViewById(R.id.bt_choose);
        ivUpload = root.findViewById(R.id.bt_upload);
        etName = root.findViewById(R.id.et_name);
        ivResult = root.findViewById(R.id.iv_result);
        tvUrl = root.findViewById(R.id.tv_url);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker, Selected);
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivUpload.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivUpload.setEnabled(true);
        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Selected) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Log.v("uriImage"," Okey"+data);
                uriImage = data.getData();
                String name[]=uriImage.toString().split("/");
                 fileName=name[(name.length-1)];
                Log.v("fileName"," Okey"+fileName);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver()
                            , uriImage);
                    ivResult.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }



    private void UploadImage() throws IOException {

        imageRef = storageRef.child("Images/" + fileName + "." + GetExtension(uriImage));
        Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImage);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(true);

        // uploadTask = imageRef.putFile(uriImage);
        uploadTask = imageRef.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) /
                        taskSnapshot.getTotalByteCount();
                progressDialog.incrementProgressBy((int) progress);

            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Successfully Uploaded !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


                Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        String ImageURL = downloadUrl.toString();
                        tvUrl.setText(ImageURL);
                    }
                });
                //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                // String ImageURL = downloadUrl.toString();
                //tvUrl.setText(ImageURL);-->
            }
        });

    }

    private String GetExtension(Uri uriImage) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uriImage));
    }



}