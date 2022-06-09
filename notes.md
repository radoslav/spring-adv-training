- endpoint, ktory wystwaiwa nieskonczony strumien kursu wymiany waluty
- klient, ktory nasluchuje zmian kurs
- endpoint, ktory pozwala na wymiane kwoty po aktualnym kursie (ostatni aktulny ze strumienia kursy waluty),
    przy czym jednoczesnie loguje w bazie kwote, czas i kurs wymiany

- stworzyć usługę magazyn, która będzie pozwalać na:
  - wyszykiwanie produktów według zadanej specyfikacji 
  - rozgłaszać przez kafkę informacje o aktualizacji produktów
  - umożliwać rezerwację brakująych produktów i powiadamiać mailem o tym, że są one dostępne
