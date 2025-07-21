package com.realbom.aidl_client2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.realbom.aidl_server.FirstService;
import com.realbom.aidl_server.IMessageService;
import com.realbom.aidl_server.Message;
import com.realbom.aidl_server.MessageListener;
//import com.realbom.aidl_server.MyService;
import com.realbom.aidl_server.ServiceManager;
//
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.BindServerMessage();
    }

    private void bindFirst()
    {
        // ComponentName 是 Android中用来标识组件的类，由包名和类名构成
        ComponentName comonentName = new ComponentName("com.realbom.aidl_server","com.realbom.aidl_server.RemoteService");
        Intent intent = new Intent();
        // 启动组件对象
        intent.setComponent(comonentName);
        // 绑定服务，参数1：指定要绑定的服务，参数二：回调函数，用来处理绑定服务的生命周期，参数三：BIND_AUTO_CREATE 如果服务未启动，则自动启动
        // ServiceConnection 用于监听服务绑定和解绑状态
        bindService(intent, new ServiceConnection() {
            // 绑定成功，componentName 连接组件的名称，iBinder :与服务交互的接口对象，此iBinder是通过AIDL机制暴露给客户端的远程接口
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                System.out.println("--------->onServiceConnected");

                FirstService firstService = FirstService.Stub.asInterface(iBinder);
                try {
                    firstService.sayHello();
                }catch (RemoteException e){
                    e.printStackTrace();
                }
                Message message = new Message();
                message.setContent("Hello Message");

                try {
                    firstService.syaHello_Massage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                System.out.println("--------->onServiceDisconnected");
            }
        },BIND_AUTO_CREATE);
    }
    MessageListener messageListener = new MessageListener.Stub(){
        @Override
        public void onReceive(Message message) throws RemoteException {
            System.out.println("-------->客户端收到服务端的回调");
        }
    };
    private void bindMessage()
    {
        // ComponentName 是 Android中用来标识组件的类，由包名和类名构成
        ComponentName comonentName = new ComponentName("com.realbom.aidl_server","com.realbom.aidl_server.MessageService");
        Intent intent = new Intent();
        // 启动组件对象
        intent.setComponent(comonentName);
        // 绑定服务，参数1：指定要绑定的服务，参数二：回调函数，用来处理绑定服务的生命周期，参数三：BIND_AUTO_CREATE 如果服务未启动，则自动启动
        // ServiceConnection 用于监听服务绑定和解绑状态
        bindService(intent, new ServiceConnection() {
            private IMessageService messageService;
            // 绑定成功，componentName 连接组件的名称，iBinder :与服务交互的接口对象，此iBinder是通过AIDL机制暴露给客户端的远程接口
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                System.out.println("--------->onServiceConnected");

                messageService = IMessageService.Stub.asInterface(iBinder);

                Message message = new Message();
                message.setContent("Hello Message");

                try {
                    messageService.registerMessageLister(messageListener);
                    messageService.sendMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                try {
                    messageService.unregisterMessageLister(messageListener);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("--------->onServiceDisconnected");
            }
        },BIND_AUTO_CREATE);
    }


    private void BindServerMessage()
    {
        // ComponentName 是 Android中用来标识组件的类，由包名和类名构成
        ComponentName comonentName = new ComponentName("com.realbom.aidl_server","com.realbom.aidl_server.MyService");
        Intent intent = new Intent();
        // 启动组件对象
        intent.setComponent(comonentName);
        // 绑定服务，参数1：指定要绑定的服务，参数二：回调函数，用来处理绑定服务的生命周期，参数三：BIND_AUTO_CREATE 如果服务未启动，则自动启动
        // ServiceConnection 用于监听服务绑定和解绑状态
        bindService(intent, new ServiceConnection() {
            private ServiceManager serviceManager;
            private IMessageService managerService;
            private  FirstService firstservice;
            // 绑定成功，componentName 连接组件的名称，iBinder :与服务交互的接口对象，此iBinder是通过AIDL机制暴露给客户端的远程接口
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                System.out.println("--------->onServiceConnected");
                Message message = new Message();
                message.setContent("Hello Message");
                // 获取ServiceManager
                serviceManager = ServiceManager.Stub.asInterface(iBinder);
                try {
                    firstservice = FirstService.Stub.asInterface(serviceManager.getService(FirstService.class.getName()));
                    managerService = IMessageService.Stub.asInterface(serviceManager.getService(IMessageService.class.getName()));

                    firstservice.syaHello_Massage(message);
                    managerService.registerMessageLister(messageListener);
                    managerService.sendMessage(message);


                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                try {
                    managerService.unregisterMessageLister(messageListener);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("--------->onServiceDisconnected");
                System.out.println("--------->onServiceDisconnected");
            }
        },BIND_AUTO_CREATE);
    }
}