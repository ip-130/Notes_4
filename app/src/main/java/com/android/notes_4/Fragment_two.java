package com.android.notes_4;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Fragment_two extends Fragment {
    private static final String ARG_INDEX = "index";
    private int index;

    TextView tvNoteDate;
    EditText etText;
    EditText etTitle;
    Calendar dateAndTime=Calendar.getInstance();

    public static Fragment_two newInstance(int index) {
        Fragment_two f = new Fragment_two();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);
        setInitialDateTime();
        return view;
    }

    //Метод получение пользовательских элементов по идентификатору
    public void initView(View view){
        etText = (EditText)view.findViewById(R.id.etText);
        tvNoteDate = (TextView)view.findViewById(R.id.tvNoteDate);
        etTitle = (EditText)view.findViewById(R.id.etTitle);
    }

    private void setInitialDateTime() {
        tvNoteDate.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }
}
