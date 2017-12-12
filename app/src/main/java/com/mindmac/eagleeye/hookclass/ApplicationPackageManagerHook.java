package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class ApplicationPackageManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static final String mClassName = "android.app.ApplicationPackageManager";

	private static int hash =0 ;

	public ApplicationPackageManagerHook(Methods method, boolean logTrace) {
		super(mClassName, method.name());
		mMethod = method;
	}


	// frameworks/base/core/java/android/app/ApplicationPackageManager.java
	
	// public void installPackage(Uri packageURI, IPackageInstallObserver observer, int	flags, String installerPackageName)
	// public List<PackageInfo>	getInstalledPackages(int flags)
	// public List<ApplicationInfo>	getInstalledApplications(int flags)

	private enum Methods {
		installPackage, getInstalledPackages, getInstalledApplications
	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new ApplicationPackageManagerHook(method, true));
		return methodHookList;
	}
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.installPackage)
			argNames = "packageURI|observer|flags|installerPackageName";
		else if(mMethod == Methods.getInstalledPackages || mMethod == Methods.getInstalledApplications)
			argNames = "flags";


		hash = hash + 1;
		logBefore(uid, param, argNames,hash);
	}
	
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.installPackage)
			argNames = "packageURI|observer|flags|installerPackageName";
		else if(mMethod == Methods.getInstalledPackages || mMethod == Methods.getInstalledApplications)
			argNames = "flags";
		
		logAfter(uid, param, argNames,hash);
	}
}
