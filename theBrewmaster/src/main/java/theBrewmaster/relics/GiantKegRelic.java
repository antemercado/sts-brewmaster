package theBrewmaster.relics;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.ApplyIntoxicationPower;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GiantKegRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("GiantKegRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("beer_stein.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("beer_stein.png"));

    public GiantKegRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        this.counter = 0;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
            if (AbstractDungeon.player.hasRelic(BeerSteinRelic.ID)){
                this.counter = AbstractDungeon.player.getRelic(BeerSteinRelic.ID).counter;
            }
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        if (this.counter <= 0)
            return;
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, this.counter, true)));
    }

    public void onVictory(){
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(IntoxicationPower.POWER_ID)){
            this.counter = 0;
            return;
        }
        this.counter = p.getPower(IntoxicationPower.POWER_ID).amount;
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BeerSteinRelic.ID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}