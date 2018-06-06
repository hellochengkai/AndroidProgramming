package com.hellochengkai.www.mytoast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chengkai on 18-6-6.
 */

public class MyToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public MyToast(Context context,String content) {
        super(context);
        View view = View.inflate(context,R.layout.my_toast,null);
        setView(view);
        setText(content);
        setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    public void setText(CharSequence s) {
        View view = getView();
        TextView textView = view.findViewById(R.id.text1);
//        textView.setText(s);
        textView = view.findViewById(R.id.text2);
        textView.setText(s);
    }
}
