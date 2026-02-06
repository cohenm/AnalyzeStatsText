AnalyzeStatsText, budowane w IntelliJ IDEA, z JDK 23.
Kroki:
-- commit 1 -> struktura katalogów, 'hello world
-- commit 2 -> lekka zmiana metody countWords() z wykorzystaniem ArrayList<String>
-- commit 3 -> czasowe przeniesienie metod do osobnego pliku
-- commit 4 -> czytanie plików tekstowych z lokacji zasobów
-- commit 5 -> Budowa core/ analizatorów: słowa, znaki, rekord TextStats, usunięcie statycznych metod
-- commit 6 -> Dodanie nowego interfejsu, implementacji, możliwości liczenia zdań.
-- commit 7 -> dodanie skanera i menu, nowe funkcje statystyk
-- commit 8 -> dodane zapisy do pliku w wybranych formatach
-- commit 9 -> dodaj sortowanie "n najpopularniejszych słów" do zestawu - alfabetycznie oraz rosnąco i malejąco wg liczby wtstąpień
-- commit 10.3 -> rozbicie TextMenu, powstały nowe klasy w UI, refactor util,
-- commit 10.2 -> refaktoryzacja ReportWriter na mniejsze moduły builder, format i util
-- commit 10.1 -> uproszczenie TextAnalyzer

Struktury katalogów

docs/
resources/
src/
└── com/
    └── cohenm/
        └── analyzer/
        └── analyzer/
            ├── app/
            ├── core/
            ├── io/
                └── builder/
                └── format/
            ├── model/
            ├── ui/
            └── util/
test/
└── com/
    └── cohenm/
        └── analyzer/
            ├── app/
            ├── core/
            ├── io/
                └── builder/
                └── format/
            ├── model/
            ├── ui/
            └── util/
            
            
Dostępne release v.0.1 w zakładce release, plik jar.
uruchamiany: java -jar AnalyzeStatsText.jar
