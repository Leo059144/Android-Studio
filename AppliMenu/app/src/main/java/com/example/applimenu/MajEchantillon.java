package com.example.applimenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MajEchantillon extends Activity {

    private EditText editCode;
    private EditText editQte;
    private TextView textMessage;
    private Button buttonSupprimer;
    private Button buttonAjouter;
    private Button buttonQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maj_echantillon);

        editCode = findViewById(R.id.majEditTextCode);
        editQte = findViewById(R.id.majEditTextQte);
        textMessage = findViewById(R.id.majTextViewMessage);
        buttonSupprimer = findViewById(R.id.majButtonSupprimer);
        buttonAjouter = findViewById(R.id.majButtonAjouter);
        buttonQuitter = findViewById(R.id.majButtonQuitterListe);

        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                majStock(true);
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                majStock(false);
            }
        });
    }

    private void majStock(boolean ajouter) {
        String code = editCode.getText().toString().trim();
        String qteStr = editQte.getText().toString().trim();

        if (code.isEmpty() || qteStr.isEmpty()) {
            textMessage.setText("Veuillez remplir tous les champs.");
            return;
        }

        int qte;
        try {
            qte = Integer.parseInt(qteStr);
        } catch (NumberFormatException e) {
            textMessage.setText("La quantité doit être un nombre.");
            return;
        }

        BdAdapter echantBdd = new BdAdapter(this);
        echantBdd.open();
        Echantillon unEchant = echantBdd.getEchantillonWithLib(code);

        if (unEchant == null) {
            textMessage.setText("Echantillon introuvable pour le code : " + code);
            echantBdd.close();
            return;
        }

        int stockActuel = Integer.parseInt(unEchant.getQuantiteStock());
        int nouveauStock;

        if (ajouter) {
            nouveauStock = stockActuel + qte;
            unEchant.setQuantiteStock(String.valueOf(nouveauStock));
            echantBdd.updateEchantillon(code, unEchant);
            textMessage.setText("quantité ajoutée");
        } else {
            nouveauStock = stockActuel - qte;
            unEchant.setQuantiteStock(String.valueOf(nouveauStock));
            echantBdd.updateEchantillon(code, unEchant);
            textMessage.setText("quantité supprimée");
        }

        echantBdd.close();
    }
}
