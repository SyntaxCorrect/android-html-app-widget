/*
 * Copyright (C) 2017 Google Inc.
 * Modifications Copyright 2019 Melely S.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.appwidgetsample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * App widget provider class, to handle update broadcast intents and updates
 * for the app widget.
 */
public class NewAppWidget extends AppWidgetProvider
{

 // Name of shared preferences file & key
 private static final String SHARED_PREF_FILE=
  "com.example.android.appwidgetsample";
 private static final String COUNT_KEY="count";

 /**
  * Update a single app widget.  This is a helper method for the standard
  * onUpdate() callback that handles one widget update at a time.
  *
  * @param context          The application context.
  * @param appWidgetManager The app widget manager.
  * @param appWidgetId      The current app widget id.
  */
 private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
 {
  RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


  appWidgetManager.updateAppWidget(appWidgetId, views);
 }


 /**
  * Override for onUpdate() method, to handle all widget update requests.
  *
  * @param context          The application context.
  * @param appWidgetManager The app widget manager.
  * @param appWidgetIds     An array of the app widget IDs.
  */
 @Override
 public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                      int[] appWidgetIds)
 {
  // There may be multiple widgets active, so update all of them.
  for (int appWidgetId : appWidgetIds)
  {
   updateAppWidget(context, appWidgetManager, appWidgetId);
  }
 }
}

