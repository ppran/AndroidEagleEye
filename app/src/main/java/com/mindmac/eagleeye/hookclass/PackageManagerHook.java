package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class PackageManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash;
	
	private PackageManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
		
	}


	// @formatter:off
	
	// public List<ApplicationInfo> getInstalledApplications(int flags)
	// public List<PackageInfo> getInstalledPackages(int flags)
	// public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags)
	// public List<PackageInfo> getPreferredPackages(int flags)
	// public abstract String[] getPackagesForUid (int uid)
	// public void installPackage(Uri packageURI, IPackageInstallObserver observer, int flags, String installerPackageName) 
	// public void deletePackage(String packageName, IPackageDeleteObserver observer, int flags)
	// frameworks/base/core/java/android/app/ApplicationPackageManager.java
	
	// @formatter:on

	private enum Methods {
		queryInstrumentation
		,getApplicationEnabledSetting
		,getPackageArchiveInfo
		,addOnPermissionsChangeListener
		,getNameForUid
		,addPermission
		,resolveService
		,getReceiverInfo
		,addPermissionAsync
		,getActivityBanner
		,getUserBadgedDrawableForDensity
		,resolveActivity
		,grantRuntimePermission
		,getApplicationLabel
		,canonicalToCurrentPackageNames
		,getLeanbackLaunchIntentForPackage
		,queryIntentActivities
		,hasSystemFeature
		,setInstallerPackageName
		,checkPermission
		,queryPermissionsByGroup
		,getPermissionFlags
		,verifyPendingInstall
		,getPackageGids
		,extendVerificationTimeout
		,getApplicationLogo
		,getXml
		,addPackageToPreferred
		,clearPackagePreferredActivities
		,queryIntentContentProviders
		,getProviderInfo
		,getDrawable
		,verifyIntentFilter
		,getApplicationBanner
		,queryIntentActivityOptions
		,getUserBadgedLabel
		,getPackageInfo
		,resolveContentProvider
		,getAllPermissionGroups
		,getResourcesForActivity
		,isPermissionRevokedByPolicy
		,getPermissionGroupInfo
		,removePermission
		,setComponentEnabledSetting
		,getPackagesHoldingPermissions
		,getApplicationInfo
		,isSafeMode
		,getPreferredPackages
		,updatePermissionFlags
		,getPermissionInfo
		,getInstrumentationInfo
		,getLaunchIntentForPackage
		,getInstalledApplications
		,queryBroadcastReceivers
		,currentToCanonicalPackageNames
		,queryContentProviders
		,getInstalledPackages
		,getActivityIcon
		,removeOnPermissionsChangeListener
		,queryIntentServices
		,removePackageFromPreferred
		,getText
		,getInstallerPackageName
		,revokeRuntimePermission
		,getActivityLogo
		,checkSignatures
		,getResourcesForApplication
		,addPreferredActivity
		,getServiceInfo
		,getSystemSharedLibraryNames
		,getComponentEnabledSetting
		,setApplicationEnabledSetting
		,getApplicationIcon
		,getPackageInstaller
		,getPreferredActivities
		,getSystemAvailableFeatures
		,getPackagesForUid
		,getActivityInfo
		,getDefaultActivityIcon
		,getUserBadgedIcon
		,installPackage
		,deletePackage

	};

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();
		
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for (Methods method : Methods.values())
			methodHookList.add(new PackageManagerHook(className, method));
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.getInstalledApplications || mMethod == Methods.getInstalledPackages ||
				mMethod == Methods.getPreferredPackages){
			argNames = "flags";
		}else if(mMethod == Methods.getPackagesHoldingPermissions)
			argNames = "permissions|flags";
		else if(mMethod == Methods.getPackagesForUid)
			argNames = "uid";
		else if(mMethod == Methods.installPackage)
			argNames = "packageURI|observer|flags|installerPackageName";
		else if(mMethod == Methods.deletePackage)
			argNames = "packageName|observer|flags";

		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}
	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.getInstalledApplications || mMethod == Methods.getInstalledPackages || 
				mMethod == Methods.getPreferredPackages){

		    //List<ApplicationInfo> packages = (List<ApplicationInfo>) param.thisObject;
		    //packages.clear();
		    //param.thisObject=null;


			//Log.i("EagleEye","reached here");
			argNames = "flags";
		}else if(mMethod == Methods.getPackagesHoldingPermissions)
			argNames = "permissions|flags";
		else if(mMethod == Methods.getPackagesForUid)
			argNames = "uid";
		else if(mMethod == Methods.installPackage)
			argNames = "packageURI|observer|flags|installerPackageName";
		else if(mMethod == Methods.deletePackage)
			argNames = "packageName|observer|flags";

		logAfter(uid, param, argNames,hash);
	}
}
