package com.idealista.infrastructure.api;

import com.idealista.application.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AdsController {

    @Autowired
    private AdsService adsService;

    @GetMapping("/ads/quality")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        return ResponseEntity.ok(adsService.findQualityAds());
    }

    @GetMapping("/ads/public")
    public ResponseEntity<List<PublicAd>> publicListing() {
        return ResponseEntity.ok(adsService.findPublicAds());
    }

    @GetMapping("/ads/score")
    public ResponseEntity<Void> calculateScore() {
        adsService.calculateScores();
        return ResponseEntity.accepted().build();
    }

    // New endpoint to get ad by ID
    @GetMapping("/ads/{id}")
    public ResponseEntity<AdDetails> getAdById(@PathVariable Integer id) {
        AdDetails adDetails = adsService.findAdById(id);
        if (adDetails != null) {
            return ResponseEntity.ok(adDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
