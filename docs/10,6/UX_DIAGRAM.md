# Diagram UX - Text Analyzer

## Przepływ użytkownika w aplikacji

```mermaid
graph TD
    Start([Start aplikacji]) --> Input["📝 Wpisz nazwę pliku<br/>(bez .txt)"]
    Input --> FileLoad["📂 Wczytanie pliku"]
    FileLoad --> Menu["🎯 MENU GŁÓWNE"]
    
    Menu --> Option1["1. Statystyki podstawowe"]
    Menu --> Option2["2. Top N słów"]
    Menu --> Option3["3. Częstość w fragmencie"]
    Menu --> Option4["4. Zmień min. długość słowa"]
    Menu --> Option5["5. Toggleuj stop-words"]
    Menu --> Option6["6. Zapisz raport basic"]
    Menu --> Option7["7. Zapisz raport full"]
    Menu --> Option8["8. Zapisz raport frequency"]
    Menu --> Option0["0. Wyjście"]
    
    Option1 --> Display1["📊 Wyświetl:<br/>- Liczba słów<br/>- Liczba znaków<br/>- Liczba zdań"]
    Option2 --> Input2["📌 Podaj N"]
    Input2 --> Settings1{Ustawienia:<br/>- Min. długość?<br/>- Stop-words?}
    Settings1 --> Display2["📊 Wyświetl Top N słów"]
    
    Option3 --> Input3["📌 Podaj fragment tekstu"]
    Input3 --> Display3["📊 Wyświetl częstość słów<br/>w fragmencie"]
    
    Option4 --> Input4["📌 Podaj nową min. długość"]
    Input4 --> Update1["✅ Ustawienie zmienione"]
    
    Option5 --> Toggle["✅ Stop-words: ON/OFF"]
    
    Option6 --> Input5["💾 Podaj nazwę pliku"]
    Input5 --> Save1["💾 Zapisz raport (TXT)"]
    
    Option7 --> Input6["💾 Podaj nazwę pliku"]
    Input6 --> Save2["💾 Zapisz raport (Multi-format)"]
    
    Option8 --> Input7["💾 Podaj nazwę pliku"]
    Input7 --> Save3["💾 Zapisz raport (CSV/JSON/XML)"]
    
    Option0 --> End(["🔴 Koniec aplikacji"])
    
    Display1 --> Menu
    Display2 --> Menu
    Display3 --> Menu
    Update1 --> Menu
    Toggle --> Menu
    Save1 --> Menu
    Save2 --> Menu
    Save3 --> Menu
    
    style Start fill:#90EE90
    style End fill:#FFB6C6
    style Menu fill:#87CEEB
    style Input fill:#FFE4B5
    style FileLoad fill:#DDA0DD

