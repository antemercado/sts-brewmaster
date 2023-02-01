package theBrewmaster.relics;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class SpiritHelmetRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("SpiritHelmetRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("spirit_helmet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("spirit_helmet.png"));

    public static final int MULTIPLIER = 2;

    public SpiritHelmetRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}