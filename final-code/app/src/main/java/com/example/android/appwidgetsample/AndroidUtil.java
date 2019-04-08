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

import android.content.res.Resources;
import android.util.DisplayMetrics;


public class AndroidUtil
{

 public static float pixels2Dp(float px)
 {
  DisplayMetrics metrics=Resources.getSystem().getDisplayMetrics();
  float dp=px/(metrics.densityDpi/160f);
  return Math.round(dp);
 }

 public static float dp2Pixels(float dp)
 {
  DisplayMetrics metrics=Resources.getSystem().getDisplayMetrics();
  float px=dp*(metrics.densityDpi/160f);
  return Math.round(px);
 }


 private int convertDpToPx(int dp)
 {
  return Math.round(dp*(Resources.getSystem().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));

 }

 private int convertPxToDp(int px)
 {
  return Math.round(px/(Resources.getSystem().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
 }

}
