package br.com.inventory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class InventoryApplication extends Application<InventoryConfiguration> {

    public static void main(final String[] args) throws Exception {
        new InventoryApplication().run(args);
    }

    @Override
    public String getName() {
        return "Inventory";
    }

    @Override
    public void initialize(final Bootstrap<InventoryConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final InventoryConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
