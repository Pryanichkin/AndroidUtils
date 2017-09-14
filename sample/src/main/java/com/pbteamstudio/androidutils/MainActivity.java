package com.pbteamstudio.androidutils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.pbteamstudio.androidhelpers.music.MusicListFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(MusicListFragment.class, false, null);
    }

    // Служебный метод запуска нужного фрагмента
    private void setFragment(Class fragmentClass, boolean addToBackStack, String tag) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Toast.makeText(this, "Problem with a Fragment", Toast.LENGTH_SHORT).show();
        }
        // Получаем обьект транзакции и начинаем транзакцию фрагмента
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //Включаем анимацию расстворения и проявления фрагмента
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // Меняем существующий фрагмент на новый
        if (tag == null) {
            transaction.replace(R.id.fragment_container, fragment);
        } else {
            transaction.replace(R.id.fragment_container, fragment, tag);
        }
        // Загоняем в стек
        if (addToBackStack) transaction.addToBackStack(null);
        // завершаем\закрепляем транзакцию
        transaction.commit();
    }
}
