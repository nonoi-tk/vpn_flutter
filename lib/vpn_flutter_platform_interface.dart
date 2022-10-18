import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'vpn_flutter_method_channel.dart';

abstract class VpnFlutterPlatform extends PlatformInterface {
  /// Constructs a VpnFlutterPlatform.
  VpnFlutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static VpnFlutterPlatform _instance = MethodChannelVpnFlutter();

  /// The default instance of [VpnFlutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelVpnFlutter].
  static VpnFlutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [VpnFlutterPlatform] when
  /// they register themselves.
  static set instance(VpnFlutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> initovpn() {
    throw UnimplementedError('initovpn() has not been implemented.');
  }

  Future<void> connect() {
    throw UnimplementedError('connect() has not been implemented.');
  }

  Future<void> disconnect() {
    throw UnimplementedError('disconnect() has not been implemented.');
  }
}
