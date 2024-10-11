package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView tvNome, tvSobrenome, tvMatricula;
    private String nomeCompleto = "Felipe";
    private String sobrenomeCompleto = "de Lima Oliveira Alfaya";
    private String matriculaCompleta = "200029380";
    private String mensagem = "PARABÉNS, VOCÊ COMPLETOU!";
    private StringBuilder displayNome, displaySobrenome, displayMatricula;
    private Set<String> letrasClicadas;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvNome = findViewById(R.id.tv_nome);
        tvSobrenome = findViewById(R.id.tv_sobrenome);
        tvMatricula = findViewById(R.id.tv_matricula);

        displayNome = new StringBuilder("*".repeat(nomeCompleto.length()));
        displaySobrenome = new StringBuilder("*".repeat(sobrenomeCompleto.length()));
        displayMatricula = new StringBuilder("*".repeat(matriculaCompleta.length()));
        letrasClicadas = new HashSet<>();

        tvNome.setText(displayNome.toString());
        tvSobrenome.setText(displaySobrenome.toString());
        tvMatricula.setText(displayMatricula.toString());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TableLayout tableLayout = findViewById(R.id.tl_keyboard);

        TextView tvMensagem = findViewById(R.id.tv_mensagem);
        tvMensagem.setVisibility(View.INVISIBLE);



        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            if (tableLayout.getChildAt(i) instanceof TableRow) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View view = tableRow.getChildAt(j);

                    if (view instanceof Button) {
                        Button button = (Button) view;

                        button.setOnClickListener(v -> {
                            if (Color.WHITE == Color.parseColor("#000000")) {
                                Log.d(TAG, "Botão não clicável");
                                return;
                            }
                            String letter = button.getText().toString();
                            Log.d(TAG, "Button clicked: " + letter);
                            Log.d(TAG, "clicados:" + letrasClicadas.size());

                            button.setBackgroundColor(Color.parseColor("#FFA500"));
                            onLetterClicked(letter);
                        });
                    }
                }
            }
        }
    }

    public void onLetterClicked(String letter) {
        letrasClicadas.add(letter);

        updateDisplay(nomeCompleto, displayNome, letter);
        updateDisplay(sobrenomeCompleto, displaySobrenome, letter);
        updateDisplay(matriculaCompleta, displayMatricula, letter);

        tvNome.setText(displayNome.toString());
        tvSobrenome.setText(displaySobrenome.toString());
        tvMatricula.setText(displayMatricula.toString());

        if (letrasClicadas.size() >= 18) {
            mostrarMensagemParabens();
        }
    }

    private void mostrarMensagemParabens() {
        TextView tvMensagem = findViewById(R.id.tv_mensagem);
        tvMensagem.setVisibility(View.VISIBLE);
    }


    private void updateDisplay(String completeString, StringBuilder displayString, String letter) {
        for (int i = 0; i < completeString.length(); i++) {
            if (String.valueOf(completeString.charAt(i)).equalsIgnoreCase(letter)) {
                displayString.setCharAt(i, completeString.charAt(i));
            }
        }
    }
    public void reset(View view) {
        resetarPosicaoDosBotoes();
    }

    private void resetarPosicaoDosBotoes() {
        TableLayout tableLayout = findViewById(R.id.tl_keyboard);

        List<String> letrasOriginais = new ArrayList<>(Arrays.asList("F", "E", "L", "I", "P", "A", "Y", "2", "0", "9", "3", "8", "D", "I", "M", "V", "R"," ", "O"));

        List<Button> buttons = new ArrayList<>();

        displayNome = new StringBuilder("*".repeat(nomeCompleto.length()));
        displaySobrenome = new StringBuilder("*".repeat(sobrenomeCompleto.length()));
        displayMatricula = new StringBuilder("*".repeat(matriculaCompleta.length()));

        tvNome.setText(displayNome.toString());
        tvSobrenome.setText(displaySobrenome.toString());
        tvMatricula.setText(displayMatricula.toString());
        TextView tvMensagem = findViewById(R.id.tv_mensagem);
        tvMensagem.setVisibility(View.INVISIBLE);
        letrasClicadas.clear();


        try {
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                if (tableLayout.getChildAt(i) instanceof TableRow) {
                    TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

                    for (int j = 0; j < tableRow.getChildCount(); j++) {
                        View btnView = tableRow.getChildAt(j);

                        if (btnView instanceof Button) {
                            Button button = (Button) btnView;
                            buttons.add(button);
                        }
                    }
                }
            }

            for (int index = 0; index < buttons.size(); index++) {
                Button button = buttons.get(index);

                if (index < letrasOriginais.size()) {
                    button.setText(letrasOriginais.get(index));
                    button.setBackgroundColor(Color.WHITE);
                    button.setAlpha(1.0f);
                    button.setEnabled(true);
                } else {
                    button.setText("");
                    button.setBackgroundColor(Color.BLACK);
                    button.setAlpha(0.5f);
                    button.setEnabled(false);
                }
            }
        } catch (Exception e) {
            Log.e("ResetError", "Erro ao resetar os botões: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void embaralhar(View view) {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < ((TableLayout) findViewById(R.id.tl_keyboard)).getChildCount(); i++) {
            TableRow row = (TableRow) ((TableLayout) findViewById(R.id.tl_keyboard)).getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                buttons.add(button);
            }
        }

        Collections.shuffle(buttons);

        List<String> letras = new ArrayList<>(Arrays.asList("F", "E", "L", "I", "P", "A", "Y", "2", "0", "9", "3", "8", "D", "I", "M", "V", "R"," ", "O"));
        Collections.shuffle(letras);

        int index = 0;
        for (Button button : buttons) {
            if (index < letras.size()) {
                String letra = letras.get(index);
                button.setText(letra);
                button.setBackgroundColor(Color.WHITE);
                button.setAlpha(1.0f);
                button.setEnabled(true);

                if (letrasClicadas.contains(letra)) {
                    button.setBackgroundColor(Color.parseColor("#FFA500"));
                }
            } else {
                button.setText("");
                button.setBackgroundColor(Color.BLACK);
                button.setAlpha(0.5f);
                button.setEnabled(false);
            }
            index++;
        }
    }
}
