
Användes för att skapa något överhuvudtaget.
https://developer.android.com/training/basics/firstapp/index.html 

Kollade upp lite olika tutorials för hur man skulle ändra färger och liknande, och hur man rundade av knapparna. Kopierade i stort sett bara koden från denna hemsidan för det:
https://stackoverflow.com/questions/9334618/rounded-button-in-android

För att göra knappar kollade jag mycket i androids “my first app” och för att sedan göra accelereometern kollade jag en del på https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125 samt https://developer.android.com/reference/android/hardware/SensorManager.html
https://developer.android.com/reference/android/hardware/SensorEvent.html 

Blev lite omskrivet, men en del kopiering eftersom metoderna är väldigt lika varandra alltid. 
onSensorChanged var dock ganska mycket eget tänk för att få appen att inte krascha, med användning av sensorEvents “values” och setText från myApp.

För att göra Kompassen använde jag mig en del av det jag gjort i accelerometern.
https://developer.android.com/guide/topics/sensors/sensors_position.html Använde jag för vilken sensor som behövdes.
https://www.javacodegeeks.com/2013/09/android-compass-code-example.html
Användes för att röra kompassbilden.
https://www.ssaurel.com/blog/learn-how-to-make-a-compass-application-for-android/
Användes för att ta reda på riktningen.
Använde i slutändan mycket kod från:
https://www.wlsdevelop.com/index.php/en/blog?option=com_content&view=article&id=38
Då koden inte fungerade som den skulle. Det blev alltså väldigt mycket kopiering i slutet, där start och onSensorChanged togs nästan direkt av, med några få ändringar i onSensorChanged.
