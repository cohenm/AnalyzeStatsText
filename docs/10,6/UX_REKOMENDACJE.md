# UX Rekomendacje - Text Analyzer

## 1. Analiza obecnego UX

### Mocne strony ✅
- **Prosty interfejs menu** - Łatwy do nauki dla nowych użytkowników
- **Jasna numeracja** - Szybki dostęp do funkcji (0-8)
- **Konsekwentny schemat** - Menu → Akcja → Menu (przewidywalny)
- **Modularna architektura** - Łatwe dodawanie nowych akcji
- **Wiele formatów raportu** - CSV, JSON, XML, TXT
- **Konfiguracja** - Możliwość dostosowania ustawień

### Słabe strony ⚠️
1. **Brak statusu bieżących ustawień** - Użytkownik nie wie jaki minLength jest aktualnie ustawiony
2. **Brak walidacji danych** - N może być 0 lub ujemne, długość może być negat
3. **Brak potwierdzenia akcji destructive** - Np. przy zapisie
4. **Brak help system** - Żaden wskaźnik co zrobić na każdym ekranie
5. **Brak informacji o załadowanym pliku** - Ile znaków, słów itp w samym menu
6. **Brak paginacji** - Top 1000 słów = ściana tekstu
7. **Brak historii akcji** - Nie wiadomo co zostało zrobione
8. **Brak obsługi Ctrl+C** - Nieskładne wyjście
9. **Brak progressbar** - Dla dużych plików
10. **Brak temów** - Tylko czarny tekst na białym

---

## 2. Rekomendacje usprawniające (Priority: HIGH)

### 2.1 Wyświetl status ustawień w menu

**Zmiana:**
```
┌─────────────────────────────────────┐
│  === MENU ===                       │
│  📊 Settings:                       │
│     Min length: 3                   │
│     Stop-words: ON                  │
│                                     │
│  1. Basic Statistics                │
│  2. Top N Words                     │
│  ...                                │
│  Wybór: [_]                         │
└─────────────────────────────────────┘
```

**Kod sugerowany:**
```java
private void printMenu() {
    System.out.println("\n=== MENU ===");
    System.out.println("📊 Settings: Min=" + settings.getMinLength() 
                      + " | StopWords=" + (settings.isStopWordsEnabled() ? "ON" : "OFF"));
    System.out.println("───────────────────");
    actions.values().forEach(a -> System.out.println(a.label()));
    System.out.print("Wybór: ");
}
```

---

### 2.2 Walidacja danych wejściowych

**Dla opcji "Top N Words":**
```java
int n = Integer.parseInt(input.readLine().trim());
while (n <= 0) {
    System.out.println("❌ N musi być > 0");
    System.out.print("Podaj N: ");
    n = Integer.parseInt(input.readLine().trim());
}
```

**Dla "Min Word Length":**
```java
int newLength = Integer.parseInt(input.readLine().trim());
if (newLength < 0) {
    System.out.println("❌ Długość nie może być ujemna");
    return;
}
settings.setMinLength(newLength);
System.out.println("✅ Min długość zmieniona na: " + newLength);
```

---

### 2.3 Wyświetl info o załadowanym pliku

**Po załadowaniu pliku:**
```
┌─────────────────────────────────────┐
│  📂 Plik załadowany: document.txt   │
│  📊 Statystyka:                     │
│     • Słowa: 1,250                  │
│     • Znaki: 8,423                  │
│     • Zdania: 45                    │
│                                     │
│  === MENU ===                       │
└─────────────────────────────────────┘
```

---

### 2.4 Help system

**Zmiana:**
```
┌─────────────────────────────────────┐
│  === MENU ===                       │
│                                     │
│  1. Basic Statistics - pokaż stat   │
│  2. Top N Words - N słów             │
│  3. Frequency Fragment - słowa w txt │
│  4. Min Word Length - zmień ustawienie
│  5. Toggle Stop-Words - włącz/wyłącz
│  6. Save Basic Report - raport TXT  │
│  7. Save Full Report - wieloformat  │
│  8. Save Frequency Report - CSV/JSON│
│  0. Exit - wyjście                  │
│                                     │
│  Wskaż 'H' dla help, 'S' dla status │
│  Wybór: [_]                         │
└─────────────────────────────────────┘
```

---

### 2.5 Paginacja dla dużych wyników

**Zamiast drukować wszystko naraz:**
```java
public void printTopWordsWithPagination(List<WordCount> words, int pageSize) {
    int totalPages = (words.size() + pageSize - 1) / pageSize;
    int currentPage = 0;
    
    while (true) {
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, words.size());
        
        System.out.println("\n=== TOP WORDS (Page " + (currentPage + 1) 
                          + "/" + totalPages + ") ===");
        
        for (int i = start; i < end; i++) {
            WordCount wc = words.get(i);
            System.out.printf("%d. %-20s %d%n", i+1, wc.getWord(), wc.getCount());
        }
        
        System.out.println("\n[N]ext | [P]revious | [Q]uit");
        String choice = input.readLine().trim().toUpperCase();
        
        if (choice.equals("N") && currentPage < totalPages - 1) {
            currentPage++;
        } else if (choice.equals("P") && currentPage > 0) {
            currentPage--;
        } else if (choice.equals("Q")) {
            break;
        }
    }
}
```

---

## 3. Rekomendacje usprawniające (Priority: MEDIUM)

### 3.1 Potwierdzenie dla akcji save

```java
System.out.print("Podaj nazwę pliku: ");
String filename = input.readLine().trim();

File file = new File(filename);
if (file.exists()) {
    System.out.println("⚠️  Plik już istnieje!");
    System.out.print("Czy na pewno chcesz go nadpisać? [Y/n]: ");
    String confirm = input.readLine().trim().toLowerCase();
    if (!confirm.equals("y") && !confirm.isEmpty()) {
        System.out.println("❌ Anulowano");
        return;
    }
}

saver.save(filename, settings);
System.out.println("✅ Raport zapisany: " + filename);
```

---

### 3.2 Obsługa Ctrl+C

```java
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    System.out.println("\n\n👋 Aplikacja zamknięta. Do widzenia!");
}));
```

---

### 3.3 Progress bar dla dużych plików

```java
public void showProgress(int current, int total) {
    int progress = (int) ((double) current / total * 100);
    int filled = progress / 5;
    int empty = 20 - filled;
    
    System.out.print("\r[");
    for (int i = 0; i < filled; i++) System.out.print("█");
    for (int i = 0; i < empty; i++) System.out.print("░");
    System.out.print("] " + progress + "%");
}
```

---

### 3.4 Presets dla ustawień

```
┌─────────────────────────────────────┐
│  ⚙️  PRESETS                         │
│                                     │
│  1. Academic (long words, no stops) │
│  2. Social Media (short, with stops)│
│  3. Custom...                       │
│                                     │
│  Wybór: [_]                         │
└─────────────────────────────────────┘
```

---

## 4. Rekomendacje usprawniające (Priority: LOW)

### 4.1 Kolory i emoji

```java
public class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    
    public static void success(String msg) {
        System.out.println(GREEN + "✅ " + msg + RESET);
    }
    
    public static void warning(String msg) {
        System.out.println(YELLOW + "⚠️  " + msg + RESET);
    }
    
    public static void error(String msg) {
        System.out.println(RED + "❌ " + msg + RESET);
    }
}
```

---

### 4.2 Alias dla opcji

```
Zamiast tylko: 1, 2, 3...
Dodać: 
  1 or 'stats' or 's'
  2 or 'top' or 't'
  3 or 'freq' or 'f'
```

---

### 4.3 Historia akcji

```java
public class ActionHistory {
    private Queue<String> history = new LinkedList<>();
    private static final int MAX_HISTORY = 10;
    
    public void add(String action) {
        history.add(LocalDateTime.now() + " - " + action);
        if (history.size() > MAX_HISTORY) {
            history.poll();
        }
    }
    
    public void show() {
        System.out.println("\n=== HISTORIA ===");
        for (String entry : history) {
            System.out.println(entry);
        }
    }
}
```

---

### 4.4 Eksport konfiguracji

```
Dodać opcję: 'X. Export Settings'
Która zapisze ustawienia do settings.json:
{
  "minWordLength": 3,
  "stopWordsEnabled": true,
  "lastFile": "document.txt"
}
```

---

## 5. Propozycja nowej struktury menu

### Wersja ulepszona

```
┌─────────────────────────────────────┐
│  📊 TEXT ANALYZER v2.0              │
├─────────────────────────────────────┤
│  📂 Plik: document.txt              │
│  📈 Słowa: 1,250 | Znaki: 8,423    │
│  ⚙️  Min: 3 | Stop-words: ON       │
├─────────────────────────────────────┤
│                                     │
│  📊 ANALYSIS                        │
│  1. Show basic statistics           │
│  2. Show top N words                │
│  3. Show word frequency             │
│                                     │
│  ⚙️  SETTINGS                        │
│  4. Set minimum word length         │
│  5. Toggle stop-words               │
│                                     │
│  💾 EXPORT                          │
│  6. Save basic report (TXT)         │
│  7. Save full report (CSV/JSON...)  │
│  8. Save frequency report           │
│                                     │
│  🔧 TOOLS                           │
│  9. Show history                    │
│  H. Help                            │
│  S. Settings summary                │
│                                     │
│  0. Exit                            │
│  ────────────────────────           │
│  Choose action (1-9, H, S, 0): [_]  │
│                                     │
└─────────────────────────────────────┘
```

---

## 6. Macierz decyzji - Prioritet zmian

| Rekomendacja | Priority | Effort | Impact | Status |
|---|---|---|---|---|
| Status w menu | HIGH | ⭐ | ⭐⭐⭐ | 🟢 TODO |
| Walidacja input | HIGH | ⭐ | ⭐⭐⭐ | 🟢 TODO |
| Info o pliku | HIGH | ⭐⭐ | ⭐⭐ | 🟢 TODO |
| Help system | MEDIUM | ⭐⭐⭐ | ⭐⭐ | 🟢 TODO |
| Paginacja | MEDIUM | ⭐⭐⭐ | ⭐⭐ | 🟢 TODO |
| Potwierdzenie save | MEDIUM | ⭐ | ⭐⭐ | 🟢 TODO |
| Kolory/emoji | LOW | ⭐ | ⭐ | 🟢 TODO |
| Historia | LOW | ⭐⭐ | ⭐ | 🟢 TODO |

---

## 7. Plan implementacji - Quick Wins

### Faza 1 (1-2 godziny)
1. ✅ Wyświetl status w menu
2. ✅ Walidacja liczb
3. ✅ Info o załadowanym pliku
4. ✅ Potwierdzenie przy save

### Faza 2 (2-3 godziny)
5. ⏳ Help na przycisk H
6. ⏳ Paginacja dla top words
7. ⏳ Kolory/emoji

### Faza 3 (1 godzina)
8. ⏳ Historia akcji
9. ⏳ Ulepszone komunikaty błędów

---

## 8. Mockupy - Przed i po

### PRZED

```
=== MENU ===
1. Statystyki podstawowe
2. Top N słów
3. Częstość w fragmencie
4. Zmień min. długość słowa
5. Toggleuj stop-words
6. Zapisz raport (basic)
7. Zapisz raport (full)
8. Zapisz raport (frequency)
0. Wyjście

Wybór: 2
Podaj N: 10

Top 10 słów:
słowo1 45
słowo2 38
...

=== MENU ===
...
```

### PO

```
📊 TEXT ANALYZER
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📂 File: document.txt
📈 Stats: Words=1,250 | Chars=8,423 | Sentences=45
⚙️  Settings: MinLength=3 | StopWords=ON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

📊 ANALYSIS
  1. Basic statistics
  2. Top N words
  3. Word frequency

⚙️  SETTINGS
  4. Min word length
  5. Toggle stop-words

💾 EXPORT
  6. Save basic report
  7. Save full report
  8. Save frequency report

🔧 TOOLS
  H. Help          S. Status         0. Exit

Choose action: 2

How many top words? 10

🔍 Analyzing...
[██████████░░░░░░░░] 50%

TOP 10 WORDS (Page 1/1)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
#1   analyzed        ▓▓▓▓▓▓▓▓░░  45
#2   important       ▓▓▓▓▓▓░░░░  38
#3   frequency       ▓▓▓▓▓░░░░░  32
#4   results         ▓▓▓▓░░░░░░  29
#5   system          ▓▓▓░░░░░░░  25
#6   process         ▓▓▓░░░░░░░  22
#7   algorithm       ▓▓░░░░░░░░  19
#8   optimization    ▓▓░░░░░░░░  16
#9   function        ▓░░░░░░░░░  14
#10  efficiency      ▓░░░░░░░░░  12
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[N]ext | [P]revious | [Q]uit: q

✅ Back to menu...
```

---

## 9. Checklist do wdrożenia

### HIGH Priority
- [ ] Dodaj status ustawień w printMenu()
- [ ] Dodaj walidację liczb całkowitych
- [ ] Wyświetl info o załadowanym pliku
- [ ] Dodaj potwierdzenie przy save

### MEDIUM Priority
- [ ] Implementuj help system
- [ ] Dodaj paginację
- [ ] Dodaj kolory ANSI
- [ ] Ulepsz komunikaty błędów

### LOW Priority
- [ ] Historia akcji
- [ ] Presets
- [ ] Eksport settings
- [ ] Alias dla opcji

---

## 10. Metryki do śledzenia

Po wdrożeniu zmian, śledź:

1. **Czas do wykonania akcji** - Czy skrócił się?
2. **Liczba błędów wejścia** - Czy mniej walidacji?
3. **Satysfakcja użytkownika** - Ankieta
4. **Zrozumienie menu** - Czy potrzebna help?
5. **Komplety sesji** - Ile akcji na sesję?

---

## Podsumowanie

**Obecny UX:** 6/10 - Funkcjonalny, ale bez frills
**Po HIGH zmianach:** 7.5/10 - Solidny i intuicyjny
**Po wszystkich zmianach:** 9/10 - Profesjonalny i przyjazny

Rekomenduję rozpocząć od zmian HIGH priority, które są proste do implementacji i dają duży impact na doświadczenie użytkownika.

