package com.yolt.yts.sdk.service.transaction.enrichment;

import lombok.Data;

import java.util.List;

@Data
public class MerchantSuggestions {
    List<String> merchantSuggestions;
}