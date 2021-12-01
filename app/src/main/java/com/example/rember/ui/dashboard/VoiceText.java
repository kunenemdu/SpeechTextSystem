package com.example.rember.ui.dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rember.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class VoiceText extends Fragment {
    protected static final int RESULT_SPEECH = 1;
    private static final int RESULT_OK = 1;
    private static final int AUDIO_REQUEST_CODE = 2;
    private static final int WRITE_REQUEST_CODE = 1;
    ImageButton btn_speak;
    Button btn_save;
    TextView result;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_speak = getView().findViewById(R.id.btn_speak);
        btn_save = getView().findViewById(R.id.btn_save);
        result = getView().findViewById(R.id.result);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, WRITE_REQUEST_CODE);
            }
        }

        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) !=
                        PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.RECORD_AUDIO};
                    requestPermissions(permissions, AUDIO_REQUEST_CODE);
                } else {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    try {
                        startActivityForResult(intent, RESULT_SPEECH);
                    }catch (Exception e){
                        Toast.makeText(getContext().getApplicationContext(),
                                "Error " + e, Toast.LENGTH_SHORT).show();
                        result.setText("");
                    }
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!result.getText().equals(""))
                    saveToDocs(result.getText().toString().trim());
                else
                    Toast.makeText(getContext(), "Nothing Recorded!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveToDocs(String result) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "Patient.docx");

        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    result.setText(text.get(0));
                }
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    OutputStream stream = null;
                    try {
                        stream = getContext().getContentResolver().openOutputStream(uri);
                        stream.write("Welcome!".getBytes());
                        stream.close();
                        Toast.makeText(getContext().getApplicationContext(),
                                "Saved!", Toast.LENGTH_LONG).show();
                    }catch (IOException e) {
                        Toast.makeText(getContext().getApplicationContext(),
                                "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
}