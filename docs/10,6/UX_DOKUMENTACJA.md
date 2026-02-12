# Dokumentacja UX - Text Analyzer

## 1. Przegląd ogólny

Text Analyzer to konsolowa aplikacja do analizy tekstu z interfejsem menu-driven. Użytkownik pobiera plik tekstowy i wykonuje różne operacje analityczne poprzez menu interaktywne.

---

## 2. Ekrany i przepływy

### 2.1 Ekran startowy

```
┌─────────────────────────────────────┐
│  TEXT ANALYZER - Ekran startowy     │
├─────────────────────────────────────┤
│                                     │
│  Podaj bazową nazwę pliku          │
│  (bez .txt): [_____________]       │
│                                     │
└─────────────────────────────────────┘
```

**Akcje:**
- Użytkownik wpisuje nazwę pliku (np. `dokument`)
- Aplikacja ładuje plik `dokument.txt` z bieżącego katalogu
- Przejście do menu głównego

---

### 2.2 Menu główne

```
┌─────────────────────────────────────┐
│  === MENU ===                       │
├─────────────────────────────────────┤
│  1. Statystyki podstawowe           │
│  2. Top N słów                      │
│  3. Częstość w fragmencie           │
│  4. Zmień min. długość słowa        │
│  5. Toggleuj stop-words             │
│  6. Zapisz raport (basic)           │
│  7. Zapisz raport (full)            │
│  8. Zapisz raport (frequency)       │
│  0. Wyjście                         │
│                                     │
│  Wybór: [_]                         │
│                                     │
└─────────────────────────────────────┘
```

**Cechy interfejsu:**
- Przejrzyste numerowanie opcji
- Stały powtarzający się widok menu
- Walidacja wyboru (nieznana opcja = błąd + powrót do menu)

---

## 3. Przepływy funkcjonalności

### 3.1 Opcja 1: Statystyki podstawowe

```
Menu Główne
    ↓
[Użytkownik wybiera 1]
    ↓
Analiza tekstu
    ↓
┌──────────────────────┐
│ Liczba słów: 1250    │
│ Liczba znaków: 8423  │
│ Liczba zdań: 45      │
└──────────────────────┘
    ↓
Powrót do Menu
```

**Ustawienia:**
- Brak wpływu ustawień
- Zawsze pokazuje pełne statystyki

---

### 3.2 Opcja 2: Top N słów

```
Menu Główne
    ↓
[Użytkownik wybiera 2]
    ↓
Poproszenie o N
│ Podaj N: [_]
    ↓
Sprawdzenie ustawień
┌────────────────────────────────┐
│ Min. długość słowa: 3          │
│ Stop-words włączone: TAK       │
└────────────────────────────────┘
    ↓
Filtrowanie i sortowanie słów
    ↓
┌──────────────────────────┐
│ Top N słów:              │
│ 1. słowo1: 45 razy      │
│ 2. słowo2: 38 razy      │
│ 3. słowo3: 32 razy      │
│ ...                      │
└──────────────────────────┘
    ↓
Powrót do Menu
```

**Ustawienia wpływające:**
- Minimalna długość słowa
- Włączenie/wyłączenie stop-words

---

### 3.3 Opcja 3: Częstość w fragmencie

```
Menu Główne
    ↓
[Użytkownik wybiera 3]
    ↓
Podaj fragment tekstu
│ Fragment: [____________________]
    ↓
Analiza fragmentu
    ↓
┌──────────────────────┐
│ Słowa w fragmencie:  │
│ słowo1: 5 razy      │
│ słowo2: 3 razy      │
│ słowo3: 2 razy      │
└──────────────────────┘
    ↓
Powrót do Menu
```

---

### 3.4 Opcja 4: Zmień min. długość słowa

```
Menu Główne
    ↓
[Użytkownik wybiera 4]
    ↓
Podaj nową min. długość
│ Nowa min. długość: [_]
    ↓
✅ Ustawienie zmienione
    ↓
Powrót do Menu
```

**Efekt:**
- Wpływa na Opcję 2 i 3
- Dotyczy filtrowania słów w analizach

---

### 3.5 Opcja 5: Toggleuj stop-words

```
Menu Główne
    ↓
[Użytkownik wybiera 5]
    ↓
Zmiana stanu
┌────────────────────────┐
│ Stop-words: ON → OFF   │
└────────────────────────┘
  lub
┌────────────────────────┐
│ Stop-words: OFF → ON   │
└────────────────────────┘
    ↓
Powrót do Menu
```

**Efekt:**
- Włącza/wyłącza filtrowanie stop-words
- Wpływa na wyniki w Opcjach 2 i 3

---

### 3.6 Opcje 6-8: Zapisz raporty

```
Menu Główne
    ↓
[Użytkownik wybiera 6/7/8]
    ↓
Podaj nazwę pliku
│ Nazwa: [______________]
    ↓
Generowanie raportu
    ↓
Wybór formatu (zależy od opcji):
┌──────────────────────────────┐
│ Opcja 6: TXT                 │
│ Opcja 7: TXT, CSV, JSON, XML │
│ Opcja 8: CSV, JSON, XML      │
└──────────────────────────────┘
    ↓
💾 Zapis pliku
    ↓
Powrót do Menu
```

---

### 3.7 Opcja 0: Wyjście

```
Menu Główne
    ↓
[Użytkownik wybiera 0]
    ↓
🔴 Koniec aplikacji
```

---

## 4. Architektura UX

```
┌─────────────────────────────────────┐
│         Warstwa UI (UI)             │
│  UserInput, StatsPrinter, ReportSaver
└──────────────────┬──────────────────┘
                   │
┌──────────────────▼──────────────────┐
│      Warstwa Aplikacji (APP)        │
│  TextApp, TextMenu, MenuAction       │
└──────────────────┬──────────────────┘
                   │
┌──────────────────▼──────────────────┐
│   Warstwa Logiki (CORE + MODEL)     │
│  TextAnalyzer, Normalizer, Tokenizer │
└──────────────────┬──────────────────┘
                   │
┌──────────────────▼──────────────────┐
│    Warstwa I/O i Formatowania       │
│  FileReader, ReportWriter, Formatters
└─────────────────────────────────────┘
```

---

## 5. Stan aplikacji

### Ustawienia użytkownika

Aplikacja przechowuje dwa stany:

```
Settings {
    minWordLength: int       (domyślnie: 0)
    stopWordsEnabled: bool   (domyślnie: true)
}
```

Te ustawienia wpływają na:
- Wyświetlanie Top N słów
- Wyświetlanie częstości w fragmencie
- Zapisywanie raportów

---

## 6. Mapa decyzji

```
START
  │
  ├─→ Wczytaj plik tekstowy
  │
  └─→ Pętla Menu
      │
      ├─→ Pokaż statystyki?
      │     └─→ Wyświetl podstawowe statystyki
      │
      ├─→ Pokaż top słowa?
      │     ├─→ Pobierz N
      │     ├─→ Filtruj po min. długości
      │     ├─→ Filtruj stop-words?
      │     └─→ Wyświetl top N
      │
      ├─→ Pokaż częstość w fragmencie?
      │     ├─→ Pobierz fragment
      │     ├─→ Filtruj po min. długości
      │     ├─→ Filtruj stop-words?
      │     └─→ Wyświetl częstość
      │
      ├─→ Zmień min. długość słowa?
      │     └─→ Aktualizuj Settings
      │
      ├─→ Toggle stop-words?
      │     └─→ Zmień Settings.stopWordsEnabled
      │
      ├─→ Zapisz raport?
      │     ├─→ Pobierz nazwę pliku
      │     ├─→ Wybierz format(y)
      │     └─→ Zapisz plik(i)
      │
      └─→ Wyjście?
            └─→ KONIEC
```

---

## 7. Interakcje użytkownika

| Akcja | Input | Output | Stany |
|-------|-------|--------|-------|
| Start | Nazwa pliku | Menu główne | Plik załadowany |
| Opcja 1 | - | Statystyki | - |
| Opcja 2 | N (liczba) | Top N słów | minLength, stopWords |
| Opcja 3 | Fragment | Częstość | minLength, stopWords |
| Opcja 4 | Nowa długość | Potwierdzenie | Settings.minLength |
| Opcja 5 | - | Toggle info | Settings.stopWords |
| Opcja 6-8 | Nazwa pliku | Zapis + powrót | - |
| Opcja 0 | - | Koniec | - |

---

## 8. Obsługa błędów

```
Walidacja wejścia
    │
    ├─→ Nieznana opcja menu
    │     └─→ Wyświetl "Nieznana opcja." + ponowne menu
    │
    ├─→ Plik nie istnieje?
    │     └─→ Exception handling
    │
    └─→ Niepoprawny format?
          └─→ Try-catch + komunikat błędu
```

---

## 9. Podsumowanie UX

**Pozytywne aspekty:**
✅ Prosty, przejrzysty interfejs menu
✅ Jasne numerowanie opcji
✅ Powtarzalny schemat (Menu → Akcja → Menu)
✅ Ustawienia wpływające na wyniki
✅ Wiele formatów raportu

**Możliwości usprawnienia:**
- Wskaźnik aktualnych ustawień w menu
- Podpowiedzi/help dla każdej opcji
- Walidacja danych wejściowych (N > 0, długość > 0)
- Opcja "powrót do menu" zamiast automatycznego
- Wyświetlenie stanu załadowanego pliku

