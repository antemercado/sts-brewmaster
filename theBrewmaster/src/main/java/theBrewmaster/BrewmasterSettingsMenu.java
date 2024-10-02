package theBrewmaster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.ModSlider;
import basemod.interfaces.PostInitializeSubscriber;
import theBrewmaster.util.TextureLoader;

public class BrewmasterSettingsMenu implements PostInitializeSubscriber{

    public static final String BADGE_IMAGE = "theBrewmasterResources/images/Badge.png";
    private static final String MODNAME = "The Brewmaster";
    private static final String AUTHOR = "Coda";
    private static final String DESCRIPTION = "The Brewmaster Character Mod";

    private static final float SLIDER_X_POS = 450.0f;

    public static final Logger logger = LogManager.getLogger(BrewmasterMod.class.getName());


    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        BrewmasterMod.load();

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        ModLabel beerSteinLabel = new ModLabel(
            "Amount of charges that the Beer Stein can hold.",
            400.0f, 700.0f,
            settingsPanel,
            (me) -> {}
        );

        ModSlider beerSteinSlider = new ModSlider("",
            SLIDER_X_POS,
            665.0f,
            200, "", settingsPanel, (me) -> {
                int val = Math.round(me.value * me.multiplier);
                BrewmasterMod.beerSteinAmount = val;
                BrewmasterMod.save();
            }
        );

        ModLabel intoxDecayLabel = new ModLabel(
            "Amount of intoxication lost each turn.",
            400.0f, 600.0f,
            settingsPanel,
            (me) -> {}
        );

        ModSlider intoxDecaySlider = new ModSlider("",
            SLIDER_X_POS,
            565.0f,
            100, "%", settingsPanel, (me) -> {
                int val = Math.round(me.value * me.multiplier);
                BrewmasterMod.intoxDecayAmount = val;
                BrewmasterMod.save();
            }
        );

        ModLabel intoxThreshLabel = new ModLabel(
            "Amount of intoxication needed to enter Intoxicated stance.",
            400.0f, 500.0f,
            settingsPanel,
            (me) -> {}
        );

        ModSlider intoxThreshSlider = new ModSlider("",
            SLIDER_X_POS,
            465.0f,
            200, "", settingsPanel, (me) -> {
                int val = Math.round(me.value * me.multiplier);
                BrewmasterMod.intoxThreshAmount = val;
                BrewmasterMod.save();
            }
        );

        beerSteinSlider.setValue((float)BrewmasterMod.beerSteinAmount / 200.0f);
        intoxDecaySlider.setValue((float)BrewmasterMod.intoxDecayAmount / 100.0f);
        intoxThreshSlider.setValue((float)BrewmasterMod.intoxThreshAmount / 200.0f);

        settingsPanel.addUIElement(beerSteinLabel);
        settingsPanel.addUIElement(beerSteinSlider);
        settingsPanel.addUIElement(intoxDecayLabel);
        settingsPanel.addUIElement(intoxDecaySlider);
        settingsPanel.addUIElement(intoxThreshLabel);
        settingsPanel.addUIElement(intoxThreshSlider);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");
    }

}
