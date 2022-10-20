package com.nonoi.vpnlib.vpn_flutter

import android.app.Activity
import android.content.*
import android.net.VpnService
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.startActivityForResult
import de.blinkt.openvpn.OpenVpnApi
import de.blinkt.openvpn.core.OpenVPNThread
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.content.BroadcastReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/** VpnFlutterPlugin */
class VpnFlutterPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  companion object {
    const val TAG = "vpn_flutter"
  }
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var _mContext : Context;
  private lateinit var _activity: Activity;

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    _activity = binding.activity
    LocalBroadcastManager.getInstance(_mContext)
      .registerReceiver(broadcastReceiver, IntentFilter("connectionState"))
  }
  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    _activity = binding.activity
  }
  override fun onDetachedFromActivityForConfigChanges() {
  }
  override fun onDetachedFromActivity() {
    LocalBroadcastManager.getInstance(_mContext).unregisterReceiver(broadcastReceiver)
  }

  var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      try {
        //setStatus(intent.getStringExtra("state"))
        val state = intent.getStringExtra("state").toString()
        // update status...
        //TODO:setStatus(state)
      } catch (e: java.lang.Exception) {
        e.printStackTrace()
      }
      try {
        var duration = intent.getStringExtra("duration")
        var lastPacketReceive = intent.getStringExtra("lastPacketReceive")
        var byteIn = intent.getStringExtra("byteIn")
        var byteOut = intent.getStringExtra("byteOut")
        if (duration == null) duration = "00:00:00"
        if (lastPacketReceive == null) lastPacketReceive = "0"
        if (byteIn == null) byteIn = " "
        if (byteOut == null) byteOut = " "
        Log.d(TAG, "openvpn val:" + duration.toString() + lastPacketReceive.toString() + byteIn.toString() + byteOut.toString())
        //updateConnectionStatus(duration, lastPacketReceive, byteIn, byteOut)
        //TLogger.writeln()
        //Log.d(TAG, duration.toString() + lastPacketReceive.toString() + byteIn.toString() + byteOut.toString())
      } catch (e: java.lang.Exception) {
        e.printStackTrace()
      }
    }
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "vpn_flutter")
    channel.setMethodCallHandler(this)

    _mContext = flutterPluginBinding.applicationContext;

    //_mContext.startActivity(intent,Bundle(22));

  /*
    var config = ovpnconfig
    OpenVpnApi.startVpn(
      _mContext,
      config,
      "Japan",
      "V202200073301",
      "5WAqZ3Ys2D9H"
    )
*/
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    Log.d(TAG, "onMethodCall:" + call.method);
    when (call.method) {
        "getPlatformVersion" -> {
        result.success("Android ${android.os.Build.VERSION.RELEASE}")
      }
      "initovpn" -> {
        val intent = VpnService.prepare(_mContext)
        _activity.startActivityForResult(intent,1);
      }
      "connect" -> {
/*        val intent = VpnService.prepare(_mContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _mContext.startActivity(intent,Bundle(22));
*/
        var config = ovpnconfig
        OpenVpnApi.startVpn(
          _mContext,
          config,
          "Japan",
          "V202200073301",
          "5WAqZ3Ys2D9H"
        )
      }
      "disconnect" -> {
        OpenVPNThread.stop()
      }
      else -> {
      }
    }

    /*
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
    OpenVPNThread.stop()
    */
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private var ovpnconfig =
    "###############################################################################\n" +
            "# OpenVPN 2.0 Sample Configuration File\n" +
            "# for PacketiX VPN / SoftEther VPN Server\n" +
            "#\n" +
            "# !!! AUTO-GENERATED BY SOFTETHER VPN SERVER MANAGEMENT TOOL !!!\n" +
            "#\n" +
            "# !!! YOU HAVE TO REVIEW IT BEFORE USE AND MODIFY IT AS NECESSARY !!!\n" +
            "#\n" +
            "# This configuration file is auto-generated. You might use this config file\n" +
            "# in order to connect to the PacketiX VPN / SoftEther VPN Server.\n" +
            "# However, before you try it, you should review the descriptions of the file\n" +
            "# to determine the necessity to modify to suitable for your real environment.\n" +
            "# If necessary, you have to modify a little adequately on the file.\n" +
            "# For example, the IP address or the hostname as a destination VPN Server\n" +
            "# should be confirmed.\n" +
            "#\n" +
            "# Note that to use OpenVPN 2.0, you have to put the certification file of\n" +
            "# the destination VPN Server on the OpenVPN Client computer when you use this\n" +
            "# config file. Please refer the below descriptions carefully.\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# Specify the type of the layer of the VPN connection.\n" +
            "#\n" +
            "# To connect to the VPN Server as a \"Remote-Access VPN Client PC\",\n" +
            "#  specify 'dev tun'. (Layer-3 IP Routing Mode)\n" +
            "#\n" +
            "# To connect to the VPN Server as a bridging equipment of \"Site-to-Site VPN\",\n" +
            "#  specify 'dev tap'. (Layer-2 Ethernet Bridgine Mode)\n" +
            "\n" +
            "dev tun\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# Specify the underlying protocol beyond the Internet.\n" +
            "# Note that this setting must be correspond with the listening setting on\n" +
            "# the VPN Server.\n" +
            "#\n" +
            "# Specify either 'proto tcp' or 'proto udp'.\n" +
            "\n" +
            "proto udp\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# The destination hostname / IP address, and port number of\n" +
            "# the target VPN Server.\n" +
            "#\n" +
            "# You have to specify as 'remote <HOSTNAME> <PORT>'. You can also\n" +
            "# specify the IP address instead of the hostname.\n" +
            "#\n" +
            "# Note that the auto-generated below hostname are a \"auto-detected\n" +
            "# IP address\" of the VPN Server. You have to confirm the correctness\n" +
            "# beforehand.\n" +
            "#\n" +
            "# When you want to connect to the VPN Server by using TCP protocol,\n" +
            "# the port number of the destination TCP port should be same as one of\n" +
            "# the available TCP listeners on the VPN Server.\n" +
            "#\n" +
            "# When you use UDP protocol, the port number must same as the configuration\n" +
            "# setting of \"OpenVPN Server Compatible Function\" on the VPN Server.\n" +
            "\n" +
            "# Note: The below hostname is came from the Dynamic DNS Client function\n" +
            "#       which is running on the VPN Server. If you don't want to use\n" +
            "#       the Dynamic DNS hostname, replace it to either IP address or\n" +
            "#       other domain's hostname.\n" +
            "\n" +
            "remote vpn-public22.glocalnet.jp 80\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# The HTTP/HTTPS proxy setting.\n" +
            "#\n" +
            "# Only if you have to use the Internet via a proxy, uncomment the below\n" +
            "# two lines and specify the proxy address and the port number.\n" +
            "# In the case of using proxy-authentication, refer the OpenVPN manual.\n" +
            "\n" +
            ";http-proxy-retry\n" +
            ";http-proxy [proxy server] [proxy port]\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# The encryption and authentication algorithm.\n" +
            "#\n" +
            "# Default setting is good. Modify it as you prefer.\n" +
            "# When you specify an unsupported algorithm, the error will occur.\n" +
            "#\n" +
            "# The supported algorithms are as follows:\n" +
            "#  cipher: [NULL-CIPHER] NULL AES-128-CBC AES-192-CBC AES-256-CBC BF-CBC\n" +
            "#          CAST-CBC CAST5-CBC DES-CBC DES-EDE-CBC DES-EDE3-CBC DESX-CBC\n" +
            "#          RC2-40-CBC RC2-64-CBC RC2-CBC CAMELLIA-128-CBC CAMELLIA-192-CBC CAMELLIA-256-CBC\n" +
            "#  auth:   SHA SHA1 SHA256 SHA384 SHA512 MD5 MD4 RMD160\n" +
            "\n" +
            "cipher AES-128-CBC\n" +
            "auth SHA1\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# Other parameters necessary to connect to the VPN Server.\n" +
            "#\n" +
            "# It is not recommended to modify it unless you have a particular need.\n" +
            "\n" +
            "resolv-retry infinite\n" +
            "nobind\n" +
            "persist-key\n" +
            "persist-tun\n" +
            "client\n" +
            "verb 3\n" +
            "\n" +
            "###############################################################################\n" +
            "# Authentication with credentials.\n" +
            "#\n" +
            "# Comment the line out in case you want to use the certificate authentication.\n" +
            "\n" +
            "auth-user-pass\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# The certificate file of the destination VPN Server.\n" +
            "#\n" +
            "# The CA certificate file is embedded in the inline format.\n" +
            "# You can replace this CA contents if necessary.\n" +
            "# Please note that if the server certificate is not a self-signed, you have to\n" +
            "# specify the signer's root certificate (CA) here.\n" +
            "\n" +
            "<ca>\n" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDpjCCAo6gAwIBAgIBADANBgkqhkiG9w0BAQsFADBSMRUwEwYDVQQDDAx2cG4t\n" +
            "cHVibGljMjIxFTATBgNVBAoMDHZwbi1wdWJsaWMyMjEVMBMGA1UECwwMdnBuLXB1\n" +
            "YmxpYzIyMQswCQYDVQQGEwJVUzAeFw0yMjAzMjIwOTM3NDdaFw0zNzEyMzEwOTM3\n" +
            "NDdaMFIxFTATBgNVBAMMDHZwbi1wdWJsaWMyMjEVMBMGA1UECgwMdnBuLXB1Ymxp\n" +
            "YzIyMRUwEwYDVQQLDAx2cG4tcHVibGljMjIxCzAJBgNVBAYTAlVTMIIBIjANBgkq\n" +
            "hkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzNdDkwowk0Pgh5oZcfAfezkUmU0iEULz\n" +
            "e84esvRCUYSFVcF4HVKfRlcW13MOIXSsK20gQsEzIy3zlxMgxAT036J7OWD5/+hb\n" +
            "uIBjpeJzmFkMvA+AsWsgNZG3jUZSNTdDThab4g0JzDdL6ePhD6GDSqueBuiKhxI8\n" +
            "1mat07oy2rH1IyWk42s33jsI5xlih3Ii2rS/do2+nHgEfv5537cSmBwrNWNz9bGT\n" +
            "cO3Mdd2R/WvUXF47hJkIwJ/VZ4VbECIHJwHe8BAgTWvcn+EXI+rinELeN0oXTKNA\n" +
            "K0Kspd85F+0rH+grFm1mZF0qRwdzykuaOs3/LKspGfFEOxnWTpt6zwIDAQABo4GG\n" +
            "MIGDMA8GA1UdEwEB/wQFMAMBAf8wCwYDVR0PBAQDAgH2MGMGA1UdJQRcMFoGCCsG\n" +
            "AQUFBwMBBggrBgEFBQcDAgYIKwYBBQUHAwMGCCsGAQUFBwMEBggrBgEFBQcDBQYI\n" +
            "KwYBBQUHAwYGCCsGAQUFBwMHBggrBgEFBQcDCAYIKwYBBQUHAwkwDQYJKoZIhvcN\n" +
            "AQELBQADggEBAFWHhUc/ReCvDekOWGxhnEA4tPZGu0r5UdlDmXQWfpX+keGsTqxO\n" +
            "dHKQx/BnmDuET5A3KPPjFL2ji/f/8F8/gRgnxA+ycUlf5C+Qe0UbfRnc0tWs7yjk\n" +
            "wXSJHdhiOTpmfvWlDP21/gSEilNcKXQWJPCvbh7UknMsxjbyWUP71vzfv1rp61/5\n" +
            "5XH3+9I68h7cvh4M/M3LwEoUo0TRie/7UoR+nfhVOTVuvmWKnrQniyDyANzwcWQu\n" +
            "zO+zgc2F1S/2N4Wf5V43wUHlJ2E29hN4Huz0ArNJRsxA2VR2SnBzrVQXOyblLCxx\n" +
            "ltAwLM3XtSOUfdikuh7aQeeyKJYYi8Je18E=\n" +
            "-----END CERTIFICATE-----\n" +
            "\n" +
            "</ca>\n" +
            "\n" +
            "\n" +
            "###############################################################################\n" +
            "# Client certificate and key.\n" +
            "#\n" +
            "# A pair of client certificate and private key is required in case you want to\n" +
            "# use the certificate authentication.\n" +
            "#\n" +
            "# To enable it, uncomment the lines below.\n" +
            "# Paste your certificate in the <cert> block and the key in the <key> one.\n" +
            "\n" +
            ";<cert>\n" +
            ";-----BEGIN CERTIFICATE-----\n" +
            ";\n" +
            ";-----END CERTIFICATE-----\n" +
            ";</cert>\n" +
            "\n" +
            ";<key>\n" +
            ";-----BEGIN RSA PRIVATE KEY-----\n" +
            ";\n" +
            ";-----END RSA PRIVATE KEY-----\n" +
            ";</key>\n" +
            "\n" +
            "\n";

}
