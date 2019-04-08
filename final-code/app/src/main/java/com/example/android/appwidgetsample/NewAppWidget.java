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
import android.app.PendingIntent.CanceledException;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.SparseArray;
import android.webkit.WebView;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * App widget provider class, to handle update broadcast intents and updates
 * for the app widget.
 */
public class NewAppWidget extends AppWidgetProvider
{
 private static SparseArray<WebView> cachedWebViews=null;


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

  Bitmap canvasBitmap=null;
  Canvas canvas=null;
  Paint paint=null;

  Bundle options=appWidgetManager.getAppWidgetOptions(appWidgetId);
  Sizes newSizes=new Sizes(options);

  canvasBitmap=Bitmap.createBitmap(newSizes.minWidth, newSizes.maxHeight, Bitmap.Config.ARGB_8888);

  canvas=new Canvas(canvasBitmap);

  paint=new Paint();
  paint.setAntiAlias(true);

  canvas.drawColor(0, PorterDuff.Mode.CLEAR);


  WebView webView=prepareWebView(context, appWidgetManager, appWidgetId);
  webView.layout(0, 0, newSizes.minWidth, newSizes.maxHeight);

  int contentHeight=webView.getContentHeight();

  if (contentHeight==0)
  {
   // we need to wait!

   Intent intentUpdate=new Intent(context, NewAppWidget.class);

   // The intent action must be an app widget update.
   intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

   // Include the widget ID to be updated as an intent extra.
   int[] idArray=new int[]{appWidgetId};
   intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

   final PendingIntent pendingUpdate=PendingIntent.getBroadcast(context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

   // We could use an Alarm but Handlers are way more precise for sub-second schedulings
   Handler handler=new Handler();
   handler.postDelayed(new Runnable()
   {
    public void run()
    {
     try
     {
      pendingUpdate.send();
     }
     catch (CanceledException e)
     {
      e.printStackTrace();
     }
    }
   }, 2000);
   //}, 300);

   // return instead of drawing
   return;
  }

  webView.draw(canvas);

  views.setImageViewBitmap(R.id.draw, canvasBitmap);

  appWidgetManager.updateAppWidget(appWidgetId, views);
 }


 private WebView prepareWebView(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
 {
  if (cachedWebViews==null) { cachedWebViews=new SparseArray<>(); }
  WebView webView=cachedWebViews.get(appWidgetId);

  if (webView==null)
  {
   webView=new WebView(context);
   cachedWebViews.put(appWidgetId, webView);

   webView.setBackgroundColor(Color.TRANSPARENT);

//   String unencodedHtml="<!DOCTYPE html><html><body>Hello World in HTML!</body></html>";

   String unencodedHtml="<!DOCTYPE html>\n"+
    "<html>\n"+
    " <body>\n"+
    "  <a class=\"weatherwidget-io\" href=\"https://forecast7.com/en/40d71n74d01/new-york/\" data-label_1=\"NEW YORK\" data-label_2=\"WEATHER\" data-theme=\"original\" >NEW YORK WEATHER</a>\n"+
    "  <script>\n"+
    "!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src='https://weatherwidget.io/js/widget.min.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','weatherwidget-io-js');\n"+
    "  </script>\n"+
    " </body>\n"+
    "</html>\n";


   String encodedHtml=Base64.encodeToString(unencodedHtml.getBytes(), Base64.DEFAULT);
   webView.loadData(encodedHtml, "text/html", "base64");

   webView.setDrawingCacheEnabled(true);
   webView.buildDrawingCache();
   webView.getSettings().setJavaScriptEnabled(true);
  }

  return webView;
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

