package com.kimjinhwan.android.contactss;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.R.attr.level;

//권한을 처리하는 클래스
public class CheckPermissionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission);

        //권한 설정이 api level이 23이상일 경우에만 실행
        //Build.VERSION.SDK_INT = 설치 안드로이드폰의 api level 가져오기
        //Build.VERSION_CODES 아래에 상수로 각 버전별 api level 이 작성되어 있다.(마시멜로부터는 맨 앞글자만, 롤리팝까지는 모든 글자를 다 써줌
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            //api level이 23미만일 경우 그냥 run
            run();
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        private void checkPermission(){
            //1. 권한체크 - 특정 권한이 있는지 시스템에 물어본다.
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                run();
            } else {
                //2. 권한이 없으면 사용자에게 권한을 달라고 요청
                String permissions[] = {Manifest.permission.READ_CONTACTS};
                requestPermissions(permissions, 100); // 권한을 요구하는 팝업이 사용자의 화면에 노출된다.
            }

        }
        private final int REQ_PERMISSION = 100;

        //3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메소드가 호출된다.
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQ_PERMISSION) {
                //3.1 사용자가 승인을 했음
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    run();
                }
                //3.2 사용자가 거절 했음
            } else {
                cancel();
            }
        }


    public void run(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void cancel(){
        Toast.makeText(this, "권한요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
