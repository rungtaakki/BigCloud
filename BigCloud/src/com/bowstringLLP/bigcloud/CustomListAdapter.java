package com.bowstringLLP.bigcloud;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

	public class CustomListAdapter extends ArrayAdapter<ListItem>{

		Activity context;
		List<ListItem> rec;

		public CustomListAdapter(Activity context) {
			super(context, R.layout.list_item_layout);
			this.context=context;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row = convertView;
			ViewWrapper wrapper=null;
			
			if (row==null) {
				LayoutInflater inflater=context.getLayoutInflater();

				row = inflater.inflate(R.layout.list_item_layout, null);
				wrapper=new ViewWrapper(row);
				row.setTag(wrapper);
			}
			else {
				wrapper=(ViewWrapper)row.getTag();
			}
			
			if(rec.get(position).title !=null)
			{
				wrapper.getName().setText(rec.get(position).title);
			}
			else
				wrapper.getName().setText("Unknown");
			

			if(rec.get(position).bitmap !=null)
			{
				wrapper.getThumb().setImageBitmap(rec.get(position).bitmap);
			}
			else
				wrapper.getName().setText("Unknown");
			
			return row;
		}

		public void setContent(List<ListItem> list) {
			rec = list;
		}
		
		@Override
		public int getCount()
		{
			if(rec == null)
				return 0;
			else
				return rec.size();
		}
}
