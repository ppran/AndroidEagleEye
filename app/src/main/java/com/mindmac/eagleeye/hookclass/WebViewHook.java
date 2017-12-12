package com.mindmac.eagleeye.hookclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Binder;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class WebViewHook extends MethodHook {
	private Methods mMethod = null;
	private static final String mClassName = "android.webkit.WebView";
	private static int hash=0;
	
	private WebViewHook(Methods method) {
		super(mClassName, method.name());
		mMethod = method;
	}


	// @formatter:off

	// public void loadUrl(String url)
	// public void loadUrl(String url, Map<String, String> additionalHttpHeaders)
	// frameworks/base/core/java/android/webkit/WebView.java
	// http://developer.android.com/reference/android/webkit/WebView.html

	// @formatter:on

	private enum Methods {

         onPause
        ,getTitle
        ,isPrivateBrowsingEnabled
        ,clearMatches
        ,pageUp
        ,flingScroll
        ,destroy
        ,clearClientCertPreferences
        ,setDownloadListener
        ,getCertificate
        ,onResume
        ,requestFocusNodeHref
        ,setWebChromeClient
        ,setHttpAuthUsernamePassword
        ,getHitTestResult
        ,setNetworkAvailable
        ,clearFormData
        ,onGlobalFocusChanged
        ,pageDown
        ,getWebViewProvider
        ,freeMemory
        ,setPictureListener
        ,postWebMessage
        ,getHttpAuthUsernamePassword
        ,getScale
        ,onChildViewAdded
        ,getContentHeight
        ,clearCache
        ,getFavicon
        ,zoomIn
        ,savePassword
        ,saveWebArchive
        ,canGoBack
        ,loadData
        ,getSettings
        ,getOriginalUrl
        ,overlayVerticalScrollbar
        ,requestImageRef
        ,findAll
        ,loadUrl
        ,resumeTimers
        ,postVisualStateCallback
        ,setInitialScale
        ,enableSlowWholeDocumentDraw
        ,loadDataWithBaseURL
        ,addJavascriptInterface
        ,copyBackForwardList
        ,canZoomOut
        ,setMapTrackballToArrowKeys
        ,documentHasImages
        ,goForward
        ,canGoForward
        ,restoreState
        ,setWebContentsDebuggingEnabled
        ,evaluateJavascript
        ,goBack
        ,showFindDialog
        ,overlayHorizontalScrollbar
        ,onFindResultReceived
        ,saveState
        ,stopLoading
        ,canGoBackOrForward
        ,setCertificate
        ,zoomBy
        ,capturePicture
        ,clearSslPreferences
        ,setHorizontalScrollbarOverlay
        ,clearView
        ,getProgress
        ,findAddress
        ,zoomOut
        ,canZoomIn
        ,onChildViewRemoved
        ,findAllAsync
        ,createWebMessageChannel
        ,findNext
        ,reload
        ,setVerticalScrollbarOverlay
        ,pauseTimers
        ,setWebViewClient
        ,goBackOrForward
        ,setFindListener
        ,getUrl
        ,createPrintDocumentAdapter
        ,clearHistory
        ,invokeZoomPicker
        ,removeJavascriptInterface
        ,postUrl

		};

	public static List<MethodHook> getMethodHookList() {
		List<MethodHook> methodHookList = new ArrayList<MethodHook>();
		methodHookList.add(new WebViewHook(Methods.loadUrl));
		
		return methodHookList;
	}

	@Override
	public void before(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;

		if(mMethod == Methods.loadUrl){
			if(param.args.length == 1)
				argNames = "url";
			else if(param.args.length == 2)
				argNames = "url|additionalHttpHeaders";
		}

		hash =hash +1;
		logBefore(uid, param, argNames,hash);
	}
	
	@Override
	public void after(MethodHookParam param) throws Throwable {
		int uid = Binder.getCallingUid();
		String argNames = null;
		
		if(mMethod == Methods.loadUrl){
			if(param.args.length == 1)
				argNames = "url";
			else if(param.args.length == 2)
				argNames = "url|additionalHttpHeaders";
		}

		logAfter(uid, param, argNames,hash);
	}
}
