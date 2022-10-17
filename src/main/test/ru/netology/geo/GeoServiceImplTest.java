package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

public class GeoServiceImplTest {
    GeoService sut = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("source")
    public void testByIp(String ip, Country expectedCountry) {

        final Country resultCountry = sut.byIp(ip).getCountry();

        Assertions.assertEquals(expectedCountry, resultCountry);
    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of("127.0.0.1", null),
                Arguments.of("172.0.32.11", RUSSIA),
                Arguments.of("96.44.183.149", USA));
    }
}