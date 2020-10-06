package com.example.qrandbarcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn=findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scanCode();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("SCANNING IN PROCESS");
        integrator.setPrompt("Align the code with the Scanner");
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!= null){
            if(result.getContents()!= null){
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scan Successful");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                Toast.makeText(this,"Result Not Found",Toast.LENGTH_LONG).show();
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);

        }
    }
}