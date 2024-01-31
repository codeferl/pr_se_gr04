Frontend
1. Installation von Grafana (nach belieben, z.B. Docker)
2. Bei Grafana rechts oben "+" drücken
3. "Import" wählen
4. JSON file hochladen
5. "Import" auswählen
6. Datenquelle verbinden unter "Menü" -> "Connections"
7. Achtung: Das Board ist auf PostgreSQL DBs ausgelegt

Backend 
1. Git Repository klonen / herunterladen.
2. ADR Manager in IDE einbinden. WICHTIG: IDE muss Spring Boot untersützen. Diese Projekt wurde daher mittels IntelliJ realisiert.
3. Alle fehlenden Spring Boot und Spring Data Features installieren und aktivieren. IntelliJ unterstütz den Anwender dabei.
4. Im SRC Ordner die Datei 'application properties' auswählen. Hier den Speicherpfad zur Postgres Datenbank sowie die Zugangsdaten eintragen.
5. ADR Manager ausführen. 
