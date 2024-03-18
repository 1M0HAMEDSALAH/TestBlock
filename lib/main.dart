// main.dart

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:device_apps/device_apps.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: AppListScreen(),
    );
  }
}

class AppListScreen extends StatefulWidget {
  @override
  _AppListScreenState createState() => _AppListScreenState();
}

class _AppListScreenState extends State<AppListScreen> {
  List<Application> _apps = [];
  static const platform = MethodChannel('app_suspension_channel');

  @override
  void initState() {
    super.initState();
    _loadApps();
  }

  Future<void> _loadApps() async {
    List<Application> apps = await DeviceApps.getInstalledApplications(
      includeAppIcons: true, includeSystemApps: true, onlyAppsWithLaunchIntent: true,
    );
    setState(() {
      _apps = apps;
    });
  }

  Future<void> _disableApp(String packageName) async {
    await platform.invokeMethod('disableApp', {'packageName': packageName})
        .then((value) =>Scaffold(body: Text('block'),))
        .catchError((e)=>print(e.toString()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('App Suspension Demo'),
      ),
      body: ListView.builder(
        itemCount: _apps.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(_apps[index].appName),
            onTap: () {
              _disableApp(_apps[index].packageName);
            },
          );
        },
      ),
    );
  }
}

