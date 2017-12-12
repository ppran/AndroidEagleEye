package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import com.mindmac.eagleeye.Util;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class TelephonyManagerHook extends MethodHook {
	private Methods mMethod = null;
	private static int hash =0 ;

	
	private TelephonyManagerHook(String className, Methods method) {
		super(className, method.name());
		mMethod = method;
	}



	// public CellLocation getCellLocation()
	// public List<CellInfo> getAllCellInfo ()
	// public String getDeviceId()
	// public String getGroupIdLevel1()
	// public String getLine1Number()
	// public String getNetworkCountryIso()
	// public String getNetworkOperator()
	// public String getNetworkOperatorName()
	// public int getNetworkType()
	// public int getPhoneType()
	// public String getSimCountryIso()
	// public String getSimOperator()
	// public String getSimOperatorName()
	// public String getSimSerialNumber()
	// public String getSubscriberId()
	// public String getVoiceMailAlphaTag()
	// public String getVoiceMailNumber()
	// public List<NeighboringCellInfo> getNeighboringCellInfo ()
	// frameworks/base/telephony/java/android/telephony/TelephonyManager.java
	// http://developer.android.com/reference/android/telephony/TelephonyManager.html

	// @formatter:off
	private enum Methods {
		getVoiceMailNumber
		,getCdmaMin
		,setDataEnabled
		,getDataActivity
		,getCurrentPhoneType
		,getCallState
		,setVoiceMailNumber
		,getNetworkOperatorName
		,listen
		,iccExchangeSimIO
		,getSimOperatorName
		,isSimPinEnabled
		,getSimCountryIso
		,getSimSerialNumber
		,getNetworkType
		,getDeviceSoftwareVersion
		,isRinging
		,getGroupIdLevel1
		,getPhoneCount
		,getSubscriberId
		,hasIccCard
		,handlePinMmi
		,checkCarrierPrivilegesForPackage
		,handlePinMmiForSubscriber
		,setRadio
		,getSimOperator
		,supplyPin
		,getMmsUAProfUrl
		,isWorldPhone
		,getSimState
		,needsOtaServiceProvisioning
		,iccTransmitApduBasicChannel
		,getNetworkOperator
		,isVideoCallingEnabled
		,getDeviceId
		,getNetworkCountryIso
		,sendEnvelopeWithStatus
		,answerRingingCall
		,updateServiceLocation
		,dial
		,supplyPukReportResult
		,getCarrierPackageNamesForIntent
		,isDataConnectivityPossible
		,isTtyModeSupported
		,getAllCellInfo
		,iccOpenLogicalChannel
		,getVoiceMailAlphaTag
		,getLine1Number
		,getDataEnabled
		,isIdle
		,supplyPinReportResult
		,toggleRadioOnOff
		,isVoiceCapable
		,disableDataConnectivity
		,setRadioPower
		,silenceRinger
		,enableDataConnectivity
		,checkCarrierPrivilegesForPackageAnyPhone
		,call
		,getCellLocation
		,setPreferredNetworkTypeToGlobal
		,getMmsUserAgent
		,hasCarrierPrivileges
		,getCdmaMdn
		,setLine1NumberForDisplay
		,enableVideoCalling
		,isNetworkRoaming
		,getDataState
		,getNeighboringCellInfo
		,setOperatorBrandOverride
		,isRadioOn
		,iccTransmitApduLogicalChannel
		,canChangeDtmfToneLength
		,isSmsCapable
		,getCarrierPackageNamesForIntentAndPhone
		,supplyPuk
		,iccCloseLogicalChannel
		,isHearingAidCompatibilitySupported
		,isOffhook
		,getPhoneType
		,endCall

	};
	// @formatter:on

	public static List<MethodHook> getMethodHookList(Object instance) {
		String className = instance.getClass().getName();

		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		
		for(Methods method : Methods.values())
			methodHookList.add(new TelephonyManagerHook(className, method));

		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		hash = hash + 1;

		logBefore(uid, param, argNames, Integer.toString(hash).hashCode());

	}


	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		//hash = hash +1;

		logAfter(uid, param, argNames,Integer.toString(hash).hashCode());

		// Anti anti emulator
		if(this.isNeedLog(uid))
			this.antiAntiEmu(param);
		
	}
	
	private void antiAntiEmu(MethodHookParam param){
		if(mMethod == Methods.getLine1Number || mMethod == Methods.getVoiceMailNumber)
			param.setResult(Util.generateRandomNums(11));
		else if(mMethod == Methods.getDeviceId || mMethod == Methods.getSubscriberId)
			param.setResult(Util.generateRandomNums(15));
		else if(mMethod == Methods.getSimSerialNumber)
			param.setResult(Util.generateRandomNums(20));
		else if(mMethod == Methods.getNetworkOperatorName)
			param.setResult(Util.generateRandomStrs(4));
	}

}
