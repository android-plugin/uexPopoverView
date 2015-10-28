package org.zywx.wbpalmstar.plugin.uexpopoverview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class EUExPopoverView extends EUExBase implements Serializable {

	private LayoutInflater inflater;

	private ListView lv;
	private String bgColor = null;
	private String clickColor = null;
	private String dividerColor = null;
	private String textColor = null;
	private DataAdapter adapter;
	ArrayList<DataSource> data;
	private Map<String, EPopoverViewBaseView> map_view;
	private Map<String, ArrayList<DataSource>> map_data;
	
	public EUExPopoverView(Context context, EBrowserView eBrowserView) {
		super(context, eBrowserView);
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		map_view = new HashMap<String, EPopoverViewBaseView>();
		map_data = new HashMap<String, ArrayList<DataSource>>();
	}
	
	public void setPopoverData(String[] params) {
		sendMessageWithType(EPopoverViewUtils.POPOVERVIEW_MSG_CODE_SETDATA, params);
	}
	
	public void showPopover(String[] params) {
		sendMessageWithType(EPopoverViewUtils.POPOVERVIEW_MSG_CODE_SHOW, params);
	}
	
	public void hiddenPopover(String[] params) {
		sendMessageWithType(EPopoverViewUtils.POPOVERVIEW_MSG_CODE_DISMISS, params);
	}
	
	public void close(String[] params) {
		sendMessageWithType(EPopoverViewUtils.POPOVERVIEW_MSG_CODE_CLOSE, params);
	}
	
	private void sendMessageWithType(int msgType, String[] params) {
		if(mHandler == null) {
			return;
		}
		Message msg = Message.obtain();
		msg.what = msgType;
		msg.obj = this;
		Bundle bundle = new Bundle();
		bundle.putStringArray(EPopoverViewUtils.POPOVERVIEW_KEY_CODE_FUNCTION, params);
		msg.setData(bundle);
		mHandler.sendMessage(msg);
	}
	
	
	@Override
	public void onHandleMessage(Message msg) {
		if(msg.what == EPopoverViewUtils.POPOVERVIEW_MSG_CODE_SETDATA) {
			handleSetPopoverData(msg);
		}else if(msg.what == EPopoverViewUtils.POPOVERVIEW_MSG_CODE_SHOW) {
			handleShow(msg);
		}else {
			handleMessageInPopover(msg);
		}
	}
	
	private void handleMessageInPopover(Message msg) {
		String[] params = msg.getData().getStringArray(EPopoverViewUtils.POPOVERVIEW_KEY_CODE_FUNCTION);
        if(params != null && params.length == 1){
            String id = params[0];
            if(map_view.containsKey(id)) {
                EPopoverViewBaseView view = map_view.get(id);
                if(view != null) {
                    switch (msg.what) {
                        case EPopoverViewUtils.POPOVERVIEW_MSG_CODE_DISMISS:
                            handleDismiss(id);
                            break;
                        case EPopoverViewUtils.POPOVERVIEW_MSG_CODE_CLOSE:
                            handleClose(id);
                            break;
                    }
                }
            }
        }

	}

	private void handleDismiss(String id) {
        removeViewFromCurrentWindow(map_view.get(id));
        map_view.remove(id);
	}

	private void handleSetPopoverData(Message msg) {
		String[] params = msg.getData().getStringArray(EPopoverViewUtils.POPOVERVIEW_KEY_CODE_FUNCTION);
		if(params != null && params.length == 1) {
			try {
				JSONObject json = new JSONObject(params[0]);
				String id = json.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_ID);
				if(map_data.containsKey(id)) {
					data = map_data.get(id);
					if(data.size() > 0) {
						data.clear();
					}
				}else {
					data = new ArrayList<DataSource>();
				}
				if(json.has(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_BGCOLOR)) {
					bgColor = json.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_BGCOLOR);
				}
				if(json.has(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_CLICKCOLOR)) {
					clickColor = json.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_CLICKCOLOR);
				}
				if(json.has(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_DIVIDERCOLOR)) {
					dividerColor = json.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_DIVIDERCOLOR);
				}
				if(json.has(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_TEXTCOLOR)){
					textColor = json.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_TEXTCOLOR);
				}
				JSONArray array = json.getJSONArray(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_DATA);
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					String icon = obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_ICON);
					String text = obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_TEXT);
					if(icon.contains("http://") || icon.contains("https://")) {
						return;
					}
					data.add(new DataSource(icon, text));
				}
				map_data.put(id, data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void handleClose(String id) {
        removeViewFromCurrentWindow(map_view.get(id));
        map_view.remove(id);
        if(map_data.containsKey(id)) {
            map_data.remove(id);
        }
	}

	private void handleShow(Message msg) {
		String[] params = msg.getData().getStringArray(EPopoverViewUtils.POPOVERVIEW_KEY_CODE_FUNCTION);
		try {
			JSONObject obj = new JSONObject(params[0]);
			final String id = obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_ID);
			final double x = Double.parseDouble(obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_X));
			final double y = Double.parseDouble(obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_Y));
			final double w = Double.parseDouble(obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_W));
			final double h = Double.parseDouble(obj.getString(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_H));
			if(map_view.containsKey(id) || !map_data.containsKey(id)) {
				return;
			}else {
				data = map_data.get(id);
			}
			EPopoverViewBaseView ePopoverViewBaseView = new EPopoverViewBaseView(mContext,
                    this, bgColor, textColor, id, data);
			LayoutParams param = new LayoutParams((int)w, (int)h);
			param.leftMargin = (int)x;
			param.topMargin = (int)y;
			addView2CurrentWindow(ePopoverViewBaseView, param);
			map_view.put(id, ePopoverViewBaseView);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addView2CurrentWindow(View child, RelativeLayout.LayoutParams parms) {
		int l = (int) (parms.leftMargin);
		int t = (int) (parms.topMargin);
		int w = parms.width;
		int h = parms.height;
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
		lp.gravity = Gravity.NO_GRAVITY;
		lp.leftMargin = l;
		lp.topMargin = t;
		adptLayoutParams(parms, lp);
		mBrwView.addViewToCurrentWindow(child, lp);
	}

	public void callBack(String str) {
		onCallback(str);
	}
	
	@Override
	protected boolean clean() {
		return false;
	}

}
