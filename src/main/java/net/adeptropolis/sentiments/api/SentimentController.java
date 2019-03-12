package net.adeptropolis.sentiments.api;
import net.adeptropolis.sentiments.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SentimentController {

  private final SentimentAnalysisService service;

  @Autowired
  public SentimentController(SentimentAnalysisService service) {
    this.service = service;
  }

  @PostMapping("/sentiments")
  @ResponseBody
  public SentimentAnalysisResult analyze(@RequestBody SentimentAnalysisRequest request) {
    return service.analyze(request.getText());
  }

}
