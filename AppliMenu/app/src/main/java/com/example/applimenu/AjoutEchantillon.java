package com.example.applimenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AjoutEchantillon extends Activity {

    private EditText editCode;
    private EditText editLib;
    private EditText editStock;
    private TextView textMessage;
    private Button buttonAjouter;
    private Button buttonQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_echantillon);

        // Récupération des vues par leur id (doivent exister dans ajout_echantillon.xml)
        editCode = findViewById(R.id.ajoutEditTextCode);
        editLib = findViewById(R.id.ajoutEditTextLib);
        editStock = findViewById(R.id.ajoutEditTextStock);
        textMessage = findViewById(R.id.ajoutTextViewMessage);
        buttonAjouter = findViewById(R.id.ajoutButtonAjouter);
        buttonQuitter = findViewById(R.id.ajoutButtonQuitter);

        // Bouton Quitter : ferme la fenêtre
        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // fermeture de la fenêtre
            }
        });

        // Bouton Ajouter : insère dans la base de données
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insererEchantillon();
            }
        });
    }

    private void insererEchantillon() {
        // Récupérer les valeurs saisies
        String code = editCode.getText().toString().trim();
        String lib = editLib.getText().toString().trim();
        String stock = editStock.getText().toString().trim();

        // Vérification rapide
        if (code.isEmpty() || lib.isEmpty() || stock.isEmpty()) {
            textMessage.setText("Veuillez remplir tous les champs.");
            return;
        }

        // Création de l'objet Echantillon
        Echantillon e = new Echantillon(code, lib, stock);

        // Insertion dans la BD via BdAdapter
        BdAdapter echantBdd = new BdAdapter(this);
        echantBdd.open();
        long res = echantBdd.insererEchantillon(e);
        echantBdd.close();

        if (res == -1) {
            textMessage.setText("Erreur lors de l'insertion.");
        } else {
            // Message du type "code, libellé, quantité"
            String msg = code + ", " + lib + ", " + stock;
            textMessage.setText(msg);

            // Optionnel : vider les champs après insertion
            editCode.setText("");
            editLib.setText("");
            editStock.setText("");
        }
    }
}