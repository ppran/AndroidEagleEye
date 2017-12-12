package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class LocationManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash;
	
	private LocationManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
	}


	// @formatter:off
	// public Location getLastKnownLocation(String provider)
	// frameworks/base/location/java/android/location/LocationManager.java
	// http://developer.android.com/reference/android/location/LocationManager.html

	// @formatter:on

	private enum Methods {
		getProvider
		,addProximityAlert
		,clearTestProviderStatus
		,getProviders
		,addTestProvider
		,isProviderEnabled
		,removeGpsNavigationMessageListener
		,removeGpsMeasurementListener
		,getLastKnownLocation
		,removeNmeaListener
		,clearTestProviderLocation
		,setTestProviderEnabled
		,getAllProviders
		,setTestProviderLocation
		,removeGpsStatusListener
		,getBestProvider
		,requestSingleUpdate
		,addNmeaListener
		,addGpsStatusListener
		,removeProximityAlert
		,sendExtraCommand
		,requestLocationUpdates
		,clearTestProviderEnabled
		,getGpsStatus
		,removeUpdates
		,addGpsNavigationMessageListener
		,removeTestProvider
		,setTestProviderStatus
		,addGpsMeasurementListener

	};

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		
		methodHookList.add(new LocationManagerHook(className, Methods.getLastKnownLocation));
		
		return methodHookList;
	}


	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}
	
	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		logAfter(uid, param, argNames,hash);
	}
}
