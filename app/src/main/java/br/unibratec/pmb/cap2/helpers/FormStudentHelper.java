package br.unibratec.pmb.cap2.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;

import br.unibratec.pmb.cap2.FormStudentActivity;
import br.unibratec.pmb.cap2.R;
import br.unibratec.pmb.cap2.model.Student;

/**
 * Created by thiago.sousa on 01/02/2017.
 */

public class FormStudentHelper {

    private ImageView photoField;
    private EditText nameField;
    private EditText phoneField;
    private EditText addressField;
    private EditText emailField;
    private EditText ageField;
    private RatingBar rateField;

    public FormStudentHelper(FormStudentActivity activity) {
        photoField = (ImageView) activity.findViewById(R.id.iv_student_photo);
        nameField = (EditText) activity.findViewById(R.id.et_student_name);
        phoneField = (EditText) activity.findViewById(R.id.et_student_phone);
        addressField = (EditText) activity.findViewById(R.id.et_student_address);
        emailField = (EditText) activity.findViewById(R.id.et_student_email);
        ageField = (EditText) activity.findViewById(R.id.et_student_age);
        rateField = (RatingBar) activity.findViewById(R.id.rb_student_rate);
    }

    public Student getStudent() {
        Student student = new Student();
        student.setPhoto((String) photoField.getTag());
        student.setName(nameField.getText().toString());
        student.setPhone(phoneField.getText().toString());
        student.setAddress(addressField.getText().toString());
        student.setEmail(emailField.getText().toString());
        student.setAge(Integer.valueOf(ageField.getText().toString()));
        student.setRate(Float.valueOf(rateField.getProgress()));
        return student;
    }

    public void loadImage(String photoPath) {
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            photoField.setImageBitmap(bitmapScaled);
            photoField.setScaleType(ImageView.ScaleType.FIT_XY);
            photoField.setTag(photoPath);
        }
    }


    public void fillForm(Student student) {
        nameField.setText(student.getName());
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());
        addressField.setText(student.getAddress());
        ageField.setText(String.valueOf(student.getAge()));
        rateField.setProgress(student.getRate().intValue());
        loadImage(student.getPhoto());
    }
}
