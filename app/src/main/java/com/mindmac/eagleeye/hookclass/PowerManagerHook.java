package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class PowerManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash=0;
	
	private PowerManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
	}

	// @formatter:off

	// public void reboot(String reason)	
	// frameworks/base/core/java/android/os/PowerManager.java
	// http://developer.android.com/reference/android/os/PowerManager.html

	// @formatter:on

	private enum Methods {
		isPowerSaveMode
		,isScreenOn
		,newWakeLock
		,isScreenBrightnessBoosted
		,isDeviceIdleMode
		,reboot
		,isWakeLockLevelSupported
		,isIgnoringBatteryOptimizations
		,isInteractive
		,userActivity

	};

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();
		
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new PowerManagerHook(className, Methods.reboot));
		
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.reboot){
			argNames = "reason";
		}

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.reboot){
			argNames = "reason";
		}

		logAfter(uid, param, argNames,hash);
	}
}
