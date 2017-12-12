package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;


import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class BluetoothAdapterHook extends MethodHook {
	
	private static final String mClassName = "android.bluetooth.BluetoothAdapter";

	private static int hash =0 ;
	
	private BluetoothAdapterHook(Methods method) {
		super(mClassName, method.name());
	}

	// @formatter:off

	// public boolean enable()
	// public String getAddress()
	// frameworks/base/core/java/android/bluetooth/BluetoothAdapter.java
	// http://developer.android.com/reference/android/bluetooth/BluetoothAdapter.html

	// @formatter:on

	private enum Methods {
		enable, getAddress,isLeEnabled,setName,isDiscovering,closeProfileProxy,
		stopLeScan,getDefaultAdapter,isOffloadedScanBatchingSupported,onLeScan,
		enableBLE,getBluetoothLeAdvertiser,getName,isOffloadedFilteringSupported,
		isEnabled,getScanMode,getState,getBondedDevices,cancelDiscovery,startDiscovery,
		disable,listenUsingInsecureRfcommWithServiceRecord,getRemoteDevice,checkBluetoothAddress,
		getProfileConnectionState,getProfileProxy,disableBLE, startLeScan, listenUsingRfcommWithServiceRecord,
		getBluetoothLeScanner,isMultipleAdvertisementSupported,isBleScanAlwaysAvailable
	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new BluetoothAdapterHook(method));
		
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
