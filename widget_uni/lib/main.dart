import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:sentry_flutter/sentry_flutter.dart';
import 'package:sentry/sentry.dart';
import 'package:redux/redux.dart';
import 'package:uni/controller/local_storage/app_shared_preferences.dart';
import 'package:uni/controller/middleware.dart';
import 'package:uni/controller/widget/widget_controller.dart';
import 'package:uni/model/app_state.dart';
import 'package:uni/redux/actions.dart';
import 'package:uni/redux/reducers.dart';
import 'package:uni/utils/constants.dart' as Constants;
import 'package:uni/view/Pages/about_page_view.dart';
import 'package:uni/view/Pages/bug_report_page_view.dart';
import 'package:uni/view/Pages/bus_stop_next_arrivals_page.dart';
import 'package:uni/view/Pages/exams_page_view.dart';
import 'package:uni/view/Pages/home_page_view.dart';
import 'package:uni/view/Pages/logout_route.dart';
import 'package:uni/view/Pages/splash_page_view.dart';
import 'package:uni/view/Widgets/page_transition.dart';
import 'package:uni/view/navigation_service.dart';
import 'package:uni/view/theme.dart';
import 'package:home_widget/home_widget.dart';

import 'package:tuple/tuple.dart';

import 'controller/on_start_up.dart';
import 'model/schedule_page_model.dart';


/// Stores the state of the app
final Store<AppState> state = Store<AppState>(appReducers,
    /* Function defined in the reducers file */
    initialState: AppState(null),
    middleware: [generalMiddleware]);

SentryEvent beforeSend(SentryEvent event) {
  return event.level == SentryLevel.info ? event : null;
}

Future<void> main() async {
  OnStartUp.onStart(state);
  await SentryFlutter.init(
    (options) {
      options.dsn =
          'https://a2661645df1c4992b24161010c5e0ecb@o553498.ingest.sentry.io/5680848';
    },
    appRunner: () => {runApp(MyApp())},
  );
}

/// Manages the state of the app
/// 
/// This class is necessary to track the app's state for
/// the current execution
class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppState(
        state: Store<AppState>(appReducers,
            /* Function defined in the reducers file */
            initialState: AppState(null),
            middleware: [generalMiddleware]));
  }
}

/// Manages the app depending on its current state
class MyAppState extends State<MyApp> {
  DateTime now;
  MyAppState({@required this.state}) {
    this.now = DateTime.now();
  }

  final Store<AppState> state;

  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations([
      DeviceOrientation.portraitUp,
    ]);
    return StoreProvider(
      store: state,
      child: MaterialApp(
          title: 'uni',
          theme: applicationLightTheme,
          home: SplashScreen(),
          navigatorKey: NavigationService.navigatorKey,
          // ignore: missing_return
          onGenerateRoute: (RouteSettings settings) {
            switch (settings.name) {
              case '/' + Constants.navPersonalArea:
                return PageTransition.makePageTransition(
                    page: HomePageView(), settings: settings);
              case '/' + Constants.navSchedule:
                return PageTransition.makePageTransition(
                    page: SchedulePage(), settings: settings);
              case '/' + Constants.navExams:
                return PageTransition.makePageTransition(
                    page: ExamsPageView(), settings: settings);
              case '/' + Constants.navStops:
                return PageTransition.makePageTransition(
                    page: BusStopNextArrivalsPage(), settings: settings);
              case '/' + Constants.navAbout:
                return PageTransition.makePageTransition(
                    page: AboutPageView(), settings: settings);
              case '/' + Constants.navBugReport:
                return PageTransition.makePageTransition(
                    page: BugReportPageView(),
                    settings: settings,
                    maintainState: false);
              case '/' + Constants.navLogOut:
                return LogoutRoute.buildLogoutRoute();
            }
          }),
    );
  }

  void checkUserPassAvailability(Timer t, WidgetController widgetController) async {
    final Tuple2<String, String> userPersistentInfo = await AppSharedPreferences
        .getPersistentUserInfo();
    if (userPersistentInfo.item1 != '' && userPersistentInfo.item2 != '') {
      final WidgetController widgetController = WidgetController();
      widgetController.fetchData(userPersistentInfo.item1, userPersistentInfo.item2);
      t.cancel();
    }
    else if (widgetController.isUpdated()) {
      t.cancel();
    }
  }

  @override
  void initState() {
    super.initState();
    final WidgetController widgetController = WidgetController();
    widgetController.updateAppWidget();
    Timer.periodic(Duration(seconds: 5), (Timer t) => checkUserPassAvailability(t, widgetController));
    Timer.periodic(Duration(seconds: 60),
        (Timer t) => state.dispatch(SetCurrentTimeAction(DateTime.now())));
  }

}
