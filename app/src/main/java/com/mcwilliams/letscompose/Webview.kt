package com.mcwilliams.letscompose

import android.print.PrintDocumentAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.Composable
import androidx.ui.viewinterop.emitView

class WebContext {
    companion object {
        val debug = true
    }
    fun createPrintDocumentAdapter(documentName: String): PrintDocumentAdapter {
        validateWebView()
        return webView!!.createPrintDocumentAdapter(documentName)
    }
    fun goForward() {
        validateWebView()
        webView!!.goForward()
    }
    fun goBack() {
        validateWebView()
        webView!!.goBack()
    }
    fun canGoBack(): Boolean {
        validateWebView()
        return webView!!.canGoBack()
    }
    private fun validateWebView() {
        if (webView == null) {
            throw IllegalStateException("The WebView is not initialized yet.")
        }
    }
    internal var webView: WebView? = null
}
private fun WebView.setRef(ref: (WebView) -> Unit) {
    ref(this)
}
private fun WebView.setUrl(url: String) {
    if (originalUrl != url) {
        if (WebContext.debug) {
            Log.d("WebComponent", "WebComponent load url")
        }
        loadUrl(url)
    }
}
@Composable
fun WebComponent(
    url: String,
    webViewClient: WebViewClient = WebViewClient(),
    webContext: WebContext
) {
    if (WebContext.debug) {
        Log.d("WebComponent", "WebComponent compose " + url)
    }
    emitView(::WebView) {
        it.setRef { view -> webContext.webView = view }
        it.setUrl(url)
        it.webViewClient = webViewClient
    }
}