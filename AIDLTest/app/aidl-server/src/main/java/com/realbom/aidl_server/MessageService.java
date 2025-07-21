package com.realbom.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class MessageService extends Service {
    public MessageService() {
    }
    RemoteCallbackList<MessageListener> messageLists = new RemoteCallbackList<>();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return stub.asBinder();
    }
    IMessageService.Stub stub = new IMessageService.Stub() {
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
}