package org.zywx.wbpalmstar.plugin.uexpopoverview;

import java.util.ArrayList;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EPopoverViewBaseActivity extends Activity implements OnItemClickListener {
	static final String ONITEMCLICK = "uexPopoverView.onItemClick";
	static final String SCRIPT_HEADER = "javascript:";
	private View view;
	private ListView lv;
	private DataAdapter adapter;
	
	private String bgColor = null;
	private String textColor = null;
	//private String clickColor = null;
	private EUExPopoverView euexPopoverView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		euexPopoverView = (EUExPopoverView) intent.getSerializableExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_OBJ);
		String id = intent.getStringExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_ID);
		ArrayList<DataSource> data = intent.getParcelableArrayListExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_DATA);
		bgColor = intent.getStringExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_BGCOLOR);
		textColor = intent.getStringExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_TEXTCOLOR);
		//clickColor = intent.getStringExtra(EPopoverViewUtils.POPOVERVIEW_PARAMS_JSON_KEY_CLICKCOLOR);
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		view = inflater.inflate(EUExUtil.getResLayoutID("plugin_popover_dialog"), null);
		setContentView(view);
		lv = (ListView) view.findViewById(EUExUtil.getResIdID("lv"));
		adapter = new DataAdapter(getApplicationContext(), data, bgColor, textColor);
		lv.setId(Integer.parseInt(id));
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//View item = lv.getChildAt(position - lv.getFirstVisiblePosition());
		//if(clickColor != null) {
//			item.setBackgroundColor(Color.parseColor(clickColor));
//		}
		int lvId = parent.getId();
		euexPopoverView.hiddenPopover(new String[]{lvId+""});
		euexPopoverView.callBack(SCRIPT_HEADER + "if(" + ONITEMCLICK + "){"+ ONITEMCLICK + "("+position+");}");
	}
}
