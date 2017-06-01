package com.kimjinhwan.android.contactss;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.kimjinhwan.android.contactss.domain.Data;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListView listView;
    RecyclerAdapter adapter;
    List<Data> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        datas = getContacts();
        adapter = new RecyclerAdapter(datas);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        for(Data data : getContacts()){     //List<Data> data = getContacts();
            Log.i("Contact","이름="+data.getName() +", tel=" + data.getTel());


        }
    }

    //주소록으로 가져오는 함수 만듦.(처음엔 onCreate에 썼으나, 너무 많아져서+어울리지 않아서 새로운 함수를 만들어서 옮김.)
    public List<Data> getContacts(){


        //데이터베이스 혹은 content resolver를 통해 가져온 데이터를 적재할
        //데이터 저장소를 먼저 정의한다.
        List<Data> datas = new ArrayList<>();


        //ContentResolver : 일종의 database 관리툴
        //전화번호부에는 이미 만들어져있는 ContentProvider를 통해서 data를 가져올 수 있다.
        ContentResolver resolver = getContentResolver();

        //1. 데이터 컨텐츠 Uri(자원의 주소)를 정의
        //전화번호부안에 전화번호Uri가 따로 있음.
        //전화번호 Uri(미리 정해져 있음)
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //Uri를 하나의 엑셀 시트라고 생각하면 좋음.(테이블(엑셀) 타입)
        //2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                ,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ,ContactsContract.CommonDataKinds.Phone.NUMBER};

        //3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        //null로 된 부분에는 조건을 넣을 수 있다.
        //Cursor라는 객체도 미리 정의되어 있음.
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null);
        // 반복문을 통해 cursor에 담겨 있는 데이터를 하나씩 추출한다.
        if(cursor != null){
            //moveToNext는 가져와야할 데이터를 가리킨다. (엑셀에서 한 행을 선택한 것과 같음)
            while(cursor.moveToNext()){
                int idIndex = cursor.getColumnIndex(projections[0]);
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(nameIndex);

                //4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스 값을 조회하고
                int telIndex = cursor.getColumnIndex(projections[2]);
                //4.2 해당 index를 사용해서 실제값을 가져온다.
                String tel = cursor.getString(telIndex);

                //5. 내가 설계한 데이터 클래스에 담아준다.
                Data data = new Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                //6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
                datas.add(data);

            }
        }
        return datas;
    }
}
