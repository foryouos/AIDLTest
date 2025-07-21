package com.realbom.aidl_server;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Message implements Parcelable {
    private String content;
    // 构造函数
    public Message() {
    }
    public Message(Parcel in) {
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0; // 如果对象没有文件描述符，通常返回0
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        // 写入数据到Parcel对象中
        parcel.writeString(content);
    }
    public void readToParcel(Parcel parcel) {
        this.content = parcel.readString();
    }
    // 设置内容的方法
    public void setContent(String content) {
        this.content = content;
    }
    // 获取内容的方法
    public String getContent() {
        return content;
    }

    // CREATOR对象用于从Parcel中创建Message对象
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            // 从Parcel中读取数据并返回Message对象
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
