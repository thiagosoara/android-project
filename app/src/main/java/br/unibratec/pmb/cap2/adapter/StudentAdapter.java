package br.unibratec.pmb.cap2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import br.unibratec.pmb.cap2.MainActivity;
import br.unibratec.pmb.cap2.R;
import br.unibratec.pmb.cap2.model.Student;

/**
 * Created by thiago.sousa on 21/02/2017.
 */
public class StudentAdapter extends BaseAdapter {
    private final List<Student> students;
    private final Context context;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = this.students.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.students_list_layout, parent, false);

        ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_item_student_photo);
        TextView tvName = (TextView) view.findViewById(R.id.tv_item_student_name);
        TextView tvPhone = (TextView) view.findViewById(R.id.tv_item_student_phone);

        if (student.getPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getPhoto());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            ivPhoto.setImageBitmap(scaledBitmap);
            ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        tvName.setText(student.getName());
        tvPhone.setText(student.getPhone());


        return view;
    }
}
