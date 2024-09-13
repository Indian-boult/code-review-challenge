package com.idealista.application;

import com.idealista.domain.*;
import com.idealista.infrastructure.api.AdDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdsServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdsServiceImpl scoreService;

    @Test
    public void calculateScoresTest() {
        when(adRepository.findAllAds()).thenReturn(Arrays.asList(irrelevantAd(), relevantAd()));
        scoreService.calculateScores();
        verify(adRepository).findAllAds();
        verify(adRepository, times(2)).save(any());
    }

    // New test method
    @Test
    public void findAdByIdTest() {
        Ad ad = relevantAd();
        when(adRepository.findAdById(1)).thenReturn(ad);

        AdDetails adDetails = scoreService.findAdById(1);

        verify(adRepository).findAdById(1);
        assertNotNull(adDetails);
        assertEquals(ad.getId(), adDetails.getId());
        assertEquals(ad.getDescription(), adDetails.getDescription());
        assertEquals(ad.getTypology().name(), adDetails.getTypology());
        assertEquals(ad.getHouseSize(), adDetails.getHouseSize());
        assertEquals(ad.getGardenSize(), adDetails.getGardenSize());
        assertEquals(ad.getScore(), adDetails.getScore());
        assertEquals(ad.getIrrelevantSince(), adDetails.getIrrelevantSince());
        assertEquals(ad.getPictures().size(), adDetails.getPictureUrls().size());
    }

    private Ad relevantAd() {
        return new Ad(1,
                Typology.FLAT,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                Arrays.asList(
                        new Picture(1, "http://urldeprueba.com/1", Quality.HD),
                        new Picture(2, "http://urldeprueba.com/2", Quality.HD)),
                50,
                null,
                80,
                null);
    }

    private Ad irrelevantAd() {
        return new Ad(2,
                Typology.FLAT,
                "",
                Collections.emptyList(),
                100,
                null,
                10,
                new Date());
    }
}
