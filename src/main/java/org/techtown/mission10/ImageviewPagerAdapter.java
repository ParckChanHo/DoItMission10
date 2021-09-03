package org.techtown.mission10;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImageviewPagerAdapter extends PagerAdapter {
    private int[] images = {R.drawable.action,R.drawable.ms,R.drawable.wark};
    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext;

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    public ImageviewPagerAdapter(Context context) {
        mContext = context ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // 항목을 구성한다.
        // 매개변수로 넘어오는 position 값에 맞는 항목을 구성할 뷰를 준비하여서 container에 addView() 함수로 추가해준다.

        // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page, container, false);

        ImageView imageView = view.findViewById(R.id.imageView) ;
        imageView.setImageResource(images[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                switch (position){
                    case 0:
                        url = "https://meditationwiki.net/wiki/%EB%AA%85%EC%83%81_%ED%9A%A8%EA%B3%BC";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                        return;
                    case 1:
                        url = "https://smartdata.tistory.com/146";
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent2);
                        return;
                    case 2:
                        url = "https://yeori12.tistory.com/entry/%EC%82%B0%EC%B1%85%EC%9D%98-%ED%9A%A8%EA%B3%BC";
                        Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent3);
                        return;
                    default:
                        return;
                }// end switch

            }// onClick()

        });

        // 뷰페이저에 추가.
        container.addView(view);
        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        // 개발자 코드로 뷰 객체를 동적으로 소멸시키기 위해서 호출한다. 개발자가 원할 때
        // destroyItem() 함수를 재정의하여서 함수의 매개변수인 position에 해당하는 뷰를 소멸시킬 수 있다.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 항목의 개수를 결정한다.
        // 전체 페이지 수는 10개로 고정.
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 항목을 위한 뷰를 결정한다.
        // instantiateItem() 함수에서 결정한 뷰를 최종 화면에 출력할 것인지 판단하기 위해서 호출한다.
        // 반환값이 true 이면 매개변수의 뷰를 화면에 출력시킨다.
        return (view == (View)object);
    }
}
