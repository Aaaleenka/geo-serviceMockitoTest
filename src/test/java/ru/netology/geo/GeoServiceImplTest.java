package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    private Location location;
    private static final String MOSCOW_IP = "172.123.12.11";
    private static final String USA_IP = "96.12.13.19";

    @Test
    void locationRUS(){

        GeoServiceImpl geoService = new GeoServiceImpl();

        Location location = geoService.byIp(MOSCOW_IP);
        Assertions.assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    void locationUSA(){
        GeoServiceImpl geoService = new GeoServiceImpl();

        Location location = geoService.byIp(USA_IP);
        Assertions.assertEquals(Country.USA, location.getCountry());
    }
}