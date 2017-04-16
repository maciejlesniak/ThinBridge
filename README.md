![WSPA Logo](http://old.wspa.pl/img/wspa-logo-wh.png)

# Thin Bridge

Propozycja rozwiązania zadania z mostem, który ma tylko jeden pas ruchu i dwie grupy samochodów czekających na przejazd po obu stronach drogi. 
Zawiadujący ruchem, ma za zadanie przepuszczanie odpowiedniej liczby samochodów z każdej ze stron. 
Jeżeli po jednej stronie nie zostanie ani jeden pojazd, wtedy pojazdy z drugiej strony zostają dopuszczone do ruchu. 

W wyjątkowych sytuacjach zawiadowca ma możliwość również zmiany "aktywnej" strony mostu, tak, aby mogły przejechać pojazdy czekające na przeciwnej stronie. 

Za prawidłowy ruch pojazdów odpowiada sygnalizacja świetlna zainstalowana na każdej stronie mostu. 
Sygnalizacja świetlna ma 2 stany: czerwony - oznaczający brak możliwości przekazy oraz zielony - oznaczający zezwolenie na przejazd. 

Aktualny kierunek przejazdu samochodów przedstawiony jest za pomocą ikony samochodu oraz strzałek. 

## Użyte technologie
- Java FX
- maven


## Uruchomienie projektu

`mvn clean:clean jfx:run`

