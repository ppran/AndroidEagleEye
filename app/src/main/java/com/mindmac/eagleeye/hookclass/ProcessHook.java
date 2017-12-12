package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class ProcessHook extends MethodHook {
	private Methods mMethod = null;
	private static final String mClassName = "android.os.Process";
	private static int hash=0;

	private ProcessHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}

	// public static final void killProcess (int pid)
	// frameworks/base/core/java/android/os/Process.java
	// http://developer.android.com/reference/android/os/Process.html

	private enum Methods {
		exitValue
		,getElapsedCpuTime
		,getThreadPriority
		,getGidForName
		,myUid
		,getErrorStream
		,is64Bit
		,getUidForName
		,setThreadPriority
		,waitFor
		,myPid
		,destroy
		,sendSignal
		,supportsProcesses
		,myTid
		,killProcess
		,myUserHandle
		,getOutputStream
		,getInputStream

	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new ProcessHook(Methods.killProcess));
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.killProcess){
			argNames = "pid";
		}
		hash=hash +1;
		logBefore(uid, param, argNames,hash);
	}
	
	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.killProcess){
			argNames = "pid";
		}

		logAfter(uid, param, argNames,hash);
	}
}
