package com.example.aula05_18_08_2021_apijson;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Funcoes {

    public static void verificaPermissao(Activity activity,
                                            String nomePermissao) {

        //verifica se já nao tem permissao
        if (ContextCompat.checkSelfPermission(activity, nomePermissao) !=
                                        PackageManager.PERMISSION_GRANTED) {

            //verifica se o usuario negou a ultima solicitacao desta permissao
            //  e nao marcou o item "nunca mais mostre esse alerta"
            //if (ActivityCompat.shouldShowRequestPermissionRationale(
            //                                    activity, nomePermissao)) {

                //solicita a permissao ao usuario
                ActivityCompat.requestPermissions(activity,
                                new String[] { nomePermissao }, 0);
            //}
        }

    }

}
