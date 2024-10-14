# Softwaredev-WS24
Handout Uebung 1 (Modernes Softwaredevelopment WS24)

## Was ist Git und warum sollte es verwendet werden?

[//]: # (Emma)

## Grundlegende Git-Befehle
Git-Befehle steuern und organisieren spezifische Aktionen innerhalb des Versionskontrollsystems **Git**. Sie ermöglichen Entwicklern, den gesamten Entwicklungsprozess von Projekten zu verwalten, indem sie verschiedene Funktionen wie das Verfolgen von Änderungen, die Zusammenarbeit mit anderen und die Verwaltung von Versionen bereitstellen.  
Hier sind einige grundlegende Git-Befehle, ihre Rollen, jeweils mit einem Beispiel:


### 1. `git init`
**Rolle**: Initialisiert ein neues Git-Repository in einem bestehenden Verzeichnis. Es erstellt ein `.git`-Verzeichnis, das alle Informationen über das Repository enthält, sodass Dateien verfolgt werden können.  

**Beispiel**:  
```bash
cd mein-projekt
git init
```
Dies erstellt ein neues Git-Repository im Ordner `mein-projekt`.


### 2. `git clone`
**Rolle**: Erstellt eine lokale Kopie eines bestehenden Git-Repositories.   

**Beispiel**:  
```bash
git clone https://github.com/username/repository.git
```
Dies kopiert das Repository "repository" aus GitHub in ein neues Verzeichnis auf dem Rechner.


### 3. `git status`
**Rolle**: Zeigt, welche Dateien geändert wurden und welche Änderungen zum Commit vorgemerkt sind.

**Beispiel**:   
```bash
git status
```
Dies zeigt die aktuellen Änderungen, die noch nicht zum Commit vorgemerkt sind.


### 4. `git add`
**Rolle**: Fügt Änderungen zur Staging-Area hinzu, sodass sie für den nächsten Commit bereit sind.

**Beispiel**:   
```bash
git add datei.txt
```
Dies fügt die Datei "datei.txt" zur Staging-Area hinzu


### 5. `git commit`
**Rolle**: Speichert die Änderungen, die in der Staging-Area vorgemerkt sind, dauerhaft im Repository.

**Beispiel**:   
```bash
git commit -m "Füge neue Funktionen hinzu"
```
Dies erstellt einen neuen Commit mit der Nachricht "Füge neue Funktionen hinzu".


### 6. `git push`
**Rolle**: Überträgt lokale Commits auf ein Remote-Repository.

**Beispiel**:   
```bash
git push origin main
```
Dies überträgt die Commits auf den `main`-Branch des Remote-Repositories.


### 7. `git pull`
**Rolle**: Holt und integriert Änderungen von einem Remote-Repository in das lokale Repository. 

**Beispiel**:   
```bash
git pull origin main
```
Dies zieht die Änderungen vom `main`-Branch des Remote-Repositories und integriert sie lokal.


### 8. `git branch`
**Rolle**: Listet alle lokalen Branches auf, erstellt oder löscht Branches.  

**Beispiel**:   
```bash
git branch feature-branch
```
Dies erstellt einen neuen Branch namens `feature-branch`.


### 9. `git checkout`
**Rolle**: Wechselt zwischen Branches oder setzt den Zustand von Dateien zurück.  

**Beispiel**:   
```bash
git checkout feature-branch
```
Dies wechselt zum Branch `feature-branch`.


### 10. `git merge`
**Rolle**: Integriert Änderungen von einem Branch in einen anderen.   

**Beispiel**:   
```bash
git merge feature-branch
```
Dies integriert die Änderungen von `feature-branch` in den aktuellen Branch.

### 11. `git log`
**Rolle**: Dient zur Anzeige der Commit-Historie, die nacheinander vorgenommen wurden.

**Beispiel**:   
```bash
git log
```
Zeigt eine Liste der letzten Commits mit Details wie Autor, Datum und Commit-Nachricht.

### 12. `git remote`
**Rolle**: Zeigt alle Remote-Verbindungen an oder erlaubt das Hinzufügen/Entfernen von Remotes.

**Beispiel**:   
```bash
git remote add origin https://github.com/username/repository.git
```
Dies fügt ein Remote-Repository namens `origin` hinzu.

### 13. `git reset`
**Rolle**: Kann verwendet werden, um Commits zurückzusetzen oder die Staging-Area und Arbeitsverzeichnis zu ändern.

**Beispiel**:   
```bash
git reset --hard HEAD~1
```
Dies setzt das Repository auf den Zustand des vorherigen Commits zurück und verwirft alle Änderungen.

### 14. `git diff`
**Rolle**: Vergleicht Änderungen in Dateien oder zwischen Commits, um Unterschiede anzuzeigen.

**Beispiel**:   
```bash
git diff
```
Zeigt die Unterschiede zwischen dem aktuellen Arbeitsverzeichnis und der Staging-Area.  



## Branches und ihre Nutzung, Umgang mit Merge-Konflikten


### 1. Branches und ihre Nutzung
 **Git-Branches** sind ein zentraler Bestandteil des Workflows in Git. Sie ermöglichen es Entwicklern, parallel an verschiedenen *Funktionen*, *Bugfixes* oder *experimentellen Änderungen* zu arbeiten, ohne den Hauptzweig des Projekts zu beeinflussen. Im Nachstehenden erklären wir was Branches sind, wie man sie effektiv nutzt und wie sie die Arbeit in Teams und bei großen Projekten erleichtern.

 #### Was ist ein Git-Branch?
 Ein Git-Branch ist ein separater Entwicklungsstrang, der vom Hauptzweig (oft „`main`“ oder „`master`“) abzweigt. Jeder Branch kann seine eigene Historie haben und unabhängig vom Hauptzweig modifiziert werden.
 Wenn man in einem Branch arbeitet, betreffen die Änderungen nur diesen Branch und beeinflussen nicht den Hauptzweig, solange sie nicht zusammengeführt (*ge-merged*) werden.  
 Ein häufiger Anwendungsfall für Branches ist, dass Teams neue Features entwickeln oder Fehler beheben können, ohne dass der stabile Code im Hauptzweig unterbrochen wird.

 #### Branching-Strategien
 Es gibt verschiedene Strategien, wie Branches in einem Projekt verwendet werden können. Eine der bekanntesten ist das **Git-Flow-Modell**:
 1. **`main`-Branch**: Enthält den stabilen, produktionsreifen Code.
 2. **`develop`-Branch**: Hier findet die aktive Entwicklung statt. Neue Features werden von hier aus verzweigt.
 3. **`Feature`-Branches**: Jedes neue Feature hat einen eigenen Branch, der vom develop-Branch abzweigt.
 4. **`Release`-Branches**: Wenn ein Release vorbereitet wird, wird ein `Release`-Branch von develop erstellt.
 5. **`Hotfix`-Branches**: Wenn ein kritischer Fehler in `main` gefunden wird, kann ein Hotfix-Branch vom `main`-Branch erstellt werden.


### 2. Umgang mit Merge-Konflikten
In Git treten **Merge-Konflikte** auf, wenn du zwei Branches oder Commits zusammenführst und Git nicht automatisch entscheiden kann, wie der Code in einer oder mehreren Dateien integriert werden soll. Diese Konflikte entstehen häufig, wenn mehrere Entwickler an denselben Teilen einer Datei arbeiten und widersprüchliche Änderungen vornehmen.  
Der Umgang mit Merge-Konflikten ist ein wichtiger Bestandteil der Versionskontrolle, und es ist entscheidend, diese Konflikte sorgfältig zu lösen, um die Integrität des Codes zu gewährleisten.

#### Konflikte in den Dateien manuell beheben
Öffne die betroffenen Dateien in einem Texteditor. Git markiert die Konfliktstellen automatisch mit speziellen **Konfliktmarkierungen**, damit der Konflikt leichter erkannt wird.  
**Beispiel**:  
```javascript
<<<<<<< HEAD
// Code aus dem aktuellen Branch (main)
function example() {
    console.log('Hello from main branch!');
}
=======
function example() {
    console.log('Hello from feature-xyz branch!');
}
>>>>>>> feature-xyz
```
- Alles zwischen `<<<<<<< HEAD` und `=======` stammt aus dem Branch, in dem man sich aktuell befindet (z.B. `main`).
- Alles zwischen `=======` und `>>>>>>> feature-xyz` stammt aus dem Branch, den man mergen möchte (z.B. `feature-xyz`).

##### Lösung des Konflikts
Man muss entscheiden, welche Änderungen beibehalten werden sollen. Man kann entweder eine der beiden Versionen behalten oder beide kombinieren.   
**Beispiel**:  
```javascript
function example() {
    console.log('Hello from main branch!');
    console.log('Hello from feature-xyz branch!');
}
```
Lösung durch Kombination beider Änderungen:

##### Änderungen markieren, committen und Merge Abschließen
- Nachdem die Konflikte in allen Dateien behoben wurden, müssen die betroffenen Dateien zum Staging-Bereich hinzufügt werden: `git add src/app.js`.
- Den Status mit `git status` überprüfen. Wenn alle Konflikte gelöst wurden, kann der Merge-Vorgang mit dem Commiten der Änderungen abschloßen werden: `git commit`.
- Nach dem Commit ist der Merge abgeschlossen, und der Konflikt ist behoben. Der Branch kann gepusht werden, um die Änderungen mit dem Remote-Repository zu synchronisieren: `git push origin main`.


#### Tipps zum Umgang mit Merge-Konflikten
1. **Regelmäßig mergen**: Um Merge-Konflikte zu minimieren, sollte regelmäßig den main-Branch in die Feature-Branches gemerged werden, insbesondere wenn mehrere Entwickler parallel arbeiten. Dies hält dem Branch aktuell und reduziert die Anzahl der Konflikte.
2. **Kleine, häufige Commits**: Konflikte sind leichter zu lösen, wenn sie auf wenige Änderungen begrenzt sind. Es ist empfohlen in kleinen, thematisch getrennten Commits, zu arbeiten, um Konflikte besser nachvollziehen zu können.
3. **Branching-Strategie**: Die Verwendung einer klaren Branching-Strategie (z.B. **Git-Flow** oder **GitHub Flow**), um die Entwicklung zu strukturieren und Konflikte zu vermeiden. Neue Features sollten in separaten Branches entwickelt werden, die erst dann mit `main` zusammengeführt werden, wenn sie getestet und bereit für die Produktion sind.
4. **Kommunikation im Team**: Es ist sicher zu stellen, dass alle Entwickler im Team regelmäßig miteinander kommunizieren, um zu vermeiden, dass mehrere Personen an denselben Dateien arbeiten, ohne dies zu wissen.

# Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository

Nachdem die grundlegenden Git-Befehle und der Umgang mit Branches erklärt wurden, wird in diesem Abschnitt die Nutzung von Git in Verbindung mit den Entwicklungsumgebungen IntelliJ und PyCharm behandelt. 
Dies ermöglicht eine effiziente Verwaltung lokaler und entfernter Repositories direkt aus der IDE.

# Git mit IntelliJ/PyCharm benutzen: Lokales und Remote Repository

## Voraussetzungen

Bevor du beginnst, stelle sicher, dass du Folgendes installiert und eingerichtet hast:

- **Git**: Lade Git von [git-scm.com](https://git-scm.com/) herunter und installiere es.
- **IntelliJ** oder **PyCharm**: Wähle die für deine Programmiersprache geeignete IDE von [JetBrains](https://www.jetbrains.com/) und installiere sie.
- **Git-Integration**: Die JetBrains IDEs haben in der Regel Git bereits integriert. Überprüfe in den Einstellungen unter "Version Control > Git", ob der Pfad zur Git-ausführbaren Datei korrekt ist.

## Lokales Repository erstellen

1. **Neues Projekt anlegen**:
 - Öffne IntelliJ IDEA oder PyCharm.
 - Wähle "File > New > Project" aus dem Hauptmenü.
 - Konfiguriere dein Projekt nach Bedarf und klicke auf "Create".

![Beispiel Neues Projekt](https://i.ibb.co/61fYYCp/IJ-Neues-Projekt-small.webp)

*Beispiel: Neues Projekt anlegen*

2. **Git-Initialisierung**:
 - Navigiere zu "VCS > Enable Version Control Integration" im Menü.
 - Wähle "Git" aus dem Dropdown-Menü und bestätige.
- Alternativ kannst du im Terminal-Fenster der IDE `git init` eingeben.

![Beispiel Git](https://i.ibb.co/HNv3VPX/IJ-VCS-Git-s.webp)

*Beispiel: Menüpunkt "VCS > Enable Version Control Integration > Git"*

3. **Erste Dateien hinzufügen und committen**:
- Erstelle oder bearbeite Dateien in deinem Projekt.
- Du hast nun zwei Möglichkeiten zum Committen:

  a) Über die IDE:
  - Öffne das "Commit"-Fenster (Alt + 0 oder ⌘0).
  - Wähle die zu committenden Dateien aus.
  - Gib eine aussagekräftige Commit-Nachricht ein.
  - Klicke auf "Commit" oder "Commit and Push".

  b) Über das Terminal in der IDE:
  - Öffne das Terminal-Fenster in der IDE.
  - Füge Dateien zum Staging-Bereich hinzu mit: 
    - `git add .` (für alle Änderungen) oder 
    - `git add <dateiname>` (für spezifische Dateien).
  - Führe den Commit aus mit: 
    - `git commit -m "Deine aussagekräftige Commit-Nachricht"`.
  - Optional: Pushe die Änderungen mit: 
    - `git push`.

![Beispiel Commit](https://i.ibb.co/rH4n2RH/IJ-commit.webp)

*Beispiel: Commit-Möglichkeit via GUI*
  
## Remote Repository verbinden

1. **Remote Repository erstellen**:
 - Gehe zu GitHub, GitLab oder Bitbucket und erstelle ein neues Repository.
 - Kopiere die URL des Remote-Repositories.

![Beispiel Github Copy Remote](https://i.ibb.co/YfMgDG3/GH-copy-URL.webp)

*Beispiel: Copy GitHub URL*

2. **Remote Repository zum lokalen Projekt hinzufügen**:
 - In IntelliJ/PyCharm, gehe zu "Git > Manage Remotes".
 - Klicke auf das "+"-Symbol, um ein neues Remote hinzuzufügen.
 - Gib "origin" als Name ein und füge die kopierte URL ein.
 - Bestätige mit "OK".

![Beispiel IntelliJ Manage Remotes](https://i.ibb.co/BfGH1Wd/IJ-manage-remotes.webp)

*Beispiel: Remote Repository hinzufügen*

3. **Änderungen pushen**:
 - Wähle "Git > Push" aus dem Menü oder nutze das "Commit"-Fenster.
 - Wähle den Branch aus, den du pushen möchtest (meist "master" oder "main").
 - Klicke auf "Push", um deine Änderungen zum Remote-Repository zu senden.


## Abschlussbemerkung

Die Integration von Git in IntelliJ/PyCharm bietet eine verknüpfte Versionskontrolle direkt in deiner Entwicklungsumgebung. 
Diese Tools vereinfachen den Git-Workflow, indem sie visuelle Unterstützung für Commits, Merges und die Verwaltung von Branches bieten. 
Durch die Nutzung der integrierten Funktionen von IntelliJ/PyCharm kannst du effizienter arbeiten und dich besser auf den Code konzentrieren, während du gleichzeitig von den Vorteilen der Versionskontrolle profitierst.

# Nützliche Git-Tools und Plattormen (z. B. GitHub)

[//]: # (Svetlana, Jesse)

## Aufgabenverteilung

| Aufgaben                                                                  | Bearbeiter |
|---------------------------------------------------------------------------|------------|
| Git Repository auf GitHub anlegen und Team einladen                       | Emma       |
| Was ist Git und warum sollte es verwendet werden?                         | -          |
| Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push)  | Anderson   |
| Branches und ihre Nutzung, Umgang mit Merge-Konflikten                    | Anderson   |
| Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository | Jesse      |
| Nützliche Git-Tools und Plattformen (z. B. GitHub)                        | -          |




