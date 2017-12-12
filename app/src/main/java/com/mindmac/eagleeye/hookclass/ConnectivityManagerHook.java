package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class ConnectivityManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash =0 ;
	
	private ConnectivityManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
	}

	// @formatter:off
	// public NetworkInfo getActiveNetworkInfo()	
	// public NetworkInfo[]	getAllNetworkInfo()	
	// public NetworkInfo getNetworkInfo(int networkType)
	// int getNetworkPreference()	
	// frameworks/base/core/java/android/net/ConnectivityManager.java	
	// http://developer.android.com/reference/android/net/ConnectivityManager.html
	// @formatter:on

	private enum Methods {
		reportNetworkConnectivity
		,getAllNetworks
		,onLosing
		,requestNetwork
		,reportBadNetwork
		,onLost
		,unregisterNetworkCallback
		,isActiveNetworkMetered
		,getNetworkInfo
		,getNetworkCapabilities
		,getBoundNetworkForProcess
		,getLinkProperties
		,setProcessDefaultNetwork
		,setNetworkPreference
		,getActiveNetworkInfo
		,requestRouteToHost
		,getProcessDefaultNetwork
		,getNetworkPreference
		,onLinkPropertiesChanged
		,startUsingNetworkFeature
		,removeDefaultNetworkActiveListener
		,bindProcessToNetwork
		,getActiveNetwork
		,registerNetworkCallback
		,getBackgroundDataSetting
		,onCapabilitiesChanged
		,getDefaultProxy
		,releaseNetworkRequest
		,onNetworkActive
		,isDefaultNetworkActive
		,addDefaultNetworkActiveListener
		,isNetworkTypeValid
		,onAvailable
		,requestBandwidthUpdate
		,getAllNetworkInfo
		,stopUsingNetworkFeature

	};

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new ConnectivityManagerHook(className, method));
		
		return methodHookList;
	}
	public void before(MethodHookParam param) throws Throwable {

		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.getNetworkInfo)
			argNames = "networkType";

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}
	
	public void after(MethodHookParam param) throws Throwable {
		
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.getNetworkInfo)
			argNames = "networkType";
		
		logAfter(uid, param, argNames,hash);
	}
}
