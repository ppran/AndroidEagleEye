package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

import android.annotation.SuppressLint;
import android.os.Binder;

public class ContentResolverHook extends MethodHook {
	private Methods mMethod = null;
	private static final String mClassName = "android.content.ContentResolver";
	private static int hash=0;
	
	private ContentResolverHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}
	

	// @formatter:off

	// public final Cursor query(final Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder,CancellationSignal cancellationSignal)
	// public final Cursor query(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder)
	// public final Uri insert(Uri url, ContentValues values)
	// public final int update(Uri uri, ContentValues values, String where, String[] selectionArgs) 
	// public final int delete(Uri url, String where, String[] selectionArgs)

	// frameworks/base/core/java/android/content/ContentResolver.java

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
		,getContentResolver
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
		,addProvider
		,isSyncPending
		,getPeriodicSyncs
		,addStatusChangeListener
		,openInputStream
		,removeStatusChangeListener
		,startSync
		,query
		,openTypedAssetFileDescriptor
		,getMockContentResolver

	};
	

	@SuppressLint("InlinedApi")
	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new ContentResolverHook(method));
		
		return methodHookList;
	}
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.query){
			if(param.args.length == 6)
				argNames = "uri|projection|selection|selectionArgs|sortOrder|cancellationSignal";
			else if(param.args.length == 5)
				argNames = "uri|projection|selection|selectionArgs|sortOrder";
		}else if(mMethod == Methods.insert){
			argNames = "url|values";
		}else if(mMethod == Methods.update){
			argNames = "uri|values|where|selectionArgs";
		}else if(mMethod == Methods.delete){
			argNames = "url|where|selectionArgs";
		}

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}
	
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.query){
			if(param.args.length == 6)
				argNames = "uri|projection|selection|selectionArgs|sortOrder|cancellationSignal";
			else if(param.args.length == 5)
				argNames = "uri|projection|selection|selectionArgs|sortOrder";
		}else if(mMethod == Methods.insert){
			argNames = "url|values";
		}else if(mMethod == Methods.update){
			argNames = "uri|values|where|selectionArgs";
		}else if(mMethod == Methods.delete){
			argNames = "url|where|selectionArgs";
		}
		
		logAfter(uid, param, argNames,hash);
	}
}
