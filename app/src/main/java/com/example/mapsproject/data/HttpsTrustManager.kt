package com.example.mapsproject.data

import android.annotation.SuppressLint
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class HttpsTrustManager : X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    @Throws(CertificateException::class)
    override fun checkClientTrusted(
        x509Certificates: Array<X509Certificate?>?, s: String?
    ) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    @Throws(CertificateException::class)
    override fun checkServerTrusted(
        x509Certificates: Array<X509Certificate?>?, s: String?
    ) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return _AcceptedIssuers
    }

    fun isClientTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    fun isServerTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    companion object {
        private var trustManagers: Array<TrustManager>? = null
        private val _AcceptedIssuers: Array<X509Certificate> = arrayOf<X509Certificate>()
        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            var context: SSLContext? = null
            if (trustManagers == null) {
                trustManagers = arrayOf(HttpsTrustManager())
            }
            try {
                context = SSLContext.getInstance("TLS")
                context.init(null, trustManagers, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(
                context?.socketFactory
            )
        }
    }
}