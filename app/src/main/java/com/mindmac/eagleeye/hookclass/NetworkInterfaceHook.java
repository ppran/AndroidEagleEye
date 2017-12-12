package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class NetworkInterfaceHook extends MethodHook {
	private Methods mMethod = null;
	private static final String mClassName = "java.net.NetworkInterface";
	private static int hash=0;
	
	private NetworkInterfaceHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}

	// @formatter:off
	// public	static	NetworkInterface	getByInetAddress(InetAddress	address)
	// public	static	NetworkInterface	getByName(String	interfaceName)
	// public	static	Enumeration<NetworkInterface>	getNetworkInterfaces()
	// public	byte[]	getHardwareAddress()
	// public	Enumeration<InetAddress>	getInetAddresses()
	// public	List<InterfaceAddress>	getInterfaceAddresses()
	// libcore/luni/src/main/java/java/net/NetworkInterface.java	
	// http://developer.android.com/reference/java/net/NetworkInterface.html
	// @formatter:on

	private enum Methods {
		getNetworkInterfaces
		,isVirtual
		,getSubInterfaces
		,supportsMulticast
		,getHardwareAddress
		,isPointToPoint
		,getInterfaceAddresses
		,getIndex
		,getInetAddresses
		,isUp
		,getByIndex
		,getDisplayName
		,getParent
		,getByName
		,getName
		,getMTU
		,getByInetAddress
		,isLoopback

	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new NetworkInterfaceHook(method));
		
		return methodHookList;
	}


	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.getByInetAddress){
			argNames = "address";
		}else if(mMethod == Methods.getByName){
			argNames = "interfaceName";
		}

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.getByInetAddress){
			argNames = "address";
		}else if(mMethod == Methods.getByName){
			argNames = "interfaceName";
		}

		logAfter(uid, param, argNames,hash);
	}
}
