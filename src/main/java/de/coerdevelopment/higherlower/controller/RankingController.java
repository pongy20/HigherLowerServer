package de.coerdevelopment.higherlower.controller;

import de.coerdevelopment.higherlower.api.RankingEntry;
import de.coerdevelopment.higherlower.repository.RankingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @GetMapping("/all")
    private List<RankingEntry> all() {
        return RankingRepository.getInstance().getAllRankings();
    }

    @GetMapping("/score/{name}")
    private int getScoreByName(@PathVariable String name) {
        return RankingRepository.getInstance().getScore(name);
    }
    @PostMapping("/insert")
    private boolean insertScore(@RequestBody RankingEntry entry) {
        RankingRepository.getInstance().insertScore(entry.name, entry.score);
        return RankingRepository.getInstance().getScore(entry.name) > 0;
    }
    @PutMapping("/update")
    private void updateScore(@RequestBody RankingEntry entry) {
        int currentScore = RankingRepository.getInstance().getScore(entry.name);
        if (entry.score <= currentScore) {
            return;
        }
        RankingRepository.getInstance().updateScore(entry.name, entry.score);
    }

}
