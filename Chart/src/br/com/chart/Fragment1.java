package br.com.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment1 extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		TextView tv = new TextView(getActivity());
		
		tv.setText("Fragment 1 em ação");
		
		return tv;
		
	}

}
