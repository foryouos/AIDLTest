// IMessageService.aidl
package com.realbom.aidl_server;

// Declare any non-default types here with import statements
import com.realbom.aidl_server.Message;
import com.realbom.aidl_server.MessageListener;
interface IMessageService {
    void sendMessage(in Message message);

    // 注册方法 往服务器里注册 和 解 注册方法
    void registerMessageLister(MessageListener messageListener);
    // 解除注册
    void unregisterMessageLister(MessageListener messageListener);
}