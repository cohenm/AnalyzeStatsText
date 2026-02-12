# \# Text Analyzer





## Modularny analizator tekstu z obsługą raportów w wielu formatach (CSV, TXT, JSON, XML)



Text Analyzer to lekki, modularny projekt w Javie służący do analizy tekstu, generowania statystyk, liczenia częstotliwości słów oraz tworzenia raportów w różnych formatach.

Projekt został zaprojektowany w architekturze warstwowej, z wyraźnym podziałem na:

* **core** – logika analizy tekstu
* **model** – struktury danych
* **io** – zapis i formatowanie raportów
* **ui** – interakcja z użytkownikiem
* **util** – narzędzia pomocnicze

Całość jest w pełni udokumentowana i łatwa do rozszerzenia.





### Funkcjonalności



**Analiza tekstu i plików:**

* liczba słów
* liczba znaków (ze spacjami i bez)
* liczba zdań



**Liczenie częstotliwości słów:**

* filtrowanie stop‑words
* minimalna długość słowa
* sortowanie wg `WordSort`



**Generowanie raportów w formatach:**

* CSV
* TXT
* JSON
* XML



Zapis raportów do plików z automatycznym tworzeniem katalogów

Interaktywne UI w konsoli

Czytelne formatowanie wyników





### Struktura katalogów


```
src/

└── main/

└── java/

└── com/cohenm/analyzer/

├── core/

│   └── TextAnalyzer.java

│

├── model/

│   ├── TextStats.java

│   ├── WordCount.java

│   └── WordSort.java

│

├── io/

│   ├── FileReader.java

│   ├── ReportWriter.java

│   ├── builder/

│   │   └── ReportBuilder.java

│   ├── util/

│   │   └── Escape.java

│   │   └── SortUtil.java

│   │   └── TimeUtil.java

│   └── format/

│       ├── Formatter.java

│       ├── CsvFormatter.java

│       ├── JsonFormatter.java

│       ├── TxtFormatter.java

│       └── XmlFormatter.java

│

├── ui/

│   ├── UserInput.java

│   ├── StatsPrinter.java

│   └── ReportSaver.java
```



### Jak działa analiza?



**TextAnalyzer** wykonuje:



1. *Normalizację* tekstu
2. *Tokenizację* słów
3. *Tokenizację* zdań
4. Obliczenie statystyk *TextStats*
5. Liczenie częstotliwości słów
6. Sortowanie wg *WordSort*





### Przykład użycia (konsola)





#### Podstawowe statystyki



```
=== STATYSTYKI ===

Słowa: 123

Znaki (ze spacjami): 789

Znaki (bez spacji): 654

Zdania: 7
```





#### Top 10 słów



```
=== TOP 10 słów — sortowanie: FREQUENCY\\\_DESC ===


lorem                : 15
ipsum                : 12
dolor                : 9
...
```



#### Fragment częstotliwości



=== Częstotliwości (pierwsze 50) ===

```
lorem                : 15

ipsum                : 12

...

(razem: 342)
```





#### Przykładowy raport JSON



```
json
{
\\\&nbsp; "type": "basic\\\\\\\_stats",
\\\&nbsp; "generatedAt": "2025-01-01T12:00:00+01:00",
\\\&nbsp; "stats": {
\\\&nbsp;   "words": 123,
\\\&nbsp;   "charsWithSpaces": 789,
\\\&nbsp;   "charsWithoutSpaces": 654,
\\\&nbsp;   "sentences": 7
\\\&nbsp; }
}
```





#### Zapis raportu



**Raporty zapisywane są automatycznie do katalogu:**



output/



**Przykład:**



```
output/example-basic\_stats.json

```





### Wymagania



Java 23+
Pliki wejściowe w katalogu resources/





### Uruchomienie



**1. Skompiluj projekt**



`mvn clean package`

lub

`javac -d out $(find src -name "\\\\\\\*.java")`



**2. Uruchom aplikację**



Jeśli masz klasę Main:



`java -cp out com.cohenm.analyzer.Main`





### Rozszerzanie projektu



Możesz łatwo dodać:



✔ nowy format raportu
Wystarczy stworzyć klasę implementującą Formatter i dodać ją w ReportWriter.



✔ nowe metryki analizy
Dodaj pola do TextStats i logikę w TextAnalyzer.



✔ nowe tryby sortowania
Dodaj wartość w WordSort.

