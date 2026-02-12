# \# Text Analyzer





## Modularny analizator tekstu z obsługą raportów w wielu formatach (CSV, TXT, JSON, XML)



Text Analyzer to lekki, modularny projekt w Javie służący do analizy tekstu, generowania statystyk, liczenia częstotliwości słów oraz tworzenia raportów w różnych formatach.

Projekt został zaprojektowany w architekturze warstwowej, z wyraźnym podziałem na:

* **app** – klasa z main klasą, obsługa menu
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




### Jak działa analiza?



**TextAnalyzer** wykonuje:



1. *Normalizację* tekstu
2. *Tokenizację* słów
3. *Tokenizację* zdań
4. Obliczenie statystyk *TextStats*
5. Liczenie częstotliwości słów
6. Sortowanie wg *WordSort*






### Wymagania



Java 23+
Pliki wejściowe w katalogu resources/







### Rozszerzanie projektu



Możesz łatwo dodać:



✔ nowy format raportu
Wystarczy stworzyć klasę implementującą Formatter i dodać ją w ReportWriter.



✔ nowe metryki analizy
Dodaj pola do TextStats i logikę w TextAnalyzer.



✔ nowe tryby sortowania
Dodaj wartość w WordSort.

