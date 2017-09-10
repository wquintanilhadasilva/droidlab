package com.example.wquin.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String[] lstEstados;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista3);

        listView = (ListView) findViewById(R.id.lista3);

        //Criar um array de String
        lstEstados = new String[] {"São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
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

        //Criar um ArrayAdapter do tipo String, que vai fazer aparecer as Strings acima
        //em seu ListView do tipo que checked
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, lstEstados);

        //Associar o adapter ao listview
        listView.setAdapter(adapter);
    }

    //Este evento foi definido no arquivo de layout xml
    public void btnMarcados_click(View view){
        String lstrEstadosSelecionados = "";

        //Cria um array com os itens selecionados no listview
        SparseBooleanArray checked = listView.getCheckedItemPositions();

        for (int i = 0; i < checked.size(); i++){
            //pega os itens marcados
            lstrEstadosSelecionados += lstEstados[checked.keyAt(i)] + ",";
        }
        Toast.makeText(this, "Estados marcados : " + lstrEstadosSelecionados, Toast.LENGTH_LONG).show();
    }

    //Este evento foi definido no arquivo de layout xml
    public void btnDesmarcados_click(View view){
        String lstrEstadosSelecionados = "";

        for (int i = 0; i < listView.getCount(); i++){
            //pega os itens desmarcados
            if (listView.isItemChecked(i) == false){
                lstrEstadosSelecionados += lstEstados[i] + ",";
            }
        }
        Toast.makeText(this, "Estados desmarcados : " + lstrEstadosSelecionados, Toast.LENGTH_LONG).show();
    }
}
