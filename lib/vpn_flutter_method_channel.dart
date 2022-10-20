import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'vpn_flutter_platform_interface.dart';

/// An implementation of [VpnFlutterPlatform] that uses method channels.
class MethodChannelVpnFlutter extends VpnFlutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('vpn_flutter');

  final methodChannel2 = const MethodChannel('com.nonoi.vpnlib/vpncontrol');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  Future<void> initovpn() async {
    await methodChannel.invokeMethod<String>('initovpn');
  }

  Future<void> connect() async {
    //await methodChannel.invokeMethod<String>('connect');
    await methodChannel2.invokeMethod<String>('start');
  }

  Future<void> disconnect() async {
    //await methodChannel.invokeMethod<String>('disconnect');
    await methodChannel2.invokeMethod<String>('stop');
  }
}
