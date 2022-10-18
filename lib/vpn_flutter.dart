import 'vpn_flutter_platform_interface.dart';

class VpnFlutter {
  Future<String?> getPlatformVersion() {
    return VpnFlutterPlatform.instance.getPlatformVersion();
  }

  Future<void> initovpn() {
    return VpnFlutterPlatform.instance.initovpn();
  }

  Future<void> connect() {
    return VpnFlutterPlatform.instance.connect();
  }

  Future<void> disconnect() {
    return VpnFlutterPlatform.instance.disconnect();
  }
}
