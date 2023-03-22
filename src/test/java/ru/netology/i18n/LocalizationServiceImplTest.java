package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    @Test
    void TextMassage(){
        LocalizationServiceImpl localizationServiceImpl = new LocalizationServiceImpl();
        Assertions.assertEquals("Добро пожаловать", localizationServiceImpl.locale(Country.RUSSIA));
    }

}