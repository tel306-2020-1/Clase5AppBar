package com.example.clase5appbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActionMode actionMode;

    private static int tema = R.style.AppTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(tema);

        setContentView(R.layout.activity_main);

        registerForContextMenu(findViewById(R.id.texto));

    }

    public void test(View view){
        tema = R.style.AppTheme2;
        this.recreate();
    }

    public void abrirDialog(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("mi alerta!!!");
        alertDialog.setMessage("este es el cuerpo del alert dialog!!!! :D");
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("dialogMsg", "Boton positivo presionado");
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("dialogMsg", "Boton negativo presionado");
            }
        });
        alertDialog.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("dialogMsg", "Boton neutral presionado");
            }
        });
        alertDialog.show();
    }

    public void abrirDialogoConLista(View view) {
        final String[] listaTextos = {"texto1", "texto2", "texto3"};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("mi alerta con lista!!!");
        alertDialog.setItems(listaTextos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("dialogMsg", listaTextos[i]);
            }
        });
        alertDialog.show();
    }


    public void cambiarActionBar(View view) {
        //if (actionMode == null) {
        actionMode = this.startActionMode(new MiContextualActionBar());
        //}else{
        //    actionMode.finish();
        //    actionMode = null;
        //}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAbrirNav:
                Log.d("accMenu", "Menu abrir en el navegador");
                break;
            case R.id.menuShare:
                Log.d("accMenu", "Menu compartir");
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public void agregarMenuBtn(MenuItem item) {
        Toast.makeText(this, "botón Agregar presionado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAndroid:
                Toast.makeText(this, "botón Android presionado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuBorrar:
                Toast.makeText(this, "botón Borrar presionado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuPersona:
                Log.d("accMenu", "menu Persona");
                View view = findViewById(R.id.menuPersona);
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_alarma:
                                Toast.makeText(MainActivity.this, "Menu Alarma", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu_settings:
                                Log.d("accMenu", "menu settings presionado");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    class MiContextualActionBar implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_avion:
                    Log.d("accMenu", "clic en el boton de avión");
                    View view = findViewById(R.id.menu_avion);
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_alarma:
                                    Toast.makeText(MainActivity.this, "Menu Alarma", Toast.LENGTH_SHORT).show();
                                    actionMode.finish();
                                    return true;
                                case R.id.menu_settings:
                                    Log.d("accMenu", "menu settings presionado");
                                    actionMode.finish();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();

                    return true;
                case R.id.menuLlamada:
                    Log.d("accMenu", "clic en el boton de llamada");
                    MainActivity.this.actionMode.finish();
                    return true;
                default:
                    return false;
            }


        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            // actionMode = null;
        }
    }


}