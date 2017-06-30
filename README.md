  # Owa-Notifier

This application throw notification when number of unread mail change in a Office365 inbox.

It use oauth2 to init credidential.

# Screenshot
![screenshot-notification](https://raw.githubusercontent.com/matgou/owa-notifier/master/src/main/resources/screenshot-notification.png "Screenshot Notification")
![screenshot-tray](https://raw.githubusercontent.com/matgou/owa-notifier/master/src/main/resources/screenshot-tray.png "Screenshot tray-icon")
# Minimum requirement :
 * java 1.6
 * Desktop environnement
 * Linux or Windows
 
# Build with maven : 

```bash
mvn clean package
```

# Run

```bash
java -jar target\OwaNotifier-jar-with-dependencies.jar
```