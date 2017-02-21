package br.unibratec.pmb.cap2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.List;

import br.unibratec.pmb.cap2.dao.StudentDAO;
import br.unibratec.pmb.cap2.model.Student;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private ListView lvAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadStudentList();

        registerForContextMenu(lvAlunos);

        lvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, FormStudentActivity.class);
                it.putExtra("student", students.get(position));
                startActivity(it);
            }
        });

        Button btNewStudent = (Button)findViewById(R.id.bt_new_student);
        btNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FormStudentActivity.class );
                startActivity(it);
            }
        });
    }

    private void loadStudentList() {
        StudentDAO dao = new StudentDAO(this);
        students = dao.getStudents();
        dao.close();
        lvAlunos = (ListView) findViewById(R.id.lv_students);
        ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, students);
        lvAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Student student = (Student) lvAlunos.getItemAtPosition(info.position);

        MenuItem itemCall = menu.add("Ligar");
        itemCall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    intentCall.setData(Uri.parse("tel:" + student.getPhone()));
                    startActivity(intentCall);
                }
                return false;
            }
        });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + student.getPhone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + student.getAddress()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = student.getEmail();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem delete = menu.add("Deletar");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                StudentDAO dao = new StudentDAO(MainActivity.this);
                dao.delete(student);
                dao.close();

                loadStudentList();
                return false;
            }
        });
    }

}
