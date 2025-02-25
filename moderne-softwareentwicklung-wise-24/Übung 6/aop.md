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


# LLM-Einsatz für AOP

### (a) Einsatz von GitHub Copilot für AOP

#### 1. Identifikation von Cross-cutting Concerns
GitHub Copilot wurde genutzt, um potenzielle Querschnittsbelange im Projekt zu identifizieren. Basierend auf Codeanalyse und Kommentaren schlug Copilot vor:
- Logging für zentrale Methoden wie createComplaint() und authenticateUser() zu integrieren.
- Performance-Monitoring in allen Endpunkten zu implementieren.
- Standardisierte Fehlerbehandlung zentral zu verwalten.

#### 2. Entwicklung von Aspekten
Mit Copilot wurde die Implementierung von Aspekten effizient unterstützt:
- Vorschläge für Annotation-basierte Aspects wurden automatisch generiert, z. B. für Logging (`@LogExecution`) und Transaktionsmanagement (`@Transactional`).
- Copilot half bei der Erstellung der Logging-Aspekte mit detaillierten Vorschlägen für die `@Around`-Advice-Methoden
- Es wurden auch Boilerplate-Codes für `Pointcuts` und Aspektklassen generiert, die nur minimal angepasst werden mussten.

#### 3. Optimierung der Implementierung
Copilot unterstützte die Optimierung bestehender Aspekte:
- Vorschläge zur Vereinheitlichung von Logging-Mechanismen mit Tools wie **SLF4J**.
- Optimierung von Pointcuts, um nur relevante Methoden (z. B. in `service.*`-Paketen) zu adressieren.
- Empfehlungen für Performance-Monitoring mit Zeitmessung und detaillierten Reports.


### (b) Reflektion über den LLM-Einsatz

#### Nützlichkeit
Copilot hat den Entwicklungsprozess erheblich beschleunigt, indem es wiederholte Muster erkannte, Boilerplate-Codes lieferte und Vorschläge für Pointcuts und Advices machte. Dies reduzierte die Fehleranfälligkeit und steigerte die Produktivität.

#### Herausforderungen und Lösungen
- **Herausforderung**: Copilot schlug gelegentlich nicht-relevante oder zu allgemeine Aspekte vor.
- **Lösung**: Manuelle Filterung und Präzisierung der generierten Vorschläge durch gezielte Eingabe von Kommentaren und Codekontext.

#### Learnings für zukünftige Entwicklungen
- Die Dokumentation von Querschnittsbelangen im Code hilft, präzisere Vorschläge von Copilot zu erhalten.
- Kombination mit bestehenden AOP-Frameworks (z. B. Spring AOP) verstärkt die Effektivität der Vorschläge.
