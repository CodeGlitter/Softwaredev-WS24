# Dokumentation - Gruppe 1 

# Task 1 - Review Der Implementierung
## Entwicklungsstrategie
Unsere Implementierungsstrategie wird weiterhin auf den Prinzipien des *Domain-Driven Design (DDD)* und *Test-Driven Development (TDD)* basieren. Hierzu haben wir eine klare Struktur mit spezifischen Verzeichnissen für **Tests**, **Domänen**, **Repositories**, **Services** und **Exceptions** im Projekt angelegt.
Im Zuge der Überprüfung unserer Strategie haben wir festgestellt, dass eine **Benutzerregistrierung und -anmeldung** eine wesentliche Funktion für unsere Anwendung darstellt, da nur registrierte Nutzer Beschwerden zuordnen und bearbeiten können. Die *Bürgerauthentifizierung* war ursprünglich nicht als Domain-Event in unserem Bounded Context vorgesehen, stellt sich jedoch als zentrales Event für die CityFeedback-Webanwendung heraus.
In unserer initialen Konzeption hatten wir bereits ein `AuthenticationRepository` und eine `AuthenticationException` definiert. Die Überprüfung ergab jedoch zwei wichtige Erkenntnisse:
 
**1.** Mit `Spring Boot Security` entfällt die Notwendigkeit eines eigenen `AuthenticationRepository`, da die grundlegenden Authentifizierungsfunktionen wie `tokenValidation`, `signIn` und `signUp` bereits in der `SecurityConfig` durch den `DaoAuthenticationProvider` von Spring Boot Security bereitgestellt werden. 

**2.** Spring Boot Security enthält zudem eine eigene `AuthenticationException`, wodurch eine eigene Implementierung überflüssig wird.

## Die wichtigsten Domain-Events
Für den aktuellen Entwicklungsabschnitt haben wir die Domain-Events wie folgt definiert: 

**1.** **Authentifizierung** der Nutzer 

**2.** **Beschwerde-Verwaltung für Bürger**, insbesondere das Erstellen, Bearbeiten und Anzeigen von Beschwerden.


# Task 5 - Modularität und CI/CD Pipeline
## Wie sind Modularität und Testbarkeit in ihrem Code umgesetzt
In unserem Projekt haben wir die Modularität und Testbarkeit sichergestellt, indem wir die Struktur nach *Domain-Driven Design (DDD)* gegliedert und eine saubere Trennung zwischen den Bounded Contexts geschaffen haben. 

Die Testbarkeit unseres Codes wird durch die konsequente Anwendung von **Mocking** und **Dependency Injection** weiter unterstützt. In den Unit-Tests, z. B. für den `AuthenticationService`, werden mit `@Mock-Annotationen` Abhängigkeiten wie das `UserRepository` und `PasswordEncoder` gemockt, um isolierte Tests durchzuführen. Dies ermöglicht es, spezifische Testfälle zu prüfen, wie etwa die erfolgreiche Speicherung eines neuen Benutzers oder die Validierung von fehlerhaften Eingaben. Im UserRegistrationControllerTest verwenden wir `@WebMvcTest`, um den Controller isoliert vom gesamten Spring-Kontext zu testen. Hier sorgt `MockMvc` dafür, dass HTTP-Anfragen simuliert werden können, während `@MockBean` sicherstellt, dass Abhängigkeiten wie der UserService und das UserRepository gemockt werden, sodass nur der Controller getestet wird.

### Fragestellung: wie die CI-Pipeline (inkl. Tests) aufgesetzt wurde.

# Task 6 - Reflektion zu TDD und DDD
Die Anwendung von *Test-Driven Design (TDD)* und *Domain-Driven Design (DDD)* hat unsere Entwicklung in mehrfacher Hinsicht positiv beeinflusst. Durch TDD wurde der Entwicklungsprozess strukturierter und gezielter, da wir bereits vor dem Schreiben der eigentlichen Funktionen Tests erstellt haben. Das führte dazu, dass der Code von Anfang an besser strukturiert, klar verständlich und weniger fehleranfällig war. Die iterative Entwicklung ermöglichte es uns, Fehler frühzeitig zu erkennen und zu beheben, was die Wartbarkeit des Codes erheblich verbessert hat. 

DDD half uns, die Anwendungslogik und -struktur auf die Bedürfnisse der Domäne auszurichten. Die Verwendung von klar definierten Domänenmodellen und Bounded Contexts sorgte dafür, dass der Code 
nicht nur technisch korrekt, sondern auch konzeptionell präzise war. Dadurch war die Zusammenarbeit im Team einfacher, da alle ein gemeinsames Verständnis der Kernelemente teilten.   

Herausfordernd waren die initiale Planung und die konsequente Disziplin, TDD und DDD durchgehend anzuwenden, da sie eine präzise Analyse und Modellierung erfordern. Insgesamt haben TDD und DDD jedoch zu einem robusteren und besser wartbaren System geführt, das den Domänenanforderungen gerecht wird.


### ...

# Task 7 - Einsatz von Large Language Models (LLM)

## Debugging-Unterstützung

## Optimierungen

## Konkrete Verbesserungen

## Dokumentation des LLM-Einsatzes
> **Referenz zum Bachelorkurs Softwaretechnik**: Die Nutzung von LLMs orientiert sich an den Erkenntnissen aus dem Kurs, insbesondere Kapitel 1.6. Softwaretechnik und Generative AI und den vier Haupteinsatzbereichen von GenAI in der Softwareentwicklung: Programmierung, Erstellung von Artefakten, Analyse von Artefakten und Routineaufgaben. Wie in Mollicks "Co-Intelligence: Living and Working with AI" beschrieben, wurden die LLMs als intelligente Assistenten im gesamten Entwicklungsprozess eingesetzt.

Für die Entwicklung wurden verschiedene LLM-Tools strategisch eingesetzt:

- **Claude.ai 3.5 Sonnet (New)**: Unterstützte bei komplexen Architekturentscheidungen und Code-Reviews aufgrund der starken analytischen Fähigkeiten und des tiefen Verständnisses für Software-Design-Prinzipien.

- **ChatGPT 4o**: Half bei allgemeinen Programmierherausforderungen, Debugging und der Generierung von Testfällen. Besonders wertvoll für schnelle Lösungsvorschläge und Best Practices.

- **Tabnine**: Als IntelliJ IDE-Integration ermöglichte es effizienteres Coding durch kontextbezogene Code-Vervollständigung und intelligente Vorschläge direkt im Entwicklungsworkflow. Dies beschleunigte insbesondere das Schreiben von boilerplate Code.

Die Kombination dieser Tools optimierte den Entwicklungsprozess durch Zeiteinsparung bei gleichzeitiger Qualitätssicherung. Während Claude.ai und ChatGPT für strategische und konzeptionelle Unterstützung genutzt wurden, diente Tabnine der taktischen Entwicklungsunterstützung.
