package theBrewmaster.relics;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class LouseLiverRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("LouseLiverRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static final int INTOX_THRESHOLD = 50;

    public LouseLiverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void atBattleStartPreDraw() {
        flash();
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}