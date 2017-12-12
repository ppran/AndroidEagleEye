package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class WifiManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash=0;
	
	private WifiManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
	}


	// @formatter:off
	// public	List<WifiConfiguration>	getConfiguredNetworks()	
	// public	WifiInfo	getConnectionInfo()
	// int	getWifiState()
	// boolean	isWifiEnabled()
	// public	WifiConfiguration	getWifiApConfiguration()
	// public	boolean	setWifiEnabled(boolean	enabled)
	// frameworks/base/wifi/java/android/net/wifi/WifiManager.java	
	// http://developer.android.com/reference/android/net/wifi/WifiManager.html

	// @formatter:on

	private enum Methods {

	 getWifiApConfiguration,
		isBatchedScanSupported
		,startWps
		,isDeviceToDeviceRttSupported
		,getWifiState
		,isScanAlwaysAvailable
		,reassociate
		,saveConfiguration
		,isDeviceToApRttSupported
		,startScan
		,setTdlsEnabled
		,is5GHzBandSupported
		,cancelWps
		,pingSupplicant
		,startLocationRestrictedScan
		,isP2pSupported
		,isEnhancedPowerReportingSupported
		,setWifiEnabled
		,getDhcpInfo
		,updateNetwork
		,enableNetwork
		,getConnectionStatistics
		,setTdlsEnabledWithMacAddress
		,isWifiScannerSupported
		,calculateSignalLevel
		,isTdlsSupported
		,isPortableHotspotSupported
		,disableNetwork
		,isWifiEnabled
		,removeNetwork
		,addNetwork
		,isPreferredNetworkOffloadSupported
		,disconnect
		,getConnectionInfo
		,compareSignalLevel
		,getScanResults
		,createWifiLock
		,reconnect
		,getPrivilegedConfiguredNetworks
		,getConfiguredNetworks
		,createMulticastLock
		,getBatchedScanResults

	};

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new WifiManagerHook(className, method));
		
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.setWifiEnabled){
			argNames = "enabled";
		}

		hash=hash +1;
		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.setWifiEnabled){
			argNames = "enabled";
		}

		logAfter(uid, param, argNames,hash);
	}
}
