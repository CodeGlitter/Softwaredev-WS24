# Softwaredev-WS24
Handout Uebung 1 (Modernes Softwaredevelopment WS24)



## Grundlegende Git-Befehle
Git-Befehle steuern und organisieren spezifische Aktionen innerhalb des Versionskontrollsystems **Git**. Sie ermöglichen Entwicklern, den gesamten Entwicklungsprozess von Projekten zu verwalten, indem sie verschiedene Funktionen wie das Verfolgen von Änderungen, die Zusammenarbeit mit anderen und die Verwaltung von Versionen bereitstellen.  
Hier sind einige grundlegende Git-Befehle, ihre Rollen, jeweils mit einem Beispiel:


### 1. `git init`
**Rolle**: Initialisiert ein neues Git-Repository in einem bestehenden Verzeichnis. Es erstellt ein `.git`-Verzeichnis, das alle Informationen über das Repository enthält, sodass Dateien verfolgt werden können.  

**Beispiel**:  
![image](https://github.com/user-attachments/assets/46d37c81-5296-41e4-80be-25774f151b87)  
Dies erstellt ein neues Git-Repository im Ordner `mein-projekt`.


### 2. `git clone`
**Rolle**: Erstellt eine lokale Kopie eines bestehenden Git-Repositories.   

**Beispiel**:  
![image](https://github.com/user-attachments/assets/e67fb9ea-1c74-4fb1-b7d8-5e7b41a4bd1c)  
Dies kopiert das Repository "repository" aus GitHub in ein neues Verzeichnis auf dem Rechner.


### 3. `git status`
**Rolle**: Zeigt, welche Dateien geändert wurden und welche Änderungen zum Commit vorgemerkt sind.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/c2eb91f9-5907-4436-b09d-5f4e5e3df227)  
Dies zeigt die aktuellen Änderungen, die noch nicht zum Commit vorgemerkt sind.


### 4. `git add`
**Rolle**: Fügt Änderungen zur Staging-Area hinzu, sodass sie für den nächsten Commit bereit sind.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/d100f4ab-a650-4ece-a537-7d85eec43cce)  
Dies fügt die Datei "datei.txt" zur Staging-Area hinzu


### 5. `git commit`
**Rolle**: Speichert die Änderungen, die in der Staging-Area vorgemerkt sind, dauerhaft im Repository.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/f0a0a635-91a9-4b5f-98c6-f88a8a434aba)  
Dies erstellt einen neuen Commit mit der Nachricht "Füge neue Funktionen hinzu".


### 6. `git push`
**Rolle**: Überträgt lokale Commits auf ein Remote-Repository.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/6d32fd4b-9eea-4b12-a065-adfa4ab6dbf7)  
Dies überträgt die Commits auf den `main`-Branch des Remote-Repositories.


### 7. `git pull`
**Rolle**: Holt und integriert Änderungen von einem Remote-Repository in das lokale Repository. 

**Beispiel**:   
![image](https://github.com/user-attachments/assets/00ada60b-5b2a-4970-88d0-1470c0d7c8fc)  
Dies zieht die Änderungen vom `main`-Branch des Remote-Repositories und integriert sie lokal.


### 8. `git branch`
**Rolle**: Listet alle lokalen Branches auf, erstellt oder löscht Branches.  

**Beispiel**:   
![image](https://github.com/user-attachments/assets/8d60bd77-446f-4026-9d6a-fa400bac96f8)  
Dies erstellt einen neuen Branch namens `feature-branch`.


### 9. `git checkout`
**Rolle**: Wechselt zwischen Branches oder setzt den Zustand von Dateien zurück.  

**Beispiel**:   
![image](https://github.com/user-attachments/assets/a5df942a-92c6-40ae-850a-0b7d64ea63d4)  
Dies wechselt zum Branch `feature-branch`.


### 10. `git merge`
**Rolle**: Integriert Änderungen von einem Branch in einen anderen.   

**Beispiel**:   
![image](https://github.com/user-attachments/assets/2e04a626-4ddf-491f-970e-e9e132ad887b)  
Dies integriert die Änderungen von `feature-branch` in den aktuellen Branch.

### 11. `git log`
**Rolle**: Dient zur Anzeige der Commit-Historie, die nacheinander vorgenommen wurden.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/ca033b2e-b28a-4d5f-bac1-c3b637f5f48f)  
Zeigt eine Liste der letzten Commits mit Details wie Autor, Datum und Commit-Nachricht.

### 12. `git remote`
**Rolle**: Zeigt alle Remote-Verbindungen an oder erlaubt das Hinzufügen/Entfernen von Remotes.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/16be6430-84a7-47be-9102-66bbf1b55b1b)  
Dies fügt ein Remote-Repository namens `origin` hinzu.

### 13. `git reset`
**Rolle**: Kann verwendet werden, um Commits zurückzusetzen oder die Staging-Area und Arbeitsverzeichnis zu ändern.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/e4d669db-4a9b-48d7-9888-d9cc4471caad)  
Dies setzt das Repository auf den Zustand des vorherigen Commits zurück und verwirft alle Änderungen.

### 14. `git diff`
**Rolle**: Vergleicht Änderungen in Dateien oder zwischen Commits, um Unterschiede anzuzeigen.

**Beispiel**:   
![image](https://github.com/user-attachments/assets/55f60573-61d9-4309-a2c3-98a5805d4426)  
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
![image](https://github.com/user-attachments/assets/9576126e-6a62-42ad-9cfd-7160fad86616)  
- Alles zwischen `<<<<<<< HEAD` und `=======` stammt aus dem Branch, in dem man sich aktuell befindet (z.B. `main`).
- Alles zwischen `=======` und `>>>>>>> feature-xyz` stammt aus dem Branch, den man mergen möchte (z.B. `feature-xyz`).

##### Lösung des Konflikts
Man muss entscheiden, welche Änderungen beibehalten werden sollen. Man kann entweder eine der beiden Versionen behalten oder beide kombinieren.   
**Beispiel**:  
![image](https://github.com/user-attachments/assets/b3792334-2a10-4cd5-b9a4-87f762835ba2)  
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
