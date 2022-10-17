package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

public class LocalizationServiceImplTest {
    LocalizationServiceImpl sut = new LocalizationServiceImpl();

    @ParameterizedTest
    @MethodSource("source")
    public void testLocal(Country country, String expected) {
        String result = sut.locale(country);

        Assertions.assertEquals(result, expected);
    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(RUSSIA, "Добро пожаловать"), Arguments.of(USA, "Welcome"));
    }
}