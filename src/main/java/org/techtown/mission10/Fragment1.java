package org.techtown.mission10;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pedro.library.AutoPermissions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment1 extends Fragment {
	// 메인 페이지이다.
	TextView user_location,time,temperature,TextViewdescription,TextViewmaxTemp,text_mood;
	ImageView weatherIcon,time1,time2,time3;
	String area_one,area_two;
	static RequestQueue requestQueue;

	DateFormat dateFormat;
	Calendar calendar;
	Date date;
	double latitude,longitude;

	int count = 0;
	int sum = 0;
	boolean t1_flag = false; // 하루에 1번씩만 하게하기 위해서
	boolean t2_flag = false;
	boolean t3_flag = false;

	Cursor cursor;
	SQLiteDatabase db;
	moodDB helper;
	int[] images = new int[]{R.drawable.face1,R.drawable.face2,R.drawable.face3,R.drawable.face5,R.drawable.face6};
	int[] moodScore = new int[]{10,8,6,4,2};
	//boolean newCreate;
	ContentValues values;

	SimpleDateFormat format1;
	Date todayTime;
	String today;
	//myImageView.setImageResource(myImageList[i]);
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

		time1 = (ImageView) v.findViewById(R.id.imageView_morning);
		time2 = (ImageView) v.findViewById(R.id.imageView_daytime);
		time3 = (ImageView) v.findViewById(R.id.imageView_evening);
		weatherIcon = (ImageView) v.findViewById(R.id.weather_img);
		user_location = (TextView) v.findViewById(R.id.user_location);
		time = (TextView) v.findViewById(R.id.time);
		temperature = (TextView) v.findViewById(R.id.temperature);
		TextViewdescription = (TextView) v.findViewById(R.id.description);
		TextViewmaxTemp = (TextView) v.findViewById(R.id.maxTemp);
		text_mood = (TextView)v.findViewById(R.id.text_mood);

		dateFormat = new SimpleDateFormat("MM월 dd일 E요일 a hh:mm", Locale.KOREAN);
		calendar = Calendar.getInstance();
		date = calendar.getTime();
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		final String dateResult = dateFormat.format(date);

		helper = new moodDB(getContext());

		format1 = new SimpleDateFormat ("yyyy-MM-dd");
		todayTime = new Date();
		today = format1.format(todayTime);

		db = helper.getReadableDatabase();
		cursor = db.rawQuery("select count(morning),count(lunch), count(dinner) from mood where date = date('now') order by _id desc limit 1",null); // null 값을 제외하고 count함
		while(cursor.moveToNext()){
			if(cursor.getInt(0) >= 1) // 아침
				t1_flag = true;
			if(cursor.getInt(1) >= 1) // 점심
				t2_flag = true;
			if(cursor.getInt(2) >= 1) // 저녁
				t3_flag = true;

			System.out.println("morningCount: "+cursor.getInt(0)); // morning
			System.out.println("lunchCount: "+cursor.getInt(1)); // lunch
			System.out.println("dinnerCount: "+cursor.getInt(2)); // dinner
		}
		if(t1_flag == false && t2_flag == false && t3_flag == false){
			db = helper.getWritableDatabase();
			values = new ContentValues();
			values.put("date",today);
			db.insert("mood",null,values);
			// 초기값 설정하기!!
			text_mood.setText("\n오늘은 기분이 매우 좋아보여요. 모든 일이 다 잘 될겁니다!!");
		}

		cursor = db.rawQuery("select morning,lunch,dinner from mood where date = date('now') order by _id desc limit 1",null);
		while(cursor.moveToNext()){
			System.out.println("1) 아침 값: "+cursor.getInt(0));
			System.out.println("1) 점심 값: "+cursor.getInt(1));
			System.out.println("1) 저녁 값: "+cursor.getInt(2));

			if(t1_flag){ // 아침이 null이 아니다.
				System.out.println("아침 값: "+cursor.getInt(0));
				time1.setImageResource(images[cursor.getInt(0)]);
				count++;
				sum+=moodScore[cursor.getInt(0)];
			}
			if(t2_flag){
				System.out.println("점심 값: "+cursor.getInt(1));
				time2.setImageResource(images[cursor.getInt(1)]);
                count++;
                sum+=moodScore[cursor.getInt(1)];
			}
			if(t3_flag){
				System.out.println("저녁 값: "+cursor.getInt(2));
				time3.setImageResource(images[cursor.getInt(2)]);
                count++;
                sum+=moodScore[cursor.getInt(2)];
			}
        }
        System.out.println("sum: "+sum);
        System.out.println("count: "+count);
        if(sum!=0 && count!=0)
			cf();

		text_mood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 검진결과 조회로 이동하기!!!
			}
		});

		// 나의 기분 날씨
		final String[] myMood = new String[]{"너무 좋아요^~^", "좋아요!", "괜찮아요~", "안좋아요ㅡ.ㅡ", "엄청 힘들어요ㅠ.ㅠ"};
		// 다이어로그 창 - 기분 고르는 곳(값은 2 4 6 8 10)

		final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
		dlg.setTitle("나의 기분을 선택하세요");
		/*format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		todayTime = new Date();
		today = format1.format(todayTime);*/

		// int[] moodScore = new int[]{10,8,6,4,2};
		/* INSERT INTOthen use WHERE절 을 사용할 수 없습니다 .
		INSERT INTO는 이전에 존재하지 않았던 새로운 행을 테이블에 추가한다는 의미입니다.
		UPDATE 및 SELECT 문에서는 WHERE를 사용할 수 있지만 INSERT INTO에서는 사용할 수 없습니다.
		INSERT INTO 대신 UPDATE를 의미한다고 생각합니다.*/

		values = new ContentValues(); // 이렇게 하니깐 count 및 sum 값이 정상적으로 동작을 함. 왜 그런지는 모르겠음!!!
		time1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(t1_flag == false){
					db = helper.getWritableDatabase();
					dlg.setItems(myMood, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(myMood[which].equals("너무 좋아요^~^")){
								values.put("morning",0);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[0];
								time1.setImageResource(R.drawable.face1);
								t1_flag = true;
							}else if(myMood[which].equals("좋아요!")){
								//values = new ContentValues();
								values.put("morning",1);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[1];
								time1.setImageResource(R.drawable.face2);
								t1_flag = true;
							}else if(myMood[which].equals("괜찮아요~")){
								//values = new ContentValues();
								values.put("morning",2);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[2];
								time1.setImageResource(R.drawable.face3);
								t1_flag = true;
							}else if(myMood[which].equals("안좋아요ㅡ.ㅡ")){
								//values = new ContentValues();
								values.put("morning",3);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[3];
								time1.setImageResource(R.drawable.face5);
								t1_flag = true;
							}else if(myMood[which].equals("엄청 힘들어요ㅠ.ㅠ")){
								//values = new ContentValues();
								values.put("morning",4);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[4];
								time1.setImageResource(R.drawable.face6);
								t1_flag = true;
							}
							cf();
						}
					});
					dlg.show();
				}else
					Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
			}
		});
		time2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(t2_flag == false){
					db = helper.getWritableDatabase();
					dlg.setItems(myMood, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(myMood[which].equals("너무 좋아요^~^")){
								//values = new ContentValues();
								values.put("lunch",0);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[0];
								time2.setImageResource(R.drawable.face1);
								t2_flag = true;
							}else if(myMood[which].equals("좋아요!")){
								//values = new ContentValues();
								values.put("lunch",1);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[1];
								time2.setImageResource(R.drawable.face2);
								t2_flag = true;
							}else if(myMood[which].equals("괜찮아요~")){
								//values = new ContentValues();
								values.put("lunch",2);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[2];
								time2.setImageResource(R.drawable.face3);
								t2_flag = true;
							}else if(myMood[which].equals("안좋아요ㅡ.ㅡ")){
								//values = new ContentValues();
								values.put("lunch",3);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[3];
								time2.setImageResource(R.drawable.face5);
								t2_flag = true;
							}else if(myMood[which].equals("엄청 힘들어요ㅠ.ㅠ")){
								//values = new ContentValues();
								values.put("lunch",4);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[4];
								time2.setImageResource(R.drawable.face6);
								t2_flag = true;
							}
							cf();
						}
					});
					dlg.show();
				}else
					Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
			}
		});
		time3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(t3_flag == false){
					db = helper.getWritableDatabase();
					dlg.setItems(myMood, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(myMood[which].equals("너무 좋아요^~^")){
								//values = new ContentValues();
								values.put("dinner",0);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[0];
								time3.setImageResource(R.drawable.face1);
								t3_flag = true;
							}else if(myMood[which].equals("좋아요!")){
								//values = new ContentValues();
								values.put("dinner",1);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[1];
								time3.setImageResource(R.drawable.face2);
								t3_flag = true;
							}else if(myMood[which].equals("괜찮아요~")){
								//values = new ContentValues();
								values.put("dinner",2);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[2];
								time3.setImageResource(R.drawable.face3);
								t3_flag = true;
							}else if(myMood[which].equals("안좋아요ㅡ.ㅡ")){
								//values = new ContentValues();
								values.put("dinner",3);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[3];
								time3.setImageResource(R.drawable.face5);
								t3_flag = true;
							}else if(myMood[which].equals("엄청 힘들어요ㅠ.ㅠ")){
								//values = new ContentValues();
								values.put("dinner",4);
								db.update("mood",values,"date = ?",new String[]{ today });

								count++;
								sum+=moodScore[4];
								time3.setImageResource(R.drawable.face6);
								t3_flag = true;
							}
							cf();
						}
					});
					dlg.show();
				}
				else
					Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
			}
		});

		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(getContext());
		}
		locationService();

		String url = "http://api.openweathermap.org/data/2.5/weather?lat="
				+latitude
				+"&lon="+longitude
				+"&appid="+"98aadc5685c38c35f55fc0d41f61e983";
		//String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=e8f45ea2d762cd16bdb9c04523126aa5";
		// openweathermap.org에서 &units=metric>> 화씨를 섭씨로 바꾸기!!!
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
				new Response.Listener<String>() { //3번째 매개변수
					String result;
					@Override
					public void onResponse(String response) {
						try{
							JSONObject jsonObject = new JSONObject(response);

							JSONArray jsonArray = jsonObject.getJSONArray("weather"); // Weather Array
							JSONObject weatherObject = jsonArray.getJSONObject(0);
							String main = weatherObject.getString("main"); // "main": "Clear"
							//날씨에 따라 이미지 바꾸기
							transferImage(main);
							//한글 날씨로 변환
							main = transferWeather(main);

							JSONObject mainObject =  jsonObject.getJSONObject("main"); // "main": {
                            /*"temp": 281.52,
                                    "feels_like": 278.99,
                                    "temp_min": 280.15,
                                    "temp_max": 283.71,
                                    "pressure": 1016,
                                    "humidity": 93*/
							Integer nowTemp = mainObject.getInt("temp");
							Integer minTemp = mainObject.getInt("temp_min");
							Integer maxTemp = mainObject.getInt("temp_max");
							//섭씨로 변환
							nowTemp -= 273;
							minTemp -= 273;
							maxTemp -= 273;

							user_location.setText(area_one+" "+area_two);
							time.setText(dateResult);
							temperature.setText(nowTemp+"°C");
							TextViewdescription.setText(main);
							TextViewmaxTemp.setText(minTemp+"°C"+" / "+maxTemp+"°C");
						}
						catch (Exception e){
							// tv.setText(e.getMessage());
							e.getMessage();
						}
					}
				},
				new Response.ErrorListener() { //4번째 매개변수
					@Override
					public void onErrorResponse(VolleyError error) {
						//tv.setText(error.getMessage());
						error.getMessage();
					}
				}
		) {
		}; //end new StringRequest
		// Add the request to the RequestQueue.

		stringRequest.setShouldCache(false);
		requestQueue.add(stringRequest);
		//AutoPermissions.Companion.loadAllPermissions(getActivity(), 101);

		return v;

	} // end Oncreate()

	public void locationService(){
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Geocoder geocoder = new Geocoder(getContext());
		GPSListener gpsListener = new GPSListener();
		long minTime = 20000;
		float minDistance = 0;
		if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!=
				PackageManager.PERMISSION_GRANTED
				&&
				ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!=
						PackageManager.PERMISSION_GRANTED){ return; }

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

		try {
			Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();

				List<Address> list = geocoder.getFromLocation(latitude, longitude,10);
				area_one = list.get(0).getAdminArea(); //서욽륵별시(시)
				area_two = list.get(0).getThoroughfare(); //봉천동(동)

				//Toast.makeText(getContext(),"위치는: "+area_one+area_two,Toast.LENGTH_LONG).show();
				//user_location.setText(area_one+" "+area_two);

			}
		} catch (SecurityException | IOException e) {
			//tv.setText(e.getMessage());
			e.getMessage();
		}

	}

	private void transferImage(String main) {
		String img = main.toLowerCase();
		if (img.equals("clear")) {
			weatherIcon.setImageResource(R.drawable.clear);
		} else if (img.equals("clouds")) {
			weatherIcon.setImageResource(R.drawable.cloud);
		} else if (img.equals("rain")) {
			weatherIcon.setImageResource(R.drawable.rain_cloud);
		} else if (img.equals("snow")) {
			weatherIcon.setImageResource(R.drawable.snow);
		}
	}

	private String transferWeather(String weather) {
		String a = weather.toLowerCase();
		if (a.equals("clear"))
			return "맑음";
		else if (a.equals("thunderstorm"))
			return "뇌우";
		else if (a.equals("clouds"))
			return "구름";
		else if (a.equals("snow"))
			return "눈";
		else if (a.equals("rain"))
			return "비";
		else if (a.equals("drizzle"))
			return "이슬비";
		else if (a.equals("mist"))
			return "안개";
		else if (a.equals("fog"))
			return "안개";
		else if (a.equals("dust"))
			return "먼지";
		else return "안녕";
	}

	public void cf(){
		int average = sum/count;
		text_mood.setText("");

		if (2<= average && average <4) // 2<=average<4
			text_mood.append("오늘 엄청 힘들어 하시네요.ㅠㅠ 그래도 화이팅!!");
		else if (4<= average && average <6) // 4<=average<6
			text_mood.append("\n오늘은 기분이 별로시군요 음악을 들으면서 쉬는 걸 추천드려요 화이팅!");
		else if (6<= average  && average <8) // 6<=average<8
			text_mood.append("\n기분이 더 좋아지게 하고 싶었던걸 고민하지말고 해보세요!");
		else if (8<= average && average <10) // 8<=average<10
			text_mood.append("\n오늘은 기분이 좋아보이네요. 더 좋은 일만 일어나길~");
		else if (average == 10)
			text_mood.append("\n오늘은 기분이 매우 좋아보여요. 모든 일이 다 잘 될겁니다!!");
	}

	class GPSListener implements LocationListener {
		@Override
		public void onLocationChanged(@NonNull Location location) {
		}
	}
}
