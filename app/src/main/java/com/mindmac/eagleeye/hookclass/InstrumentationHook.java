package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class InstrumentationHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash =0;

	private static final String mClassName = "android.app.Instrumentation";

	private InstrumentationHook(Methods method) {
		super( mClassName, method.name());
		mMethod = method;
	}



	// @formatter:off

	// public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,Intent intent, int requestCode, Bundle options, UserHandle user)
	// public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,Intent intent, int requestCode, Bundle options)
	// frameworks/base/core/java/android/app/Instrumentation.java

	// @formatter:on

	private enum Methods {
		execStartActivity,
		stopProfiling
		,callActivityOnDestroy
		,sendPointerSync
		,callActivityOnRestoreInstanceState
		,invokeMenuActionSync
		,callActivityOnStop
		,setAutomaticPerformanceSnapshots
		,sendStatus
		,callActivityOnNewIntent
		,runOnMainSync
		,callActivityOnSaveInstanceState
		,sendKeySync
		,getContext
		,setInTouchMode
		,startPerformanceSnapshot
		,callActivityOnStart
		,getTargetContext
		,finish
		,startProfiling
		,callActivityOnPause
		,invokeContextMenuAction
		,sendCharacterSync
		,stopAllocCounting
		,getUiAutomation
		,startActivitySync
		,removeMonitor
		,callActivityOnCreate
		,callActivityOnResume
		,callActivityOnPostCreate
		,callApplicationOnCreate
		,getBinderCounts
		,isProfiling
		,onStart
		,callActivityOnRestart
		,startAllocCounting
		,waitForIdleSync
		,onDestroy
		,endPerformanceSnapshot
		,callActivityOnUserLeaving
		,onException
		,getAllocCounts
		,waitForMonitorWithTimeout
		,getComponentName
		,addMonitor
		,waitForIdle
		,sendKeyDownUpSync
		,sendStringSync
		,waitForMonitor
		,newApplication
		,sendTrackballEventSync
		,checkMonitorHit
		,onCreate
		,start
		,newActivity


	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new InstrumentationHook(Methods.execStartActivity));

		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.execStartActivity){
			if(param.args.length == 8)
				argNames = "who|contextThread|token|target|intent|requestCode|options|user";
			else if(param.args.length == 7)
				argNames = "who|contextThread|token|target|intent|requestCode|options";
		}
		hash= hash+1;

		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.execStartActivity){
			if(param.args.length == 8)
				argNames = "who|contextThread|token|target|intent|requestCode|options|user";
			else if(param.args.length == 7)
				argNames = "who|contextThread|token|target|intent|requestCode|options";
		}
		
		logAfter(uid, param, argNames,hash);
	}
}
