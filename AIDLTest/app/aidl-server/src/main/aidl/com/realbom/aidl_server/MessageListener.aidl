// MessageListener.aidl
package com.realbom.aidl_server;

// Declare any non-default types here with import statements
import com.realbom.aidl_server.Message;
interface MessageListener {
    void onReceive(in Message message);
}