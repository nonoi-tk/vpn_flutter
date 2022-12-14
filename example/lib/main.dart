import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:vpn_flutter/vpn_flutter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _vpnFlutterPlugin = VpnFlutter();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await _vpnFlutterPlugin.getPlatformVersion() ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextButton(
                child: const Text("init"),
                onPressed: () async {
                  await _vpnFlutterPlugin.initovpn();
                  // initPlatformState();
                },
              ),
              TextButton(
                child: const Text("Start"),
                onPressed: () async {
                  await _vpnFlutterPlugin.connect();
                  // initPlatformState();
                },
              ),
              TextButton(
                child: const Text("STOP"),
                onPressed: () async {
                  await _vpnFlutterPlugin.disconnect();
                  // engine.disconnect();
                },
              ),
              /*
              if (Platform.isAndroid){}
                TextButton(
                  child: Text(_granted ? "Granted" : "Request Permission"),
                  onPressed: () {
                    engine.requestPermissionAndroid().then((value) {
                      setState(() {
                        _granted = value;
                      });
                    });
                  },
                ),
                */
            ],
          ),
          // child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
