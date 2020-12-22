package com.tassiovirginio.jnoseanalyze;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.SingleThemeProvider;
import org.apache.wicket.protocol.http.WebApplication;

@ApplicationInitExtension
public class ConditionalConfig implements WicketApplicationInitConfiguration {

    private void configureBootstrap(WebApplication webApplication) {
        Bootstrap.install(webApplication,
                new BootstrapSettings().setThemeProvider(new SingleThemeProvider(new BootstrapTheme())));
    }

    @Override
    public void init(WebApplication webApplication) {
        configureBootstrap(webApplication);
    }
}


