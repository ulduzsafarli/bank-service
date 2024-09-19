package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.az.bankservice.service.CurrencyService;
import java.az.bankservice.util.FetchingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${url.prefix}")
    private String urlPrefix;

    private String generateUrlWithDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(new Date());
        return urlPrefix + formattedDate + ".xml";
    }

    @Override
    public void fetch() {
        String currentUrl = generateUrlWithDate();
        log.info("Fetching and saving from URL: {}", currentUrl);
        String xmlData = FetchingUtil.fetchXmlData(currentUrl);
        if (Objects.nonNull(xmlData)) {
            String filteredCurrencies = FetchingUtil.filterCurrencies(xmlData);
            FetchingUtil.saveCurrenciesToFile(filteredCurrencies);
            log.info("Successfully fetch and save currency from URL: {}", currentUrl);
        }
    }

    @Override
    public String getFile() {
        log.info("Getting the latest currency file");
        String content = FetchingUtil.readCurrencyFile();
        log.info("Successfully read the latest currency file");
        return content;
    }
}
