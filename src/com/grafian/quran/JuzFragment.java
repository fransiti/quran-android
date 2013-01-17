package com.grafian.quran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.grafian.quran.MetaData.Mark;
import com.grafian.quran.MetaData.Sura;

public class JuzFragment extends SherlockListFragment {

	private App app;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		app = (App) getActivity().getApplication();

		setListAdapter(new JuzAdapter());
		getListView().setFastScrollEnabled(true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ViewerActivity.class);
		Mark mark = app.metaData.getJuz(position + 1);
		intent.putExtra(QuranFragment.MODE, Config.MODE_JUZ);
		intent.putExtra(QuranFragment.SURA, mark.sura);
		intent.putExtra(QuranFragment.AYA, mark.aya);
		startActivity(intent);
	}

	class JuzAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return app.loaded ? app.metaData.getJuzCount() : 0;
		}

		@Override
		public Object getItem(int position) {
			return app.metaData.getJuz(position + 1);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.main_juz_row, null);
			}

			Mark mark = (Mark) getItem(position);
			TextView juzNumber = (TextView) convertView.findViewById(R.id.juz_number);
			TextView suraName = (TextView) convertView.findViewById(R.id.sura_name);
			TextView ayaNumber = (TextView) convertView.findViewById(R.id.aya_number);

			Sura sura = app.metaData.getSura(mark.sura);
			juzNumber.setText("" + (position + 1));
			suraName.setText("" + sura.index + ". " + App.getSuraName(sura.index) + " : ");
			ayaNumber.setText("" + mark.aya);

			return convertView;
		}
	}

}
