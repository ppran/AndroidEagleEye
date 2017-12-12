package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class SettingsSecureHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash=0;
	
	private static final String mClassName = "android.provider.Settings.Secure";

	private SettingsSecureHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}



	// @formatter:off

	// public synchronized static String getString(ContentResolver resolver, String name)
	// frameworks/base/core/java/android/provider/Settings.java
	// frameworks/base/core/java/android/content/ContentResolver.java
	// http://developer.android.com/reference/android/provider/Settings.Secure.html

	// @formatter:on

	private enum Methods {
		update
		,setIsSyncable
		,releasePersistableUriPermission
		,cancelSync
		,getSyncAutomatically
		,uncanonicalize
		,acquireUnstableContentProviderClient
		,canonicalize
		,getType
		,applyBatch
		,openFileDescriptor
		,call
		,takePersistableUriPermission
		,getCurrentSyncs
		,getPersistedUriPermissions
		,openOutputStream
		,openAssetFileDescriptor
		,requestSync
		,getCurrentSync
		,getOutgoingPersistedUriPermissions
		,registerContentObserver
		,getStreamTypes
		,acquireContentProviderClient
		,notifyChange
		,insert
		,bulkInsert
		,unregisterContentObserver
		,getSyncAdapterTypes
		,removePeriodicSync
		,setMasterSyncAutomatically
		,delete
		,validateSyncExtrasBundle
		,addPeriodicSync
		,getMasterSyncAutomatically
		,setSyncAutomatically
		,isSyncActive
		,getIsSyncable
		,isSyncPending
		,getPeriodicSyncs
		,addStatusChangeListener
		,openInputStream
		,removeStatusChangeListener
		,startSync
		,query
		,openTypedAssetFileDescriptor
		,getString
	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new SettingsSecureHook(Methods.getString));
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.getString){
			argNames = "resolver|name";
		}

		hash= hash+1;
		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.getString){
			argNames = "resolver|name";
		}

		logAfter(uid, param, argNames,hash);
	}
}
