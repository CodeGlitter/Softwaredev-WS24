# AOP-Analyse


### (a) Querschnittsbelange (Cross-cutting Concerns)
Im Projekt CityFeedback, das Funktionen wie Authentifizierung und die Verwaltung von Beschwerden bereitstellt, 
lassen sich folgende Querschnittsbelange identifizieren:

#### 1. Logging
- Protokollierung von Methodenaufrufen, wie z. B. beim Erstellen, Aktualisieren oder Abrufen von Beschwerden.
- Erfassen von Fehlern, um Debugging zu erleichtern und Systemfehler zu analysieren.

#### 2. Performance-Monitoring
- Überwachung der Laufzeitkritischer Operationen wie Authentifizierungsprozesse oder Datenbankabfragen.
- Identifikation von Engpässen in Echtzeit oder durch historische Analyse.

#### 3. Transaktionsmanagement
- Sicherstellung der Datenintegrität bei Operationen, die mehrere Schritte umfassen
(z. B. Speichern einer neuen Beschwerde mit zugehörigen Anhängen in der Datenbank).


### (b) Wiederholende Funktionalitäten
Im Projekt wurden folgende wiederholende Funktionalitäten identifiziert, die durch AOP zentralisiert und optimiert werden könnten:

#### 1. Eingabevalidierung
- Einheitliche Überprüfung von Nutzereingaben wie Beschwerdetexten oder Authentifizierungsdaten.
- Verhinderung von SQL-Injections oder ähnlichen Sicherheitslücken durch zentralisierte Mechanismen.

#### 2. Fehlerbehandlung
- Einheitliches Abfangen und Protokollieren von Ausnahmen, um redundante `try-catch`-Blöcke zu vermeiden.


### (c) Potenzielle AOP-Anwendungsfälle
In unserem Projekt CityFeedback bieten sich folgende konkrete Anwendungsfälle für AOP an:#

#### 1. Logging
- Protokollierung der Methodenaufrufe innerhalb von `AuthenticationService` und beim Anlegen neuer Beschwerden (`ComplaintService`).

#### 2. Performance-Monitoring
- Messung der Antwortzeit von API-Endpunkten, insbesondere bei datenintensiven Vorgängen wie dem Filtern von Beschwerden.

#### 3. Transaktionsmanagement
- Konsistente Verwaltung von Datenbanktransaktionen, insbesondere bei Methoden, die Daten 
schreiben oder aktualisieren, z. B. `createComplaint()` oder `updateComplaint()`
