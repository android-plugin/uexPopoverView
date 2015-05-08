package org.zywx.wbpalmstar.plugin.uexpopoverview;

import java.util.List;

import org.zywx.wbpalmstar.base.BUtility;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter {

	private Context context;
	private List<DataSource> data;
	private String bgColor;
	private String desColor;
	public DataAdapter(Context context, List<DataSource> data, String bgColor, String desColor) {
		this.context = context;
		this.data = data;
		this.bgColor = bgColor;
		this.desColor = desColor;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataSource dt = data.get(position);
		View view = null;
		Holder holder = null;
		if(convertView == null) {
			view = View.inflate(context, EUExUtil.getResLayoutID("plugin_popover_dialog_item"), null);
			holder = new Holder();
			holder.iv = (ImageView) view.findViewById(EUExUtil.getResIdID("plugin_item_iv"));
			holder.tv = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_item_tv"));
			view.setTag(holder);
		}else {
			view = convertView;
			holder = (Holder) view.getTag();
		}
		holder.iv.setImageBitmap(EPopoverViewUtils.getImage(context, dt.getIcon()));
		holder.tv.setText(dt.getDes());
		if(desColor != null) {
			holder.tv.setTextColor(BUtility.parseColor(desColor));
		}
		if(bgColor != null) {
			view.setBackgroundColor(Color.parseColor(bgColor));
		}
		return view;
	}
	
	private class Holder {
		public ImageView iv;
		public TextView tv;
	}

}
