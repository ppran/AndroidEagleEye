package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class AbstractHttpClientHook extends MethodHook {
	private static final String mClassName = "org.apache.http.impl.client.AbstractHttpClient";
	private static int hash =0 ;

	private AbstractHttpClientHook(Methods method) {
		super(mClassName, method.name());
	}



	// public abstract HttpResponse execute (HttpHost target, HttpRequest request, HttpContext context)
	// external/apache-http/src/org/apache/http/impl/client/AbstractHttpClient.java
	// http://developer.android.com/reference/org/apache/http/impl/client/AbstractHttpClient.html

	private enum Methods {
		execute
	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new AbstractHttpClientHook(Methods.execute));
		
		return methodHookList;
	}


	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = "target|request|context";
		hash = hash +1;
		logBefore(uid, param, argNames,hash);
	}
	
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = "target|request|context";
		logAfter(uid, param, argNames,hash);
	}
}
