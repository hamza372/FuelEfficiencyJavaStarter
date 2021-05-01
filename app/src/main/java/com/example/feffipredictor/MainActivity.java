package com.example.feffipredictor;

import android.content.res.AssetFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {


    float[] mean = {5.477707f,195.318471f,104.869427f,2990.251592f,15.559236f,75.898089f,0.624204f,0.178344f,0.197452f};
    float[] std = {1.699788f,104.331589f,38.096214f,843.898596f,2.789230f,3.675642f,0.485101f,0.383413f,0.398712f};

    Interpreter interpreter;
    ScrollView sv;
    EditText cylinders,displacement,horsePower,weight,accelration,modelYear;
    Spinner origin;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            interpreter = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sv = (ScrollView)findViewById(R.id.sv);
        cylinders = findViewById(R.id.editText);
        displacement = findViewById(R.id.editText2);
        horsePower = findViewById(R.id.editText3);
        weight = findViewById(R.id.editText4);
        accelration = findViewById(R.id.editText5);
        modelYear = findViewById(R.id.editText6);
        origin = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,new String[]{"USA","Europe","Japan"});
        origin.setAdapter(arrayAdapter);
        result = findViewById(R.id.textView2);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.scrollTo( sv.getBottom(),0);
                doInference();
            }
        });
    }

    public void doInference(){

    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("automobile.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }
}
