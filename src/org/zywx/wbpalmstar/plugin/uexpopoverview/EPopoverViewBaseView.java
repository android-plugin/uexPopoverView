package org.zywx.wbpalmstar.plugin.uexpopoverview;

import java.util.ArrayList;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

public class EPopoverViewBaseView extends FrameLayout implements OnItemClickListener {
	static final String ONITEMCLICK = "uexPopoverView.onItemClick";
	static final String SCRIPT_HEADER = "javascript:";
	private View view;
	private ListView lv;
	private DataAdapter adapter;
	
	private String bgColor = null;
	private String textColor = null;
	//private String clickColor = null;
	private EUExPopoverView euexPopoverView;
    private Context mContext;

    public EPopoverViewBaseView(Context context, EUExPopoverView base,
                                String bgColor, String textColor, String id,
                                ArrayList<DataSource> data) {
        super(context);
        this.mContext = context;
        this.euexPopoverView = base;
        this.bgColor = bgColor;
        this.textColor = textColor;
        initView(id, data);
    }

	private void initView(String id, ArrayList<DataSource> data) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(EUExUtil.getResLayoutID("plugin_popover_dialog"), null);
		addView(view);
		lv = (ListView) view.findViewById(EUExUtil.getResIdID("lv"));
		adapter = new DataAdapter(mContext, data, bgColor, textColor);
		lv.setId(Integer.parseInt(id));
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
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
