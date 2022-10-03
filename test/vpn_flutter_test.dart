import 'package:flutter_test/flutter_test.dart';
import 'package:vpn_flutter/vpn_flutter.dart';
import 'package:vpn_flutter/vpn_flutter_platform_interface.dart';
import 'package:vpn_flutter/vpn_flutter_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockVpnFlutterPlatform 
    with MockPlatformInterfaceMixin
    implements VpnFlutterPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final VpnFlutterPlatform initialPlatform = VpnFlutterPlatform.instance;

  test('$MethodChannelVpnFlutter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelVpnFlutter>());
  });

  test('getPlatformVersion', () async {
    VpnFlutter vpnFlutterPlugin = VpnFlutter();
    MockVpnFlutterPlatform fakePlatform = MockVpnFlutterPlatform();
    VpnFlutterPlatform.instance = fakePlatform;
  
    expect(await vpnFlutterPlugin.getPlatformVersion(), '42');
  });
}
