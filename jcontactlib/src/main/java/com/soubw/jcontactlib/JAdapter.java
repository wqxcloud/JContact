package com.soubw.jcontactlib;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by WX_JIN on 2016/3/10.
 */
public class JAdapter extends BaseAdapter{

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SECTION = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SECTION + 1;

	LayoutInflater mLayoutInflater;

	ArrayList<Integer> mListSectionPos;

	ArrayList<JContacts> mListItems;

	Context mContext;

	public JAdapter(Context context, ArrayList<JContacts> listItems, ArrayList<Integer> listSectionPos) {
		this.mContext = context;
		this.mListItems = listItems;
		this.mListSectionPos = listSectionPos;

		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mListItems.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return !mListSectionPos.contains(position);
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		return mListSectionPos.contains(position) ? TYPE_SECTION : TYPE_ITEM;
	}

	@Override
	public Object getItem(int position) {
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mListItems.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case TYPE_ITEM:
				convertView = mLayoutInflater.inflate(R.layout.jcontact_row_view, null);
				break;
			case TYPE_SECTION:
				convertView = mLayoutInflater.inflate(R.layout.jcontact_section_row_view, null);
				break;
			}
			holder.textView = (TextView) convertView.findViewById(R.id.row_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (type) {
			case TYPE_ITEM:
				holder.textView.setText(mListItems.get(position).getjName()+mListItems.get(position).getjPhoneNumber());
				break;
			case TYPE_SECTION:
				holder.textView.setText(mListItems.get(position).getjName());
				break;
		}
		return convertView;
	}

	public void setUpdateDate(ArrayList<JContacts> listItems, ArrayList<Integer> listSectionPos){
		this.mListItems = listItems;
		this.mListSectionPos = listSectionPos;
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView textView;
	}
}