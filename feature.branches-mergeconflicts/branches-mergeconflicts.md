# Branches und ihre Nutzung, Umgang mit Merge-Konflikten

Ein Branch ermöglicht es, Entwicklungsarbeit getrennt vom Hauptcode durchzuführen und dabei Änderungen unabhängig von anderen Branches vorzunehmen. Dies bietet die Flexibilität, neue Funktionen zu entwickeln, Fehler zu beheben oder Ideen zu testen, ohne den Hauptbranch zu beeinträchtigen. Änderungen von einem Branch können über einen Pull Request in den Hauptbranch integriert werden. Git kann viele Unterschiede automatisch zusammenführen, jedoch erfordert das Auftreten von Konflikten, die nicht automatisch gelöst werden können, manuelles Eingreifen. Solche Mergekonflikte müssen vor dem erfolgreichen Zusammenführen eines Pull Requests behoben werden.

## Branches
Ein Branch ermöglicht Entwicklungsarbeit isoliert durchzuführen, ohne andere Branches im Repository zu beeinflussen. Jedes Repository verfügt über einen Hauptbranch (Standardbranch), und es können zusätzliche Branches erstellt werden. Änderungen von einem Branch können über einen Pull Request in einen anderen Branch zusammengeführt werden.

Branches bieten die Freiheit, neue Features zu entwickeln, Fehler zu beheben oder neue Ideen sicher zu erproben, ohne den Hauptcode zu verändern. Ein neuer Branch wird dabei stets von einem bereits existierenden Branch erstellt, meist vom Hauptbranch. In diesem separaten Bereich kann unabhängig von den Arbeiten anderer Entwickler agiert werden. Branches, die speziell zur Entwicklung neuer Funktionen genutzt werden, bezeichnet man oft als Feature- oder Themen-Branches.

Um einen Branch zu erstellen, einen Pull Request zu öffnen oder Branches im Rahmen eines Pull Requests zu löschen oder wiederherzustellen, sind Schreibrechte im Repository erforderlich.

### Standardbranch
Jedes Repository verfügt über einen ersten einzelnen Branch, auch Hauptbranch oder Standardbranch genannt. Dieser wird oft als **main** oder **master** bezeichnet. Der Standardbranch in einem Repository ist der Basisbranch für neue Pull Requests und Codecommits.

### Feature-Branch
Ein Feature-Branch ist ein speziell für die Entwicklung eines neuen Features oder einer bestimmten Funktion oder zur Behebung eines bestimmten Problems erstellter Entwicklungszweig. Dieser Branch wird typischerweise vom Hauptbranch (oft „main“ oder „master“) abgezweigt und isoliert die Arbeit an einem bestimmten Feature von der restlichen Codebasis. Die Idee hinter einem Feature-Branch ist, dass ein Team neue Funktionen oder Änderungen entwickeln kann, ohne den stabilen und getesteten Code im Hauptbranch zu beeinflussen. Dies ist besonders wichtig in Teams, in denen mehrere Entwickler parallel arbeiten.

### Branches Workflow
Branches können aufgelistet, umbenannt, gelöscht oder auf andere Branches verschoben werden. 

1. Erstellen eines neuen Branches
Bevor du einen neuen Branch erstellst, solltest du sicherstellen, dass du die neueste Version des Hauptbranchs hast. Dazu führst du folgende Befehle aus:

```
git checkout main
git fetch origin
git reset --hard origin/main

```
Mit diesen Befehlen wechselst du zum Hauptbranch, ziehst die neuesten Änderungen aus dem Remote-Repository und setzt deine lokale Kopie auf den neuesten Stand zurück.

2. Änderungen im neuen Branch vornehmen
Für jedes Feature oder jede Aufgabe, an der du arbeitest, solltest du einen separaten Branch verwenden. Dieser Branch wird vom Hauptbranch abgezweigt, um deine Änderungen isoliert zu entwickeln.

```
git checkout -b feature/mein-feature

```
Mit diesem Befehl erstellst du einen neuen Branch namens feature/mein-feature, basierend auf dem aktuellen Stand des Hauptbranchs. Das -b-Flag sorgt dafür, dass der Branch erstellt wird, falls er noch nicht existiert.

3. Änderungen vornehmen, hinzufügen und committen
Nachdem du den neuen Branch erstellt hast, kannst du mit der Entwicklung deines Features beginnen. Jede Änderung wird zunächst zur Staging-Area hinzugefügt und anschließend als Commit gespeichert.

```
git add <datei>
git commit -m "Beschreibung der Änderung"

```
Du kannst mehrere Commits machen, um deine Arbeit in kleinen, nachvollziehbaren Schritten zu dokumentieren.

4. Pullen und Synchronisieren mit dem Standardbranch

Während du an deinem Feature arbeitest, können im Hauptbranch Änderungen vorgenommen werden. Um sicherzustellen, dass deine Arbeit auf dem neuesten Stand ist und Merge-Konflikte vermieden werden, solltest du regelmäßig den Hauptbranch in deinen Feature-Branch integrieren:

5. Den Feature-Branch ins Remote-Repository pushen

Wenn du mit der Entwicklung fertig bist oder einen Zwischenstand sichern möchtest, solltest du den Feature-Branch ins Remote-Repository pushen. Dies ermöglicht es anderen Entwicklern, deinen Fortschritt zu sehen, und dient gleichzeitig als Backup.

```
git push -u origin feature/mein-feature
```
Der -u-Parameter stellt eine Verbindung zu einem Remote-Tracking-Branch her, sodass du zukünftig einfach git push verwenden kannst, um deine Änderungen zu synchronisieren.

6. Erstellen eines Pull Requests
Sobald dein Feature fertig entwickelt und getestet ist, kannst du einen Pull Request erstellen. Ein Pull Request dient dazu, den Code von anderen Entwicklern überprüfen zu lassen, bevor er in den Hauptbranch gemergt wird. In GitHub oder anderen Plattformen wie GitLab oder Bitbucket kannst du Reviewer hinzufügen und Kommentare oder Änderungswünsche einholen.

7. Feedback bearbeiten
Deine Teamkollegen können Feedback zu deinem Pull Request geben. Änderungen, die vorgeschlagen werden, kannst du lokal vornehmen, committen und dann erneut pushen. Die aktualisierten Änderungen erscheinen automatisch im Pull Request und werden weiter überprüft.

8. Den Pull Request mergen
Nachdem dein Pull Request genehmigt wurde, kann der Branch in den Hauptbranch gemergt werden. Dieser Schritt erfolgt in der Regel über das Repository-Management-Tool (z.B. GitHub). In manchen Fällen können dabei Merge-Konflikte auftreten, die gelöst werden müssen. Das passiert, wenn im Hauptbranch und im Feature-Branch dieselben Codebereiche verändert wurden.

Nach erfolgreichem Merge wird dein Feature Teil des Hauptbranches.

9. Löschen des Feature-Branches
Nachdem dein Feature erfolgreich in den Hauptbranch gemergt wurde, kannst du den Feature-Branch löschen, da er nun nicht mehr benötigt wird. Dies hilft, die Branch-Struktur im Repository sauber zu halten.

```
git branch -d feature/mein-feature
git push origin --delete feature/mein-feature
```
Durch das Löschen des Branches wird er sowohl lokal als auch im Remote-Repository entfernt.


### Best Practices für Umgang mit Branches
* **Arbeiten in isolierten Branches**:
Jede neue Aufgabe (Feature, Bugfix, Experiment) sollte in einem eigenen Branch durchgeführt werden. Dies verhindert Konflikte und erlaubt parallele Arbeit ohne Auswirkungen auf den Hauptbranch.
* **Regelmäßiges Pullen der aktuellen Änderungen**:
Bevor du mit der Arbeit an einem neuen Branch beginnst, solltest du sicherstellen, dass du die neuesten Änderungen des Hauptbranches heruntergeladen hast (git pull), um Konflikte zu minimieren.
* **Kleinere, zusammenhängende Änderungen**:
Vermeide riesige Commits mit vielen Änderungen. Stattdessen sollten Änderungen auf kleine, zusammenhängende Schritte aufgeteilt werden. Das erleichtert das Überprüfen des Codes und die Fehlersuche.


## Mergekonflikte
In vielen Fällen kann Git Unterschiede zwischen Branches automatisch zusammenführen, besonders wenn die Änderungen in unterschiedlichen Zeilen oder Dateien vorgenommen wurden. Dadurch sind diese Zusammenführungen meist problemlos und erfordern kein Eingreifen. Es kann jedoch vorkommen, dass konkurrierende Änderungen auftreten, die Git nicht eigenständig auflösen kann. Ein Mergekonflikt entsteht beispielsweise, wenn mehrere Personen dieselbe Zeile in einer Datei ändern oder wenn eine Datei gleichzeitig geändert und gelöscht wird.
Bevor ein Pull Request auf GitHub gemergt werden kann, müssen alle Mergekonflikte manuell gelöst werden. Wenn ein Mergekonflikt zwischen dem Vergleichsbranch und dem Basisbranch besteht, wird eine Liste der betroffenen Dateien oberhalb der Schaltfläche „Pull Request mergen“ angezeigt. Diese Schaltfläche bleibt deaktiviert, bis alle Konflikte behoben sind und eine problemfreie Zusammenführung möglich ist.

### Häufige Ursachen von Merge-Konflikten
### Workflow zur Lösung von Mergekonflikten
### Best Practices für Umgang mit Merge-Konflikten


