package org.loader.labelview;

import java.util.List;

import com.example.accountantmoney.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	public Context context;
	public List<NewsItem> lists;

	public NewsAdapter(Context context, List<NewsItem> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public NewsItem getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_list_zixun, parent, false);
			holder = new ViewHolder();
			holder.pic = (ImageView) convertView.findViewById(R.id.id_iv_news_icon);
			holder.title = (TextView) convertView.findViewById(R.id.id_tv_news_title);
			convertView.setTag(holder); // ½«Holder´æ´¢µ½convertViewÖÐ
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.pic.setBackgroundResource(lists.get(position).getIcon());
		holder.title.setText(lists.get(position).getTitle());
		return convertView;
	}

	static class ViewHolder {
		ImageView pic;
		TextView title;
	}

}
