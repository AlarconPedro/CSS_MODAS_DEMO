package com.example.aula05_18_08_2021_apijson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImagem extends AsyncTask<String, Void, Bitmap> {

    ImageView imagem;

    public DownloadImagem(ImageView imagem) {
        this.imagem = imagem;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        String link = strings[0];
        try {
            //criando a URL a partir do link recebido por parametro
            URL url = new URL(link);

            //salvar os dados prevenientes do link
            InputStream inputStream = url.openStream();

            //trasformando os dados da web em uma imagem bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        //capturando o bitmap que foi baixado da web e mostrando na tela
        imagem.setImageBitmap(bitmap);
    }
}
