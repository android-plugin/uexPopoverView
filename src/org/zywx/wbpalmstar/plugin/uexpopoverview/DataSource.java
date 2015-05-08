package org.zywx.wbpalmstar.plugin.uexpopoverview;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSource implements Parcelable {

	private String icon;
	private String des;
	public DataSource(String icon, String des) {
		super();
		this.icon = icon;
		this.des = des;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
}
