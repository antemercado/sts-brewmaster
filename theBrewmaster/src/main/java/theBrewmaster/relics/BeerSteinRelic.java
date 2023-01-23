package theBrewmaster.relics;

import basemod.abstracts.CustomRelic;
import theBrewmaster.DefaultMod;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.DefaultMod.makeRelicOutlinePath;
import static theBrewmaster.DefaultMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BeerSteinRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BeerSteinRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BeerSteinRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void atBattleStartPreDraw() {
        if (this.counter <= 0)
            return;
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, this.counter)));
    }

    public void onVictory(){
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(IntoxicationPower.POWER_ID))
            return;
        this.counter = p.getPower(IntoxicationPower.POWER_ID).amount;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}