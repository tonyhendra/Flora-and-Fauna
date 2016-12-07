
package com.tonyhendra.florafauna.ui;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import ibmmobileappbuilder.ui.DrawerActivity;

import ibmmobileappbuilder.actions.StartActivityAction;
import ibmmobileappbuilder.util.Constants;
import com.tonyhendra.florafauna.R;

public class FloraandFaunaMainActivity extends DrawerActivity {


    private final SparseArray<Class<? extends Fragment>> sectionFragments = new SparseArray<>();
    {
            sectionFragments.append(R.id.entry0, FloraandFaunaFragment.class);

    }

    @Override
    public SparseArray<Class<? extends Fragment>> getSectionFragmentClasses() {
      return sectionFragments;
    }

}
