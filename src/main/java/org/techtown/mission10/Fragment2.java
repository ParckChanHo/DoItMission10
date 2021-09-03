package org.techtown.mission10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

public class Fragment2 extends Fragment {
	// 추천 채널이다.!!!
	ViewPager viewPager;
	ImageviewPagerAdapter pagerAdapter;
	CircleIndicator indicator;
	RecyclerView recyclerView;
	ArrayList<Music> list;
	Cursor cursor;
	SQLiteDatabase db;
	MusicDB helper;

	Context c;
	MusicAdapter musicAdapter;
	FloatingActionButton btnAdd;

	View dialogView;
	EditText dlgMusicTitle,dlgMusicUrl;


	public Fragment2() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

		c = getContext();
		//final String[] images = getResources().getStringArray(R.array.music_array);
		recyclerView = (RecyclerView)v.findViewById(R.id.RecyclerView);
		btnAdd = (FloatingActionButton)v.findViewById(R.id.btnAdd);

		viewPager = (ViewPager) v.findViewById(R.id.pager);
		pagerAdapter = new ImageviewPagerAdapter(getContext());
		viewPager.setAdapter(pagerAdapter);
		indicator = v.findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);

		helper = new MusicDB(getContext());
		db = helper.getReadableDatabase();
		// db 테이블에서 레코드 읽기
		cursor = db.rawQuery("select title,url,_id from music order by _id desc;",null);

		list = new ArrayList<>();
		while(cursor.moveToNext()){
			String title = cursor.getString(0);
			String url = cursor.getString(1);
			String _id = cursor.getString(2); // 1 2 3 4 5

			list.add(new Music(title,url,_id));
		}


		//list.add(new Music("인연",images[0],"https://www.youtube.com/watch?v=kUJv343NHsU&ab_channel=KENKAMIKITAKENKAMIKITA"));
		//list.add(new Music("스물다섯 스물하나",images[1],"https://www.youtube.com/watch?v=qvJ1FHRR1n8&ab_channel=KENKAMIKITAKENKAMIKITA"));
		//list.add(new Music("안드로이드",images[2],"https://www.youtube.com/watch?v=y7mfblGo_gw&list=RDMMNOr0l9n5dSE&index=8&ab_channel=XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84"));


		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		musicAdapter = new MusicAdapter(list,c);
		recyclerView.setAdapter(musicAdapter);

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogView = (View)View.inflate(c,R.layout.usermusic_item,null);
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("새로운 음악");
				builder.setMessage("새로운 음악을 입력해주세요");
				builder.setView(dialogView);
				builder.setPositiveButton("예",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dlgMusicTitle = (EditText)dialogView.findViewById(R.id.music_title);
								dlgMusicUrl = (EditText)dialogView.findViewById(R.id.music_url);

								String music_title = dlgMusicTitle.getText().toString();
								String music_url = dlgMusicUrl.getText().toString();

								String sql = "insert into music(title,url) values('"
										+ music_title + "', '" + music_url + "')";
								db.execSQL(sql);

								reOncreate();
								musicAdapter.notifyDataSetChanged();
								//Toast.makeText(getContext(),music_title+music_url,Toast.LENGTH_LONG).show();
							}
						});
				builder.setNegativeButton("닫기",null);
				builder.show();
			}
		}); //end setOnClickListener();

		musicAdapter.setOnMyTouchListener(new MusicAdapter.OnMyTouchListener() { // 1번째 리스너
			@Override
			public void onTouch(View v, int pos) {
				int position = pos;
				Music item = list.get(position);
				String url = item.getUrl();

				Uri uri = Uri.parse(url);
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					uri = Uri.parse("http://" + url);
				}

				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				c.startActivity(intent);
			}
		});

		musicAdapter.setOnMyTouchListenerTwo(new MusicAdapter.OnMyTouchListenerTwo() { // 수정하기 이미지를 눌렀을 때
			@Override
			public void onTouch(View v, int pos) {
				int position = pos;
				// 전체 내용 보여주기 + 수정하기
				db = helper.getReadableDatabase();
				final String _id = list.get(position).get_id();
				//Toast.makeText(c,"_id 값 : "+_id,Toast.LENGTH_SHORT).show();
				// _id : 0 title : 1 url : 2
				final Cursor cursor=db.rawQuery("select * from music where _id="+_id, null);

				dialogView = (View)View.inflate(c,R.layout.usermusic_item,null);
				AlertDialog.Builder builder = new AlertDialog.Builder(c);
				builder.setTitle("음악 수정하기");
				builder.setMessage("수정할 음악을 입력해주세요");
				dlgMusicTitle = (EditText)dialogView.findViewById(R.id.music_title);
				dlgMusicUrl = (EditText)dialogView.findViewById(R.id.music_url);

				if(cursor.moveToNext()){
					dlgMusicTitle.setText(cursor.getString(1));
					dlgMusicUrl.setText(cursor.getString(2));
				}
				builder.setView(dialogView);
				builder.setPositiveButton("예",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Toast.makeText(c,"cursor 값 : "+cursor.getString(1)+cursor.getString(2),Toast.LENGTH_SHORT).show();
								db=helper.getWritableDatabase();
								String music_title = dlgMusicTitle.getText().toString();
								String music_url = dlgMusicUrl.getText().toString();

								String titleSql="update music set title='" + music_title + "'" +
										"where _id=" +_id;
								String urlSql="update music set url='" + music_url + "'" +
										"where _id=" +_id;

								db.execSQL(titleSql);
								db.execSQL(urlSql);
								reOncreate();
								//Toast.makeText(getContext(),music_title+music_url,Toast.LENGTH_LONG).show();
							}
						});
				builder.setNegativeButton("닫기",null);
				builder.show();
			}
		});

		musicAdapter.setOnMyTouchListenerThird(new MusicAdapter.OnMyTouchListenerThird() { //삭제하기 이미지를 눌렀을 때이다.
			@Override
			public void onTouch(View v, int pos) {
				// 지우기
				// int position = getAdapterPosition();
				// Toast.makeText(c,list.get(position).getDistrict(),Toast.LENGTH_SHORT).show();
				db = helper.getReadableDatabase();
				int position = pos; // list의 몇 번째 아이템인지를 알게 해준다.
				final String id = list.get(position).get_id();
				androidx.appcompat.app.AlertDialog.Builder box=new androidx.appcompat.app.AlertDialog.Builder(c);
				box.setMessage("삭제하시겠습니까?");
				box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String sql="delete from music where _id="+id;
						db.execSQL(sql);
						reOncreate();
					}

				});
				box.setNegativeButton("닫기",null);
				box.show();
			}
		});

		return v;
	}

	public static int imageReturn(){
		int value1 = R.drawable.music;
		int value2 = R.drawable.music2;
		int value3 = R.drawable.music3;

		Random random = new Random();
		int randomValue = random.nextInt(3); //0~2 까지의 숫자를 랜덤으로 뽑아준다.
		if(randomValue==0)
			return value1;
		else if(randomValue==1)
			return value2;

		return value3;
	}




	public void reOncreate(){
		db = helper.getReadableDatabase();
		// db 테이블에서 레코드 읽기
		cursor = db.rawQuery("select title,url,_id from music order by _id desc;",null);
		list = new ArrayList<>();

		while(cursor.moveToNext()){
			String title = cursor.getString(0);
			String url = cursor.getString(1);
			String _id = cursor.getString(2); // 1 2 3 4 5

			list.add(new Music(title,url,_id));
		}

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		musicAdapter = new MusicAdapter(list,c);
		recyclerView.setAdapter(musicAdapter);

		((MainActivity)getActivity()).callFragment2();
	}
}
