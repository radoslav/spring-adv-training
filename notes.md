Bazując na przykładzie z płatnościami, zaimplementować moduł orders tak, aby spełniał kontrakt zdefiniowany w orders-ports.
Dodać wymagane adaptery na poziomie shop-service, tak aby możliwe było składanie zamówień
(wymaga stworzenia FakeProductsProviderAdapter, który w przyszłości będzie pobierał dane z sieci oraz integracji z modułem Payments).
Składanie zamówienia ma się sprowadzać do naliczenia należności / kwoty całkowitej, dokonania płatności na tę kwotę i zalogowania
na konsoli informacji o zamówieniu i płatności.
