package todfresser.smash.game;

import org.bukkit.configuration.file.FileConfiguration;

public interface MapComponent {

    String getIdentifier();

    void read(FileConfiguration cfg);

    void write(FileConfiguration cfg);

    boolean validate();

}
