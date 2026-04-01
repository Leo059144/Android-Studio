package com.example.applimenu;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListeEchantillon extends Activity {

    private ListView listViewEchant;
    private BdAdapter echantBdd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_echantillon);
        listViewEchant = (ListView) findViewById(R.id.listeListViewEchantillon);

        echantBdd = new BdAdapter(this);
        //On ouvre la base de données pour écrire dedans
        echantBdd.open();
        Cursor leCurseur = echantBdd.getData();
        // colonnes à afficher
        String[] colNoms = new String[] {BdAdapter.COL_CODE, BdAdapter.COL_LIB,
                BdAdapter.COL_STOCK};
        // champs dans lesquelles afficher les colonnes
        int[] colNumeros = new int[] {R.id.listeTextViewCode, R.id.listeTextViewLib, R.id.listeTextViewStock};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.list_entree,
                leCurseur,colNoms,colNumeros);
        // Assign adapter to ListView
        listViewEchant.setAdapter(dataAdapter);
        echantBdd.close();


        Button buttonQuitter = (Button)findViewById(R.id.listeButtonQuitter);
        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //fermeture de la fenêtre
            }
        });
    }

}
