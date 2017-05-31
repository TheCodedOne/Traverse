package prospector.traverse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import prospector.shootingstar.version.Version;
import prospector.traverse.core.TraverseConstants;

import java.io.*;

public class TraverseConfig {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static File configDir;
    public static File versionConfig;
    public static File mainConfig;
    public static Configuration config;
    public static TraverseConfig traverseConfiguration;

    public static Version version;
    public static int major;
    public static int minor;
    public static int patch;

    public static boolean useVanillaWood = false;

    private static TraverseConfig instance = null;

    private TraverseConfig() {
        config = new Configuration(mainConfig);
        config.load();

        useVanillaWood = config.get(Configuration.CATEGORY_GENERAL, "useVanillaWood", useVanillaWood, "Use vanilla logs for Traverse trees (might not look as nice)").getBoolean();

        config.save();
    }

    public static TraverseConfig initialize() {
        if (instance == null)
            instance = new TraverseConfig();
        else
            throw new IllegalStateException("Cannot initialize config twice");

        return instance;
    }

    public static TraverseConfig getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance of config requested before initialization");
        }
        return instance;
    }

    public static void initialize(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), TraverseConstants.MOD_ID);
        if (!configDir.exists()) {
            configDir.mkdir();
        }

        mainConfig = new File(configDir, "traverse.cfg");
        traverseConfiguration = initialize();

//        versionConfig = new File(configDir, "instance_version.json");
//
//        try {
//            Pattern pattern = Pattern.compile("-(\\d+)\\.(\\d+)\\.(\\d+)-");
//            Matcher matcher = pattern.matcher(TraverseConstants.MOD_VERSION);
//            major = Integer.parseInt(matcher.group(1));
//            minor = Integer.parseInt(matcher.group(2));
//            patch = Integer.parseInt(matcher.group(3));
//            version = new Version(major, minor, patch);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            version = Version.NULL_VERSION;
//        }
//
//        reloadVersionConfig();
    }

    public static void reloadVersionConfig() {
        if (!versionConfig.exists()) {
            writeVersionConfig(new VersionConfig());
        }
        if (versionConfig.exists()) {
            VersionConfig config = null;
            try (Reader reader = new FileReader(versionConfig)) {
                config = GSON.fromJson(reader, VersionConfig.class);
                major = config.major;
                minor = config.minor;
                patch = config.patch;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (config == null) {
                config = new VersionConfig();
                writeVersionConfig(config);
            }
        }
    }

    public static void writeVersionConfig(VersionConfig config) {
        try (Writer writer = new FileWriter(versionConfig)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {

        }
        reloadVersionConfig();
    }

    public static class VersionConfig {

        public int major = TraverseConfig.major;
        public int minor = TraverseConfig.minor;
        public int patch = TraverseConfig.patch;

        public int getMajor() {
            return major;
        }

        public void setMajor(int major) {
            this.major = major;
        }

        public int getMinor() {
            return minor;
        }

        public void setMinor(int minor) {
            this.minor = minor;
        }

        public int getPatch() {
            return patch;
        }

        public void setPatch(int patch) {
            this.patch = patch;
        }
    }
}