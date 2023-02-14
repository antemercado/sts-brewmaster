package theBrewmaster.relics;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.ApplyIntoxicationPower;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.ui.campfire.RelaxOption;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class CozyBeerRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("CozyBeerRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public CozyBeerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void atBattleStartPreDraw() {
        if (this.counter <= 0)
            return;
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, this.counter, true)));
        this.counter = 0;
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new RelaxOption());
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}