package org.zywx.wbpalmstar.plugin.uexpopoverview;

import java.io.IOException;
import java.io.InputStream;

import org.zywx.wbpalmstar.base.BUtility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EPopoverViewUtils {
	public final static String POPOVERVIEW_KEY_CODE_ACTIVITYID = "activityId";
	public final static String POPOVERVIEW_KEY_CODE_FUNCTION = "function";
	
	public static final int POPOVERVIEW_MSG_CODE_CLOSE = 0;
	public static final int POPOVERVIEW_MSG_CODE_SETDATA = 1;
	public static final int POPOVERVIEW_MSG_CODE_SHOW = 2;
	public static final int POPOVERVIEW_MSG_CODE_DISMISS = 3;
	
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_ID = "id";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_X = "x";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_Y = "y";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_W = "w";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_H = "h";
	
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_OBJ = "obj";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_BGCOLOR = "bgColor";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_CLICKCOLOR = "clickColor";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_DIVIDERCOLOR = "dividerColor";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_TEXTCOLOR = "textColor";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_DATA = "data";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_ICON = "icon";
	public final static String POPOVERVIEW_PARAMS_JSON_KEY_TEXT = "text";
	
	
	
	public static Bitmap getImage(Context ctx, String imgUrl) {
		if (imgUrl == null || imgUrl.length() == 0) {
			return null;
		}
		Bitmap bitmap = null;
		InputStream is = null;
		try {
			if (imgUrl.startsWith(BUtility.F_Widget_RES_SCHEMA)) {
				is = BUtility.getInputStreamByResPath(ctx, imgUrl);
				bitmap = BitmapFactory.decodeStream(is);
			} else if (imgUrl.startsWith(BUtility.F_FILE_SCHEMA)) {
				imgUrl = imgUrl.replace(BUtility.F_FILE_SCHEMA, "");
				bitmap = BitmapFactory.decodeFile(imgUrl);
			} else if (imgUrl.startsWith(BUtility.F_Widget_RES_path)) {
				try {
					is = ctx.getAssets().open(imgUrl);
					if (is != null) {
						bitmap = BitmapFactory.decodeStream(is);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				bitmap = BitmapFactory.decodeFile(imgUrl);
			}

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}
}
