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
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GiantKegRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("GiantKegRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("beer_stein_upgrade.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("beer_stein.png"));

    private static int RETAIN = 150;

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
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BeerSteinRelic.ID)){
            for (AbstractRelic r : AbstractDungeon.player.relics){
                if (r.relicId.equals(BeerSteinRelic.ID)){
                    instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.indexOf(r), true);
                    break;
                }
            }
        } else {
            super.obtain();
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
        if (p.getPower(IntoxicationPower.POWER_ID).amount > RETAIN){
            this.counter = RETAIN;
        } else {
            this.counter = p.getPower(IntoxicationPower.POWER_ID).amount;
        }
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