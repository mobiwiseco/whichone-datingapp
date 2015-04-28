# whichone-datingapp

Whichone is a dating app which you compare people. And you will get notification when someone likes you. Then 
you can add them on facebook or send direct message.

This project developed just for fun. If you want to make it better, please do not hesitate. Feel free to fork , develop and publish.
App is available only android market. Download from [here][0].

#STEP 1 

Download SQL Script from [here][1].
This DB basicly records 3 table.
- Person : keeps user information
- User Matches : Every single match stores in this table. When some new user registered. Stored procedure does its job
  and insert every matches to this table. So matches are not created dynamicly. New user registered, procedure insert 
  every binary combinations to here.
- Match Votes : holds information about; who voted, which match and winner.

#STEP 2

Download web services from [here][2].

You have to update these webservices with your DB information.
Update connection information of every service file.
```php
$con = new mysqli("localhost","USER_HERE","PASSWORD_HERE","DB_HERE");
```

Create new service key and put it ```YOUR_SERVICE_KEY_HERE```
```php
...
if($_POST['key'] == 'YOUR_KEY_IS_HERE'){
...
```

Service key will be used on android development to secure webservices.

#STEP 3
App is using Parse api to push notification. First, you need to get client key and app id from [Parse][3] and 
replace it with below code APP_ID and CLIENT_KEY. Parse is simply let us to use push notification.

Application.class

```java
Parse.initialize(this, "APP_ID", "CLIENT_ID");
```

#STEP 4
You need to register your app to developer facebook. They will give you app_id. Put it to you res/string.xml file.
```xml
<string name="facebook_app_id">app_id_here</string>
```

#STEP 5

Now, we need to add webservice links and webservice key to application. Edit your ApiConstant class with 
your webservice and key information.
```java
public class ApiConstants {

    public static String KEY = "YOUR_SERVICE_KEY_HERE";

    public static String URL_NEW_USER = "____YOUR_BASE_URL_HERE_____/addNewUser.php";
    public static String URL_GET_WINS = "____YOUR_BASE_URL_HERE_____/getMyWins.php";
    public static String URL_GET_NEW_MATCH = "____YOUR_BASE_URL_HERE_____/getNewMatch.php";
    public static String URL_VOTE_MATCH = "____YOUR_BASE_URL_HERE_____/voteMatch.php";
...
```


[0]:https://play.google.com/store/apps/details?id=akilliyazilim.whichone
[1]:https://drive.google.com/file/d/0B7adhz34muiVay13TUtYbWpUbHM/view?usp=sharing
[2]:https://drive.google.com/folderview?id=0B7adhz34muiVfjFvYkd3bnJybkI2dkpTYWE0b1J3MzJpSHRHVXE4Nkd6Tktnclh2U01Wek0&usp=sharing
[3]:https://parse.com/docs
