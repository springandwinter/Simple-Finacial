package org.loader.labelview;

import android.os.Environment;

public class Tools { 
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // �д洢��SDCard
            return true;
        } else {
            return false;
        }
	}
}
