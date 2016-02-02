[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SimpleArcLoader-green.svg?style=true)](https://android-arsenal.com/details/1/3066)
# SimpleArcLoader 
- bored of seeing the same old Android Loader ? SimpleArcLoader is one thing you should try. 

# Preview 
<img src="https://github.com/generic-leo/SimpleArcLoader/blob/master/preview/simplearcdialog_1.gif" width="30%">
<img src="https://github.com/generic-leo/SimpleArcLoader/blob/master/preview/simplearcdialog_2.gif" width="30%">

# Setup
## Gradle
```groovy
  dependencies {
      compile 'com.leo.simplearcloader:simplearcloader:1.0.+'
  }
```

## Example 1
To show dialog
```java
SimpleArcDialog mDialog = new SimpleArcDialog(this);
mDialog.setConfiguration(new ArcConfiguration(this));
mDialog.show();
```
## Example 2 
Making use of just the Loader
```xml
<com.leo.simplearcloader.SimpleArcLoader
  android:visibility="visible"
  android:id="@+id/loader"
  android:layout_centerInParent="true"
  android:layout_width="60dp"
  android:layout_height="60dp"
  custom:arc_style="simple_arc"
  custom:arc_speed="medium"
  custom:arc_margin="3dp">
</com.leo.simplearcloader.SimpleArcLoader>
```
## Example 3
Customizing Dialog/SimpleArcLoader View using ArcConfiguration 
```java
ArcConfiguration configuration = new ArcConfiguration(context);
configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
configuration.setText("Please wait..");

// Using this configuration with Dialog 
mDialog.setConfiguration(configuration);

// Using this configuration with ArcLoader
mSimpleArcLoader.refreshArcLoaderDrawable(configuration);
```

You can customize Arc/Dialog with ArcConfiguration methods -
- setLoaderStyle(SimpleArcLoader.STYLE mLoaderStyle)
- setArcMargin(int mArcMargin)
- setArcWidthInPixel(int mStrokeWidth)
- setColors(int[] colors)
- setTypeFace(Typeface typeFace)
- setText(String mText)
- setTextColor(int mTextColor)
- setTextSize(int size)
- setAnimationSpeedWithIndex(int mAnimationIndex) 
Values to be passed SimpleArcLoader.SPEED_SLOW, SimpleArcLoader.SPEED_MEDIUM, SimpleArcLoader.SPEED_FAST

Please refer the examples for some of the customization. 

# Developed By
- prathamesh.s1989@gmail.com

# License

  Copyright 2016 Prathamesh Sawant

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

