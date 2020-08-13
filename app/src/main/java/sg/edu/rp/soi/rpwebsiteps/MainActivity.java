package sg.edu.rp.soi.rpwebsiteps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCat, spnSubCat;
    Button btnGo;
    ArrayAdapter<String> aaSubCat;
    ArrayList<String> alSubCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnCat = findViewById(R.id.spinner1);
        spnSubCat = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.btnGo);
        alSubCat = new ArrayList<>();
        aaSubCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alSubCat);
        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        alSubCat.clear();
                        String[] RPthings = getResources().getStringArray(R.array.RP_sub_cat_array);
                        alSubCat.addAll(Arrays.asList(RPthings));
                        spnSubCat.setAdapter(aaSubCat);
                        break;
                    case 1:
                        alSubCat.clear();
                        String[] SOIthings = getResources().getStringArray(R.array.SOI_sub_cat_array);
                        alSubCat.addAll(Arrays.asList(SOIthings));
                        spnSubCat.setAdapter(aaSubCat);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                String[][] sites = {
                        {
                                "https://www.rp.edu.sg/",
                                "https://www.rp.edu.sg/student-life",
                        },
                        {
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                        }
                };
                String url = sites[spnCat.getSelectedItemPosition()][spnSubCat.getSelectedItemPosition()];
                intent.putExtra("website", url);
                int cate = spnCat.getSelectedItemPosition();
                int subCate = spnSubCat.getSelectedItemPosition();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putInt("cate", cate);
                prefEdit.putInt("subCate", subCate);
                prefEdit.commit();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selCat = prefs.getInt("cate", 0);
        int selSubCat = prefs.getInt("subCate", 0);
        spnCat.setSelection(selCat);
        spnSubCat.setSelection(selSubCat);
        Toast.makeText(this, "" + selSubCat, Toast.LENGTH_SHORT).show();
    }
}

