package org.loader.labelview;

import android.os.Environment;

public class Tools { 
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // ÓÐ´æ´¢µÄSDCard
            return true;
        } else {
            return false;
        }
	}
}
