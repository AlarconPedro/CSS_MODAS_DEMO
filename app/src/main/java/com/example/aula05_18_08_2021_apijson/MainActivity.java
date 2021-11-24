package com.example.aula05_18_08_2021_apijson;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ListActivity {

    ArrayList<Produtos> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Funcoes.verificaPermissao(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        try {
            String link = "http://cssmodas.herokuapp.com/api/";
            listaProdutos = new BuscaDados().execute(link).get();

            //criando a fonte de dados para a listagem
            ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, //classe que controla a lista (MainActivity)
                getLista(), //lista com os dados em formato ArrayList<HashMap>
                R.layout.listview_emissora, //modelo da linha da listagem
                new String[] { "produto" }, //campos que vem da lista de dados
                new int[] { R.id.lblNome } //campos visuais do modelo
            );

            //adicionando a fonte de dados na lista principal da tela
            setListAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<HashMap<String, String>> getLista() {
        ArrayList<HashMap<String, String>> listaRetorno = new ArrayList<>();

        for (int i = 0; i < listaProdutos.size(); i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("api/id", String.valueOf(listaProdutos.get(i).codigo));
            item.put("api/produto", listaProdutos.get(i).nome);
            item.put("api/foto", listaProdutos.get(i).logo);

            listaRetorno.add(item);
        }

        return listaRetorno;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //criando o caminho para abrir a segunda tela
        Intent telaDetalhe = new Intent(
                MainActivity.this, DetalhesActivity.class);

        //criando a classe responsavel pelos parametros
        Bundle parans = new Bundle();

        //adicionando os dados do item selecionado no bundle de parametros
        parans.putString("produto", listaProdutos.get(position).nome);
        parans.putString("foto", listaProdutos.get(position).logo);
        parans.putInt("id", listaProdutos.get(position).codigo);

        //adicionando os parametros criados no caminho da tela
        telaDetalhe.putExtras(parans);

        //mandando abrir a tela de detalhes
        startActivity(telaDetalhe);
    }
}