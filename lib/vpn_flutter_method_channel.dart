import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'vpn_flutter_platform_interface.dart';

/// An implementation of [VpnFlutterPlatform] that uses method channels.
class MethodChannelVpnFlutter extends VpnFlutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('vpn_flutter');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  Future<void> connect() async {
    await methodChannel.invokeMethod<String>('connect');
  }
  Future<void> disconnect() async {
    await methodChannel.invokeMethod<String>('disconnect');
  }
}
