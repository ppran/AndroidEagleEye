package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class SmsManagerHook extends MethodHook {
	private Methods mMethod;
	private static int hash=0;
	
	private static final String mClassName = "android.telephony.SmsManager";

	private SmsManagerHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}

	// @formatter:off

	// public static ArrayList<SmsMessage> getAllMessagesFromIcc()
	// public void sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent)
	// public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents)
	// public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent)
	// frameworks/base/telephony/java/android/telephony/SmsManager.java
	// http://developer.android.com/reference/android/telephony/SmsManager.html

	// @formatter:on

	private enum Methods {

		downloadMultimediaMessage
		,getSmsManagerForSubscriptionId
		,getCarrierConfigValues
		,getAllMessagesFromIcc

		,getSubscriptionId


		,divideMessage
		,getDefault
		,sendMultimediaMessage
		,sendMultipartTextMessage
		,sendTextMessage
		,injectSmsPdu

		,sendDataMessage
		,getDefaultSmsSubscriptionId

	};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		for(Methods method : Methods.values())
			methodHookList.add(new SmsManagerHook(method));
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		if(!this.isNeedLog(uid))
			return;

		String argNames = null;

		if(mMethod == Methods.sendTextMessage)
			argNames = "destinationAddress|scAddress|text|sentIntent|deliveryIntent";
		else if(mMethod == Methods.sendMultipartTextMessage)
			argNames = "destinationAddress|scAddress|parts|sentIntents|deliveryIntents";
		else if(mMethod == Methods.sendDataMessage)
			argNames = "destinationAddress|scAddress|destinationPort|data|sentIntent|deliveryIntent";
		hash=hash+1;
		logBefore(uid, param, argNames,hash);
	}

	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		if(!this.isNeedLog(uid))
			return;
		
		String argNames = null;
		
		if(mMethod == Methods.sendTextMessage)
			argNames = "destinationAddress|scAddress|text|sentIntent|deliveryIntent";
		else if(mMethod == Methods.sendMultipartTextMessage)
			argNames = "destinationAddress|scAddress|parts|sentIntents|deliveryIntents";
		else if(mMethod == Methods.sendDataMessage)
			argNames = "destinationAddress|scAddress|destinationPort|data|sentIntent|deliveryIntent";
		logAfter(uid, param, argNames,hash);
	}
}
