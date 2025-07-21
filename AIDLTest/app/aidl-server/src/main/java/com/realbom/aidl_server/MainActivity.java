package com.realbom.aidl_server;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ComponentName 是 Android中用来标识组件的类，由包名和类名构成
//        ComponentName comonentName = new ComponentName("com.realbom.aidl_server","com.realbom.aidl_server.RemoteService");
//        Intent intent = new Intent();
//        // 启动组件对象
//        intent.setComponent(comonentName);
//        // 绑定服务，参数1：指定要绑定的服务，参数二：回调函数，用来处理绑定服务的生命周期，参数三：BIND_AUTO_CREATE 如果服务未启动，则自动启动
//        // ServiceConnection 用于监听服务绑定和解绑状态
//        bindService(intent, new ServiceConnection() {
//            // 绑定成功，componentName 连接组件的名称，iBinder :与服务交互的接口对象，此iBinder是通过AIDL机制暴露给客户端的远程接口
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                System.out.println("--------->onServiceConnected");
//
//                FirstService firstService = FirstService.Stub.asInterface(iBinder);
//                try {
//                    firstService.sayHello();
//                }catch (RemoteException e){
//                    e.printStackTrace();
//                }
//                Message message = new Message();
//                message.setContent("Hello Message");
//
//                try {
//                    firstService.syaHello_Massage(message);
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//                System.out.println("--------->onServiceDisconnected");
//            }
//        },BIND_AUTO_CREATE);

    }
}