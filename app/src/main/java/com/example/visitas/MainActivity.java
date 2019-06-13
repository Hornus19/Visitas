package com.example.visitas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtcliente, txtop, txtmes, txtcomercial, txtgere;
    private Statement st;
    private ResultSet rs, rs2;
    private Connection con;
    private String baseDatos = "Clientes";
    private ArrayList<String> listado = new ArrayList<>(0);
    private Spinner spnestado, spnmes, spnvisitas;

    private TextView idvisita, txtrzs, txttlf, txtpsc, txtdireccion, direccion, txtfecha, txthora, txtobservaciones, pass1, user1, txtestadovisita, txtestadoventa, fecha, estado, mes, telefono, observaciones, estadovisita, estadoventa, cliente, visita, razonsocial, personacontacto, hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        spnmes = (Spinner) findViewById(R.id.spnmes);
        spnestado = (Spinner) findViewById(R.id.spnestado);
        spnvisitas = (Spinner) findViewById(R.id.spnvisitas);
        idvisita = (TextView) findViewById(R.id.idvisita);
        txtrzs = (TextView) findViewById(R.id.txtrzs);
        txttlf = (TextView) findViewById(R.id.txttlf);
        txtpsc = (TextView) findViewById(R.id.txtpsc);
        txtdireccion = (TextView) findViewById(R.id.txtdireccion);
        txtfecha = (TextView) findViewById(R.id.txtfecha);
        txthora = (TextView) findViewById(R.id.txthora);
        txtobservaciones = (TextView) findViewById(R.id.txtobservaciones);
        txtestadovisita = (TextView) findViewById(R.id.txtestadovisita);
        txtestadoventa = (TextView) findViewById(R.id.txtestadovisita);

        fecha = (TextView) findViewById(R.id.fecha);
        estado = (TextView) findViewById(R.id.estado);
        mes = (TextView) findViewById(R.id.mes);
        telefono = (TextView) findViewById(R.id.telefono);
        observaciones = (TextView) findViewById(R.id.observaciones);
        estadovisita = (TextView) findViewById(R.id.estadovisita);
        estadoventa = (TextView) findViewById(R.id.estadoventa);
        cliente = (TextView) findViewById(R.id.cliente);
        visita = (TextView) findViewById(R.id.visita);
        razonsocial = (TextView) findViewById(R.id.razonsocial);
        personacontacto = (TextView) findViewById(R.id.personacontacto);
        hora = (TextView) findViewById(R.id.hora);
        direccion = (TextView) findViewById(R.id.direccion);
        txtobservaciones.setMovementMethod(new ScrollingMovementMethod());
        final ArrayList<String> listas = new ArrayList<>();
        final String[] userfinal = new String[1];
        final String[] passfinal = new String[1];

        final ArrayList<String> numcli = new ArrayList<>();
        nover();
        try {
            String driver = "com.mysql.jdbc.Driver";
            final String urlMysql = "jdbc:mysql://sencitel.ddns.net/";
            Class.forName(driver).newInstance();

            con = DriverManager.getConnection(urlMysql + baseDatos, "web", "Ludwig1753");
            final Dialog fdDialogue = new Dialog(MainActivity.this, R.style.Theme_AppCompat_DayNight_DarkActionBar);
            fdDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fdDialogue.setContentView(R.layout.login);
            fdDialogue.setCancelable(false);
            fdDialogue.show();
            ((Button) fdDialogue.findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user1 = (EditText) fdDialogue.findViewById(R.id.user);
                    pass1 = (EditText) fdDialogue.findViewById(R.id.pass);

                    userfinal[0] = user1.getText().toString();
                    passfinal[0] = pass1.getText().toString();
                    if (!userfinal[0].equalsIgnoreCase("") && !passfinal[0].equalsIgnoreCase("")) {
                        try {
                            st = con.createStatement();
                            rs = st.executeQuery("Select * from disvis where usuario='" + userfinal[0] + "'");
                            if (rs.first()) {
                                if (rs.getString("pass").equals(passfinal[0])) {
                                    if (passfinal[0].equalsIgnoreCase("Start123.")) {
                                        fdDialogue.dismiss();
                                        final Dialog fdDialogue2 = new Dialog(MainActivity.this, R.style.Theme_AppCompat_DayNight_DarkActionBar);
                                        fdDialogue2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                                        fdDialogue2.setContentView(R.layout.cmpass);
                                        fdDialogue2.setCancelable(false);
                                        fdDialogue2.findViewById(R.id.btnpass).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                EditText pass = (EditText) fdDialogue2.findViewById(R.id.pass2);
                                                EditText rptpass = (EditText) fdDialogue2.findViewById(R.id.pass3);

                                                if (pass.getText().toString().equalsIgnoreCase(rptpass.getText().toString())) {
                                                    try {
                                                        st.executeUpdate("UPDATE disvis set pass='" + pass.getText().toString() + "' where usuario='" + userfinal[0] + "'");
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                    fdDialogue2.dismiss();
                                                    AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                                                    a.setTitle("Contraseña");
                                                    a.setMessage("La contraseña a sido cambiada correctamente");

                                                    a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.cancel();
                                                            fdDialogue.show();
                                                        }
                                                    });
                                                    a.show();


                                                } else {
                                                    AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                                                    a.setTitle("Contraseña");
                                                    a.setMessage("Las Contraseñas no coinciden");

                                                    a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.cancel();
                                                        }
                                                    });
                                                    a.show();
                                                }
                                            }
                                        });
                                        fdDialogue2.show();

                                    } else {
                                        //aqui va codigo de busquedas
                                        rs2 = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                                        rs2.next();
                                        String dis = rs2.getString("nombre");
                                        rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and (estado='ENVIADA' or estado='OK' or estado='KO')");
                                        listas.removeAll(listas);
                                        numcli.removeAll(numcli);
                                        listas.add("-");
                                        numcli.add("-");
                                        rs.first();
                                        do {
                                            listas.add(rs.getString("razonsocial"));
                                            numcli.add(rs.getString("visita"));
                                        } while (rs.next());
                                        ArrayAdapter<String> adaptador = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, listas);
                                        spnvisitas.setAdapter(adaptador);
                                        fdDialogue.dismiss();
                                        ArrayList<String> list3 = new ArrayList<>();
                                        String[] meses = new String[]{"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                                        for (int a = 0; meses.length > a; a++) {
                                            list3.add(meses[a]);
                                        }
                                        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, list3);
                                        spnmes.setAdapter(arrayAdapter3);
                                        ArrayList<String> estados = new ArrayList<>();
                                        String[] estado = new String[]{"Todos", "ENVIADA", "OK", "KO"};
                                        for (int a = 0; estado.length > a; a++) {
                                            estados.add(estado[a]);
                                        }
                                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, estados);
                                        spnestado.setAdapter(arrayAdapter2);
                                        ver();
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spnvisitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (spnvisitas.getSelectedItemPosition() != 0) {
                        rs = st.executeQuery("select * from visitas where visita='" + numcli.get(spnvisitas.getSelectedItemPosition()) + "'");
                        if (rs.next()) {
                            idvisita.setText(numcli.get(spnvisitas.getSelectedItemPosition()));
                            txtrzs.setText(rs.getString("razonsocial"));
                            txttlf.setText(rs.getString("telefono"));
                            txtpsc.setText(rs.getString("persona"));
                            txtdireccion.setText(rs.getString("direccion"));
                            txtfecha.setText(rs.getString("fecha"));
                            txthora.setText(rs.getString("hora"));
                            txtobservaciones.setText(rs.getString("observaciones"));
                            txtestadoventa.setText(rs.getString("venta"));
                            txtestadovisita.setText(rs.getString("estado"));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnestado.getSelectedItemPosition() == 0) {
                    if (spnmes.getSelectedItemPosition() == 0) {
                        try {
                            rs2 = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs2.next();
                            String dis = rs2.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and (estado='ENVIADA' or estado='OK' or estado='KO')");
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs.first();
                            do {
                                listas.add(rs.getString("razonsocial"));
                                numcli.add(rs.getString("visita"));
                            } while (rs.next());
                            ArrayAdapter<String> adaptador = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, listas);
                            spnvisitas.setAdapter(adaptador);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String mes = null;
                        if (spnmes.getSelectedItemPosition() < 10) {
                            mes = "0" + spnmes.getSelectedItemPosition();
                        } else {
                            mes = String.valueOf(spnmes.getSelectedItemPosition());
                        }
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and (estado='ENVIADA' or estado='OK' or estado='KO') and MONTH(fecha)='" + mes + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (spnmes.getSelectedItemPosition() == 0) {
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and estado='" + spnestado.getSelectedItem().toString() + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String mes = null;
                        if (spnmes.getSelectedItemPosition() < 10) {
                            mes = "0" + spnmes.getSelectedItemPosition();
                        } else {
                            mes = String.valueOf(spnmes.getSelectedItemPosition());
                        }
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and estado='" + spnestado.getSelectedItem().toString() + "' and MONTH(fecha)='" + mes + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnmes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnestado.getSelectedItemPosition() == 0) {
                    if (spnmes.getSelectedItemPosition() == 0) {
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and (estado='ENVIADA' or estado='OK' or estado='KO')");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String mes = null;
                        if (spnmes.getSelectedItemPosition() < 10) {
                            mes = "0" + spnmes.getSelectedItemPosition();
                        } else {
                            mes = String.valueOf(spnmes.getSelectedItemPosition());
                        }
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and (estado='ENVIADA' or estado='OK' or estado='KO') and MONTH(fecha)='" + mes + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (spnmes.getSelectedItemPosition() == 0) {
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and estado='" + spnestado.getSelectedItem().toString() + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String mes = null;
                        if (spnmes.getSelectedItemPosition() < 10) {
                            mes = "0" + spnmes.getSelectedItemPosition();
                        } else {
                            mes = String.valueOf(spnmes.getSelectedItemPosition());
                        }
                        try {
                            listas.removeAll(listas);
                            numcli.removeAll(numcli);
                            listas.add("-");
                            numcli.add("-");
                            rs = st.executeQuery("select nombre from disvis where usuario='" + userfinal[0] + "' and activo='SI'");
                            rs.next();
                            String dis = rs.getString("nombre");
                            rs = st.executeQuery("select * from visitas where distribuidor='" + dis + "' and estado='" + spnestado.getSelectedItem().toString() + "' and MONTH(fecha)='" + mes + "'");
                            if (!rs.next()) {
                                limpiar();
                            } else {
                                rs.first();
                                do {
                                    listas.add(rs.getString("razonsocial"));
                                    numcli.add(rs.getString("visita"));
                                } while (rs.next());
                                ArrayAdapter adaptador = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, listas);
                                spnvisitas.setAdapter(adaptador);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void nover() {
        spnmes.setVisibility(View.GONE);
        spnestado.setVisibility(View.GONE);
        spnvisitas.setVisibility(View.GONE);
        idvisita.setVisibility(View.GONE);
        txtrzs.setVisibility(View.GONE);
        txttlf.setVisibility(View.GONE);
        txtpsc.setVisibility(View.GONE);
        txtdireccion.setVisibility(View.GONE);
        txtfecha.setVisibility(View.GONE);
        txthora.setVisibility(View.GONE);
        txtobservaciones.setVisibility(View.GONE);
        txtestadovisita.setVisibility(View.GONE);
        txtestadoventa.setVisibility(View.GONE);
        fecha.setVisibility(View.GONE);
        estado.setVisibility(View.GONE);
        mes.setVisibility(View.GONE);
        telefono.setVisibility(View.GONE);
        observaciones.setVisibility(View.GONE);
        estadovisita.setVisibility(View.GONE);
        estadoventa.setVisibility(View.GONE);
        cliente.setVisibility(View.GONE);
        visita.setVisibility(View.GONE);
        razonsocial.setVisibility(View.GONE);
        personacontacto.setVisibility(View.GONE);
        hora.setVisibility(View.GONE);
        direccion.setVisibility(View.GONE);
    }

    public void ver() {
        spnmes.setVisibility(View.VISIBLE);
        spnestado.setVisibility(View.VISIBLE);
        spnvisitas.setVisibility(View.VISIBLE);
        idvisita.setVisibility(View.VISIBLE);
        txtrzs.setVisibility(View.VISIBLE);
        txttlf.setVisibility(View.VISIBLE);
        txtpsc.setVisibility(View.VISIBLE);
        txtdireccion.setVisibility(View.VISIBLE);
        txtfecha.setVisibility(View.VISIBLE);
        txthora.setVisibility(View.VISIBLE);
        txtobservaciones.setVisibility(View.VISIBLE);
        txtestadovisita.setVisibility(View.VISIBLE);
        txtestadoventa.setVisibility(View.VISIBLE);
        fecha.setVisibility(View.VISIBLE);
        estado.setVisibility(View.VISIBLE);
        mes.setVisibility(View.VISIBLE);
        telefono.setVisibility(View.VISIBLE);
        observaciones.setVisibility(View.VISIBLE);
        estadovisita.setVisibility(View.VISIBLE);
        estadoventa.setVisibility(View.VISIBLE);
        cliente.setVisibility(View.VISIBLE);
        visita.setVisibility(View.VISIBLE);
        razonsocial.setVisibility(View.VISIBLE);
        personacontacto.setVisibility(View.VISIBLE);
        hora.setVisibility(View.VISIBLE);
        direccion.setVisibility(View.VISIBLE);
    }

    public void limpiar() {
        spnmes.setSelection(0);
        spnestado.setSelection(0);
        idvisita.setText("");
        txtrzs.setText("");
        txttlf.setText("");
        txtpsc.setText("");
        txtdireccion.setText("");
        txtfecha.setText("");
        txthora.setText("");
        txtobservaciones.setText("");
        txtestadovisita.setText("");
        txtestadoventa.setText("");
    }

}


