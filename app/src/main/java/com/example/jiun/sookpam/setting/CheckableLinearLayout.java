package com.example.jiun.sookpam.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.example.jiun.sookpam.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.category_checkbox);
        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.category_checkbox);
        setChecked(checkBox.isChecked() ? false : true);
    }

    @Override
    public void setChecked(boolean isChecked) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.category_checkbox);

        if (checkBox.isChecked() != isChecked) {
            checkBox.setChecked(isChecked);
        }
    }

}
