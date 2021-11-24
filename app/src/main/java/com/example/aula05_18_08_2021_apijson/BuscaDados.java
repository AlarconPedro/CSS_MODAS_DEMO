package com.example.aula05_18_08_2021_apijson;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class BuscaDados extends AsyncTask<String, Void, ArrayList<Produtos>> {

    @Override
    protected ArrayList<Produtos> doInBackground(String... parametros) {
        ArrayList<Produtos> listaRetorno = new ArrayList<>();

        //pegando o link da API que veio por parametro
        String link = parametros[0];

        try {
            //criando uma URL valida a partir do link
            URL url = new URL(link);

            //criando a conexao com a URL
            URLConnection connection = url.openConnection();

            //cria um espaço na memoria para salvar os dados da web (API)
            InputStreamReader inputStreamReader =
                    new InputStreamReader(connection.getInputStream());

            //classe que permite manipulador os dados salvos na memoria
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                //criando um array de objetos JSON com o retorno da web
                JSONArray ja = new JSONArray(linha);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    //criando a emissora
                    Produtos produtos = new Produtos();

                    //pegando os valores do JSON e salvando nos campos da classe
                    produtos.codigo = jo.getInt("id");
                    produtos.nome = jo.getString("produto");
                    produtos.logo = jo.getString("foto");

                    produtos.logo = produtos.logo.replace("~/",
                            "http://cssmodas.herokuapp.com/api/produtos/data/");

                    //adiconando a emissora na lista de retorno
                    listaRetorno.add(produtos);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listaRetorno;
    }
}