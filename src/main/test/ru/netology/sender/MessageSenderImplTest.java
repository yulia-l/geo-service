package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTest {

    static GeoService geoService;
    static LocalizationService localizationService;
    static MessageSender messageSender;

    @BeforeAll
    public static void initTest() {
        geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location(null, Country.USA, null, 0));

        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testSend(String ip, String expectedMessage) {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String resultMessage = messageSender.send(headers);

        Assertions.assertEquals(expectedMessage, resultMessage);
    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of("172.", "Добро пожаловать"),
                Arguments.of("96.", "Welcome"));
    }
}