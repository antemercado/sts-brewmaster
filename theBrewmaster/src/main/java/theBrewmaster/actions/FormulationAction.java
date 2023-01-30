package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomTags;

public class FormulationAction extends AbstractGameAction{
    
    public static final String ID = BrewmasterMod.makeID(FormulationAction.class.getSimpleName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public FormulationAction(AbstractPlayer p, int discardAmount) {
        this.source = p;
        this.target = p;
        this.p = p;
        this.amount = discardAmount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()){
                this.isDone = true;
                return;
            }
    
            if (this.p.hand.size() == 1) {
                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                tickDuration();
                return;
            }
    
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            tickDuration();
    
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                if (c.hasTag(CustomTags.BREW)){
                    addToTop(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                }
                this.p.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }
}
