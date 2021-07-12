package com.android.notes_4;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Fragment_one extends Fragment {

    private static final String ARG_INDEX = "index";
    private int index;
    private boolean isLandscape;
    public static final String CURRENT_NOTES = "CurrentNotes";
    private int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    // вызывается после создания макета фрагмента, проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        String[] note = getResources().getStringArray(R.array.notes);

        for(int i=0; i < note.length; i++){
            String notes = note[i];
            TextView tv = new TextView(getContext());
            tv.setText(notes);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int fi = 1;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = fi;
                    showNotes(currentPosition);

                }
            });
        }
    }
    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTES, currentPosition);
        super.onSaveInstanceState(outState);
    }


    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentPosition = savedInstanceState.getInt(CURRENT_NOTES, 0);
        }

        if (isLandscape) {
            showLandNotes(0);
        }
    }

    private void showNotes(int index) {
        if (isLandscape) {
            showLandNotes(index);
        } else {
            showPortNotes(index);
        }
    }

    // Показать в ландшафтной ориентации
    private void showLandNotes(int index) {
        // Создаём новый фрагмент с текущей позицией для вывода
        Fragment_two detail = Fragment_two.newInstance(index);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показать в портретной ориентации.
    private void showPortNotes(int index) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), Fragment_two.class);
        // и передадим туда параметры
        intent.putExtra(Fragment_two.ARG_INDEX, index);
        startActivity(intent);
    }
}