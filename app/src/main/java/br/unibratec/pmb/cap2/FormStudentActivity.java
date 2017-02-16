package br.unibratec.pmb.cap2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.unibratec.pmb.cap2.dao.StudentDAO;
import br.unibratec.pmb.cap2.helpers.FormStudentHelper;
import br.unibratec.pmb.cap2.model.Student;

public class FormStudentActivity extends AppCompatActivity {

    private static final int CAMERA_RESULT_CODE = 27;
    private FormStudentHelper formStudentHelper;
    private Student student;
    private Button btCamera;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_student);
        student = (Student) getIntent().getSerializableExtra("student");
        formStudentHelper = new FormStudentHelper(this);

        if (student != null){
            formStudentHelper.fillForm(student);
        }

        btCamera = (Button) findViewById(R.id.bt_camera);
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoPath = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File photoFile = new File(photoPath );
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(it, CAMERA_RESULT_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_RESULT_CODE) {
                formStudentHelper.loadImage(photoPath);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form_student_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                student = formStudentHelper.getStudent();
                //Criar conexão com o banco de dados
                StudentDAO studentDAO = new StudentDAO(this);
                //inserir o objeto Student
                studentDAO.save(student);
                //fechar conexão com o banco de dados
                studentDAO.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
