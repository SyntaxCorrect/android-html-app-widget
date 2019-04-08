/*
 * Copyright (C) 2019 Melely S.r.l.
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

import android.appwidget.AppWidgetManager;
import android.os.Bundle;

import static com.example.android.appwidgetsample.AndroidUtil.dp2Pixels;

public class Sizes
{
 int minWidth;
 int maxWidth;
 int minHeight;
 int maxHeight;

 Sizes(Bundle options)
 {
  minWidth=(int)dp2Pixels(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH));
  maxWidth=(int)dp2Pixels(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH));
  minHeight=(int)dp2Pixels(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT));
  maxHeight=(int)dp2Pixels(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT));
 }

 public boolean equals(Object obj)
 {
  if (obj instanceof Sizes)
  {
   Sizes co=(Sizes)obj;
   return (minWidth==co.minWidth && maxWidth==co.maxWidth && minHeight==co.minHeight && maxHeight==co.maxHeight);
  }

  return false;
 }


}
