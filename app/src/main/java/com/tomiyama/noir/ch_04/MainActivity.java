package com.tomiyama.noir.ch_04;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

    }

    public void onClick(View v){

        int id = v.getId();
        switch (id){
            // ファイルへの書き込み
            case R.id.writeButton:
                try {
                    String str = editText.getText().toString();
                    data2file(this,str.getBytes(),"text.txt");
                } catch (Exception e) {
                    editText.setText("書き込み失敗しました");
                }
                break;
            // ファイルからの読み込み
            case R.id.readButton:
                try {
                    String str = new String(file2data(this,"text.txt"));
                    editText.setText(str);
                } catch (Exception e) {
                    editText.setText("読み込み失敗しました");
                }
                break;
        }

    }

    // バイトデータ -> ファイル
    private static void data2file(Context context,byte[] w,String fileName) throws Exception {
        OutputStream out = null;

        try {
            // ファイル出力ストリームのオープン
            out = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            // バイト配列の書き込み
            out.write(w,0,w.length);
            // ファイル出力ストリームのクローズ
            out.close();
        } catch (Exception e) {
            try{
                if(out != null) out.close();
            }catch (Exception e2){

            }throw e;
        }
    }

    // ファイル -> バイトデータ
    private static byte[] file2data(Context context,String fileName) throws Exception {
        int size;
        byte[] w = new byte[1024];
        InputStream in = null;
        ByteArrayOutputStream out = null;

        try{
            in = context.openFileInput(fileName);
            //バイト配列の読み込み
            out = new ByteArrayOutputStream();
            while (true){
                size = in.read(w);
                if(size <= 0) break;
                out.write(w,0,size);
            }
            out.close();

            // ファイル入力ストリームのクローズ
            in.close();

            // ByteArrayOutputStreamオブジェクトのバイト配列化
            return out.toByteArray();
        }catch (Exception e){
            try{
                if(in != null) in.close();
                if(out != null) out.close();
            }catch (Exception e2){

            }throw e;
        }
    }
}
