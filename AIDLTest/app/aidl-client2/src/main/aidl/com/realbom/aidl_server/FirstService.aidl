// FirstService.aidl
package com.realbom.aidl_server;

// Declare any non-default types here with import statements
import com.realbom.aidl_server.Message;
interface FirstService {
    oneway void sayHello();
    void syaHello_Massage(in Message message);
}