package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomTags;

public class ShotsAction extends AbstractGameAction{

    public static final String ID = BrewmasterMod.makeID(FormulationAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;
    private boolean upgraded;
    private AbstractPlayer p;

    public ShotsAction(boolean upgraded){
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 0;
        this.upgraded = upgraded;
        this.p = AbstractDungeon.player;
    }
    @Override
    public void update() {
        addToTop((AbstractGameAction)new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone){
            for (AbstractCard c : DrawCardAction.drawnCards){
                if (!c.hasTag(CustomTags.BREW)){
                    return;
                }
                if (this.p.hand.contains(c)){
                    addToBot(new PlayCardFromHandAction(c));
                    if (!upgraded){
                        addToBot(new ExhaustSpecificCardAction(c, this.p.drawPile));
                    }
                    return;
                }
                if (this.p.discardPile.contains(c)){
                    addToBot(new PlayCardFromDiscardAction(c));
                    if (!upgraded){
                        addToBot(new ExhaustSpecificCardAction(c, this.p.discardPile));
                    }
                    return;
                }
            }
        }
    }    
}
