package com.mreoz;

import android.content.Context;
import android.content.Intent;

public class MRZStart {
    public static void LoadMe(Context ctx){
        try{
            ctx.startActivity(new Intent(ctx, Class.forName("com.mreoz.MRZPHP")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
