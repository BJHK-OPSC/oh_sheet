OPSC POE - Timesheet Application
---------------------------------------------
Oh sheet!
----------------------------------------------------------
github link- https://github.com/BJHK-OPSC/oh_sheet

-------------------------------------------------------------------------------
Group - st10082120(group leader), st10082201, st10082256, st10082240

--------------------------------------------------------------------------------------
Gradle must be synced before running the application. 
The gradle sync button looks like an elephant with a blue arrow 
and is located in the top right corner of the anroid studio window.  

Feature 1 - Notifications
-----------------------------------------------------------------------
The application sends the user various notifications once they have 
reached certain milestones or if they have neglected their goals/the application for an extended period. 
This enables the user stay on track and ensures that they are meeting their personal objectives.
It also notifies the user when timer feature starts and ends


Feature 2 - Timer
-----------------------------------------------------------------------
The function works similarly to the timesheet entries. 
This feauture will enable the user to track/time how long the've
spent on a particular task. The timer functions as a replacment for entering 
start and stop times. It tracks the user's work in real time, rather than asking
for user input.


--Requirements--
-----------------------------------------------------------------------
Android Studio Electric Eel | 2022.1.1

minSdk 31

target Sdk 33

------------------------------------------------------------------------
--dependencies--
------------------------------------------------------------------------

implementation 'androidx.core:core-ktx:1.7.0'

implementation 'androidx.appcompat:appcompat:1.6.1'

implementation 'com.google.android.material:material:1.8.0'

implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

testImplementation 'junit:junit:4.13.2'

androidTestImplementation 'androidx.test.ext:junit:1.1.5'

androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
