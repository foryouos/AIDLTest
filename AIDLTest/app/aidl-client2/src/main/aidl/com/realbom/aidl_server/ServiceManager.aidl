// ServiceManager.aidl
package com.realbom.aidl_server;

// Declare any non-default types here with import statements

interface ServiceManager {
    IBinder getService(String service_name);
}