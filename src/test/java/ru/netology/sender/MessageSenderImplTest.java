package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {

    private static final String MOSCOW_IP = "172.123.12.11";
    private static final String USA_IP = "96.12.13.19";
    private LocalizationServiceImpl localizationServiceImpl = Mockito.mock(LocalizationServiceImpl.class);
    private GeoServiceImpl geoServiceImpl = Mockito.mock(GeoServiceImpl.class);
    //Проверить, что MessageSenderImpl всегда отправляет только русский текст,
    // если ip относится к российскому сегменту адресов.

    @Test
    void Message_COUNTRY_RUSS() {

        MessageSender messageSender = new MessageSenderImpl(geoServiceImpl, localizationServiceImpl);

        Mockito.when(geoServiceImpl.byIp(Mockito.eq(MOSCOW_IP))).thenReturn(new Location("Moscow", Country.RUSSIA, "Kamova", 1));
        Mockito.when(localizationServiceImpl.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, MOSCOW_IP);
        String actual = messageSender.send(headers);


        Boolean expected = Pattern.matches(".*\\p{InCyrillic}.*", "Добро пожаловать");
        Assertions.assertEquals(expected, Pattern.matches(".*\\p{InCyrillic}.*",actual));
    }

    //Если IP-Американский, то сообщение не русское
    @Test
    void Message_COUNRY_USA() {

        MessageSender messageSender = new MessageSenderImpl(geoServiceImpl, localizationServiceImpl);

        Mockito.when(geoServiceImpl.byIp(Mockito.eq(USA_IP))).thenReturn(new Location("New York", Country.USA, "Kamova", 1));
        Mockito.when(localizationServiceImpl.locale(Country.USA)).thenReturn("Welcome");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, USA_IP);
        String actual = messageSender.send(headers);

        Boolean expected = Pattern.matches(".*\\p{InCyrillic}.*", "Welcome");
        Assertions.assertEquals(expected, Pattern.matches(".*\\p{InCyrillic}.*",actual));

    }

}