
import 'vpn_flutter_platform_interface.dart';

class VpnFlutter {
  Future<String?> getPlatformVersion() {
    return VpnFlutterPlatform.instance.getPlatformVersion();
  }
}
