package edu.svcet.javatutorial;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Overview extends AppCompatActivity {

    TextView textView;
    WebView webView;
    PDFView pdfView;
    Integer pageNumber = 0;
    String file_name = null;

    String[] list = {"javaintroduction.pdf","fundamentalofoops.pdf","jdkjrejvm.pdf","variableanddatatypes.pdf", "operators.pdf", "decisionandlooping.pdf", "classobjectmethods.pdf", "numbercharacter.pdf",
            "stringarray.pdf","inheritence.pdf","polymorphism.pdf","encapsulation.pdf","abstraction.pdf","interfaceabstract.pdf","packages.pdf","file.pdf",
            "exception.pdf","multithreading.pdf","networking.pdf","synchronization.pdf","serialization.pdf","collectionframework.pdf","jdbc.pdf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        pdfView=findViewById(R.id.pdfView);
        Intent intent = getIntent();
        String[] topics=null;
        if (intent != null) {
            Resources resources=getResources();
            topics=resources.getStringArray(R.array.javatopic);
            int index = intent.getIntExtra("index", 0);
            file_name = list[index];
            getSupportActionBar().setTitle(topics[index]);
            Toast.makeText(this,"File Open",Toast.LENGTH_SHORT).show();
            MyAsyncTask task=new MyAsyncTask(file_name);
            task.execute();
        }
       // pdfView.fromAsset(file_name).load();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     /*   AssetManager manager=getAssets();
        StringBuilder builder=new StringBuilder();
        try {
            InputStream in=manager.open("index.html");
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String str=null;
            while((str=br.readLine())!=null){
                builder.append(str);
                builder.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

*/



       /* webView.getSettings().setDefaultFontSize(30);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/"+"unit4.pdf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class MyAsyncTask extends AsyncTask<Void,Void,File>{
        InputStream in=null;
        FileOutputStream out=null;
        File myfile=null;
        String filename;
        AssetManager assetManager=getAssets();
        MyAsyncTask(String filename){
            this.filename=filename;
        }

        @Override
        protected File doInBackground(Void... voids) {
            myfile=new File(getExternalFilesDir("javatutorial"),filename);
            if(myfile.exists()){
                return myfile;
            }
            else{
                myfile=getExternalFilesDir("javatutorial");
                if(myfile==null){
                    myfile.mkdir();
                }
                File file=new File(myfile,filename);
                try{
                    in=assetManager.open(filename);
                    out=new FileOutputStream(file);
                    copyFile(in,out);
                    in.close();
                    out.flush();
                    out.close();
                    myfile=new File(getExternalFilesDir("javatutorial")+"/"+filename);
                    return myfile;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }




            return null;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            pdfView.fromFile(file).load();
        }
    }
    public void copyFile(InputStream in,FileOutputStream out) throws IOException {
        int value=0;
        byte[] buffer=new byte[1024];
        while((value=in.read(buffer))!=-1){
            out.write(buffer,0,value);
        }
    }
}

