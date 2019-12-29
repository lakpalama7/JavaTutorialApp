package edu.svcet.javatutorial;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.javarecyclerview);
        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null, chooser = null;
        switch (id) {
            case R.id.rateongoogleplay:
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                }
                catch (Exception e){
                    intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName()));
                    startActivity(intent);
                }
                return true;
            case R.id.email:
               /* intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to={"sherpasvcet@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL,to);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Java Tutorial");
                intent.setType("text/plain");
                intent.setType("message/rfc822");
               chooser=intent.createChooser(intent,"Send Mail");
                startActivity(chooser);*/
                intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"liveinbits@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Java Tutorial");
                intent.putExtra(Intent.EXTRA_CC, "liveinbits@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
                return true;

            case R.id.about:
                MyDialog dialog = new MyDialog();
                dialog.show(getSupportFragmentManager(), "Dialog");
                return true;
        }
        return false;
    }

    /*public void openWhatsApp() {
        try {
            String text = "This is a test";// Replace with your message.

            String toNumber = "916303062192"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}


class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    String[] sno;
    String[] topic;
    LayoutInflater inflater;

    MyRecyclerViewAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources res = context.getResources();
        // sno=res.getStringArray(R.array.javasno);
        topic = res.getStringArray(R.array.javatopic);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.java_single_layout, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        //holder.javasno.setText(sno[i]);
        holder.javatopic.setText(topic[i]);
        //Toast.makeText(context,"success "+ sno[i] +" "+topic[i],Toast.LENGTH_LONG).show();

    }


    @Override
    public int getItemCount() {
        return topic.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView javasno;
        TextView javatopic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // javasno=itemView.findViewById(R.id.javasno);
            javatopic = itemView.findViewById(R.id.javatopic);
            javatopic.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context,topic[getAdapterPosition()] +"Clicked ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, Overview.class);
            intent.putExtra("index", getAdapterPosition());
            context.startActivity(intent);

        }
    }
}
