package be.bartel.sssm;

import com.budhash.cliche.Shell;
import com.budhash.cliche.ShellFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new GuiceConfig());
        ApplicationCommands applicationCommands = injector.getInstance(ApplicationCommands.class);
        final Shell consoleShell = ShellFactory
            .createConsoleShell("SSSM", "SSSM welcomes you, use ?l to start", applicationCommands);

        consoleShell.commandLoop();
    }
}
