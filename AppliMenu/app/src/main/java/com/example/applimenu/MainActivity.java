package com.example.applimenu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        jeuEssaiBd();

        Button buttonAjoutEchant = (Button)findViewById(R.id.ajoutButtonAjouter);

        buttonAjoutEchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjout = new Intent(MainActivity.this, AjoutEchantillon.class);
                startActivity(intentAjout);
            }
        });
        Button buttonListe = findViewById(R.id.menuButtonListeEchantillons);
        buttonListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ouvrir la fenêtre ListeEchantillon
                Intent intentListe = new Intent(MainActivity.this, ListeEchantillon.class);
                startActivity(intentListe);
            }
        });

        Button buttonMaj = findViewById(R.id.menuButtonMajEchantillons);
        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ouvrir la fenêtre MajEchantillon (qu'on va créer ensuite)
                Intent intentMaj = new Intent(MainActivity.this, MajEchantillon.class);
                startActivity(intentMaj);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
//dans le cas où on clique sur l’item quittter
        if (itemId == R.id.quitter){
            finish();
            return true;
        }else if(itemId == R.id.ajout){
//dans le cas où on clique sur l’item ajouter
            Intent intentAjout = new Intent(MainActivity.this, AjoutEchantillon.class);
            startActivity(intentAjout);
            Toast.makeText(getApplicationContext(), "ouverture fenêtre Ajout !", Toast.LENGTH_LONG).show();
            return true;
        }else if(itemId == R.id.liste){
//dans le cas où on clique sur l’item liste
            Intent intentListe = new Intent(MainActivity.this, ListeEchantillon.class);
            startActivity(intentListe);
            Toast.makeText(getApplicationContext(), "ouverture fenêtre Liste !", Toast.LENGTH_LONG).show();
            return true;
        }else if(itemId == R.id.maj){
//dans le cas où on clique sur l’item maj
            Intent intentMaj = new Intent(MainActivity.this, MajEchantillon.class);
            startActivity(intentMaj);
            Toast.makeText(getApplicationContext(), "ouverture fenêtre Maj !", Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }



    public void jeuEssaiBd(){
        //Création d'une instance de la classe echantBDD
        BdAdapter echantBdd = new BdAdapter(this);

        //On ouvre la base de données pour écrire dedans
        echantBdd.open();
        //On insère DES ECHANTILLONS DANS LA BD
        echantBdd.insererEchantillon(new Echantillon("code1", "lib1", "3"));
        echantBdd.insererEchantillon(new Echantillon("code2", "lib2", "5"));
        echantBdd.insererEchantillon(new Echantillon("code3", "lib3", "7"));
        echantBdd.insererEchantillon(new Echantillon("code4", "lib4", "6"));
        Cursor unCurseur = echantBdd.getData();
        System.out.println("il y a "+String.valueOf(unCurseur.getCount())+" echantillons dans la BD");
        echantBdd.close();
    }

}