package theBrewmaster.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import theBrewmaster.BrewmasterMod;

public class MisplaceAction extends AbstractGameAction{

    public static final String ID = BrewmasterMod.makeID(FormulationAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;
    
    private boolean upgraded;
    private AbstractPlayer p = AbstractDungeon.player;

    public MisplaceAction(boolean isUpgraded) {
        this.upgraded = isUpgraded;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            if (this.p.hand.isEmpty()){
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1){
                addToTop(new MoveCardsAction(null, null));
                this.p.hand.moveToDeck(this.p.hand.getBottomCard(), true);;
                p.drawPile.shuffle();
                tickDuration();
                return;
            }

            if (upgraded){
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            }
            tickDuration();

            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                this.p.hand.moveToDeck(c, true);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }
    
}
