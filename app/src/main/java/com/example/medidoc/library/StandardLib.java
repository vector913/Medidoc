package com.example.medidoc.library;

import android.app.Activity;
import android.content.Intent;

public class StandardLib {
    public static void openActivity(Activity context, Class targetClass)
    {
        Intent to_find_id = new Intent(context, targetClass);
        context.startActivityForResult(to_find_id,0);
    }
}
