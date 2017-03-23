package com.dali;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static void startActivity(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }
    private MyService.MyBinder myBinder;
    private MyAIDLService myAIDLService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*myBinder = (MyService.MyBinder) service;
            myBinder.startTask();*/
            myAIDLService = MyAIDLService.Stub.asInterface(service);
            try {
                int result = myAIDLService.plus(3, 5);
                String upperStr = myAIDLService.toUpperCase("hello world");
                Log.i("MyService", "result is " + result);
                Log.i("MyService", "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 开启服务
     * @param view
     */
    public void startService(View view) {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }

    /**
     * 停止服务
     * @param view
     */
    public void stopService(View view) {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }

    /**
     * 绑定服务
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    /**
     * 解除绑定服务
     * @param view
     */
    public void unBindService(View view) {
        unbindService(serviceConnection);
    }

}
