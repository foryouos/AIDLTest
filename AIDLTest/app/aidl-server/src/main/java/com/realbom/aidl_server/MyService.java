package com.realbom.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub.asBinder();
    }
    RemoteCallbackList<MessageListener> messageLists = new RemoteCallbackList<>();
    IMessageService.Stub messageService = new IMessageService.Stub() {
        @Override
        public void sendMessage(Message message) throws RemoteException {
            System.out.println("----------------->服务器收到信息" + message.getContent());
            message.setContent("来自服务端" + message.getContent());
            // 以广播的形式 将全局数据返回过去
            int size = messageLists.beginBroadcast();
            for(int i = 0;i<size;++i)
            {
                MessageListener item = messageLists.getBroadcastItem(i);
                item.onReceive(message);
            }
            messageLists.finishBroadcast();
        }

        @Override
        public void registerMessageLister(MessageListener messageListener) throws RemoteException {
            System.out.println("----------------->收到注册信息" );
            messageLists.register(messageListener);
        }

        @Override
        public void unregisterMessageLister(MessageListener messageListener) throws RemoteException {
            System.out.println("----------------->收到解除注册信息");
            messageLists.unregister(messageListener);
        }
    };
    FirstService.Stub firstService = new FirstService.Stub() {
        @Override
        public void sayHello() throws RemoteException {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("------------->这是来自服务端的sayhello()");
        }

        @Override
        public void syaHello_Massage(Message message) throws RemoteException {
            System.out.println("------------->这是来自服务端的 syaHello_Massage()");
        }
    };
    ServiceManager.Stub stub = new ServiceManager.Stub() {
        @Override
        public IBinder getService(String service_name) throws RemoteException {
            if(IMessageService.class.getName().equals(service_name))
            {
                return messageService.asBinder();
            } else if (FirstService.class.getName().equals(service_name) ) {
                return firstService.asBinder();
            }
            return null;
        }
    };
}