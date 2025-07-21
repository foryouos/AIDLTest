package com.realbom.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return stub.asBinder();
    }

    FirstService.Stub stub = new FirstService.Stub() {
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
}