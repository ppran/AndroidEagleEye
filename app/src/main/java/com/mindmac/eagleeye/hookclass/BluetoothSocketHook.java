package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class BluetoothSocketHook extends MethodHook {
	private static final String mClassName = "android.bluetooth.BluetoothSocket";
	private static int hash =0 ;


	private BluetoothSocketHook(Methods method) {
		super(mClassName, method.name());
	}

	// @formatter:off

	// public void connect()	
	// frameworks/base/core/java/android/bluetooth/BluetoothSocket.java	
	// http://developer.android.com/reference/android/bluetooth/BluetoothSocket.html

	// @formatter:on

	private enum Methods {
		connect,getOutputStream,getInputStream,getRemoteDevice,getMaxReceivePacketSize,
		getMaxTransmitPacketSize,getConnectionType,close,isConnected
	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new BluetoothSocketHook(Methods.connect));
		
		return methodHookList;
	}

	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		hash = hash + 1;
		logBefore(uid, param, argNames,hash);
	}

	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		logAfter(uid, param, argNames,hash);
	}
}
