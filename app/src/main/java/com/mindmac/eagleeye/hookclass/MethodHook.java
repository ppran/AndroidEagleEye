package com.mindmac.eagleeye.hookclass;

import com.mindmac.eagleeye.MethodParser;
import com.mindmac.eagleeye.Util;


import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;


/***
 * 
 * @author Wenjun Hu
 * 
 * Abstract base class of hooked method
 *
 ***/


public abstract class MethodHook {
	private String mClassName;
	private String mMethodName;
	
	protected MethodHook(String className, String methodName){
		mClassName = className;
		mMethodName = methodName;
	}
	
	
	public String getClassName(){
		return mClassName;
	}
	
	public String getMethodName(){
		return mMethodName;
	}

	
	public void before(MethodHookParam param) throws Throwable {
		// Do nothing
	}

	public void after(MethodHookParam param) throws Throwable {
		// Do nothing
        //Log.i("EagleEye", "Pranav logging in method program : {" +mMethodName+ "}" + "{" + MethodParser.parseReturnValue(param) + "}" );
        /*
        if (mMethodName == "getInstalledApplications"){
            List<ApplicationInfo> packages = (List<ApplicationInfo>) param.thisObject;
            packages.clear();
            Log.i("EagleEye","ITWORKS" + MethodParser.parseReturnValue(param));
        }*/

    }
	
	public boolean isNeedLog(int uid){
		return Util.isAppNeedFrLog(uid);
	}
		
	protected void logBefore(int uid, MethodHookParam param, String argNames, int hash){
		// Check if need log
		if(!this.isNeedLog(uid))
			return;
		
		String[] argNamesArray = null;
		if(argNames != null)
			argNamesArray = argNames.split("\\|");
		String formattedArgs = MethodParser.parseMethodArgs(param, argNamesArray);
		if(formattedArgs == null)
			formattedArgs = "";
		String returnValue = MethodParser.parseReturnValue(param);
		/*
        if (mMethodName == "getNetworkType"){
            //List<ApplicationInfo> packages = (List<ApplicationInfo>) param.thisObject;
            int k = 15;
            param.setResult(k);
            //Log.i("EagleEye","ITWORKS" );
        }*/

        returnValue = MethodParser.parseReturnValue(param);
		String activityName =  param.thisObject.getClass().toString();

		String logMsg = String.format("$$$\"BasicSpecial\":\"%d\"$$$\"Before\":[\"%d\",\"%s\",\"false\"]$$$" +"\"ActivityPranName\":\"%s\""+
						"$$$\"InvokeApi\":\"%s;->%s\":[%s]$$$ \"return\":{%s}",hash, uid, Util.FRAMEWORK_HOOK_SYSTEM_API,
				activityName,mClassName, mMethodName, formattedArgs, returnValue);
		Log.i(Util.LOG_TAG, logMsg);
	}

	protected void logAfter(int uid, MethodHookParam param, String argNames, int hash){
		// Check if need log
		if(!this.isNeedLog(uid))
			return;

		String[] argNamesArray = null;
		if(argNames != null)
			argNamesArray = argNames.split("\\|");
		String formattedArgs = MethodParser.parseMethodArgs(param, argNamesArray);
		if(formattedArgs == null)
			formattedArgs = "";
		String returnValue = MethodParser.parseReturnValue(param);


		//Pranav Magic
		/*
		if (mMethodName == "getNetworkType"){
			//List<ApplicationInfo> packages = (List<ApplicationInfo>) param.thisObject;
			int k = 15;
			param.setResult(k);
			//Log.i("EagleEye","ITWORKS" );
		}*/

		//returnValue = MethodParser.parseReturnValue(param);
		String activityName =  param.thisObject.getClass().toString();

		String logMsg = String.format("$$$\"BasicSpecial\":\"%d\"$$$\"After\":[\"%d\",\"%s\",\"false\"]$$$" +"\"ActivityPranName\":\"%s\""+
						"$$$\"InvokeApi\":\"%s;->%s\":[%s]$$$ \"return\":{%s",hash, uid, Util.FRAMEWORK_HOOK_SYSTEM_API,
				activityName,mClassName, mMethodName, formattedArgs, returnValue);
		Log.i(Util.LOG_TAG, logMsg);
	}

	protected void log(int uid, MethodHookParam param, String argNames){
		// Check if need log
		if(!this.isNeedLog(uid))
			return;

		String[] argNamesArray = null;
		if(argNames != null)
			argNamesArray = argNames.split("\\|");
		String formattedArgs = MethodParser.parseMethodArgs(param, argNamesArray);
		if(formattedArgs == null)
			formattedArgs = "";
		String returnValue = MethodParser.parseReturnValue(param);


		//Pranav Magic
		/*
		if (mMethodName == "getNetworkType"){
			//List<ApplicationInfo> packages = (List<ApplicationInfo>) param.thisObject;
			int k = 15;
			param.setResult(k);
			//Log.i("EagleEye","ITWORKS" );
		}*/


		returnValue = MethodParser.parseReturnValue(param);
		String activityName =  param.thisObject.getClass().toString();

		String logMsg = String.format("$$$\"BasicSpecialThrough\":[\"%d\",\"%s\",\"false\"]$$$ " + "ActivityPranName:\"%s"+
						"$$$\"InvokeApi\":{\"%s;->%s\":[%s],$$$ \"return\":{%s", uid, Util.FRAMEWORK_HOOK_SYSTEM_API,
				activityName,mClassName, mMethodName, formattedArgs, returnValue);
		Log.i(Util.LOG_TAG, logMsg);
	}

}
