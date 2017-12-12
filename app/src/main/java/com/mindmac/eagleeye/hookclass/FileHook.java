package com.mindmac.eagleeye.hookclass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mindmac.eagleeye.MethodParser;
import com.mindmac.eagleeye.Util;

import android.os.Binder;
import android.util.Log;



import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class FileHook extends MethodHook {
	private HashMap<String, Boolean> excludedFileMap = new HashMap<String, Boolean>();
	
	private String[] excludedFileArray = {"/dev/socket/qemud", "/dev/qemu_pipe", "/sys/qemu_trace",
			"/system/lib/libc_malloc_debug_qemu.so", "/system/bin/qemu-props",
			"/dev/socket/genyd", "/dev/socket/baseband_genyd"};
	
	private static final String mClassName = "java.io.File";
	private Methods mMethod = null;
	private static int hash=0;
	
	private FileHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
		
		for(String excludedFile : excludedFileArray)
			excludedFileMap.put(excludedFile, true);
	}
	
	// public boolean delete ()
	// public void deleteOnExit ()
	// libcore/luni/src/main/java/java/lang/Runtime.java
	// http://developer.android.com/reference/java/lang/Runtime.html

	private enum Methods {
		/*
		length
		,getPath
		,list
		,isHidden
		,canRead
		,isAbsolute
		,mkdirs
		,createNewFile
		,renameTo
		,setReadOnly
		,lastModified
		,getCanonicalPath
		,setExecutable
		,isFile
		,compareTo
		,setReadable
		,getParent
		,getParentFile
		,delete
		,getName
		,exists
		,toURL
		,getFreeSpace
		,listRoots
		,canExecute
		,getTotalSpace
		,deleteOnExit
		,getAbsoluteFile
		,createTempFile
		,getAbsolutePath
		,setLastModified
		,getCanonicalFile
		,setWritable
		,toURI
		,listFiles
		,getUsableSpace
		,canWrite
		,isDirectory
		,mkdir
		*/
		delete, deleteOnExit, exists, listRoots,mkdirs

	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new FileHook(method));

		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		hash=hash+1;
		logSpecialBefore(uid, param,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		logSpecial(uid, param,hash);
	}

	private void logSpecialBefore(int uid, MethodHookParam param,int hash){
		// Check if need log
		if(!this.isNeedLog(uid))
			return;

		File file = (File) param.thisObject;
		String filePath = "";
		if(file != null){
			filePath = file.getAbsolutePath();
		}
		if(mMethod == Methods.exists)
			antiAntiEmu(param, filePath);
		else{
			String returnValue = MethodParser.parseReturnValue(param);
			String logMsg = String.format("$$$\"BasicFileLog\":\"%d\"$$$\"Before\":[\"%d\", \"%s\",\"false\"]$$$\"InvokeApi\":{\"%s;->%s\":{\"file\":\"%s\"}$$$\"return\":[%s]}}",
					hash,uid, Util.FRAMEWORK_HOOK_SYSTEM_API, this.getClassName(), this.getMethodName(), filePath, returnValue);
			Log.i(Util.LOG_TAG, logMsg);
		}
	}

	private void logSpecial(int uid, MethodHookParam param, int hash){
		// Check if need log
		if(!this.isNeedLog(uid))
			return;
		
		File file = (File) param.thisObject;
		String filePath = "";
		if(file != null){
			filePath = file.getAbsolutePath();
		}
		if(mMethod == Methods.exists)
			antiAntiEmu(param, filePath);
		else{
			String returnValue = MethodParser.parseReturnValue(param);
			String logMsg = String.format("$$$\"BasicFileLog\":\"%d\"$$$\"After\":[\"%d\", \"%s\",\"false\"]$$$\"InvokeApi\":{\"%s;->%s\":{\"file\":\"%s\"}$$$\"return\":[%s]}}",
					hash,uid, Util.FRAMEWORK_HOOK_SYSTEM_API, this.getClassName(), this.getMethodName(), filePath, returnValue);
			Log.i(Util.LOG_TAG, logMsg);
		}
	}
	
	private void antiAntiEmu(MethodHookParam param, String filePath){
		if(excludedFileMap.containsKey(filePath))
			param.setResult(false);
	}
}
