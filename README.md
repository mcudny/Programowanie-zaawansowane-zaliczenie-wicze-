**Opis projektu**

Projekt przedstawia aplikację działającą w modelu klient–serwer, zrealizowaną w języku Java, zgodnie z założeniami zadania projektowego. Serwer przechowuje zestaw obiektów różnych klas i udostępnia je klientom na żądanie. Klienci łączą się z serwerem, przekazują swoje identyfikatory, a następnie pobierają kolekcje obiektów określonego typu. Aplikacja obsługuje wielu klientów równocześnie, z ograniczeniem maksymalnej liczby aktywnych połączeń.

**Struktura projektu**

Projekt składa się z klas:
* Server – uruchamia serwer, inicjalizuje dane oraz obsługuje klientów (osoby odpowiedzialne: Mateusz Cudny 166174 i Kacper Kujawski 166188)
* Client – aplikacja kliencka (osoba odpowiedzialna: Kacper Kujawski 166188)
* Laptop – klasa reprezentująca laptop (osoba odpowiedzialna: Kacper Kujawski 166188)
* Telefon – klasa reprezentująca telefon (osoba odpowiedzialna: Mateusz Cudny 166174)
* Ksiazka – klasa reprezentująca książkę (osoba odpowiedzialna: Mateusz Cudny 166174)

Każda z klas domenowych:
* posiada co najmniej dwa pola
* zawiera konstruktor
* implementuje metody toString(), equals() oraz hashCode()
* implementuje interfejs Serializable

Po uruchomieniu serwer:
* tworzy po cztery obiekty każdej klasy (Laptop, Telefon, Ksiazka)
* inicjalizuje je różnymi danymi
* zapisuje je w mapie (Map<String, Object>)

Serwer:
* obsługuje klientów w oddzielnych wątkach
* posiada stałą MAX_CLIENTS określającą maksymalną liczbę jednocześnie obsługiwanych klientów
* rejestruje identyfikatory klientów
* w przypadku przekroczenia limitu klientów odsyła status REFUSED
* posiada losowe opóźnienia podczas obsługi klientów

**Komunikacja klient–serwer**

1. Klient łączy się z serwerem i wysyła swoje ID.
2. Serwer odpowiada jednym z statusów:
   * OK – połączenie zaakceptowane
   * REFUSED – połączenie odrzucone
3. W przypadku odpowiedzi REFUSED klient kończy działanie
4. W przypadku odpowiedzi OK:
   * klient wysyła nazwę klasy, której obiekty chce otrzymać
   * serwer pobiera odpowiednie obiekty z mapy
   * tworzy kolekcję obiektów i przesyła ją w postaci zserializowanej
   * serwer wypisuje na konsoli informacje o tym, jakie obiekty i któremu klientowi (ID) zostały przesłane

Klient:
* odbiera kolekcję obiektów
* przetwarza ją strumieniowo
* wypisuje każdy obiekt na konsoli wraz ze swoim identyfikatorem

**Sposób uruchamiania projektu**
1. Uruchomić klasę Server
2. Uruchomić klasę Client
3. Każdy klient powinien posiadać inne ID
4. Na konsoli serwera i klientów można obserwować przebieg komunikacji

Projekt realizuje wszystkie wymagania zadania:
* model klient–serwer
* obsługę wielu klientów z limitem połączeń
* serializację i przesyłanie obiektów
* obsługę wyjątków
* współbieżność oraz losowe opóźnienia
