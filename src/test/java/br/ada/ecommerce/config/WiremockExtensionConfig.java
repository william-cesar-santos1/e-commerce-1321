package br.ada.ecommerce.config;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

public class WiremockExtensionConfig extends WireMockExtension {

    protected WiremockExtensionConfig() {
        super(
                WireMockExtension.newInstance()
                        .options(
                                new WireMockConfiguration()
                                        .usingFilesUnderDirectory("./local/wiremock")
                                        .port(8282)
                                        .notifier(new ConsoleNotifier(true))
                        )
        );
    }

}
