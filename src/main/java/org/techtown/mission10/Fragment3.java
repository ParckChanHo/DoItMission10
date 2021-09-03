package org.techtown.mission10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
	// fragment3 ==> 자기 분석 테스트(2번째 탭)
	Button btn_start;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

		btn_start = (Button)v.findViewById(R.id.bt_start);
		btn_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), Test1.class);
				startActivity(intent);
			}
		});

		return v;
	}

	
	
}
