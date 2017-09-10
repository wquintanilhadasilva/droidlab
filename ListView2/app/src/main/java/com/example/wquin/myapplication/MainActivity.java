package com.example.wquin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String []s = new String[] {"São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas","São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
                "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas"};
        lv = (ListView)findViewById(R.id.listView1);
        //lv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,s));

//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.tv,s));
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                ListView lv = (ListView) arg0;
//                TextView tv = (TextView) lv.getChildAt(arg2);
//                String s = tv.getText().toString();
//                Toast.makeText(MainActivity.this, "Clicked item is"+s, Toast.LENGTH_LONG).show();
//            } });

        //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,s));
        //lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, s));

        btn =(Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int p = lv.getCheckedItemPosition();
//                if(p!=ListView.INVALID_POSITION) {
//                    String s = ((TextView) lv.getChildAt(p)).getText().toString();
//                    Toast.makeText(MainActivity.this, "Selected item is " + s, Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(MainActivity.this, "Nothing Selected..", Toast.LENGTH_LONG).show();
//                }

                SparseBooleanArray sp = lv.getCheckedItemPositions();
                StringBuffer str = new StringBuffer();
                for(int i=0;i<sp.size();i++){
                    if(sp.valueAt(i)==true){
                        String s = ((TextView) lv.getChildAt(i)).getText().toString();
                        str = str.append(" "+s);
                    }
                }
                Toast.makeText(MainActivity.this, "Selected items are "+str.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
